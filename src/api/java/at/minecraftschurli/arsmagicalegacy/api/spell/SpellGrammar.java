package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.SpellPartDataManager;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public record SpellGrammar(List<SpellPart> parts, List<Pair<SpellComponent, List<SpellModifier>>> components) {
    public static final int MAX_PARTS = 8;
    public static final SpellGrammar EMPTY = new SpellGrammar(List.of(), List.of());
    public static final Codec<SpellGrammar> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ArsMagicaApi.getSpellPartRegistry().byNameCodec().listOf(0, MAX_PARTS).fieldOf("parts").forGetter(SpellGrammar::parts)
    ).apply(inst, SpellGrammar::of));
    public static final StreamCodec<RegistryFriendlyByteBuf, SpellGrammar> STREAM_CODEC = StreamCodec.composite(
        ByteBufCodecs.registry(AMRegistryKeys.SPELL_PART).apply(ByteBufCodecs.list()), SpellGrammar::parts,
        SpellGrammar::of);

    public static SpellGrammar of(List<SpellPart> parts) {
        if (parts.isEmpty() || !parts.getFirst().isComponent()) return EMPTY;
        if (parts.size() > MAX_PARTS) {
            parts = parts.subList(0, MAX_PARTS);
        }
        List<Pair<SpellComponent, List<SpellModifier>>> components = new ArrayList<>();
        SpellComponent currentComponent = null;
        List<SpellModifier> currentModifiers = new ArrayList<>();
        for (SpellPart part : parts) {
            if (part.isShape()) return EMPTY;
            if (part.isModifier()) {
                currentModifiers.add((SpellModifier) part);
            } else if (part.isComponent()) {
                if (currentComponent != null) {
                    components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
                    currentModifiers = new ArrayList<>();
                }
                currentComponent = (SpellComponent) part;
            }
        }
        components.add(Pair.of(currentComponent, Collections.unmodifiableList(currentModifiers)));
        return new SpellGrammar(parts, Collections.unmodifiableList(components));
    }

    public static SpellGrammar of(SpellPart... parts) {
        return of(List.of(parts));
    }

    @Override
    public boolean equals(Object o) {
        return this == o || o != null && getClass() == o.getClass() && parts.equals(((SpellGrammar) o).parts);
    }

    @Override
    public int hashCode() {
        return parts.hashCode();
    }

    public double getManaCost() {
        return components.stream()
            .mapToDouble(SpellGrammar::getManaCost)
            .sum();
    }

    private static double getManaCost(Pair<SpellComponent, List<SpellModifier>> pair) {
        SpellPartDataManager manager = ArsMagicaApi.getSpellPartDataManager();
        return manager.get(pair.getFirst()).mana() * pair.getSecond().stream().mapToDouble(e -> manager.get(e).mana()).reduce(1, (a, b) -> a * b);
    }
}
