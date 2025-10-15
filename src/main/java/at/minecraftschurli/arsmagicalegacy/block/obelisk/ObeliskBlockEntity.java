package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.LockCode;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.StackedContentsCompatible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ObeliskBlockEntity extends BlockEntity implements MenuProvider, Nameable, StackedContentsCompatible, WorldlyContainer {
    private static final int[] SLOTS = new int[]{0};
    private LockCode lockCode = LockCode.NO_LOCK;
    private ItemStack stack = ItemStack.EMPTY;
    private Component name;

    public ObeliskBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntities.OBELISK.get(), pos, blockState);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        lockCode = LockCode.fromTag(tag);
        if (tag.contains("CustomName", 8)) {
            name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }
        stack = ItemStack.parseOptional(registries, tag.getCompound("Item"));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        lockCode.addToTag(tag);
        if (name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(name, registries));
        }
        tag.put("Item", stack.saveOptional(registries));
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        super.applyImplicitComponents(componentInput);
        name = componentInput.get(DataComponents.CUSTOM_NAME);
        lockCode = componentInput.getOrDefault(DataComponents.LOCK, LockCode.NO_LOCK);
        componentInput.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY).copyInto(NonNullList.copyOf(List.of(stack)));
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        super.collectImplicitComponents(components);
        components.set(DataComponents.CUSTOM_NAME, name);
        if (!lockCode.equals(LockCode.NO_LOCK)) {
            components.set(DataComponents.LOCK, lockCode);
        }
        components.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(NonNullList.copyOf(List.of(stack))));
    }

    @SuppressWarnings("deprecation")
    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("Lock");
        tag.remove("Items");
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
    public Component getName() {
        return name == null ? AMTranslations.OBELISK : name;
    }

    @Override
    public Component getDisplayName() {
        return getName();
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
    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return BaseContainerBlockEntity.canUnlock(player, lockCode, getDisplayName()) ? new ObeliskMenu(containerId, playerInventory, this) : null;
    }

    @Override
    public void fillStackedContents(StackedContents contents) {
        contents.accountStack(stack);
    }
}
