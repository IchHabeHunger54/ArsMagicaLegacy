package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.Comparator;
import java.util.List;

public class BlackAuremBlockEntity extends EtheriumGeneratorBlockEntity {
    private static final String TIME_KEY = "time";
    private int time = 0;

    public BlackAuremBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.BLACK_AUREM.get(), pos, state, AMEtheriumTypes.DARK);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (etherium >= getMaxAmount()) return;
        time--;
        if (time < 0) {
            time = 6;
            Vec3 vec3 = Vec3.atBottomCenterOf(pos);
            List<Mob> mobs = level.getEntities(EntityTypeTest.forClass(Mob.class), new AABB(vec3.add(-2, 0, -2), vec3.add(2, 4, 2)), e -> true);
            mobs.sort(Comparator.comparingDouble(e -> e.distanceToSqr(vec3)));
            for (Mob mob : mobs) {
                if (mob.isAlive() && !mob.isInvertedHealAndHarm() && mob.hurt(level.damageSources().magic(), 1)) {
                    etherium++;
                    break;
                }
            }
        }
        setChanged();
    }

    @Override
    public int getMaxAmount() {
        return AMServerConfig.BLACK_AUREM_MAX_ETHERIUM.get();
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
}
