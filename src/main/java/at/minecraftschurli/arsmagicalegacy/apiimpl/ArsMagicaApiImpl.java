package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Registry<SpellPart> SPELL_PART_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_PART).sync(true).create();
    private static final Registry<DataComponentType<?>> SPELL_DATA_COMPONENT_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_DATA_COMPONENT).sync(true).create();

    @Override
    protected Registry<SpellPart> _getSpellPartRegistry() {
        return SPELL_PART_REGISTRY;
    }

    @Override
    protected Registry<DataComponentType<?>> _getSpellDataComponentRegistry() {
        return SPELL_DATA_COMPONENT_REGISTRY;
    }

    @Override
    protected SpellHelper _getSpellHelper() {
        return SpellHelperImpl.INSTANCE;
    }
}
