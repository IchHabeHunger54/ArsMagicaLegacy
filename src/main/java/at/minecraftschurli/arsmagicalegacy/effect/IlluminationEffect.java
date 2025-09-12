package at.minecraftschurli.arsmagicalegacy.effect;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.LightLayer;

public class IlluminationEffect extends MobEffect {
    public IlluminationEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xffffbe);
    }

    @Override
    public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level().getBrightness(LightLayer.BLOCK, livingEntity.blockPosition()) == 0) {
            livingEntity.level().setBlockAndUpdate(livingEntity.blockPosition(), AMBlocks.SPELL_LIGHT.get().defaultBlockState());
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
        return true;
    }
}
