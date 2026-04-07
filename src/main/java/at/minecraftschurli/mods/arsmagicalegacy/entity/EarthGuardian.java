package at.minecraftschurli.mods.arsmagicalegacy.entity;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StompGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.StrikeGoal;
import at.minecraftschurli.mods.arsmagicalegacy.entity.ai.ThrowRockGoal;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMAttributes;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public class EarthGuardian extends AbstractBoss {
    public static final byte RENDER_ROCK_TRUE = (byte) -8;
    public static final byte RENDER_ROCK_FALSE = (byte) -9;
    private static final String RENDER_ROCK_KEY = "render_rock";
    public boolean renderRock = false;

    public EarthGuardian(EntityType<? extends EarthGuardian> type, Level level) {
        super(type, level, AMTags.DamageTypes.EARTH_GUARDIAN_IS_VULNERABLE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_IMMUNE_TO, AMTags.DamageTypes.EARTH_GUARDIAN_IS_HEAL_TO);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createBossAttributes()
            .add(Attributes.MAX_HEALTH, 120)
            .add(Attributes.ARMOR, 10)
            .add(AMAttributes.MAX_MANA, 1000)
            .add(AMAttributes.MAX_BURNOUT, 1000);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.child(ArsMagicaApi.MOD_ID).ifPresent(child -> renderRock = child.getBooleanOr(RENDER_ROCK_KEY, true));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.child(ArsMagicaApi.MOD_ID).putBoolean(RENDER_ROCK_KEY, renderRock);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return AMSounds.EARTH_GUARDIAN_AMBIENT.get();
    }

    @Override
    public SoundEvent getAttackSound() {
        return AMSounds.EARTH_GUARDIAN_ATTACK.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return AMSounds.EARTH_GUARDIAN_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return AMSounds.EARTH_GUARDIAN_HURT.get();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(1, new StompGoal<>(this));
        goalSelector.addGoal(1, new StrikeGoal<>(this));
        goalSelector.addGoal(1, new ThrowRockGoal(this));
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == RENDER_ROCK_TRUE) {
            renderRock = true;
        } else if (id == RENDER_ROCK_FALSE) {
            renderRock = false;
        }
        super.handleEntityEvent(id);
    }
}
