package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public record SpellDamage(Map<Integer, Map<ResourceKey<DamageType>, Float>> damage) {
    public static final Codec<SpellDamage> CODEC = Codec.unboundedMap(AMUtil.STRING_ENCODED_INT_CODEC, Codec.unboundedMap(ResourceKey.codec(Registries.DAMAGE_TYPE), Codec.FLOAT)).xmap(SpellDamage::new, SpellDamage::damage);
    public static final SpellDamage EMPTY = new SpellDamage();

    public SpellDamage() {
        this(new HashMap<>());
    }

    public SpellDamage setDamage(Entity entity, ResourceKey<DamageType> type, float amount) {
        Map<Integer, Map<ResourceKey<DamageType>, Float>> newDamage = new HashMap<>(damage);
        Map<ResourceKey<DamageType>, Float> map = new HashMap<>(newDamage.getOrDefault(entity.getId(), new HashMap<>()));
        map.put(type, amount);
        newDamage.put(entity.getId(), map);
        return new SpellDamage(newDamage);
    }

    public void apply(LivingEntity caster, Entity directEntity) {
        Level level = caster.level();
        Registry<DamageType> damageTypes = level.registryAccess().registryOrThrow(Registries.DAMAGE_TYPE);
        for (Map.Entry<Integer, Map<ResourceKey<DamageType>, Float>> damageEntry : damage.entrySet()) {
            Entity entity = level.getEntity(damageEntry.getKey());
            if (entity == null) continue;
            int invulnerableTime = entity.invulnerableTime;
            boolean hurtMarked = entity.hurtMarked;
            for (Map.Entry<ResourceKey<DamageType>, Float> entry : damageEntry.getValue().entrySet()) {
                Optional<Holder.Reference<DamageType>> holder = damageTypes.getHolder(entry.getKey());
                if (holder.isEmpty()) continue;
                DamageSource source = new DamageSource(holder.get(), directEntity, caster);
                if (entity.isInvulnerableTo(source)) continue;
                entity.hurt(source, entry.getValue());
                invulnerableTime = Math.max(invulnerableTime, entity.invulnerableTime);
                hurtMarked |= entity.hurtMarked;
                entity.invulnerableTime = 0;
                entity.hurtMarked = false;
            }
            entity.invulnerableTime = invulnerableTime;
            entity.hurtMarked = hurtMarked;
        }
    }
}
