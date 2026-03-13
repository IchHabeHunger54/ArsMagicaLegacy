package at.minecraftschurli.arsmagicalegacy.item;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMSounds;
import net.minecraft.util.Util;
import net.minecraft.core.Holder;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class InfinityOrbItem extends DataComponentNamedItem<Holder<SkillPoint>> {
    @SuppressWarnings("DataFlowIssue")
    public InfinityOrbItem(Properties properties) {
        super(properties, AMDataComponents.SKILL_POINT.get());
        withTranslationKeyGetter((holder, name) -> Util.makeDescriptionId(name, holder.getKey().identifier()));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        if (!stack.has(AMDataComponents.SKILL_POINT)) return super.use(level, player, usedHand);
        ArsMagicaApi.magicHelper().addSkillPoint(player, stack.get(AMDataComponents.SKILL_POINT));
        if (!player.isCreative()) {
            stack.shrink(1);
        }
        level.playSound(null, player, AMSounds.INFINITY_ORB.get(), SoundSource.PLAYERS, 1, 1);
        return InteractionResultHolder.success(stack);
    }
}
