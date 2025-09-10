package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityEffect;
import at.minecraftschurli.arsmagicalegacy.api.ability.AbilityHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
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
    public static ResourceLocation modLoc(String path) {
        return ResourceLocation.fromNamespaceAndPath(ArsMagicaApi.MOD_ID, path);
    }

    /**
     * @return An Arcane Compendium {@link ItemStack}.
     */
    public static ItemStack book() {
        return INSTANCE.get().getBook();
    }

    /**
     * @return The spell part registry.
     */
    public static Registry<SpellPart> spellPartRegistry() {
        return INSTANCE.get().getSpellPartRegistry();
    }

    /**
     * @return The spell data component registry.
     */
    public static Registry<DataComponentType<?>> spellDataComponentRegistry() {
        return INSTANCE.get().getSpellDataComponentRegistry();
    }

    /**
     * @return The spell ingredient registry.
     */
    public static Registry<MapCodec<? extends SpellIngredient>> spellIngredientRegistry() {
        return INSTANCE.get().getSpellIngredientRegistry();
    }

    /**
     * @return The ability effect registry.
     */
    public static Registry<MapCodec<? extends AbilityEffect>> abilityEffectRegistry() {
        return INSTANCE.get().getAbilityEffectRegistry();
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
     * @param part The {@link SpellPart} to get the {@link SpellPartData} for.
     * @return A {@link SpellPartData} instance.
     */
    public static SpellPartData spellPartData(SpellPart part) {
        return INSTANCE.get().getSpellPartData(part);
    }

    @ApiStatus.Internal
    protected abstract ItemStack getBook();

    @ApiStatus.Internal
    protected abstract Registry<SpellPart> getSpellPartRegistry();

    @ApiStatus.Internal
    protected abstract Registry<DataComponentType<?>> getSpellDataComponentRegistry();

    @ApiStatus.Internal
    protected abstract Registry<MapCodec<? extends SpellIngredient>> getSpellIngredientRegistry();

    @ApiStatus.Internal
    protected abstract Registry<MapCodec<? extends AbilityEffect>> getAbilityEffectRegistry();

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
    protected abstract SpellPartData getSpellPartData(SpellPart part);
}
