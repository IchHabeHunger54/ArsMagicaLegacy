package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Represents a spell's grammar.
 *
 * @param parts      A {@link List} of all parts. Immutable by contract. Used mainly for serialization, use {@link SpellGrammar#components} for gameplay.
 * @param components A view of {@link SpellGrammar#parts} that lists the parts as {@link SpellComponent}s with their associated {@link SpellModifier}s. Immutable by contract.
 */
public record SpellGrammar(List<SpellPart> parts, List<Pair<SpellComponent, List<SpellModifier>>> components) {
    public static final int MAX_PARTS = 8;
    public static final SpellGrammar EMPTY = new SpellGrammar(List.of(), List.of());
    public static final Codec<SpellGrammar> CODEC = ArsMagicaApi.spellPartRegistry().byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").xmap(SpellGrammar::of, SpellGrammar::parts).codec();
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellGrammar> STREAM_CODEC = ByteBufCodecs.registry(AMRegistryKeys.SPELL_PART).apply(ByteBufCodecs.list()).map(SpellGrammar::of, SpellGrammar::parts);

    /**
     * @deprecated Use {@link SpellGrammar#of(List)} instead.
     */
    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    public SpellGrammar {
    }

    /**
     * Validates the given {@link List} of {@link SpellPart}s and constructs a {@link SpellGrammar} from it.
     *
     * @param parts The {@link List} of {@link SpellPart}s.
     * @return A new {@link SpellGrammar}, or {@link SpellGrammar#EMPTY} if validation failed.
     */
    public static SpellGrammar of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isComponent()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        List<Pair<SpellComponent, List<SpellModifier>>> components = new ArrayList<>();
        SpellComponent currentComponent = null;
        List<SpellModifier> currentModifiers = new ArrayList<>();
        for (SpellPart part : parts) {
            if (part.isModifier()) {
                currentModifiers.add((SpellModifier) part);
            } else if (part.isComponent() && components.stream().noneMatch(pair -> pair.getFirst() == part)) {
                if (currentComponent != null) {
                    components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
                    currentModifiers = new ArrayList<>();
                }
                currentComponent = (SpellComponent) part;
            }
        }
        components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
        return new SpellGrammar(parts, components);
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && parts.equals(((SpellGrammar) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    /**
     * @return Whether the spell grammar is considered empty.
     */
    public boolean isEmpty() {
        return parts.isEmpty();
    }

    /**
     * @return The combined mana cost of the spell grammar.
     */
    public double getManaCost() {
        return components.stream()
            .mapToDouble(pair -> pair.getFirst().getData().mana() * pair.getSecond().stream().mapToDouble(e -> e.getData().mana()).reduce(1, (a, b) -> a * b))
            .sum();
    }

    /**
     * @return The combined burnout cost of the spell grammar.
     */
    public double getBurnoutCost() {
        return components.stream()
            .mapToDouble(pair -> pair.getFirst().getData().burnoutOrGenerated())
            .sum();
    }

    /**
     * @return A {@link Map} of combined {@link Affinity} shifts of the spell grammar.
     */
    public Map<Holder<Affinity>, Double> affinityShifts() {
        return components.stream()
            .map(Pair::getFirst)
            .map(SpellPart::getData)
            .map(SpellPartData::affinityShifts)
            .map(Map::entrySet)
            .flatMap(Set::stream)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Double::sum));
    }
}
