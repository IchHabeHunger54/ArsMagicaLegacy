package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Disarm extends SpellComponent.CastEntity {
    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return SpellComponentCastResult.pass(spell);
        if (entity instanceof EnderMan enderMan) {
            BlockState state = enderMan.getCarriedBlock();
            if (state != null) {
                addItemEntity(level, entity, new ItemStack(state.getBlock()));
                enderMan.setCarriedBlock(null);
            }
            if (caster != null) {
                enderMan.setTarget(caster);
            }
            return SpellComponentCastResult.success(spell);
        } else if (!entity.getMainHandItem().isEmpty()) {
            addItemEntity(level, entity, entity.getMainHandItem().copy());
            entity.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
            return SpellComponentCastResult.success(spell);
        } else if (!entity.getOffhandItem().isEmpty()) {
            addItemEntity(level, entity, entity.getOffhandItem().copy());
            entity.setItemInHand(InteractionHand.OFF_HAND, ItemStack.EMPTY);
            return SpellComponentCastResult.success(spell);
        }
        return SpellComponentCastResult.pass(spell);
    }

    private static void addItemEntity(Level level, Entity entity, ItemStack stack) {
        ItemEntity item = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), stack);
        item.setDefaultPickUpDelay();
        level.addFreshEntity(item);
    }
}
