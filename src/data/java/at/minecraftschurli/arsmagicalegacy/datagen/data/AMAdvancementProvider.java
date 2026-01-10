package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.advancement.SkillChangeTrigger;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.init.AMCriterionTriggers;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.common.data.AdvancementProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

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
                .addCriterion("knows", AMCriterionTriggers.SKILL_CHANGE.get().createCriterion(new SkillChangeTrigger.TriggerInstance(List.of(skill))))
                .save(saver, ArsMagicaApi.modLoc("book/" + skill.getKey().location().getPath()).toString()));
            AdvancementHolder root = Advancement.Builder.advancement()
                .display(book, title("root"), description("root"), ArsMagicaApi.modLoc("textures/gui/advancements/background.png"), AdvancementType.TASK, false, false, true)
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.modLoc("root").toString());
            advancement(saver, "spell", root, AMItems.SPELL, false, Pair.of("spell", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.SPELL)));
        }

        private Component title(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title");
        }

        private Component description(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description");
        }

        @SuppressWarnings({"SameParameterValue", "UnusedReturnValue"})
        @SafeVarargs
        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, ItemLike icon, boolean hidden, Pair<String, Criterion<?>>... criteria) {
            Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, AdvancementType.TASK, true, true, hidden);
            Arrays.stream(criteria).forEach(criterion -> builder.addCriterion(criterion.getFirst(), criterion.getSecond()));
            builder.requirements(AdvancementRequirements.allOf(Arrays.stream(criteria).map(Pair::getFirst).toList()));
            return builder.save(saver, ArsMagicaApi.modLoc(name).toString());
        }
    }
}
