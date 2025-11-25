package at.minecraftschurli.arsmagicalegacy.blockentity;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellDataComponentMap;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellGrammar;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellShapeGroup;
import at.minecraftschurli.arsmagicalegacy.init.AMBlockEntities;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.menu.InscriptionTableMenu;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
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

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class InscriptionTableBlockEntity extends BlockEntity implements Container, MenuProvider {
    private static final String INVENTORY_KEY = ArsMagicaApi.modLoc("inventory").toString();
    private static final String SPELL_KEY = ArsMagicaApi.modLoc("spell").toString();
    private ItemStack stack = ItemStack.EMPTY;
    private Data data = Data.EMPTY;
    private boolean open;

    public InscriptionTableBlockEntity(BlockPos pos, BlockState state) {
        super(AMBlockEntities.INSCRIPTION_TABLE.get(), pos, state);
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
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
        data = Data.CODEC.decode(NbtOps.INSTANCE, tag.getCompound(SPELL_KEY)).getOrThrow().getFirst();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put(INVENTORY_KEY, stack.saveOptional(registries));
        tag.put(SPELL_KEY, Data.CODEC.encodeStart(NbtOps.INSTANCE, data).getOrThrow());
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

    public record Data(Optional<Component> name, List<Holder<Skill>> grammar, List<List<Holder<Skill>>> shapeGroups) {
        public static final Codec<Data> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ComponentSerialization.CODEC.optionalFieldOf("name").forGetter(Data::name),
            Skill.CODEC.listOf(0, SpellGrammar.MAX_PARTS).fieldOf("grammar").forGetter(Data::grammar),
            Skill.CODEC.listOf(0, SpellShapeGroup.MAX_PARTS).listOf(0, Spell.MAX_SHAPE_GROUPS).fieldOf("shape_groups").forGetter(Data::shapeGroups)
        ).apply(inst, Data::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(
            ComponentSerialization.STREAM_CODEC.apply(ByteBufCodecs::optional), Data::name,
            ByteBufCodecs.holderRegistry(AMRegistries.SKILL).apply(ByteBufCodecs.list()), Data::grammar,
            ByteBufCodecs.holderRegistry(AMRegistries.SKILL).apply(ByteBufCodecs.list()).apply(ByteBufCodecs.list()), Data::shapeGroups,
            Data::new);
        public static final Data EMPTY = new Data(Optional.empty(), List.of(), List.of());

        public static Data fromSpell(Spell spell, RegistryAccess registryAccess) {
            List<List<Holder<Skill>>> groups = spell.shapeGroups()
                .stream()
                .map(e -> skills(e.parts(), registryAccess))
                .toList();
            return new Data(spell.name(), skills(spell.grammar().parts(), registryAccess), groups);
        }

        public Spell toSpell() {
            List<SpellShapeGroup> groups = shapeGroups.stream()
                .map(Data::spellParts)
                .map(SpellShapeGroup::of)
                .toList();
            return new Spell(name, Optional.empty(), groups, 0, SpellGrammar.of(spellParts(grammar)), SpellDataComponentMap.EMPTY);
        }

        private static List<SpellPart> spellParts(List<Holder<Skill>> skills) {
            return skills.stream()
                .map(Holder::getKey)
                .filter(Objects::nonNull)
                .map(ResourceKey::location)
                .map(ArsMagicaApi.spellPartRegistry()::get)
                .toList();
        }

        private static List<Holder<Skill>> skills(List<SpellPart> parts, RegistryAccess registryAccess) {
            return parts.stream()
                .map(ArsMagicaApi.spellPartRegistry()::getKey)
                .filter(Objects::nonNull)
                .map(AMRegistries.skills(registryAccess)::getHolder)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(e -> (Holder<Skill>) e)
                .toList();
        }
    }
}
