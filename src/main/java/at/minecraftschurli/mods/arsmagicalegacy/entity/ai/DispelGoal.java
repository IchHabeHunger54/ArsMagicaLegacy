package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCasterEntity;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPrefab;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Mob;

public class DispelGoal<T extends Mob & SpellCasterEntity> extends ExecuteSpellGoal<T> {
    private static final ResourceKey<SpellPrefab> SPELL = ResourceKey.create(AMRegistries.Keys.SPELL_PREFAB, ArsMagicaApi.id("dispel"));

    public DispelGoal(T caster) {
        super(caster, SPELL, 0);
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
