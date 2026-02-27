package at.minecraftschurli.arsmagicalegacy.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.level.Level;

public class WitchwoodChestBoat extends ChestBoat {
    public WitchwoodChestBoat(EntityType<? extends ChestBoat> entityType, Level level) {
        super(entityType, level);
        setVariant(WitchwoodBoat.TYPE);
    }

    public WitchwoodChestBoat(Level level, double x, double y, double z) {
        super(level, x, y, z);
        setVariant(WitchwoodBoat.TYPE);
    }
}
