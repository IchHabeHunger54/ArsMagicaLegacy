package at.minecraftschurli.arsmagicalegacy.client.layer;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.client.AMClientConfig;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.SpellBookItem;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public class SpellBookLayer implements LayeredDraw.Layer {
    private static final ResourceLocation TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/overlay.png");
    private static final ResourceLocation HIGHLIGHT_TEXTURE = ArsMagicaApi.id("textures/gui/spell_book/highlight.png");

    @Override
    public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
        if (AMClientUtil.mc().options.hideGui) return;
        Player player = AMClientUtil.player();
        if (player == null) return;
        ItemStack item = player.getMainHandItem();
        if (!item.is(AMItems.SPELL_BOOK)) {
            item = player.getOffhandItem();
            if (!item.is(AMItems.SPELL_BOOK)) return;
        }
        int index = item.getOrDefault(AMDataComponents.SELECTED_INDEX, -1);
        if (index < 0 || index >= SpellBookItem.HOTBAR_SLOTS) return;
        ItemContainerContents container = item.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        int x = AMClientConfig.SPELL_BOOK_X_ANCHOR.get().getLocation(AMClientConfig.SPELL_BOOK_X);
        int y = AMClientConfig.SPELL_BOOK_Y_ANCHOR.get().getLocation(AMClientConfig.SPELL_BOOK_Y);
        PoseStack stack = guiGraphics.pose();
        stack.pushPose();
        stack.translate(x, y, 0);
        stack.scale(0.75f, 0.75f, 0.75f);
        guiGraphics.blit(TEXTURE, 0, 0, 0, 0, 0, 148, 22, 148, 22);
        for (int i = 0; i < Math.min(container.getSlots(), SpellBookItem.HOTBAR_SLOTS); i++) {
            AMClientUtil.renderItem(guiGraphics, container.getStackInSlot(i), i * 18 + 3, 3);
        }
        guiGraphics.blit(HIGHLIGHT_TEXTURE, index * 18 + 1, 1, 0, 0, 0, 20, 20, 20, 20);
        stack.popPose();
    }
}
