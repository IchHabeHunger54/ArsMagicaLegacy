package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.SpellPartDataManager;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Registry<SpellPart> SPELL_PART_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_PART).sync(true).create();
    private static final Registry<DataComponentType<?>> SPELL_DATA_COMPONENT_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_DATA_COMPONENT).sync(true).create();
    private static final SpellPartDataManager SPELL_PART_DATA_MANAGER = new SpellPartDataManagerImpl();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final ManaHelper MANA_HELPER = new ManaHelperImpl();
    private static final SpellHelper SPELL_HELPER = new SpellHelperImpl();

    @Override
    protected Registry<SpellPart> _getSpellPartRegistry() {
        return SPELL_PART_REGISTRY;
    }

    @Override
    protected Registry<DataComponentType<?>> _getSpellDataComponentRegistry() {
        return SPELL_DATA_COMPONENT_REGISTRY;
    }

    @Override
    protected SpellPartDataManager _getSpellPartDataManager() {
        return SPELL_PART_DATA_MANAGER;
    }

    @Override
    protected BurnoutHelper _getBurnoutHelper() {
        return BURNOUT_HELPER;
    }

    @Override
    protected ManaHelper _getManaHelper() {
        return MANA_HELPER;
    }

    @Override
    protected SpellHelper _getSpellHelper() {
        return SPELL_HELPER;
    }
}
