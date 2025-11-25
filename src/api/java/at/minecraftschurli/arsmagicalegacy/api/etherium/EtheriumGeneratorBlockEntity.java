package at.minecraftschurli.arsmagicalegacy.api.etherium;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

/**
 * Simple implementation of an etherium generator, as used by the three generators in the base mod (Obelisk, Celestial Prism and Black Aurem).
 */
public abstract class EtheriumGeneratorBlockEntity extends BlockEntity implements EtheriumHandler {
    private static final String ETHERIUM_KEY = "etherium";
    protected final ResourceKey<EtheriumType> etheriumType;
    protected int etherium = 0;

    /**
     * @param type         The registered {@link BlockEntityType}.
     * @param pos          The {@link BlockPos}, as supplied by {@link net.minecraft.world.level.block.EntityBlock#newBlockEntity(BlockPos, BlockState)}.
     * @param state        The {@link BlockState}, as supplied by {@link net.minecraft.world.level.block.EntityBlock#newBlockEntity(BlockPos, BlockState)}.
     * @param etheriumType The {@link EtheriumType} of the generator.
     */
    public EtheriumGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ResourceKey<EtheriumType> etheriumType) {
        super(type, pos, state);
        this.etheriumType = etheriumType;
    }

    /**
     * Ticks the block entity.
     *
     * @param level The {@link Level}.
     * @param pos   The {@link BlockPos}.
     * @param state The {@link BlockState}.
     */
    public abstract void tick(Level level, BlockPos pos, BlockState state);

    /**
     * @return The max etherium amount that can be stored. Usually resolved from a config value or similar.
     */
    public abstract int getMaxAmount();

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        etherium = tag.getInt(ETHERIUM_KEY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(ETHERIUM_KEY, etherium);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public List<Holder<EtheriumType>> getEtheriumTypes() {
        return List.of(AMRegistries.etheriumTypes(level.registryAccess()).getHolderOrThrow(etheriumType));
    }

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return type.is(etheriumType) ? etherium : 0;
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return type.is(etheriumType) ? getMaxAmount() : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        if (type.is(etheriumType)) {
            etherium = amount;
            setChanged();
        }
    }

    @Override
    public int addAmount(Holder<EtheriumType> type, int amount) {
        return amount;
    }

    @Override
    public int subtractAmount(Holder<EtheriumType> type, int amount) {
        if (!type.is(etheriumType)) return amount;
        int min = Math.min(etherium, amount);
        etherium -= min;
        setChanged();
        return amount - min;
    }
}
