package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.spell.SpellPartDataManager;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.RegistryBuilder;
import vazkii.patchouli.api.PatchouliAPI;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final ResourceLocation ARCANE_COMPENDIUM = ArsMagicaApi.modLoc("arcane_compendium");
    private static final Registry<SpellPart> SPELL_PART_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_PART).sync(true).create();
    private static final Registry<DataComponentType<?>> SPELL_DATA_COMPONENT_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_DATA_COMPONENT).sync(true).create();
    private static final Registry<SpellIngredient.Type<?>> SPELL_INGREDIENT_REGISTRY = new RegistryBuilder<>(AMRegistryKeys.SPELL_INGREDIENT).sync(true).create();
    private static final BurnoutHelper BURNOUT_HELPER = new BurnoutHelperImpl();
    private static final MagicHelper MAGIC_HELPER = new MagicHelperImpl();
    private static final ManaHelper MANA_HELPER = new ManaHelperImpl();
    private static final SpellHelper SPELL_HELPER = new SpellHelperImpl();

    @Override
    protected ItemStack getBook() {
        return PatchouliAPI.get().getBookStack(ARCANE_COMPENDIUM);
    }

    @Override
    protected Registry<SpellPart> getSpellPartRegistry() {
        return SPELL_PART_REGISTRY;
    }

    @Override
    protected Registry<DataComponentType<?>> getSpellDataComponentRegistry() {
        return SPELL_DATA_COMPONENT_REGISTRY;
    }

    @Override
    protected Registry<SpellIngredient.Type<?>> getSpellIngredientRegistry() {
        return SPELL_INGREDIENT_REGISTRY;
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
    protected SpellPartData getSpellPartData(SpellPart part) {
        return SpellPartDataManager.INSTANCE.getOrDefault(spellPartRegistry().getKey(part), SpellPartData.DEFAULT);
    }
}
