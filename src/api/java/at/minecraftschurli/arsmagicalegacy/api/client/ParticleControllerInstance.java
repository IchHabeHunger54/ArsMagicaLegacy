package at.minecraftschurli.arsmagicalegacy.api.client;

/**
 * An instance of a {@link ParticleController}.
 */
public final class ParticleControllerInstance {
    public final ControlledParticle particle;
    public final ParticleController controller;
    private int tickCount = 0;
    private boolean first = true;
    private boolean finished = false;

    /**
     * @param particle   The {@link ControlledParticle} the controller belongs to.
     * @param controller The {@link ParticleController} to query for values.
     */
    public ParticleControllerInstance(ControlledParticle particle, ParticleController controller) {
        this.particle = particle;
        this.controller = controller;
    }

    /**
     * Ticks the instance.
     */
    public void tick() {
        tickCount++;
        if (particle.isRemoved() || finished) return;
        if (first) {
            controller.tickFirst(this);
            first = false;
        } else {
            controller.tick(this);
        }
    }

    /**
     * Marks the instance as finished and (if {@link ParticleController#killOnFinish()} is true) removes the particle.
     */
    public void finish() {
        finished = true;
        if (controller.killOnFinish() && !particle.isRemoved()) {
            particle.setRemoved(true);
        }
    }

    /**
     * @return Whether the instance is marked as finished.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * @return The amount of ticks this instance has been active so far.
     */
    public int getTickCount() {
        return tickCount;
    }
}
