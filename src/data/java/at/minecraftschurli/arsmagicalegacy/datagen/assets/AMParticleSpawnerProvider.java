package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.data.ParticleSpawnerProvider;
import at.minecraftschurli.arsmagicalegacy.client.particle.FloatUpwardController;
import at.minecraftschurli.arsmagicalegacy.init.AMParticles;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

public final class AMParticleSpawnerProvider extends ParticleSpawnerProvider {
    public AMParticleSpawnerProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        builder(AMSpells.ABSORPTION.getId(), AMParticles.STARDUST.get(), 25, 20)
            .offset(-0.5, 0.5, -1, 0, -0.5, 0.5)
            .color(0x007fff)
            .controller(new FloatUpwardController(0, 0.1));
    }
}
