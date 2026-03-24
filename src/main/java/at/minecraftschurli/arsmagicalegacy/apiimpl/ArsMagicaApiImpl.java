package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.data.JsonDataManager;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Identifier ARCANE_COMPENDIUM = ArsMagicaApi.id("arcane_compendium");
    private static final AbilityHelper ABILITY_HELPER = new AbilityHelperImpl();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final MagicHelper MAGIC_HELPER = new MagicHelperImpl();
    private static final ManaHelper MANA_HELPER = new ManaHelperImpl();
    private static final SpellHelper SPELL_HELPER = new SpellHelperImpl();
    private static final AMDataManager<Plant> PLANT_MANAGER = new AMDataManager<>(id("plant"), Plant.CODEC);
    private static final AMDataManager<Ritual<?>> RITUAL_MANAGER = new AMDataManager<>(id("ritual"), Ritual.CODEC);
    private static final AMDataManager<SpellPartData> SPELL_PART_DATA_MANAGER = new AMDataManager<>(id("spell_part"), SpellPartData.CODEC);

    @Override
    protected ItemStackTemplate getBook() {
        return new ItemStackTemplate(Items.BOOK);
        // TODO patchouli return PatchouliAPI.get().getBookStackTemplate(ARCANE_COMPENDIUM);
    }

    @Override
    protected AbilityHelper getAbilityHelper() {
        return ABILITY_HELPER;
    }

    @Override
    protected BurnoutHelper getBurnoutHelper() {
        return BURNOUT_HELPER;
    }

    @Override
    protected MagicHelper getMagicHelper() {
        return MAGIC_HELPER;
    }

    @Override
    protected ManaHelper getManaHelper() {
        return MANA_HELPER;
    }

    @Override
    protected SpellHelper getSpellHelper() {
        return SPELL_HELPER;
    }

    @Override
    protected JsonDataManager<Plant> getPlantManager() {
        return PLANT_MANAGER;
    }

    @Override
    protected JsonDataManager<Ritual<?>> getRitualManager() {
        return RITUAL_MANAGER;
    }

    @Override
    protected JsonDataManager<SpellPartData> getSpellPartDataManager() {
        return SPELL_PART_DATA_MANAGER;
    }
}
