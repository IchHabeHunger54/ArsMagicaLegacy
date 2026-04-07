package at.minecraftschurli.mods.arsmagicalegacy.entity.ai;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ExecuteRandomSpellGoal<T extends AbstractBoss> extends ExecuteBossSpellGoal<T> {
    private final List<SpellFacade> spells;

    public ExecuteRandomSpellGoal(T caster, List<SpellFacade> spells, int duration) {
        super(caster, duration);
        this.spells = spells;
    }

    public static <T extends AbstractBoss> ExecuteRandomSpellGoal<T> of(T caster, int duration, Identifier... prefabSpellIds) {
        return new ExecuteRandomSpellGoal<>(caster, Arrays.stream(prefabSpellIds).map(id -> getSpellPrefab(caster, id)).filter(Objects::nonNull).toList(), duration);
    }

    @Override
    @Nullable
    protected SpellFacade getSpell(T caster) {
        return spells.get(caster.level().getRandom().nextInt(spells.size()));
    }
}
