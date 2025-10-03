package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.datafixers.util.Either;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class SpellItem extends DataComponentNamedItem<Spell> {
    public SpellItem(Properties properties) {
        super(properties, AMDataComponents.SPELL.get());
        withNameGetter((spell, name) -> spell.name().map(e -> e.getString().isEmpty() ? null : e).orElse(name));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        Spell spell = stack.get(AMDataComponents.SPELL);
        if (spell == null) return InteractionResultHolder.fail(stack);
        if (spell.name().isEmpty() || spell.icon().isEmpty()) {
            if (level.isClientSide()) {
                AMClientUtil.setSpellCustomizationScreen(spell);
            }
            return InteractionResultHolder.consume(stack);
        }
        Either<Spell, Component> either = ArsMagicaApi.spellHelper().cast(spell, player, true, true)
            .ifLeft(result -> stack.set(AMDataComponents.SPELL, result))
            .ifRight(message -> player.displayClientMessage(message, true));
        return either.left().isPresent() ? InteractionResultHolder.success(stack) : InteractionResultHolder.fail(stack);
    }
}
