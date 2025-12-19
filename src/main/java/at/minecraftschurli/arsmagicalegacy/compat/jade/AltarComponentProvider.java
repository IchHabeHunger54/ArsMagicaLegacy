package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

class AltarComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final ResourceLocation ID = ArsMagicaApi.modLoc("altar");
    private static final String POWER = "power";
    static final AltarComponentProvider INSTANCE = new AltarComponentProvider();

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if (tag.contains(POWER)) {
            iTooltip.add(Component.translatable(AMTranslations.ALTAR_CORE_POWER_KEY, tag.getInt(POWER)));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof AltarCoreBlockEntity altar) {
            compoundTag.putInt(POWER, altar.getPower());
        }
    }
}
