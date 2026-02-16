package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.data.JsonDataManager;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;

import java.util.ServiceLoader;

/**
 * The main entrypoint for the Ars Magica: Legacy API.
 */
@ApiStatus.NonExtendable
public abstract class ArsMagicaApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getGameLayer(), ArsMagicaApi.class).findFirst().orElseThrow());

    /**
     * The id of the Ars Magica: Legacy mod.
     */
    public static final String MOD_ID = "arsmagicalegacy";

    /**
     * Creates a new {@link ResourceLocation} with the mod's namespace.
     *
     * @param path The path of the {@link ResourceLocation}.
     * @return A new {@link ResourceLocation}.
     */
    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }

    /**
     * @return An Arcane Compendium {@link ItemStack}.
     */
    public static ItemStack book() {
        return INSTANCE.get().getBook();
    }

    /**
     * @return The {@link ManaHelper} instance.
     */
    public static AbilityHelper abilityHelper() {
        return INSTANCE.get().getAbilityHelper();
    }

    /**
     * @return The {@link ManaHelper} instance.
     */
    public static BurnoutHelper burnoutHelper() {
        return INSTANCE.get().getBurnoutHelper();
    }

    /**
     * @return The {@link ManaHelper} instance.
     */
    public static MagicHelper magicHelper() {
        return INSTANCE.get().getMagicHelper();
    }

    /**
     * @return The {@link ManaHelper} instance.
     */
    public static ManaHelper manaHelper() {
        return INSTANCE.get().getManaHelper();
    }

    /**
     * @return The {@link SpellHelper} instance.
     */
    public static SpellHelper spellHelper() {
        return INSTANCE.get().getSpellHelper();
    }

    /**
     * @return The {@link JsonDataManager} for {@link Plant}s.
     */
    public static JsonDataManager<Plant> plantManager() {
        return INSTANCE.get().getPlantManager();
    }

    /**
     * @return The {@link JsonDataManager} for {@link Ritual}s.
     */
    public static JsonDataManager<Ritual> ritualManager() {
        return INSTANCE.get().getRitualManager();
    }

    /**
     * @return The {@link JsonDataManager} for {@link SpellPartData}.
     */
    public static JsonDataManager<SpellPartData> spellPartDataManager() {
        return INSTANCE.get().getSpellPartDataManager();
    }

    @ApiStatus.Internal
    protected abstract ItemStack getBook();

    @ApiStatus.Internal
    protected abstract AbilityHelper getAbilityHelper();

    @ApiStatus.Internal
    protected abstract BurnoutHelper getBurnoutHelper();

    @ApiStatus.Internal
    protected abstract MagicHelper getMagicHelper();

    @ApiStatus.Internal
    protected abstract ManaHelper getManaHelper();

    @ApiStatus.Internal
    protected abstract SpellHelper getSpellHelper();

    @ApiStatus.Internal
    protected abstract JsonDataManager<Plant> getPlantManager();

    @ApiStatus.Internal
    protected abstract JsonDataManager<Ritual> getRitualManager();

    @ApiStatus.Internal
    protected abstract JsonDataManager<SpellPartData> getSpellPartDataManager();
}
