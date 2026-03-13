package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class SpellIconAtlasHolder extends TextureAtlasHolder {
    public static final Identifier ATLAS = ArsMagicaApi.id("textures/atlas/spell_icon.png");
    public static final Identifier ATLAS_INFO = ArsMagicaApi.id("spell_icon");
    public static final Lazy<SpellIconAtlasHolder> INSTANCE = Lazy.of(SpellIconAtlasHolder::new);

    private SpellIconAtlasHolder() {
        super(AMClientUtil.mc().getTextureManager(), ATLAS, ATLAS_INFO);
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable Identifier location) {
        return location == null ? super.getSprite(MissingTextureAtlasSprite.getLocation()) : super.getSprite(location);
    }

    public Collection<Identifier> getIcons() {
        return textureAtlas.getTextures().keySet();
    }
}
