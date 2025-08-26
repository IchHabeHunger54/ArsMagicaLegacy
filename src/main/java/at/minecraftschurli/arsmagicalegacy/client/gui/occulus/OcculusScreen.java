package at.minecraftschurli.arsmagicalegacy.client.gui.occulus;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.OcculusTabRenderer;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.packet.ForgetSkillsPacket;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OcculusScreen extends Screen {
    private static final ResourceLocation BUTTON_INDICATOR = ArsMagicaApi.modLoc("textures/gui/occulus/tab_button_indicator.png");
    private static final ResourceLocation FRAME = ArsMagicaApi.modLoc("textures/gui/occulus/frame.png");
    private static final ResourceLocation SKILL_POINTS = ArsMagicaApi.modLoc("textures/gui/occulus/skill_points.png");
    private static final int SIZE = 210;
    private static final int FRAME_SIZE = 7;
    private final List<Holder<OcculusTab>> tabs = new ArrayList<>();
    private final List<OcculusTabButton> buttons = new ArrayList<>();
    private Button nextButton;
    private Button prevButton;
    private OcculusTabRenderer renderer;
    private int posX;
    private int posY;
    private int tabX;
    private int tabY;
    private int tab = 0;
    private int page = 0;
    private int maxPage = 0;

    public OcculusScreen() {
        super(AMTranslations.OCCULUS);
    }

    @Override
    protected void init() {
        posX = (width - SIZE) / 2;
        posY = (height - SIZE - OcculusTabButton.SIZE - 24) / 2;
        tabX = posX + FRAME_SIZE;
        tabY = posY + OcculusTabButton.SIZE + FRAME_SIZE;
        tabs.clear();
        buttons.clear();
        LocalPlayer player = ClientUtil.player();
        Registry<OcculusTab> registry = ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.OCCULUS_TAB);
        List<? extends Holder<OcculusTab>> list = registry
            .holders()
            .sorted(Comparator.comparingInt(e -> e.value().index()))
            .toList();
        if (list.isEmpty()) return;
        tabs.addAll(list);
        if (list.size() < 10) {
            // we don't need page buttons
            maxPage = 0;
            for (int i = 0; i < list.size(); i++) {
                final int j = i;
                buttons.add(addRenderableWidget(new OcculusTabButton(list.get(i), posX + 6 + i * OcculusTabButton.SIZE, posY, $ -> setTab(j))));
            }
        } else {
            // we need page buttons
            maxPage = list.size() / 7;
            for (int i = 0; i < list.size(); i++) {
                final int j = i;
                buttons.add(addRenderableWidget(new OcculusTabButton(list.get(i), posX + 28 + i % 7 * OcculusTabButton.SIZE, posY, $ -> setTab(j))));
            }
            nextButton = Button.builder(AMTranslations.OCCULUS_NEXT, $ -> nextPage()).bounds(posX + SIZE - 20, posY, 20, 20).build();
            prevButton = Button.builder(AMTranslations.OCCULUS_PREV, $ -> prevPage()).bounds(posX, posY, 20, 20).build();
            onPageChange();
        }
        setRenderer(tabs.getFirst());
        Button button = addRenderableWidget(Button.builder(AMTranslations.OCCULUS_FORGET_ALL, $ -> forgetAll())
            .bounds(width / 2 - 100, posY + SIZE + OcculusTabButton.SIZE + 4, 98, 20)
            .tooltip(Tooltip.create(AMTranslations.OCCULUS_FORGET_ALL_TOOLTIP))
            .build());
        button.active = player.getInventory().contains(AMTags.Items.OCCULUS_FORGET_ALL) || player.isCreative();
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, $ -> onClose())
            .bounds(width / 2 + 2, posY + SIZE + OcculusTabButton.SIZE + 4, 98, 20)
            .build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.blit(FRAME, posX, posY + OcculusTabButton.SIZE, 0, 0, SIZE, SIZE);
        guiGraphics.blit(BUTTON_INDICATOR, maxPage == 0 ? posX + 6 + tab * OcculusTabButton.SIZE : posX + 28 + tab % 7 * OcculusTabButton.SIZE, posY + OcculusTabButton.SIZE, 0, 0, OcculusTabButton.SIZE, FRAME_SIZE, OcculusTabButton.SIZE, FRAME_SIZE);
        if (renderer.hasSkillPointPanel()) {
            List<Holder.Reference<SkillPoint>> holders = ClientUtil.registryAccess()
                .registryOrThrow(AMRegistryKeys.SKILL_POINT)
                .holders()
                .toList();
            List<MutableComponent> components = holders
                .stream()
                .map(e -> ArsMagicaApi.magicHelper().getSkillPoint(ClientUtil.player(), e))
                .map(String::valueOf)
                .map(Component::literal)
                .toList();
            int width = 24 + components.stream()
                .mapToInt(ClientUtil.font()::width)
                .max()
                .orElse(0);
            int height = components.size() * 16 + 4;
            guiGraphics.blit(SKILL_POINTS, posX - width, posY + OcculusTabButton.SIZE, 0, 0, width, height);
            guiGraphics.blit(SKILL_POINTS, posX - width, posY + OcculusTabButton.SIZE + height, 0, 252, width, 4);
            for (int i = 0; i < holders.size(); i++) {
                Holder<SkillPoint> holder = holders.get(i);
                ItemStack stack = AMItems.INFINITY_ORB.toStack();
                stack.set(AMDataComponents.SKILL_POINT, holder);
                guiGraphics.renderItem(stack, posX - width + 4, posY + OcculusTabButton.SIZE + 4 + i * 16);
                guiGraphics.drawString(ClientUtil.font(), components.get(i), posX - width + 22, posY + OcculusTabButton.SIZE + 9 + i * 16, holder.value().color(), false);
            }
        }
        guiGraphics.enableScissor(tabX, tabY, tabX + OcculusTabRenderer.TAB_SIZE, tabY + OcculusTabRenderer.TAB_SIZE);
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(tabX, tabY, 0);
        renderer.render(guiGraphics, mouseX - tabX, mouseY - tabY, partialTick);
        guiGraphics.pose().popPose();
        guiGraphics.disableScissor();
        if (mouseX >= tabX && mouseX < tabX + OcculusTabRenderer.TAB_SIZE && mouseY >= tabY && mouseY < tabY + OcculusTabRenderer.TAB_SIZE) {
            renderer.renderTooltip(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void setTab(int tab) {
        if (this.tab == tab) return;
        this.tab = tab;
        setRenderer(tabs.get(tab));
    }

    private void setRenderer(Holder<OcculusTab> occulusTab) {
        renderer = ArsMagicaClientApi.occulusTabRendererFactory(occulusTab).create(occulusTab);
    }

    private void nextPage() {
        page++;
        onPageChange();
    }

    private void prevPage() {
        page--;
        onPageChange();
    }

    private void onPageChange() {
        nextButton.active = page < maxPage;
        prevButton.active = page > 0;
        buttons.forEach(button -> button.visible = false);
        for (int i = page * 7; i < (page + 1) * 7 && i < buttons.size(); i++) {
            buttons.get(i).visible = true;
        }
    }

    private void forgetAll() {
        PacketDistributor.sendToServer(new ForgetSkillsPacket());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return super.mouseClicked(mouseX, mouseY, button) || renderer.mouseClicked(mouseX - tabX, mouseY - tabY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY) || renderer.mouseDragged(mouseX - tabX, mouseY - tabY, button, dragX, dragY);
    }
}
