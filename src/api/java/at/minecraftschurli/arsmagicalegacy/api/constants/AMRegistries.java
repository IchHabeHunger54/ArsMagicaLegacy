package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
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
     * The registry for {@link SpellIngredient}s.
     */
    Registry<MapCodec<? extends RitualEffect>> RITUAL_EFFECTS = new RegistryBuilder<>(Keys.RITUAL_EFFECT).sync(true).create();
    /**
     * The registry for {@link SpellIngredient}s.
     */
    Registry<MapCodec<? extends RitualRequirement>> RITUAL_REQUIREMENTS = new RegistryBuilder<>(Keys.RITUAL_REQUIREMENT).sync(true).create();
    /**
     * The registry for {@link SpellIngredient}s.
     */
    Registry<MapCodec<? extends RitualTrigger>> RITUAL_TRIGGERS = new RegistryBuilder<>(Keys.RITUAL_TRIGGER).sync(true).create();
    /**
     * The registry for {@link SpellIngredient}s.
     */
    Registry<MapCodec<? extends SpellIngredient>> SPELL_INGREDIENTS = new RegistryBuilder<>(Keys.SPELL_INGREDIENT).sync(true).create();
    /**
     * The registry for {@link SpellPart}s.
     */
    Registry<SpellPart> SPELL_PARTS = new RegistryBuilder<>(Keys.SPELL_PART).sync(true).create();

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Ability}s.
     */
    static Registry<Ability> abilities(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.ABILITY);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link Ability}s.
     */
    static Registry<Ability> abilities(boolean client) {
        return abilities(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Affinity}s.
     */
    static Registry<Affinity> affinities(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.AFFINITY);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link Affinity}s.
     */
    static Registry<Affinity> affinities(boolean client) {
        return affinities(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link AltarCapMaterial}s.
     */
    static Registry<AltarCapMaterial> altarCapMaterials(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.ALTAR_CAP_MATERIAL);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link AltarCapMaterial}s.
     */
    static Registry<AltarCapMaterial> altarCapMaterials(boolean client) {
        return altarCapMaterials(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link AltarMaterial}s.
     */
    static Registry<AltarMaterial> altarMaterials(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.ALTAR_MATERIAL);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link AltarMaterial}s.
     */
    static Registry<AltarMaterial> altarMaterials(boolean client) {
        return altarMaterials(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link EtheriumType}s.
     */
    static Registry<EtheriumType> etheriumTypes(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.ETHERIUM_TYPE);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link EtheriumType}s.
     */
    static Registry<EtheriumType> etheriumTypes(boolean client) {
        return etheriumTypes(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link OcculusTab}s.
     */
    static Registry<OcculusTab> occulusTabs(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.OCCULUS_TAB);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link OcculusTab}s.
     */
    static Registry<OcculusTab> occulusTabs(boolean client) {
        return occulusTabs(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Skill}s.
     */
    static Registry<Skill> skills(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.SKILL);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link Skill}s.
     */
    static Registry<Skill> skills(boolean client) {
        return skills(registryAccess(client));
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link SkillPoint}s.
     */
    static Registry<SkillPoint> skillPoints(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(Keys.SKILL_POINT);
    }

    /**
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The registry for {@link SkillPoint}s.
     */
    static Registry<SkillPoint> skillPoints(boolean client) {
        return skillPoints(registryAccess(client));
    }

    /**
     * Returns the correct {@link RegistryAccess} for the current side.
     * Note that during scenarios such as world loading, this may be unreliable, use more reliable sources there, e.g. {@link Level#registryAccess()}.
     *
     * @param client True if this is called from a client context, false if this is called from a server context.
     * @return The correct {@link RegistryAccess} for the current side.
     */
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
        ResourceKey<Registry<MapCodec<? extends RitualTrigger>>>     RITUAL_TRIGGER     = createKey("ritual_trigger");
        ResourceKey<Registry<MapCodec<? extends SpellIngredient>>>   SPELL_INGREDIENT   = createKey("spell_ingredient");
        ResourceKey<Registry<SpellPart>>                             SPELL_PART         = createKey("spell_part");
        // Datapack registries
        ResourceKey<Registry<Ability>>          ABILITY            = createKey("ability");
        ResourceKey<Registry<Affinity>>         AFFINITY           = createKey("affinity");
        ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = createKey("altar_cap_material");
        ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = createKey("altar_material");
        ResourceKey<Registry<EtheriumType>>     ETHERIUM_TYPE      = createKey("etherium_type");
        ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = createKey("occulus_tab");
        ResourceKey<Registry<Skill>>            SKILL              = createKey("skill");
        ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = createKey("skill_point");
        // @formatter:on

        private static <T> ResourceKey<Registry<T>> createKey(String path) {
            return ResourceKey.createRegistryKey(ArsMagicaApi.id(path));
        }
    }
}
