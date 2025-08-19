package at.minecraftschurli.arsmagicalegacy.apiimpl;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.SpellPartDataManager;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellPartData;
import at.minecraftschurli.arsmagicalegacy.util.AMDataManager;
import org.slf4j.LoggerFactory;

final class SpellPartDataManagerImpl extends AMDataManager<SpellPartData> implements SpellPartDataManager {
    SpellPartDataManagerImpl() {
        super("spell_part", SpellPartData.CODEC, LoggerFactory.getLogger(SpellPartDataManagerImpl.class));
    }

    @Override
    public SpellPartData get(SpellPart part) {
        return get(ArsMagicaApi.getSpellPartRegistry().getKey(part));
    }
}
