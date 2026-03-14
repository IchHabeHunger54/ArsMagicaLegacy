package at.minecraftschurli.arsmagicalegacy.attachment;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public record TemporalAnchorAttachment(
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
    public static Codec<TemporalAnchorAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Vec3.CODEC.fieldOf("position").forGetter(TemporalAnchorAttachment::position),
        Codec.FLOAT.fieldOf("pitch").forGetter(TemporalAnchorAttachment::pitch),
        Codec.FLOAT.fieldOf("yaw").forGetter(TemporalAnchorAttachment::yaw),
        Codec.FLOAT.fieldOf("headYaw").forGetter(TemporalAnchorAttachment::headYaw),
        Codec.DOUBLE.fieldOf("mana").forGetter(TemporalAnchorAttachment::mana),
        Codec.DOUBLE.fieldOf("burnout").forGetter(TemporalAnchorAttachment::burnout),
        Codec.FLOAT.fieldOf("health").forGetter(TemporalAnchorAttachment::health),
        Codec.INT.fieldOf("air").forGetter(TemporalAnchorAttachment::air),
        CompoundTag.CODEC.fieldOf("attributes").forGetter(TemporalAnchorAttachment::attributes),
        MobEffectInstance.CODEC.listOf().fieldOf("mob_effects").forGetter(TemporalAnchorAttachment::mobEffects),
        CompoundTag.CODEC.fieldOf("food").forGetter(TemporalAnchorAttachment::food),
        MagicAttachment.CODEC.optionalFieldOf("magic").forGetter(TemporalAnchorAttachment::magic)
    ).apply(inst, TemporalAnchorAttachment::new));
    private static final String ATTRIBUTES_KEY = "attributes";

    public static TemporalAnchorAttachment from(LivingEntity entity) {
        CompoundTag attributes = new CompoundTag();
        attributes.put(ATTRIBUTES_KEY, entity.getAttributes().save());
        CompoundTag food = new CompoundTag();
        if (entity instanceof ServerPlayer player) {
            player.getFoodData().addAdditionalSaveData(food);
        }
        return new TemporalAnchorAttachment(
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
        entity.removeAllEffects();
        mobEffects.forEach(entity::addEffect);
        if (entity instanceof ServerPlayer player) {
            player.teleportTo(player.level(), position.x, position.y, position.z, Set.of(), yaw, pitch, true);
            FoodData foodData = new FoodData();
            foodData.readAdditionalSaveData(food);
            player.foodData = foodData;
            magic.ifPresent(data -> player.setData(AMAttachments.MAGIC, data));
        } else {
            entity.snapTo(position.x, position.y, position.z, yaw, pitch);
            entity.setYHeadRot(headYaw);
        }
    }
}
