package at.minecraftschurli.arsmagicalegacy.worldgen;

import com.google.common.base.Preconditions;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.registries.holdersets.AndHolderSet;
import net.neoforged.neoforge.registries.holdersets.NotHolderSet;
import net.neoforged.neoforge.registries.holdersets.OrHolderSet;

import java.util.Arrays;

public final class HolderSets {
    private HolderSets() {}

    @SafeVarargs
    public static <T> HolderSet<T> direct(BootstrapContext<?> bootstrap, ResourceKey<? extends Registry<T>> registryKey, ResourceKey<T>... keys) {
        HolderGetter<T> lookup = bootstrap.lookup(registryKey);
        return HolderSet.direct(Arrays.stream(keys).map(lookup::getOrThrow).toList());
    }

    public static HolderSet<Biome> biome(BootstrapContext<?> bootstrap, ResourceKey<Biome> biome) {
        return HolderSet.direct(bootstrap.lookup(Registries.BIOME).getOrThrow(biome));
    }

    public static HolderSet<Biome> biomeTag(BootstrapContext<?> bootstrap, TagKey<Biome> biome) {
        return bootstrap.lookup(Registries.BIOME).getOrThrow(biome);
    }

    @SafeVarargs
    public static <T> HolderSet<T> and(HolderSet<T>... holders) {
        Preconditions.checkArgument(holders.length > 0);
        return holders.length == 1 ? holders[0] : new AndHolderSet<>(holders);
    }

    @SafeVarargs
    public static <T> HolderSet<T> or(HolderSet<T>... holders) {
        Preconditions.checkArgument(holders.length > 0);
        return holders.length == 1 ? holders[0] : new OrHolderSet<>(holders);
    }

    @SuppressWarnings("DataFlowIssue")
    public static <T> HolderSet<T> not(HolderSet<T> holder) {
        return new NotHolderSet<>(null, holder);
    }
}
