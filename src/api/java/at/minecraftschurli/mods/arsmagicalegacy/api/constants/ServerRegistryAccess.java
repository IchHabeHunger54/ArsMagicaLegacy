package at.minecraftschurli.mods.arsmagicalegacy.api.constants;

import net.minecraft.core.RegistryAccess;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

/**
 * Classloading barrier for getting the server-side {@link RegistryAccess}.
 */
final class ServerRegistryAccess {
    /**
     * @return The server-side {@link RegistryAccess}.
     */
    @SuppressWarnings("DataFlowIssue")
    static RegistryAccess get() {
        return ServerLifecycleHooks.getCurrentServer().registryAccess();
    }
}
