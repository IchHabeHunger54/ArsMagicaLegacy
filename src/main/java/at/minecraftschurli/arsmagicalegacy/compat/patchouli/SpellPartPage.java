package at.minecraftschurli.arsmagicalegacy.compat.patchouli;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.client.atlas.SkillAtlasHolder;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import at.minecraftschurli.arsmagicalegacy.util.AMClientUtil;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import vazkii.patchouli.api.IComponentRenderContext;
import vazkii.patchouli.api.ICustomComponent;
import vazkii.patchouli.api.IVariable;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.UnaryOperator;

@SuppressWarnings({"DataFlowIssue", "unused"})
public class SpellPartPage implements ICustomComponent {
    public static final Identifier ID = ArsMagicaApi.id("spell_part");
    public static final String TEMPLATE = "{\"components\":[{\"type\":\"patchouli:custom\",\"class\":\"at.minecraftschurli.arsmagicalegacy.compat.patchouli.SpellPartPage\",\"part\":\"#part\"}]}";
    private static final Comparator<Holder<Affinity>> COMPARATOR = Comparator.comparing(Holder::getKey);
    private static final int INGREDIENT_COLUMNS = 6;
    private static final int SLOT_SIZE = 18;
    private static final int TEXT_BOTTOM_PADDING = 2;
    private static final int WIDTH = 116;
    private String part;
    private transient int x;
    private transient int y;
    private transient List<SpellIngredient> recipe;
    private transient Map<Holder<Affinity>, Double> affinityShifts;
    private transient List<? extends Holder<Skill>> modifierHolders;

    @Override
    public void build(int x, int y, int page) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(GuiGraphicsExtractor guiGraphics, IComponentRenderContext context, float partialTick, int mouseX, int mouseY) {
        int x = this.x;
        int y = this.y;
        Font font = AMClientUtil.font();
        drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_INGREDIENTS, y);
        y += font.lineHeight + TEXT_BOTTOM_PADDING - SLOT_SIZE;
        if (!recipe.isEmpty()) {
            for (int i = 0; i < recipe.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(recipe.size() - i, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                SpellIngredient ingredient = recipe.get(i);
                drawItemStack(guiGraphics, context, AMUtil.getByTick(ingredient.asItemStacks(), AMClientUtil.player().tickCount / 20).copyWithCount(ingredient.count()), ingredient.tooltip(), x, y, mouseX, mouseY);
            }
            y += TEXT_BOTTOM_PADDING + SLOT_SIZE;
        }
        if (!affinityShifts.isEmpty()) {
            x = (int) (WIDTH - font.getSplitter().stringWidth(String.valueOf(Math.round(affinityShifts.values().stream().min(Double::compareTo).orElse(0.) * 1000) / 1000.))) / 2;
            drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_AFFINITY_BREAKDOWN, y);
            y += font.lineHeight + TEXT_BOTTOM_PADDING;
            for (Holder<Affinity> affinity : affinityShifts.keySet().stream().sorted(COMPARATOR).toList()) {
                drawItemStack(guiGraphics, context, DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinity), List.of(Affinity.getName(affinity)), x - 9, y, mouseX, mouseY);
                guiGraphics.drawString(font, String.valueOf(Math.round(affinityShifts.get(affinity) * 1000) / 1000.), x + 9, y + font.lineHeight / 2, affinity.value().color(), false);
                y += SLOT_SIZE - TEXT_BOTTOM_PADDING;
            }
            y += TEXT_BOTTOM_PADDING;
        }
        List<Skill> modifiers = modifierHolders.stream()
            .filter(e -> !e.value().hidden() || ArsMagicaApi.magicHelper().knows(AMClientUtil.player(), e))
            .map(Holder::value)
            .toList();
        if (!modifiers.isEmpty()) {
            drawCentered(guiGraphics, font, AMTranslations.JEI_SKILL_MODIFIED_BY, y);
            y += font.lineHeight + TEXT_BOTTOM_PADDING - SLOT_SIZE;
            for (int i = 0; i < modifiers.size(); i++) {
                if (i % INGREDIENT_COLUMNS != 0) {
                    x += SLOT_SIZE;
                } else {
                    x = (WIDTH - Math.min(modifiers.size() - i, INGREDIENT_COLUMNS) * SLOT_SIZE) / 2;
                    y += SLOT_SIZE;
                }
                Skill skill = modifiers.get(i);
                guiGraphics.blit(x, y, 0, 16, 16, SkillAtlasHolder.INSTANCE.get().getSprite(skill));
                if (context.isAreaHovered(mouseX, mouseY, x, y, 16, 16)) {
                    context.setHoverTooltipComponents(List.of(Skill.getName(AMRegistries.skills(true).wrapAsHolder(skill))));
                }
            }
        }
    }

    @Override
    public void onVariablesAvailable(UnaryOperator<IVariable> unaryOperator, HolderLookup.Provider registries) {
        HolderLookup.RegistryLookup<SpellPart> spellParts = registries.lookupOrThrow(AMRegistries.Keys.SPELL_PART);
        HolderLookup.RegistryLookup<Skill> skills = registries.lookupOrThrow(AMRegistries.Keys.SKILL);
        SpellPart spellPart = spellParts.getOrThrow(ResourceKey.create(AMRegistries.Keys.SPELL_PART, Identifier.parse(unaryOperator.apply(IVariable.wrap(part, registries)).asString()))).value();
        SpellPartData data = spellPart.getData();
        recipe = data.recipe();
        affinityShifts = data.affinityShifts();
        modifierHolders = ArsMagicaApi.spellHelper()
            .getModifiers(spellPart)
            .stream()
            .map(e -> skills.getOrThrow(ResourceKey.create(AMRegistries.Keys.SKILL, AMRegistries.SPELL_PARTS.getKey(e))))
            .toList();
    }

    private static void drawItemStack(GuiGraphicsExtractor guiGraphics, IComponentRenderContext context, ItemStack stack, List<Component> tooltip, int x, int y, int mouseX, int mouseY) {
        AMClientUtil.renderItem(guiGraphics, stack, x, y);
        if (context.isAreaHovered(mouseX, mouseY, x, y, 16, 16)) {
            context.setHoverTooltipComponents(tooltip);
        }
    }

    private static void drawCentered(GuiGraphicsExtractor graphics, Font font, Component component, int y) {
        graphics.drawString(font, component, (int) ((WIDTH - font.getSplitter().stringWidth(component.getString())) / 2), y, 0x404040, false);
    }
}
