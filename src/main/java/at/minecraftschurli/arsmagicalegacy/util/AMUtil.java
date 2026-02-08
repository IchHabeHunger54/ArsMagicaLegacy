package at.minecraftschurli.arsmagicalegacy.util;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.init.AMAttachments;
import at.minecraftschurli.arsmagicalegacy.init.AMBlocks;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.item.SpellRecipeItem;
import at.minecraftschurli.arsmagicalegacy.packet.OpenBookInLecternPacket;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.attachment.AttachmentHolder;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;

public final class AMUtil {
    public static final ResourceLocation MISSINGNO = ResourceLocation.withDefaultNamespace("missingno");

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

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    public static Holder<SpellPart> spellPart(Holder<Skill> skill) {
        return ArsMagicaApi.spellPartRegistry().getHolder(skill.getKey().location()).orElse(null);
    }

    @SuppressWarnings("DataFlowIssue")
    @Nullable
    public static Holder<Skill> skill(Holder<SpellPart> part, boolean client) {
        return AMRegistries.skills(client).getHolder(part.getKey().location()).orElse(null);
    }

    public static Vec3 bezier(Vec3 start, Vec3 control1, Vec3 control2, Vec3 end, double delta) {
        delta = Math.clamp(delta, 0, 1);
        double invertedDelta = 1 - delta;
        return Vec3.ZERO
            .add(start.scale(invertedDelta * invertedDelta * invertedDelta))
            .add(control1.scale(3 * invertedDelta * invertedDelta * delta))
            .add(control2.scale(3 * invertedDelta * delta * delta))
            .add(end.scale(delta * delta * delta));
    }

    public static void doCompendiumConversion(AttachmentHolder entity, Level level, Vec3 vec, AABB aabb, Supplier<ItemStack> bookGetter, Consumer<ItemStack> bookSetter) {
        if (!bookGetter.get().is(AMTags.Items.ARCANE_COMPENDIUM_BOOKS)) {
            if (entity.hasData(AMAttachments.COMPENDIUM_TIMER)) {
                entity.removeData(AMAttachments.COMPENDIUM_TIMER);
            }
            return;
        }
        List<BlockPos> positions = new ArrayList<>();
        for (BlockPos pos : BlockPos.betweenClosed(BlockPos.containing(aabb.getMinPosition()), BlockPos.containing(aabb.getMaxPosition()))) {
            BlockPos above = pos.above();
            if (level.getBlockState(pos).is(AMBlocks.LIQUID_ETHERIUM) && !level.getBlockState(above).isSolidRender(level, above)) {
                positions.add(new BlockPos(pos));
            }
        }
        if (!positions.isEmpty()) {
            int timer = entity.getData(AMAttachments.COMPENDIUM_TIMER);
            if (timer >= AMServerConfig.ARCANE_COMPENDIUM_CONVERSION_DURATION.getAsInt()) {
                bookSetter.accept(ArsMagicaApi.book());
                if (level.isClientSide()) {
                    AMClientUtil.spawnArcaneCompendiumConversionFinishParticles(vec);
                }
                entity.removeData(AMAttachments.COMPENDIUM_TIMER);
            } else {
                if (level.isClientSide()) {
                    AMClientUtil.spawnArcaneCompendiumConversionParticles(positions, vec);
                }
                entity.setData(AMAttachments.COMPENDIUM_TIMER, timer + 1);
            }
        } else if (entity.hasData(AMAttachments.COMPENDIUM_TIMER)) {
            entity.removeData(AMAttachments.COMPENDIUM_TIMER);
        }
    }

    public static <A, B> BiConsumer<A, B> dropResult(BiFunction<A, B, ?> function) {
        return function::apply;
    }

    public static <T> T getByTick(T[] array, int tick) {
        return array[tick % array.length];
    }

    public static <T> T getByTick(List<T> list, int tick) {
        return list.get(tick % list.size());
    }

    public static HitResult getHitResult(Vec3 from, Vec3 to, Entity entity, ClipContext.Block blockContext, ClipContext.Fluid fluidContext) {
        HitResult hitResult = entity.level().clip(new ClipContext(from, to, blockContext, fluidContext, entity));
        if (hitResult.getType() != HitResult.Type.MISS) {
            to = hitResult.getLocation();
        }
        HitResult entityHitResult = ProjectileUtil.getEntityHitResult(entity.level(), entity, from, to, entity.getBoundingBox().expandTowards(entity.getDeltaMovement()).inflate(1), e -> true);
        if (entityHitResult != null) {
            hitResult = entityHitResult;
        }
        return hitResult;
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

    public static <T> Optional<T> ifModLoaded(String modId, Supplier<T> supplier) {
        return ModList.get().isLoaded(modId) ? Optional.of(supplier.get()) : Optional.empty();
    }

    public static VoxelShape joinShapes(VoxelShape first, VoxelShape... others) {
        VoxelShape result = first;
        for (VoxelShape shape : others) {
            result = Shapes.joinUnoptimized(result, shape, BooleanOp.OR);
        }
        return result.optimize();
    }

    public static Collector<MutableComponent, MutableComponent, MutableComponent> joiningComponents(Component delimiter) {
        return Collector.of(Component::empty, dropResult((c1, c2) -> !c1.getString().isEmpty() ? c1.append(delimiter).append(c2) : c1.append(c2)), (c1, c2) -> !c1.getString().isEmpty() ? c1.append(delimiter).append(c2) : c1.append(c2));
    }

    @SafeVarargs
    public static <T> NonNullList<T> nonNullList(T defaultValue, T... entries) {
        NonNullList<T> list = NonNullList.withSize(entries.length, defaultValue);
        for (int i = 0; i < entries.length; i++) {
            list.set(i, entries[i]);
        }
        return list;
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
}
