package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;

public class ManaBlast extends SpellComponent.CastEntity {
    public ManaBlast() {
        super(AMSpells.DAMAGE_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        if (!(hitResult.getEntity() instanceof LivingEntity entity)) return spell;
        ManaHelper helper = ArsMagicaApi.manaHelper();
        double mana = helper.getMana(caster);
        entity.hurt(entity.damageSources().indirectMagic(caster, directEntity), (float) ArsMagicaApi.spellHelper().getModifiedStat(mana * AMServerConfig.MANA_BLAST_FACTOR.get(), AMSpells.DAMAGE_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        helper.decreaseMana(caster, mana);
        return spell;
    }
}
