package at.minecraftschurli.arsmagicalegacy.block.obelisk;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.api.etherium.ObeliskFuel;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMEtheriumTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ObeliskBlockEntity extends BlockEntity implements MenuProvider, Nameable, StackedContentsCompatible, WorldlyContainer, EtheriumHandler {
    private static final int[] SLOTS = new int[]{0};
    private LockCode lockCode = LockCode.NO_LOCK;
    private ItemStack stack = ItemStack.EMPTY;
    private Component name;
    private int etherium;
    private int burnTime;
    private int maxBurnTime;
    private int etheriumPerTick;

    public ObeliskBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.OBELISK.get(), pos, state);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (burnTime > 0) {
            etherium += etheriumPerTick;
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
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        lockCode = LockCode.fromTag(tag);
        if (tag.contains("CustomName", CompoundTag.TAG_STRING)) {
            name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }
        stack = ItemStack.parseOptional(registries, tag.getCompound("Item"));
        etherium = tag.getInt("Etherium");
        burnTime = tag.getInt("BurnTime");
        maxBurnTime = tag.getInt("MaxBurnTime");
        etheriumPerTick = tag.getInt("EtheriumPerTick");
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        lockCode.addToTag(tag);
        if (name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(name, registries));
        }
        if (!stack.isEmpty()) {
            tag.put("Item", stack.save(registries));
        }
        tag.putInt("Etherium", etherium);
        tag.putInt("BurnTime", burnTime);
        tag.putInt("MaxBurnTime", maxBurnTime);
        tag.putInt("EtheriumPerTick", etheriumPerTick);
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

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return type.is(AMEtheriumTypes.NEUTRAL) ? etherium : 0;
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return type.is(AMEtheriumTypes.NEUTRAL) ? AMServerConfig.OBELISK_MAX_ETHERIUM.get() : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        if (type.is(AMEtheriumTypes.NEUTRAL)) {
            etherium = amount;
            setChanged();
        }
    }

    @Override
    public int addAmount(Holder<EtheriumType> type, int amount) {
        return amount;
    }

    @Override
    public int subtractAmount(Holder<EtheriumType> type, int amount) {
        if (!type.is(AMEtheriumTypes.NEUTRAL)) return amount;
        int min = Math.min(etherium, amount);
        etherium -= min;
        setChanged();
        return amount - min;
    }

    public boolean isLit() {
        return maxBurnTime > 0;
    }

    public float getLitProgress() {
        return isLit() ? (float) burnTime / maxBurnTime : 0;
    }
}
