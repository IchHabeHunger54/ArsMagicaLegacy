package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Plow extends SpellComponent.CastBlock {
    @Override
    public SpellComponentCastResult castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult) {
        ItemStack stack = new ItemStack(Items.WOODEN_HOE);
        stack.useOn(new UseOnContext(level, caster instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, hitResult));
        return SpellComponentCastResult.success(spell);
    }
}
