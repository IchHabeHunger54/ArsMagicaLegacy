package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class InscriptionTableBlockEntity extends BlockEntity implements Container, MenuProvider {
    private static final String INVENTORY_KEY = ArsMagicaApi.modLoc("inventory").toString();
    private static final String SPELL_KEY = ArsMagicaApi.modLoc("spell").toString();
    private ItemStack stack = ItemStack.EMPTY;
    private InscriptionTableData data = InscriptionTableData.EMPTY;
    private boolean open;

    public InscriptionTableBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pos, state);
    }

    public InscriptionTableData getData() {
        return data;
    }

    public void setData(InscriptionTableData data) {
        this.data = data;
        setChanged();
    }

    public ItemStack setSpell(ItemStack stack) {
        stack.set(AMDataComponents.SPELL, getData().toSpell());
        return stack;
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        stack = tag.contains(INVENTORY_KEY) ? ItemStack.parseOptional(registries, tag.getCompound(INVENTORY_KEY)) : ItemStack.EMPTY;
        data = InscriptionTableData.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(INVENTORY_KEY, stack.saveOptional(registries));
        tag.put(SPELL_KEY, InscriptionTableData.CODEC.encodeStart(NbtOps.INSTANCE, data).getOrThrow());
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
        if (slot != 0 || amount <= 0) return ItemStack.EMPTY;
        ItemStack result = stack;
        stack = ItemStack.EMPTY;
        setChanged();
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        return removeItem(slot, 1);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot != 0) return;
        this.stack = stack;
        setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        BlockPos pos = getBlockPos();
        return player.level().getBlockEntity(pos) == this && player.distanceToSqr(Vec3.atCenterOf(pos)) <= 64D;
    }

    @Override
    public void clearContent() {
        stack = ItemStack.EMPTY;
        setChanged();
    }

    @Override
    public Component getDisplayName() {
        return AMTranslations.INSCRIPTION_TABLE;
    }

    @Override
    @Nullable
    public AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return open ? null : new InscriptionTableMenu(containerId, playerInventory, this);
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public void startOpen(Player player) {
        open = true;
    }

    @Override
    public void stopOpen(Player player) {
        open = false;
    }
}
