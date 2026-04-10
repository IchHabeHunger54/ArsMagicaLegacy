package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCasterEntity;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Mob;

public class DispelGoal<T extends Mob & SpellCasterEntity> extends ExecuteSpellGoal<T> {
    private static final Identifier SPELL = ArsMagicaApi.id("dispel");

    public DispelGoal(T caster) {
        super(caster, ArsMagicaApi.spellPrefabManager().get(SPELL), 0);
    }

    @Override
    public boolean canUse() {
        return (!caster.getActiveEffects().isEmpty() || caster.isOnFire()) && super.canUse();
    }

    @Override
    public void stop() {
        super.stop();
        caster.clearFire();
    }
}
