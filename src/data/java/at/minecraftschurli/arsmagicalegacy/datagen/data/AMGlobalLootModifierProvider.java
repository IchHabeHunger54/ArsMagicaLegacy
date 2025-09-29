package at.minecraftschurli.arsmagicalegacy.datagen.data;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public final class AMGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public AMGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, ArsMagicaApi.MOD_ID);
    }

    @Override
    protected void start() {
        addTomeModifier(BuiltInLootTables.ANCIENT_CITY);
        addTomeModifier(BuiltInLootTables.ANCIENT_CITY_ICE_BOX);
        addTomeModifier(BuiltInLootTables.SHIPWRECK_TREASURE);
        addTomeModifier(BuiltInLootTables.UNDERWATER_RUIN_BIG);
        addTomeModifier(BuiltInLootTables.UNDERWATER_RUIN_SMALL);
        addTomeModifier(BuiltInLootTables.BASTION_TREASURE);
        addTomeModifier(BuiltInLootTables.NETHER_BRIDGE);
        addTomeModifier(BuiltInLootTables.ABANDONED_MINESHAFT);
        addTomeModifier(BuiltInLootTables.SIMPLE_DUNGEON);
        addTomeModifier(BuiltInLootTables.DESERT_PYRAMID);
        addTomeModifier(BuiltInLootTables.VILLAGE_DESERT_HOUSE);
        addTomeModifier(BuiltInLootTables.IGLOO_CHEST);
        addTomeModifier(BuiltInLootTables.VILLAGE_SNOWY_HOUSE);
        addTomeModifier(BuiltInLootTables.VILLAGE_TAIGA_HOUSE);
        addTomeModifier(BuiltInLootTables.PILLAGER_OUTPOST);
        addTomeModifier(BuiltInLootTables.VILLAGE_SAVANNA_HOUSE);
        addTomeModifier(BuiltInLootTables.JUNGLE_TEMPLE);
        addTomeModifier(BuiltInLootTables.VILLAGE_PLAINS_HOUSE);
        addTomeModifier(BuiltInLootTables.STRONGHOLD_LIBRARY);
        addTomeModifier(BuiltInLootTables.WOODLAND_MANSION);
        addTomeModifier(BuiltInLootTables.VILLAGE_TEMPLE);
        addTomeModifier(BuiltInLootTables.END_CITY_TREASURE);
    }

    private void addTomeModifier(ResourceKey<LootTable> table) {
        String path = table.location().getPath();
        add(path.replace("chests/", ""), new AddTableLootModifier(new LootItemCondition[]{LootTableIdCondition.builder(table.location()).build()}, ResourceKey.create(Registries.LOOT_TABLE, ArsMagicaApi.modLoc(path.replace("chests/", "chests/modify/")))));
    }
}
