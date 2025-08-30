package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.List;

class PartsPage extends Page {
    private static final int X_OFFSET = 3;
    private static final int Y_OFFSET = 11;
    private static final int SIZE = 32;
    private static final int SPACING = 4;
    private static final int MAX_PER_LINE = 3;
    private final List<Holder<SpellPart>> spellParts;
    private final Component title;

    public PartsPage(List<SpellPart> spellParts, Component title) {
        this.spellParts = spellParts.stream().map(ArsMagicaApi.spellPartRegistry()::wrapAsHolder).toList();
        this.title = title;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void render(GuiGraphics graphics, int x, int y) {
        for (int i = 0; i < spellParts.size(); i++) {
            graphics.blit(x + X_OFFSET + i % MAX_PER_LINE * (SIZE + SPACING), y + Y_OFFSET + i / MAX_PER_LINE * (SIZE + SPACING), 0, SIZE, SIZE, SkillAtlasHolder.INSTANCE.get().getSprite(skill(spellParts.get(i)).value()));
        }
    }

    @Override
    public List<Component> getTooltip(int mouseX, int mouseY) {
        int i = getTooltipIndex(mouseX - X_OFFSET, mouseY - Y_OFFSET, SIZE, SPACING, MAX_PER_LINE, spellParts.size());
        return i == -1 ? List.of() : List.of(Skill.getName(skill(spellParts.get(i))));
    }

    @SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
    private static Holder<Skill> skill(Holder<SpellPart> spellPart) {
        return ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getHolder(spellPart.getKey().location()).get();
    }
}
