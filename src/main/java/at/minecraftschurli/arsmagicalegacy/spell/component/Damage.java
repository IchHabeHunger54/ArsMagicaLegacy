package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.spell.SpellDamage;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

import java.util.List;
import java.util.function.Function;

public class Damage extends SpellComponent.CastEntity {
    private final Function<LivingEntity, ResourceKey<DamageType>> damageType;

    public Damage(Function<LivingEntity, ResourceKey<DamageType>> damageType) {
        super(AMSpells.DAMAGE_STAT, AMSpells.HEALING_STAT);
        this.damageType = damageType;
    }

    public Damage(ResourceKey<DamageType> damageType) {
        this($ -> damageType);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        double damage = AMServerConfig.DAMAGE_DAMAGE.get();
        if (damage < 0 && target instanceof LivingEntity living) {
            living.heal((float) ArsMagicaApi.spellHelper().getModifiedStat(-damage, AMSpells.HEALING_STAT, modifiers, spell, caster, directEntity, hitResult));
        } else if (damage >= 0 || !(target instanceof ServerPlayer player) || player.serverLevel().getServer().isPvpAllowed()) {
            float finalDamage = (float) ArsMagicaApi.spellHelper().getModifiedStat(damage, AMSpells.DAMAGE_STAT, modifiers, spell, caster, directEntity, hitResult);
            spell = spell.updateDataComponents(map -> map.updateGrammar(grammar -> grammar.set(AMDataComponents.SPELL_DAMAGE.get(), grammar.getOrDefault(AMDataComponents.SPELL_DAMAGE.get(), SpellDamage.EMPTY).setDamage(target, damageType.apply(caster), finalDamage))));
        }
        return spell;
    }
}
