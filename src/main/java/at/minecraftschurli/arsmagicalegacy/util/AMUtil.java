package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.client.util.ClientUtil;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.SpellRecipeItem;
import at.minecraftschurli.arsmagicalegacy.packet.OpenBookInLecternPacket;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.server.ServerLifecycleHooks;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public final class AMUtil {
    private AMUtil() {
    }

    public static boolean handleLecternUse(Level level, BlockPos pos, BlockState state, LecternBlockEntity lectern, Player player, InteractionHand hand) {
        ItemStack book = lectern.getBook();
        if (book.isEmpty()) {
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.is(ItemTags.LECTERN_BOOKS) || !stack.is(AMItems.SPELL_RECIPE)) return false;
            int pageCount = SpellRecipeItem.getPageCount(stack);
            if (pageCount == 0) return false;
            lectern.setBook(stack.consumeAndReturn(1, player));
            LecternBlock.resetBookState(player, level, pos, state, true);
            level.playSound(null, pos, SoundEvents.BOOK_PUT, SoundSource.BLOCKS, 1f, 1f);
            lectern.pageCount = pageCount;
            return true;
        } else if (book.is(AMItems.SPELL_RECIPE)) {
            if (player.isSecondaryUseActive()) {
                takeLecternBook(player, level, pos);
            } else if (!level.isClientSide() && player instanceof ServerPlayer sp) {
                PacketDistributor.sendToPlayer(sp, new OpenBookInLecternPacket(pos, book));
            }
            return true;
        }
        return false;
    }

    public static void takeLecternBook(Player player, Level level, BlockPos pos) {
        if (!(level.getBlockEntity(pos) instanceof LecternBlockEntity lectern)) return;
        ItemStack stack = lectern.getBook();
        lectern.setBook(ItemStack.EMPTY);
        LecternBlock.resetBookState(player, level, pos, level.getBlockState(pos), false);
        if (!player.getInventory().add(stack)) {
            player.drop(stack, false);
        }
    }

    public static <T> int getCommandSelf(CommandContext<CommandSourceStack> context, Function<ServerPlayer, T> function, ToIntFunction<T> toIntFunction, BiFunction<Component, T, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        T value = function.apply(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(player.getDisplayName(), value), true);
        return toIntFunction.applyAsInt(value);
    }

    public static <T> int getCommand(CommandContext<CommandSourceStack> context, Function<ServerPlayer, T> function, ToIntFunction<T> toIntFunction, BiFunction<Component, T, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "target");
        T value = function.apply(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(player.getDisplayName(), value), true);
        return toIntFunction.applyAsInt(value);
    }

    public static int runCommandSelf(CommandContext<CommandSourceStack> context, Consumer<ServerPlayer> consumer, Function<Component, Component> messageFactory) throws CommandSyntaxException {
        ServerPlayer player = context.getSource().getPlayerOrException();
        consumer.accept(player);
        context.getSource().sendSuccess(() -> messageFactory.apply(player.getDisplayName()), true);
        return 1;
    }

    public static int runCommand(CommandContext<CommandSourceStack> context, Consumer<ServerPlayer> consumer, Function<Component, Component> singleMessageFactory, IntFunction<Component> multipleMessageFactory) throws CommandSyntaxException {
        Collection<ServerPlayer> players = EntityArgument.getPlayers(context, "target");
        players.forEach(consumer);
        if (players.size() == 1) {
            context.getSource().sendSuccess(() -> singleMessageFactory.apply(players.iterator().next().getDisplayName()), true);
        } else {
            context.getSource().sendSuccess(() -> multipleMessageFactory.apply(players.size()), true);
        }
        return players.size();
    }

    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }

    public static VoxelShape joinShapes(VoxelShape first, VoxelShape... others) {
        VoxelShape result = first;
        for (VoxelShape shape : others) {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    @SuppressWarnings("DataFlowIssue")
    public static RegistryAccess registryAccess() {
        return FMLEnvironment.dist.isClient() ? ClientUtil.registryAccess() : ServerLifecycleHooks.getCurrentServer().registryAccess();
    }

    public static RegistryAccess registryAccess(BlockGetter blockGetter) {
        return blockGetter instanceof Level level ? level.registryAccess() : registryAccess();
    }
}
