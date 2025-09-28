package at.minecraftschurli.arsmagicalegacy.client.model.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.BakedModelWrapper;
import org.jetbrains.annotations.Nullable;

public class SpellItemModel extends BakedModelWrapper<BakedModel> {
    private ResourceLocation icon;
    private ResourceKey<Affinity> affinity;
    private final ItemOverrides overrides = new ItemOverrides() {
        @SuppressWarnings("DataFlowIssue")
        @Override
        @Nullable
        public BakedModel resolve(BakedModel model, ItemStack stack, @Nullable ClientLevel level, @Nullable LivingEntity entity, int seed) {
            if (stack.has(AMDataComponents.SPELL)) {
                Spell spell = stack.get(AMDataComponents.SPELL);
                icon = spell.icon().orElse(null);
                affinity = spell.grammar().primaryAffinity();
            }
            return super.resolve(model, stack, level, entity, seed);
        }
    };

    public SpellItemModel(BakedModel originalModel) {
        super(originalModel);
    }

    private static boolean isHand(ItemDisplayContext cameraTransformType) {
        return cameraTransformType == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || cameraTransformType == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || cameraTransformType.firstPerson();
    }

    @Override
    public boolean usesBlockLight() {
        return false;
    }

    @Override
    public boolean isCustomRenderer() {
        return true;
    }

    @Override
    public ItemOverrides getOverrides() {
        return overrides;
    }

    @Override
    public BakedModel applyTransform(ItemDisplayContext cameraTransformType, PoseStack poseStack, boolean applyLeftHandTransform) {
        Player player = AMClientUtil.player();
        if (player == null || !ArsMagicaApi.magicHelper().knowsMagic(player)) return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        if (affinity != null && isHand(cameraTransformType))
            return new SpellItemHandModel(AMClientUtil.mc().getModelManager().getModel(ModelResourceLocation.standalone(affinity.location().withPrefix("item/spell_")))).applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        if (icon == null || cameraTransformType != ItemDisplayContext.GUI) return super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform);
        return new SpellItemIconModel(super.applyTransform(cameraTransformType, poseStack, applyLeftHandTransform), icon);
    }
}
