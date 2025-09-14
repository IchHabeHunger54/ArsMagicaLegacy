package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Storm extends SpellComponent {
    public Storm() {
        super(AMSpells.DURATION_STAT, AMSpells.RANGE_STAT);
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (!(caster.level() instanceof ServerLevel level)) return spell;
        SpellHelper helper = ArsMagicaApi.spellHelper();
        if (!(level.getRainLevel(1f) > 0.9)) {
            level.setWeatherParameters(0, (int) helper.getModifiedStat(AMServerConfig.STORM_DURATION.get(), AMSpells.DURATION_STAT, modifiers, spell, caster, directEntity, hitResult), true, true);
        }
        int range = (int) helper.getModifiedStat(AMServerConfig.STORM_RANGE.get(), AMSpells.RANGE_STAT, modifiers, spell, caster, directEntity, hitResult);
        RandomSource random = level.getRandom();
        double randomValue = random.nextDouble();
        if (randomValue < AMServerConfig.STORM_LIGHTNING_BOLT_CHANCE.get()) {
            double x = caster.getX() + random.nextDouble() * range - range / 2.;
            double z = caster.getZ() + random.nextDouble() * range - range / 2.;
            double y = caster.getY();
            while (!level.canSeeSky(BlockPos.containing(x, y, z))) {
                y++;
            }
            while (level.getBlockState(BlockPos.containing(x, y - 1, z)).getBlock().equals(Blocks.AIR)) {
                y--;
            }
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            bolt.setPos(x, y, z);
            bolt.setVisualOnly(false);
            level.addFreshEntity(bolt);
        } else if (randomValue < AMServerConfig.STORM_LIGHTNING_BOLT_TARGET_CHANCE.get()) {
            List<Entity> entities = level.getEntities(caster, caster.getBoundingBox().inflate(range / 2., range / 2., range / 2.));
            if (entities.isEmpty()) return spell;
            Entity entity = entities.get(random.nextInt(entities.size()));
            if (entity == null || !level.canSeeSky(entity.blockPosition())) return spell;
            if (caster instanceof Player player) {
                entity.hurt(level.damageSources().playerAttack(player), 1);
            }
            LightningBolt bolt = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
            bolt.setPos(entity.position());
            bolt.setVisualOnly(false);
            level.addFreshEntity(bolt);
        }
        return spell;
    }
}
