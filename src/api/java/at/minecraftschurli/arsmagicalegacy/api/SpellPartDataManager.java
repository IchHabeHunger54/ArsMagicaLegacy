package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPartData;

public interface SpellPartDataManager {
    SpellPartData get(SpellPart part);
}
