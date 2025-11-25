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
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
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
public class AltarCoreBlockEntity extends BlockEntity implements EtheriumConsumerBlockEntity, EtheriumHandler {
    public static final ModelProperty<BlockState> CAMO = new ModelProperty<>();
    private static final String CAMO_KEY = ArsMagicaApi.modLoc("camo").toString();
    private static final String POWER_KEY = ArsMagicaApi.modLoc("power").toString();
    private static final String CURRENT_KEY = ArsMagicaApi.modLoc("current").toString();
    private static final String SPELL_KEY = ArsMagicaApi.modLoc("spell").toString();
    private static final String PROVIDERS_KEY = ArsMagicaApi.modLoc("providers").toString();
    private static final String ETHERIUM_KEY = ArsMagicaApi.modLoc("etherium").toString();
    private static final String TYPE_KEY = "type";
    private static final String AMOUNT_KEY = "amount";
    private final SequencedSet<BlockPos> etheriumProviders = new LinkedHashSet<>();
    private final Map<Holder<EtheriumType>, Integer> etherium = new HashMap<>();
    private int checkCounter = 0;
    private Direction direction;
    private BlockPos lecternPos;
    private BlockPos leverPos;
    private AltarMaterial material;
    private AltarCapMaterial capMaterial;
    private BlockState camo;
    private int power;
    private int currentIngredient;
    private Spell spell;
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
                        etherium.put(type, etherium.getOrDefault(type, 0) + power - e.subtractAmount(type, power));
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
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        if (camo != null) {
            tag.put(CAMO_KEY, BlockState.CODEC.encodeStart(NbtOps.INSTANCE, camo).getOrThrow());
        }
        if (spell != null) {
            tag.put(SPELL_KEY, Spell.CODEC.encodeStart(NbtOps.INSTANCE, spell).getOrThrow());
        }
        tag.putInt(POWER_KEY, power);
        tag.putInt(CURRENT_KEY, currentIngredient);
        if (!etheriumProviders.isEmpty()) {
            ListTag list = new ListTag();
            for (BlockPos pos : etheriumProviders) {
                list.add(BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, pos).getOrThrow());
            }
            tag.put(PROVIDERS_KEY, list);
        }
        if (!etherium.isEmpty()) {
            CompoundTag etheriumTag = new CompoundTag();
            for (Map.Entry<Holder<EtheriumType>, Integer> value : etherium.entrySet()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putString(TYPE_KEY, value.getKey().getKey().location().toString());
                compoundTag.putInt(AMOUNT_KEY, value.getValue());
                etheriumTag.put(TYPE_KEY, compoundTag);
            }
            tag.put(ETHERIUM_KEY, etheriumTag);
        }
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        if (tag.contains(CAMO_KEY)) {
            camo = BlockState.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(CAMO_KEY)).map(Pair::getFirst).getOrThrow();
        }
        if (tag.contains(POWER_KEY)) {
            power = tag.getInt(POWER_KEY);
        }
        if (tag.contains(SPELL_KEY)) {
            spell = Spell.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).map(Pair::getFirst).getOrThrow();
            SpellHelper helper = ArsMagicaApi.spellHelper();
            recipe = spell != null && helper.getFlatRecipe(spell).size() <= power ? helper.getRecipe(spell) : null;
        }
        if (tag.contains(CURRENT_KEY)) {
            currentIngredient = tag.getInt(CURRENT_KEY);
        }
        if (tag.contains(PROVIDERS_KEY)) {
            etheriumProviders.clear();
            for (Tag t : tag.getList(PROVIDERS_KEY, CompoundTag.TAG_COMPOUND)) {
                etheriumProviders.add(BlockPos.CODEC.decode(NbtOps.INSTANCE, t).map(Pair::getFirst).getOrThrow());
            }
        }
        if (tag.contains(ETHERIUM_KEY)) {
            etherium.clear();
            for (Tag t : tag.getList(ETHERIUM_KEY, CompoundTag.TAG_COMPOUND)) {
                CompoundTag compound = (CompoundTag) t;
                if (compound.contains(TYPE_KEY) && compound.contains(AMOUNT_KEY)) {
                    etherium.put(AMRegistries.etheriumTypes(level.registryAccess()).getHolder(ResourceLocation.parse(compound.getString(TYPE_KEY))).get(), compound.getInt(AMOUNT_KEY));
                }
            }
        }
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
        return Math.min(etherium.getOrDefault(type, 0), getMaxAmount(type));
    }

    @Override
    public int getMaxAmount(Holder<EtheriumType> type) {
        return getCurrentIngredient() instanceof EtheriumSpellIngredient(Optional<Holder<EtheriumType>> etheriumType, int count) && (etheriumType.isEmpty() || etheriumType.get().is(type.getKey())) ? count : 0;
    }

    @Override
    public void setAmount(Holder<EtheriumType> type, int amount) {
        etherium.put(type, amount);
        setChanged();
    }

    @Override
    public int addAmount(Holder<EtheriumType> type, int amount) {
        int min = Math.min(etherium.getOrDefault(type, getMaxAmount(type)), amount);
        etherium.put(type, etherium.get(type) - min);
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
        return recipe != null;
    }

    public boolean consumeEtherium(EtheriumSpellIngredient ingredient) {
        if (ingredient.etheriumType().isPresent()) {
            int count = etherium.getOrDefault(ingredient.etheriumType().get(), 0);
            if (count < ingredient.count()) return false;
            etherium.put(ingredient.etheriumType().get(), count - ingredient.count());
        } else {
            int count = etherium.values().stream().mapToInt(Integer::intValue).sum();
            if (count < ingredient.count()) return false;
            while (count > 0) {
                int decrease = Math.min(etherium.values().stream().mapToInt(Integer::intValue).min().orElse(0), count / etherium.size());
                for (Holder<EtheriumType> holder : etherium.keySet()) {
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
}
