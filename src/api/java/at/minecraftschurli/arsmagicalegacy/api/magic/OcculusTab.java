package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryFileCodec;
import net.minecraft.resources.ResourceLocation;

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

    public static ResourceLocation getBackground(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/background/" + id.getPath() + ".png");
    }

    public static ResourceLocation getIcon(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/icon/" + id.getPath() + ".png");
    }

    public static Component getName(ResourceLocation id) {
        return Component.translatable(Util.makeDescriptionId("occulus_tab", id));
    }
}
