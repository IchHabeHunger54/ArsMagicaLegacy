package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
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
    @Nullable
    public static OcculusTabRenderer.Factory occulusTabRendererFactory(Holder<OcculusTab> tab) {
        return INSTANCE.get().getOcculusTabRendererFactory(tab);
    }

    /**
     * @param id The id of the {@link ParticleController} to get.
     * @return The {@link ParticleController} for the given id.
     */
    @Nullable
    public static ParticleController.Type particleController(ResourceLocation id) {
        return INSTANCE.get().getParticleController(id);
    }

    /**
     * @param ingredient The {@link SpellIngredient} to get the {@link SpellIngredientRenderer} for.
     * @return The {@link SpellIngredientRenderer} for the given {@link SpellIngredient}.
     * @param <T> The exact type of the {@link SpellIngredient}.
     */
    @Nullable
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

    /**
     * @param id       The id of the {@link ParticleSpawner} to use.
     * @param position The position of the particles.
     * @param color    The particle color to use. Use -1 to not set a color.
     */
    public static void spawnParticles(ResourceLocation id, Vec3 position, int color) {
        INSTANCE.get().doSpawnParticles(id, position, color);
    }

    @ApiStatus.Internal
    @Nullable
    protected abstract OcculusTabRenderer.Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab);

    @ApiStatus.Internal
    @Nullable
    protected abstract ParticleController.Type getParticleController(ResourceLocation id);

    @ApiStatus.Internal
    @Nullable
    protected abstract <T extends SpellIngredient> SpellIngredientRenderer<T> getSpellIngredientRenderer(T ingredient);

    @ApiStatus.Internal
    @Nullable
    protected abstract SpellPartCustomizationScreen.Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart);

    @ApiStatus.Internal
    protected abstract void doSpawnParticles(ResourceLocation id, Vec3 position, int color);
}
