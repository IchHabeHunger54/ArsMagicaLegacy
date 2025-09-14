package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Explosion extends SpellComponent {
    public Explosion() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (hitResult != null && hitResult.getType() != HitResult.Type.MISS) {
            Vec3 location = hitResult.getLocation();
            caster.level().explode(directEntity, location.x(), location.y(), location.z(), (float) ArsMagicaApi.spellHelper().getModifiedStat(AMServerConfig.EXPLOSION_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, directEntity, hitResult), caster instanceof Player ? Level.ExplosionInteraction.BLOCK : Level.ExplosionInteraction.MOB);
        }
        return spell;
    }
}
