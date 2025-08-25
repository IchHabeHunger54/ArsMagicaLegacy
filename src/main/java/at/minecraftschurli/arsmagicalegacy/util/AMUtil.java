package at.minecraftschurli.arsmagicalegacy.util;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

public final class AMUtil {
    private AMUtil() {
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

    /**
     * @param first  VoxelShape #1.
     * @param second VoxelShape #2.
     * @param others All other VoxelShapes.
     * @return All given shapes, joined into a single VoxelShape.
     */
    public static VoxelShape joinShapes(VoxelShape first, VoxelShape second, VoxelShape... others) {
        VoxelShape result = Shapes.join(first, second, BooleanOp.OR);
        for (VoxelShape shape : others) {
            result = Shapes.join(result, shape, BooleanOp.OR);
        }
        return result;
    }
}
