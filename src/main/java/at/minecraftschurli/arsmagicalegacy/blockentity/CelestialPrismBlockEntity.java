package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CelestialPrismBlockEntity extends BlockEntity implements EtheriumHandler {
    private static final String ETHERIUM_KEY = "etherium";
    private static final String TIME_KEY = "time";
    private int etherium = 0;
    private int time = 0;

    public CelestialPrismBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntities.CELESTIAL_PRISM.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (etherium >= getMaxAmount() || !level.isDay() || !level.canSeeSky(pos.above())) return;
        time--;
        if (time < 0) {
            time = 6;
            etherium++;
        }
        setChanged();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        etherium = tag.getInt(ETHERIUM_KEY);
        time = tag.getInt(TIME_KEY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(ETHERIUM_KEY, etherium);
        tag.putInt(TIME_KEY, time);
    }

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return type.is(AMEtheriumTypes.LIGHT) ? etherium : 0;
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return type.is(AMEtheriumTypes.LIGHT) ? getMaxAmount() : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        if (type.is(AMEtheriumTypes.LIGHT)) {
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
        if (!type.is(AMEtheriumTypes.LIGHT)) return amount;
        int min = Math.min(etherium, amount);
        etherium -= min;
        setChanged();
        return amount - min;
    }

    private int getMaxAmount() {
        return AMServerConfig.CELESTIAL_PRISM_MAX_ETHERIUM.get();
    }
}
