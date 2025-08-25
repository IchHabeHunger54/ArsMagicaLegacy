package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellItem extends Item {
    public SpellItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(stack.get(AMDataComponents.SPELL), player, true, true);
        if (result.spell() != null) {
            stack.set(AMDataComponents.SPELL, result.spell());
        }
        if (result.message() != null) {
            player.displayClientMessage(result.message(), true);
        }
        return result.result().isFalse() ? InteractionResultHolder.fail(stack) : InteractionResultHolder.success(stack);
    }
}
