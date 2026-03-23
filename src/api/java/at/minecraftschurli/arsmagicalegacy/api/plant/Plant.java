package at.minecraftschurli.arsmagicalegacy.api.plant;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleTest;

import java.util.Optional;

/**
 * Represents a plant. Plants are used by certain mod mechanics, such as the Harvest component or Dryads growing certain crops.
 *
 * @param growthType    The {@link GrowthType} to use. This dictates most of the plant's logic.
 * @param seed          The seed {@link ItemStack} to use. This is used e.g. for replanting.
 * @param crop          The crop {@link ItemStack} to use. This is used e.g. for harvest bonuses.
 * @param tool          The tool {@link ItemStack} to use when harvesting.
 * @param allStates     A {@link RuleTest} for all states of the plant.
 */
public record Plant(GrowthType growthType, RuleTest allStates, Optional<ItemStackTemplate> seed, Optional<ItemStackTemplate> crop, Optional<ItemStackTemplate> tool) {
    public static final Codec<Plant> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        GrowthType.CODEC.fieldOf("growth_type").forGetter(Plant::growthType),
        RuleTest.CODEC.fieldOf("all_states").forGetter(Plant::allStates),
        ItemStackTemplate.CODEC.optionalFieldOf("seed").forGetter(Plant::seed),
        ItemStackTemplate.CODEC.optionalFieldOf("crop").forGetter(Plant::crop),
        ItemStackTemplate.CODEC.optionalFieldOf("tool").forGetter(Plant::tool)
    ).apply(inst, Plant::new));

    /**
     * @param player The {@link ServerPlayer} to use.
     * @param level  The {@link ServerLevel} to use.
     * @param pos    The {@link BlockPos} to use.
     * @param state  The {@link BlockState} to use.
     * @param tool   The {@link ItemStack} to use.
     * @return A new {@link GrowthContext}.
     */
    public GrowthContext createContext(ServerPlayer player, ServerLevel level, BlockPos pos, BlockState state, ItemStack tool) {
        return new GrowthContext(this, player, level, pos, state, tool);
    }
}
