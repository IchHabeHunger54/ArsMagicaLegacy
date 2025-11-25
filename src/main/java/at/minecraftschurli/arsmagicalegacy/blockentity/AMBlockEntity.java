package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class AMBlockEntity<T> extends BlockEntity {
    private static final String DATA_KEY = ArsMagicaApi.modLoc("data").toString();
    private final Codec<T> codec;

    public AMBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, Codec<T> codec) {
        super(type, pos, state);
        this.codec = codec;
    }

    public abstract void fromData(T data);

    public abstract T toData();

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        fromData(codec.decode(NbtOps.INSTANCE, tag.get(DATA_KEY)).map(Pair::getFirst).getOrThrow());
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(DATA_KEY, codec.encodeStart(NbtOps.INSTANCE, toData()).getOrThrow());
    }
}
