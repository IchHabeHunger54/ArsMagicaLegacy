package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.SpellPrefabProvider;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class AMSpellPrefabProvider extends SpellPrefabProvider {
    public AMSpellPrefabProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder("water_bolt", ArsMagicaApi.id("beam_blue_3"),
            List.of(AMSpells.DROWNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("fire_bolt", ArsMagicaApi.id("beam_orange_3"),
            List.of(AMSpells.FIRE_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("earth_bolt", ArsMagicaApi.id("beam_acid_3"),
            List.of(AMSpells.PHYSICAL_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("ice_bolt", ArsMagicaApi.id("beam_sky_3"),
            List.of(AMSpells.FROST_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("lightning_bolt", ArsMagicaApi.id("beam_eerie_3"),
            List.of(AMSpells.LIGHTNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("arcane_bolt", ArsMagicaApi.id("beam_magenta_3"),
            List.of(AMSpells.MAGIC_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_water_bolt", ArsMagicaApi.id("lightning_blue_3"),
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_fire_bolt", ArsMagicaApi.id("lightning_orange_3"),
            List.of(AMSpells.FIRE_DAMAGE.get(), AMSpells.IGNITION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_earth_bolt", ArsMagicaApi.id("lightning_acid_3"),
            List.of(AMSpells.PHYSICAL_DAMAGE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_ice_bolt", ArsMagicaApi.id("lightning_sky_3"),
            List.of(AMSpells.FROST_DAMAGE.get(), AMSpells.FROST.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_lightning_bolt", ArsMagicaApi.id("lightning_eerie_3"),
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.BLINDNESS.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("strong_arcane_bolt", ArsMagicaApi.id("lightning_magenta_3"),
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.LEVITATION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("area_lightning", ArsMagicaApi.id("rip_water_3"),
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get()));
        builder("blink", ArsMagicaApi.id("whirlwind_magenta_3"),
            List.of(AMSpells.BLINK.get()),
            List.of(AMSpells.SELF.get()));
        builder("chaos_water_bolt", ArsMagicaApi.id("beam_red_3"),
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("debuff", ArsMagicaApi.id("explosion_sky_3"),
            List.of(AMSpells.NAUSEA.get(), AMSpells.SLOWNESS.get(), AMSpells.ASTRAL_DISTORTION.get(), AMSpells.ENTANGLE.get(), AMSpells.GRAVITY_WELL.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("dispel", ArsMagicaApi.id("shield_royal_3"),
            List.of(AMSpells.DISPEL.get()),
            List.of(AMSpells.SELF.get()));
        builder("ender_bolt", ArsMagicaApi.id("beam_jade_3"),
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.RANDOM_TELEPORT.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("ender_torrent", ArsMagicaApi.id("light_magenta_3"),
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get(), AMSpells.AREA_OF_EFFECT.get()));
        builder("ender_wave", ArsMagicaApi.id("wind_magenta_3"),
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.WAVE.get(), AMSpells.RANGE.get()));
        builder("heal_self", ArsMagicaApi.id("heart_royal_3"),
            List.of(AMSpells.HEAL.get()),
            List.of(AMSpells.SELF.get()));
        builder("lightning_rune", ArsMagicaApi.id("rune_orange_3"),
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.RUNE.get()));
        builder("melt_armor", ArsMagicaApi.id("spawner_fire_3"),
            List.of(AMSpells.MELT_ARMOR.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("nausea", ArsMagicaApi.id("sword_eerie_3"),
            List.of(AMSpells.NAUSEA.get()),
            List.of(AMSpells.PROJECTILE.get()));
        builder("otherworldly_roar", ArsMagicaApi.id("gravity_magenta_3"),
            List.of(AMSpells.BLINDNESS.get(), AMSpells.SLOWNESS.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get()));
        builder("scramble_synapses", ArsMagicaApi.id("slice_orange_3"),
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.SCRAMBLE_SYNAPSES.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get()));
    }
}
