package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EnderGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class OtherworldlyRoarGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    private static final Identifier SPELL = ArsMagicaApi.id("otherworldly_roar");

    public OtherworldlyRoarGoal(EnderGuardian caster) {
        super(caster, ArsMagicaApi.spellPrefabManager().get(SPELL), 10);
    }

    @Override
    protected SoundEvent getAttackSound() {
        return AMSounds.ENDER_GUARDIAN_ROAR.value();
    }
}
