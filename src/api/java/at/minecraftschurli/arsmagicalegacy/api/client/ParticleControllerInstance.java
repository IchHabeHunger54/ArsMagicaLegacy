package at.minecraftschurli.arsmagicalegacy.api.client;

public class ParticleControllerInstance {
    public final ControlledParticle particle;
    private final ParticleController controller;
    protected int tickCount = 0;
    private boolean first = true;
    private boolean finished = false;

    public ParticleControllerInstance(ControlledParticle particle, ParticleController controller) {
        this.particle = particle;
        this.controller = controller;
    }

    public void tick() {
        tickCount++;
        if (!particle.isAlive() || finished) return;
        if (first) {
            tickFirst();
            first = false;
        } else {
            controller.tick(this);
        }
    }

    public void tickFirst() {
        controller.tick(this);
    }

    public void finish() {
        finished = true;
        if (controller.killOnFinish() && particle.isAlive()) {
            particle.remove();
        }
    }
}
