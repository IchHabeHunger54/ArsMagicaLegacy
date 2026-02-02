package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.event.BurnoutCostCalculationEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.ManaCostCalculationEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellCastEvent;
import at.minecraftschurli.arsmagicalegacy.api.event.SpellPartCastEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMMobEffects;
import at.minecraftschurli.arsmagicalegacy.spell.ItemSpellIngredient;
import at.minecraftschurli.arsmagicalegacy.spell.SpellPartDataManager;
import at.minecraftschurli.arsmagicalegacy.spell.ToolTiers;
import at.minecraftschurli.arsmagicalegacy.spell.data.SpellDamage;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.NeoForge;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

final class SpellHelperImpl implements SpellHelper {
    @Override
    public Either<Spell, Component> cast(Spell spell, Level level, @Nullable LivingEntity caster, boolean consume, boolean awardXp) {
        if (spell.isMalformed()) return Either.right(AMTranslations.SPELL_CAST_MALFORMED);
        if (caster != null && caster.hasEffect(AMMobEffects.SILENCE)) return Either.right(AMTranslations.SPELL_CAST_SILENCED);
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        double manaCost = caster != null && caster.hasEffect(AMMobEffects.CLARITY) ? 0 : NeoForge.EVENT_BUS.post(new ManaCostCalculationEvent(caster, spell, spell.getManaCost(), burnoutHelper.getBurnout(caster))).getResult();
        double burnoutCost = caster != null && caster.hasEffect(AMMobEffects.CLARITY) ? 0 : NeoForge.EVENT_BUS.post(new BurnoutCostCalculationEvent(caster, spell, spell.grammar().getBurnoutCost())).getBurnout();
        if (caster != null) {
            caster.removeEffect(AMMobEffects.CLARITY);
        }
        SpellCastEvent.Pre event = new SpellCastEvent.Pre(caster, spell, manaCost, burnoutCost, consume, awardXp);
        if (event.isCanceled()) return Either.right(event.getCancellationMessage());
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            if (manaHelper.getMana(caster) < manaCost) return Either.right(AMTranslations.SPELL_CAST_NOT_ENOUGH_MANA);
            if (burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) < burnoutCost) return Either.right(AMTranslations.SPELL_CAST_BURNED_OUT);
        }
        spell = castPrimary(spell, level, caster);
        if (event.isConsume() && !(caster instanceof Player player && player.isCreative())) {
            manaHelper.decreaseMana(caster, manaCost);
            burnoutHelper.increaseBurnout(caster, burnoutCost);
        }
        if (event.isAwardXp() && caster instanceof Player player) {
            MagicHelper helper = ArsMagicaApi.magicHelper();
            Registry<Skill> registry = AMRegistries.skills(player.registryAccess());
            boolean affinityGains = registry.containsKey(AMMagic.AFFINITY_GAINS_BOOST) && helper.knows(player, registry.getHolderOrThrow(AMMagic.AFFINITY_GAINS_BOOST));
            boolean continuous = spell.isContinuous();
            Map<Holder<Affinity>, Double> affinityShifts = spell.grammar().affinityShifts();
            if (continuous) {
                affinityShifts.replaceAll((k, v) -> v * AMServerConfig.CONTINUOUS_MODIFIER.get());
            }
            if (affinityGains) {
                affinityShifts.replaceAll((k, v) -> v * AMServerConfig.AFFINITY_GAINS_MODIFIER.get());
            }
            helper.applyAffinityShift(player, affinityShifts);
            double xp = AMServerConfig.AFFINITY_TO_XP_RATIO.get() * affinityShifts.size();
            if (continuous) {
                xp *= AMServerConfig.CONTINUOUS_MODIFIER.get();
            }
            if (affinityGains) {
                xp *= AMServerConfig.AFFINITY_GAINS_XP_MODIFIER.get();
            }
            helper.addXp(player, xp);
        }
        NeoForge.EVENT_BUS.post(new SpellCastEvent.Post(caster, spell, manaCost, burnoutCost, event.isConsume(), event.isAwardXp()));
        return Either.left(spell);
    }

    @Override
    public Spell castPrimary(Spell spell, Level level, @Nullable LivingEntity caster) {
        PrimarySpellShape primary = spell.currentShapeGroup().primaryShape();
        List<SpellModifier> modifiers = spell.currentShapeGroup().primaryModifiers();
        if (primary != null) {
            spell = primary.cast(spell, modifiers, level, caster);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.PrimaryShape(caster, spell, primary, modifiers));
        }
        return spell;
    }

    @Override
    public Spell castSecondary(Spell spell, Level level, @Nullable LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        SecondarySpellShape secondary = spell.currentShapeGroup().secondaryShape();
        List<SpellModifier> modifiers = spell.currentShapeGroup().secondaryModifiers();
        if (secondary != null) {
            spell = secondary.cast(spell, modifiers, level, caster, directEntity, hitResult);
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.SecondaryShape(caster, spell, secondary, modifiers, directEntity));
        }
        return spell;
    }

    @Override
    public Spell castGrammar(Spell spell, Level level, @Nullable LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        for (Pair<SpellComponent, List<SpellModifier>> pair : spell.grammar().components()) {
            SpellComponent component = pair.getFirst();
            List<SpellModifier> modifiers = pair.getSecond();
            spell = component.cast(spell, modifiers, level, caster, directEntity, hitResult);
            if (level.isClientSide()) {
                component.spawnParticles(spell, modifiers, level, caster, directEntity, hitResult);
            }
            NeoForge.EVENT_BUS.post(new SpellPartCastEvent.Component(caster, spell, component, modifiers, directEntity, hitResult));
        }
        SpellDamage damage = spell.dataComponents().grammar().get(AMDataComponents.SPELL_DAMAGE.get());
        if (damage != null) {
            damage.apply(level, caster, directEntity);
            spell = spell.updateDataComponents(components -> components.updateGrammar(grammar -> grammar.remove(AMDataComponents.SPELL_DAMAGE.get())));
        }
        return spell;
    }

    @Override
    public Spell castSecondaryOrGrammar(Spell spell, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        return spell.currentShapeGroup().secondaryShape() != null ? castSecondary(spell, level, caster, directEntity, hitResult) : castGrammar(spell, level, caster, directEntity, hitResult);
    }

    @Override
    public SpellPartData getData(SpellPart part) {
        return SpellPartDataManager.INSTANCE.getOrDefault(ArsMagicaApi.spellPartRegistry().getKey(part), SpellPartData.DEFAULT);
    }

    @Override
    public double getModifiedStat(double base, SpellStat stat, List<SpellModifier> modifiers, Spell spell, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        double modified = base;
        for (SpellModifier modifier : modifiers) {
            if (modifier.getStats().contains(stat)) {
                modified = modifier.getModifier(stat).modify(base, modified, spell, level, caster, directEntity, hitResult);
            }
        }
        return modified;
    }

    @Override
    public int getColor(List<SpellModifier> modifiers, Spell spell, int shapeGroupIndex) {
        return spell.dataComponents().get(shapeGroupIndex).getOrDefault(AMDataComponents.SPELL_COLOR.get(), -1);
    }

    @Override
    public List<SpellModifier> getModifiers(SpellPart part) {
        if (part.isModifier()) return List.of();
        Set<SpellStat> stats = part.getStats();
        return ArsMagicaApi.spellPartRegistry()
            .stream()
            .filter(SpellPart::isModifier)
            .filter(p -> !Sets.intersection(stats, p.getStats()).isEmpty())
            .map(p -> (SpellModifier) p)
            .toList();
    }

    @Override
    public TagKey<Block> getIncorrectTagForToolTier(int toolTier) {
        return ToolTiers.INSTANCE.get(toolTier);
    }

    @Override
    public double getManaToBurnoutRatio() {
        return AMServerConfig.MANA_TO_BURNOUT_RATIO.get();
    }

    @Override
    public List<SpellIngredient> getRecipe(Spell spell) {
        List<SpellIngredient> list = new ArrayList<>();
        list.add(new ItemSpellIngredient(Ingredient.of(AMTags.Items.SPELLCRAFTING_START), 1));
        spell.shapeGroups()
            .stream()
            .map(SpellShapeGroup::parts)
            .flatMap(List::stream)
            .map(SpellPart::getData)
            .map(SpellPartData::recipe)
            .forEach(list::addAll);
        spell.grammar()
            .parts()
            .stream()
            .map(SpellPart::getData)
            .map(SpellPartData::recipe)
            .forEach(list::addAll);
        list.add(new ItemSpellIngredient(Ingredient.of(AMTags.Items.SPELLCRAFTING_END), 1));
        return list;
    }

    @Override
    public List<SpellIngredient> getFlatRecipe(Spell spell) {
        List<SpellIngredient> result = new ArrayList<>();
        for (SpellIngredient ingredient : getRecipe(spell)) {
            Optional<SpellIngredient> optional = result.stream().filter(e -> e.canCombine(ingredient)).findAny();
            if (optional.isPresent()) {
                SpellIngredient previous = optional.get();
                int index = result.indexOf(previous);
                result.remove(previous);
                result.add(index, ingredient.combine(previous));
            } else {
                result.add(ingredient);
            }
        }
        return result;
    }

    @Override
    public void spawnParticles(ResourceLocation part, Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, HitResult hitResult) {
        if (!level.isClientSide()) return;
        AMClientUtil.spawnParticles(part, switch (hitResult) {
            case BlockHitResult blockHitResult -> blockHitResult.getBlockPos().getBottomCenter();
            case EntityHitResult entityHitResult -> hitResult.getLocation().add(0, entityHitResult.getEntity().getEyeHeight(), 0);
            default -> hitResult.getLocation();
        }, getColor(modifiers, spell, -1), caster, directEntity, hitResult);
    }
}
