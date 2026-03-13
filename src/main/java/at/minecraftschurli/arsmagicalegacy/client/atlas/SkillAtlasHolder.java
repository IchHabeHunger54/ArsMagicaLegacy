package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class SkillAtlasHolder extends TextureAtlasHolder {
    public static final Identifier ATLAS = ArsMagicaApi.id("textures/atlas/skill.png");
    public static final Identifier ATLAS_INFO = ArsMagicaApi.id("skill");
    public static final Lazy<SkillAtlasHolder> INSTANCE = Lazy.of(SkillAtlasHolder::new);

    private SkillAtlasHolder() {
        super(AMClientUtil.mc().getTextureManager(), ATLAS, ATLAS_INFO);
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable Identifier location) {
        return location == null ? super.getSprite(MissingTextureAtlasSprite.getLocation()) : super.getSprite(location);
    }

    public TextureAtlasSprite getSprite(Skill skill) {
        return getSprite(AMRegistries.skills(true).getKey(skill));
    }
}
