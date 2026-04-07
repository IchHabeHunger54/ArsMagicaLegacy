package at.minecraftschurli.mods.arsmagicalegacy.spell.shape;

import at.minecraftschurli.mods.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.mods.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class Touch extends PrimarySpellShape {
    public Touch() {
        super(AMSpells.TARGET_NON_SOLID_STAT);
    }

    @Override
    public SpellCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        LivingEntity caster = context.caster();
        if (caster == null) return new SpellCastResult(context).setMessage(AMTranslations.SPELL_FAIL_NO_CASTER);
        Vec3 eyePos = caster.getEyePosition();
        boolean targetNonSolid = ArsMagicaApi.spellHelper().getModifiedStat(0, AMSpells.TARGET_NON_SOLID_STAT, modifiers, context) > 0;
        ClipContext.Block blockContext = targetNonSolid ? ClipContext.Block.OUTLINE : ClipContext.Block.COLLIDER;
        ClipContext.Fluid fluidContext = targetNonSolid ? ClipContext.Fluid.ANY : ClipContext.Fluid.NONE;
        HitResult result = getHitResult(eyePos, caster, Attributes.ENTITY_INTERACTION_RANGE, blockContext, fluidContext);
        if (result.getType() == HitResult.Type.ENTITY) {
            ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, result));
        } else {
            result = getHitResult(eyePos, caster, Attributes.BLOCK_INTERACTION_RANGE, blockContext, fluidContext);
            if (result.getType() == HitResult.Type.BLOCK) {
                ArsMagicaApi.spellHelper().castSecondaryOrGrammar(context.setDirectEntityAndHitResult(caster, result));
            }
        }
        return new SpellCastResult(context).setSuccess();
    }

    private HitResult getHitResult(Vec3 eyePos, LivingEntity caster, Holder<Attribute> attribute, ClipContext.Block blockContext, ClipContext.Fluid fluidContext) {
        return AMUtil.getHitResult(eyePos, eyePos.add(caster.getLookAngle().normalize().scale(caster.getAttributeValue(attribute))), caster, blockContext, fluidContext);
    }
}
