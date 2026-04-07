package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import com.mojang.datafixers.util.Function5;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public interface SpellFacade {
    SpellFacade.Immutable EMPTY = new Immutable(Optional.empty(), Optional.empty(), Spell.EMPTY, SpellDataComponentMap.EMPTY, (byte) 0);

    /// @return The spell.
    Spell spell();

    /// @return The currently active shape group of the spell.
    default SpellShapeGroup currentShapeGroup() {
        return shapeGroups().get(activeShapeGroup());
    }

    /// @return The {@link SpellShapeGroup}s of the spell. Immutable by contract.
    default List<SpellShapeGroup> shapeGroups() {
        return spell().shapeGroups();
    }

    /// @return The {@link SpellGrammar} of the spell. Immutable by contract.
    default SpellGrammar grammar() {
        return spell().grammar();
    }

    /// @return Whether the spell is continuous, i.e., can be cast by holding down the spell.
    default boolean isContinuous() {
        return currentShapeGroup().isContinuous();
    }

    /// @return Whether the spell is malformed, i.e., does not fulfill basic requirements to the spell's structure.
    default boolean isMalformed() {
        return currentShapeGroup().primaryShape() == null || grammar().components().isEmpty();
    }

    /// @return The name of the spell.
    Optional<Component> name();

    /// @return The icon of the spell.
    Optional<Identifier> icon();

    /// @return The data components of the spell. To modify, call {@link MutableSpellFacade#updateSpellData(UnaryOperator)}.
    SpellDataComponentMap spellData();

    /// @return The currently active shape group index.
    byte activeShapeGroup();

    /// @return The combined mana cost of the spell.
    default double getManaCost(HolderLookup.Provider registries) {
        return currentShapeGroup().getManaCost(registries) * grammar().getManaCost(registries);
    }

    default Immutable asImmutable() {
        return this instanceof Immutable immutable ? immutable : new Immutable(this);
    }

    default MutableSpellFacade.MutableHolder asMutable() {
        return new MutableSpellFacade.MutableHolder(this);
    }

    record Immutable(Optional<Component> name, Optional<Identifier> icon, Spell spell, SpellDataComponentMap spellData, byte activeShapeGroup) implements SpellFacade {
        public static final MapCodec<Immutable> MAP_CODEC = SpellFacade.codec(SpellFacade.Immutable::new);
        public static final Codec<Immutable> CODEC = MAP_CODEC.codec();
        public static final StreamCodec<RegistryFriendlyByteBuf, Immutable> STREAM_CODEC = SpellFacade.streamCodec(Immutable::new);

        public Immutable(SpellFacade spell) {
            this(spell.name(), spell.icon(), spell.spell(), spell.spellData(), spell.activeShapeGroup());
        }

        public static Immutable of(SpellPrefab spellPrefab) {
            return new Immutable(spellPrefab.name(), Optional.of(spellPrefab.icon()), spellPrefab.spell(), spellPrefab.spellData(), (byte) 0);
        }
    }

    static <T extends SpellFacade> MapCodec<T> codec(Function5<Optional<Component>, Optional<Identifier>, Spell, SpellDataComponentMap, Byte, T> constructor) {
        return RecordCodecBuilder.mapCodec(inst -> inst.group(
            ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(SpellFacade::name),
            Identifier.CODEC.optionalFieldOf("icon").forGetter(SpellFacade::icon),
            Spell.CODEC.optionalFieldOf("spell", Spell.EMPTY).forGetter(SpellFacade::spell),
            SpellDataComponentMap.CODEC.optionalFieldOf("data", SpellDataComponentMap.EMPTY).forGetter(SpellFacade::spellData),
            Codec.BYTE.optionalFieldOf("active_shape_group", (byte) 0).forGetter(SpellFacade::activeShapeGroup)
        ).apply(inst, constructor));
    }

    static <T extends SpellFacade> StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(Function5<Optional<Component>, Optional<Identifier>, Spell, SpellDataComponentMap, Byte, T> constructor) {
        return StreamCodec.composite(
            ByteBufCodecs.optional(ComponentSerialization.STREAM_CODEC), SpellFacade::name,
            ByteBufCodecs.optional(Identifier.STREAM_CODEC), SpellFacade::icon,
            Spell.STREAM_CODEC, SpellFacade::spell,
            SpellDataComponentMap.STREAM_CODEC, SpellFacade::spellData,
            ByteBufCodecs.BYTE, SpellFacade::activeShapeGroup,
            constructor
        );
    }
}
