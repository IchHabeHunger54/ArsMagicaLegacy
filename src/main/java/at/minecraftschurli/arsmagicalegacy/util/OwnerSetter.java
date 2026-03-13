package at.minecraftschurli.arsmagicalegacy.util;

import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.LivingEntity;
import org.jspecify.annotations.Nullable;

public interface OwnerSetter {
    void setOwner(@Nullable EntityReference<LivingEntity> owner);
    default void setOwner(@Nullable LivingEntity owner) {
        setOwner(owner == null ? null : EntityReference.of(owner));
    }
}
