package at.minecraftschurli.arsmagicalegacy.spell;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import org.slf4j.LoggerFactory;

public final class SpellPartDataManager extends AMDataManager<SpellPartData> {
    public static final SpellPartDataManager INSTANCE = new SpellPartDataManager();

    private SpellPartDataManager() {
        super("spell_part", SpellPartData.CODEC, LoggerFactory.getLogger(SpellPartDataManager.class));
    }
}
