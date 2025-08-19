package at.minecraftschurli.arsmagicalegacy.util;

import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public final class AMUtil {
    private AMUtil() {
    }

    /**
     * @param first  VoxelShape #1.
     * @param second VoxelShape #2.
     * @param others All other VoxelShapes.
     * @return All given shapes, joined into a single VoxelShape.
     */
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
