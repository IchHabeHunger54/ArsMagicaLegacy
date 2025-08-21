package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.helper.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.helper.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.helper.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.spell.SpellPartDataManager;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.neoforged.neoforge.registries.RegistryBuilder;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Registry<SpellPart> SPELL_PART_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_PART).sync(true).create();
    private static final Registry<DataComponentType<?>> SPELL_DATA_COMPONENT_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_DATA_COMPONENT).sync(true).create();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final MagicHelper MAGIC_HELPER = new MagicHelperImpl();
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
    protected BurnoutHelper _getBurnoutHelper() {
        return BURNOUT_HELPER;
    }

    @Override
    protected MagicHelper _getMagicHelper() {
        return MAGIC_HELPER;
    }

    @Override
    protected ManaHelper _getManaHelper() {
        return MANA_HELPER;
    }

    @Override
    protected SpellHelper _getSpellHelper() {
        return SPELL_HELPER;
    }

    @Override
    protected SpellPartData _getSpellPartData(SpellPart part) {
        return SpellPartDataManager.INSTANCE.getOrDefault(getSpellPartRegistry().getKey(part), SpellPartData.DEFAULT);
    }
}
