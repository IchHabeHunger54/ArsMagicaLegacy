package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.entity.EnderGuardian;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class EnderTorrentGoal extends ExecuteBossSpellGoal<EnderGuardian> {
    private static final Identifier SPELL = ArsMagicaApi.id("ender_bolt");

    public EnderTorrentGoal(EnderGuardian caster) {
        super(caster, ArsMagicaApi.spellPrefabManager().get(SPELL), 10);
    }

    @Override
    public void tick() {
        super.tick();
        if (caster.getTarget() != null) {
            caster.getLookControl().setLookAt(caster.getTarget(), 30, 30);
            Level level = caster.level();
            if (caster.getTicksInAction() % 2 == 0 && spell != null) {
                ArsMagicaApi.spellHelper().cast(spell, level, caster, false, false);
            } else if (caster.getTicksInAction() == 10) {
                level.playSound(null, caster, AMSounds.ENDER_GUARDIAN_ATTACK.value(), SoundSource.HOSTILE, 1.0f, (float) (0.5 + caster.getRandom().nextDouble() * 0.5f));
            }
        }
    }
}
