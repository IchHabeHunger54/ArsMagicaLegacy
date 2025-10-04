package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStatModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ColorModifier extends SpellModifier {
    public ColorModifier() {
        super(Map.of(SpellStat.COLOR, SpellStatModifier.NOOP));
    }

    @Override
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_COLOR.get();
    }
}
