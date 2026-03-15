package at.minecraftschurli.arsmagicalegacy.client.model.item;

import at.minecraftschurli.arsmagicalegacy.item.CrystalPhylacteryItem;
import com.mojang.serialization.MapCodec;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public record CrystalPhylacteryItemTintSource() implements ItemTintSource {
    public static final CrystalPhylacteryItemTintSource INSTANCE = new CrystalPhylacteryItemTintSource();
    public static final MapCodec<CrystalPhylacteryItemTintSource> CODEC = MapCodec.unit(INSTANCE);

    @Override
    public int calculate(ItemStack itemStack, @Nullable ClientLevel level, @Nullable LivingEntity owner) {
        return CrystalPhylacteryItem.getColor(itemStack);
    }

    @Override
    public MapCodec<CrystalPhylacteryItemTintSource> type() {
        return CODEC;
    }
}
