package at.minecraftschurli.mods.arsmagicalegacy.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;

public class DispenseBucketBehavior extends DefaultDispenseItemBehavior {
    public static final DispenseBucketBehavior INSTANCE = new DispenseBucketBehavior();
    private static final DefaultDispenseItemBehavior FALLBACK = new DefaultDispenseItemBehavior();

    private DispenseBucketBehavior() {
    }

    @Override
    public ItemStack execute(BlockSource block, ItemStack stack) {
        DispensibleContainerItem item = (DispensibleContainerItem) stack.getItem();
        BlockPos pos = block.pos().relative(block.state().getValue(DispenserBlock.FACING));
        Level level = block.level();
        if (!item.emptyContents(null, level, pos, null, stack)) return FALLBACK.dispense(block, stack);
        item.checkExtraContent(null, level, stack, pos);
        return consumeWithRemainder(block, stack, new ItemStack(Items.BUCKET));
    }
}
