package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageTypes;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public final class AMDamageTypeProvider {
    public static void addDamageTypes(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(AMDamageTypes.SPELL_DROWNING, new DamageType("drown", 0, DamageEffects.DROWNING));
        bootstrap.register(AMDamageTypes.SPELL_FIRE, new DamageType("inFire", 0.1f, DamageEffects.BURNING));
        bootstrap.register(AMDamageTypes.SPELL_FROST, new DamageType("freeze", 0.1f, DamageEffects.FREEZING));
        bootstrap.register(AMDamageTypes.SPELL_LIGHTNING, new DamageType("lightningBolt", 0.1f));
        bootstrap.register(AMDamageTypes.SPELL_MAGIC, new DamageType("magic", 0));
        bootstrap.register(AMDamageTypes.SPELL_PHYSICAL, new DamageType("mob", 0.1f));
        bootstrap.register(AMDamageTypes.SPELL_PHYSICAL_PLAYER, new DamageType("player", 0.1f));
        bootstrap.register(AMDamageTypes.FALLING_STAR, new DamageType(AMDamageTypes.FALLING_STAR.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.NATURE_SCYTHE, new DamageType(AMDamageTypes.NATURE_SCYTHE.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.SHOCKWAVE, new DamageType(AMDamageTypes.SHOCKWAVE.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.THROWN_ROCK, new DamageType(AMDamageTypes.THROWN_ROCK.identifier().getPath(), 0.1f));
        bootstrap.register(AMDamageTypes.WHIRLWIND, new DamageType(AMDamageTypes.WHIRLWIND.identifier().getPath(), 0.1f));
    }
}
