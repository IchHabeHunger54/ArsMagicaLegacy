package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.ParticleSpawnerBuilder;
import at.minecraftschurli.arsmagicalegacy.api.data.ParticleSpawnerProvider;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ApproachEntityController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ArcToEntityController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.ChangeSizeController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.FadeOutController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.FloatUpwardController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.LeaveTrailController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.MoveInKnockbackDirectionController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.MoveInViewDirectionController;
import at.minecraftschurli.arsmagicalegacy.client.particle.controller.OrbitPointController;
import at.minecraftschurli.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.spell.component.Heal;
import at.minecraftschurli.arsmagicalegacy.spell.component.Transplace;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class AMParticleSpawnerProvider extends ParticleSpawnerProvider {
    public AMParticleSpawnerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder(AMSpells.ABSORPTION.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x007fff)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.BLINDNESS.getId(), AMParticles.LENS_FLARE.get(), 15, 25, 35)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0)
            .controller(new OrbitPointController(0.1, 0.5, 1.5, true));
        builder(AMSpells.HASTE.getId(), AMParticles.LIGHTS.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.1, 0.3, 0.6, false))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.INVISIBILITY.getId(), AMParticles.EMBER.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, false))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.JUMP_BOOST.getId(), AMParticles.WIND.get(), 15, 15)
            .offset(-0.5, 0.5, -1.25, -0.75, -0.5, 0.5)
            .speed(-0.05, 0.05, 0, 0.2, -0.05, 0.05)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.LEVITATION.getId(), AMParticles.EMBER.get(), 15, 40)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .color(0x333399)
            .controller(new OrbitPointController(0.1, 0.2, 0.4, 0.8, true));
        builder(AMSpells.NIGHT_VISION.getId(), AMParticles.LIGHTS.get(), 8, 30)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .color(0x337f33)
            .controller(new OrbitPointController(0.1, 0.2, 0.4, 0.8, true));
        builder(AMSpells.REGENERATION.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x19ffcc)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.RESISTANCE.getId(), AMParticles.SYMBOLS.get(), 25, 10)
            .offset(0, -1, 0)
            .scale(0.5f)
            .controller(new OrbitPointController(0.2, 1, 1, true));
        builder(AMSpells.SLOWNESS.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, 0, 2, -0.5, 0.5)
            .controller(new FloatUpwardController(-0.1))
            .controller(new OrbitPointController(0.2, 0.3, 0.6, true));
        builder(AMSpells.SLOW_FALLING.getId(), AMParticles.WIND.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.SWIFTNESS.getId(), AMParticles.STARDUST.get(), 15, 25, 35)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .controller(new OrbitPointController(0.1, 1, 1.5, true));
        builder(AMSpells.WATER_BREATHING.getId(), AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(AMSpells.ASTRAL_DISTORTION.getId(), AMParticles.PULSE.get(), 10, 25, 35)
            .offset(-2.5, 2.5, -2, 2, -2.5, 2.5)
            .color(0xb233e5)
            .controller(new FloatUpwardController(0.2, 0));
        builder(AMSpells.ENTANGLE.getId(), AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new ApproachEntityController(0.15, 0.4));
        builder(AMSpells.FLIGHT.getId(), AMParticles.WIND.get(), 15, 20)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .scale(0.5f)
            .controller(new OrbitPointController(0.2, 0.4, true));
        builder(AMSpells.FROST.getId(), ParticleTypes.SNOWFLAKE, 5, 10)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.3, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FURY.getId(), AMParticles.PULSE.get(), 10, 10)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff0000)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.15, 1, 2, true));
        builder(AMSpells.GRAVITY_WELL.getId(), AMParticles.PULSE.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.05f)
            .controller(new LeaveTrailController(new ParticleSpawnerBuilder(ArsMagicaApi.id("gravity_well_trail"), AMParticles.PULSE.get(), 1, 5)
                .color(0xb233e5)
                .controller(new FloatUpwardController(-0.3))
                .build()))
            .controller(new OrbitPointController(0.2, true))
            .controller(new FadeOutController(false, true, 0.05f));
        builder(AMSpells.REFLECT.getId(), AMParticles.LENS_FLARE.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5);
        builder(AMSpells.SWIFT_SWIM.getId(), AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.TEMPORAL_ANCHOR.getId(), AMParticles.CLOCK.get(), 25, 40)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new OrbitPointController(0.1, 0.2, true));
        builder(AMSpells.TRUE_SIGHT.getId(), AMParticles.STARDUST.get(), 25, 40)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0xb219b2)
            .controller(new OrbitPointController(0.1, 1, 1, true));
        builder(AMSpells.WATERY_GRAVE.getId(), AMParticles.WATER_BALL.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.05f)
            .controller(new FadeOutController())
            .controller(new LeaveTrailController(new ParticleSpawnerBuilder(ArsMagicaApi.id("watery_grave"), AMParticles.WATER_BALL.get(), 1, 5)
                .color(0xffffff)
                .controller(new FloatUpwardController(-0.3))
                .build()))
            .controller(new OrbitPointController(0.2, true));
        builder(AMSpells.DROWNING_DAMAGE.getId(), ParticleTypes.BUBBLE, 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FIRE_DAMAGE.getId(), AMParticles.EXPLOSION.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.FROST_DAMAGE.getId(), ParticleTypes.SNOWFLAKE, 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.LIGHTNING_DAMAGE.getId(), ParticleTypes.ELECTRIC_SPARK, 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.MAGIC_DAMAGE.getId(), AMParticles.ARCANE.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.PHYSICAL_DAMAGE.getId(), AMParticles.EMBER.get(), 5, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f)
            .color(0xcc3333);
        builder(AMSpells.ATTRACT.getId(), AMParticles.ARCANE.get(), 5, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xcc4cb2);
        builder(AMSpells.BANISH_RAIN.getId(), AMParticles.WATER_BALL.get(), 25, 25, 35)
            .offset(-2.5, 2.5, -2, 2, -2.5, 2.5)
            .controller(new FloatUpwardController(0.5));
        builder(AMSpells.BLINK.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.CHARM.getId(), ParticleTypes.HEART, 10, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FloatUpwardController(0, 0.1, 0.15));
        builder(AMSpells.CREATE_WATER.getId(), ParticleTypes.BUBBLE, 15, 10)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .speed(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5);
        builder(AMSpells.DISARM.getId(), ParticleTypes.ELECTRIC_SPARK, 25, 40)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xb2b219)
            .controller(new FadeOutController())
            .controller(new MoveInViewDirectionController(0.1, 0.6));
        builder(AMSpells.DISPEL.getId(), ParticleTypes.ELECTRIC_SPARK, 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .scale(0.5f)
            .color(0xb219b2)
            .controller(new OrbitPointController(0.1, 0.2, true));
        builder(AMSpells.DIVINE_INTERVENTION.getId(), AMParticles.ARCANE.get(), 100, 25, 35)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .controller(new OrbitPointController(0.1, 0.5, 1.5, true));
        builder(AMSpells.DROUGHT.getId(), AMParticles.EMBER.get(), 25, 40)
            .offset(-0.5, 0.5, 1, 1, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xe5cc7f)
            .controller(new FadeOutController())
            .controller(new FloatUpwardController(0.1));
        builder(AMSpells.ENDER_INTERVENTION.getId(), AMParticles.GHOST.get(), 100, 25, 35)
            .offset(-0.5, 0.5, -2, 0, -0.5, 0.5)
            .color(0xb23333)
            .controller(new FloatUpwardController(0.1));
        builder(AMSpells.FLING.getId(), AMParticles.WIND.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FloatUpwardController(0, 0.3, 0.6));
        builder(AMSpells.FORGE.getId(), AMParticles.LIGHTS.get(), 1, 20)
            .scale(1.5f)
            .alpha(0.1f);
        builder(AMSpells.GROW.getId(), AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController())
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.1, 0.3, 0.6, false));
        builder(AMSpells.HARVEST.getId(), AMParticles.PLANT.get(), 25, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .gravity(1)
            .scale(0.5f)
            .color(0xb23319)
            .controller(new FloatUpwardController(0.3));
        builder(AMSpells.HEAL.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .color(0x19ff19)
            .controller(new FloatUpwardController(0.1))
            .controller(new OrbitPointController(0.5, 0.3, 0.6, true));
        builder(Heal.UNDEAD_PARTICLES, AMParticles.SYMBOLS.get(), 25, 50)
            .offset(-0.5, 0.5, -1.5, -0.5, -0.5, 0.5)
            .scale(0.5f)
            .controller(new FadeOutController(0.02f))
            .controller(new FloatUpwardController(-0.01));
        builder(AMSpells.IGNITION.getId(), AMParticles.EXPLOSION.get(), 25, 5)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.3, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.KNOCKBACK.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 1, -0.5, 0.5)
            .controller(new FadeOutController())
            .controller(new MoveInKnockbackDirectionController(0.1, 0.6));
        builder(AMSpells.LIFE_DRAIN.getId(), AMParticles.EMBER.get(), 15, 100)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff3333)
            .alpha(0.5f)
            .controller(new ArcToEntityController());
        builder(AMSpells.LIFE_TAP.getId(), ParticleTypes.ELECTRIC_SPARK, 25, 15)
            .offset(-1, 1, -0.25, 0.25, -1, 1)
            .scale(0.5f)
            .color(0x66197f)
            .controller(new ApproachEntityController(0.1, 0.1));
        builder(AMSpells.LIGHT.getId(), ParticleTypes.ELECTRIC_SPARK, 5, 20)
            .offset(-0.5, 0.5, -0.25, 1.25, -0.5, 0.5)
            .speed(-0.1, 0.1, 0, 0.2, -0.1, 0.1)
            .color(0x9933cc);
        builder(AMSpells.MANA_BLAST.getId(), ParticleTypes.ELECTRIC_SPARK, 100, 10)
            .offset(-0.5, 0.5, -0.25, 0.25, -0.5, 0.5)
            .color(0x9900e5)
            .controller(new ApproachEntityController(0.15, 0.1))
            .controller(new FadeOutController(0.1f));
        builder(AMSpells.MANA_DRAIN.getId(), AMParticles.STARDUST.get(), 15, 100)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0x0066ff)
            .alpha(0.5f)
            .controller(new ArcToEntityController());
        builder(AMSpells.MELT_ARMOR.getId(), AMParticles.LIGHTS.get(), 1, 20)
            .scale(1.5f)
            .color(0xb26633)
            .alpha(0.1f);
        builder(AMSpells.PLANT.getId(), AMParticles.PLANT.get(), 15, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f);
        builder(AMSpells.PLOW.getId(), AMParticles.ROCK.get(), 10, 20)
            .offset(-0.5, 0.5, 0.5, 1.5, -0.5, 0.5)
            .speed(-0.1, 0.1, 0.2, -0.1, 0.1)
            .gravity(1)
            .scale(0.25f);
        builder(AMSpells.RECALL.getId(), AMParticles.ARCANE.get(), 25, 20)
            .offset(-1.5, 1.5, -2, 0, -1.5, 1.5)
            .controller(new ApproachEntityController(0.3, 0.1));
        builder(AMSpells.REPEL.getId(), AMParticles.STARDUST.get(), 1, 20)
            .controller(new FadeOutController());
        builder(AMSpells.TRANSPLACE.getId(), ParticleTypes.ELECTRIC_SPARK, 15, 40)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0xff0000)
            .controller(new ArcToEntityController());
        builder(Transplace.CASTER_PARTICLES, ParticleTypes.ELECTRIC_SPARK, 15, 40)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .color(0x0000ff)
            .controller(new ArcToEntityController());
        builder(AMSpells.BLIZZARD.getId(), ParticleTypes.SNOWFLAKE, 20, 40)
            .speed(-0.1, 0.1, -0.05, 0.05, -0.1, 0.1)
            .gravity(1)
            .scale(0.5f)
            .alpha(0.6f);
        builder(FallingStar.FALL_PARTICLES, AMParticles.EMBER.get(), 1, 5)
            .controller(new ChangeSizeController());
        builder(FallingStar.GROUND_PARTICLES, AMParticles.EMBER.get(), 24, 5)
            .offset(-0.25, 0.25, -0.25, 0.25, -0.25, 0.25)
            .controller(new ChangeSizeController());
        builder(AMSpells.FIRE_RAIN.getId(), AMParticles.EXPLOSION.get(), 20, 40)
            .speed(-0.1, 0.1, -0.05, 0.05, -0.1, 0.1)
            .gravity(1);
        builder(AMSpells.PROJECTILE.getId(), ParticleTypes.CRIT, 1, 5)
            .offset(-0.05, 0.05, -0.05, 0.05, -0.05, 0.05)
            .scale(0.25f)
            .controller(new FadeOutController(0.2f))
            .controller(new FloatUpwardController(0.05, 0));
        builder(AMSpells.WALL.getId(), ParticleTypes.CRIT, 2, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .scale(0.75f)
            .controller(new FloatUpwardController(0.07));
        builder(AMSpells.WAVE.getId(), ParticleTypes.CRIT, 1, 20)
            .offset(-0.5, 0.5, -0.5, 0.5, -0.5, 0.5)
            .scale(0.75f)
            .controller(new MoveInViewDirectionController(0.07, 0.07));
        builder(AMSpells.ZONE.getId(), ParticleTypes.CRIT, 3, 20)
            .offset(-0.5, 0.5, 0, 0.25, -0.5, 0.5)
            .scale(0.75f)
            .controller(new FloatUpwardController(0.07));
    }
}
