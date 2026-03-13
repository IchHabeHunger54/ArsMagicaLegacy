package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.util.CrystalPhylacteryContentsSize;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Consumer;

public class CrystalPhylacteryItem extends Item {
    public static final Identifier FILL = ArsMagicaApi.id("crystal_phylactery_fill");

    public CrystalPhylacteryItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!player.isSecondaryUseActive()) return super.use(level, player, usedHand);
        ItemStack stack = player.getItemInHand(usedHand);
        stack.remove(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        return InteractionResultHolder.success(stack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount == 0) {
            tooltipComponents.add(AMTranslations.CRYSTAL_PHYLACTERY_EMPTY);
        } else {
            EntityType<?> type = contents.type;
            MutableComponent component = Component.translatable(AMTranslations.CRYSTAL_PHYLACTERY_KEY, type.getDescription(), contents.amount, CrystalPhylacteryContentsSize.get(type));
            if (isFull(stack)) {
                component.withStyle(ChatFormatting.GOLD);
            }
            tooltipComponents.add(component);
        }
    }

    public static int getColor(ItemStack stack) {
        Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount == 0) return -1;
        // TODO 26.1 replace uses of SpawnEggItem
        SpawnEggItem spawnEgg = SpawnEggItem.byId(contents.type);
        return spawnEgg == null ? -1 : spawnEgg.getColor(0);
    }

    public static float getFill(ItemStack stack) {
        Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount == 0) return 0;
        int size = CrystalPhylacteryContentsSize.get(contents.type);
        return size == 0 ? 0 : (float) contents.amount / size;
    }

    public static boolean isFull(ItemStack stack) {
        Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null) return false;
        int size = CrystalPhylacteryContentsSize.get(contents.type);
        return size > 0 && contents.amount >= size;
    }

    public static void addFill(Player player, Entity entity) {
        EntityType<?> type = entity.getType();
        if (!CrystalPhylacteryContentsSize.has(type) && !(entity instanceof Mob)) return;
        int size = CrystalPhylacteryContentsSize.get(type);
        for (ItemStack stack : player.getInventory().items) {
            if (!stack.is(AMItems.CRYSTAL_PHYLACTERY)) continue;
            Contents contents = stack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
            if (contents == null || contents.amount == 0) {
                contents = new Contents(type, 1);
            } else if (contents.type == type && size > contents.amount) {
                contents = new Contents(type, contents.amount + 1);
            } else continue;
            stack.set(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS, contents);
            break;
        }
    }

    public static void addToCreativeTab(Consumer<ItemStack> consumer) {
        for (EntityType<?> type : BuiltInRegistries.ENTITY_TYPE) {
            ItemStack stack = getFilled(type);
            if (!stack.isEmpty()) {
                consumer.accept(stack);
            }
        }
    }

    public static ItemStack getFilled(EntityType<?> type) {
        int size = CrystalPhylacteryContentsSize.get(type);
        if (size <= 0) return ItemStack.EMPTY;
        ItemStack stack = AMItems.CRYSTAL_PHYLACTERY.toStack();
        stack.set(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS, new Contents(type, size));
        return stack;
    }

    public record Contents(EntityType<?> type, int amount) {
        public static final Codec<Contents> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BuiltInRegistries.ENTITY_TYPE.byNameCodec().fieldOf("type").forGetter(Contents::type),
            ExtraCodecs.NON_NEGATIVE_INT.fieldOf("amount").forGetter(Contents::amount)
        ).apply(inst, Contents::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Contents> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.registry(Registries.ENTITY_TYPE), Contents::type,
            ByteBufCodecs.INT, Contents::amount,
            Contents::new);
    }
}
