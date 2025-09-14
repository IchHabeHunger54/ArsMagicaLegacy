package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SingleRecipeInput;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.Optional;

public class Forge extends SpellComponent.CastBoth {
    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult) {
        Level level = caster.level();
        if (level.isClientSide()) return spell;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.isAir()) return spell;
        Optional<RecipeHolder<SmeltingRecipe>> recipe = level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, new SingleRecipeInput(new ItemStack(state.getBlock())), level);
        if (recipe.isEmpty()) return spell;
        ItemStack stack = recipe.get().value().getResultItem(level.registryAccess());
        level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        if (stack.getItem() instanceof BlockItem blockItem) {
            Direction direction = hitResult.getDirection();
            Vec3i normal = direction.getNormal();
            blockItem.place(new BlockPlaceContext(level, caster instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, new BlockHitResult(hitResult.getLocation().add(normal.getX(), normal.getY(), normal.getZ()), direction, pos.offset(normal), hitResult.isInside())));
        } else {
            ItemEntity item = new ItemEntity(level, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, stack);
            item.setDefaultPickUpDelay();
            level.addFreshEntity(item);
        }
        return spell;
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!AMServerConfig.FORGE_SMELTS_VILLAGERS.get() || !(hitResult.getEntity() instanceof Villager villager)) return spell;
        Level level = villager.level();
        if (!level.isClientSide()) {
            ItemEntity item = new ItemEntity(level, villager.getX(), villager.getY(), villager.getZ(), new ItemStack(Items.EMERALD));
            item.setDefaultPickUpDelay();
            level.addFreshEntity(item);
        }
        villager.hurt(caster instanceof Player player ? level.damageSources().playerAttack(player) : level.damageSources().mobAttack(caster), 5000);
        return spell;
    }
}
