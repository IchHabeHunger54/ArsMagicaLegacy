package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.RegistryBuilder;
import vazkii.patchouli.api.PatchouliAPI;

public final class ArsMagicaApiImpl extends ArsMagicaApi {
    private static final ResourceLocation ARCANE_COMPENDIUM = ArsMagicaApi.modLoc("arcane_compendium");
    private static final Registry<SpellPart> SPELL_PART_REGISTRY = new RegistryBuilder<>(AMRegistries.SPELL_PART).sync(true).create();
    private static final Registry<MapCodec<? extends SpellIngredient>> SPELL_INGREDIENT_REGISTRY = new RegistryBuilder<>(AMRegistries.SPELL_INGREDIENT).sync(true).create();
    private static final Registry<MapCodec<? extends AbilityEffect>> ABILITY_EFFECT_REGISTRY = new RegistryBuilder<>(AMRegistries.ABILITY_EFFECT).sync(true).create();
    private static final AbilityHelper ABILITY_HELPER = new AbilityHelperImpl();
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
    protected Registry<MapCodec<? extends SpellIngredient>> getSpellIngredientRegistry() {
        return SPELL_INGREDIENT_REGISTRY;
    }

    @Override
    protected Registry<MapCodec<? extends AbilityEffect>> getAbilityEffectRegistry() {
        return ABILITY_EFFECT_REGISTRY;
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
}
