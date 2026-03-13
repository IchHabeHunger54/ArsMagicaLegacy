package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Collection;

public class SpellIconAtlasHolder {
    public static final Identifier ATLAS = ArsMagicaApi.id("textures/atlas/spell_icon.png");
    public static final Identifier ATLAS_ID = ArsMagicaApi.id("spell_icon");

    public static TextureAtlasSprite getSprite(@Nullable Identifier location) {
        TextureAtlas atlas = AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID);
        return location == null ? atlas.missingSprite() : atlas.getSprite(location);
    }

    public static Collection<Identifier> getIcons() {
        return AMClientUtil.mc().getAtlasManager().getAtlasOrThrow(ATLAS_ID).getTextures().keySet();
    }
}
