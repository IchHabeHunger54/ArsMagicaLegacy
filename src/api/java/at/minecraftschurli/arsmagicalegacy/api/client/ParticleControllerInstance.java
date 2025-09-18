package at.minecraftschurli.arsmagicalegacy.api.client;

public class ParticleControllerInstance {
    private final ParticleController controller;
    protected int tickCount = 0;
    private boolean first = true;
    private boolean finished = false;

    public ParticleControllerInstance(ParticleController controller) {
        this.controller = controller;
    }

    public void tick(ControlledParticle particle) {
        tickCount++;
        if (!particle.isAlive() || finished) return;
        if (first) {
            tickFirst(particle);
            first = false;
        } else {
            controller.tick(particle, tickCount);
        }
    }

    public void tickFirst(ControlledParticle particle) {
        controller.tick(particle, tickCount);
    }

    public void finish(ControlledParticle particle) {
        finished = true;
        if (controller.killOnFinish() && particle.isAlive()) {
            particle.remove();
        }
    }
}
