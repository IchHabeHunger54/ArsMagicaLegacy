package at.minecraftschurli.mods.arsmagicalegacy.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.util.context.ContextMap;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@SuppressWarnings("deprecation")
public record ItemSpellIngredient(Ingredient item, int count) implements SpellIngredient {
    public static final MapCodec<ItemSpellIngredient> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Ingredient.CODEC.fieldOf("item").forGetter(ItemSpellIngredient::item),
        Codec.INT.fieldOf("count").forGetter(ItemSpellIngredient::count)
    ).apply(inst, ItemSpellIngredient::new));

    @Override
    public MapCodec<? extends SpellIngredient> codec() {
        return AMSpells.ITEM_SPELL_INGREDIENT.get();
    }

    @Override
    public List<Component> tooltip() {
        return Stream.concat(switch (this.item.display()) {
            case SlotDisplay.TagSlotDisplay(TagKey<Item> tag) ->
                Stream.of(AMUtil.getTagName(tag));
            case SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate stack) -> Stream.of(stack.create().getItemName());
            default -> {
                List<ItemStack> itemStacks = asItemStacks();
                if (itemStacks.size() == 1) {
                    ItemStack item = itemStacks.getFirst();
                    yield Stream.of(item.getItemName());
                }
                yield itemStacks.stream().map(ItemStack::getItemName);
            }
        }, Stream.of(Component.translatable(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, count))).toList();
    }

    @Override
    public boolean canCombine(SpellIngredient other) {
        if (!(other instanceof ItemSpellIngredient that)) return false;
        List<ItemStack> thisItems = this.item.items()
            .map(Holder::value)
            .map(Item::getDefaultInstance)
            .toList();
        List<ItemStack> thatItems = that.item.items()
            .map(Holder::value)
            .map(Item::getDefaultInstance)
            .toList();
        return thisItems.size() == thatItems.size() && IntStream.range(0, thisItems.size()).allMatch(i -> ItemStack.isSameItemSameComponents(thisItems.get(i), thatItems.get(i)));
    }

    @Override
    @Nullable
    public SpellIngredient combine(SpellIngredient other) {
        return canCombine(other) ? new ItemSpellIngredient(item, count + ((ItemSpellIngredient) other).count) : null;
    }

    @Override
    public boolean consume(Level level, BlockPos pos) {
        for (ItemEntity entity : level.getEntities(EntityTypeTest.forClass(ItemEntity.class), new AABB(pos).inflate(1, 1, 1).move(0, -2, 0), e -> true)) {
            if (consume(entity.getItem())) {
                level.playSound(null, pos.getX(), pos.getY() - 2, pos.getZ(), AMSounds.SPELLCRAFTING_ADD_INGREDIENT.get(), SoundSource.BLOCKS, 1, 1);
                return true;
            }
        }
        return false;
    }

    @Override
    public List<ItemStack> asItemStacks() {
        List<ItemStack> list = item.display().resolveForStacks(ContextMap.EMPTY);
        return !list.isEmpty() ? list : item.items()
            .map(Holder::value)
            .map(e -> new ItemStack(e, count))
            .toList();
    }

    private boolean consume(ItemStack stack) {
        ResourceHandler<ItemResource> handler = stack.getCapability(Capabilities.Item.ITEM, ItemAccess.forStack(stack));
        if (handler != null) {
            try (Transaction transaction = Transaction.openRoot()) {
                int count = this.count;
                for (int i = 0; i < handler.size(); i++) {
                    ItemResource resource = handler.getResource(i);
                    if (!item.test(resource.toStack())) continue;
                    count -= handler.extract(resource, count, transaction);
                    if (count <= 0) {
                        transaction.commit();
                        return true;
                    }
                }
            }
        }
        if (item.test(stack) && stack.getCount() >= count) {
            stack.shrink(count);
            return true;
        }
        return false;
    }
}
