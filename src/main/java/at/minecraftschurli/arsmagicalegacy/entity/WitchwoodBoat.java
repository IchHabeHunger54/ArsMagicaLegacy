package at.minecraftschurli.arsmagicalegacy.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.level.Level;

public class WitchwoodBoat extends Boat {
    public static final Boat.Type TYPE = Boat.Type.valueOf("ARSMAGICALEGACY_WITCHWOOD");

    public WitchwoodBoat(EntityType<? extends Boat> entityType, Level level) {
        super(entityType, level);
        setVariant(TYPE);
    }

    public WitchwoodBoat(Level level, double x, double y, double z) {
        super(level, x, y, z);
        setVariant(TYPE);
    }
}
