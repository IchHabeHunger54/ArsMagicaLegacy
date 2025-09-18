package at.minecraftschurli.arsmagicalegacy.client.particle;

import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleSpawner;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.IntStream;

public class AMParticle extends SimpleAnimatedParticle implements ControlledParticle {
    @SuppressWarnings("DataFlowIssue")
    private AMParticle(ClientLevel level, double x, double y, double z, @Nullable SpriteSet sprites) {
        super(level, x, y, z, sprites, 0);
    }

    public static void spawn(ClientLevel level, double x, double y, double z, ParticleSpawner spawner, int color) {
        ParticleEngine particleEngine = AMClientUtil.mc().particleEngine;
        Particle vanillaParticle = particleEngine.createParticle(spawner.particle(), x, y, z, 0, 0, 0);
        if (vanillaParticle == null) return;
        SpriteSet sprites = vanillaParticle instanceof SimpleAnimatedParticle particle ? particle.sprites : null;
        TextureAtlasSprite sprite = switch (vanillaParticle) {
            case SimpleAnimatedParticle ignored -> sprites.get(0, 1);
            case TextureSheetParticle particle -> particle.sprite;
            default -> null;
        };
        vanillaParticle.remove();
        if (sprite == null) return;
        for (int i = 0; i < spawner.count(); i++) {
            AMParticle particle = new AMParticle(level, x, y, z, sprites);
            particle.setSprite(sprite);
            particle.setColor(color);
            // TODO set other options
            particleEngine.add(particle);
        }
    }
}
