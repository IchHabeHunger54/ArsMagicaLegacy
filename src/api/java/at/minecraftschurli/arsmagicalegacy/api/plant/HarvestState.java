package at.minecraftschurli.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.level.block.state.BlockState;

public record HarvestState(BlockState from, BlockState to) {
    public static final Codec<HarvestState> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        BlockState.CODEC.fieldOf("from").forGetter(HarvestState::from),
        BlockState.CODEC.fieldOf("to").forGetter(HarvestState::to)
    ).apply(inst, HarvestState::new));
}
