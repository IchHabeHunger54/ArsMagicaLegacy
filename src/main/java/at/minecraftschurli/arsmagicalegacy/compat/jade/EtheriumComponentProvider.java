package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMCapabilities;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumHandler;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@SuppressWarnings("DataFlowIssue")
class EtheriumComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final ResourceLocation ID = ArsMagicaApi.modLoc("etherium");
    private static final String ETHERIUM_TYPES = "etherium_types";
    private static final String ETHERIUM = "etherium";
    private static final String MAX_ETHERIUM = "max_etherium";
    static final EtheriumComponentProvider INSTANCE = new EtheriumComponentProvider();

    private EtheriumComponentProvider() {
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag compoundTag = blockAccessor.getServerData();
        if (!compoundTag.contains(ETHERIUM_TYPES)) return;
        CompoundTag etheriumTag = compoundTag.getCompound(ETHERIUM_TYPES);
        blockAccessor.getLevel()
            .registryAccess()
            .registryOrThrow(AMRegistryKeys.ETHERIUM_TYPE)
            .holders()
            .filter(holder -> etheriumTag.contains(holder.getKey().location().toString()))
            .forEach(holder -> {
                CompoundTag tag = etheriumTag.getCompound(holder.getKey().location().toString());
                if (!tag.contains(ETHERIUM) || !tag.contains(MAX_ETHERIUM)) return;
                iTooltip.add(Component.translatable(AMTranslations.ETHERIUM_KEY, EtheriumType.getName(holder), tag.getInt(ETHERIUM), tag.getInt(MAX_ETHERIUM)));
            });
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
    }

    @Override
    public ResourceLocation getUid() {
        return ID;
    }
}
