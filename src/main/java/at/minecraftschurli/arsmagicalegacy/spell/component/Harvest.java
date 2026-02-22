package at.minecraftschurli.arsmagicalegacy.spell.component;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.plant.GrowthContext;
import at.minecraftschurli.arsmagicalegacy.api.plant.Plant;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellComponent;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellStat;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Harvest extends SpellComponent.CastBlock {
    private static final GameProfile GAME_PROFILE = new GameProfile(UUID.randomUUID(), ArsMagicaApi.MOD_ID + "_harvest");
    private final boolean replant;

    public Harvest(boolean replant) {
        super(AMSpells.FORTUNE_STAT, AMSpells.SILK_TOUCH_STAT);
        this.replant = replant;
    }

    @Override
    public Spell castBlock(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, BlockHitResult hitResult) {
        if (level.isClientSide() || !(level instanceof ServerLevel serverLevel)) return spell;
        ServerPlayer player = caster instanceof ServerPlayer p ? p : FakePlayerFactory.get(serverLevel, GAME_PROFILE);
        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);
        if (state.getBlock() instanceof GameMasterBlock && !player.canUseGameMasterBlocks() || player.blockActionRestricted(level, pos, player.gameMode.getGameModeForPlayer())) return spell;
        for (Plant plant : AMUtil.getPlants(state)) {
            Map<ResourceKey<Enchantment>, SpellStat> enchantments = Map.of(Enchantments.FORTUNE, AMSpells.FORTUNE_STAT, Enchantments.SILK_TOUCH, AMSpells.SILK_TOUCH_STAT);
            ItemStack tool = plant.tool();
            GrowthContext context = plant.createContext(player, serverLevel, pos, state, tool.isEmpty()
                ? AMUtil.getEnchantedSpell(spell, modifiers, level, caster, directEntity, hitResult, enchantments)
                : AMUtil.getEnchanted(tool.copy(), spell, modifiers, level, caster, directEntity, hitResult, enchantments));
            if (!plant.growthType().canHarvest(context)) continue;
            plant.growthType().harvest(context, replant).forEach(stack -> {
                if (player.isFakePlayer() || !player.getInventory().add(stack)) {
                    player.drop(stack, false);
                }
            });
            break;
        }
        return spell;
    }
}
