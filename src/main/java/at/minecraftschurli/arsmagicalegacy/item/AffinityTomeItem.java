package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.AMServerConfig;
import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMTranslations;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.MagicHelper;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.Map;
import java.util.stream.Collectors;

@SuppressWarnings("DataFlowIssue")
public class AffinityTomeItem extends DataComponentNamedItem<Holder<Affinity>> {
    public AffinityTomeItem(Properties properties) {
        super(properties, AMDataComponents.AFFINITY.get());
        withTranslationKeyGetter((holder, name) -> Util.makeDescriptionId(name, holder.getKey().location()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!stack.has(AMDataComponents.AFFINITY)) return super.use(level, player, usedHand);
        MagicHelper helper = ArsMagicaApi.magicHelper();
        if (!helper.knowsMagic(player)) {
            player.displayClientMessage(AMTranslations.PREVENT_ITEM, true);
            return InteractionResultHolder.fail(stack);
        }
        Holder<Affinity> affinity = stack.get(AMDataComponents.AFFINITY);
        double shift = AMServerConfig.AFFINITY_TOME_SHIFT.get();
        double reduction = -AMServerConfig.AFFINITY_TOME_REDUCTION.get();
        helper.addAffinityDepth(player, AMRegistries.affinities(level.registryAccess())
            .holders()
            .filter(e -> e.getKey() != Affinity.NONE)
            .map(e -> Map.entry(e, e.getKey() == affinity.getKey() ? shift : reduction))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)), true, false);
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        return InteractionResultHolder.success(stack);
    }
}
