package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.client.ArsMagicaClientApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

/**
 * Represents an occulus tab.
 *
 * @param width    The width of the tab.
 * @param height   The height of the tab.
 * @param startX   The default X position of the tab.
 * @param startY   The default Y position of the tab.
 * @param index    The index of the tab in relation to other tabs.
 * @param renderer The id of the renderer type to use. Get an actual renderer only on the client using {@link ArsMagicaClientApi#occulusTabRendererFactory(Holder)}.
 */
public record OcculusTab(int width, int height, int startX, int startY, int index, ResourceLocation renderer) {
    public static final Codec<OcculusTab> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("width").forGetter(OcculusTab::width),
        Codec.INT.fieldOf("height").forGetter(OcculusTab::height),
        Codec.INT.fieldOf("start_x").forGetter(OcculusTab::startX),
        Codec.INT.fieldOf("start_y").forGetter(OcculusTab::startY),
        Codec.INT.fieldOf("index").forGetter(OcculusTab::index),
        ResourceLocation.CODEC.fieldOf("renderer").forGetter(OcculusTab::renderer)
    ).apply(inst, OcculusTab::new));
    public static final Codec<Holder<OcculusTab>> CODEC = RegistryFileCodec.create(AMRegistryKeys.OCCULUS_TAB, DIRECT_CODEC);

    /**
     * @param holder The occulus tab {@link Holder} to query.
     * @return The background {@link ResourceLocation} for the given occulus tab.
     */
    public static ResourceLocation getBackground(Holder<OcculusTab> holder) {
        ResourceLocation id = holder.getKey().location();
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/background/" + id.getPath() + ".png");
    }

    /**
     * @param holder The occulus tab {@link Holder} to query.
     * @return The icon {@link ResourceLocation} for the given occulus tab.
     */
    public static ResourceLocation getIcon(Holder<OcculusTab> holder) {
        ResourceLocation id = holder.getKey().location();
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/icon/" + id.getPath() + ".png");
    }

    /**
     * @param holder The occulus tab {@link Holder} to query.
     * @return The display name of the given occulus tab.
     */
    public static Component getName(Holder<OcculusTab> holder) {
        return Component.translatable(Util.makeDescriptionId("occulus_tab", holder.getKey().location()));
    }
}
