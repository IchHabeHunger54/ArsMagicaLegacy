package at.minecraftschurli.arsmagicalegacy.block;

import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.util.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LiquidEtheriumCauldronBlock extends AbstractCauldronBlock {
    private static final MapCodec<LiquidEtheriumCauldronBlock> CODEC = simpleCodec(LiquidEtheriumCauldronBlock::new);

    public LiquidEtheriumCauldronBlock(Properties properties) {
        super(properties, Util.make(CauldronInteraction.newInteractionMap("liquid_etherium"), map -> {
            map.map().put(Items.BUCKET, LiquidEtheriumCauldronBlock::fillBucket);
            CauldronInteraction.addDefaultInteractions(map.map());
        }));
    }

    @Override
    protected MapCodec<? extends AbstractCauldronBlock> codec() {
        return CODEC;
    }

    @Override
    public boolean isFull(BlockState state) {
        return true;
    }

    @Override
    protected int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return 3;
    }

    @Override
    protected double getContentHeight(BlockState state) {
        return 0.9375;
    }

    public static ItemInteractionResult fillBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack emptyStack) {
        return CauldronInteraction.fillBucket(state, level, pos, player, hand, emptyStack, AMItems.LIQUID_ETHERIUM_BUCKET.toStack(), $ -> true, SoundEvents.BUCKET_FILL);
    }

    public static ItemInteractionResult emptyBucket(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, ItemStack filledStack) {
        return CauldronInteraction.emptyBucket(level, pos, player, hand, filledStack, AMBlocks.LIQUID_ETHERIUM_CAULDRON.get().defaultBlockState(), SoundEvents.BUCKET_EMPTY);
    }
}
