package at.minecraftschurli.arsmagicalegacy.api.client;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.ApiStatus;

/**
 * Accessor interface for a particle controlled by a {@link ParticleControllerInstance}.
 */
@ApiStatus.NonExtendable
public interface ControlledParticle {
    /**
     * @return Whether the particle is removed.
     */
    boolean isRemoved();

    /**
     * Sets the particle to be removed.
     *
     * @param removed The removal state to set.
     */
    void setRemoved(boolean removed);

    /**
     * @return The x position of the particle.
     */
    double x();

    /**
     * @return The y position of the particle.
     */
    double y();

    /**
     * @return The z position of the particle.
     */
    double z();

    /**
     * @return The {@link ClientLevel} the particle is in.
     */
    ClientLevel level();

    /**
     * @return The particle's {@link RandomSource}.
     */
    RandomSource random();

    /**
     * Moves the particle by the specified amounts.
     *
     * @param x The amount to move by in x direction.
     * @param y The amount to move by in y direction.
     * @param z The amount to move by in z direction.
     */
    void move(double x, double y, double z);
}
