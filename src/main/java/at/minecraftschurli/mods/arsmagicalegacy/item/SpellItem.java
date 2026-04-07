package at.minecraftschurli.mods.arsmagicalegacy.item;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class SpellItem extends Item {
    public SpellItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        MutableSpellFacade spell = stack.getCapability(AMCapabilities.SPELL);
        if (spell == null) return InteractionResult.FAIL;
        if (spell.name().isEmpty() || spell.icon().isEmpty()) {
            if (level.isClientSide()) {
                AMClientUtil.setSpellCustomizationScreen(spell, usedHand);
            }
            return InteractionResult.CONSUME.heldItemTransformedTo(stack);
        }
        if (spell.isContinuous()) {
            player.startUsingItem(usedHand);
            return InteractionResult.CONSUME.heldItemTransformedTo(stack);
        }
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, player, true, true);
        if (result.isSuccess()) {
            onSuccess(level, player, spell, result.getSpell());
            return InteractionResult.SUCCESS.heldItemTransformedTo(stack);
        } else {
            onFailure(player, result.getMessage());
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
        MutableSpellFacade spell = stack.getCapability(AMCapabilities.SPELL);
        if (spell == null || !spell.isContinuous()) return;
        SpellCastResult result = ArsMagicaApi.spellHelper().cast(spell, level, livingEntity, true, true);
        if (result.isSuccess()) {
            onSuccess(level, livingEntity, spell, result.getSpell());
        } else if (livingEntity instanceof Player player) {
            onFailure(player, result.getMessage());
        }
    }

    @Override
    public Component getName(ItemStack stack) {
        MutableSpellFacade spell = stack.getCapability(AMCapabilities.SPELL);
        if (spell == null) return super.getName(stack);
        return spell
            .name()
            .map(e -> e.getString().isEmpty() ? null : e)
            .orElse(super.getName(stack));
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 72000;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag tooltipFlag) {
        super.appendHoverText(stack, context, display, builder, tooltipFlag);
        MutableSpellFacade spell = stack.getCapability(AMCapabilities.SPELL);
        builder.accept(spell == null || spell.isMalformed() ? AMTranslations.SPELL_INVALID : Component.translatable(AMTranslations.SPELL_MANA_COST_KEY, spell.getManaCost(context.registries())));
    }

    @Override
    public boolean canDestroyBlock(ItemStack itemStack, BlockState state, Level level, BlockPos pos, LivingEntity user) {
        return false;
    }

    @Override
    public boolean isFoil(ItemStack stack) {
        return false;
    }

    private void onSuccess(Level level, LivingEntity entity, MutableSpellFacade spell, SpellFacade data) {
        spell.setFrom(data);
        Affinity affinity = AMRegistries.affinities(level.registryAccess()).getValue(spell.grammar().primaryAffinity(level.registryAccess()));
        if (affinity == null) return;
        Optional<Holder<SoundEvent>> optional = spell.isContinuous() ? affinity.loopSound() : affinity.castSound();
        optional.ifPresent(sound -> level.playSeededSound(null, entity, sound, SoundSource.PLAYERS, 1f, 1f, level.getRandom().nextLong()));
    }

    private void onFailure(Player player, @Nullable Component message) {
        if (message != null) {
            player.sendOverlayMessage(message);
        }
    }

    public static @Nullable MutableSpellFacade getSpellCap(ItemStack stack, @Nullable Void ignoredUnused) {
        if (!(stack.getItem() instanceof SpellItem)) return null;
        return new MutableSpellFacade() {
            @Override
            public Spell spell() {
                return stack.getOrDefault(AMDataComponents.SPELL, Spell.EMPTY);
            }

            @Override
            public Optional<Component> name() {
                return Optional.ofNullable(stack.get(AMDataComponents.SPELL_NAME));
            }

            @Override
            public void setName(@Nullable Component name) {
                stack.set(AMDataComponents.SPELL_NAME, name);
            }

            @Override
            public void clearName() {
                stack.remove(AMDataComponents.SPELL_NAME);
            }

            @Override
            public Optional<Identifier> icon() {
                return Optional.ofNullable(stack.get(AMDataComponents.SPELL_ICON));
            }

            @Override
            public void setIcon(@Nullable Identifier icon) {
                stack.set(AMDataComponents.SPELL_ICON, icon);
            }

            @Override
            public void clearIcon() {
                stack.remove(AMDataComponents.SPELL_ICON);
            }

            @Override
            public SpellDataComponentMap spellData() {
                return stack.getOrDefault(AMDataComponents.SPELL_DATA, SpellDataComponentMap.EMPTY);
            }

            @Override
            public void updateSpellData(UnaryOperator<SpellDataComponentMap> update) {
                stack.update(AMDataComponents.SPELL_DATA, SpellDataComponentMap.EMPTY, update);
            }

            @Override
            public void setSpellData(SpellDataComponentMap data) {
                stack.set(AMDataComponents.SPELL_DATA, data);
            }

            @Override
            public byte activeShapeGroup() {
                return stack.getOrDefault(AMDataComponents.SPELL_SHAPE_GROUP, (byte) 0);
            }

            @Override
            public void setActiveShapeGroup(byte activeShapeGroup) {
                stack.set(AMDataComponents.SPELL_SHAPE_GROUP, activeShapeGroup);
            }

            @Override
            public void setFrom(SpellFacade spell) {
                setName(spell.name().orElse(null));
                setIcon(spell.icon().orElse(null));
                setSpellData(spell.spellData());
                setActiveShapeGroup(spell.activeShapeGroup());
            }
        };
    }
}
