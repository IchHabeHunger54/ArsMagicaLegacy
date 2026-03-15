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
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemModels;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.ItemOwner;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.model.quad.MutableQuad;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

import java.util.IdentityHashMap;
import java.util.Map;

public final class SpellItemModel implements ItemModel {
    private final ItemModel defaultModel;
    private final SpriteGetter sprites;
    private final Map<TextureAtlasSprite, BakedQuad> spriteQuads = new IdentityHashMap<>();

    public SpellItemModel(ItemModel defaultModel, SpriteGetter sprites) {
        this.defaultModel = defaultModel;
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
            layer.prepareQuadList().add(spriteQuads.computeIfAbsent(icon.get(), SpellItemModel::bakedSpriteQuads));
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

    public record Unbaked(ItemModel.Unbaked defaultModel) implements ItemModel.Unbaked {
        public static final MapCodec<Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
            ItemModels.CODEC.fieldOf("default").forGetter(Unbaked::defaultModel)
        ).apply(i, Unbaked::new));

        @Override
        public MapCodec<Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public ItemModel bake(BakingContext context, Matrix4fc transformation) {
            ItemModel baked = defaultModel.bake(context, transformation);
            return new SpellItemModel(baked, context.sprites());
        }

        @Override
        public void resolveDependencies(Resolver resolver) {
            defaultModel.resolveDependencies(resolver);
        }
    }
}
