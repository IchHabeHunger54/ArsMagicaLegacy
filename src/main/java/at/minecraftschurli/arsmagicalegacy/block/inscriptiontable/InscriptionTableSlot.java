package at.minecraftschurli.arsmagicalegacy.block.inscriptiontable;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMTags;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class InscriptionTableSlot extends Slot {
    private final InscriptionTableBlockEntity blockEntity;

    public InscriptionTableSlot(InscriptionTableBlockEntity blockEntity, int x, int y) {
        super(blockEntity, 0, x, y);
        this.blockEntity = blockEntity;
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }

    @Override
    public boolean mayPlace(ItemStack stack) {
        return stack.is(AMTags.Items.INSCRIPTION_TABLE_BOOKS);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void set(ItemStack stack) {
        super.set(stack);
        if (stack.has(AMDataComponents.SPELL)) {
            blockEntity.setData(InscriptionTableData.fromSpell(stack.get(AMDataComponents.SPELL), blockEntity.getLevel().registryAccess()));
        }
    }

    @Override
    public Optional<ItemStack> tryRemove(int count, int decrement, Player player) {
        Spell spell = blockEntity.getData().toSpell();
        return super.tryRemove(count, decrement, player).map(stack -> {
            if (spell.isEmpty()) return stack;
            BlockPos pos = blockEntity.getBlockPos();
            player.level().playSound(null, pos.getX(), pos.getY(), pos.getZ(), AMSounds.TAKE_BOOK.get(), SoundSource.BLOCKS, 1f, 1f);
            return blockEntity.setSpell(AMItems.SPELL_RECIPE.toStack());
        });
    }
}
