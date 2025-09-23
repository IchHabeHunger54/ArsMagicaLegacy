package at.minecraftschurli.arsmagicalegacy.api.client;

import at.minecraftschurli.arsmagicalegacy.api.client.event.RegisterParticleControllersEvent;
import com.mojang.datafixers.Products;
import com.mojang.datafixers.kinds.App;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

/**
 * Represents a particle controller, as serialized from a {@link ParticleSpawner}. To make a tickable instance, see {@link ParticleControllerInstance}.
 * <p>
 * Register {@link ParticleController}s during {@link RegisterParticleControllersEvent}, using the {@link ParticleController.Type} record.
 */
public interface ParticleController {
    Codec<ParticleController> CODEC = ResourceLocation.CODEC.comapFlatMap(
        id -> Optional.ofNullable(ArsMagicaClientApi.particleController(id))
            .map(DataResult::success)
            .orElseGet(() -> DataResult.error(() -> "Unknown particle controller: " + id)),
        ParticleController.Type::id
    ).dispatch(controller -> ArsMagicaClientApi.particleController(controller.id()), ParticleController.Type::codec);

    /**
     * @param instance The {@link RecordCodecBuilder.Instance} to use.
     * @return A codec builder with the base fields for every controller set. Call {@link Products.P3#and(App)} to add further fields.
     * @param <T> The exact type of the controller.
     */
    static <T extends ParticleController> Products.P2<RecordCodecBuilder.Mu<T>, Boolean, Boolean> baseFields(RecordCodecBuilder.Instance<T> instance) {
        return instance.group(
            Codec.BOOL.optionalFieldOf("stop_other_controllers", false).forGetter(ParticleController::stopOtherControllers),
            Codec.BOOL.optionalFieldOf("kill_on_finish", false).forGetter(ParticleController::killOnFinish));
    }

    /**
     * Ticks the given {@link ParticleControllerInstance}.
     *
     * @param instance The {@link ParticleControllerInstance} to tick.
     */
    void tick(ParticleControllerInstance instance);

    /**
     * Ticks the given {@link ParticleControllerInstance} on its first tick. Override this for special behavior on first tick.
     *
     * @param instance The {@link ParticleControllerInstance} to tick.
     */
    default void tickFirst(ParticleControllerInstance instance) {
        tick(instance);
    }

    /**
     * @return The registered id of the controller.
     */
    ResourceLocation id();

    /**
     * @return Whether all further controllers are stopped when this controller is run.
     */
    boolean stopOtherControllers();

    /**
     * @return Whether the particle should be removed after this controller has finished.
     */
    boolean killOnFinish();

    /**
     * The registered type of a {@link ParticleController}.
     *
     * @param id    The id of the controller.
     * @param codec The {@link MapCodec} of the controller.
     */
    record Type(ResourceLocation id, MapCodec<? extends ParticleController> codec) {
    }
}
