package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleController;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleSpawner;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellIngredientRenderer;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterOcculusTabRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellIngredientRenderersEvent;
import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterSpellPartCustomizationScreensEvent;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.client.particle.AMParticle;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.fml.ModLoader;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ArsMagicaClientApiImpl extends ArsMagicaClientApi {
    private static final Map<ResourceLocation, OcculusTabRenderer.Factory> OCCULUS_TAB_RENDERERS = new HashMap<>();
    private static final Map<ResourceLocation, ParticleController.Type> PARTICLE_CONTROLLERS = new HashMap<>();
    private static final Map<MapCodec<? extends SpellIngredient>, SpellIngredientRenderer<?>> SPELL_INGREDIENT_RENDERERS = new HashMap<>();
    private static final Map<Holder<SpellPart>, SpellPartCustomizationScreen.Factory<?, ?>> SPELL_PART_CUSTOMIZATION_SCREENS = new HashMap<>();

    @Override
    @Nullable
    protected OcculusTabRenderer.Factory getOcculusTabRendererFactory(Holder<OcculusTab> tab) {
        return OCCULUS_TAB_RENDERERS.get(tab.value().renderer());
    }

    @Override
    @Nullable
    protected ParticleController.Type getParticleController(ResourceLocation id) {
        return PARTICLE_CONTROLLERS.get(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    @Nullable
    protected <T extends SpellIngredient> SpellIngredientRenderer<T> getSpellIngredientRenderer(T ingredient) {
        return (SpellIngredientRenderer<T>) SPELL_INGREDIENT_RENDERERS.get(ingredient.codec());
    }

    @Override
    @Nullable
    protected SpellPartCustomizationScreen.Factory<?, ?> getSpellPartCustomizationScreen(Holder<SpellPart> spellPart) {
        return SPELL_PART_CUSTOMIZATION_SCREENS.get(spellPart);
    }

    @Override
    protected List<? extends ControlledParticle> doSpawnParticles(ParticleSpawner spawner, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        return AMParticle.spawn(AMClientUtil.level(), position.x(), position.y(), position.z(), spawner, color, caster, directEntity, hitResult);
    }

    public static void postEvents() {
        OCCULUS_TAB_RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterOcculusTabRenderersEvent()).getRenderers());
        PARTICLE_CONTROLLERS.putAll(ModLoader.postEventWithReturn(new RegisterParticleControllersEvent()).getControllers());
        SPELL_INGREDIENT_RENDERERS.putAll(ModLoader.postEventWithReturn(new RegisterSpellIngredientRenderersEvent()).getRenderers());
        SPELL_PART_CUSTOMIZATION_SCREENS.putAll(ModLoader.postEventWithReturn(new RegisterSpellPartCustomizationScreensEvent()).getScreens());
    }
}
