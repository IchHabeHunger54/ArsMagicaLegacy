package at.minecraftschurli.arsmagicalegacy.api.data;

import at.minecraftschurli.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualEffect;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualRequirement;
import at.minecraftschurli.arsmagicalegacy.api.ritual.RitualTrigger;
import net.minecraft.resources.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Builder class for {@link Ritual}s, for use in {@link RitualProvider}. Get an instance via {@link RitualProvider#builder(String, RitualTrigger)}.
 */
public class RitualBuilder extends AbstractDataProvider.Builder<Ritual<?>> {
    private final List<RitualRequirement> requirements = new ArrayList<>();
    private final List<RitualEffect> effects = new ArrayList<>();
    private final RitualTrigger<?> trigger;

    /**
     * @param id      The id of the {@link Ritual} to generate data for.
     * @param trigger The {@link RitualTrigger} to use.
     */
    public RitualBuilder(Identifier id, RitualTrigger<?> trigger) {
        super(id);
        this.trigger = trigger;
    }

    /**
     * Adds a {@link RitualRequirement}.
     *
     * @param requirement The {@link RitualRequirement} to add.
     * @return This builder, for chaining.
     */
    public RitualBuilder addRequirement(RitualRequirement requirement) {
        requirements.add(requirement);
        return this;
    }

    /**
     * Adds a {@link RitualEffect}.
     *
     * @param effect The {@link RitualEffect} to add.
     * @return This builder, for chaining.
     */
    public RitualBuilder addEffect(RitualEffect effect) {
        effects.add(effect);
        return this;
    }

    @Override
    public Ritual<?> build() {
        return new Ritual<>(requirements, trigger, effects);
    }
}
