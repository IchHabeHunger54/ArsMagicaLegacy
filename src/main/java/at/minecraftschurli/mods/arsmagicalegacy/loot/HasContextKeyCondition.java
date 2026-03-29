package at.minecraftschurli.mods.arsmagicalegacy.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;

import java.util.List;

public record HasContextKeyCondition(List<ContextKey<?>> params) implements LootItemCondition {
    public static final MapCodec<HasContextKeyCondition> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Identifier.CODEC.<ContextKey<?>>xmap(ContextKey::new, ContextKey::name).listOf().fieldOf("params").forGetter(HasContextKeyCondition::params)
    ).apply(inst, HasContextKeyCondition::new));

    @Override
    public MapCodec<? extends LootItemCondition> codec() {
        return CODEC;
    }

    @Override
    public boolean test(LootContext lootContext) {
        return params.stream().allMatch(lootContext::hasParameter);
    }
}
