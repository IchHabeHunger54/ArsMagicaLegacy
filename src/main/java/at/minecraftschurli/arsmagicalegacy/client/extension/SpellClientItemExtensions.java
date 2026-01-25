package at.minecraftschurli.arsmagicalegacy.client.extension;

import at.minecraftschurli.arsmagicalegacy.client.renderer.SpellItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;

public class SpellClientItemExtensions implements IClientItemExtensions {
    public static final SpellClientItemExtensions INSTANCE = new SpellClientItemExtensions();

    @Override
    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
        return SpellItemRenderer.INSTANCE;
    }
}
