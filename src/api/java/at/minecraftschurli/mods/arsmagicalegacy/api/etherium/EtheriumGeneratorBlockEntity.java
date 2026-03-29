package at.minecraftschurli.mods.arsmagicalegacy.api.etherium;

import at.minecraftschurli.mods.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.mods.arsmagicalegacy.api.constants.AMRegistries;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.SequencedSet;

/**
 * Simple implementation of an etherium generator, as used by the three generators in the base mod (Obelisk, Celestial Prism and Black Aurem).
 * If this class (or any other implementation of a block-bound etherium handler) is used, an associated {@link BlockEntityRenderer}
 * that calls {@link ArsMagicaClientApi#renderGogglesOutline(BlockEntity, PoseStack, MultiBufferSource)} should be registered for the Magitech Goggles item to pick it up.
 */
public abstract class EtheriumGeneratorBlockEntity extends BlockEntity implements EtheriumHandler {
    private static final String ETHERIUM_KEY = "etherium";
    private static final SequencedSet<BlockPos> POSITIONS = Collections.unmodifiableSequencedSet(new LinkedHashSet<>());
    protected final ResourceKey<EtheriumType> etheriumType;
    protected int etherium = 0;

    /**
     * @param type         The registered {@link BlockEntityType}.
     * @param pos          The {@link BlockPos}, as supplied by {@link net.minecraft.world.level.block.EntityBlock#newBlockEntity(BlockPos, BlockState)}.
     * @param state        The {@link BlockState}, as supplied by {@link net.minecraft.world.level.block.EntityBlock#newBlockEntity(BlockPos, BlockState)}.
     * @param etheriumType The {@link EtheriumType} of the generator.
     */
    public EtheriumGeneratorBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, ResourceKey<EtheriumType> etheriumType) {
        super(type, pos, state);
        this.etheriumType = etheriumType;
    }

    /**
     * Ticks the block entity.
     *
     * @param level The {@link Level}.
     * @param pos   The {@link BlockPos}.
     * @param state The {@link BlockState}.
     */
    public abstract void tick(Level level, BlockPos pos, BlockState state);

    /**
     * @return The max etherium amount that can be stored. Usually resolved from a config value or similar.
     */
    public abstract int getMaxAmount();

    /**
     * Returns the tier of the generator at the given position.
     *
     * @param level The {@link Level} the generator is in.
     * @param pos   The {@link BlockPos} at which the check should place.
     * @return The tier of the generator.
     */
    public abstract int getTier(Level level, BlockPos pos);

    @Override
    protected void loadAdditional(ValueInput input) {
        super.loadAdditional(input);
        etherium = input.getInt(ETHERIUM_KEY).orElse(0);
    }

    @Override
    protected void saveAdditional(ValueOutput output) {
        super.saveAdditional(output);
        output.putInt(ETHERIUM_KEY, etherium);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public List<Holder<EtheriumType>> getEtheriumTypes() {
        return List.of(AMRegistries.etheriumTypes(level.registryAccess()).getOrThrow(etheriumType));
    }

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return type.is(etheriumType) ? etherium : 0;
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return type.is(etheriumType) ? getMaxAmount() : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        if (type.is(etheriumType)) {
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
        if (!type.is(etheriumType)) return amount;
        int min = Math.min(etherium, amount);
        etherium -= min;
        setChanged();
        return amount - min;
    }

    @Override
    public boolean canHaveConnectedPositions() {
        return false;
    }

    @Override
    public SequencedSet<BlockPos> getConnectedPositions() {
        return POSITIONS;
    }

    @Override
    public void addConnectedPosition(BlockPos pos) {
    }

    @Override
    public void removeConnectedPosition(BlockPos pos) {
    }
}
