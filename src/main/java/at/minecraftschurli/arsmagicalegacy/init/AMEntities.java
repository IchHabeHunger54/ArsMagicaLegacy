package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.entity.Blizzard;
import at.minecraftschurli.arsmagicalegacy.entity.Dryad;
import at.minecraftschurli.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.arsmagicalegacy.entity.FireRain;
import at.minecraftschurli.arsmagicalegacy.entity.ManaCreeper;
import at.minecraftschurli.arsmagicalegacy.entity.ManaVortex;
import at.minecraftschurli.arsmagicalegacy.entity.Projectile;
import at.minecraftschurli.arsmagicalegacy.entity.Wall;
import at.minecraftschurli.arsmagicalegacy.entity.Wave;
import at.minecraftschurli.arsmagicalegacy.entity.Zone;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.vehicle.boat.Boat;
import net.minecraft.world.entity.vehicle.boat.ChestBoat;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface AMEntities {
    DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<EntityType<?>, EntityType<Blizzard>>    BLIZZARD             = register("blizzard",             Blizzard::new,    MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<FallingStar>> FALLING_STAR         = register("falling_star",         FallingStar::new, MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<FireRain>>    FIRE_RAIN            = register("fire_rain",            FireRain::new,    MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<ManaVortex>>  MANA_VORTEX          = register("mana_vortex",          ManaVortex::new,  MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Projectile>>  PROJECTILE           = register("projectile",           Projectile::new,  MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Wall>>        WALL                 = register("wall",                 Wall::new,        MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Wave>>        WAVE                 = register("wave",                 Wave::new,        MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Zone>>        ZONE                 = register("zone",                 Zone::new,        MobCategory.MISC,     0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Dryad>>       DRYAD                = register("dryad",                Dryad::new,       MobCategory.CREATURE, 0.6f,   1.8f,    builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<ManaCreeper>> MANA_CREEPER         = register("mana_creeper",         ManaCreeper::new, MobCategory.MONSTER,  0.6f,   1.7f,    builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Boat>>        WITCHWOOD_BOAT       = register("witchwood_boat",       boatFactory(AMItems.WITCHWOOD_BOAT::get),            MobCategory.MISC, 1.375f, 0.5625f, builder -> builder.eyeHeight(0.5625f).clientTrackingRange(10));
    DeferredHolder<EntityType<?>, EntityType<ChestBoat>>   WITCHWOOD_CHEST_BOAT = register("witchwood_chest_boat", chestBoatFactory(AMItems.WITCHWOOD_CHEST_BOAT::get), MobCategory.MISC, 1.375f, 0.5625f, builder -> builder.eyeHeight(0.5625f).clientTrackingRange(10));
    // @formatter:on

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, UnaryOperator<EntityType.Builder<T>> operator) {
        return ENTITIES.registerEntityType(name, factory, category, b -> operator.apply(b).sized(width, height));
    }

    private static EntityType.EntityFactory<Boat> boatFactory(Supplier<Item> boatItem) {
        return (entityType, level) -> new Boat(entityType, level, boatItem);
    }

    private static EntityType.EntityFactory<ChestBoat> chestBoatFactory(Supplier<Item> dropItem) {
        return (entityType, level) -> new ChestBoat(entityType, level, dropItem);
    }
}
