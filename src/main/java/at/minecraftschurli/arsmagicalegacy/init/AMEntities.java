package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.entity.Blizzard;
import at.minecraftschurli.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.arsmagicalegacy.entity.FireRain;
import at.minecraftschurli.arsmagicalegacy.entity.ManaCreeper;
import at.minecraftschurli.arsmagicalegacy.entity.ManaVortex;
import at.minecraftschurli.arsmagicalegacy.entity.Projectile;
import at.minecraftschurli.arsmagicalegacy.entity.Wall;
import at.minecraftschurli.arsmagicalegacy.entity.Wave;
import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodBoat;
import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodChestBoat;
import at.minecraftschurli.arsmagicalegacy.entity.Zone;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public interface AMEntities {
    DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Registries.ENTITY_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<EntityType<?>, EntityType<Blizzard>>           BLIZZARD             = register("blizzard",             Blizzard::new,           MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<FallingStar>>        FALLING_STAR         = register("falling_star",         FallingStar::new,        MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<FireRain>>           FIRE_RAIN            = register("fire_rain",            FireRain::new,           MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<ManaCreeper>>        MANA_CREEPER         = register("mana_creeper",         ManaCreeper::new,        MobCategory.MONSTER, 0.6f,   1.7f,    builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<ManaVortex>>         MANA_VORTEX          = register("mana_vortex",          ManaVortex::new,         MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Projectile>>         PROJECTILE           = register("projectile",           Projectile::new,         MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Wall>>               WALL                 = register("wall",                 Wall::new,               MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<Wave>>               WAVE                 = register("wave",                 Wave::new,               MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    DeferredHolder<EntityType<?>, EntityType<WitchwoodBoat>>      WITCHWOOD_BOAT       = register("witchwood_boat",       WitchwoodBoat::new,      MobCategory.MISC,    1.375f, 0.5625f, builder -> builder.eyeHeight(0.5625f).clientTrackingRange(10));
    DeferredHolder<EntityType<?>, EntityType<WitchwoodChestBoat>> WITCHWOOD_CHEST_BOAT = register("witchwood_chest_boat", WitchwoodChestBoat::new, MobCategory.MISC,    1.375f, 0.5625f, builder -> builder.eyeHeight(0.5625f).clientTrackingRange(10));
    DeferredHolder<EntityType<?>, EntityType<Zone>>               ZONE                 = register("zone",                 Zone::new,               MobCategory.MISC,    0.25f,  0.25f,   builder -> builder.clientTrackingRange(8));
    // @formatter:on

    private static <T extends Entity> DeferredHolder<EntityType<?>, EntityType<T>> register(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, UnaryOperator<EntityType.Builder<T>> operator) {
        return ENTITIES.register(name, () -> operator.apply(EntityType.Builder.of(factory, category).sized(width, height)).build(name));
    }
}
