package at.minecraftschurli.mods.arsmagicalegacy.api;

import at.minecraftschurli.mods.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.data.JsonDataManager;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.mods.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.mods.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellHelper;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

import java.util.ServiceLoader;

/**
 * The main entrypoint for the Ars Magica: Legacy API.
 */
@NonExtendable
public abstract class ArsMagicaApi {
    /**
     * A {@link Lazy} that holds the {@link ArsMagicaApi} instance retrieved from the {@link ServiceLoader}. DO NOT ACCESS YOURSELF!
     */
    private static final Lazy<ArsMagicaApi> INSTANCE = Lazy.of(() -> ServiceLoader.load(FMLLoader.getCurrent().getGameLayer(), ArsMagicaApi.class).findFirst().orElseThrow());

    /**
     * The id of the Ars Magica: Legacy mod.
     */
    public static final String MOD_ID = "arsmagicalegacy";

    /**
     * Creates a new {@link Identifier} with the mod's namespace.
     *
     * @param path The path of the {@link Identifier}.
     * @return A new {@link Identifier}.
     */
    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }

    /**
     * @return An Arcane Compendium {@link ItemStack}.
     */
    public static ItemStackTemplate book() {
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
    public static JsonDataManager<Ritual<?>> ritualManager() {
        return INSTANCE.get().getRitualManager();
    }

    @Internal
    protected abstract ItemStackTemplate getBook();

    @Internal
    protected abstract AbilityHelper getAbilityHelper();

    @Internal
    protected abstract BurnoutHelper getBurnoutHelper();

    @Internal
    protected abstract MagicHelper getMagicHelper();

    @Internal
    protected abstract ManaHelper getManaHelper();

    @Internal
    protected abstract SpellHelper getSpellHelper();

    @Internal
    protected abstract JsonDataManager<Plant> getPlantManager();

    @Internal
    protected abstract JsonDataManager<Ritual<?>> getRitualManager();
}
