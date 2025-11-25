package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumConsumerBlockEntity;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarCapMaterial;
import at.minecraftschurli.arsmagicalegacy.api.magic.AltarMaterial;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellHelper;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellIngredient;
import at.minecraftschurli.arsmagicalegacy.block.AltarCoreBlock;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import at.minecraftschurli.arsmagicalegacy.item.SpellItem;
import at.minecraftschurli.arsmagicalegacy.spell.EtheriumSpellIngredient;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.LecternBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import net.neoforged.neoforge.client.model.data.ModelProperty;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.SequencedSet;

@SuppressWarnings("DataFlowIssue")
public class AltarCoreBlockEntity extends AMBlockEntity<AltarCoreBlockEntity.Data> implements EtheriumConsumerBlockEntity, EtheriumHandler {
    public static final ModelProperty<BlockState> CAMO = new ModelProperty<>();
    private final SequencedSet<BlockPos> etheriumProviders = new LinkedHashSet<>();
    private final Map<ResourceKey<EtheriumType>, Integer> etherium = new HashMap<>();
    private int checkCounter = 0;
    private Direction direction;
    private BlockPos lecternPos;
    private BlockPos leverPos;
    private AltarMaterial material;
    private AltarCapMaterial capMaterial;
    private BlockState camo;
    private int power = 0;
    private int currentIngredient = 0;
    private Spell spell = Spell.EMPTY;
    private List<SpellIngredient> recipe = List.of();

