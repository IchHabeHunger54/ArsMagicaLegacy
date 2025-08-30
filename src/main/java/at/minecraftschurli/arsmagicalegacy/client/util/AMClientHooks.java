package at.minecraftschurli.arsmagicalegacy.client.util;

import at.minecraftschurli.arsmagicalegacy.client.gui.occulus.OcculusScreen;
import at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe.SpellRecipeScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public final class AMClientHooks {
    private AMClientHooks() {
    }

    public static void setOcculusScreen() {
        ClientUtil.mc().setScreen(new OcculusScreen());
    }

    public static void setSpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        ClientUtil.mc().setScreen(new SpellRecipeScreen(stack, playTurnSound, startPage, lecternPos));
    }
}
