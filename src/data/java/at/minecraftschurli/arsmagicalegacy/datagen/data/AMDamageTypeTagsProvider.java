package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
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
        tag(AMTags.DamageTypes.AFFECTED_BY_FIRE_RESISTANCE_ABILITY).addTag(DamageTypeTags.IS_FIRE);
        tag(AMTags.DamageTypes.AFFECTED_BY_RESISTANCE_ABILITY).addTag(Tags.DamageTypes.IS_PHYSICAL).remove(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_FALL_DAMAGE_ABILITY).addTag(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_FEATHER_FALLING_ABILITY).addTag(DamageTypeTags.IS_FALL);
        tag(AMTags.DamageTypes.AFFECTED_BY_MAGIC_DAMAGE_ABILITY).addTag(Tags.DamageTypes.IS_MAGIC).remove(Tags.DamageTypes.IS_POISON);
    }
}
