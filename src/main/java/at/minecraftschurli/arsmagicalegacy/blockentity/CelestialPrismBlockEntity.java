package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.arsmagicalegacy.block.CelestialPrismBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class CelestialPrismBlockEntity extends EtheriumGeneratorBlockEntity {
    private static final String TIME_KEY = "time";
    private int time = 0;

    public CelestialPrismBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.CELESTIAL_PRISM.get(), pos, state, AMEtheriumTypes.LIGHT);
    }

    @Override
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
    public int getMaxAmount() {
        return AMServerConfig.CELESTIAL_PRISM_MAX_ETHERIUM.get();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        time = tag.getInt(TIME_KEY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putInt(TIME_KEY, time);
    }

    @Override
    @Nullable
    public AABB getOutline(Level level, BlockPos pos, BlockState state) {
        return state.getValue(CelestialPrismBlock.PART) == CelestialPrismBlock.Part.LOWER ? new AABB(Vec3.ZERO, new Vec3(1, 2, 1)) : null;
    }

    @Override
    public int getOutlineColor(Level level, BlockPos pos, BlockState state) {
        return AMRegistries.etheriumTypes(level.registryAccess()).getOrThrow(AMEtheriumTypes.LIGHT).color();
    }
}
