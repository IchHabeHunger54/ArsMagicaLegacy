package at.minecraftschurli.mods.arsmagicalegacy.apiimpl;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.JsonDataManager;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMDataManager;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStackTemplate;
import vazkii.patchouli.api.PatchouliAPI;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final Identifier ARCANE_COMPENDIUM = id("arcane_compendium");
    private static final AbilityHelper ABILITY_HELPER = new AbilityHelperImpl();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final MagicHelper MAGIC_HELPER = new MagicHelperImpl();
    private static final ManaHelper MANA_HELPER = new ManaHelperImpl();
    private static final SpellHelper SPELL_HELPER = new SpellHelperImpl();
    private static final AMDataManager<Plant> PLANT_MANAGER = new AMDataManager<>(id("plant"), Plant.CODEC);
    private static final AMDataManager<Ritual<?>> RITUAL_MANAGER = new AMDataManager<>(id("ritual"), Ritual.CODEC);
    private static final AMDataManager<SpellPartData> SPELL_PART_DATA_MANAGER = new AMDataManager<>(id("spell_part"), SpellPartData.CODEC);
    private static final AMDataManager<Spell> SPELL_PREFAB_DATA_MANAGER = new AMDataManager<>(id("spell_prefab"), Spell.CODEC);

    @SuppressWarnings("DataFlowIssue")
    @Override
    protected ItemStackTemplate getBook() {
        return PatchouliAPI.get().getBookStackTemplate(ARCANE_COMPENDIUM);
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

    @Override
    protected JsonDataManager<Spell> getSpellPrefabManager() {
        return SPELL_PREFAB_DATA_MANAGER;
    }
}
