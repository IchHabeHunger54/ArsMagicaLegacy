package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class InscriptionTableBlockEntity extends BlockEntity {
    public InscriptionTableBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pos, state);
    }
}
