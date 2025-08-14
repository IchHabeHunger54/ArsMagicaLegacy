package at.minecraftschurli.arsmagicalegacy.api.spell;

import at.minecraftschurli.arsmagicalegacy.api.AMRegistryKeys;
import net.minecraft.core.Registry;
import net.neoforged.neoforge.registries.RegistryBuilder;

public abstract sealed class SpellPart permits SpellComponent, SpellModifier, SpellShape {
    public abstract boolean isShape();

    public abstract boolean isComponent();

    public abstract boolean isModifier();
}
