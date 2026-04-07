package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPrefab;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.registries.RegistryBuilder;

/**
 * Holds all registries added by the mod, including getters for the datapack registries.
 */
public interface AMRegistries {
    /**
     * The registry for {@link AbilityEffect}s.
     */
    Registry<MapCodec<? extends AbilityEffect>> ABILITY_EFFECTS = new RegistryBuilder<>(Keys.ABILITY_EFFECT).sync(true).create();
    /**
     * The registry for {@link GrowthType}s.
     */
    Registry<MapCodec<? extends GrowthType>> GROWTH_TYPES = new RegistryBuilder<>(Keys.GROWTH_TYPE).sync(true).create();
    /**
     * The registry for {@link RitualEffect}s.
     */
    Registry<MapCodec<? extends RitualEffect>> RITUAL_EFFECTS = new RegistryBuilder<>(Keys.RITUAL_EFFECT).sync(true).create();
    /**
     * The registry for {@link RitualRequirement}s.
     */
    Registry<MapCodec<? extends RitualRequirement>> RITUAL_REQUIREMENTS = new RegistryBuilder<>(Keys.RITUAL_REQUIREMENT).sync(true).create();
    /**
     * The registry for {@link RitualTrigger}s.
     */
    Registry<MapCodec<? extends RitualTrigger<?>>> RITUAL_TRIGGERS = new RegistryBuilder<>(Keys.RITUAL_TRIGGER).sync(true).create();
    /**
     * The registry for {@link SpellIngredient}s.
     */
    Registry<MapCodec<? extends SpellIngredient>> SPELL_INGREDIENTS = new RegistryBuilder<>(Keys.SPELL_INGREDIENT).sync(true).create();
    /**
     * The registry for {@link SpellPart}s.
     */
    Registry<SpellPart> SPELL_PARTS = new RegistryBuilder<>(Keys.SPELL_PART).sync(true).create();

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Ability]s.
    static HolderLookup.RegistryLookup<Ability> abilities(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.ABILITY);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Ability]s.
    static Registry<Ability> abilities(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ABILITY);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Affinity]s.
    static HolderLookup.RegistryLookup<Affinity> affinities(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.AFFINITY);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Affinity]s.
    static Registry<Affinity> affinities(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.AFFINITY);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [AltarCapMaterial]s.
    static HolderLookup.RegistryLookup<AltarCapMaterial> altarCapMaterials(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.ALTAR_CAP_MATERIAL);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [AltarCapMaterial]s.
    static Registry<AltarCapMaterial> altarCapMaterials(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ALTAR_CAP_MATERIAL);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [AltarMaterial]s.
    static HolderLookup.RegistryLookup<AltarMaterial> altarMaterials(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.ALTAR_MATERIAL);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [AltarMaterial]s.
    static Registry<AltarMaterial> altarMaterials(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ALTAR_MATERIAL);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [EtheriumType]s.
    static HolderLookup.RegistryLookup<EtheriumType> etheriumTypes(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.ETHERIUM_TYPE);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [EtheriumType]s.
    static Registry<EtheriumType> etheriumTypes(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.ETHERIUM_TYPE);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [OcculusTab]s.
    static HolderLookup.RegistryLookup<OcculusTab> occulusTabs(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.OCCULUS_TAB);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [OcculusTab]s.
    static Registry<OcculusTab> occulusTabs(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.OCCULUS_TAB);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Plant]s.
    static HolderLookup.RegistryLookup<Plant> plants(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.PLANT);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Plant]s.
    static Registry<Plant> plants(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.PLANT);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Ritual]s.
    static HolderLookup.RegistryLookup<Ritual<?>> rituals(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.RITUAL);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Ritual]s.
    static Registry<Ritual<?>> rituals(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.RITUAL);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Skill]s.
    static HolderLookup.RegistryLookup<Skill> skills(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.SKILL);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Skill]s.
    static Registry<Skill> skills(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SKILL);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [SkillPoint]s.
    static HolderLookup.RegistryLookup<SkillPoint> skillPoints(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.SKILL_POINT);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [SkillPoint]s.
    static Registry<SkillPoint> skillPoints(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SKILL_POINT);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [SpellPartData]s.
    static HolderLookup.RegistryLookup<SpellPartData> spellPartData(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.SPELL_PART_DATA);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [SpellPartData]s.
    static Registry<SpellPartData> spellPartData(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SPELL_PART_DATA);
    }

    /// @param registries The [HolderLookup.Provider] to use.
    /// @return The registry for [Spell] prefabs.
    static HolderLookup.RegistryLookup<SpellPrefab> spellPrefabs(HolderLookup.Provider registries) {
        return registries.lookupOrThrow(Keys.SPELL_PREFAB);
    }

    /// @param registryAccess The [RegistryAccess] to use.
    /// @return The registry for [Spell] prefabs.
    static HolderLookup.RegistryLookup<SpellPrefab> spellPrefabs(RegistryAccess registryAccess) {
        return registryAccess.lookupOrThrow(Keys.SPELL_PREFAB);
    }

    /// Returns the correct [RegistryAccess] for the current side.
    /// Note that during scenarios such as world loading, this may be unreliable, use more reliable sources there, e.g. [Level#registryAccess()].
    ///
    /// @param client True if this is called from a client context, false if this is called from a server context.
    /// @return The correct [RegistryAccess] for the current side.
    static RegistryAccess registryAccess(boolean client) {
        return client ? ClientRegistryAccess.get() : ServerRegistryAccess.get();
    }

    /**
     * Holds all registry keys used by the mod.
     */
    interface Keys {
        // @formatter:off
        // Static registries
        ResourceKey<Registry<MapCodec<? extends AbilityEffect>>>     ABILITY_EFFECT     = createKey("ability_effect");
        ResourceKey<Registry<MapCodec<? extends GrowthType>>>        GROWTH_TYPE        = createKey("growth_type");
        ResourceKey<Registry<MapCodec<? extends RitualEffect>>>      RITUAL_EFFECT      = createKey("ritual_effect");
        ResourceKey<Registry<MapCodec<? extends RitualRequirement>>> RITUAL_REQUIREMENT = createKey("ritual_requirement");
        ResourceKey<Registry<MapCodec<? extends RitualTrigger<?>>>>  RITUAL_TRIGGER     = createKey("ritual_trigger");
        ResourceKey<Registry<MapCodec<? extends SpellIngredient>>>   SPELL_INGREDIENT   = createKey("spell_ingredient");
        ResourceKey<Registry<SpellPart>>                             SPELL_PART         = createKey("spell_part");
        // Datapack registries
        ResourceKey<Registry<Ability>>          ABILITY            = createKey("ability");
        ResourceKey<Registry<Affinity>>         AFFINITY           = createKey("affinity");
        ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = createKey("altar_cap_material");
        ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = createKey("altar_material");
        ResourceKey<Registry<EtheriumType>>     ETHERIUM_TYPE      = createKey("etherium_type");
        ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = createKey("occulus_tab");
        ResourceKey<Registry<Plant>>            PLANT              = createKey("plant");
        ResourceKey<Registry<Ritual<?>>>        RITUAL             = createKey("ritual");
        ResourceKey<Registry<Skill>>            SKILL              = createKey("skill");
        ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = createKey("skill_point");
        ResourceKey<Registry<SpellPartData>>    SPELL_PART_DATA    = createKey("spell_part_data");
        ResourceKey<Registry<SpellPrefab>>      SPELL_PREFAB       = createKey("spell_prefab");
        // @formatter:on

        private static <T> ResourceKey<Registry<T>> createKey(String path) {
            return ResourceKey.createRegistryKey(ArsMagicaApi.id(path));
        }
    }
}
