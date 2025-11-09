package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.loot.AddConditionsModifier;
import at.minecraftschurli.arsmagicalegacy.loot.HasLootContextParamCondition;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public interface AMLoot {
    DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Registries.LOOT_CONDITION_TYPE, ArsMagicaApi.MOD_ID);
    DeferredHolder<LootItemConditionType, LootItemConditionType> HAS_LOOT_CONTEXT_PARAM = LOOT_CONDITIONS.register("has_loot_context_param", () -> new LootItemConditionType(HasLootContextParamCondition.CODEC));

    DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ArsMagicaApi.MOD_ID);
    DeferredHolder<MapCodec<? extends IGlobalLootModifier>, MapCodec<AddConditionsModifier>> ADD_CONDITIONS = GLOBAL_LOOT_MODIFIERS.register("add_conditions", () -> AddConditionsModifier.CODEC);
}
