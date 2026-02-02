package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Grow extends SpellComponent.CastBlock {
    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult) {
        if (!(level instanceof ServerLevel serverLevel)) return spell;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = serverLevel.getBlockState(pos);
        if (!(state.getBlock() instanceof BonemealableBlock) && hitResult.getDirection() == Direction.UP) {
            pos = pos.above();
            state = serverLevel.getBlockState(pos);
        }
        if (state.getBlock() instanceof BonemealableBlock block && block.isValidBonemealTarget(serverLevel, pos, state) && block.isBonemealSuccess(serverLevel, serverLevel.getRandom(), pos, state)) {
            block.performBonemeal(serverLevel, serverLevel.getRandom(), pos, state);
        }
        return spell;
    }
}
