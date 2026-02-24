package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.data.RitualBuilder;
import at.minecraftschurli.arsmagicalegacy.api.data.RitualProvider;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.ritual.IngredientRitualRequirement;
import at.minecraftschurli.arsmagicalegacy.ritual.LearnSkillRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SetBlockRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SpawnEntityRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SpellCastRitualTrigger;
import at.minecraftschurli.arsmagicalegacy.ritual.StructureRitualRequirement;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMRitualProvider extends RitualProvider {
    public AMRitualProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder("purification", new SpellCastRitualTrigger(List.of(AMSpells.SELF.get(), AMSpells.LIGHT.get())))
            .addRequirement(new IngredientRitualRequirement(Ingredient.of(AMItems.MOONSTONE), 4))
            .addRequirement(new StructureRitualRequirement(AMMultiblocks.PURIFICATION, BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.LOWER), BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.CELESTIAL_PRISM.get().defaultBlockState().setValue(CelestialPrismBlock.PART, CelestialPrismBlock.Part.UPPER), BlockPos.ZERO.below(2)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1)));
        builder("corruption", new SpellCastRitualTrigger(List.of(AMSpells.FIRE_DAMAGE.get())))
            .addRequirement(new IngredientRitualRequirement(Ingredient.of(AMItems.SUNSTONE), 4))
            .addRequirement(new StructureRitualRequirement(AMMultiblocks.CORRUPTION, BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(3)))
            .addEffect(new SetBlockRitualEffect(AMBlocks.BLACK_AUREM.get().defaultBlockState(), BlockPos.ZERO.below(2)))
            .addEffect(new SetBlockRitualEffect(Blocks.AIR.defaultBlockState(), BlockPos.ZERO.below(1)));
        HolderLookup.RegistryLookup<Skill> skills = provider.lookupOrThrow(AMRegistries.Keys.SKILL);
        unlock(skills, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
        unlock(skills, AMSpells.DAYLIGHT, AMSpells.DIVINE_INTERVENTION, AMSpells.TRUE_SIGHT, AMSpells.SOLAR);
        unlock(skills, AMSpells.DISMEMBERING, AMSpells.PHYSICAL_DAMAGE, AMSpells.DAMAGE, AMSpells.HEALING, AMSpells.PIERCING);
        unlock(skills, AMSpells.EFFECT_POWER, AMSpells.FLIGHT, AMSpells.FURY, AMSpells.REFLECT, AMSpells.SHRINK, AMSpells.SWIFT_SWIM, AMSpells.TEMPORAL_ANCHOR);
        unlock(skills, AMSpells.FALLING_STAR, AMSpells.ASTRAL_DISTORTION, AMSpells.MAGIC_DAMAGE, AMSpells.GRAVITY);
        unlock(skills, AMSpells.FIRE_RAIN, AMSpells.FIRE_DAMAGE, AMSpells.IGNITION, AMSpells.STORM);
        unlock(skills, AMSpells.HEALTH_BOOST, AMSpells.LIFE_TAP, AMSpells.RESISTANCE);
        unlock(skills, AMSpells.MANA_BLAST, AMSpells.EXPLOSION, AMSpells.MANA_DRAIN);
        unlock(skills, AMSpells.MOONRISE, AMSpells.ENDER_INTERVENTION, AMSpells.NIGHT_VISION, AMSpells.LUNAR);
        unlock(skills, AMSpells.PROSPERITY, AMSpells.DIG, AMSpells.PHYSICAL_DAMAGE, AMSpells.MINING_POWER, AMSpells.SILK_TOUCH);
        builder("unlock_shield_overload", new SpellCastRitualTrigger(List.of(AMSpells.RESISTANCE.get(), AMSpells.MANA_DRAIN.get())))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(AMMagic.SHIELD_OVERLOAD)));
    }

    private RitualBuilder spawn(DeferredHolder<EntityType<?>, EntityType<?>> boss, RitualTrigger<?> trigger, ResourceLocation structure, BlockPos offset) {
        return builder("spawn_" + boss.getId().getPath(), trigger)
            .addRequirement(new StructureRitualRequirement(structure, offset))
            .addEffect(new SpawnEntityRitualEffect(boss.get()));
    }

    @SafeVarargs
    private void unlock(HolderLookup.RegistryLookup<Skill> skills, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        ResourceLocation id = part.getId();
        builder("unlock_" + id.getPath(), new SpellCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(ResourceKey.create(AMRegistries.Keys.SKILL, id))));
    }
}
