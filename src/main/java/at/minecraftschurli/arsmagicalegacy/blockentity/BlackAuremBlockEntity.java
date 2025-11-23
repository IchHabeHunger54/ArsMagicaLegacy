package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BlackAuremBlockEntity extends EtheriumGeneratorBlockEntity {
    public BlackAuremBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.BLACK_AUREM.get(), pos, state, AMEtheriumTypes.DARK);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {

    }

    @Override
    public int getMaxAmount() {
        return 0;
    }
}
