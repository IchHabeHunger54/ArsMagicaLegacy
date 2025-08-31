package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.packet.SetLecternPagePacket;
import at.minecraftschurli.arsmagicalegacy.packet.TakeSpellRecipeFromLecternPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.PageButton;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpellRecipeScreen extends Screen {
    private static final ResourceLocation BACKGROUND = ArsMagicaApi.modLoc("textures/gui/spell_recipe.png");
    private static final int WIDTH = 192;
    private static final int HEIGHT = 192;
    private final List<Page<?>> pages = new ArrayList<>();
    private final boolean playTurnSound;
    private final int startPage;
    @Nullable
    private final BlockPos lecternPos;
    private int currentPage = -1;
    private int cachedPage = -1;
    private int xPos;
    private PageButton forwardButton;
    private PageButton backButton;

    public SpellRecipeScreen(ItemStack stack, boolean playTurnSound, int startPage, @Nullable BlockPos lecternPos) {
        super(stack.getDisplayName());
        this.playTurnSound = playTurnSound;
        this.startPage = startPage;
        this.lecternPos = lecternPos;
        Spell spell = stack.getOrDefault(AMDataComponents.SPELL, Spell.EMPTY);
        pages.add(new IngredientsPage(ArsMagicaApi.spellHelper().getFlatRecipe(spell)));
        List<SpellShapeGroup> shapeGroups = spell.shapeGroups();
        for (int i = 0; i < shapeGroups.size(); i++) {
            SpellShapeGroup shapeGroup = shapeGroups.get(i);
            if (!shapeGroup.isEmpty()) {
                pages.add(new PartsPage(shapeGroup.parts(), Component.translatable(AMTranslations.SPELL_RECIPE_SHAPE_GROUP_KEY, i + 1)));
            }
        }
        pages.add(new PartsPage(spell.grammar().parts(), AMTranslations.SPELL_RECIPE_GRAMMAR));
        pages.add(new AffinityPage(spell.grammar().affinityShifts()));
    }

    @Override
    protected void init() {
        xPos = (width - WIDTH) / 2;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose()).bounds(width / 2 - 100, 196, lecternPos == null ? 200 : 98, 20).build());
        if (lecternPos != null) {
            addRenderableWidget(Button.builder(Component.translatable("lectern.take_book"), $ -> {
                PacketDistributor.sendToServer(new TakeSpellRecipeFromLecternPacket(lecternPos));
                onClose();
            }).pos(this.width / 2 + 2, 196).size(98, 20).build());
        }
        forwardButton = addRenderableWidget(new PageButton(xPos + 116, 159, true, p -> setPage(currentPage + 1), playTurnSound));
        backButton = addRenderableWidget(new PageButton(xPos + 43, 159, false, p -> setPage(currentPage - 1), playTurnSound));
        setPage(startPage);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(BACKGROUND, xPos, 2, 0, 0, WIDTH, HEIGHT);
        Page<?> page = pages.get(currentPage);
        String title = page.getTitle().getString();
        guiGraphics.drawString(font, title, xPos + 93 - font.width(title) / 2, 18, 0, false);
        page.render(guiGraphics, xPos + 36, 32);
        if (cachedPage != currentPage) {
            cachedPage = currentPage;
        }
        for (Renderable renderable : renderables) {
            renderable.render(guiGraphics, mouseX, mouseY, partialTick);
        }
        List<Component> tooltip = page.getTooltip(mouseX - xPos - 36, mouseY - 32);
        if (!tooltip.isEmpty()) {
            guiGraphics.renderTooltip(font, tooltip, Optional.empty(), mouseX, mouseY);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (super.keyPressed(keyCode, scanCode, modifiers)) return true;
        if (keyCode == GLFW.GLFW_KEY_PAGE_UP) {
            backButton.onPress();
            return true;
        }
        if (keyCode == GLFW.GLFW_KEY_PAGE_DOWN) {
            forwardButton.onPress();
            return true;
        }
        return false;
    }

    @Override
    public boolean handleComponentClicked(@Nullable Style style) {
        if (style == null) return false;
        ClickEvent clickevent = style.getClickEvent();
        if (clickevent == null) return false;
        if (clickevent.getAction() == ClickEvent.Action.CHANGE_PAGE) {
            try {
                return setPage(Integer.parseInt(clickevent.getValue()) - 1);
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return super.handleComponentClicked(style);
    }

    private boolean setPage(int pPageNum) {
        int i = Math.clamp(pPageNum, 0, pages.size() - 1);
        if (i == currentPage) return false;
        currentPage = i;
        cachedPage = -1;
        forwardButton.visible = currentPage < pages.size() - 1;
        backButton.visible = currentPage > 0;
        if (lecternPos != null) {
            PacketDistributor.sendToServer(new SetLecternPagePacket(lecternPos, currentPage));
        }
        return true;
    }
}
