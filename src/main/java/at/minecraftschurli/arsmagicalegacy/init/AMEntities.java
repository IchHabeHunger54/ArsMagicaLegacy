package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.entity.Projectile;
import at.minecraftschurli.arsmagicalegacy.entity.Zone;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMEntities {
    DeferredHolder<EntityType<?>, EntityType<Projectile>> PROJECTILE = register("projectile", Projectile::new, MobCategory.MISC, 0.25f, 0.25f);
    DeferredHolder<EntityType<?>, EntityType<Zone>>       ZONE       = register("zone",       Zone::new,       MobCategory.MISC, 0.25f, 0.25f);

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        return AMRegistries.ENTITIES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).clientTrackingRange(8).build(name));
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
