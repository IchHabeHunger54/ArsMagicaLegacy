package at.minecraftschurli.mods.arsmagicalegacy.api.data;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellShapeGroup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Builder class for {@link Spell} prefabs, for use in {@link SpellPrefabProvider}. Get an instance via {@link SpellPrefabProvider#builder(String, Identifier, List, List[])} or {@link SpellPrefabProvider#builder(String, Component, Identifier, List, List[])}.
 */
public class SpellPrefabBuilder extends AbstractDataProvider.Builder<Spell> {
    private final Component name;
    private final Identifier icon;
    private final List<SpellShapeGroup> shapeGroups;
    private final SpellGrammar grammar;

    /**
     * @param id          The id of the {@link Spell} prefab to generate.
     * @param name        The name of the {@link Spell} prefab.
     * @param icon        The icon of the {@link Spell} prefab.
     * @param grammar     A list of {@link SpellPart}s representing the {@link SpellGrammar} of the {@link Spell} prefab.
     * @param shapeGroups A list of lists of {@link SpellPart}s representing the {@link SpellShapeGroup}s of the {@link Spell} prefab.
     */
    @SafeVarargs
    public SpellPrefabBuilder(Identifier id, Component name, Identifier icon, List<SpellPart> grammar, List<SpellPart>... shapeGroups) {
        if (shapeGroups.length == 0) throw new IllegalArgumentException("Spell prefab must have at least one spell shape group");
        super(id);
        this.name = name;
        this.icon = icon;
        this.shapeGroups = Arrays.stream(shapeGroups)
            .map(SpellShapeGroup::of)
            .toList();
        this.grammar = SpellGrammar.of(grammar);
    }

    @Override
    public Spell build() {
        return new Spell(Optional.of(name), Optional.of(icon), shapeGroups, 0, grammar, SpellDataComponentMap.EMPTY);
    }
}
