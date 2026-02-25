package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMEnchantments;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.spell.SpellDamage;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Damage extends SpellComponent.CastEntity {
    private final Function<@Nullable LivingEntity, ResourceKey<DamageType>> damageType;

    public Damage(Function<@Nullable LivingEntity, ResourceKey<DamageType>> damageType) {
        super(AMSpells.DAMAGE_STAT, AMSpells.DISMEMBERING_STAT, AMSpells.FORTUNE_STAT, AMSpells.HEALING_STAT);
        this.damageType = damageType;
    }

    public Damage(ResourceKey<DamageType> damageType) {
        this($ -> damageType);
    }

    @Override
    public SpellComponentCastResult castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        Entity target = hitResult.getEntity();
        double damage = AMServerConfig.DAMAGE_DAMAGE.get();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (damage < 0 && target instanceof LivingEntity living) {
            living.heal((float) helper.getModifiedStat(-damage, AMSpells.HEALING_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        } else if (damage >= 0 || !(level instanceof ServerLevel serverLevel) || !serverLevel.getServer().isPvpAllowed()) {
            float finalDamage = (float) helper.getModifiedStat(damage, AMSpells.DAMAGE_STAT, modifiers, spell, level, caster, directEntity, hitResult);
            ItemStack stack = AMUtil.getEnchantedSpell(spell, modifiers, level, caster, directEntity, hitResult, Map.of(Enchantments.LOOTING, AMSpells.FORTUNE_STAT, AMEnchantments.DISMEMBERING, AMSpells.DISMEMBERING_STAT));
            spell = spell.updateDataComponents(map -> map.updateGrammar(grammar -> grammar.set(AMDataComponents.SPELL_DAMAGE.get(), grammar.getOrDefault(AMDataComponents.SPELL_DAMAGE.get(), SpellDamage.EMPTY).setDamage(target, damageType.apply(caster), finalDamage, stack))));
        }
        return SpellComponentCastResult.success(spell);
    }
}
