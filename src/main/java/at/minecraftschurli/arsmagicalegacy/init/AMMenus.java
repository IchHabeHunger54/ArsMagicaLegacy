package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.block.inscriptiontable.InscriptionTableMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMMenus {
    DeferredHolder<MenuType<?>, MenuType<InscriptionTableMenu>> INSCRIPTION_TABLE = AMRegistries.MENUS.register("inscription_table", () -> IMenuTypeExtension.create(InscriptionTableMenu::new));

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
