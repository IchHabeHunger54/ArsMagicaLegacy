package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodBoat;
import at.minecraftschurli.arsmagicalegacy.entity.WitchwoodChestBoat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.BoatItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class WitchwoodBoatItem extends BoatItem {
    private final boolean hasChest;

    public WitchwoodBoatItem(boolean hasChest, Properties properties) {
        super(hasChest, WitchwoodBoat.TYPE, properties);
        this.hasChest = hasChest;
    }

    @Override
    protected Boat getBoat(Level level, HitResult hitResult, ItemStack stack, Player player) {
        Vec3 vec3 = hitResult.getLocation();
        Boat boat = hasChest ? new WitchwoodChestBoat(level, vec3.x, vec3.y, vec3.z) : new WitchwoodBoat(level, vec3.x, vec3.y, vec3.z);
        if (level instanceof ServerLevel serverlevel) {
            EntityType.<Boat>createDefaultStackConfig(serverlevel, stack, player).accept(boat);
        }
        return boat;
    }
}
