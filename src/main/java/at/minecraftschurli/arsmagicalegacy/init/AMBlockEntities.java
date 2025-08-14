package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.block.sign.WitchwoodHangingSignBlockEntity;
import at.minecraftschurli.arsmagicalegacy.block.sign.WitchwoodSignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;

public interface AMBlockEntities {
    DeferredHolder<BlockEntityType<?>, BlockEntityType<WitchwoodHangingSignBlockEntity>> WITCHWOOD_HANGING_SIGN = register("witchwood_hanging_sign", WitchwoodHangingSignBlockEntity::new, AMBlocks.WITCHWOOD_HANGING_SIGN, AMBlocks.WITCHWOOD_WALL_HANGING_SIGN);
    DeferredHolder<BlockEntityType<?>, BlockEntityType<WitchwoodSignBlockEntity>>        WITCHWOOD_SIGN         = register("witchwood_sign",         WitchwoodSignBlockEntity::new,        AMBlocks.WITCHWOOD_SIGN, AMBlocks.WITCHWOOD_WALL_SIGN);

    private static <T extends BlockEntity> DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> register(String name, BlockEntityType.BlockEntitySupplier<T> factory, DeferredBlock<?>... blocks) {
        return AMRegistries.BLOCK_ENTITIES.register(name, () -> BlockEntityType.Builder.of(factory, Arrays.stream(blocks).map(DeferredBlock::get).toArray(Block[]::new)).build(null));
    }

    static void init() {
    }
}
