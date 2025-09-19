package at.minecraftschurli.arsmagicalegacy.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;

public class SymbolsParticleProvider implements ParticleProvider<SimpleParticleType> {
    private final SpriteSet sprites;

    public SymbolsParticleProvider(SpriteSet sprites) {
        this.sprites = sprites;
    }

    @Override
    public Particle createParticle(SimpleParticleType type, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        return new SymbolsParticle(level, x, y, z, sprites.get(level.random));
    }

    private static class SymbolsParticle extends TextureSheetParticle {
        private SymbolsParticle(ClientLevel level, double x, double y, double z, TextureAtlasSprite sprite) {
            super(level, x, y, z);
            setSprite(sprite);
        }

        @Override
        public ParticleRenderType getRenderType() {
            return ParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
        }
    }
}
