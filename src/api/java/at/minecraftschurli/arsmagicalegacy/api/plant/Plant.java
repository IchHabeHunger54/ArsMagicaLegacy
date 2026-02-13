package at.minecraftschurli.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.List;

/**
 * Represents a plant. Plants are used by certain mod mechanics, such as the Harvest component or Dryads growing certain crops.
 *
 * @param conditions    A list of {@link ICondition} to check before fully loading the plant.
 * @param growthType    The {@link GrowthType} to use. This dictates most of the plant's logic.
 * @param seed          The seed {@link ItemStack} to use. This is used e.g. for replanting.
 * @param crop          The crop {@link ItemStack} to use. This is used e.g. for harvest bonuses.
 * @param allStates     A {@link RuleTest} for all states of the plant.
 */
public record Plant(List<ICondition> conditions, GrowthType growthType, ItemStack seed, ItemStack crop, RuleTest allStates) {
    public static final Codec<Plant> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        ICondition.LIST_CODEC.optionalFieldOf("conditions", List.of()).forGetter(Plant::conditions),
        GrowthType.CODEC.fieldOf("growth_type").forGetter(Plant::growthType),
        ItemStack.OPTIONAL_CODEC.optionalFieldOf("seed", ItemStack.EMPTY).forGetter(Plant::seed),
        ItemStack.OPTIONAL_CODEC.optionalFieldOf("crop", ItemStack.EMPTY).forGetter(Plant::crop),
        RuleTest.CODEC.fieldOf("all_states").forGetter(Plant::allStates)
    ).apply(inst, Plant::new));

    /**
     * @param player The {@link ServerPlayer} to use.
     * @param level  The {@link ServerLevel} to use.
     * @param pos    The {@link BlockPos} to use.
     * @param state  The {@link BlockState} to use.
     * @return A new {@link GrowthContext}.
     */
    public GrowthContext createContext(ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state) {
        return new GrowthContext(this, player, level, pos, state);
    }
}
