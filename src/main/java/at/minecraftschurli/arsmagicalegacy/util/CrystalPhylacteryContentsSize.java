package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import com.mojang.serialization.Codec;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.neoforged.neoforge.registries.datamaps.DataMapType;

@SuppressWarnings("deprecation")
public record CrystalPhylacteryContentsSize(int size) {
    public static final Codec<CrystalPhylacteryContentsSize> CODEC = ExtraCodecs.NON_NEGATIVE_INT.xmap(CrystalPhylacteryContentsSize::new, CrystalPhylacteryContentsSize::size);
    public static final DataMapType<EntityType<?>, CrystalPhylacteryContentsSize> DATA_MAP = DataMapType.builder(ArsMagicaApi.id("crystal_phylactery_storage_size"), Registries.ENTITY_TYPE, CODEC)
        .synced(CODEC, true)
        .build();

    @SuppressWarnings("unchecked")
    public static int get(EntityType<?> type) {
        CrystalPhylacteryContentsSize data = type.builtInRegistryHolder().getData(DATA_MAP);
        if (data != null) return data.size();
        return DefaultAttributes.hasSupplier(type) ? (int) DefaultAttributes.getSupplier((EntityType<? extends LivingEntity>) type).getBaseValue(Attributes.MAX_HEALTH) : 0;
    }

    public static boolean has(EntityType<?> type) {
        return type.builtInRegistryHolder().getData(DATA_MAP) != null;
    }
}
