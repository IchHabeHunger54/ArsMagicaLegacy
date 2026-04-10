package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import org.jspecify.annotations.Nullable;

public class HealGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    private static final Identifier SPELL = ArsMagicaApi.id("heal_self");

    public HealGoal(T caster) {
        super(caster, ArsMagicaApi.spellPrefabManager().get(SPELL), 20);
    }

    @Override
    public boolean canUse() {
        return caster.getHealth() != caster.getMaxHealth() && super.canUse();
    }

    @Override
    @Nullable
    protected SoundEvent getAttackSound() {
        return AMSounds.LIFE_GUARDIAN_HEAL.value();
    }
}
