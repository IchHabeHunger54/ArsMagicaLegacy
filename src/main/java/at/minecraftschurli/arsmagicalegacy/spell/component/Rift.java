package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.menu.RiftMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Rift extends SpellComponent.CastEntity {
    public Rift() {
        super(AMSpells.RANGE_STAT);
    }

    @Override
    public Spell castEntity(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, EntityHitResult hitResult) {
        if (caster instanceof ServerPlayer player && hitResult.getEntity() instanceof LivingEntity entity) {
            int entityId = entity.getId();
            int size = (int) modifiers.stream()
                .filter(e -> e.getStats().contains(AMSpells.RANGE_STAT))
                .count() * 9 + 9;
            player.openMenu(new SimpleMenuProvider((id, inventory, $) -> new RiftMenu(id, inventory, entityId, size), AMTranslations.RIFT), buf -> {
                buf.writeInt(entityId);
                buf.writeInt(size);
            });
        }
        return spell;
    }
}
