package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceKey;

public interface AMRegistryKeys {
    ResourceKey<Registry<SpellPart>> SPELL_PART = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_part"));
    ResourceKey<Registry<DataComponentType<?>>> SPELL_DATA_COMPONENT = ResourceKey.createRegistryKey(ArsMagicaApi.modLoc("spell_data_component"));
}
