package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class SpellIconAtlasHolder extends TextureAtlasHolder {
    public static final ResourceLocation ATLAS = ArsMagicaApi.modLoc("textures/atlas/spell_icon.png");
    public static final ResourceLocation ATLAS_INFO = ArsMagicaApi.modLoc("spell_icon");
    public static final Lazy<SpellIconAtlasHolder> INSTANCE = Lazy.of(SpellIconAtlasHolder::new);

    private SpellIconAtlasHolder() {
        super(AMClientUtil.mc().getTextureManager(), ATLAS, ATLAS_INFO);
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        return location == null ? super.getSprite(MissingTextureAtlasSprite.getLocation()) : super.getSprite(location);
    }

    public Collection<ResourceLocation> getIcons() {
        return textureAtlas.getTextures().keySet();
    }
}
