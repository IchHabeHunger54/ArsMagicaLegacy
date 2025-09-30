package at.minecraftschurli.arsmagicalegacy.loot;

import at.minecraftschurli.arsmagicalegacy.init.AMLoot;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.List;

public record HasLootContextParamCondition(List<LootContextParam<?>> params) implements LootItemCondition {
    public static final MapCodec<HasLootContextParamCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        ResourceLocation.CODEC.<LootContextParam<?>>xmap(LootContextParam::new, LootContextParam::getName).listOf().fieldOf("params").forGetter(HasLootContextParamCondition::params)
    ).apply(inst, HasLootContextParamCondition::new));

    @Override
    public LootItemConditionType getType() {
        return AMLoot.HAS_LOOT_CONTEXT_PARAM.get();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return params.stream().allMatch(lootContext::hasParam);
    }
}
