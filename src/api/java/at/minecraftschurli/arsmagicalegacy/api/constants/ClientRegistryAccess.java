package at.minecraftschurli.arsmagicalegacy.api.constants;

import net.minecraft.client.Minecraft;
import net.minecraft.core.RegistryAccess;

/**
 * Classloading barrier for getting the client-side {@link RegistryAccess}.
 */
final class ClientRegistryAccess {
    /**
     * @return The client-side {@link RegistryAccess}.
     */
    @SuppressWarnings("DataFlowIssue")
    static RegistryAccess get() {
        return Minecraft.getInstance().getConnection().registryAccess();
    }
}
