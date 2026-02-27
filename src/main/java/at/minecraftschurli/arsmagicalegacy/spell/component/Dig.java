package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.BurnoutHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.ManaHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellCastContext;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponentCastResult;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Dig extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_dig");

    public Dig() {
        super(AMSpells.FORTUNE_STAT, AMSpells.MINING_POWER_STAT, AMSpells.SILK_TOUCH_STAT);
    }

    @Override
    public SpellComponentCastResult castBlock(List<SpellModifier> modifiers, SpellCastContext context, BlockHitResult hitResult) {
        Spell spell = context.spell();
        if (!(context.level() instanceof ServerLevel level)) return SpellComponentCastResult.pass(spell);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        float hardness = state.getDestroySpeed(level, pos);
        if (hardness < 0) return SpellComponentCastResult.pass(spell);
        SpellHelper helper = ArsMagicaApi.spellHelper();
        ManaHelper manaHelper = ArsMagicaApi.manaHelper();
        BurnoutHelper burnoutHelper = ArsMagicaApi.burnoutHelper();
        TagKey<Block> incorrectTag = helper.getIncorrectTagForToolTier((int) helper.getModifiedStat(AMServerConfig.DIG_TOOL_TIER.get(), AMSpells.MINING_POWER_STAT, modifiers, context));
        if (state.requiresCorrectToolForDrops() && state.is(incorrectTag)) return SpellComponentCastResult.pass(spell);
        LivingEntity caster = context.caster();
        double manaCost = hardness * AMServerConfig.DIG_MANA_FACTOR.get();
        if (manaHelper.getMana(caster) <= manaCost || burnoutHelper.getMaxBurnout(caster) - burnoutHelper.getBurnout(caster) <= manaCost) return SpellComponentCastResult.failure(spell, AMTranslations.SPELL_FAIL_NOT_ENOUGH_MANA);
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(level, GAME_PROFILE);
        Block block = state.getBlock();
        if (block instanceof GameMasterBlock && !player.canUseGameMasterBlocks() || player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer())) return SpellComponentCastResult.pass(spell);
        manaHelper.decreaseMana(caster, manaCost);
        burnoutHelper.increaseBurnout(caster, manaCost);
        if (AMUtil.cancelDestroyBlock(level, pos, state, player)) return SpellComponentCastResult.success(spell);
        ItemStack stack = AMUtil.getEnchantedSpell(modifiers, context, Map.of(Enchantments.FORTUNE, AMSpells.FORTUNE_STAT, Enchantments.SILK_TOUCH, AMSpells.SILK_TOUCH_STAT));
        stack.set(DataComponents.TOOL, new Tool(List.of(Tool.Rule.deniesDrops(incorrectTag)), Float.MAX_VALUE, 0));
        Block.dropResources(state, level, pos, level.getBlockEntity(pos), player, stack);
        return SpellComponentCastResult.success(spell);
    }
}
