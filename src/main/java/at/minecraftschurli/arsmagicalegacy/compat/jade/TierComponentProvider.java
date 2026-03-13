package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.etherium.EtheriumGeneratorBlockEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

class TierComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final Identifier ID = ArsMagicaApi.id("tier");
    private static final String TIER = "power";
    static final TierComponentProvider INSTANCE = new TierComponentProvider();

    @Override
    public Identifier getUid() {
        return ID;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if (tag.contains(TIER)) {
            iTooltip.add(Component.translatable(AMTranslations.TIER_KEY, tag.getInt(TIER)));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof EtheriumGeneratorBlockEntity blockEntity) {
            int tier = blockEntity.getTier(blockAccessor.getLevel(), blockAccessor.getPosition());
            if (tier > 0) {
                compoundTag.putInt(TIER, tier);
            }
        }
    }
}
