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
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

/**
 * Holds the registry keys of all registries added by Ars Magica: Legacy.
 * Also includes getters for the datapack registries. For static registries, see the methods in {@link ArsMagicaApi}.
 */
public interface AMRegistries {
    // @formatter:off
    // Static registries
    ResourceKey<Registry<MapCodec<? extends AbilityEffect>>>   ABILITY_EFFECT   = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability_effect"));
    ResourceKey<Registry<MapCodec<? extends SpellIngredient>>> SPELL_INGREDIENT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_ingredient"));
    ResourceKey<Registry<SpellPart>>                           SPELL_PART       = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_part"));
    // Datapack registries
    ResourceKey<Registry<Ability>>          ABILITY            = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability"));
    ResourceKey<Registry<Affinity>>         AFFINITY           = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("affinity"));
    ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_cap_material"));
    ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_material"));
    ResourceKey<Registry<EtheriumType>>     ETHERIUM_TYPE      = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("etherium_type"));
    ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("occulus_tab"));
    ResourceKey<Registry<Skill>>            SKILL              = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill"));
    ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill_point"));
    // @formatter:on

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Ability}s.
     */
    static Registry<Ability> abilities(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(ABILITY);
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
        return registryAccess.registryOrThrow(AFFINITY);
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
        return registryAccess.registryOrThrow(ALTAR_CAP_MATERIAL);
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
        return registryAccess.registryOrThrow(ALTAR_MATERIAL);
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
        return registryAccess.registryOrThrow(ETHERIUM_TYPE);
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
        return registryAccess.registryOrThrow(OCCULUS_TAB);
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
        return registryAccess.registryOrThrow(SKILL);
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
        return registryAccess.registryOrThrow(SKILL_POINT);
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
}
