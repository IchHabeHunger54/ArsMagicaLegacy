package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPrefab;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import org.jspecify.annotations.Nullable;

public class ExecuteBossSpellGoal<T extends AbstractBoss> extends ExecuteSpellGoal<T> {
    public ExecuteBossSpellGoal(T caster, int duration) {
        super(caster, duration);
    }

    public ExecuteBossSpellGoal(T caster, ResourceKey<SpellPrefab> prefabSpell, int duration) {
        super(caster, prefabSpell, duration);
    }

    public ExecuteBossSpellGoal(T caster, Identifier prefabSpellId, int duration) {
        super(caster, prefabSpellId, duration);
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && caster.getTicksInAction() <= duration * 2;
    }

    @Override
    @Nullable
    protected SoundEvent getAttackSound() {
        return caster.getAttackSound();
    }
}
