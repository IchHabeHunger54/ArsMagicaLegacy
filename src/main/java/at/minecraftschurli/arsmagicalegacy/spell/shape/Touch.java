package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Touch extends PrimarySpellShape {
    public Touch() {
        super(AMSpells.TARGET_NON_SOLID_STAT);
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster) {
        Vec3 eyePos = caster.getEyePosition();
        boolean targetNonSolid = ArsMagicaApi.spellHelper().getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, spell, level, caster, caster, null) > 0;
        ClipContext.Block blockContext = targetNonSolid ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER;
        ClipContext.Fluid fluidContext = targetNonSolid ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE;
        HitResult result = AMUtil.getHitResult(eyePos, eyePos.add(getLookAngle(caster, Attributes.ENTITY_INTERACTION_RANGE)), caster, blockContext, fluidContext);
        if (result.getType() == HitResult.Type.ENTITY) {
            ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, level, caster, caster, result);
        } else {
            result = AMUtil.getHitResult(eyePos, eyePos.add(getLookAngle(caster, Attributes.BLOCK_INTERACTION_RANGE)), caster, blockContext, fluidContext);
            if (result.getType() == HitResult.Type.BLOCK) {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, level, caster, caster, result);
            }
        }
        return spell;
    }

    private Vec3 getLookAngle(LivingEntity caster, Holder<Attribute> attribute) {
        return caster.getLookAngle().normalize().scale(caster.getAttributeValue(attribute));
    }
}
