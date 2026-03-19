package at.minecraftschurli.arsmagicalegacy.client.gui.spellrecipe;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;

import java.util.List;

class PartsPage extends Page<Holder<SpellPart>> {
    private final Component title;

    public PartsPage(List<SpellPart> spellParts, Component title) {
        super(3, 11, 32, 4, 3, spellParts.stream()
            .map(AMRegistries.SPELL_PARTS::wrapAsHolder)
            .toList());
        this.title = title;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void extractElement(Holder<SpellPart> element, int index, GuiGraphicsExtractor guiGraphics, int x, int y) {
        guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED, SkillAtlasHolder.getSprite(skill(element).value()), x + index % maxPerLine * (size + spacing), y + index / maxPerLine * (size + spacing), size, size);
    }

    @Override
    public List<Component> getElementTooltip(Holder<SpellPart> element) {
        return List.of(Skill.getName(skill(element)));
    }

    @SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
    private static Holder<Skill> skill(Holder<SpellPart> spellPart) {
        return AMRegistries.skills(true).get(spellPart.getKey().identifier()).get();
    }
}
