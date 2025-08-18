package at.minecraftschurli.arsmagicalegacy.init;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.Util;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.neoforge.registries.DeferredHolder;

import java.util.function.Function;

public interface AMAttributes {
    DeferredHolder<Attribute, Attribute> MANA_REGENERATION = register("mana_regeneration", key -> new RangedAttribute(key, 0.1, 0, Short.MAX_VALUE), Attribute.Sentiment.POSITIVE);
    DeferredHolder<Attribute, Attribute> MAX_MANA =          register("max_mana",          key -> new RangedAttribute(key, 0, 0, Short.MAX_VALUE), Attribute.Sentiment.POSITIVE);

    private static DeferredHolder<Attribute, Attribute> register(String name, Function<String, Attribute> factory, Attribute.Sentiment sentiment) {
        return AMRegistries.ATTRIBUTES.register(name, () -> factory.apply(Util.makeDescriptionId("attribute", ArsMagicaApi.modLoc(name))).setSentiment(sentiment).setSyncable(true));
    }

    /**
     * Empty method used for classloading this class.
     */
    static void init() {
    }
}
