package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ControlledParticle;
import at.minecraftschurli.arsmagicalegacy.api.client.ParticleSpawner;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization.SpellCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe.SpellRecipeScreen;
import at.minecraftschurli.arsmagicalegacy.client.particle.ParticleSpawnerManager;
import at.minecraftschurli.arsmagicalegacy.entity.FallingStar;
import at.minecraftschurli.arsmagicalegacy.entity.SpellEntity;
import at.minecraftschurli.arsmagicalegacy.entity.SpellShapeEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public final class AMClientUtil {
    private static final Map<SpellEntityKey, ParticleSpawner> SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS = new HashMap<>();

    private AMClientUtil() {
    }

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    public static LocalPlayer player() {
        return mc().player;
    }

    public static ClientLevel level() {
        return mc().level;
    }

    public static Font font() {
        return mc().font;
    }

    public static RegistryAccess registryAccess() {
        return level().registryAccess();
    }

    public static int getRedI(int color) {
        return 0xFF & color >> 16;
    }

    public static int getGreenI(int color) {
        return 0xFF & color >> 8;
    }

    public static int getBlueI(int color) {
        return 0xFF & color;
    }

    public static float getRedF(int color) {
        return getRedI(color) / 255f;
    }

    public static float getGreenF(int color) {
        return getGreenI(color) / 255f;
    }

    public static float getBlueF(int color) {
        return getBlueI(color) / 255f;
    }

    public static float[] rgbToHsb(int red, int green, int blue) {
        int max = Math.max(Math.max(red, green), blue);
        int min = Math.min(Math.min(red, green), blue);
        if (max == 0) return new float[]{0, 0, 0};
        float hue, saturation, brightness;
        brightness = max / 255f;
        saturation = (max - min) / (float) max;
        if (saturation == 0) {
            hue = 0;
        } else {
            float r = (float) (max - red) / (float) (max - min);
            float g = (float) (max - green) / (float) (max - min);
            float b = (float) (max - blue) / (float) (max - min);
            hue = red == max ? b - g : green == max ? 2f + r - b : 4f + g - r;
            hue /= 6f;
            if (hue < 0) {
                hue += 1f;
            }
        }
        return new float[]{hue, saturation, brightness};
    }

    public static int[] hsbToRgb(float hue, float saturation, float brightness) {
        if (saturation == 0) {
            int gray = (int) (brightness * 255f + 0.5f);
            return new int[]{gray, gray, gray};
        }
        float h = (hue - (float) Math.floor(hue)) * 6f;
        float f = h - (float) Math.floor(h);
        float p = brightness * (1f - saturation);
        float q = brightness * (1f - saturation * f);
        float t = brightness * (1f - (saturation * (1f - f)));
        return switch ((int) h) {
            case 0 -> new int[]{(int) (brightness * 255 + 0.5f), (int) (t * 255 + 0.5f), (int) (p * 255 + 0.5f)};
            case 1 -> new int[]{(int) (q * 255 + 0.5f), (int) (brightness * 255 + 0.5f), (int) (p * 255 + 0.5f)};
            case 2 -> new int[]{(int) (p * 255 + 0.5f), (int) (brightness * 255 + 0.5f), (int) (t * 255 + 0.5f)};
            case 3 -> new int[]{(int) (p * 255 + 0.5f), (int) (q * 255 + 0.5f), (int) (brightness * 255 + 0.5f)};
            case 4 -> new int[]{(int) (t * 255 + 0.5f), (int) (p * 255 + 0.5f), (int) (brightness * 255 + 0.5f)};
            case 5 -> new int[]{(int) (brightness * 255 + 0.5f), (int) (p * 255 + 0.5f), (int) (q * 255 + 0.5f)};
            default -> new int[]{0, 0, 0};
        };
    }

    public static void setOcculusScreen() {
        AMClientUtil.mc().setScreen(new OcculusScreen());
    }

    public static void setSpellCustomizationScreen(Spell spell) {
        AMClientUtil.mc().setScreen(new SpellCustomizationScreen(spell));
    }

    public static void setSpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        AMClientUtil.mc().setScreen(new SpellRecipeScreen(stack, playTurnSound, startPage, lecternPos));
    }

    public static List<? extends ControlledParticle> spawnParticles(ResourceLocation id, Vec3 position, int color, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        ParticleSpawner spawner = ParticleSpawnerManager.INSTANCE.get(id);
        return spawner != null ? ArsMagicaClientApi.spawnParticles(spawner, position, color, caster, directEntity, hitResult) : List.of();
    }

    public static void spawnFallingStarParticles(FallingStar entity, boolean ground) {
        Vec3 position = entity.position();
        int color = entity.getColor();
        LivingEntity owner = entity.getOwner();
        List<? extends ControlledParticle> list;
        if (ground) {
            list = IntStream.range(0, (int) entity.getDamage())
                .mapToObj($ -> spawnParticles(FallingStar.GROUND_PARTICLES, position, color, owner, entity, null))
                .flatMap(List::stream)
                .toList();
            int lifetime = (int) entity.getRange();
            for (int i = 0; i < list.size(); i++) {
                ControlledParticle particle = list.get(i);
                particle.setLifetime(lifetime);
                Vec3 speed = Vec3.directionFromRotation(0, (float) i / list.size() * 360).normalize();
                particle.setParticleSpeed(speed.x(), 0, speed.z());
            }
        } else {
            list = AMClientUtil.spawnParticles(FallingStar.FALL_PARTICLES, position, color, owner, entity, null);
        }
        if (color == -1) {
            list.forEach(particle -> particle.setColor(particle.random().nextInt(0xffffff)));
        }
    }

    public static void spawnSpellEntityParticles(SpellEntity entity, double range, double verticalRange, int color, @Nullable LivingEntity caster) {
        spawnParticles(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), entity.getEyePosition(), color, caster, entity, null).forEach(particle -> {
            RandomSource random = particle.random();
            particle.setParticleSpeed(Mth.lerp(random.nextDouble(), -range, range), random.nextDouble() * verticalRange, Mth.lerp(random.nextDouble(), -range, range));
        });
    }

    @SuppressWarnings("DataFlowIssue")
    public static void spawnSpellEntityParticles(SpellShapeEntity entity, Spell spell, Vec3 position, int color, @Nullable LivingEntity caster) {
        SpellEntityKey key = new SpellEntityKey(BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()), spell.grammar().primaryAffinity());
        SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.computeIfAbsent(key, k -> {
            ParticleSpawner spawner = ParticleSpawnerManager.INSTANCE.get(key.id);
            return new ParticleSpawner(entity.registryAccess().registryOrThrow(AMRegistryKeys.AFFINITY).get(key.affinity).particle(),
                spawner.count(),
                spawner.minLifetime(),
                spawner.maxLifetime(),
                spawner.minOffset(),
                spawner.maxOffset(),
                spawner.minSpeed(),
                spawner.maxSpeed(),
                spawner.gravity(),
                spawner.scale(),
                spawner.color(),
                spawner.alpha(),
                spawner.controllers());
        });
        ArsMagicaClientApi.spawnParticles(SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.get(key), position, color, caster, entity, null);
    }

    public static void clearParticleSpawnerCache() {
        SPELL_SHAPE_ENTITY_PARTICLE_SPAWNERS.clear();
    }

    private record SpellEntityKey(ResourceLocation id, ResourceKey<Affinity> affinity) {
    }
}
