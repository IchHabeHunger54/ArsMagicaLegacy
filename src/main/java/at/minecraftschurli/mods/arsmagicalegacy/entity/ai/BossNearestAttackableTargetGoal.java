package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import org.jspecify.annotations.Nullable;

public class BossNearestAttackableTargetGoal<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
    public BossNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee) {
        super(mob, targetType, mustSee);
    }

    public BossNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, TargetingConditions.Selector selector) {
        super(mob, targetType, mustSee, selector);
    }

    public BossNearestAttackableTargetGoal(Mob mob, Class<T> targetType, boolean mustSee, boolean pMustReach) {
        super(mob, targetType, mustSee, pMustReach);
    }

    public BossNearestAttackableTargetGoal(Mob mob, Class<T> targetType, int pRandomInterval, boolean mustSee, boolean mustReach, TargetingConditions.@Nullable Selector selector) {
        super(mob, targetType, pRandomInterval, mustSee, mustReach, selector);
    }

    @Override
    protected AABB getTargetSearchArea(double followDistance) {
        return super.getTargetSearchArea(followDistance).inflate(followDistance, 2, followDistance);
    }
}
