package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHoldingItem;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellItem extends Item implements SpellHoldingItem {
    public SpellItem(Properties properties) {
        super(properties);
    }

    @Override
    public Spell getActiveSpell(ItemStack stack) {
        return stack.get(AMDataComponents.SPELL);
    }

    @Override
    public void setActiveSpell(ItemStack stack, Spell spell) {
        stack.set(AMDataComponents.SPELL, spell);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        SpellCastResult result = ArsMagicaApi.getSpellHelper().castPrimary(getActiveSpell(stack), player);
        setActiveSpell(stack, result.spell());
        return result.result().isFalse() ? InteractionResultHolder.fail(stack) : InteractionResultHolder.success(stack);
    }
}
