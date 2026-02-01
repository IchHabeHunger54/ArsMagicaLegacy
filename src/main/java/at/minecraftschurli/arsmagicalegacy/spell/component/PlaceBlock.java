package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;

import java.util.List;
import java.util.UUID;

public class PlaceBlock extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_place_block");

    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, LivingEntity caster, Entity directEntity, BlockHitResult hitResult) {
        Level level = directEntity.level();
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return spell;
        Block block = spell.dataComponents().grammar().get(AMDataComponents.SPELL_BLOCK.get());
        if (block == null || block.defaultBlockState().isAir()) return spell;
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(serverLevel, GAME_PROFILE);
        ItemStack stack = new ItemStack(block.asItem());
        Inventory inventory = player.getInventory();
        if (!player.isCreative() && !inventory.contains(stack)) return spell;
        BlockPos pos = hitResult.getBlockPos();
        BlockPlaceContext context = new BlockPlaceContext(level, player, InteractionHand.MAIN_HAND, stack, hitResult);
        if (!level.getBlockState(pos).canBeReplaced(context)) {
            pos = pos.offset(hitResult.getDirection().getNormal());
        }
        BlockState state = block.getStateForPlacement(context);
        if (state == null || !state.canSurvive(level, pos)) return spell;
        level.setBlockAndUpdate(pos, state);
        block.setPlacedBy(level, pos, state, player, stack);
        if (!player.isCreative()) {
            inventory.getItem(inventory.findSlotMatchingItem(stack)).shrink(1);
        }
        return spell;
    }

    @Override
    public DataComponentType<?> getDataComponentType() {
        return AMDataComponents.SPELL_BLOCK.get();
    }
}
