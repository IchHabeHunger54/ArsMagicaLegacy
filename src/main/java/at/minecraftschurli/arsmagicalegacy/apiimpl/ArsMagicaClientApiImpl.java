package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.RegisterSpellIngredientRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.ModLoader;

import java.util.HashMap;
import java.util.Map;

public final class ArsMagicaClientApiImpl extends ArsMagicaClientApi {
    private static final Map<ResourceLocation, OcculusTabRenderer.Factory> OCCULUS_TAB_RENDERERS = new HashMap<>();
    private static final Map<SpellIngredient.Type<?>, SpellIngredientRenderer<?>> SPELL_INGREDIENT_RENDERERS = new HashMap<>();

    @Override
    protected OcculusTabRenderer.Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab) {
        return OCCULUS_TAB_RENDERERS.get(tab.value().renderer());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T extends SpellIngredient> SpellIngredientRenderer<T> getSpellIngredientRenderer(T ingredient) {
        return (SpellIngredientRenderer<T>) SPELL_INGREDIENT_RENDERERS.get(ingredient.type());
    }

    public static void postEvents() {
        OCCULUS_TAB_RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterOcculusTabRenderersEvent()).getRenderers());
        SPELL_INGREDIENT_RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterSpellIngredientRenderersEvent()).getRenderers());
    }
}
