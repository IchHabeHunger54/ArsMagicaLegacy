package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMRecipes;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.recipe.spelltransformation.SpellTransformationInput;
import at.minecraftschurli.arsmagicalegacy.recipe.spelltransformation.SpellTransformationRecipe;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.BlockHitResult;

import java.util.List;
import java.util.Optional;

public class Drought extends SpellComponent.CastBlock {
    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult) {
        Level level = directEntity.level();
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        BlockPos normalPos = pos.offset(hitResult.getDirection().getNormal());
        if (state.hasProperty(BlockStateProperties.WATERLOGGED) && state.getValue(BlockStateProperties.WATERLOGGED)) {
            level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.WATERLOGGED, false));
            return spell;
        }
        Optional<RecipeHolder<SpellTransformationRecipe>> optional = level.getRecipeManager().getRecipeFor(AMRecipes.SPELL_TRANSFORMATION_TYPE.get(), new SpellTransformationInput(state, AMSpells.DROUGHT), level);
        if (optional.isPresent()) {
            level.setBlockAndUpdate(pos, optional.get().value().result());
        } else if (level.getBlockState(normalPos).is(Blocks.WATER)) {
            level.setBlockAndUpdate(normalPos, Blocks.AIR.defaultBlockState());
        }
        return spell;
    }
}
