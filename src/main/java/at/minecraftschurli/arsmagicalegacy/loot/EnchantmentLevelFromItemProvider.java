package at.minecraftschurli.arsmagicalegacy.loot;

import at.minecraftschurli.arsmagicalegacy.init.AMLoot;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParam;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.providers.number.LootNumberProviderType;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.Set;

public record EnchantmentLevelFromItemProvider(Holder<Enchantment> enchantment, LevelBasedValue value) implements NumberProvider {
    public static final MapCodec<EnchantmentLevelFromItemProvider> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
        Enchantment.CODEC.fieldOf("enchantment").forGetter(e -> e.enchantment),
        LevelBasedValue.CODEC.fieldOf("value").forGetter(e -> e.value)
    ).apply(inst, EnchantmentLevelFromItemProvider::new));

    @Override
    public float getFloat(LootContext lootContext) {
        if (!lootContext.hasParam(LootContextParams.DAMAGE_SOURCE)) return 0;
        ItemStack weapon = lootContext.getParam(LootContextParams.DAMAGE_SOURCE).getWeaponItem();
        return weapon != null ? value.calculate(weapon.getEnchantmentLevel(enchantment)) : 0;
    }

    @Override
    public LootNumberProviderType getType() {
        return AMLoot.ENCHANTMENT_LEVEL_FROM_ITEM.get();
    }

    @Override
    public Set<LootContextParam<?>> getReferencedContextParams() {
        return Set.of(LootContextParams.DAMAGE_SOURCE);
    }
}
