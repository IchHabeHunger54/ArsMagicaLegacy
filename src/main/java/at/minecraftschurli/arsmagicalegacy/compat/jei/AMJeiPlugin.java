package at.minecraftschurli.arsmagicalegacy.compat.jei;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.ISubtypeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public final class AMJeiPlugin implements IModPlugin {
    private static final ResourceLocation ID = ArsMagicaApi.modLoc(ArsMagicaApi.MOD_ID);

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerItemSubtypes(ISubtypeRegistration registration) {
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.INFINITY_ORB.get(), DataComponentSubtypeInterpreter.SKILL_POINT);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_ESSENCE.get(), DataComponentSubtypeInterpreter.AFFINITY);
        registration.registerSubtypeInterpreter(VanillaTypes.ITEM_STACK, AMItems.AFFINITY_TOME.get(), DataComponentSubtypeInterpreter.AFFINITY);
    }
}
