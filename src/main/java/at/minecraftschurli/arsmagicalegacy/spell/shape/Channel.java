package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.PrimarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Channel extends PrimarySpellShape {
    @Override
    public SpellCastResult cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster) {
        return caster != null
            ? ArsMagicaApi.spellHelper().castSecondaryOrGrammar(spell, level, caster, caster, new EntityHitResult(caster))
            : new SpellCastResult(spell).setMessage(AMTranslations.SPELL_CAST_NO_CASTER);
    }

    @Override
    public boolean isContinuous() {
        return true;
    }
}
