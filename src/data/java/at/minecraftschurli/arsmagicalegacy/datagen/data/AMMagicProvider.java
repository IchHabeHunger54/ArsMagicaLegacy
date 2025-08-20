package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistryKeys;
import at.minecraftschurli.arsmagicalegacy.api.magic.OcculusTab;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.magic.SkillPoint;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;

import java.util.Arrays;
import java.util.Map;

public final class AMMagicProvider {
    public static void addOcculusTabs(BootstrapContext<OcculusTab> bootstrap) {
        bootstrap.register(AMMagic.OFFENSE, new OcculusTab(1024, 1024, 226, 46, 0, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.DEFENSE, new OcculusTab(1024, 1024, 181, 46, 1, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.UTILITY, new OcculusTab(1024, 1024, 136, 46, 2, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.TALENT, new OcculusTab(1024, 1024, 91, 46, 3, ArsMagicaApi.modLoc("default")));
        bootstrap.register(AMMagic.AFFINITY, new OcculusTab(1024, 1024, 0, 0, 4, ArsMagicaApi.modLoc("affinity")));
    }

    public static void addSkillPoints(BootstrapContext<SkillPoint> bootstrap) {
        bootstrap.register(AMMagic.BLUE_POINT, new SkillPoint(0x0000ff, 0, 1));
        bootstrap.register(AMMagic.GREEN_POINT, new SkillPoint(0x00ff00, 10, 2));
        bootstrap.register(AMMagic.RED_POINT, new SkillPoint(0xff0000, 20, 3));
    }

    public static void addSkills(BootstrapContext<Skill> bootstrap) {
    }

    private static void addSkill(BootstrapContext<Skill> bootstrap, String name, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, boolean hidden, String... parents) {
        bootstrap.register(ResourceKey.create(AMRegistryKeys.SKILL, ArsMagicaApi.modLoc(name)), new Skill(
            Arrays.stream(parents)
                .map(ArsMagicaApi::modLoc)
                .map(e -> bootstrap.lookup(AMRegistryKeys.SKILL).getOrThrow(ResourceKey.create(AMRegistryKeys.SKILL, e)))
                .map(Holder::value)
                .toList(),
            Map.of(bootstrap.lookup(AMRegistryKeys.SKILL_POINT).getOrThrow(point), 1),
            bootstrap.lookup(AMRegistryKeys.OCCULUS_TAB).getOrThrow(tab).value(),
            x,
            y,
            hidden));
    }

    private static void addSkill(BootstrapContext<Skill> bootstrap, String name, ResourceKey<SkillPoint> point, ResourceKey<OcculusTab> tab, int x, int y, String... parents) {
        addSkill(bootstrap, name, point, tab, x, y, false, parents);
    }
}
