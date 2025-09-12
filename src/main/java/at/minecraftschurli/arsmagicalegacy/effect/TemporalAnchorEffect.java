package at.minecraftschurli.arsmagicalegacy.effect;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class TemporalAnchorEffect extends AMMobEffect {
    public TemporalAnchorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.setData(AMAttachments.TEMPORAL_ANCHOR_SNAPSHOT, Snapshot.from(entity));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        Snapshot snapshot = entity.removeData(AMAttachments.TEMPORAL_ANCHOR_SNAPSHOT);
        if (snapshot != null) {
            snapshot.apply(entity);
        }
    }

    public record Snapshot(
        Vec3 position,
        float pitch,
        float yaw,
        float headYaw,
        double mana,
        double burnout,
        float health,
        int air,
        CompoundTag attributes,
        List<MobEffectInstance> mobEffects,
        CompoundTag food,
        Optional<MagicAttachment> magic
    ) {
        public static Codec<Snapshot> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Vec3.CODEC.fieldOf("position").forGetter(Snapshot::position),
            Codec.FLOAT.fieldOf("pitch").forGetter(Snapshot::pitch),
            Codec.FLOAT.fieldOf("yaw").forGetter(Snapshot::yaw),
            Codec.FLOAT.fieldOf("headYaw").forGetter(Snapshot::headYaw),
            Codec.DOUBLE.fieldOf("mana").forGetter(Snapshot::mana),
            Codec.DOUBLE.fieldOf("burnout").forGetter(Snapshot::burnout),
            Codec.FLOAT.fieldOf("health").forGetter(Snapshot::health),
            Codec.INT.fieldOf("air").forGetter(Snapshot::air),
            CompoundTag.CODEC.fieldOf("attributes").forGetter(Snapshot::attributes),
            MobEffectInstance.CODEC.listOf().fieldOf("mob_effects").forGetter(Snapshot::mobEffects),
            CompoundTag.CODEC.fieldOf("food").forGetter(Snapshot::food),
            MagicAttachment.CODEC.optionalFieldOf("magic").forGetter(Snapshot::magic)
        ).apply(inst, Snapshot::new));
        private static final String ATTRIBUTES_KEY = "attributes";

        public static Snapshot from(LivingEntity entity) {
            CompoundTag attributes = new CompoundTag();
            attributes.put(ATTRIBUTES_KEY, entity.getAttributes().save());
            CompoundTag food = new CompoundTag();
            if (entity instanceof ServerPlayer player) {
                player.getFoodData().addAdditionalSaveData(food);
            }
            return new Snapshot(
                entity.position(),
                entity.getXRot(),
                entity.getYRot(),
                entity.getYHeadRot(),
                ArsMagicaApi.manaHelper().getMana(entity),
                ArsMagicaApi.burnoutHelper().getBurnout(entity),
                entity.getHealth(),
                entity.getAirSupply(),
                attributes,
                entity.getActiveEffects().stream().map(MobEffectInstance::new).toList(),
                food,
                entity instanceof ServerPlayer player ? Optional.of(player.getData(AMAttachments.MAGIC)) : Optional.empty()
            );
        }

        public void apply(LivingEntity entity) {
            ArsMagicaApi.manaHelper().setMana(entity, mana);
            ArsMagicaApi.burnoutHelper().setBurnout(entity, burnout);
            entity.setHealth(health);
            entity.setAirSupply(air);
            entity.getAttributes().load(attributes.getList(ATTRIBUTES_KEY, ListTag.TAG_COMPOUND));
            mobEffects.forEach(entity::addEffect);
            if (entity instanceof ServerPlayer player) {
                player.teleportTo(player.serverLevel(), position.x, position.y, position.z, Set.of(), yaw, pitch);
                FoodData foodData = new FoodData();
                foodData.readAdditionalSaveData(food);
                player.foodData = foodData;
                magic.ifPresent(data -> player.setData(AMAttachments.MAGIC, data));
            } else {
                entity.moveTo(position.x, position.y, position.z, yaw, pitch);
                entity.setYHeadRot(headYaw);
            }
        }
    }
}
