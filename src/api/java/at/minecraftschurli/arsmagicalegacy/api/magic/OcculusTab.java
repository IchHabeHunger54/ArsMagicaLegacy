package at.minecraftschurli.arsmagicalegacy.api.magic;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

public record OcculusTab(int width, int height, int startX, int startY, int index, ResourceLocation renderer) {
    public static final Codec<OcculusTab> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.INT.fieldOf("width").forGetter(OcculusTab::width),
        Codec.INT.fieldOf("height").forGetter(OcculusTab::height),
        Codec.INT.fieldOf("start_x").forGetter(OcculusTab::startX),
        Codec.INT.fieldOf("start_y").forGetter(OcculusTab::startY),
        Codec.INT.fieldOf("index").forGetter(OcculusTab::index),
        ResourceLocation.CODEC.fieldOf("renderer").forGetter(OcculusTab::renderer)
    ).apply(inst, OcculusTab::new));

    public static ResourceLocation background(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/background/" + id.getPath() + ".png");
    }

    public static ResourceLocation icon(ResourceLocation id) {
        return ResourceLocation.fromNamespaceAndPath(id.getNamespace(), "textures/gui/occulus/icon/" + id.getPath() + ".png");
    }
}
