package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe.SpellRecipeScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class AMClientUtil {
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

    public static float getBlue(int color) {
        return (0xFF & color) / 255f;
    }

    public static float getGreen(int color) {
        return (0xFF & (color >> 8)) / 255f;
    }

    public static float getRed(int color) {
        return (0xFF & (color >> 16)) / 255f;
    }

    public static void setOcculusScreen() {
        AMClientUtil.mc().setScreen(new OcculusScreen());
    }

    public static void setSpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        AMClientUtil.mc().setScreen(new SpellRecipeScreen(stack, playTurnSound, startPage, lecternPos));
    }
}
