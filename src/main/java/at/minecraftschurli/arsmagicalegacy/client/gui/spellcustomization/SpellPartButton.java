package at.minecraftschurli.arsmagicalegacy.client.gui.spellcustomization;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.client.SpellPartCustomizationScreen;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class SpellPartButton<T> extends Button {
    public static final int SIZE = 16;
    private final Holder<SpellPart> spellPart;
    private final TextureAtlasSprite sprite;
    private Function<DataComponentType<T>, T> valueGetter;
    private BiConsumer<DataComponentType<T>, T> valueSetter;

    private SpellPartButton(int x, int y, Holder<SpellPart> spellPart, TextureAtlasSprite sprite) {
        super(x, y, SIZE, SIZE, Component.empty(), $ -> {}, DEFAULT_NARRATION);
        this.spellPart = spellPart;
        this.sprite = sprite;
    }

    @SuppressWarnings("DataFlowIssue")
    public static <T> SpellPartButton<T> create(int x, int y, Holder<SpellPart> spellPart, SpellCustomizationScreen screen, int index) {
        Holder<Skill> skill = AMUtil.skill(spellPart);
        SpellPartButton<T> button = new SpellPartButton<>(x, y, spellPart, SkillAtlasHolder.INSTANCE.get().getSprite(skill.value()));
        if (spellPart.value().getDataComponentType() != null) {
            button.valueGetter = type -> (index == -1 ? screen.getSpell().dataComponents().grammar() : screen.getSpell().dataComponents().shapeGroups().get(index)).get(type);
            button.valueSetter = (type, value) -> screen.setSpell(screen.getSpell().updateDataComponents(components -> components.update(index, map -> {
                if (value == null) {
                    map.remove(type);
                } else {
                    map.set(type, value);
                }
            })));
        }
        button.setTooltip(Tooltip.create(Skill.getName(skill)));
        button.active = ArsMagicaClientApi.spellPartCustomizationScreen(spellPart) != null;
        return button;
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blit(getX(), getY(), 8, SIZE, SIZE, sprite);
        if (active) return;
        guiGraphics.setColor(0.5f, 0.5f, 0.5f, 1);
        RenderSystem.enableBlend();
        guiGraphics.blit(getX(), getY(), 16, SIZE, SIZE, sprite);
        RenderSystem.disableBlend();
        guiGraphics.setColor(1, 1, 1, 1);
    }

    @SuppressWarnings({"unchecked", "DataFlowIssue"})
    @Override
    public void onPress() {
        if (!active) return;
        SpellPartCustomizationScreen.Factory<T, ?> factory = (SpellPartCustomizationScreen.Factory<T, ?>) ArsMagicaClientApi.spellPartCustomizationScreen(spellPart);
        if (valueGetter != null && valueSetter != null) {
            AMClientUtil.mc().pushGuiLayer(factory.create(valueGetter, valueSetter));
        }
    }
}
