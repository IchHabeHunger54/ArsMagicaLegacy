package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.BushGrowthType;
import at.minecraftschurli.arsmagicalegacy.plant.CropGrowthType;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public interface AMGrowthTypes {
    DeferredRegister<MapCodec<? extends GrowthType>> GROWTH_TYPES = DeferredRegister.create(AMRegistries.GROWTH_TYPE, ArsMagicaApi.MOD_ID);

    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<BushGrowthType>> BUSH = GROWTH_TYPES.register("bush", () -> BushGrowthType.CODEC);
    DeferredHolder<MapCodec<? extends GrowthType>, MapCodec<CropGrowthType>> CROP = GROWTH_TYPES.register("crop", () -> CropGrowthType.CODEC);
}
