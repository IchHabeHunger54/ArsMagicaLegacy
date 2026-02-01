package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import at.minecraftschurli.arsmagicalegacy.blockentity.BlackAuremBlockEntity;
import at.minecraftschurli.arsmagicalegacy.blockentity.CelestialPrismBlockEntity;
import at.minecraftschurli.arsmagicalegacy.blockentity.InscriptionTableBlockEntity;
import at.minecraftschurli.arsmagicalegacy.blockentity.ObeliskBlockEntity;
import at.minecraftschurli.arsmagicalegacy.blockentity.SpellRuneBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Arrays;

public interface AMBlockEntities {
    DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, ArsMagicaApi.MOD_ID);
    // @formatter:off
    DeferredHolder<BlockEntityType<?>, BlockEntityType<InscriptionTableBlockEntity>> INSCRIPTION_TABLE = register("inscription_table", InscriptionTableBlockEntity::new, AMBlocks.INSCRIPTION_TABLE);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<AltarCoreBlockEntity>>        ALTAR_CORE        = register("altar_core",        AltarCoreBlockEntity::new,        AMBlocks.ALTAR_CORE);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<ObeliskBlockEntity>>          OBELISK           = register("obelisk",           ObeliskBlockEntity::new,          AMBlocks.OBELISK);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<CelestialPrismBlockEntity>>   CELESTIAL_PRISM   = register("celestial_prism",   CelestialPrismBlockEntity::new,   AMBlocks.CELESTIAL_PRISM);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<BlackAuremBlockEntity>>       BLACK_AUREM       = register("black_aurem",       BlackAuremBlockEntity::new,       AMBlocks.BLACK_AUREM);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<SpellRuneBlockEntity>>        SPELL_RUNE        = register("spell_rune",        SpellRuneBlockEntity::new,        AMBlocks.SPELL_RUNE);
    // @formatter:on

    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... blocks) {
        return BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new)).build(null));
    }
}
