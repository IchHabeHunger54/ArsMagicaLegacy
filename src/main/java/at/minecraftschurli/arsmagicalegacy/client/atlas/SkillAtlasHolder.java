package at.minecraftschurli.arsmagicalegacy.client.atlas;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.TextureAtlasHolder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.Nullable;

public class SkillAtlasHolder extends TextureAtlasHolder {
    public static final ResourceLocation SKILL_ICON_ATLAS = ArsMagicaApi.modLoc("textures/atlas/skill.png");
    public static final ResourceLocation SKILL_ICON_ATLAS_INFO = ArsMagicaApi.modLoc("skill");
    public static final Lazy<SkillAtlasHolder> INSTANCE = Lazy.of(SkillAtlasHolder::new);

    private SkillAtlasHolder() {
        super(ClientUtil.mc().getTextureManager(), SKILL_ICON_ATLAS, SKILL_ICON_ATLAS_INFO);
    }

    @Override
    public TextureAtlasSprite getSprite(@Nullable ResourceLocation location) {
        return location == null ? super.getSprite(MissingTextureAtlasSprite.getLocation()) : super.getSprite(location);
    }

    public TextureAtlasSprite getSprite(Skill skill) {
        return getSprite(ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getKey(skill));
    }
}
