package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.entity.Projectile;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMEntities {
    DeferredHolder<EntityType<?>, EntityType<Projectile>> PROJECTILE = AMRegistries.ENTITIES.register("projectile", () -> EntityType.Builder.of(Projectile::new, MobCategory.MISC).build("projectile"));

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
