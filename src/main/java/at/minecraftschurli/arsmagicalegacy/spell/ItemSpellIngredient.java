package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

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
        ItemStack[] items = item.getItems();
        if (items.length == 1) return List.of(items[0].getDisplayName(), Component.translatable(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, count));
        List<Component> components = new ArrayList<>(Arrays.stream(items).map(ItemStack::getDisplayName).toList());
        components.add(Component.translatable(AMTranslations.SPELL_INGREDIENT_COUNT_KEY, count));
        return components;
    }

    @Override
    public boolean canCombine(SpellIngredient other) {
        if (!(other instanceof ItemSpellIngredient that)) return false;
        ItemStack[] thisItems = this.item.getItems();
        ItemStack[] thatItems = that.item.getItems();
        return thisItems.length == thatItems.length && IntStream.range(0, thisItems.length).allMatch(i -> ItemStack.isSameItemSameComponents(thisItems[i], thatItems[i]));
    }

    @Override
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
        return Arrays.stream(item.getItems())
            .map(e -> e.copyWithCount(count))
            .toList();
    }

    private boolean consume(ItemStack stack) {
        IItemHandler handler = stack.getCapability(Capabilities.ItemHandler.ITEM);
        if (handler != null && testItemHandler(handler)) {
            int count = this.count;
            for (int i = 0; i < handler.getSlots(); i++) {
                ItemStack slotStack = handler.getStackInSlot(i).copy();
                if (item.test(slotStack)) {
                    int slotCount = slotStack.getCount();
                    if (count > slotCount) {
                        handler.extractItem(i, slotCount, false);
                        count -= slotCount;
                    } else {
                        handler.extractItem(i, count, false);
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

    private boolean testItemHandler(IItemHandler handler) {
        int count = this.count;
        for (int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if (item.test(stack)) {
                count -= stack.getCount();
            }
            if (count <= 0) return true;
        }
        return count <= 0;
    }
}
