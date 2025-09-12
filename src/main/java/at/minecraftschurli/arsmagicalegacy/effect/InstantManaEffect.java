package at.minecraftschurli.arsmagicalegacy.effect;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMAttributes;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

public class InstantManaEffect extends InstantenousMobEffect {
    public InstantManaEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x00ffff);
    }

    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        if (livingEntity.getAttributes().hasAttribute(AMAttributes.MAX_MANA)) {
            manaHelper.increaseMana(livingEntity, manaHelper.getMaxMana(livingEntity) / 5 * amplifier);
        }
    }
}
