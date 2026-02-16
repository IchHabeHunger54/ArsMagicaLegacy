package at.minecraftschurli.arsmagicalegacy.ritual;

import at.minecraftschurli.arsmagicalegacy.api.ritual.Ritual;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import org.slf4j.LoggerFactory;

public final class RitualManager extends AMDataManager<Ritual> {
    public static final RitualManager INSTANCE = new RitualManager();

    public RitualManager() {
        super("ritual", Ritual.CODEC, LoggerFactory.getLogger(RitualManager.class));
    }
}
