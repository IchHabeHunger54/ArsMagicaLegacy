package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

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
                AMClientUtil.setSpellCustomizationScreen(spell, usedHand);
            }
            return InteractionResultHolder.consume(stack);
        }
        if (spell.isContinuous()) {
            player.startUsingItem(usedHand);
            return InteractionResultHolder.consume(stack);
        }
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, player, true, true);
        if (result.isSuccess()) {
            onSuccess(level, player, stack, result.getSpell());
            return InteractionResultHolder.success(stack);
        } else {
            onFailure(player, result.getMessage());
            return InteractionResultHolder.fail(stack);
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        Spell spell = stack.get(AMDataComponents.SPELL);
        if (spell == null || !spell.isContinuous()) return;
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, livingEntity, true, true);
        if (result.isSuccess()) {
            onSuccess(level, livingEntity, stack, result.getSpell());
        } else if (livingEntity instanceof Player player) {
            onFailure(player, result.getMessage());
        }
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        Spell spell = stack.get(AMDataComponents.SPELL);
        tooltipComponents.add(spell == null || spell.isMalformed() ? AMTranslations.SPELL_INVALID : Component.translatable(AMTranslations.SPELL_MANA_COST_KEY, spell.getManaCost()));
    }

    @Override
    public boolean canAttackBlock(BlockState state, Level level, BlockPos pos, Player player) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    private void onSuccess(Level level, LivingEntity entity, ItemStack stack, Spell spell) {
        stack.set(AMDataComponents.SPELL, spell);
        Affinity affinity = AMRegistries.affinities(level.registryAccess()).get(spell.grammar().primaryAffinity());
        if (affinity == null) return;
        Optional<Holder<SoundEvent>> optional = spell.isContinuous() ? affinity.loopSound() : affinity.castSound();
        optional.ifPresent(sound -> level.playSeededSound(null, entity, sound, SoundSource.PLAYERS, 1f, 1f, level.getRandom().nextLong()));
    }

    private void onFailure(Player player, @Nullable Component message) {
        if (message != null) {
            player.displayClientMessage(message, true);
        }
    }
}
