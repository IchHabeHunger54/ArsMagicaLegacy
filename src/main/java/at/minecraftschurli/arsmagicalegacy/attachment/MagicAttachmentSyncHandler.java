package at.minecraftschurli.arsmagicalegacy.attachment;

import at.minecraftschurli.arsmagicalegacy.api.magic.MagicAttachment;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.Nullable;

public class MagicAttachmentSyncHandler implements AttachmentSyncHandler<MagicAttachment> {
    @Override
    public void write(RegistryFriendlyByteBuf buf, MagicAttachment attachment, boolean initialSync) {
        MagicAttachment.STREAM_CODEC.encode(buf, attachment);
    }

    @Override
    public MagicAttachment read(IAttachmentHolder holder, RegistryFriendlyByteBuf buf, @Nullable MagicAttachment previousValue) {
        if (FMLEnvironment.getDist().isClient() && ModList.get().isLoaded("jei")) {
            // TODO jei
            //AMClientUtil.mc().submit(HiddenSkills::update);
        }
        return MagicAttachment.STREAM_CODEC.decode(buf);
    }
}
