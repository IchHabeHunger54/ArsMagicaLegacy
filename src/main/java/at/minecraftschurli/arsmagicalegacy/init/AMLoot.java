package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.loot.AddConditionsModifier;
import at.minecraftschurli.arsmagicalegacy.loot.HasLootContextParamCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;

public interface AMLoot {
    DeferredHolder<LootItemConditionType, LootItemConditionType> HAS_LOOT_CONTEXT_PARAM = AMRegistries.LOOT_CONDITIONS.register("has_loot_context_param", () -> new LootItemConditionType(HasLootContextParamCondition.CODEC));

    DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddConditionsModifier>> ADD_CONDITIONS = AMRegistries.GLOBAL_LOOT_MODIFIERS.register("add_conditions", () -> AddConditionsModifier.CODEC);

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
