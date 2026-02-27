package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.attachment.SummonMinionsAttachment;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.EventHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Summon extends SpellComponent {
    @Override
    public SpellComponentCastResult cast(List<SpellModifier> modifiers, SpellCastContext context) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        if (caster == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_CASTER);
        HitResult hitResult = context.hitResult();
        if (hitResult == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NO_HIT);
        EntityType<?> type = spell.dataComponents().grammar().get(AMDataComponents.SPELL_SUMMON.get());
        if (type == null) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_SUMMON_NO_SELECTION);
        SummonMinionsAttachment attachment = caster.getData(AMAttachments.SUMMON_MINIONS);
        if (attachment.size() >= ArsMagicaApi.spellHelper().getMaxSummons(caster)) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_COMPONENT_SUMMON_TOO_MANY);
        if (!(type.create(level) instanceof Mob mob)) return SpellComponentCastResult.pass(spell);
        mob.setPos(hitResult.getLocation());
        EventHooks.finalizeMobSpawn(mob, level, level.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
        if (!(caster instanceof Player player) || !player.isCreative()) {
            double mana = mob.getMaxHealth() * AMServerConfig.SUMMON_MANA_COST.get();
            ManaHelper helper = ArsMagicaApi.manaHelper();
            if (helper.getMana(caster) < mana) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NOT_ENOUGH_MANA);
            helper.decreaseMana(caster, mana);
        }
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            mob.setDropChance(slot, 0);
        }
        mob.setCanPickUpLoot(true);
        mob.setData(AMAttachments.SUMMON_OWNER, caster.getUUID());
        caster.setData(AMAttachments.SUMMON_MINIONS, attachment.add(mob.getUUID()));
        if (mob instanceof TamableAnimal animal) {
            animal.setOwnerUUID(caster.getUUID());
        }
        level.addFreshEntity(mob);
        return SpellComponentCastResult.success(spell);
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_SUMMON.get();
    }
}
