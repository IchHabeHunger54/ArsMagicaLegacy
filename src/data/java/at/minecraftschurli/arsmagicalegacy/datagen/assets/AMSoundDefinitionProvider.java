package at.minecraftschurli.arsmagicalegacy.datagen.assets;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

import java.util.HashSet;
import java.util.Set;

public final class AMSoundDefinitionProvider extends SoundDefinitionsProvider {
    private final Set<ResourceLocation> sounds = new HashSet<>();

    public AMSoundDefinitionProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, ArsMagicaApi.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        sound(AMSounds.ARCANE_GUARDIAN_ATTACK, 5);
        sound(AMSounds.LIGHTNING_GUARDIAN_LIGHTNING_ROD, 3);
        AMRegistries.SOUND_EVENTS.getEntries().forEach(this::sound);
    }

    @SuppressWarnings("DataFlowIssue")
    private void sound(Holder<SoundEvent> sound, int count) {
        if (count <= 0) return;
        ResourceLocation location = sound.getKey().location();
        if (sounds.contains(location)) return;
        sounds.add(location);
        String subtitle = "subtitle." + location.getNamespace() + "." + location.getPath();
        String path = location.toString().replace('.', '/');
        if (count == 1) {
            add(sound.value(), definition().with(sound(path)).subtitle(subtitle));
        } else {
            SoundDefinition def = definition();
            for (int value = 1; value <= count; value++) {
                def.with(sound(path + "_" + value));
            }
            add(sound.value(), def.subtitle(subtitle));
        }
    }

    private void sound(Holder<SoundEvent> sound) {
        sound(sound, 1);
    }
}
