package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import net.neoforged.neoforge.event.level.BlockEvent;

import java.util.List;
import java.util.UUID;

public class Dig extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_dig");

    public Dig() {
        super(AMSpells.FORTUNE_STAT, AMSpells.MINING_POWER_STAT, AMSpells.SILK_TOUCH_STAT);
    }

    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, Level level, LivingEntity caster, Entity directEntity, BlockHitResult hitResult) {
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return spell;
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        float hardness = state.getDestroySpeed(level, pos);
        if (hardness < 0) return spell;
        SpellHelper helper = ArsMagicaApi.spellHelper();
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        TagKey<Block> incorrectTag = helper.getIncorrectTagForToolTier((int) helper.getModifiedStat(AMServerConfig.DIG_TOOL_TIER.get(), AMSpells.MINING_POWER_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        if (state.requiresCorrectToolForDrops() && state.is(incorrectTag)) return spell;
        double manaCost = hardness * AMServerConfig.DIG_MANA_FACTOR.get();
        if (manaHelper.getMana(caster) <= manaCost || burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) <= manaCost) return spell;
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(serverLevel, GAME_PROFILE);
        if (player instanceof GameMasterBlock && !player.canUseGameMasterBlocks()) return spell;
        if (player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer())) return spell;
        if (NeoForge.EVENT_BUS.post(new BlockEvent.BreakEvent(level, pos, state, player)).isCanceled()) return spell;
        manaHelper.decreaseMana(caster, manaCost);
        burnoutHelper.increaseBurnout(caster, manaCost);
        state = state.getBlock().playerWillDestroy(level, pos, state, player);
        level.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(state));
        if (!state.onDestroyedByPlayer(level, pos, player, true, level.getFluidState(pos))) return spell;
        ItemStack stack = AMItems.SPELL.toStack();
        stack.set(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(incorrectTag)), Float.MAX_VALUE, 0));
        Registry<Enchantment> enchantments = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
        stack.enchant(enchantments.getHolderOrThrow(Enchantments.FORTUNE), (int) helper.getModifiedStat(0, AMSpells.FORTUNE_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        stack.enchant(enchantments.getHolderOrThrow(Enchantments.SILK_TOUCH), (int) helper.getModifiedStat(0, AMSpells.SILK_TOUCH_STAT, modifiers, spell, level, caster, directEntity, hitResult));
        state.getBlock().playerDestroy(level, player, pos, state, level.getBlockEntity(pos), stack);
        return spell;
    }
}
