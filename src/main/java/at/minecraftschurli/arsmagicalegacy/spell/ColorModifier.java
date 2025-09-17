package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStatModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.component.DataComponentType;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ColorModifier extends SpellModifier {
    public ColorModifier() {
        super(Map.of(AMSpells.COLOR_STAT, SpellStatModifier.NOOP));
    }

    @Override
    @Nullable
    public DataComponentType<?> getDataComponentType() {
        return AMSpells.COLOR_COMPONENT.get();
    }
}
