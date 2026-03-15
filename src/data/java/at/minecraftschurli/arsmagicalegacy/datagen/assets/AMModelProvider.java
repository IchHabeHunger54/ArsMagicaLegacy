package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.arsmagicalegacy.block.SpellRuneBlock;
import at.minecraftschurli.arsmagicalegacy.block.WizardsChalkBlock;
import at.minecraftschurli.arsmagicalegacy.client.model.AltarCoreModel;
import at.minecraftschurli.arsmagicalegacy.client.model.item.CrystalPhylacteryItemTintSource;
import at.minecraftschurli.arsmagicalegacy.client.model.item.CrystalPhylacteryRangeSelectItemModelProperty;
import at.minecraftschurli.arsmagicalegacy.client.model.item.CrystalWrenchActiveItemModelProperty;
import at.minecraftschurli.arsmagicalegacy.client.model.item.EtheriumTypeItemTintSource;
import at.minecraftschurli.arsmagicalegacy.client.model.item.DataComponentOverridesModel;
import at.minecraftschurli.arsmagicalegacy.client.model.item.SpellItemModel;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMFluids;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.mods.easydatagenlib.AbstractModelProvider;
import at.minecraftschurli.mods.easydatagenlib.util.BlockModelDatagenUtil;
import at.minecraftschurli.mods.easydatagenlib.util.WrappingCustomBlockStateModelBuilder;
import net.minecraft.client.color.item.Dye;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplate;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.renderer.block.dispatch.BlockStateModel;
import net.minecraft.client.renderer.block.dispatch.SingleVariant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.RangeSelectItemModel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperties;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.block.CustomUnbakedBlockStateModel;
import net.neoforged.neoforge.client.model.generators.blockstate.CustomBlockStateModelBuilder;
import net.neoforged.neoforge.client.model.generators.loaders.ObjModelBuilder;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import net.neoforged.neoforge.registries.DeferredItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public final class AMModelProvider extends AbstractModelProvider {
    private static final TextureSlot TEX = TextureSlot.create("tex");
    private static final ModelTemplate CELESTIAL_PRISM_TEMPLATE = ModelTemplates.create(TextureSlot.PARTICLE, TEX)
        .extend()
        .customLoader(ObjModelBuilder::new, b -> b
            .modelLocation(ArsMagicaApi.id("models/block/celestial_prism.obj"))
            .emissiveAmbient(false)
            .automaticCulling(false)
            .shadeQuads(false))
        .build();
    private static final ModelTemplate OBELISK_TEMPLATE = ModelTemplates.create(TextureSlot.PARTICLE, TEX)
        .extend()
        .customLoader(ObjModelBuilder::new, b -> b
            .modelLocation(ArsMagicaApi.id("models/block/obelisk.obj"))
            .emissiveAmbient(false)
            .automaticCulling(false)
            .shadeQuads(false))
        .build();
    private static final ModelTemplate PARTICLE_ONLY_TEMPLATE = ModelTemplates.PARTICLE_ONLY.extend().suffix("_particle").build();
    private static final Map<Direction, ModelTemplate> SPELL_RUNE_TEMPLATE = Util.makeEnumMap(
        Direction.class,
        direction -> new ModelTemplate(Optional.empty(), Optional.of("_" + direction.getName()), TextureSlot.TEXTURE)
            .extend()
            .element(element -> {
                AABB aabb = SpellRuneBlock.SHAPES.get(direction).bounds();
                element
                    .from((float) aabb.minX * 16, (float) aabb.minY * 16, (float) aabb.minZ * 16)
                    .to((float) aabb.maxX * 16, (float) aabb.maxY * 16, (float) aabb.maxZ * 16)
                    .face(direction.getOpposite(), face -> face.texture(TextureSlot.TEXTURE));
            })
            .build());
    private static final ModelTemplate ALTAR_CORE_TEMPLATE = ModelTemplates.create();

    public AMModelProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        registerBlockModels(blockModels);
        registerItemModels(itemModels);
    }

    private void registerBlockModels(BlockModelGenerators blockModels) {
        BlockFamily blockFamily = AMBlocks.WITCHWOOD_BLOCK_FAMILY.get();
        blockModels.family(blockFamily.getBaseBlock()).generateFor(blockFamily);
        blockModels.createNonTemplateModelBlock(AMBlocks.SPELL_LIGHT.get(), Blocks.AIR);
        blockModels.woodProvider(AMBlocks.WITCHWOOD_LOG.get()).logWithHorizontal(AMBlocks.WITCHWOOD_LOG.get()).wood(AMBlocks.WITCHWOOD_WOOD.get());
        blockModels.woodProvider(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()).logWithHorizontal(AMBlocks.STRIPPED_WITCHWOOD_LOG.get()).wood(AMBlocks.STRIPPED_WITCHWOOD_WOOD.get());
        blockModels.createHangingSign(AMBlocks.STRIPPED_WITCHWOOD_LOG.get(), AMBlocks.WITCHWOOD_HANGING_SIGN.get(), AMBlocks.WITCHWOOD_WALL_HANGING_SIGN.get());
        blockModels.createPlantWithDefaultItem(AMBlocks.WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createTrivialCube(AMBlocks.WITCHWOOD_LEAVES.get());
        blockModels.createPlantWithDefaultItem(AMBlocks.WITCHWOOD_SAPLING.get(), AMBlocks.POTTED_WITCHWOOD_SAPLING.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.AUM.get(), AMBlocks.POTTED_AUM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.CERUBLOSSOM.get(), AMBlocks.POTTED_CERUBLOSSOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.DESERT_NOVA.get(), AMBlocks.POTTED_DESERT_NOVA.get(), BlockModelGenerators.PlantType.EMISSIVE_NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.TARMA_ROOT.get(), AMBlocks.POTTED_TARMA_ROOT.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createPlantWithDefaultItem(AMBlocks.WAKEBLOOM.get(), AMBlocks.POTTED_WAKEBLOOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModels.createTrivialCube(AMBlocks.CHIMERITE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_CHIMERITE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.CHIMERITE_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.TOPAZ_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_TOPAZ_ORE.get());
        blockModels.createTrivialCube(AMBlocks.TOPAZ_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.VINTEUM_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_VINTEUM_ORE.get());
        blockModels.createTrivialCube(AMBlocks.VINTEUM_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.MOONSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.DEEPSLATE_MOONSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.MOONSTONE_BLOCK.get());
        blockModels.createTrivialCube(AMBlocks.SUNSTONE_ORE.get());
        blockModels.createTrivialCube(AMBlocks.SUNSTONE_BLOCK.get());
        blockModels.createPassiveRail(AMBlocks.REDSTONE_INLAY.get());
        blockModels.createPassiveRail(AMBlocks.IRON_INLAY.get());
        blockModels.createPassiveRail(AMBlocks.GOLD_INLAY.get());
        blockModels.createNormalTorch(AMBlocks.VINTEUM_TORCH.get(), AMBlocks.VINTEUM_WALL_TORCH.get());
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.WIZARDS_CHALK)
            .withFlatItemModel()
            .withModelDispatch(WizardsChalkBlock.VARIANT, i -> ModelTemplates.RAIL_FLAT.createWithSuffix(AMBlocks.WIZARDS_CHALK.get(), "_" + i, TextureMapping.rail(TextureMapping.getBlockTexture(AMBlocks.WIZARDS_CHALK.get(), "_" + i)), blockModels.modelOutput))
            .withHorizontalRotation()
            .build();
        blockModels.createNonTemplateHorizontalBlock(AMBlocks.OCCULUS.get());
        blockModels.createTrivialCube(AMBlocks.MAGIC_WALL.get());
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.CELESTIAL_PRISM)
            .withModelDispatch(CelestialPrismBlock.PART, part -> switch (part) {
                case LOWER -> CELESTIAL_PRISM_TEMPLATE;
                case UPPER -> PARTICLE_ONLY_TEMPLATE;
            }, TextureMapping.particle(AMBlocks.CELESTIAL_PRISM.get())
                .put(TEX, TextureMapping.getBlockTexture(AMBlocks.CELESTIAL_PRISM.get())))
            .withHorizontalRotation()
            .build();
        Identifier obeliskParticleOnly = PARTICLE_ONLY_TEMPLATE.create(
            AMBlocks.OBELISK.get(),
            TextureMapping.particle(Blocks.STONE_BRICKS),
            blockModels.modelOutput);
        Identifier obeliskLit = blockModels.createSuffixedVariant(
            AMBlocks.OBELISK.get(),
            "_lit",
            OBELISK_TEMPLATE,
            mat -> TextureMapping.particle(Blocks.STONE_BRICKS).put(TEX, mat));
        Identifier obeliskUnlit = OBELISK_TEMPLATE.create(
            AMBlocks.OBELISK.get(),
            TextureMapping.particle(Blocks.STONE_BRICKS).put(TEX, TextureMapping.getBlockTexture(AMBlocks.OBELISK.get())),
            blockModels.modelOutput);
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.OBELISK)
            .withModelDispatch(
                ObeliskBlock.PART,
                ObeliskBlock.LIT,
                (part, lit) -> part != ObeliskBlock.Part.LOWER ? obeliskParticleOnly : lit ? obeliskLit : obeliskUnlit)
            .withHorizontalRotation()
            .build();
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.ALTAR_CORE)
            .withModelDispatch(
                BlockModelGenerators.createBooleanModelDispatch(
                    AltarCoreBlock.FORMED,
                    MultiVariant.of(new Builder(BlockModelGenerators.plainVariant(ModelLocationUtils.getModelLocation(AMBlocks.ALTAR_CORE.get(), "_overlay")))),
                    BlockModelGenerators.plainVariant(ModelTemplates.CUBE_ALL.create(AMBlocks.ALTAR_CORE.get(), TextureMapping.cube(AMBlocks.ALTAR_CORE.get()), blockModels.modelOutput))))
            .build();
        blockModels.createParticleOnlyBlock(AMBlocks.BLACK_AUREM.get());
        blockModels.registerSimpleFlatItemModel(AMBlocks.BLACK_AUREM.get());
        blockModels.createNonTemplateModelBlock(AMBlocks.LIQUID_ETHERIUM.get());
        blockModels.blockStateOutput.accept(
            BlockModelGenerators.createSimpleBlock(
                AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(),
                BlockModelGenerators.plainVariant(
                    ModelTemplates.CAULDRON_FULL.create(
                        AMBlocks.LIQUID_ETHERIUM_CAULDRON.get(),
                        TextureMapping.cauldron(TextureMapping.getBlockTexture(AMBlocks.LIQUID_ETHERIUM.get(), "_still")),
                        blockModels.modelOutput))));
        BlockModelDatagenUtil.builder(blockModels, AMBlocks.SPELL_RUNE)
            .withModelDispatch(
                SpellRuneBlock.FACING,
                SPELL_RUNE_TEMPLATE::get,
                TextureMapping.defaultTexture(AMBlocks.SPELL_RUNE.get()))
            .build();
    }

    private void registerItemModels(ItemModelGenerators itemModels) {
        ModelTemplates.FLAT_ITEM.create(ArsMagicaApi.id("arcane_compendium"), TextureMapping.layer0(new Material(ArsMagicaApi.id("item/arcane_compendium"))), itemModels.modelOutput);
        itemWithVariants(
            itemModels,
            AMItems.SPELL,
            new SpellItemModel.Unbaked(
                ItemModelUtils.plainModel(
                    itemModels.createFlatItemModel(
                        AMItems.SPELL.get(),
                        ModelTemplates.FLAT_ITEM))),
            AMMagic.AFFINITIES_WITH_NONE);
        itemModels.generateFlatItem(AMItems.SPELL_RECIPE.get(), Items.WRITTEN_BOOK, ModelTemplates.FLAT_ITEM);
        itemModels.itemModelOutput.accept(AMItems.ETHERIUM_PLACEHOLDER.get(), ItemModelUtils.tintedModel(itemModels.createFlatItemModel(AMItems.ETHERIUM_PLACEHOLDER.get(), ModelTemplates.FLAT_ITEM), new EtheriumTypeItemTintSource()));
        itemModels.itemModelOutput.accept(AMItems.LIQUID_ETHERIUM_BUCKET.get(), new DynamicFluidContainerModel.Unbaked(
            new DynamicFluidContainerModel.Textures(
                Optional.of(new Material(Identifier.withDefaultNamespace("item/bucket"))),
                Optional.of(new Material(Identifier.withDefaultNamespace("item/bucket"))),
                Optional.of(new Material(Identifier.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid"))),
                Optional.of(new Material(Identifier.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid_cover")))
            ),
            AMFluids.LIQUID_ETHERIUM.get(),
            false,
            true,
            true
        ));
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1);
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2);
        basicItem(itemModels, AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3);
        itemModels.generateBooleanDispatch(
            AMItems.CRYSTAL_WRENCH.get(),
            new CrystalWrenchActiveItemModelProperty(),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.CRYSTAL_WRENCH.get(), "_active", ModelTemplates.FLAT_ITEM)),
            ItemModelUtils.plainModel(itemModels.createFlatItemModel(AMItems.CRYSTAL_WRENCH.get(), ModelTemplates.FLAT_ITEM))
        );
        basicItem(itemModels, AMItems.SPELL_PARCHMENT);
        itemModels.itemModelOutput.accept(
            AMItems.SPELL_BOOK.get(),
            new SpellItemModel.Unbaked(
                ItemModelUtils.tintedModel(
                    itemModels.generateLayeredItem(
                        AMItems.SPELL_BOOK.get(),
                        TextureMapping.getItemTexture(AMItems.SPELL_BOOK.get()),
                        TextureMapping.getItemTexture(AMItems.SPELL_BOOK.get(), "_overlay")),
                    ItemModelGenerators.BLANK_LAYER,
                    new Dye(0xff000000))));
        basicItem(itemModels, AMItems.MAGITECH_GOGGLES);
        basicItem(itemModels, AMItems.MAGE_HELMET);
        basicItem(itemModels, AMItems.MAGE_CHESTPLATE);
        basicItem(itemModels, AMItems.MAGE_LEGGINGS);
        basicItem(itemModels, AMItems.MAGE_BOOTS);
        basicItem(itemModels, AMItems.BATTLEMAGE_HELMET);
        basicItem(itemModels, AMItems.BATTLEMAGE_CHESTPLATE);
        basicItem(itemModels, AMItems.BATTLEMAGE_LEGGINGS);
        basicItem(itemModels, AMItems.BATTLEMAGE_BOOTS);
        basicItem(itemModels, AMItems.MANA_CAKE);
        basicItem(itemModels, AMItems.MANA_MARTINI);
        itemWithVariants(
            itemModels,
            AMItems.INFINITY_ORB,
            new DataComponentOverridesModel.Unbaked<>(
                AMDataComponents.SKILL_POINT.get(),
                ItemModelUtils.plainModel(
                    itemModels.createFlatItemModel(
                        AMItems.INFINITY_ORB.get(),
                        ModelTemplates.FLAT_ITEM))),
            AMMagic.SKILL_POINTS);
        itemWithVariants(
            itemModels,
            AMItems.AFFINITY_ESSENCE,
            new DataComponentOverridesModel.Unbaked<>(
                AMDataComponents.AFFINITY.get(),
                ItemModelUtils.plainModel(
                    itemModels.createFlatItemModel(
                        AMItems.AFFINITY_ESSENCE.get(),
                        ModelTemplates.FLAT_ITEM))),
            AMMagic.AFFINITIES);
        itemWithVariants(
            itemModels,
            AMItems.AFFINITY_TOME,
            new DataComponentOverridesModel.Unbaked<>(
                AMDataComponents.AFFINITY.get(),
                ItemModelUtils.plainModel(
                    itemModels.createFlatItemModel(
                        AMItems.AFFINITY_TOME.get(),
                        ModelTemplates.FLAT_ITEM))),
            AMMagic.AFFINITIES_WITH_NONE);
        basicItem(itemModels, AMItems.BLANK_RUNE);
        basicItem(itemModels, AMItems.WHITE_RUNE);
        basicItem(itemModels, AMItems.ORANGE_RUNE);
        basicItem(itemModels, AMItems.MAGENTA_RUNE);
        basicItem(itemModels, AMItems.LIGHT_BLUE_RUNE);
        basicItem(itemModels, AMItems.YELLOW_RUNE);
        basicItem(itemModels, AMItems.LIME_RUNE);
        basicItem(itemModels, AMItems.PINK_RUNE);
        basicItem(itemModels, AMItems.GRAY_RUNE);
        basicItem(itemModels, AMItems.LIGHT_GRAY_RUNE);
        basicItem(itemModels, AMItems.CYAN_RUNE);
        basicItem(itemModels, AMItems.PURPLE_RUNE);
        basicItem(itemModels, AMItems.BLUE_RUNE);
        basicItem(itemModels, AMItems.BROWN_RUNE);
        basicItem(itemModels, AMItems.GREEN_RUNE);
        basicItem(itemModels, AMItems.RED_RUNE);
        basicItem(itemModels, AMItems.BLACK_RUNE);
        basicItem(itemModels, AMItems.RUNE_BAG);
        basicItem(itemModels, AMItems.CHIMERITE);
        basicItem(itemModels, AMItems.TOPAZ);
        basicItem(itemModels, AMItems.VINTEUM_DUST);
        basicItem(itemModels, AMItems.MOONSTONE);
        basicItem(itemModels, AMItems.SUNSTONE);
        basicItem(itemModels, AMItems.ARCANE_COMPOUND);
        basicItem(itemModels, AMItems.ARCANE_ASH);
        basicItem(itemModels, AMItems.PURIFIED_VINTEUM_DUST);
        basicItem(itemModels, AMItems.WITCHWOOD_DOOR);
        basicItem(itemModels, AMItems.WITCHWOOD_SIGN);
        basicItem(itemModels, AMItems.WITCHWOOD_HANGING_SIGN);
        basicItem(itemModels, AMItems.WITCHWOOD_BOAT);
        basicItem(itemModels, AMItems.WITCHWOOD_CHEST_BOAT);
        basicItem(itemModels, AMItems.DRYAD_SPAWN_EGG);
        basicItem(itemModels, AMItems.MANA_CREEPER_SPAWN_EGG);
        {
            var item = AMItems.CRYSTAL_PHYLACTERY.get();
            Material baseTexture = TextureMapping.getItemTexture(item);
            Identifier modelLocation = ModelLocationUtils.getModelLocation(item);
            List<RangeSelectItemModel.Entry> entries = new ArrayList<>();
            for (int i = 0; i < 8; i++) {
                Material fillTexture = TextureMapping.getItemTexture(item, "_fill_" + i);
                Identifier modelId = itemModels.generateLayeredItem(modelLocation.withSuffix("_fill_" + i), baseTexture, fillTexture);
                var model = ItemModelUtils.tintedModel(modelId, ItemModelGenerators.BLANK_LAYER, CrystalPhylacteryItemTintSource.INSTANCE);
                entries.add(new RangeSelectItemModel.Entry((i + 1) / 8f, model));
            }
            var fallback = ItemModelUtils.plainModel(itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM));
            itemModels.itemModelOutput.accept(item, ItemModelUtils.rangeSelect(new CrystalPhylacteryRangeSelectItemModelProperty(), fallback, entries));
        }
    }

    /**
     * Adds a flat item model.
     *
     * @param itemModels The item model generators.
     * @param item       The item to add the model for.
     */
    private void basicItem(ItemModelGenerators itemModels, DeferredItem<?> item) {
        itemModels.generateFlatItem(item.get(), ModelTemplates.FLAT_ITEM);
    }

    /**
     * Adds a flat item model and flat variant item models for an item with variants.
     *
     * @param item     The item to add the models for.
     * @param variants The variants to add models for.
     */
    private void itemWithVariants(ItemModelGenerators itemModels, DeferredItem<?> item, ItemModel.Unbaked model, List<? extends ResourceKey<?>> variants) {
        itemModels.itemModelOutput.accept(item.get(), model);
        for (ResourceKey<?> variant : variants) {
            Identifier identifier = variant.identifier().withPrefix(item.getId().getPath() + "_");
            ModelTemplates.FLAT_ITEM.create(
                identifier,
                TextureMapping.layer0(new Material(identifier.withPrefix("item/"))),
                itemModels.modelOutput);
        }
    }
    
    private static class Builder extends WrappingCustomBlockStateModelBuilder {
        private Builder(MultiVariant wrapped) {
            super(wrapped);
        }

        @Override
        public CustomBlockStateModelBuilder with(VariantMutator variantMutator) {
            return new Builder(wrapped.with(variantMutator));
        }

        @Override
        public CustomUnbakedBlockStateModel toUnbaked() {
            BlockStateModel.Unbaked unbaked = wrapped.toUnbaked();
            if (unbaked instanceof AltarCoreModel.Unbaked altarCore) {
                return altarCore;
            }
            if (unbaked instanceof SingleVariant.Unbaked singleUnbaked) {
                return new AltarCoreModel.Unbaked(singleUnbaked);
            }
            throw new IllegalStateException("Unexpected unbaked variant: " + unbaked);
        }
    }
}
