package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.client.screen.AbstractSpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.GlobalVec3;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class RecallCustomizationScreen extends AbstractSpellPartCustomizationScreen<GlobalVec3> {
    private static final int WIDTH = 200;
    private static final int HEIGHT = 80;
    private static final int MESSAGE_TIME = 100;
    private static final int MESSAGE_ALPHA_START = 10;
    private final TextureAtlasSprite sprite;
    private int leftPos;
    private int topPos;
    private Button clearButton;
    private GlobalVec3 oldValue;
    private Component message;
    private long messageTime;

    public RecallCustomizationScreen(Function<DataComponentType<GlobalVec3>, @Nullable GlobalVec3> valueGetter, BiConsumer<DataComponentType<GlobalVec3>, @Nullable GlobalVec3> valueSetter) {
        super(AMTranslations.SPELL_CUSTOMIZATION_RECALL, AMDataComponents.SPELL_RECALL_POSITION.get(), valueGetter, valueSetter);
        sprite = SkillAtlasHolder.getSprite(AMSpells.RECALL.getId());
    }

    @Override
    protected void init() {
        leftPos = (width - WIDTH) / 2;
        topPos = (height - HEIGHT) / 2;
        addRenderableWidget(Button.builder(AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET, _ -> {
            LocalPlayer player = AMClientUtil.player();
            value = new GlobalVec3(player.level().dimension(), player.position());
            clearButton.active = true;
            message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_SET_SUCCESS;
            messageTime = MESSAGE_TIME;
        }).bounds(leftPos, topPos + 36, 98, 20).build());
        clearButton = addRenderableWidget(Button.builder(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR, button -> {
            if (oldValue == null) {
                oldValue = value;
                value = null;
                button.setMessage(AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE);
                message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR_SUCCESS;
            } else {
                value = oldValue;
                oldValue = null;
                button.setMessage(AMTranslations.SPELL_CUSTOMIZATION_RECALL_CLEAR);
                message = AMTranslations.SPELL_CUSTOMIZATION_RECALL_RESTORE_SUCCESS;
            }
            messageTime = MESSAGE_TIME;
        }).bounds(leftPos + 102, topPos + 36, 98, 20).build());
        clearButton.active = value != null;
        addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, _ -> onClose()).bounds(leftPos, topPos + 60, 200, 20).build());
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        super.extractRenderState(graphics, mouseX, mouseY, a);
        graphics.blitSprite(RenderPipelines.GUI, sprite, leftPos + 84, topPos, 32, 32, 0xff7f7f7f);
        if (messageTime > 0 && message != null) {
            int alpha = messageTime > MESSAGE_ALPHA_START ? 255 : (int) Mth.lerp((messageTime - a) / MESSAGE_ALPHA_START, 0, 255);
            graphics.centeredText(font, message, leftPos + 100, topPos + 11, alpha << 24 | 0xffffff);
        }
    }

    @Override
    public void tick() {
        if (messageTime >= 0) {
            messageTime--;
        }
    }
}
