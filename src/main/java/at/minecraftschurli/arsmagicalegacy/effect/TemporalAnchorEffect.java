package at.minecraftschurli.arsmagicalegacy.effect;

import at.minecraftschurli.arsmagicalegacy.attachment.TemporalAnchorAttachment;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

public class TemporalAnchorEffect extends AMMobEffect {
    public TemporalAnchorEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xa2a2a2);
    }

    @Override
    public void startEffect(LivingEntity entity, MobEffectInstance effect) {
        entity.setData(AMAttachments.TEMPORAL_ANCHOR_SNAPSHOT, TemporalAnchorAttachment.from(entity));
    }

    @Override
    public void stopEffect(LivingEntity entity, MobEffectInstance effect) {
        TemporalAnchorAttachment attachment = entity.removeData(AMAttachments.TEMPORAL_ANCHOR_SNAPSHOT);
        if (attachment != null) {
            attachment.apply(entity);
        }
    }
}
