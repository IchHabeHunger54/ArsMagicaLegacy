package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WizardsAutumn extends SpellComponent {
    public WizardsAutumn() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (hitResult == null || hitResult.getType() == HitResult.Type.MISS) return spell;
        BlockPos origin = BlockPos.containing(hitResult.getLocation());
        int range = (int) ArsMagicaApi.spellHelper().getModifiedStat(2, AMSpells.RANGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
        for (int i = -range; i <= range; i++) {
            for (int j = -range; j <= range; j++) {
                for (int k = -range; k <= range; k++) {
                    BlockPos pos = origin.offset(i, j, k);
                    BlockState state = level.getBlockState(pos);
                    if (state.is(AMTags.Blocks.WIZARDS_AUTUMN_LEAVES)) {
                        level.destroyBlock(pos, true, caster);
                    }
                }
            }
        }
        return spell;
    }
}
