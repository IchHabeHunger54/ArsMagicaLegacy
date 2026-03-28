package at.minecraftschurli.mods.arsmagicalegacy.datagen.data;

import at.minecraftschurli.mods.arsmagicalegacy.init.AMDamageSources;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public final class AMDamageTypeProvider {
    public static void addDamageTypes(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(AMDamageSources.SPELL_DROWNING, new DamageType("drown", 0, DamageEffects.DROWNING));
        bootstrap.register(AMDamageSources.SPELL_FIRE, new DamageType("inFire", 0.1f, DamageEffects.BURNING));
        bootstrap.register(AMDamageSources.SPELL_FROST, new DamageType("freeze", 0.1f, DamageEffects.FREEZING));
        bootstrap.register(AMDamageSources.SPELL_LIGHTNING, new DamageType("lightningBolt", 0.1f));
        bootstrap.register(AMDamageSources.SPELL_MAGIC, new DamageType("magic", 0));
        bootstrap.register(AMDamageSources.SPELL_PHYSICAL, new DamageType("mob", 0.1f));
        bootstrap.register(AMDamageSources.SPELL_PHYSICAL_PLAYER, new DamageType("player", 0.1f));
        bootstrap.register(AMDamageSources.FALLING_STAR, new DamageType(AMDamageSources.FALLING_STAR.identifier().getPath(), 0.1f));
    }
}
