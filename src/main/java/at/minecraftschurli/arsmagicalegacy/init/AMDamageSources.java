package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.entity.FallingStar;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;

public interface AMDamageSources {
    ResourceKey<DamageType> SPELL_DROWNING        = damageType("spell_drowning");
    ResourceKey<DamageType> SPELL_FIRE            = damageType("spell_fire");
    ResourceKey<DamageType> SPELL_FROST           = damageType("spell_frost");
    ResourceKey<DamageType> SPELL_LIGHTNING       = damageType("spell_lightning");
    ResourceKey<DamageType> SPELL_MAGIC           = damageType("spell_magic");
    ResourceKey<DamageType> SPELL_PHYSICAL        = damageType("spell_physical");
    ResourceKey<DamageType> SPELL_PHYSICAL_PLAYER = damageType("spell_physical_player");
    ResourceKey<DamageType> FALLING_STAR          = damageType("falling_star");

    private static ResourceKey<DamageType> damageType(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, ArsMagicaApi.modLoc(name));
    }

    private static Holder<DamageType> damageType(RegistryAccess registryAccess, ResourceKey<DamageType> damageType) {
        return registryAccess.registryOrThrow(Registries.DAMAGE_TYPE).getHolderOrThrow(damageType);
    }

    static DamageSource fallingStar(FallingStar source) {
        return new DamageSource(damageType(source.registryAccess(), FALLING_STAR), source.getOwner(), source);
    }
}
