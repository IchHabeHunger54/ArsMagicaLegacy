package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

/**
 * The client entrypoint for the Ars Magica: Legacy API.
 */
@ApiStatus.NonExtendable
public abstract class ArsMagicaClientApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaClientApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaClientApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaClientApi.class).findFirst().orElseThrow());

    /**
     * @param tab The {@link OcculusTab} to get the {@link OcculusTabRenderer.Factory} for.
     * @return The {@link OcculusTabRenderer.Factory} for the specified {@link OcculusTab}.
     */
    public static OcculusTabRenderer.Factory occulusTabRendererFactory(OcculusTab tab) {
        return INSTANCE.get().getOcculusTabRendererFactory(tab);
    }

    @ApiStatus.Internal
    protected abstract OcculusTabRenderer.Factory getOcculusTabRendererFactory(OcculusTab tab);
}
