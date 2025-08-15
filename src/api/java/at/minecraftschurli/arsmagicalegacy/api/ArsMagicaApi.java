package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

/**
 * The main entrypoint for the Ars Magica: Legacy API.
 */
@ApiStatus.NonExtendable
public abstract class ArsMagicaApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaApi.class).findFirst().orElseThrow());

    /**
     * The id of the Ars Magica: Legacy mod.
     */
    public static final String MOD_ID = "arsmagicalegacy";

    /**
     * Creates a new {@link ResourceLocation} with the mod's namespace.
     *
     * @param path The path of the {@link ResourceLocation}.
     * @return A new {@link ResourceLocation}.
     */
    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }

    /**
     * @return The spell part registry.
     */
    public static Registry<SpellPart> getSpellPartRegistry() {
        return INSTANCE.get()._getSpellPartRegistry();
    }

    /**
     * @return The data component registry.
     */
    public static Registry<DataComponentType<?>> getSpellDataComponentRegistry() {
        return INSTANCE.get()._getSpellDataComponentRegistry();
    }

    /**
     * @return The {@link SpellPartDataManager} instance.
     */
    public static SpellPartDataManager getSpellPartDataManager() {
        return INSTANCE.get()._getSpellPartDataManager();
    }

    /**
     * @return The {@link SpellHelper} instance.
     */
    public static SpellHelper getSpellHelper() {
        return INSTANCE.get()._getSpellHelper();
    }

    @ApiStatus.Internal
    protected abstract Registry<SpellPart> _getSpellPartRegistry();

    @ApiStatus.Internal
    protected abstract Registry<DataComponentType<?>> _getSpellDataComponentRegistry();

    @ApiStatus.Internal
    protected abstract SpellPartDataManager _getSpellPartDataManager();

    @ApiStatus.Internal
    protected abstract SpellHelper _getSpellHelper();
}
