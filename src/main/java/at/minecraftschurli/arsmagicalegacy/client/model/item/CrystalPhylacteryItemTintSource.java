package at.minecraftschurli.arsmagicalegacy.client.model.item;

import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.item.CrystalPhylacteryItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CrystalPhylacteryItemTintSource() implements ItemTintSource {
    public static final CrystalPhylacteryItemTintSource INSTANCE = new CrystalPhylacteryItemTintSource();
    public static final MapCodec<CrystalPhylacteryItemTintSource> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        CrystalPhylacteryItem.Contents contents = itemStack.get(AMDataComponents.CRYSTAL_PHYLACTERY_CONTENTS);
        if (contents == null || contents.amount() == 0) return -1;
        EntityType<?> entityType = contents.type();
        // TODO: get color from entity type
        return -1;
    }

    @Override
    public MapCodec<CrystalPhylacteryItemTintSource> type() {
        return CODEC;
    }
}
