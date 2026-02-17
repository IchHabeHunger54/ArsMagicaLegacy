package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.data.RitualProvider;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.ritual.LearnSkillRitualEffect;
import at.minecraftschurli.arsmagicalegacy.ritual.SpellGrammarCastRitualTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

public final class AMRitualProvider extends RitualProvider {
    public AMRitualProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID);
    }

    @Override
    public void generate(HolderLookup.Provider provider) {
        HolderLookup.RegistryLookup<Skill> skills = provider.lookupOrThrow(AMRegistries.Keys.SKILL);
        unlock(skills, AMSpells.BLIZZARD, AMSpells.FROST_DAMAGE, AMSpells.FROST, AMSpells.STORM);
    }

    @SafeVarargs
    private void unlock(HolderLookup.RegistryLookup<Skill> skills, DeferredHolder<SpellPart, ?> part, DeferredHolder<SpellPart, ?>... parts) {
        ResourceLocation id = part.getId();
        builder("unlock_" + id.getPath(), new SpellGrammarCastRitualTrigger(Arrays.stream(parts)
            .map(DeferredHolder::get)
            .map(e -> (SpellPart) e)
            .toList()))
            .addEffect(new LearnSkillRitualEffect(skills.getOrThrow(ResourceKey.create(AMRegistries.Keys.SKILL, id))));
    }
}
