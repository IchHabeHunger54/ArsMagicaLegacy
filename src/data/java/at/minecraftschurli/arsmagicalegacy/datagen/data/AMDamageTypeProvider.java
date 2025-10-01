package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.init.AMDamageSources;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.damagesource.DamageType;

public final class AMDamageTypeProvider {
    public static void addDamageTypes(BootstrapContext<DamageType> bootstrap) {
        bootstrap.register(AMDamageSources.FALLING_STAR, new DamageType(AMDamageSources.FALLING_STAR.location().getPath(), 0.1f));
    }
}
