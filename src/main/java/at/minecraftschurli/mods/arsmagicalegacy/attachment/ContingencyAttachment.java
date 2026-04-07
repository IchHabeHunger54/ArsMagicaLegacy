package at.minecraftschurli.mods.arsmagicalegacy.attachment;

import at.minecraftschurli.mods.arsmagicalegacy.api.spell.MutableSpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.api.spell.SpellFacade;
import at.minecraftschurli.mods.arsmagicalegacy.util.AMUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public final class ContingencyAttachment extends MutableSpellFacade.Delegating {
    public static final Codec<ContingencyAttachment> CODEC = RecordCodecBuilder.create(inst -> inst.group(
        Identifier.CODEC.fieldOf("contingency").forGetter(ContingencyAttachment::contingency),
        MutableHolder.MAP_CODEC.forGetter(ContingencyAttachment::getDelegate)
    ).apply(inst, ContingencyAttachment::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, ContingencyAttachment> STREAM_CODEC = StreamCodec.composite(
        Identifier.STREAM_CODEC, ContingencyAttachment::contingency,
        MutableHolder.STREAM_CODEC, ContingencyAttachment::getDelegate,
        ContingencyAttachment::new);

    public static final ContingencyAttachment DEFAULT = new ContingencyAttachment(AMUtil.MISSINGNO, SpellFacade.EMPTY);
    private final Identifier contingency;

    public ContingencyAttachment(Identifier contingency, SpellFacade spell) {
        super(spell);
        this.contingency = contingency;
    }

    public Identifier contingency() {
        return contingency;
    }
}
