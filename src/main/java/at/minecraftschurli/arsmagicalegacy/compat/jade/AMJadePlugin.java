package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.arsmagicalegacy.blockentity.ObeliskBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public final class AMJadePlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerBlockDataProvider(EtheriumComponentProvider.INSTANCE, ObeliskBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(EtheriumComponentProvider.INSTANCE, ObeliskBlock.class);
        registration.addRayTraceCallback(((hitResult, accessor, original) -> {
            if (!(accessor instanceof BlockAccessor blockAccessor) || hitResult.getType() != HitResult.Type.BLOCK || !(hitResult instanceof BlockHitResult bhr)) return accessor;
            BlockState state = blockAccessor.getBlockState();
            if (state.getBlock() instanceof ObeliskBlock) {
                int offset = switch (state.getValue(ObeliskBlock.PART)) {
                    case UPPER -> -2;
                    case MIDDLE -> -1;
                    case LOWER -> 0;
                };
                BlockPos newPos = bhr.getBlockPos().offset(0, offset, 0);
                return registration.blockAccessor()
                    .from(blockAccessor)
                    .hit(new BlockHitResult(bhr.getLocation(), bhr.getDirection(), newPos, bhr.isInside()))
                    .blockState(state.setValue(ObeliskBlock.PART, ObeliskBlock.Part.LOWER))
                    .blockEntity(blockAccessor.getLevel().getBlockEntity(newPos))
                    .build();
            }
            return accessor;
        }));
    }
}
