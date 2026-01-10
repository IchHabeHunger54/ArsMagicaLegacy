package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.advancement.SkillChangeTrigger;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class AMAdvancementProvider extends AdvancementProvider {
    public AMAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, ExistingFileHelper existingFileHelper) {
        super(output, registries, existingFileHelper, List.of(new AdvancementSubProvider()));
    }

    private static class AdvancementSubProvider implements AdvancementGenerator {
        @SuppressWarnings({"DataFlowIssue", "unchecked"})
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            ItemStack book = ArsMagicaApi.book();
            DataComponentType<?> bookComponent = BuiltInRegistries.DATA_COMPONENT_TYPE.get(ResourceLocation.fromNamespaceAndPath("patchouli", "book"));
            Criterion<InventoryChangeTrigger.TriggerInstance> bookCriterion = InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(book.getItem()).hasComponents(DataComponentPredicate.builder().<Object>expect((DataComponentType<? super Object>) bookComponent, book.get(bookComponent)).build()));

            AdvancementHolder bookRoot = Advancement.Builder.advancement()
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.modLoc("book/root").toString());
            registries.lookupOrThrow(AMRegistries.SKILL).listElements().forEach(skill -> Advancement.Builder.advancement()
                .parent(bookRoot)
                .addCriterion("knows", SkillChangeTrigger.create(List.of(skill)))
                .save(saver, ArsMagicaApi.modLoc("book/" + skill.getKey().location().getPath()).toString()));

            AdvancementHolder root = Advancement.Builder.advancement()
                .display(book, title("root"), description("root"), ArsMagicaApi.modLoc("textures/gui/advancements/background.png"), AdvancementType.TASK, false, false, true)
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.modLoc("root").toString());
            AdvancementHolder skill = advancement(saver, "skill", root, AMItems.OCCULUS, AdvancementType.TASK, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_NON_HIDDEN)));
            AdvancementHolder allSkills = advancement(saver, "all_skills", skill, AMItems.OCCULUS, AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_NON_HIDDEN)));
            AdvancementHolder hiddenSkill = advancement(saver, "hidden_skill", skill, AMItems.OCCULUS, AdvancementType.TASK, true,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_HIDDEN)));
            AdvancementHolder allHiddenSkills = advancement(saver, "all_hidden_skills", hiddenSkill, AMItems.OCCULUS, AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_HIDDEN)));
            AdvancementHolder spell = advancement(saver, "spell", skill, AMItems.SPELL, AdvancementType.TASK, false,
                builder -> builder.addCriterion("spell", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.SPELL)));
        }

        private Component title(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title");
        }

        private Component description(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description");
        }

        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, ItemLike icon, AdvancementType type, boolean hidden, Consumer<Advancement.Builder> consumer) {
            Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, type, true, true, hidden);
            consumer.accept(builder);
            return builder.save(saver, ArsMagicaApi.modLoc(name).toString());
        }
    }
}
