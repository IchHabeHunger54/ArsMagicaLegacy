package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.advancement.AffinityChangeTrigger;
import at.minecraftschurli.arsmagicalegacy.advancement.LevelChangeTrigger;
import at.minecraftschurli.arsmagicalegacy.advancement.SkillChangeTrigger;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.DataComponentMatchers;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentExactPredicate;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.advancements.AdvancementProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public final class AMAdvancementProvider extends AdvancementProvider {
    public AMAdvancementProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, List.of(new AdvancementSubProvider()));
    }

    private static class AdvancementSubProvider implements net.minecraft.data.advancements.AdvancementSubProvider {
        @SuppressWarnings({"DataFlowIssue", "unchecked", "unused"})
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver) {
            HolderLookup.RegistryLookup<Affinity> affinities = registries.lookupOrThrow(AMRegistries.Keys.AFFINITY);
            ItemStack book = ArsMagicaApi.book();
            DataComponentType<?> bookComponent = registries.lookupOrThrow(Registries.DATA_COMPONENT_TYPE).getOrThrow(ResourceKey.create(Registries.DATA_COMPONENT_TYPE, Identifier.fromNamespaceAndPath("patchouli", "book"))).value();
            Criterion<InventoryChangeTrigger.TriggerInstance> bookCriterion = InventoryChangeTrigger.TriggerInstance.hasItems(ItemPredicate.Builder.item().of(registries.lookupOrThrow(Registries.ITEM), book.getItem()).withComponents(DataComponentMatchers.Builder.components().exact(DataComponentExactPredicate.builder().<Object>expect((DataComponentType<? super Object>) bookComponent, book.get(bookComponent)).build()).build()));

            AdvancementHolder bookRoot = Advancement.Builder.advancement()
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.id("book/root").toString());
            registries.lookupOrThrow(AMRegistries.Keys.SKILL).listElements().forEach(skill -> Advancement.Builder.advancement()
                .parent(bookRoot)
                .addCriterion("knows", SkillChangeTrigger.create(List.of(skill)))
                .save(saver, ArsMagicaApi.id("book/" + skill.getKey().identifier().getPath()).toString()));

            AdvancementHolder root = Advancement.Builder.advancement()
                .display(ItemStackTemplate.fromNonEmptyStack(book), title("root"), description("root"), ArsMagicaApi.id("textures/gui/advancements/background.png"), AdvancementType.TASK, false, false, true)
                .addCriterion("arcane_compendium", bookCriterion)
                .save(saver, ArsMagicaApi.id("root").toString());
            AdvancementHolder skill = advancement(saver, "skill", root, AMItems.OCCULUS.toStack(), AdvancementType.TASK, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_NON_HIDDEN)));
            AdvancementHolder allSkills = advancement(saver, "all_skills", skill, AMItems.OCCULUS.toStack(), AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_NON_HIDDEN)));
            AdvancementHolder hiddenSkill = advancement(saver, "hidden_skill", skill, AMItems.OCCULUS.toStack(), AdvancementType.TASK, true,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ANY_HIDDEN)));
            AdvancementHolder allHiddenSkills = advancement(saver, "all_hidden_skills", hiddenSkill, AMItems.OCCULUS.toStack(), AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("knows", SkillChangeTrigger.create(SkillChangeTrigger.Requirements.ALL_HIDDEN)));
            AdvancementHolder spell = advancement(saver, "spell", skill, AMItems.SPELL.toStack(), AdvancementType.TASK, false,
                builder -> builder.addCriterion("spell", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.SPELL)));
            AdvancementHolder affinityOnePercent = advancement(saver, "affinity_one_percent", spell, DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.WATER)), AdvancementType.TASK, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(0.01)));
            AdvancementHolder affinityFiftyPercent = advancement(saver, "affinity_fifty_percent", affinityOnePercent, DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.LIFE)), AdvancementType.TASK, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(0.5)));
            AdvancementHolder affinityFull = advancement(saver, "affinity_full", affinityFiftyPercent, DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(AMMagic.ENDER)), AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(1)));
            AdvancementHolder affinityTome = advancement(saver, "affinity_tome", affinityFull, DataComponentNamedItem.set(AMItems.AFFINITY_TOME.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(Affinity.NONE)), AdvancementType.TASK, true,
                builder -> builder.addCriterion("affinity", AffinityChangeTrigger.create(1, 0)));
            AdvancementHolder level10 = advancement(saver, "level_10", spell, AMItems.MOONSTONE.toStack(), AdvancementType.TASK, false,
                builder -> builder.addCriterion("level", LevelChangeTrigger.create(10)));
            AdvancementHolder level100 = advancement(saver, "level_100", level10, AMItems.SUNSTONE.toStack(), AdvancementType.CHALLENGE, false,
                builder -> builder.addCriterion("level", LevelChangeTrigger.create(100)));
        }

        private Component title(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title");
        }

        private Component description(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description");
        }

        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, ItemStack icon, AdvancementType type, boolean hidden, Consumer<Advancement.Builder> consumer) {
            Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(ItemStackTemplate.fromNonEmptyStack(icon), title(name), description(name), null, type, true, true, hidden);
            consumer.accept(builder);
            return builder.save(saver, ArsMagicaApi.id(name).toString());
        }
    }
}
