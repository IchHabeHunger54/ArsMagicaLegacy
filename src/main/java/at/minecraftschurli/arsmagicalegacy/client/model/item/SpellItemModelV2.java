package at.minecraftschurli.arsmagicalegacy.client.model.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SpellIconAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.CuboidItemModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.item.ModelRenderProperties;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ResolvedModel;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.TextureSlots;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SpellItemModelV2 implements ItemModel {
    private final ItemModel defaultModel;
    private final ModelRenderProperties properties;
    private final SpriteGetter sprites;
    private final Map<TextureAtlasSprite, BakedQuad> spriteQuads = new IdentityHashMap<>();

    public SpellItemModelV2(ItemModel defaultModel, ModelRenderProperties properties, SpriteGetter sprites) {
        this.defaultModel = defaultModel;
        this.properties = properties;
        this.sprites = sprites;
    }

    @Override
    public void update(ItemStackRenderState output, ItemStack item, ItemModelResolver resolver, ItemDisplayContext displayContext, @Nullable ClientLevel level, @Nullable ItemOwner owner, int seed) {
        output.appendModelIdentityElement(this);
        if (!ArsMagicaApi.magicHelper().knowsMagic(AMClientUtil.player())) {
            defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        Spell spell = item.get(AMDataComponents.SPELL);
        if (spell == null) {
            defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        var affinity = spell.grammar().primaryAffinity();
        if (affinity != null && isHand(displayContext)) {
            Minecraft.getInstance()
                .getModelManager()
                .getItemModel(affinity.identifier().withPrefix("item/spell_"))
                .update(output, item, resolver, displayContext, level, owner, seed);
            return;
        }
        var icon = spell.icon().map(i -> SpellIconAtlasHolder.getSpriteOrNull(sprites, i));
        if (icon.isPresent() && displayContext == ItemDisplayContext.GUI) {
            ItemStackRenderState.LayerRenderState layer = output.newLayer();
            layer.prepareQuadList().add(spriteQuads.computeIfAbsent(icon.get(), SpellItemModelV2::bakedSpriteQuads));
            properties.applyToLayer(layer, displayContext);
            layer.setUsesBlockLight(false);
            return;
        }
        defaultModel.update(output, item, resolver, displayContext, level, owner, seed);
    }

    private static boolean isHand(ItemDisplayContext displayContext) {
        return displayContext == ItemDisplayContext.THIRD_PERSON_LEFT_HAND || displayContext == ItemDisplayContext.THIRD_PERSON_RIGHT_HAND || displayContext.firstPerson();
    }

    private static BakedQuad bakedSpriteQuads(TextureAtlasSprite sprite) {
        MutableQuad mutableQuad = new MutableQuad();
        mutableQuad.setSprite(new Material.Baked(sprite, false), sprite.transparency());
        mutableQuad.setCubeFaceFromSpriteCoords(Direction.NORTH, 0, 0, 1, 1, 0);
        mutableQuad.bakeUvsFromPosition();
        return mutableQuad.toBakedQuad();
    }

    public record Unbaked(Identifier defaultModel) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            Identifier.CODEC.fieldOf("default").forGetter(Unbaked::defaultModel)
        ).apply(i, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            CuboidItemModelWrapper.Unbaked defaultModel = new CuboidItemModelWrapper.Unbaked(this.defaultModel, Optional.empty(), List.of());
            ModelBaker baker = context.blockModelBaker();
            ResolvedModel resolvedModel = baker.getModel(this.defaultModel);
            TextureSlots textureSlots = resolvedModel.getTopTextureSlots();
            ModelRenderProperties properties = ModelRenderProperties.fromResolvedModel(baker, resolvedModel, textureSlots);
            return new SpellItemModelV2(defaultModel.bake(context, transformation), properties, context.sprites());
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            resolver.markDependency(defaultModel);
        }
    }
}
