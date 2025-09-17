package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.ServiceLoader;

/**
 * The client entrypoint for the Ars Magica: Legacy API.
 */
@ApiStatus.NonExtendable
public abstract class ArsMagicaClientApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaClientApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaClientApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaClientApi.class).findFirst().orElseThrow());

    /**
     * @param tab The {@link Holder} to get the {@link OcculusTabRenderer.Factory} for.
     * @return The {@link OcculusTabRenderer.Factory} for the specified {@link Holder}.
     */
    public static OcculusTabRenderer.Factory occulusTabRendererFactory(Holder<OcculusTab> tab) {
        return INSTANCE.get().getOcculusTabRendererFactory(tab);
    }

    /**
     * @param ingredient The {@link SpellIngredient} to get the {@link SpellIngredientRenderer} for.
     * @return The {@link SpellIngredientRenderer} for the given {@link SpellIngredient}.
     * @param <T> The exact type of the {@link SpellIngredient}.
     */
    public static <T extends SpellIngredient> SpellIngredientRenderer<T> spellIngredientRenderer(T ingredient) {
        return INSTANCE.get().getSpellIngredientRenderer(ingredient);
    }

    /**
     * @param spellPart The {@link SpellPart} to get the {@link SpellPartCustomizationScreen.Factory} for.
     * @return The {@link SpellPartCustomizationScreen.Factory} for the given {@link SpellPart}.
     */
    @Nullable
    public static SpellPartCustomizationScreen.Factory<?, ?> spellPartCustomizationScreen(Holder<SpellPart> spellPart) {
        return INSTANCE.get().getSpellPartCustomizationScreen(spellPart);
    }

    @ApiStatus.Internal
    protected abstract OcculusTabRenderer.Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab);

    @ApiStatus.Internal
    protected abstract <T extends SpellIngredient> SpellIngredientRenderer<T> getSpellIngredientRenderer(T ingredient);

    @ApiStatus.Internal
    @Nullable
    protected abstract SpellPartCustomizationScreen.Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart);
}
