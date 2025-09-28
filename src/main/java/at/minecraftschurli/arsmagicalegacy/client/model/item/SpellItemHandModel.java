package at.minecraftschurli.arsmagicalegacy.client.model.item;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.Util;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemDisplayContext;
import net.neoforged.neoforge.client.RenderTypeGroup;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.List;

public class SpellItemHandModel extends SimpleBakedModel {
    private static final int SEED = 42;
    private static final RandomSource RANDOM = RandomSource.create(SEED);

    public SpellItemHandModel(BakedModel originalModel) {
        super(getQuads(originalModel, null), Util.make(new EnumMap<>(Direction.class), map -> {
            for (Direction direction : Direction.values()) {
                map.put(direction, getQuads(originalModel, direction));
            }
        }), false, false, originalModel.isGui3d(), originalModel.getParticleIcon(ModelData.EMPTY), ItemTransforms.NO_TRANSFORMS, originalModel.getOverrides(), RenderTypeGroup.EMPTY);
    }

    private static List<BakedQuad> getQuads(BakedModel model, @Nullable Direction direction) {
        RANDOM.setSeed(SEED);
        return model.getQuads(null, direction, RANDOM, ModelData.EMPTY, null);
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext transformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        if (transformType.firstPerson()) {
            poseStack.translate(applyLeftHandTransform ? 0.05f : -0.05f, 0.6f, 0);
        }
        if (transformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || transformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND) {
            poseStack.translate(0f, 0.25f, 0.1f);
        }
        poseStack.scale(0.5f, 0.5f, 0);
        return this;
    }
}
