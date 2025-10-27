package at.minecraftschurli.arsmagicalegacy.client.model;

import at.minecraftschurli.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.ChunkRenderTypeSet;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class AltarCoreModel extends BakedModelWrapper<BakedModel> {
    public AltarCoreModel(BakedModel originalModel) {
        super(originalModel);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, RandomSource rand, ModelData data, @Nullable RenderType renderType) {
        if (state == null || !state.hasProperty(AltarCoreBlock.FORMED) || !state.getValue(AltarCoreBlock.FORMED) || !data.has(AltarCoreBlockEntity.CAMO)) return super.getQuads(state, side, rand, data, renderType);
        BlockState camo = data.get(AltarCoreBlockEntity.CAMO);
        BakedModel model = AMClientUtil.mc().getBlockRenderer().getBlockModel(camo);
        //TODO test with sodium/embeddium
        List<BakedQuad> quads = new ArrayList<>(model.getQuads(camo, side, rand, ModelData.EMPTY, renderType));
        quads.addAll(super.getQuads(state, side, rand, data, renderType));
        return quads;
    }

    @Override
    public ChunkRenderTypeSet getRenderTypes(BlockState state, RandomSource rand, ModelData data) {
        if (state.hasProperty(AltarCoreBlock.FORMED) && state.getValue(AltarCoreBlock.FORMED) && data.has(AltarCoreBlockEntity.CAMO)) {
            BlockState camo = data.get(AltarCoreBlockEntity.CAMO);
            BakedModel blockModel = AMClientUtil.mc().getBlockRenderer().getBlockModel(camo);
            return ChunkRenderTypeSet.union(blockModel.getRenderTypes(camo, rand, ModelData.EMPTY), super.getRenderTypes(state, rand, data));
        }
        return super.getRenderTypes(state, rand, data);
    }
}
