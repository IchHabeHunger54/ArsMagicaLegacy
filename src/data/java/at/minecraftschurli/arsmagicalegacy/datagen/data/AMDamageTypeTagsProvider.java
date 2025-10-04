package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.init.AMDamageSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public final class AMDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public AMDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(DamageTypeTags.ALWAYS_TRIGGERS_SILVERFISH).add(AMDamageSources.SPELL_MAGIC);
        tag(DamageTypeTags.AVOIDS_GUARDIAN_THORNS).add(AMDamageSources.SPELL_MAGIC);
        tag(DamageTypeTags.BYPASSES_ARMOR).add(AMDamageSources.SPELL_DROWNING, AMDamageSources.SPELL_FROST, AMDamageSources.SPELL_MAGIC);
        tag(DamageTypeTags.BYPASSES_INVULNERABILITY).addTag(AMTags.DamageTypes.IS_SPELL);
        tag(DamageTypeTags.IGNITES_ARMOR_STANDS).add(AMDamageSources.SPELL_FIRE);
        tag(DamageTypeTags.IS_DROWNING).add(AMDamageSources.SPELL_DROWNING);
        tag(DamageTypeTags.IS_FIRE).add(AMDamageSources.SPELL_FIRE);
        tag(DamageTypeTags.IS_FREEZING).add(AMDamageSources.SPELL_FROST);
        tag(DamageTypeTags.IS_LIGHTNING).add(AMDamageSources.SPELL_LIGHTNING);
        //tag(DamageTypeTags.IS_PROJECTILE).add(AMDamageSources.NATURE_SCYTHE, AMDamageSources.THROWN_ROCK);
        tag(DamageTypeTags.WITHER_IMMUNE_TO).add(AMDamageSources.SPELL_DROWNING);
        tag(DamageTypeTags.WITCH_RESISTANT_TO).add(AMDamageSources.SPELL_MAGIC);
        tag(Tags.DamageTypes.IS_MAGIC).add(AMDamageSources.SPELL_MAGIC);
        tag(Tags.DamageTypes.IS_PHYSICAL).add(AMDamageSources.SPELL_PHYSICAL, AMDamageSources.SPELL_PHYSICAL_PLAYER);
        tag(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY).addTag(DamageTypeTags.IS_FIRE);
        tag(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY).addTag(Tags.DamageTypes.IS_PHYSICAL).remove(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY).addTag(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY).addTag(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY).addTag(Tags.DamageTypes.IS_MAGIC).remove(Tags.DamageTypes.IS_POISON);
        tag(AMTags.DamageTypes.IS_SPELL).add(AMDamageSources.SPELL_DROWNING, AMDamageSources.SPELL_FIRE, AMDamageSources.SPELL_FROST, AMDamageSources.SPELL_LIGHTNING, AMDamageSources.SPELL_MAGIC, AMDamageSources.SPELL_PHYSICAL, AMDamageSources.SPELL_PHYSICAL_PLAYER);
    }
}
