package at.minecraftschurli.arsmagicalegacy.block.altar;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.helper.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.arsmagicalegacy.item.SpellItem;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LecternBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class AltarCoreBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> CAMO = new ModelProperty<>();
    private static final String CAMO_KEY = ArsMagicaApi.modLoc("camo").toString();
    private static final String POWER_KEY = ArsMagicaApi.modLoc("power").toString();
    private static final String CURRENT_KEY = ArsMagicaApi.modLoc("current").toString();
    private int checkCounter = 0;
    private Direction direction;
    private BlockPos lecternPos;
    private BlockPos leverPos;
    private AltarMaterial material;
    private AltarCapMaterial capMaterial;
    private BlockState camo;
    private int power;
    private int currentIngredient;
    private List<SpellIngredient> recipe;

    public AltarCoreBlockEntity(BlockPos pos, BlockState blockState) {
        super(AMBlockEntities.ALTAR_CORE.get(), pos, blockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        checkCounter--;
        if (checkCounter <= 0) {
            checkCounter = AMServerConfig.ALTAR_CHECK_INTERVAL.get();
            boolean multiblock = checkMultiblock();
            if (!multiblock) {
                direction = null;
                lecternPos = null;
                leverPos = null;
                material = null;
                capMaterial = null;
                camo = null;
                power = 0;
                currentIngredient = 0;
                recipe = null;
                setChanged();
            }
            if (state.getValue(AltarCoreBlock.FORMED) != multiblock) {
                level.setBlockAndUpdate(pos, state.setValue(AltarCoreBlock.FORMED, multiblock));
            }
        }
        if (!state.getValue(AltarCoreBlock.FORMED) || recipe == null) return;
        if (currentIngredient >= recipe.size()) {
            currentIngredient = 0;
        }
        SpellIngredient ingredient = getCurrentIngredient();
        if (ingredient == null || !ingredient.consume(level, pos)) return;
        currentIngredient++;
        if (currentIngredient < recipe.size()) return;
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() - 1.5, pos.getZ() + 0.5, SpellItem.set(AMItems.SPELL.toStack(), AMDataComponents.SPELL.get(), getSpell()), 0, 0.2, 0);
        entity.setPickUpDelay(40);
        entity.setExtendedLifetime();
        level.addFreshEntity(entity);
        level.playSound(null, pos.getX(), pos.getY() - 2, pos.getZ(), AMSounds.SPELLCRAFTING_FINISH.get(), SoundSource.BLOCKS, 1, 1);
        currentIngredient = 0;
    }

    private boolean checkMultiblock() {
        Registry<AltarCapMaterial> capMaterialRegistry = level.registryAccess().registryOrThrow(AMRegistryKeys.ALTAR_CAP_MATERIAL);
        Registry<AltarMaterial> materialRegistry = level.registryAccess().registryOrThrow(AMRegistryKeys.ALTAR_MATERIAL);
        for (Direction direction : Direction.Plane.HORIZONTAL) {
            BlockPos pos = getBlockPos().relative(direction, 2).relative(direction.getCounterClockWise(), 2).below(3);
            BlockState state = level.getBlockState(pos);
            if (state.is(Blocks.LECTERN)) {
                this.direction = direction;
                lecternPos = pos;
                leverPos = pos.relative(direction.getClockWise(), 4).above(1);
                Block block = level.getBlockState(getBlockPos().relative(direction.getClockWise())).getBlock();
                material = materialRegistry.stream().filter(m -> block == m.block()).findFirst().orElse(null);
                Block capBlock = level.getBlockState(getBlockPos().relative(direction).relative(direction.getClockWise(), 2)).getBlock();
                capMaterial = capMaterialRegistry.stream().filter(m -> capBlock == m.block()).findFirst().orElse(null);
                camo = material.block().defaultBlockState();
                break;
            }
        }
        if (lecternPos == null || leverPos == null || material == null || capMaterial == null || camo == null || direction == null) return false;
        if (!level.getBlockState(leverPos).is(Blocks.LEVER)) return false;
        if (AMMultiblocks.ALTAR.validate(level, getBlockPos().below(4)) == null) return false;
        power = material.power() + capMaterial.power();
        Spell spell = getSpell();
        SpellHelper helper = ArsMagicaApi.spellHelper();
        recipe = spell != null && helper.getFlatRecipe(spell).size() <= power ? helper.getRecipe(spell) : null;
        if (recipe == null) {
            currentIngredient = 0;
        }
        setChanged();
        return true;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (camo != null) {
            tag.put(CAMO_KEY, BlockState.CODEC.encodeStart(NbtOps.INSTANCE, camo).getOrThrow());
        }
        tag.putInt(POWER_KEY, power);
        tag.putInt(CURRENT_KEY, currentIngredient);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(CAMO_KEY)) {
            camo = BlockState.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(CAMO_KEY)).map(Pair::getFirst).getOrThrow();
            requestModelDataUpdate();
        }
        if (tag.contains(POWER_KEY)) {
            power = tag.getInt(POWER_KEY);
        }
        if (tag.contains(CURRENT_KEY)) {
            currentIngredient = tag.getInt(CURRENT_KEY);
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public ModelData getModelData() {
        return camo == null ? ModelData.EMPTY : ModelData.builder().with(CAMO, camo).build();
    }

    @Nullable
    public BlockPos getLecternPos() {
        return lecternPos;
    }

    @Nullable
    public SpellIngredient getCurrentIngredient() {
        return hasRecipe() ? recipe.get(currentIngredient) : null;
    }

    public boolean hasRecipe() {
        return recipe != null;
    }

    @Nullable
    private Spell getSpell() {
        if (lecternPos == null || !level.getBlockState(lecternPos).getValue(LecternBlock.HAS_BOOK) || !(level.getBlockEntity(lecternPos) instanceof LecternBlockEntity lectern))
            return null;
        ItemStack stack = lectern.getBook();
        return stack.has(AMDataComponents.SPELL) ? stack.get(AMDataComponents.SPELL) : null;
    }
}
