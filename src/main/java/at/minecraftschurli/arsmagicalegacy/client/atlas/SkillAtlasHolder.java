package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class SkillAtlasHolder extends TextureAtlasHolder {
    public static final ResourceLocation ATLAS = ArsMagicaApi.id("textures/atlas/skill.png");
    public static final ResourceLocation ATLAS_INFO = ArsMagicaApi.id("skill");
    public static final Lazy<SkillAtlasHolder> INSTANCE = Lazy.of(SkillAtlasHolder::new);

    private SkillAtlasHolder() {
        super(AMClientUtil.mc().getTextureManager(), ATLAS, ATLAS_INFO);
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        return location == null ? super.getSprite(MissingTextureAtlasSprite.getLocation()) : super.getSprite(location);
    }

    public TextureAtlasSprite getSprite(Skill skill) {
        return getSprite(AMRegistries.skills(true).getKey(skill));
    }
}
