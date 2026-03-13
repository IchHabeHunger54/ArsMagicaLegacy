package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import net.minecraft.client.renderer.texture.atlas.sources.DirectoryLister;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.SpriteSourceProvider;

import java.util.concurrent.CompletableFuture;

public final class AMSpriteSourceProvider extends SpriteSourceProvider {
    public AMSpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    protected void gather() {
        atlas(SkillAtlasHolder.ATLAS_ID).addSource(new DirectoryLister("skill", ""));
        atlas(SpellIconAtlasHolder.ATLAS_ID).addSource(new DirectoryLister("spell_icon", ""));
    }
}
