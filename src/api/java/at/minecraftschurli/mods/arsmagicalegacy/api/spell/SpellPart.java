package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a spell part.
 */
public abstract sealed class SpellPart permits PrimarySpellShape, SecondarySpellShape, SpellComponent, SpellModifier {
    /**
     * @return Whether the spell part is a shape (primary or secondary).
     */
    public final boolean isShape() {
        return isPrimaryShape() || isSecondaryShape();
    }

    /**
     * @return Whether the spell part is a primary shape.
     */
    public abstract boolean isPrimaryShape();

    /**
     * @return Whether the spell part is a secondary shape.
     */
    public abstract boolean isSecondaryShape();

    /**
     * @return Whether the spell part is a component.
     */
    public abstract boolean isComponent();

    /**
     * @return Whether the spell part is a modifier.
     */
    public abstract boolean isModifier();

    /**
     * If the spell part is a modifier, returns the {@link SpellStat}s the modifier modifies. Otherwise, returns the {@link SpellStat}s the spell part uses.
     *
     * @return A {@link Set} of {@link SpellStat}s.
     */
    public abstract Set<SpellStat> getStats();

    /**
     * @param registryAccess The {@link RegistryAccess} to use.
     * @return The spell part's datapack-defined data.
     */
    public SpellPartData getData(RegistryAccess registryAccess) {
        Identifier id = Objects.requireNonNull(AMRegistries.SPELL_PARTS.getKey(this));
        ResourceKey<SpellPartData> key = ResourceKey.create(AMRegistries.Keys.SPELL_PART_DATA, id);
        return AMRegistries.spellPartData(registryAccess).get(key).map(Holder::value).orElse(SpellPartData.DEFAULT);
    }

    /**
     * @param registries The {@link HolderLookup.Provider} to use.
     * @return The spell part's datapack-defined data.
     */
    public SpellPartData getData(HolderLookup.Provider registries) {
        Identifier key = AMRegistries.SPELL_PARTS.getKey(this);
        return key == null ? SpellPartData.DEFAULT : registries.lookupOrThrow(AMRegistries.Keys.SPELL_PART_DATA)
            .get(ResourceKey.create(AMRegistries.Keys.SPELL_PART_DATA, key))
            .map(Holder::value)
            .orElse(SpellPartData.DEFAULT);
    }

    /**
     * @return The {@link DataComponentType} the spell part uses for additional data storage, or null if it does not use a {@link DataComponentType}.
     */
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return null;
    }

    /// @param part       The spell part to compute the mana cost of.
    /// @param modifiers  The modifiers to compute the mana cost with.
    /// @param registries The {@link HolderLookup.Provider} to use.
    /// @return The computed mana cost.
    public static double computeManaCost(SpellPart part, List<SpellModifier> modifiers, HolderLookup.Provider registries) {
        double modifiersCostFactor = modifiers
            .stream()
            .mapToDouble(e -> e.getData(registries).mana())
            .reduce(1, (a, b) -> a * b);
        return part.getData(registries).mana() * modifiersCostFactor;
    }
}
