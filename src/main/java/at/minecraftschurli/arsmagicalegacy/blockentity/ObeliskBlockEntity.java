package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import at.minecraftschurli.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.arsmagicalegacy.block.ObeliskBlock;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import at.minecraftschurli.arsmagicalegacy.util.AMUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ObeliskBlockEntity extends EtheriumGeneratorBlockEntity implements StackedContentsCompatible, WorldlyContainer {
    private static final String ITEMS_KEY = "Items";
    private static final String BURN_TIME_KEY = "burn_time";
    private static final String MAX_BURN_TIME_KEY = "max_burn_time";
    private static final String ETHERIUM_PER_TICK_KEY = "etherium_per_tick";
    private static final int[] SLOTS = new int[]{0};
    private ItemStack stack = ItemStack.EMPTY;
    private int burnTime;
    private int maxBurnTime;
    private int etheriumPerTick;

    public ObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.OBELISK.get(), pos, state, AMEtheriumTypes.NEUTRAL);
    }

    @Override
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTime > 0) {
            etherium = Math.clamp(etherium + etheriumPerTick, 0, getMaxAmount());
            burnTime--;
            setChanged();
        }
        if (burnTime <= 0) {
            maxBurnTime = 0;
            ObeliskFuel fuel = ObeliskFuel.getFuel(stack);
            if (fuel != null) {
                burnTime = fuel.burnTime();
                maxBurnTime = fuel.burnTime();
                etheriumPerTick = fuel.etheriumPerTick();
                if (stack.hasCraftingRemainingItem()) {
                    stack = stack.getCraftingRemainingItem();
                } else {
                    stack.shrink(1);
                }
                setChanged();
            }
        }
        boolean lit = burnTime > 0;
        if (state.getValue(ObeliskBlock.LIT) != lit) {
            level.setBlockAndUpdate(pos, state.setValue(ObeliskBlock.LIT, lit));
        }
    }

    @Override
    public int getMaxAmount() {
        return AMServerConfig.OBELISK_MAX_ETHERIUM.get();
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        stack = ItemStack.parseOptional(registries, tag.getCompound(ITEMS_KEY));
        burnTime = tag.getInt(BURN_TIME_KEY);
        maxBurnTime = tag.getInt(MAX_BURN_TIME_KEY);
        etheriumPerTick = tag.getInt(ETHERIUM_PER_TICK_KEY);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (!stack.isEmpty()) {
            tag.put(ITEMS_KEY, stack.save(registries));
        }
        tag.putInt(BURN_TIME_KEY, burnTime);
        tag.putInt(MAX_BURN_TIME_KEY, maxBurnTime);
        tag.putInt(ETHERIUM_PER_TICK_KEY, etheriumPerTick);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        componentInput.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(AMUtil.nonNullList(ItemStack.EMPTY, stack));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(NonNullList.copyOf(List.of(stack))));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove(ITEMS_KEY);
    }

    @Override
    public int getContainerSize() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public ItemStack getItem(int slot) {
        return slot == 0 ? stack : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        if (slot != 0) return ItemStack.EMPTY;
        ItemStack stack = getItem(slot).split(amount);
        if (!stack.isEmpty()) {
            setChanged();
        }
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        if (slot != 0) return ItemStack.EMPTY;
        ItemStack stack = this.stack;
        this.stack = ItemStack.EMPTY;
        return stack;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) return;
        this.stack = stack;
        this.stack.limitSize(getMaxStackSize(this.stack));
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
    }

    @Override
    public int[] getSlotsForFace(Direction side) {
        return SLOTS;
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return canTakeItem(this, index, stack);
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        contents.accountStack(stack);
    }
}
