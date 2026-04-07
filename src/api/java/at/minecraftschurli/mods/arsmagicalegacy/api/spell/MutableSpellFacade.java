package at.minecraftschurli.mods.arsmagicalegacy.api.spell;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.List;
import java.util.Optional;
import java.util.function.UnaryOperator;

public interface MutableSpellFacade extends SpellFacade {

    /// @param name The new name to set.
    void setName(@Nullable Component name);

    /// @param name The new name to set.
    default void setName(String name) {
        if (name.isEmpty()) {
            clearName();
        } else {
            setName(Component.literal(name));
        }
    }

    /// Clear the spells name.
    void clearName();

    /// @param icon The new icon to set.
    void setIcon(@Nullable Identifier icon);

    /// Clear the icon of the spell.
    void clearIcon();

    /// @param update The unary operator to update the spell data.
    void updateSpellData(UnaryOperator<SpellDataComponentMap> update);

    /// @param data The new spell data to set.
    void setSpellData(SpellDataComponentMap data);

    /// Increment the active shape group index, wrapping around if the current shape group is the last.
    default void nextShapeGroup() {
        List<SpellShapeGroup> shapeGroups = shapeGroups();
        if (shapeGroups.isEmpty()) return;
        byte i = activeShapeGroup();
        do {
            i = (byte) (i < shapeGroups.size() - 1 ? i + 1 : 0);
        } while (shapeGroups.get(i).isEmpty());
        setActiveShapeGroup(i);
    }

    /// Decrement the active shape group index, wrapping around if the current shape group is the first.
    default void prevShapeGroup() {
        List<SpellShapeGroup> shapeGroups = shapeGroups();
        if (shapeGroups.isEmpty()) return;
        byte i = activeShapeGroup();
        do {
            i = (byte) (i > 0 ? i - 1 : 0);
        } while (shapeGroups.get(i).isEmpty());
        setActiveShapeGroup(i);
    }

    /// @param activeShapeGroup The new active shape group.
    void setActiveShapeGroup(byte activeShapeGroup);

    void setFrom(SpellFacade spell);

    final class MutableHolder implements MutableSpellFacade {
        public static final MapCodec<MutableHolder> MAP_CODEC = SpellFacade.codec(MutableHolder::new);
        public static final StreamCodec<RegistryFriendlyByteBuf, MutableHolder> STREAM_CODEC = SpellFacade.streamCodec(MutableHolder::new);

        private final Spell spell;
        private Optional<Component> name;
        private Optional<Identifier> icon;
        private SpellDataComponentMap data;
        private byte activeShapeGroupIndex;

        MutableHolder(SpellFacade spell) {
            this.spell = spell.spell();
            this.name = spell.name();
            this.icon = spell.icon();
            this.data = spell.spellData();
            this.activeShapeGroupIndex = spell.activeShapeGroup();
        }

        private MutableHolder(Optional<Component> name, Optional<Identifier> icon, Spell spell, SpellDataComponentMap spellData, byte activeShapeGroupIndex) {
            this.name = name;
            this.icon = icon;
            this.spell = spell;
            this.data = spellData;
            this.activeShapeGroupIndex = activeShapeGroupIndex;
        }

        @Override
        public Spell spell() {
            return spell;
        }

        @Override
        public Optional<Component> name() {
            return name;
        }

        @Override
        public void setName(@Nullable Component name) {
            this.name = Optional.ofNullable(name);
        }

        @Override
        public void clearName() {
            this.name = Optional.empty();
        }

        @Override
        public Optional<Identifier> icon() {
            return icon;
        }

        @Override
        public void setIcon(@Nullable Identifier icon) {
            this.icon = Optional.ofNullable(icon);
        }

        @Override
        public void clearIcon() {
            this.icon = Optional.empty();
        }

        @Override
        public SpellDataComponentMap spellData() {
            return data;
        }

        @Override
        public void updateSpellData(UnaryOperator<SpellDataComponentMap> update) {
            this.data = update.apply(this.data);
        }

        @Override
        public void setSpellData(SpellDataComponentMap data) {
            this.data = data;
        }

        @Override
        public byte activeShapeGroup() {
            return activeShapeGroupIndex;
        }

        @Override
        public void setActiveShapeGroup(byte activeShapeGroup) {
            this.activeShapeGroupIndex = activeShapeGroup;
        }

        @Override
        public void setFrom(SpellFacade spell) {
            this.name = spell.name();
            this.icon = spell.icon();
            this.data = spell.spellData();
            this.activeShapeGroupIndex = spell.activeShapeGroup();
        }
    }

    abstract class Delegating implements MutableSpellFacade {
        private final MutableHolder delegate;

        public Delegating(SpellFacade delegate) {
            this.delegate = delegate.asMutable();
        }

        public MutableHolder getDelegate() {
            return delegate;
        }

        @Override
        public void setName(@Nullable Component name) {
            delegate.setName(name);
        }

        @Override
        public void clearName() {
            delegate.clearName();
        }

        @Override
        public void setIcon(@Nullable Identifier icon) {
            delegate.setIcon(icon);
        }

        @Override
        public void clearIcon() {
            delegate.clearIcon();
        }

        @Override
        public void updateSpellData(UnaryOperator<SpellDataComponentMap> update) {
            delegate.updateSpellData(update);
        }

        @Override
        public void setSpellData(SpellDataComponentMap data) {
            delegate.setSpellData(data);
        }

        @Override
        public void setActiveShapeGroup(byte activeShapeGroup) {
            delegate.setActiveShapeGroup(activeShapeGroup);
        }

        @Override
        public void setFrom(SpellFacade spell) {
            delegate.setFrom(spell);
        }

        @Override
        public Spell spell() {
            return delegate.spell();
        }

        @Override
        public Optional<Component> name() {
            return delegate.name();
        }

        @Override
        public Optional<Identifier> icon() {
            return delegate.icon();
        }

        @Override
        public SpellDataComponentMap spellData() {
            return delegate.spellData();
        }

        @Override
        public byte activeShapeGroup() {
            return delegate.activeShapeGroup();
        }
    }
}
