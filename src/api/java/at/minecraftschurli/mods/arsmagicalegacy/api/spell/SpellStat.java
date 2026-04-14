package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.DoubleFunction;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

/**
 * Represents a stat that can be modified, e.g. by {@link SpellModifier}s. No registration is necessary, equality is checked via {@link SpellStat#equals(Object)}.
 *
 * @param id                      The id of the stat.
 * @param genericModifierFunction A function to be used by generic {@link SpellModifier}s (e.g. Solar or Lunar), to create a {@link SpellStatModifier} from a given multiplier. If empty, generic {@link SpellModifier}s will ignore this stat.
 */
public record SpellStat(Identifier id, Optional<DoubleFunction<SpellStatModifier>> genericModifierFunction) {
    private static final List<SpellStat> ALL = new ArrayList<>();
    public static final SpellStat COLOR = new SpellStat(ArsMagicaApi.id("color"));

    public SpellStat {
        ALL.add(this);
    }

    /**
     * Represents a stat that can be modified, e.g. by {@link SpellModifier}s.
     *
     * @param id The id of the stat.
     */
    public SpellStat(Identifier id) {
        this(id, Optional.empty());
    }

    /**
     * Represents a stat that can be modified, e.g. by {@link SpellModifier}s.
     *
     * @param id                      The id of the stat.
     * @param genericModifierFunction A function to be used by generic {@link SpellModifier}s (e.g. Solar or Lunar), to create a {@link SpellStatModifier} from a given multiplier.
     */
    public SpellStat(Identifier id, DoubleFunction<SpellStatModifier> genericModifierFunction) {
        this(id, Optional.of(genericModifierFunction));
    }

    /**
     * @return All registered {@link SpellStat}s.
     */
    public static List<SpellStat> getAll() {
        return Collections.unmodifiableList(ALL);
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SpellStat other && Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    /**
     * Creation helper for a generic {@link SpellModifier}. Returns a map of {@link SpellStatModifier}s based on a multiplier function.
     * On invocation of the {@link SpellModifier}, the multiplier is calculated using the {@link SpellStatModifier}'s {@link SpellCastContext}.
     * The {@link SpellStatModifier} is then wrapped and passed along, containing the generic {@link SpellModifier}'s modifications.
     *
     * @param multiplierFunction The multiplier function to use.
     * @return A map to be passed into {@link SpellModifier#SpellModifier(Map)}.
     */
    public static Map<SpellStat, SpellStatModifier> genericModifiers(ToDoubleFunction<SpellCastContext> multiplierFunction) {
        return ALL.stream()
            .filter(stat -> stat.genericModifierFunction().isPresent())
            .collect(Collectors.toMap(
                Function.identity(),
                stat -> (base, modified, context) -> stat.genericModifierFunction().get().apply(multiplierFunction.applyAsDouble(context)).modify(base, modified, context)
            ));
    }
}
