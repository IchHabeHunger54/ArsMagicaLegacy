package at.minecraftschurli.mods.arsmagicalegacy.init;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.mods.arsmagicalegacy.entity.NatureScythe;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public interface AMDamageSources {
    // @formatter:off
    ResourceKey<DamageType> SPELL_DROWNING        = damageType("spell_drowning");
    ResourceKey<DamageType> SPELL_FIRE            = damageType("spell_fire");
    ResourceKey<DamageType> SPELL_FROST           = damageType("spell_frost");
    ResourceKey<DamageType> SPELL_LIGHTNING       = damageType("spell_lightning");
    ResourceKey<DamageType> SPELL_MAGIC           = damageType("spell_magic");
    ResourceKey<DamageType> SPELL_PHYSICAL        = damageType("spell_physical");
    ResourceKey<DamageType> SPELL_PHYSICAL_PLAYER = damageType("spell_physical_player");
    ResourceKey<DamageType> FALLING_STAR          = damageType("falling_star");
    ResourceKey<DamageType> NATURE_SCYTHE         = damageType("nature_scythe");
    // @formatter:on

    private static ResourceKey<DamageType> damageType(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ArsMagicaApi.id(name));
    }

    private static Holder<DamageType> damageType(RegistryAccess registryAccess, ResourceKey<DamageType> damageType) {
        return registryAccess.lookupOrThrow(Registries.DAMAGE_TYPE).getOrThrow(damageType);
    }

    static DamageSource fallingStar(FallingStar source) {
        return new DamageSource(damageType(source.registryAccess(), FALLING_STAR), source.getOwner(), source);
    }

    static DamageSource natureScythe(NatureScythe source) {
        return new DamageSource(damageType(source.registryAccess(), NATURE_SCYTHE), source.getOwner(), source);
    }
}
