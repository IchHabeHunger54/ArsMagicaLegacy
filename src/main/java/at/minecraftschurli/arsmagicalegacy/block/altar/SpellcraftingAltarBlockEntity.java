package at.minecraftschurli.arsmagicalegacy.block.altar;

import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class SpellcraftingAltarBlockEntity extends BlockEntity {
    public SpellcraftingAltarBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntities.SPELLCRAFTING_ALTAR.get(), pos, blockState);
    }
}
