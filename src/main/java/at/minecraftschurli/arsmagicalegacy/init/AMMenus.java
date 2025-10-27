package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.menu.InscriptionTableMenu;
import at.minecraftschurli.arsmagicalegacy.menu.RuneBagMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMMenus {
    DeferredHolder<MenuType<?>, MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = AMRegistries.MENUS.register("inscription_table", () -> IMenuTypeExtension.create(InscriptionTableMenu::new));
    DeferredHolder<MenuType<?>, MenuType<RuneBagMenu>>          RUNE_BAG          = AMRegistries.MENUS.register("rune_bag",          () -> IMenuTypeExtension.create(RuneBagMenu::new));

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
