package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumConsumerBlockEntity;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandlerBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public class CrystalWrenchItem extends Item {
    public static final ResourceLocation ACTIVE = ArsMagicaApi.modLoc("crystal_wrench_active");

    public CrystalWrenchItem(Properties properties) {
        super(properties);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack stack = context.getItemInHand();
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        BlockEntity blockEntity = state.getBlock() instanceof EtheriumHandlerBlock block ? block.getBlockEntity(level, pos, state) : level.getBlockEntity(pos);
        if (state.is(AMTags.Blocks.ETHERIUM_CONSUMERS) && blockEntity instanceof EtheriumConsumerBlockEntity consumer && stack.has(AMDataComponents.STORED_POSITIONS)) {
            List<BlockPos> list = stack.get(AMDataComponents.STORED_POSITIONS)
                .stream()
                .filter(e -> e.dimension() == level.dimension())
                .map(GlobalPos::pos)
                .toList();
            if (!list.isEmpty()) {
                if (consumer.getBoundPositions().containsAll(list)) {
                    list.forEach(consumer::removePosition);
                } else {
                    list.forEach(consumer::addPosition);
                }
                stack.set(AMDataComponents.STORED_POSITIONS, List.of());
                return InteractionResult.SUCCESS;
            }
        }
        if (state.is(AMTags.Blocks.ETHERIUM_PROVIDERS)) {
            List<GlobalPos> list = stack.has(AMDataComponents.STORED_POSITIONS) ? new ArrayList<>(stack.get(AMDataComponents.STORED_POSITIONS)) : new ArrayList<>();
            list.add(new GlobalPos(level.dimension(), blockEntity.getBlockPos()));
            stack.set(AMDataComponents.STORED_POSITIONS, list);
            return InteractionResult.SUCCESS;
        }
        return super.useOn(context);
    }
}
