package at.minecraftschurli.arsmagicalegacy.block.altar;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;

public class AltarCoreBlockEntity extends BlockEntity {
    public static final ModelProperty<BlockState> CAMO = new ModelProperty<>();
    private static final String CAMO_KEY = ArsMagicaApi.modLoc("camo").toString();
    private int checkCounter = 0;
    private Direction direction;
    private BlockPos lecternPos;
    private BlockPos leverPos;
    private AltarMaterial material;
    private AltarCapMaterial capMaterial;
    private BlockState camo;
    private int powerLevel;

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
                powerLevel = 0;
                setChanged();
            }
            if (getBlockState().getValue(AltarCoreBlock.FORMED) != multiblock) {
                level.setBlockAndUpdate(pos, state.setValue(AltarCoreBlock.FORMED, multiblock));
            }
        }
        if (state.getValue(AltarCoreBlock.FORMED)) {
            consumeTick();
        }
    }

    @SuppressWarnings("DataFlowIssue")
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
        //TODO set recipe
        powerLevel = material.power() + capMaterial.power();
        setChanged();
        return true;
    }

    private void consumeTick() {
        //TODO
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (camo != null) {
            tag.put(CAMO_KEY, BlockState.CODEC.encodeStart(NbtOps.INSTANCE, camo).getOrThrow());
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(CAMO_KEY)) {
            camo = BlockState.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(CAMO_KEY)).map(Pair::getFirst).getOrThrow();
            requestModelDataUpdate();
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
}
