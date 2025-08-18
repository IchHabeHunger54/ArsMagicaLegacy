package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import com.mojang.datafixers.util.Pair;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementType;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
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
        @Override
        public void generate(HolderLookup.Provider registries, Consumer<AdvancementHolder> saver, ExistingFileHelper existingFileHelper) {
            AdvancementHolder root = Advancement.Builder.advancement() //TODO use arcane compendium instead
                .display(AMItems.VINTEUM_DUST, title("root"), description("root"), ArsMagicaApi.modLoc("textures/gui/advancements/background.png"), AdvancementType.TASK, false, false, true)
                .addCriterion("vinteum_dust", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.VINTEUM_DUST))
                .requirements(AdvancementRequirements.allOf(List.of("vinteum_dust")))
                .save(saver, ArsMagicaApi.modLoc("root").toString());
            advancement(saver, "spell", root, AMItems.SPELL, Pair.of("spell", InventoryChangeTrigger.TriggerInstance.hasItems(AMItems.SPELL)));
        }

        private Component title(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".title");
        }

        private Component description(String name) {
            return Component.translatable("advancements." + ArsMagicaApi.MOD_ID + "." + name + ".description");
        }

        @SafeVarargs
        private AdvancementHolder advancement(Consumer<AdvancementHolder> saver, String name, AdvancementHolder parent, ItemLike icon, Pair<String, Criterion<?>>... criteria) {
            Advancement.Builder builder = Advancement.Builder.advancement()
                .parent(parent)
                .display(icon, title(name), description(name), null, AdvancementType.TASK, true, true, false);
            Arrays.stream(criteria).forEach(criterion -> builder.addCriterion(criterion.getFirst(), criterion.getSecond()));
            builder.requirements(AdvancementRequirements.allOf(Arrays.stream(criteria).map(Pair::getFirst).toList()));
            return builder.save(saver, ArsMagicaApi.modLoc(name).toString());
        }
    }
}
