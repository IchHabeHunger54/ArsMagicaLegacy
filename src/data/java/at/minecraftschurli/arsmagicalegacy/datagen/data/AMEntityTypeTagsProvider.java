package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public final class AMEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public AMEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> provider, ExistingFileHelper existingFileHelper) {
        super(output, provider, ArsMagicaApi.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AMTags.EntityTypes.AFFECTED_BY_ENDER_THORNS_ABILITY).add(EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.SHULKER);
        tag(AMTags.EntityTypes.AFFECTED_BY_SMITE_ABILITY).addTag(EntityTypeTags.UNDEAD);
        tag(AMTags.EntityTypes.AFFECTED_BY_NAUSEA_ABILITY).addTag(EntityTypeTags.UNDEAD);
    }
}
