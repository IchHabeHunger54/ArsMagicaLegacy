package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.block.altar.SpellcraftingAltarBlockEntity;
import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;

public interface AMBlockEntities {
    DeferredHolder<BlockEntityType<?>, BlockEntityType<InscriptionTableBlockEntity>>   INSCRIPTION_TABLE   = register("inscription_table",   InscriptionTableBlockEntity::new,   AMBlocks.INSCRIPTION_TABLE);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<SpellcraftingAltarBlockEntity>> SPELLCRAFTING_ALTAR = register("spellcrafting_altar", SpellcraftingAltarBlockEntity::new, AMBlocks.SPELLCRAFTING_ALTAR_CORE);

    @SuppressWarnings("DataFlowIssue")
    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... blocks) {
        return AMRegistries.BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new)).build(null));
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
