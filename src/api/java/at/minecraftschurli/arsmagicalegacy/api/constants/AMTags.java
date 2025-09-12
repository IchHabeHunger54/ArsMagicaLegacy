package at.minecraftschurli.arsmagicalegacy.api.constants;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

/**
 * Holds all {@link TagKey}s added by Ars Magica: Legacy.
 */
public interface AMTags {
    interface Blocks {
        TagKey<Block> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Block> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Block> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Block> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Block> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Block> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Block> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Block> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Block> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Block> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Block> WITCHWOOD_LOGS = tag("witchwood_logs");
        TagKey<Block> AUM_PLANTABLE_ON = tag("aum_plantable_on");
        TagKey<Block> CERUBLOSSOM_PLANTABLE_ON = tag("cerublossom_plantable_on");
        TagKey<Block> DESERT_NOVA_PLANTABLE_ON = tag("desert_nova_plantable_on");
        TagKey<Block> TARMA_ROOT_PLANTABLE_ON = tag("tarma_root_plantable_on");

        private static TagKey<Block> cTag(String name) {
            return TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Block> tag(String name) {
            return TagKey.create(Registries.BLOCK, ArsMagicaApi.modLoc(name));
        }
    }

    interface Items {
        TagKey<Item> ORES_CHIMERITE = cTag("ores/chimerite");
        TagKey<Item> ORES_TOPAZ = cTag("ores/topaz");
        TagKey<Item> ORES_VINTEUM = cTag("ores/vinteum");
        TagKey<Item> ORES_MOONSTONE = cTag("ores/moonstone");
        TagKey<Item> ORES_SUNSTONE = cTag("ores/sunstone");
        TagKey<Item> STORAGE_BLOCKS_CHIMERITE = cTag("storage_blocks/chimerite");
        TagKey<Item> STORAGE_BLOCKS_TOPAZ = cTag("storage_blocks/topaz");
        TagKey<Item> STORAGE_BLOCKS_VINTEUM = cTag("storage_blocks/vinteum");
        TagKey<Item> STORAGE_BLOCKS_MOONSTONE = cTag("storage_blocks/moonstone");
        TagKey<Item> STORAGE_BLOCKS_SUNSTONE = cTag("storage_blocks/sunstone");
        TagKey<Item> GEMS_CHIMERITE = cTag("gems/chimerite");
        TagKey<Item> GEMS_TOPAZ = cTag("gems/topaz");
        TagKey<Item> DUSTS_VINTEUM = cTag("dusts/vinteum");
        TagKey<Item> GEMS_MOONSTONE = cTag("gems/moonstone");
        TagKey<Item> GEMS_SUNSTONE = cTag("gems/sunstone");
        TagKey<Item> DUSTS_ARCANE_COMPOUND = cTag("dusts/arcane_compound");
        TagKey<Item> DUSTS_ARCANE_ASH = cTag("dusts/arcane_ash");
        TagKey<Item> DUSTS_PURIFIED_VINTEUM = cTag("dusts/purified_vinteum");
        TagKey<Item> WITCHWOOD_LOGS = tag("witchwood_logs");
        TagKey<Item> INSCRIPTION_TABLE_BOOKS = tag("inscription_table_books");
        TagKey<Item> OCCULUS_FORGET_ALL = tag("occulus_forget_all");
        TagKey<Item> RUNES = tag("runes");
        TagKey<Item> SPELLCRAFTING_START = tag("spellcrafting_start");
        TagKey<Item> SPELLCRAFTING_END = tag("spellcrafting_end");

        private static TagKey<Item> cTag(String name) {
            return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("c", name));
        }

        private static TagKey<Item> tag(String name) {
            return TagKey.create(Registries.ITEM, ArsMagicaApi.modLoc(name));
        }
    }

    interface EntityTypes {
        TagKey<EntityType<?>> AFFECTED_BY_ENDER_THORNS_ABILITY = tag("affected_by_ender_thorns_ability");
        TagKey<EntityType<?>> AFFECTED_BY_SMITE_ABILITY = tag("affected_by_smite_ability");
        TagKey<EntityType<?>> AFFECTED_BY_NAUSEA_ABILITY = tag("affected_by_nausea_ability");

        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, ArsMagicaApi.modLoc(name));
        }
    }

    interface DamageTypes {
        TagKey<DamageType> AFFECTED_BY_FIRE_RESISTANCE_ABILITY = tag("affected_by_fire_resistance_ability");
        TagKey<DamageType> AFFECTED_BY_RESISTANCE_ABILITY = tag("affected_by_resistance_ability");
        TagKey<DamageType> AFFECTED_BY_FALL_DAMAGE_ABILITY = tag("affected_by_fall_damage_ability");
        TagKey<DamageType> AFFECTED_BY_FEATHER_FALLING_ABILITY = tag("affected_by_feather_falling_ability");
        TagKey<DamageType> AFFECTED_BY_MAGIC_DAMAGE_ABILITY = tag("affected_by_magic_damage_ability");

        private static TagKey<DamageType> tag(String name) {
            return TagKey.create(Registries.DAMAGE_TYPE, ArsMagicaApi.modLoc(name));
        }
    }
}
