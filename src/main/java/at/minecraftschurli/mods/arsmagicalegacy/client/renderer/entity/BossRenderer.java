package at.minecraftschurli.mods.arsmagicalegacy.client.renderer.entity;

import at.minecraftschurli.mods.arsmagicalegacy.entity.AbstractBoss;
import com.geckolib.constant.DataTickets;
import com.geckolib.constant.dataticket.DataTicket;
import com.geckolib.renderer.GeoEntityRenderer;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.EntityType;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.Nullable;

public class BossRenderer<T extends AbstractBoss, R extends LivingEntityRenderState> extends GeoEntityRenderer<T, R> {
    public static final DataTicket<AbstractBoss.Action> ACTION_DATA_TICKET = DataTickets.create("action", new TypeToken<>() {});

    public BossRenderer(EntityRendererProvider.Context context, EntityType<? extends T> entityType) {
        super(context, entityType);
    }

    @Override
    public void addRenderData(T animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(ACTION_DATA_TICKET, animatable.getAction());
    }

    public static <T extends AbstractBoss> void register(EntityRenderersEvent.RegisterRenderers event, DeferredHolder<EntityType<?>, EntityType<T>> holder) {
        event.registerEntityRenderer(holder.get(), context -> new BossRenderer<>(context, holder.get()));
    }
}
