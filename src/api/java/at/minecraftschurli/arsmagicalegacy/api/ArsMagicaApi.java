package at.minecraftschurli.arsmagicalegacy.api;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

@ApiStatus.NonExtendable
public abstract class ArsMagicaApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaApi.class).findFirst().orElseThrow(() -> {
        IllegalStateException exception = new IllegalStateException("Unable to find implementation for " + ArsMagicaApi.class.getSimpleName() + "!");
        LoggerFactory.getLogger(ArsMagicaApi.MOD_ID).error(exception.getMessage(), exception);
        return exception;
    }));

    /**
     * The id of the Ars Magica: Legacy mod.
     */
    public static final String MOD_ID = "arsmagicalegacy";

    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }
}
