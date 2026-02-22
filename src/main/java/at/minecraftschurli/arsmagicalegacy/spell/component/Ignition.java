package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Ignition extends SpellComponent.CastBoth {
    public Ignition() {
        super(AMSpells.DURATION_STAT);
    }

    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult) {
        ItemStack stack = new ItemStack(Items.FLINT_AND_STEEL);
        stack.useOn(new UseOnContext(level, caster instanceof Player player ? player : null, InteractionHand.MAIN_HAND, stack, hitResult));
        return spell;
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        if (target instanceof Creeper creeper && !creeper.isIgnited()) {
            creeper.ignite();
        } else if (!target.isOnFire() && !target.isInWaterRainOrBubble()) {
            target.setRemainingFireTicks((int) ArsMagicaApi.spellHelper().getModifiedStat(60, AMSpells.DURATION_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        }
        return spell;
    }
}