    public AltarCoreBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.ALTAR_CORE.get(), pos, state, Data.CODEC);
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
        if (!state.getValue(AltarCoreBlock.FORMED) || spell == null || recipe == null) return;
        if (currentIngredient >= recipe.size()) {
            currentIngredient = 0;
        }
        SpellIngredient ingredient = getCurrentIngredient();
        if (ingredient == null) return;
        if (ingredient instanceof EtheriumSpellIngredient etheriumIngredient) {
            etheriumProviders.stream()
                .map(p -> level.getCapability(AMCapabilities.BLOCK_ETHERIUM, p, null))
                .filter(Objects::nonNull)
                .filter(e -> etheriumIngredient.etheriumType().isEmpty() || e.getEtheriumTypes().contains(etheriumIngredient.etheriumType().get()))
                .forEach(e -> {
                    for (Holder<EtheriumType> type : getEtheriumTypes()) {
                        etherium.put(type.getKey(), etherium.getOrDefault(type.getKey(), 0) + power - e.subtractAmount(type, power));
                    }
                });
            setChanged();
        }
        if (!ingredient.consume(level, pos)) return;
        currentIngredient++;
        if (currentIngredient < recipe.size()) return;
        currentIngredient = 0;
        if (level.isClientSide()) return;
        ItemEntity entity = new ItemEntity(level, pos.getX() + 0.5, pos.getY() - 1.5, pos.getZ() + 0.5, SpellItem.set(AMItems.SPELL.toStack(), AMDataComponents.SPELL.get(), spell), 0, 0.2, 0);
        entity.setPickUpDelay(40);
        entity.setExtendedLifetime();
        level.addFreshEntity(entity);
        level.playSound(null, pos.getX(), pos.getY() - 2, pos.getZ(), AMSounds.SPELLCRAFTING_FINISH.get(), SoundSource.BLOCKS, 1, 1);
    }

    private boolean checkMultiblock() {
        Registry<AltarCapMaterial> capMaterialRegistry = AMRegistries.altarCapMaterials(level.registryAccess());
        Registry<AltarMaterial> materialRegistry = AMRegistries.altarMaterials(level.registryAccess());
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
                break;
            }
        }
        if (lecternPos == null || leverPos == null || material == null || capMaterial == null || direction == null) return false;
        if (!level.getBlockState(lecternPos).is(Blocks.LECTERN) || !(level.getBlockEntity(lecternPos) instanceof LecternBlockEntity lectern)) return false;
        if (!level.getBlockState(leverPos).is(Blocks.LEVER)) return false;
        if (AMMultiblocks.ALTAR.validate(level, getBlockPos().below(4)) == null) return false;
        camo = material.block().defaultBlockState();
        power = material.power() + capMaterial.power();
        if (!level.isClientSide()) {
            ItemStack stack = lectern.getBook();
            spell = stack.has(AMDataComponents.SPELL) ? stack.get(AMDataComponents.SPELL) : null;
        }
        SpellHelper helper = ArsMagicaApi.spellHelper();
        recipe = spell != null && helper.getFlatRecipe(spell).size() <= power ? helper.getRecipe(spell) : null;
        if (recipe == null) {
            currentIngredient = 0;
        }
        setChanged();
        return true;
    }

    @Override
    public void fromData(Data data) {
        camo = data.camo.orElse(null);
        power = data.power;
        currentIngredient = data.current;
        spell = data.spell;
        etheriumProviders.clear();
        etheriumProviders.addAll(data.providers);
        etherium.clear();
        etherium.putAll(data.etherium);
    }

    @Override
    public Data toData() {
        return new Data(Optional.ofNullable(camo), power, currentIngredient, spell == null ? Spell.EMPTY : spell, List.copyOf(etheriumProviders), etherium);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (level != null) {
            checkMultiblock();
        }
        requestModelDataUpdate();
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

    @Override
    public SequencedSet<BlockPos> getBoundPositions() {
        return etheriumProviders;
    }

    @Override
    public void addPosition(BlockPos pos) {
        etheriumProviders.add(pos);
        setChanged();
    }

    @Override
    public void removePosition(BlockPos pos) {
        etheriumProviders.remove(pos);
        setChanged();
    }

    @Override
    public List<Holder<EtheriumType>> getEtheriumTypes() {
        return AMRegistries.etheriumTypes(level.registryAccess())
            .holders()
            .map(e -> (Holder<EtheriumType>) e)
            .toList();
    }

    @Override
    public int getAmount(Holder<EtheriumType> type) {
        return Math.min(etherium.getOrDefault(type.getKey(), 0), getMaxAmount(type));
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return getCurrentIngredient() instanceof EtheriumSpellIngredient(Optional<Holder<EtheriumType>> etheriumType, int count) && (etheriumType.isEmpty() || etheriumType.get().is(type.getKey())) ? count : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        etherium.put(type.getKey(), amount);
        setChanged();
    }

    @Override
    public int addAmount(Holder<EtheriumType> type, int amount) {
        int min = Math.min(etherium.getOrDefault(type.getKey(), getMaxAmount(type)), amount);
        etherium.put(type.getKey(), etherium.get(type.getKey()) - min);
        setChanged();
        return amount - min;
    }

    @Override
    public int subtractAmount(Holder<EtheriumType> type, int amount) {
        return amount;
    }

    @Nullable
    public BlockPos getLecternPos() {
        return lecternPos;
    }

    @Nullable
    public SpellIngredient getCurrentIngredient() {
        return hasRecipe() ? recipe.get(currentIngredient) : null;
    }

    public int getPower() {
        return power;
    }

    public boolean hasRecipe() {
        return recipe != null && !recipe.isEmpty();
    }

    public boolean consumeEtherium(EtheriumSpellIngredient ingredient) {
        if (ingredient.etheriumType().isPresent()) {
            int count = etherium.getOrDefault(ingredient.etheriumType().get().getKey(), 0);
            if (count < ingredient.count()) return false;
            etherium.put(ingredient.etheriumType().get().getKey(), count - ingredient.count());
        } else {
            int count = etherium.values().stream().mapToInt(Integer::intValue).sum();
            if (count < ingredient.count()) return false;
            while (count > 0) {
                int decrease = Math.min(etherium.values().stream().mapToInt(Integer::intValue).min().orElse(0), count / etherium.size());
                for (ResourceKey<EtheriumType> holder : etherium.keySet()) {
                    etherium.compute(holder, (k, v) -> v - decrease);
                    if (etherium.get(holder) <= 0) {
                        etherium.remove(holder);
                    }
                }
                count -= decrease * etherium.size();
            }
        }
        setChanged();
        return true;
    }

    public record Data(Optional<BlockState> camo, int power, int current, Spell spell, List<BlockPos> providers, Map<ResourceKey<EtheriumType>, Integer> etherium) {
        private static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            BlockState.CODEC.optionalFieldOf("camo").forGetter(Data::camo),
            Codec.INT.fieldOf("power").forGetter(Data::power),
            Codec.INT.fieldOf("current").forGetter(Data::current),
            Spell.CODEC.optionalFieldOf("spell", Spell.EMPTY).forGetter(Data::spell),
            BlockPos.CODEC.listOf().optionalFieldOf("providers", List.of()).forGetter(Data::providers),
            Codec.unboundedMap(ResourceKey.codec(AMRegistries.ETHERIUM_TYPE), Codec.INT).optionalFieldOf("etherium", Map.of()).forGetter(Data::etherium)
        ).apply(inst, Data::new));
    }
}
