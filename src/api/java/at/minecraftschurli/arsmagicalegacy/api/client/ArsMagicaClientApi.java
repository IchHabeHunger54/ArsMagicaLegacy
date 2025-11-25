package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.util.Lazy;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.List;
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
     * @param spellPart The {@link SpellPart} to get the {@link SpellPartCustomizationScreen.Factory} for.
     * @return The {@link SpellPartCustomizationScreen.Factory} for the given {@link SpellPart}.
     */
    @Nullable
    public static SpellPartCustomizationScreen.Factory<?, ?> spellPartCustomizationScreen(Holder<SpellPart> spellPart) {
        return INSTANCE.get().getSpellPartCustomizationScreen(spellPart);
    }

    /**
     * @param spawner      The {@link ParticleSpawner} to use.
     * @param position     The position of the particles.
     * @param color        The particle color to use. Use -1 to not set a color.
     * @param caster       The {@link LivingEntity} casting the {@link Spell}. May be null if this is not called from a spell cast.
     * @param directEntity The entity applying the {@link Spell}, e.g. a projectile. May or may not be identical to the caster. May be null if this is not called from a spell cast.
     * @param hitResult    The {@link HitResult} of the spell cast. May be null if this is not called from a spell cast.
     * @return A {@link List} of the {@link ControlledParticle}s that were created.
     */
    public static List<? extends ControlledParticle> spawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        return INSTANCE.get().doSpawnParticles(spawner, position, color, caster, directEntity, hitResult);
    }

    @ApiStatus.Internal
    @Nullable
    protected abstract OcculusTabRenderer.Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab);

    @ApiStatus.Internal
    @Nullable
    protected abstract ParticleController.Type getParticleController(ResourceLocation id);

    @ApiStatus.Internal
    @Nullable
    protected abstract SpellPartCustomizationScreen.Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart);

    @ApiStatus.Internal
    protected abstract List<? extends ControlledParticle> doSpawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult);
}
