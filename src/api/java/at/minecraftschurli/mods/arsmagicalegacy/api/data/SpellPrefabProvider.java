package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Data provider for {@link Spell} prefabs. Override {@link SpellPrefabProvider#generate(HolderLookup.Provider)} to generate your entries,
 * and use {@link SpellPrefabProvider#builder(String, Identifier, List, List[])} or {@link SpellPrefabProvider#builder(String, Component, Identifier, List, List[])} to create a new {@link SpellPrefabBuilder}.
 */
public abstract class SpellPrefabProvider extends AbstractDataProvider<Spell, SpellPrefabBuilder> {
    /**
     * @param output         The {@link PackOutput} to use. Get this from {@link GatherDataEvent}.
     * @param lookupProvider The lookup {@link CompletableFuture} to use. Get this from {@link GatherDataEvent}.
     * @param modId          Your mod id.
     */
    public SpellPrefabProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(PackOutput.Target.DATA_PACK, ArsMagicaApi.MOD_ID + "/spell_prefab", "Spell Prefabs", Spell.CODEC, output, lookupProvider, modId);
    }

    /**
     * Creates and adds a new {@link SpellPrefabBuilder}.
     *
     * @param name        The name of the {@link Spell} prefab to generate.
     * @param displayName The display name of the {@link Spell} prefab.
     * @param icon        The icon of the {@link Spell} prefab.
     * @param grammar     A list of {@link SpellPart}s representing the {@link SpellGrammar} of the {@link Spell} prefab.
     * @param shapeGroups A list of lists of {@link SpellPart}s representing the {@link SpellShapeGroup}s of the {@link Spell} prefab.
     */
    @SafeVarargs
    public final SpellPrefabBuilder builder(String name, Component displayName, Identifier icon, List<SpellPart> grammar, List<SpellPart>... shapeGroups) {
        SpellPrefabBuilder builder = new SpellPrefabBuilder(Identifier.fromNamespaceAndPath(modId, name), displayName, icon, grammar, shapeGroups);
        add(builder);
        return builder;
    }

    /**
     * Creates and adds a new {@link SpellPrefabBuilder}.
     *
     * @param name        The name of the {@link Spell} prefab to generate.
     * @param icon        The icon of the {@link Spell} prefab.
     * @param grammar     A list of {@link SpellPart}s representing the {@link SpellGrammar} of the {@link Spell} prefab.
     * @param shapeGroups A list of lists of {@link SpellPart}s representing the {@link SpellShapeGroup}s of the {@link Spell} prefab.
     */
    @SafeVarargs
    public final SpellPrefabBuilder builder(String name, Identifier icon, List<SpellPart> grammar, List<SpellPart>... shapeGroups) {
        return builder(name, Component.translatable("spell_prefab." + modId + "." + name), icon, grammar, shapeGroups);
    }
}
