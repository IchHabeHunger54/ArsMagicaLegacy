# General

- Updated to 1.21.1
- Rewrote most of the mod from the ground up for more overall stability going forward
- Added new advancements for the mod
- Added translations for the mod's tags
- Various minor balancing tweaks

# Blocks

- Made various witchwood and flower blocks/items flammable, compostable and strippable where appropriate
- The Altar now accepts Shulker Boxes, Rune Bags and other container items, and will pull spell ingredients from their contents

## Liquid Etherium

- Renamed Liquid Essence to Liquid Etherium
- Now generates in lakes in plains-like biomes, in addition to the centers of Moonstone meteorites
- Can now be used to create the Arcane Compendium by placing a book in a nearby item frame, similar to old Ars Magica
- Now has underwater fog
- Now can be placed in Cauldrons
- Liquid Etherium Bucket can now be used by Dispensers

## Occulus

- Added a Forget All button
- Improved the display of the affinities in the Affinity tab
- Updated the tab icons

## Inscription Table

- Added a Clear button
- Spell Recipes can now be changed afterwards by placing them back into the table
- Applied upgrades now show visually on the item if the block is broken, or middle-clicked in creative

## Inlays

- Now each have an extra functionality, ported over from Ars Magica 2
- Redstone Inlays act as even stronger powered rails, with the potential to derail minecarts entirely if not handled carefully
- Iron Inlays reverse the directions of minecarts that touch them
- Gold Inlays attempt to teleport minecarts to the next Gold Inlay in 8 blocks range

# Items

- Dyed Spell Books can now be undyed at a Cauldron
- Moved some Affinity Tomes to other biomes or structures
- Rune Bags can no longer be inserted into Shulker Boxes or similar containers

## Crystal Phylactery

- New item
- When killing a mob with an empty Crystal Phylactery in your inventory, a mob "soul" is added to the Crystal Phylactery
- Full Crystal Phylacteries can be used in a Summon spell to set the summoned mob type
- Stronger mobs require more kills before they can be used
- Certain mobs, such as bosses or disabled mobs, cannot be added to a Crystal Phylactery (this can be modified via datapacks)
- If not empty, a Crystal Phylactery can be shift-right clicked to empty it
- Filled variants for all supported mobs show up in the creative inventory and in JEI

## Magitech Goggles

- Are no longer unbreakable
- Can be repaired using Topaz
- Now have visual indicators for etherium connections

# Skills & Spell Parts

- Changed the name and/or behavior of the following spell parts:
    - Agility: Renamed to Swiftness. Now uses the Swiftness effect. The Agility effect was removed.
    - Dismembering: Is now functional.
    - Frost: No longer utilizes an effect, allowing it to stack with similar effects from other mods. The Frost effect was removed.
    - Grow: Now supports a variety of new crops.
    - Harvest: Now supports a variety of new crops. No longer has a built-in AoE effect.
    - Plant: Renamed to Replant. Now acts as an upgrade to Harvest that also replants the crops if applicable.
    - Rune Procs: Renamed to Rune Power.
    - Shield: Renamed to Resistance. Now uses the Resistance effect. The Shield and Magic Shield effects were removed.
    - Touch: Now uses the block/entity interaction range attributes.
- Changed the positions and costs of various skills, making them cheaper overall
- Changed how spell parts such as Color, Place Block or Recall store their metadata
- Added new config options for tweaking the behavior of various spell parts
- Removed the Telekinesis component

# Affinities

- Affinity abilities now use an effect system that can be modified via datapacks
- Added a notification when shifting into/out of affinity abilities
