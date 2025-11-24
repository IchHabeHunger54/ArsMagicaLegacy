package at.minecraftschurli.arsmagicalegacy.compat.jade;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.blockentity.AltarCoreBlockEntity;
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

class AltarComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private static final ResourceLocation ID = ArsMagicaApi.modLoc("altar");
    private static final String POWER = "power";
    private static final String POSITIONS = "positions";
    static final AltarComponentProvider INSTANCE = new AltarComponentProvider();

    @Override
    public ResourceLocation getUid() {
        return ID;
    }

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        CompoundTag tag = blockAccessor.getServerData();
        if (tag.contains(POSITIONS)) {
            List<BlockPos> list = Arrays.stream(tag.getLongArray(POSITIONS))
                .mapToObj(BlockPos::of)
                .toList();
            Level level = blockAccessor.getLevel();
            list.forEach(pos -> iTooltip.add(IElementHelper.get().item(level.getBlockState(pos).getCloneItemStack(blockAccessor.getHitResult(), level, pos, blockAccessor.getPlayer()))));
        }
        if (tag.contains(POWER)) {
            iTooltip.add(Component.translatable(AMTranslations.ALTAR_CORE_POWER_KEY, tag.getInt(POWER)));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if (blockAccessor.getBlockEntity() instanceof AltarCoreBlockEntity altar) {
            compoundTag.putLongArray(POSITIONS, altar.getBoundPositions()
                .stream()
                .map(BlockPos::asLong)
                .toList());
            compoundTag.putInt(POWER, altar.getPower());
        }
    }
}
