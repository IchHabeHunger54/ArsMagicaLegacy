package at.minecraftschurli.arsmagicalegacy.spell.shape;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.spell.SecondarySpellShape;
import at.minecraftschurli.arsmagicalegacy.api.spell.Spell;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellModifier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class Contingency extends SecondarySpellShape {
    private final ResourceLocation contingency;

    public Contingency(ResourceLocation contingency) {
        this.contingency = contingency;
    }

    @Override
    public Spell cast(Spell spell, List<SpellModifier> modifiers, Level level, @Nullable LivingEntity caster, @Nullable Entity directEntity, @Nullable HitResult hitResult) {
        if (hitResult instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof LivingEntity living) {
            ArsMagicaApi.spellHelper().setContingency(living, contingency, spell);
        }
        return spell;
    }
}
