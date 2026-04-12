package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.SpellPrefabProvider;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class AMSpellPrefabProvider {
    public static void addSpellPrefabs(BootstrapContext<Spell> bootstrap) {
        add(bootstrap, "water_bolt", "beam_blue_3",
            List.of(AMSpells.DROWNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "fire_bolt", "beam_orange_3",
            List.of(AMSpells.FIRE_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "earth_bolt", "beam_acid_3",
            List.of(AMSpells.PHYSICAL_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "ice_bolt", "beam_sky_3",
            List.of(AMSpells.FROST_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "lightning_bolt", "beam_eerie_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "arcane_bolt", "beam_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_water_bolt", "lightning_blue_3",
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_fire_bolt", "lightning_orange_3",
            List.of(AMSpells.FIRE_DAMAGE.get(), AMSpells.IGNITION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_earth_bolt", "lightning_acid_3",
            List.of(AMSpells.PHYSICAL_DAMAGE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_ice_bolt", "lightning_sky_3",
            List.of(AMSpells.FROST_DAMAGE.get(), AMSpells.FROST.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_lightning_bolt", "lightning_eerie_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.BLINDNESS.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "strong_arcane_bolt", "lightning_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.LEVITATION.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "area_lightning", "rip_water_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get()));
        add(bootstrap, "blink", "whirlwind_magenta_3",
            List.of(AMSpells.BLINK.get()),
            List.of(AMSpells.SELF.get()));
        add(bootstrap, "chaos_water_bolt", "beam_red_3",
            List.of(AMSpells.DROWNING_DAMAGE.get(), AMSpells.WATERY_GRAVE.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "debuff", "explosion_sky_3",
            List.of(AMSpells.NAUSEA.get(), AMSpells.SLOWNESS.get(), AMSpells.ASTRAL_DISTORTION.get(), AMSpells.ENTANGLE.get(), AMSpells.GRAVITY_WELL.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "dispel", "shield_royal_3",
            List.of(AMSpells.DISPEL.get()),
            List.of(AMSpells.SELF.get()));
        add(bootstrap, "ender_bolt", "beam_jade_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.RANDOM_TELEPORT.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "ender_torrent", "light_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get(), AMSpells.AREA_OF_EFFECT.get()));
        add(bootstrap, "ender_wave", "wind_magenta_3",
            List.of(AMSpells.MAGIC_DAMAGE.get(), AMSpells.KNOCKBACK.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.WAVE.get(), AMSpells.RANGE.get()));
        add(bootstrap, "heal_self", "heart_royal_3",
            List.of(AMSpells.HEAL.get()),
            List.of(AMSpells.SELF.get()));
        add(bootstrap, "lightning_rune", "rune_orange_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.DAMAGE.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.RUNE.get()));
        add(bootstrap, "melt_armor", "spawner_fire_3",
            List.of(AMSpells.MELT_ARMOR.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "nausea", "sword_eerie_3",
            List.of(AMSpells.NAUSEA.get()),
            List.of(AMSpells.PROJECTILE.get()));
        add(bootstrap, "otherworldly_roar", "gravity_magenta_3",
            List.of(AMSpells.BLINDNESS.get(), AMSpells.SLOWNESS.get(), AMSpells.KNOCKBACK.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.AREA_OF_EFFECT.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get(), AMSpells.RANGE.get()));
        add(bootstrap, "scramble_synapses", "slice_orange_3",
            List.of(AMSpells.LIGHTNING_DAMAGE.get(), AMSpells.SCRAMBLE_SYNAPSES.get()),
            List.of(AMSpells.PROJECTILE.get(), AMSpells.VELOCITY.get()));
    }

    private static void add(BootstrapContext<Spell> bootstrap, String name, String icon, List<SpellPart> grammar, List<SpellPart> shapeGroup) {
        bootstrap.register(ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id(name)), new Spell(
            Optional.of(Component.translatable("spell_prefab." + ArsMagicaApi.MOD_ID + "." + name)),
            Optional.of(ArsMagicaApi.id(icon)),
            List.of(SpellShapeGroup.of(shapeGroup)),
            0,
            SpellGrammar.of(grammar),
            SpellDataComponentMap.EMPTY
        ));
    }
}
