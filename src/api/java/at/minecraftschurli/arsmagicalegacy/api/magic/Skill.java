package at.minecraftschurli.arsmagicalegacy.api.magic;

import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryFileCodec;

import java.util.List;
import java.util.Optional;

/**
 * Represents a skill.
 *
 * @param parents A {@link List} of parent {@link Skill} {@link Holder}s.
 * @param cost    The cost of the skill. If absent, the skill has no cost.
 * @param tab     The {@link OcculusTab} the skill resides in.
 * @param x       The x position of the skill.
 * @param y       The y position of the skill.
 * @param hidden  Whether the skill is hidden. Hidden skills will only show when learned through means other than within the occulus, e.g. via command.
 */
@SuppressWarnings("DataFlowIssue")
public record Skill(List<Holder<Skill>> parents, Optional<Holder<SkillPoint>> cost, Holder<OcculusTab> tab, int x, int y, boolean hidden) {
    // This method is needed to circumvent the javac-imposed static init order and allow CODEC to be used inside DIRECT_CODEC
    // "the biggest obstacle here is javac" - Commoble, developer of More Red
    private static Codec<Holder<Skill>> getCodec() {
        return CODEC;
    }

    public static final Codec<Skill> DIRECT_CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Codec.lazyInitialized(Skill::getCodec).listOf().fieldOf("parents").forGetter(Skill::parents),
        SkillPoint.CODEC.optionalFieldOf("cost").forGetter(Skill::cost),
        OcculusTab.CODEC.fieldOf("tab").forGetter(Skill::tab),
        Codec.INT.fieldOf("x").forGetter(Skill::x),
        Codec.INT.fieldOf("y").forGetter(Skill::y),
        Codec.BOOL.optionalFieldOf("hidden", false).forGetter(Skill::hidden)
    ).apply(inst, Skill::new));
    public static final Codec<Holder<Skill>> CODEC = RegistryFileCodec.create(AMRegistryKeys.SKILL, DIRECT_CODEC);

    /**
     * @param holder The skill {@link Holder} to query.
     * @return The display name of the given skill.
     */
    public static MutableComponent getName(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().location()) + ".name");
    }

    /**
     * @param holder The skill {@link Holder} to query.
     * @return The description of the given skill.
     */
    public static MutableComponent getDescription(Holder<Skill> holder) {
        return Component.translatable(Util.makeDescriptionId("skill", holder.getKey().location()) + ".description");
    }
}
