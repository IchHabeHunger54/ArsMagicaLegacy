package at.minecraftschurli.arsmagicalegacy.client.model.item;

import at.minecraftschurli.arsmagicalegacy.client.AMRenderTypes;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockElementFace;
import net.minecraft.client.renderer.block.model.BlockFaceUV;
import net.minecraft.client.renderer.block.model.FaceBakery;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;

public class SpellItemIconModel extends BakedModelWrapper<BakedModel> {
    private static final Vector3f FROM = new Vector3f(0, 0, 8.504f);
    private static final Vector3f TO = new Vector3f(16, 16, 8.504f);
    private static final BlockFaceUV UV = new BlockFaceUV(new float[]{0, 0, 16, 16}, 0);
    private final Identifier icon;

    public SpellItemIconModel(BakedModel originalModel, Identifier icon) {
        super(originalModel);
        this.icon = icon;
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return false;
    }

    @Override
    public List<RenderType> getRenderTypes(ItemStack itemStack, boolean fabulous) {
        return List.of(fabulous ? AMRenderTypes.SPELL_ICON_FABULOUS : AMRenderTypes.SPELL_ICON);
    }

    @Override
    public List<BakedModel> getRenderPasses(ItemStack itemStack, boolean fabulous) {
        return List.of(this);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand) {
        TextureAtlasSprite sprite = SpellIconAtlasHolder.getSprite(icon);
        return List.of(new FaceBakery().bakeQuad(FROM, TO, new BlockElementFace(null, 2, sprite.contents().name().toString(), UV), sprite, Direction.SOUTH, BlockModelRotation.X0_Y0, null, true));
    }
}
