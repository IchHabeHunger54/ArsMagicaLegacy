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
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * Holds the registry keys of all registries added by Ars Magica: Legacy.
 * Also includes getters for the datapack registries. For static registries, see the methods in {@link ArsMagicaApi}.
 */
@SuppressWarnings("DataFlowIssue")
public final class AMRegistries {
    private AMRegistries() {
    }

    // @formatter:off
    // Static registries
    public static final ResourceKey<Registry<MapCodec<? extends AbilityEffect>>>   ABILITY_EFFECT   = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability_effect"));
    public static final ResourceKey<Registry<MapCodec<? extends SpellIngredient>>> SPELL_INGREDIENT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_ingredient"));
    public static final ResourceKey<Registry<SpellPart>>                           SPELL_PART       = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_part"));
    // Datapack registries
    public static final ResourceKey<Registry<Ability>>          ABILITY            = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("ability"));
    public static final ResourceKey<Registry<Affinity>>         AFFINITY           = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("affinity"));
    public static final ResourceKey<Registry<AltarCapMaterial>> ALTAR_CAP_MATERIAL = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_cap_material"));
    public static final ResourceKey<Registry<AltarMaterial>>    ALTAR_MATERIAL     = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("altar_material"));
    public static final ResourceKey<Registry<EtheriumType>>     ETHERIUM_TYPE      = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("etherium_type"));
    public static final ResourceKey<Registry<OcculusTab>>       OCCULUS_TAB        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("occulus_tab"));
    public static final ResourceKey<Registry<Skill>>            SKILL              = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill"));
    public static final ResourceKey<Registry<SkillPoint>>       SKILL_POINT        = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("skill_point"));
    // @formatter:on

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Ability}s.
     */
    public static Registry<Ability> abilities(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(ABILITY);
    }

    /**
     * @return The registry for {@link Ability}s.
     */
    public static Registry<Ability> abilities() {
        return abilities(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Affinity}s.
     */
    public static Registry<Affinity> affinities(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(AFFINITY);
    }

    /**
     * @return The registry for {@link Affinity}s.
     */
    public static Registry<Affinity> affinities() {
        return affinities(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link AltarCapMaterial}s.
     */
    public static Registry<AltarCapMaterial> altarCapMaterials(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(ALTAR_CAP_MATERIAL);
    }

    /**
     * @return The registry for {@link AltarCapMaterial}s.
     */
    public static Registry<AltarCapMaterial> altarCapMaterials() {
        return altarCapMaterials(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link AltarMaterial}s.
     */
    public static Registry<AltarMaterial> altarMaterials(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(ALTAR_MATERIAL);
    }

    /**
     * @return The registry for {@link AltarMaterial}s.
     */
    public static Registry<AltarMaterial> altarMaterials() {
        return altarMaterials(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link EtheriumType}s.
     */
    public static Registry<EtheriumType> etheriumTypes(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(ETHERIUM_TYPE);
    }

    /**
     * @return The registry for {@link EtheriumType}s.
     */
    public static Registry<EtheriumType> etheriumTypes() {
        return etheriumTypes(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link OcculusTab}s.
     */
    public static Registry<OcculusTab> occulusTabs(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(OCCULUS_TAB);
    }

    /**
     * @return The registry for {@link OcculusTab}s.
     */
    public static Registry<OcculusTab> occulusTabs() {
        return occulusTabs(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link Skill}s.
     */
    public static Registry<Skill> skills(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(SKILL);
    }

    /**
     * @return The registry for {@link Skill}s.
     */
    public static Registry<Skill> skills() {
        return skills(registryAccess());
    }

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The registry for {@link SkillPoint}s.
     */
    public static Registry<SkillPoint> skillPoints(RegistryAccess registryAccess) {
        return registryAccess.registryOrThrow(SKILL_POINT);
    }

    /**
     * @return The registry for {@link SkillPoint}s.
     */
    public static Registry<SkillPoint> skillPoints() {
        return skillPoints(registryAccess());
    }

    /**
     * Returns the correct {@link RegistryAccess} for the current side.
     * Note that during scenarios such as world loading, this may be unreliable, use more reliable sources there, e.g. {@link Level#registryAccess()}.
     *
     * @return The correct {@link RegistryAccess} for the current side.
     */
    public static RegistryAccess registryAccess() {
        return FMLEnvironment.dist.isClient() ? ClientRegistryAccess.get() : ServerRegistryAccess.get();
    }

    private static class ClientRegistryAccess {
        private static RegistryAccess get() {
            return Minecraft.getInstance().getConnection().registryAccess();
        }
    }

    private static class ServerRegistryAccess {
        private static RegistryAccess get() {
            return ServerLifecycleHooks.getCurrentServer().registryAccess();
        }
    }
}
