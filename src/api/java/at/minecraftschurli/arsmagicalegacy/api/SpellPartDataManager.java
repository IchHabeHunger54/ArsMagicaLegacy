package at.minecraftschurli.arsmagicalegacy.api;

import at.minecraftschurli.arsmagicalegacy.api.magic.SpellPart;
import at.minecraftschurli.arsmagicalegacy.api.magic.SpellPartData;

public interface SpellPartDataManager {
    SpellPartData get(SpellPart part);
}
