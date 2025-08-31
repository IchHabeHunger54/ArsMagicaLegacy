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

class PartsPage extends Page<Holder<SpellPart>> {
    private final Component title;

    public PartsPage(List<SpellPart> spellParts, Component title) {
        super(3, 11, 32, 4, 3, spellParts.stream()
            .map(ArsMagicaApi.spellPartRegistry()::wrapAsHolder)
            .toList());
        this.title = title;
    }

    @Override
    public Component getTitle() {
        return title;
    }

    @Override
    public void renderElement(Holder<SpellPart> element, int index, GuiGraphics guiGraphics, int x, int y) {
        guiGraphics.blit(x + index % maxPerLine * (size + spacing), y + index / maxPerLine * (size + spacing), 0, size, size, SkillAtlasHolder.INSTANCE.get().getSprite(skill(element).value()));
    }

    @Override
    public List<Component> getElementTooltip(Holder<SpellPart> element) {
        return List.of(Skill.getName(skill(element)));
    }

    @SuppressWarnings({"DataFlowIssue", "OptionalGetWithoutIsPresent"})
    private static Holder<Skill> skill(Holder<SpellPart> spellPart) {
        return ClientUtil.registryAccess().registryOrThrow(AMRegistryKeys.SKILL).getHolder(spellPart.getKey().location()).get();
    }
}
