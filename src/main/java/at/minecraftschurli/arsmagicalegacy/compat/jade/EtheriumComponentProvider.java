package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.IElementHelper;

import java.util.Arrays;
import java.util.List;
import java.util.SequencedSet;

@SuppressWarnings("DataFlowIssue")
class EtheriumComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final ResourceLocation ID = ArsMagicaApi.id("etherium");
    private static final String ETHERIUM_TYPES = "etherium_types";
    private static final String ETHERIUM = "etherium";
    private static final String MAX_ETHERIUM = "max_etherium";
    private static final String POSITIONS = "positions";
    static final EtheriumComponentProvider INSTANCE = new EtheriumComponentProvider();

    private EtheriumComponentProvider() {
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag compoundTag = blockAccessor.getServerData();
        if (!compoundTag.contains(ETHERIUM_TYPES)) return;
        CompoundTag etheriumTag = compoundTag.getCompound(ETHERIUM_TYPES);
        AMRegistries.etheriumTypes(blockAccessor instanceof Level l ? l.registryAccess() : AMRegistries.registryAccess(true))
            .holders()
            .filter(holder -> etheriumTag.contains(holder.getKey().location().toString()))
            .forEach(holder -> {
                CompoundTag tag = etheriumTag.getCompound(holder.getKey().location().toString());
                if (!tag.contains(ETHERIUM) || !tag.contains(MAX_ETHERIUM)) return;
                iTooltip.add(Component.translatable(AMTranslations.ETHERIUM_KEY, EtheriumType.getName(holder), tag.getInt(ETHERIUM), tag.getInt(MAX_ETHERIUM)));
            });
        if (compoundTag.contains(POSITIONS)) {
            List<BlockPos> list = Arrays.stream(compoundTag.getLongArray(POSITIONS))
                .mapToObj(BlockPos::of)
                .toList();
            Level level = blockAccessor.getLevel();
            list.forEach(pos -> iTooltip.add(IElementHelper.get().item(level.getBlockState(pos).getCloneItemStack(blockAccessor.getHitResult(), level, pos, blockAccessor.getPlayer()))));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        Level level = blockAccessor.getLevel();
        EtheriumHandler capability = level.getCapability(AMCapabilities.BLOCK_ETHERIUM, blockAccessor.getPosition(), null);
        if (capability == null) return;
        CompoundTag etheriumTag = new CompoundTag();
        capability.getEtheriumTypes().forEach(etheriumType -> {
            int maxEtherium = capability.getMaxAmount(etheriumType);
            if (maxEtherium > 0) {
                CompoundTag tag = new CompoundTag();
                tag.putInt(ETHERIUM, capability.getAmount(etheriumType));
                tag.putInt(MAX_ETHERIUM, maxEtherium);
                etheriumTag.put(etheriumType.getKey().location().toString(), tag);
            }
        });
        compoundTag.put(ETHERIUM_TYPES, etheriumTag);
        if (capability.canHaveConnectedPositions()) {
            SequencedSet<BlockPos> positions = capability.getConnectedPositions();
            if (!positions.isEmpty()) {
                compoundTag.putLongArray(POSITIONS, positions
                    .stream()
                    .map(BlockPos::asLong)
                    .toList());
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
