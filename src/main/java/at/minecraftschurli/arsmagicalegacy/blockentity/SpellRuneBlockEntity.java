package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.packet.SetBlockEntityOwnerPacket;
import at.minecraftschurli.arsmagicalegacy.util.OwnerSetter;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class SpellRuneBlockEntity extends AMBlockEntity<SpellRuneBlockEntity.Data> implements OwnerSetter {
    private Spell spell;
    private int power;
    private LivingEntity owner;

    public SpellRuneBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.SPELL_RUNE.get(), pos, state, Data.CODEC);
    }

    @Override
    public void fromData(Data data) {
        spell = data.spell;
        power = data.power;
        if (data.owner.isPresent() && level instanceof ServerLevel serverLevel && serverLevel.getEntity(data.owner.get()) instanceof LivingEntity living) {
            owner = living;
            PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(getBlockPos()), new SetBlockEntityOwnerPacket(getBlockPos(), owner.getId()));
        }
    }

    @Override
    public Data toData() {
        return new Data(spell, power, owner == null ? Optional.empty() : Optional.of(owner.getUUID()));
    }

    @Override
    public void setOwner(int id) {
        if (level != null && level.getEntity(id) instanceof LivingEntity living) {
            owner = living;
        }
    }

    public void setData(Spell spell, int power, @Nullable LivingEntity owner) {
        this.spell = spell;
        this.power = power;
        this.owner = owner;
    }

    public void cast(BlockState state, Level level, BlockPos pos, Entity entity) {
        spell = ArsMagicaApi.spellHelper().castGrammar(spell, level, owner, owner, new EntityHitResult(entity));
        power--;
        if (power < 1) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
        } else {
            setChanged();
        }
    }

    public record Data(Spell spell, int power, Optional<UUID> owner) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Spell.CODEC.fieldOf("spell").forGetter(Data::spell),
            Codec.INT.fieldOf("power").forGetter(Data::power),
            UUIDUtil.CODEC.optionalFieldOf("owner").forGetter(Data::owner)
        ).apply(inst, Data::new));
    }
}
