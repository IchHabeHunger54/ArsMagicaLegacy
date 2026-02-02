package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.blockentity.SpellRuneBlockEntity;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class Rune extends SecondarySpellShape {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_rune");

    public Rune() {
        super(AMSpells.RUNE_POWER_STAT);
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, @Nullable HitResult hitResult) {
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel) || !(hitResult instanceof BlockHitResult blockHitResult)) return spell;
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(serverLevel, GAME_PROFILE);
        Direction direction = blockHitResult.getDirection();
        BlockPos pos = blockHitResult.getBlockPos().offset(direction.getNormal());
        BlockState state = AMBlocks.SPELL_RUNE.get().getStateForPlacement(new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, ItemStack.EMPTY, new BlockHitResult(hitResult.getLocation(), direction, pos, false)));
        if (state != null) {
            level.setBlockAndUpdate(pos, state);
            if (level.getBlockEntity(pos) instanceof SpellRuneBlockEntity spellRune) {
                spellRune.setData(spell, (int) ArsMagicaApi.spellHelper().getModifiedStat(1, AMSpells.RUNE_POWER_STAT, modifiers, spell, level, caster, directEntity, hitResult), caster);
            }
        }
        return spell;
    }
}
