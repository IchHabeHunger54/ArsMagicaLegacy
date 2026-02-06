package at.minecraftschurli.arsmagicalegacy.datagen;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.api.ability.Ability;
import at.minecraftschurli.arsmagicalegacy.api.constants.AMRegistries;
import at.minecraftschurli.arsmagicalegacy.api.magic.Affinity;
import at.minecraftschurli.arsmagicalegacy.api.magic.Skill;
import at.minecraftschurli.arsmagicalegacy.api.spell.SpellPart;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.AMMultiblocks;
import at.minecraftschurli.arsmagicalegacy.compat.patchouli.SpellPartPage;
import at.minecraftschurli.arsmagicalegacy.datagen.data.AMAbilityProvider;
import at.minecraftschurli.arsmagicalegacy.init.AMCreativeTabs;
import at.minecraftschurli.arsmagicalegacy.init.AMDataComponents;
import at.minecraftschurli.arsmagicalegacy.init.AMItems;
import at.minecraftschurli.arsmagicalegacy.init.AMMagic;
import at.minecraftschurli.arsmagicalegacy.init.AMSpells;
import at.minecraftschurli.arsmagicalegacy.item.DataComponentNamedItem;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.AbstractPageBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.BookBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.EntryBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.PatchouliBookProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.translated.TranslatedBookBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.translated.TranslatedCategoryBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.translated.TranslatedEntryBuilder;
import com.google.gson.JsonObject;
import net.minecraft.Util;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

final class AMPatchouliBookProvider extends PatchouliBookProvider {
    private final BiConsumer<String, String> translationConsumer;

    public AMPatchouliBookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BiConsumer<String, String> translationConsumer, boolean includeClient, boolean includeServer) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID, includeClient, includeServer);
        this.translationConsumer = translationConsumer;
    }

    @SuppressWarnings({"ResultOfMethodCallIgnored", "DataFlowIssue"})
    @Override
    protected void addBooks(HolderLookup.Provider lookupProvider, Consumer<BookBuilder<?, ?, ?>> consumer) {
        HolderLookup.RegistryLookup<Affinity> affinityRegistry = lookupProvider.lookupOrThrow(AMRegistries.AFFINITY);
        TranslatedBookBuilder builder = createBookBuilder("arcane_compendium", "Arcane Compendium", "A renewed look into Minecraft with a splash of magic...", translationConsumer, lookupProvider)
            .setBookTexture(ResourceLocation.fromNamespaceAndPath("patchouli", "textures/gui/book_purple.png"))
            .setCreativeTab(AMCreativeTabs.MAIN.getId())
            .setModel(ArsMagicaApi.id("arcane_compendium"))
            .setVersion("1");
        builder.addCategory("mechanics", "Mechanics", "", new ItemStack(AMItems.ALTAR_CORE.get()))
            .setSortnum(0)
            .addEntry("getting_started", "Getting Started", ArsMagicaApi.book())
            .setPriority(true)
            .addSimpleTextPage("Spellcrafting looks complex from a distance, but gets very easy when doing it more often.$(br2)You start by crafting an $(l:blocks/occulus)Occulus$(), placing it down and opening it. Through the Occulus, you can unlock new skills. Skills come in four categories, more on that in a minute.")
            .addImagePage(ArsMagicaApi.id("patchouli_books/arcane_compendium/en_us/images/occulus.png")).setText("The Occulus has five tabs, the first four of which are skill tree tabs.").build()
            .addSimpleTextPage("The first category of skills, shapes, determine how the spell is cast. For example, $(l:shapes/self)Self$() means that the spell is cast onto yourself, while $(l:shapes/projectile)Projectile$() shoots a projectile that casts the spell on whatever it hits.$(br2)Shapes have a square outline in the Occulus.", "Shapes")
            .addSimpleTextPage("The second skill category, the components, represent what the spell does. For instance, $(l:components/physical_damage)Physical Damage$() acts as if the spell hit the target with a sword, while $(l:components/dig)Dig$() breaks the targeted block. As you may have guessed, some components only affect blocks, some only affect mobs, some affect both, and very few affect neither, instead doing something else entirely.$(br2)Components have an octagonal outline in the Occulus.", "Components")
            .addSimpleTextPage("Next up, modifiers. Modifiers can affect both shapes and components, but not every combination will turn out to actually have an effect (what sense would $(l:components/fire_damage)Fire Damage$() + $(l:modifiers/gravity)Gravity$() make?) The book tells you most, but not all useful combinations.$(br2)Modifiers have a rotated square outline in the Occulus.", "Modifiers")
            .addSimpleTextPage("And finally, talents. Talents are not regular spell parts, instead, they are permanent unlockables that boost the player's capability to perform magic in different ways.$(br2)Talents have an octagonal shape, like components. They can be distinguished from them by looking at the tab: All talents are in the Occulus's Talents tab, and all octagonal parts in the Talents tab are talent skills.", "Talents")
            .addSimpleTextPage("A word should also go to the Affinity tab of the Occulus. This tab displays your affinity depths. It would be too complex to explain this here, so for further reading, please consult the $(l:affinities/affinities)affinity chapter$().", "Affinities")
            .addSimpleTextPage("At this point, you may rightfully ask yourself: Why should I learn all this? We will catch up to this in a moment. For now, the next thing you need is an $(l:blocks/inscription_table)Inscription Table$(). The Inscription Table is where you will assemble your spell. Since this can be complex for novices, it will be explained in detail on the following pages.")
            .addSimpleTextPage("1) The Source Area contains all skills you currently know.$(br)2) A slot that takes in a Book & Quill. The spell recipe will be written onto this book.$(br)3) A total of five brown squares, the so-called $(l:mechanics/shape_groups)shape group$() areas. You can drag shapes and modifiers here. For the beginning, you should only be using the first one.$(br)4) The spell grammar section. This is where components and component-related modifiers go.")
            .addImagePage(ArsMagicaApi.id("patchouli_books/arcane_compendium/en_us/images/inscription_table_1.png")).setText("The search bar can be used to quickly find a spell part. The name bar can be used to name your spell, this is not required though.").build()
            .addImagePage(ArsMagicaApi.id("patchouli_books/arcane_compendium/en_us/images/inscription_table_2.png")).setText("Drag the skills down to the shape groups and the spell grammar section.").build()
            .addImagePage(ArsMagicaApi.id("patchouli_books/arcane_compendium/en_us/images/inscription_table_3.png")).setText("The shown spell recipe is $(l:shapes/projectile)Projectile$()-$(l:components/dig)Dig$(), which is recommended for beginners.").build()
            .addSimpleTextPage("Note that not every combination is allowed in the Inscription Table. Do not worry, though, since you will notice soon enough that you are trying to do something that is not permitted.$(br2)Once you are done, simply take out the book. To get back the book without writing the spell recipe, instead shift-move the book out of the table.")
            .addSimpleTextPage("Now that you have your spell recipe, you can do the final step: crafting the spell at the $(l:mechanics/crafting_altar)Crafting Altar$(). Please refer to its section to find out how to construct it. $(br2)To start crafting the spell, put the recipe onto the altar's lectern. The items you need to throw in will appear above it, always starting with a $(l:items/runes)Blank Rune$() and ending with a $(l:items/spell_parchment)Spell Parchment$().")
            .addSimpleTextPage("When first using the spell, you must choose an icon and a name for the spell. After that, you are done! It is heavily recommended to at least read the other chapters in this category, as they cover most things to know in magic.$(br2)Happy spellcasting!")
            .build()
            .addEntry("crafting_altar", "Crafting Altar", new ItemStack(AMItems.ALTAR_CORE.get()))
            .addSimpleTextPage("Harnessing the forces of creation, the crafting altar allows you to work miracles of magic. This is where you will create all of your spells.")
            .addSimpleSpotlightPage(new ItemStack(AMItems.ALTAR_CORE.get()), "A basic yet important block, it focuses an altar's power in order to perform spell crafting.")
            .addSimpleSpotlightPage(new ItemStack(AMItems.MAGIC_WALL.get()), "The magic wall was a nice try into illusion blocks, but it did not work. Instead, it has proven important when building crafting altars.")
            .addSimpleDoubleRecipePage("crafting", AMItems.ALTAR_CORE.getId(), AMItems.MAGIC_WALL.getId())
            .addSimpleMultiblockPage("Crafting Altar", AMMultiblocks.ALTAR)
            .addSimpleTextPage("The altar is upgradeable by two groups, the caps and the structure materials. Both groups stack cumulatively, so for example wooden planks (1) plus glass (1) equals a power of two, while sandstone (2) plus lapis blocks (8) equals a power of ten. The higher the power, the more different crafting ingredients the altar can consume for a single spell.")
            .addSimpleTextPage("- Glass: 1$(br)- Block of Coal: 2$(br)- Block of Copper (and variants): 3$(br)- Block of Iron: 4$(br)- Block of Redstone: 5$(br)- $(l:blocks/ores#vinteum)Block of Vinteum$(): 6$(br)- $(l:blocks/ores#chimerite)Block of Chimerite$(): 7$(br)- Block of Lapis: 8$(br)- Block of Gold: 9$(br)- $(l:blocks/ores#topaz)Block of Topaz$(): 10", "Caps")
            .addSimpleTextPage("- Block of Diamond: 11$(br)- Block of Emerald: 12$(br)- Block of Netherite: 13$(br)- $(l:blocks/ores#moonstone)Block of Moonstone$(): 14$(br)- $(l:blocks/ores#sunstone)Block of Sunstone$(): 15")
            .addSimpleTextPage("- Wooden Planks (Overworld): 1$(br)- (Mossy) Cobblestone: 2$(br)- Cobbled Deepslate: 2$(br)- Andesite/Diorite/Granite: 2$(br)- (Red) Sandstone: 2$(br)- Bricks: 3$(br)- (Mossy) Stone Bricks: 3$(br)- Polished Deepslate: 3$(br)- Deepslate Bricks/Tiles: 3$(br)- Polished Andesite/Diorite/Granite: 3$(br)- Smooth (Red) Sandstone: 3$(br)- Cut Copper (and variants): 3", "Structure Materials")
            .addSimpleTextPage("- Witchwood Planks: 4$(br)- All Prismarine Variants: 4$(br)- Wooden Planks (Nether): 4$(br)- Blackstone: 4$(br)- Quartz Block: 4$(br)- (Red) Nether Bricks: 5$(br)- Polished Blackstone (Bricks): 5$(br)- Smooth Quartz: 5$(br)- End Stone Bricks: 6$(br)- Purpur Block: 6")
            .build()
            .addEntry("etherium", "Etherium", new ItemStack(AMItems.CRYSTAL_WRENCH.get()))
            .addSimpleTextPage("During spellcrafting, you will sooner or later stumble across the requirement of Etherium. Etherium is an invisible magical substance that comes in three variants: light, neutral and dark. Each variant is created in its respective generator:$(br2)- Neutral -> $(l:blocks/obelisk)Obelisk$()$(br)- Light -> $(l:blocks/celestial_prism)Celestial Prism$()$(br)- Dark -> $(l:blocks/black_aurem)Black Aurem$()")
            .addSimpleRecipePage("crafting", AMItems.CRYSTAL_WRENCH.getId(), "To actually consume the Etherium, you need to link it to the $(l:mechanics/crafting_altar)altar$() by first right-clicking the generator, then the Altar Core with a Crystal Wrench.$(br2)To unlink them, select the combination again.")
            .addSimpleTextPage("If a spell requires Etherium, the generator is properly linked and has the required Etherium amount of the correct type, simply flip the lever on the altar. The required amount of Etherium will be drawn automatically.")
            .build()
            .addEntry("liquid_etherium", "Liquid Etherium", new ItemStack(AMItems.LIQUID_ETHERIUM_BUCKET.get()))
            .addSimpleTextPage("Liquid Etherium is a water-like substance that can rarely be found in plains and plains-like environments. For the most part, it acts exactly like water would, though it cannot be used to waterlog blocks, create bubble columns or other, rather exotic appliances. Also, it does not create sources or trickle down from dripstones.")
            .addSimpleSpotlightPage(new ItemStack(AMItems.LIQUID_ETHERIUM_BUCKET.get()), "Liquid Etherium can be processed in an $(l:blocks/obelisk)Obelisk$() to create neutral $(l:mechanics/etherium)Etherium$(). One bucket of Liquid Etherium is worth 1000 Etherium (or 10 units of $(l:blocks/ores#vinteum)vinteum$()).")
            .addSimpleRecipePage("crafting", ArsMagicaApi.id("arcane_compendium"), "And as you should know by now, combining it with a Book yields a copy of the very book you are reading this in.")
            .build()
            .addEntry("shape_groups", "Shape Groups", new ItemStack(AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.get()))
            .addSimpleTextPage("Shape groups define how your spell is executed. A spell has multiple shape groups, each shape group is represented by one of the brown squares in the $(l:blocks/inscription_table)Inscription Table$(). The shape group can be changed by pressing $(k:arsmagicalegacy.next_shape_group) or $(k:arsmagicalegacy.prev_shape_group) for the next or the previous shape group, respectively.")
            .addSimpleTextPage("If you want to have more than two shape groups, you will need to upgrade your Inscription Table. This can be done by right-clicking it with an upgrade, or by putting it in a crafting field with one.$(br2)Upgrades need to be applied consecutively, i.e. level one, then level two, then level three. The Inscription Table will retain upgrades when broken.")
            .addSimpleDoubleRecipePage("crafting", AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_1.getId(), AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_2.getId())
            .addSimpleRecipePage("crafting", AMItems.INSCRIPTION_TABLE_UPGRADE_TIER_3.getId(), "Shape groups prove useful if, for example, you want to have two modes on a $(l:components/heal)Heal$() spell, one for $(l:shapes/self)yourself$() and one for $(l:shapes/projectile)your friends$().", "")
            .build()
            .addEntry("silver_spell_parts", "Silver Spell Parts", new ItemStack(AMItems.MOONSTONE.get()))
            .addSimpleTextPage("Silver spell parts are among the strongest spell parts known. Due to this, they cannot be unlocked like any other spell part. Instead, to unlock them, a specific combination of other spell parts must be cast.$(br2)While the original combinations have been lost to time, a few general concepts are still known today:")
            .addSimpleTextPage("- Only components and modifiers need to be considered. Any shape may be used.$(br)- Additional spell parts may be used, the ritual is not strict in that regard.$(br)- There is no particular order of components and modifiers required.$(br)- There is a thematic fit to the unlocking ritual. For example, $(l:components/firestorm)Firestorm$() does not require $(l:components/drown)Drown$().$(br)- All silver spells require at least two and up to six other spell parts.")
            .build()
            .build();
        builder.addCategory("blocks", "Blocks", "", new ItemStack(AMItems.OCCULUS.get()))
            .setSortnum(1)
            .addEntry("black_aurem", "Black Aurem", new ItemStack(AMItems.BLACK_AUREM.get()))
            .addSimpleTextPage("The Black Aurem is the $(l:blocks/obelisk)Obelisk's$() dark counterpart, used to generate dark $(l:mechanics/etherium)Etherium$(). Instead of $(l:blocks/ores#vinteum)Vinteum Dust$(), it consumes the lifes of animals.$(br2)Similar to the Obelisk, it can be supported by a multiblock structure, which builds up in levels. The structure's different levels are shown on the later pages.")
/*
            .addSimpleSpotlightPage(new ItemStack(AMItems.BLACK_AUREM.get()), "The Black Aurem cannot be obtained through crafting. Instead, it must be created through a special corruption ritual. This ritual requires a multiblock structure, seen on the next page. Once built, throw a piece of $(l:blocks/ores#sunstone)Sunstone$() somewhere into the structure.")
            .addSimpleMultiblockPage("Corruption Ritual", "Finally, to initiate the ritual, you must $(l:components/fire_damage)severely burn$() the bottom of the Obelisk.", AMMultiblocks.CORRUPTION_RITUAL)
*/
            .addSimpleMultiblockPage("Black Aurem Chalk", AMMultiblocks.BLACK_AUREM_CHALK)
            .addSimpleMultiblockPage("Black Aurem Level 1", AMMultiblocks.BLACK_AUREM_PILLARS_1)
            .addSimpleMultiblockPage("Black Aurem Level 2", AMMultiblocks.BLACK_AUREM_PILLARS_2)
            .addSimpleMultiblockPage("Black Aurem Level 3", AMMultiblocks.BLACK_AUREM_PILLARS_3)
            .addSimpleMultiblockPage("Black Aurem Level 4", AMMultiblocks.BLACK_AUREM_PILLARS_4)
            .build()
            .addEntry("celestial_prism", "Celestial Prism", new ItemStack(AMItems.CELESTIAL_PRISM.get()))
            .addSimpleTextPage("The Celestial Prism is the $(l:blocks/obelisk)Obelisk's$() light counterpart, used to generate light $(l:mechanics/etherium)Etherium$(). Instead of $(l:blocks/ores#vinteum)Vinteum Dust$(), it utilizes the sun's power.$(br2)Similar to the Obelisk, it can be supported by a multiblock structure, which builds up in levels. The structure's different levels are shown on the later pages.")
/*
            .addSimpleSpotlightPage(new ItemStack(AMItems.CELESTIAL_PRISM.get()), "The Celestial Prism cannot be obtained through crafting. Instead, it must be created through a special purification ritual. This ritual requires a multiblock structure, seen on the next page. Once built, throw a piece of $(l:blocks/ores#moonstone)Moonstone$() somewhere into the structure.")
            .addSimpleMultiblockPage("Purification Ritual", "Finally, to initiate the ritual, simply place some $(l:components/light)light$() on the bottom of the Obelisk.", AMMultiblocks.PURIFICATION_RITUAL)
*/
            .addSimpleMultiblockPage("Celestial Prism Chalk", AMMultiblocks.CELESTIAL_PRISM_CHALK)
            .addSimpleMultiblockPage("Celestial Prism Level 1", AMMultiblocks.CELESTIAL_PRISM_PILLARS_1)
            .addSimpleMultiblockPage("Celestial Prism Level 2", AMMultiblocks.CELESTIAL_PRISM_PILLARS_2)
            .addSimpleMultiblockPage("Celestial Prism Level 3", AMMultiblocks.CELESTIAL_PRISM_PILLARS_3)
            .addSimpleMultiblockPage("Celestial Prism Level 4", AMMultiblocks.CELESTIAL_PRISM_PILLARS_4)
            .build()
            .addEntry("flowers", "Flowers", new ItemStack(AMItems.AUM.get()))
            .addSimpleTextPage("A variety of flowers can be found scattered across the world.")
            .addSpotlightPage(new ItemStack(AMItems.AUM.get())).setText("A flower with healing properties, Aum is a component in many spells. It can be found in forests.").setAnchor("aum").build()
            .addSpotlightPage(new ItemStack(AMItems.CERUBLOSSOM.get())).setText("Cerublossom flowers thrive in lush, green environments. They were one of the first magical plants discovered, being used in potions and light-focused spells. A short while after its discovery, it was also noted that the Cerublossom could be used as part of the $(l:items/purified_vinteum_dust)purification process for Vinteum Dust$().").setAnchor("cerublossom").build()
            .addSpotlightPage(new ItemStack(AMItems.DESERT_NOVA.get())).setText("Desert Novas grow in dry conditions. The Nova has extraordinary magical properties for a desert plant, and is highly sought after. It is one of the two plants used in the $(l:items/purified_vinteum_dust)purification process of Vinteum Dust$().").setAnchor("desert_nova").build()
            .addSpotlightPage(new ItemStack(AMItems.TARMA_ROOT.get())).setText("Lighter than it looks, Tarma Root grows in mountain biomes. It is an ingredient in spells and air essences.").setAnchor("tarma_root").build()
            .addSpotlightPage(new ItemStack(AMItems.WAKEBLOOM.get())).setText("Growing on the surface of water in warm climates, Wakebloom is used in water-based spells frequently, as well as being a component in water affinity essences.").setAnchor("wakebloom").build()
            .addSimpleDoubleRecipePage("crafting", ArsMagicaApi.id("pink_dye_from_aum"), ArsMagicaApi.id("blue_dye_from_cerublossom"))
            .addSimpleDoubleRecipePage("crafting", ArsMagicaApi.id("red_dye_from_desert_nova"), ArsMagicaApi.id("brown_dye_from_tarma_root"))
            .addSimpleRecipePage("crafting", ArsMagicaApi.id("magenta_dye_from_wakebloom"))
            .build()
/*
            .addEntry("inlays", "Inlays", new ItemStack(AMItems.IRON_INLAY.get()))
            .addSimpleTextPage("Inlays are special, magically-enhanced rail variants. While their true purpose is currently unknown, they have already made themselves very useful for summoning bosses.")
            .addSimpleDoubleRecipePage("crafting", AMItems.IRON_INLAY.getId(), AMItems.REDSTONE_INLAY.getId())
            .addSimpleRecipePage("crafting", AMItems.GOLD_INLAY.getId())
            .build()
*/
            .addEntry("inscription_table", "Inscription Table", new ItemStack(AMItems.INSCRIPTION_TABLE.get()))
            .addSimpleTextPage("Using this table with a Book and Quill, you can compose spells of incredible power.$(br2)You will see what skills you know at the top in the Source Region. You can then drag shapes and modifiers to the brown $(l:mechanics/shape_groups)Shape Group$() squares, and drag components to the gray Spell Grammar section at the bottom to lay out a spell.")
            .addSimpleTextPage("Once satisfied with the spell, you can write the spells onto that Book & Quill. This book can then be placed on a $(l:mechanics/crafting_altar)Crafting Altar's$() lectern, and will guide you through the process needed to make the spell.$(br2)The book is worth reading too, as it contains a recap of the spell you will be creating, a materials list, and an $(l:affinities/affinities)affinity$() breakdown.")
            .addSimpleRecipePage("crafting", AMItems.INSCRIPTION_TABLE.getId())
            .build()
            .addEntry("obelisk", "Obelisk", new ItemStack(AMItems.OBELISK.get()))
            .addSimpleTextPage("The Obelisk is a runed block that is used to generate neutral $(l:mechanics/etherium)Etherium$() from $(l:blocks/ores#vinteum)vinteum dust$() (or blocks).$(br2)To boost its power, it can optionally be supported by a multiblock structure, which builds up in levels.")
            .addSimpleRecipePage("crafting", AMItems.OBELISK.getId())
            .addSimpleMultiblockPage("Obelisk Chalk", AMMultiblocks.OBELISK_CHALK)
            .addSimpleMultiblockPage("Obelisk Pillars", AMMultiblocks.OBELISK_PILLARS)
            .build()
            .addEntry("occulus", "Occulus", new ItemStack(AMItems.OCCULUS.get()))
            .addSimpleTextPage("A gateway to the stars, the Occulus shows you your innermost self. Here, you can spend skill points to unlock new skills.")
            .addSimpleRecipePage("crafting", AMItems.OCCULUS.getId())
            .build()
            .addEntry("ores", "Ores", new ItemStack(AMItems.CHIMERITE_ORE.get()))
            .addSimpleTextPage("A variety of ores can be found scattered across the world.")
            .addSpotlightPage(new ItemStack(AMItems.CHIMERITE_ORE.get())).setText("Found where the stones shift, Chimerite is used in spells that require a great deal of alternation or illusion.").setAnchor("chimerite").build()
            .addSpotlightPage(new ItemStack(AMItems.TOPAZ_ORE.get())).setText("Found in the deepest depths or on the highest peaks, Topaz is commonly used as an entry level magical crystal.").setAnchor("topaz").build()
            .addSpotlightPage(new ItemStack(AMItems.VINTEUM_ORE.get())).setText("Found relatively common in most layers of the world, Vinteum Dust quickly proved itself a viable magical resource.").setAnchor("vinteum").build()
            .addSpotlightPage(new ItemStack(AMItems.MOONSTONE_ORE.get())).setText("Rarely found in meteorites that crashed upon the world a long time ago, Moonstone is a celestial material with extraordinary magical powers.").setAnchor("moonstone").build()
            .addSpotlightPage(new ItemStack(AMItems.SUNSTONE_ORE.get())).setText("Sunstone gems were formed over thousands of years inside patches of obsidian touching the nether's lava oceans. Despite this, they are not fire-resistant, and great care must be taken to obtain them.").setAnchor("sunstone").build()
            .addSimpleDoubleRecipePage("crafting", AMItems.CHIMERITE_BLOCK.getId(), AMItems.CHIMERITE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.TOPAZ_BLOCK.getId(), AMItems.TOPAZ.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.VINTEUM_BLOCK.getId(), AMItems.VINTEUM_DUST.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.MOONSTONE_BLOCK.getId(), AMItems.MOONSTONE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.SUNSTONE_BLOCK.getId(), AMItems.SUNSTONE.getId())
            .build()
            .addEntry("vinteum_torch", "Vinteum Torch", new ItemStack(AMItems.VINTEUM_TORCH.get()))
            .addSimpleTextPage("Vinteum Torches are crafted in a simplistic way, and glow with the same brightness as a standard torch. They are just an aesthetic alternative.")
            .addSimpleRecipePage("crafting", AMItems.VINTEUM_TORCH.getId())
            .build()
            .addEntry("witchwood", "Witchwood", new ItemStack(AMItems.WITCHWOOD_LOG.get()))
            .addSimpleTextPage("Witchwood is a rare wood type with extraordinary magical properties, only found in dark forests.$(br2)It can be manufactured into the usual wooden planks, slabs, stairs and other blocks.")
            .addSimpleDoubleRecipePage("crafting", AMItems.WITCHWOOD.getId(), AMItems.STRIPPED_WITCHWOOD.getId())
            .addSimpleRecipePage("crafting", AMItems.WITCHWOOD_PLANKS.getId(), "Witchwood planks and stairs make a great structure material for the $(l:mechanics/crafting_altar)Crafting Altar$().")
            .addSimpleDoubleRecipePage("crafting", AMItems.WITCHWOOD_SLAB.getId(), AMItems.WITCHWOOD_STAIRS.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.WITCHWOOD_FENCE.getId(), AMItems.WITCHWOOD_FENCE_GATE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.WITCHWOOD_DOOR.getId(), AMItems.WITCHWOOD_TRAPDOOR.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.WITCHWOOD_BUTTON.getId(), AMItems.WITCHWOOD_PRESSURE_PLATE.getId())
            .build()
            .addEntry("wizards_chalk", "Wizard's Chalk", new ItemStack(AMItems.WIZARDS_CHALK.get()))
            .addSimpleTextPage("Sometimes, it is necessary to draw markings on the floor in order to perform or boost certain rituals. The Wizard's Chalk allows for exactly that.")
            .addSimpleRecipePage("crafting", AMItems.WIZARDS_CHALK.getId())
            .build()
            .build();
        builder.addCategory("items", "Items", "", new ItemStack(AMItems.PURIFIED_VINTEUM_DUST.get()))
            .setSortnum(2)
            .addEntry("affinity_tome", "Affinity Tomes", affinityTome(affinityRegistry, Affinity.NONE))
            .addSimpleTextPage("Affinity Tomes are powerful artifacts that can be found very rarely in loot chests. They come in eleven variants, one for each $(l:affinities/affinities)affinity$() plus a special none-type variant. Using an Affinity Tome will give you a noteable boost in its affinity, while reducing your shift into all other affinities. They are the only means currently known to break an affinity lock.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.WATER), "Water affinity tomes can be found in underwater structures, such as Underwater Ruins or Shipwreck treasures.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.FIRE), "Fire affinity tomes can be found in Bastion treasures, guarded by Piglins alongside their other valuables. If you are very lucky, you might also find one in a Nether Fortress.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.EARTH), "Earth affinity tomes can be found where miners left their remains. Thus, Abandoned Mineshafts or Dungeons are the best locations to start searching.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.AIR), "Air affinity tomes can be found as a reward in Trial Chambers, home of the Breezes.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.ICE), "Ice affinity tomes can be found in cold biomes. Igloo laboratories should be double-checked!")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.LIGHTNING), "Lightning affinity tomes can be found in dry and arid places. Most of them have been hoarded in Desert Pyramids.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.NATURE), "Nature affinity tomes can be found hidden in lush places, such as Jungle Temples.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.LIFE), "Life affinity tomes have a special way of appearing. Any place other affinity tomes can spawn at, you have a very low chance to encounter a life tome instead.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.ARCANE), "Arcane affinity tomes are found in the homes of the sorcery-wielding Illagers. Woodland Mansions and, to a lesser degree, Pillager Outposts are prime examples of that.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, AMMagic.ENDER), "Ender affinity tomes can be found in the only End structure known to yield any loot - the End City. Some have also managed to bring these tomes back into Stronghold libraries.")
            .addSimpleSpotlightPage(affinityTome(affinityRegistry, Affinity.NONE), "None-type affinity tomes are found in the most nihilistic and dangerous structures of all: the Deep Dark's Ancient Cities.$(br2)These tomes are special because they do not have an affinity associated with them. As such, they lower your shift into all affinities, without buffing anything.")
            .build()
            .addEntry("arcane_ash", "Arcane Ash", new ItemStack(AMItems.ARCANE_ASH.get()))
            .addSimpleTextPage("Created by burning $(l:items/arcane_compound)Arcane Compounds$() in a furnace, Arcane Ash's magical capabilities have made it a cornerstone of advanced magic.")
            .addSimpleRecipePage("smelting", AMItems.ARCANE_ASH.getId())
            .build()
            .addEntry("arcane_compound", "Arcane Compound", new ItemStack(AMItems.ARCANE_COMPOUND.get()))
            .addSimpleTextPage("A combination of materials from multiple worlds, the Arcane Compound forms the base resource needed to get $(l:items/arcane_ash)Arcane Ash$().")
            .addSimpleRecipePage("crafting", AMItems.ARCANE_COMPOUND.getId())
            .build()
            .addEntry("mage_armor", "Mage Armor", new ItemStack(AMItems.MAGE_HELMET.get()))
            .addSimpleTextPage("Mages tend to prefer a special set of armor that has low durability, but repairs itself using mana. More wealthy mages use Battlemage Armor, which is a bit more durable and more protective.")
            .addSimpleDoubleRecipePage("crafting", AMItems.MAGE_HELMET.getId(), AMItems.MAGE_CHESTPLATE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.MAGE_LEGGINGS.getId(), AMItems.MAGE_BOOTS.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.BATTLEMAGE_HELMET.getId(), AMItems.BATTLEMAGE_CHESTPLATE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.BATTLEMAGE_LEGGINGS.getId(), AMItems.BATTLEMAGE_BOOTS.getId())
            .build()
            .addEntry("magitech_goggles", "Magitech Goggles", new ItemStack(AMItems.MAGITECH_GOGGLES.get()))
            .addSimpleTextPage("The Magitech Goggles are a fancy piece of armor that can be equipped in the helmet slot, or the head slot if Curios is installed. When wearing the Goggles, $(l:mechanics/etherium)Etherium$() connections will become visible to you.")
            .addSimpleRecipePage("crafting", AMItems.MAGITECH_GOGGLES.getId())
            .build()
            .addEntry("mana_food", "Mana Food", new ItemStack(AMItems.MANA_CAKE.get()))
            .addSimpleTextPage("The Mana Martini (shaken, not stirred) will give you burnout reduction, while the Mana Cake improves your mana regeneration. But is it a lie?")
            .addSimpleDoubleRecipePage("crafting", AMItems.MANA_CAKE.getId(), AMItems.MANA_MARTINI.getId())
            .build()
            .addEntry("purified_vinteum_dust", "Purified Vinteum Dust", new ItemStack(AMItems.PURIFIED_VINTEUM_DUST.get()))
            .addSimpleTextPage("By adding $(l:items/arcane_ash)Arcane Ash$() to strengthen its magical properties, $(l:blocks/flowers#cerublossom)Cerublossoms$() as a catalyst and $(l:blocks/flowers#desert_nova)Desert Novas$() to release instability, $(l:blocks/ores#vinteum)Vinteum Dust$() can be put into a purified state with much higher capacity for magic.")
            .addSimpleRecipePage("crafting", AMItems.PURIFIED_VINTEUM_DUST.getId())
            .build()
            .addEntry("rune_bag", "Rune Bag", new ItemStack(AMItems.RUNE_BAG.get()))
            .addSimpleTextPage("The Rune Bag can hold runes, a stack of each color, to save inventory space.")
            .addSimpleRecipePage("crafting", AMItems.RUNE_BAG.getId())
            .build()
            .addEntry("runes", "Runes", new ItemStack(AMItems.BLANK_RUNE.get()))
            .addSimpleTextPage("Runes are the basic building parts of spells. When combined with multiple other items in the right combination, a magical spell scroll can be created.")
            .addSimpleRecipePage("crafting", AMItems.BLANK_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.BLACK_RUNE.getId(), AMItems.BLUE_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.BROWN_RUNE.getId(), AMItems.CYAN_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.GRAY_RUNE.getId(), AMItems.GREEN_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.LIGHT_BLUE_RUNE.getId(), AMItems.LIGHT_GRAY_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.LIME_RUNE.getId(), AMItems.MAGENTA_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.ORANGE_RUNE.getId(), AMItems.PINK_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.PURPLE_RUNE.getId(), AMItems.RED_RUNE.getId())
            .addSimpleDoubleRecipePage("crafting", AMItems.WHITE_RUNE.getId(), AMItems.YELLOW_RUNE.getId())
            .build()
            .addEntry("spell_book", "Spell Book", new ItemStack(AMItems.SPELL_BOOK.get()))
            .addSimpleTextPage("The spell book is an easy way to store many spells in one slot. When right-clicking the spell book, the currently selected spell is executed. By shift-scrolling, the currently selected spell can be changed.")
            .addSimpleTextPage("When shift-right clicking the spell book, its storage is opened. That way, spells can be put into and out of the book, and be reorganized inside it.$(br2)The left part of the book is the book's hotbar, so to speak. If you want to use a spell that is not in the hotbar, you will have to open the book and move it there first.")
            .addSimpleRecipePage("crafting", AMItems.SPELL_BOOK.getId())
            .build()
            .addEntry("spell_parchment", "Spell Parchment", new ItemStack(AMItems.SPELL_PARCHMENT.get()))
            .addSimpleTextPage("Any mage that wants to cast spells without the need of some sort of staff or wand needs something to write the spell down on. This has worked for thousands of years, and surprisingly nothing more effective has been invented yet.")
            .addSimpleRecipePage("crafting", AMItems.SPELL_PARCHMENT.getId())
            .build()
            .build();
/*
        builder.addCategory("entities", "Entities", "", new ItemStack(AMItems.MANA_CREEPER_SPAWN_EGG.get()))
            .setSortnum(3)
            .addEntry("mana_creeper", "Mana Creeper", new ItemStack(AMItems.MANA_CREEPER_SPAWN_EGG.get()))
            .addSimpleTextPage("Occasionally, you will find special creepers in the world that are blue instead of green. These creepers are called Mana Creepers. They are just as dangerous as normal creepers, but in addition, after they explode, they create a Mana Vortex that saps your mana for a while.")
            .addEntityPage(AMEntities.MANA_CREEPER.getId()).setText("Mana creepers drop $(l:blocks/ores#vinteum)Vinteum Dust$() when killed.").build()
            .build()
            .addSubCategory("bosses", "Bosses", "", new ItemStack(AMItems.WATER_GUARDIAN_SPAWN_EGG.get()))
            .addEntry("water_guardian", "Water Guardian", new ItemStack(AMItems.WATER_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Water Guardian is a unique being. This trickster will create copies of itself, attacking from ambush if you are fooled by the decoy. If you come too close, it will begin to spin and use its tentacles to damage you. If you keep your distance, it will attack you with water projectiles.")
            .addEntityPage(AMEntities.WATER_GUARDIAN.getId()).setText("Recommended magic level: 10").build()
            .addSimpleMultiblockPage("Water Guardian Ritual", AMMultiblocks.WATER_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Water Guardian, build the structure seen on the previous page in any water-based biome. Then, drop a water bucket and a boat at the center of the circle.$(br2)The Water Guardian takes double damage from $(l:components/lightning_damage)lightning$(), and is immune to $(l:components/drowning_damage)drowning$(). If the guardian has copies, they will absorb the damage and be destroyed in the process.")
            .build()
            .addEntry("fire_guardian", "Fire Guardian", new ItemStack(AMItems.FIRE_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Fire Guardian is a living inferno from the depths of the nether. After setting its surroundings ablaze, the fire makes it hard to still see the guardian, allowing it to attack from nowhere. It will also melt down your armor in seconds. Prepare for a fight!")
            .addEntityPage(AMEntities.FIRE_GUARDIAN.getId()).setText("Recommended magic level: 90").build()
            .addSimpleMultiblockPage("Fire Guardian Ritual", AMMultiblocks.FIRE_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Fire Guardian, build the structure seen on the previous page anywhere in the nether. Then, drop a water essence at the center of the circle.$(br2)The Fire Guardian takes double damage from $(l:components/drowning_damage)drowning$(), and is immune to $(l:components/fire_damage)fire$() and $(l:components/frost_damage)frost$().")
            .build()
            .addEntry("earth_guardian", "Earth Guardian", new ItemStack(AMItems.EARTH_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The heavy Earth Guardian will rip rocks from the earth to hurl them at you. If you get close to it, it will beat you up, or pound the earth so hard that you are blown back by a shockwave.")
            .addEntityPage(AMEntities.EARTH_GUARDIAN.getId()).setText("Recommended magic level: 20").build()
            .addSimpleMultiblockPage("Earth Guardian Ritual", AMMultiblocks.EARTH_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Earth Guardian, build the structure seen on the previous page in any biome. Then, drop an emerald, a piece of chimerite and a topaz at the center of the circle.$(br2)The Earth Guardian takes double damage from $(l:components/drowning_damage)drowning$() and $(l:components/frost_damage)frost$(), and is immune to $(l:components/fire_damage)fire$() and $(l:components/lightning_damage)lightning$().")
            .build()
            .addEntry("air_guardian", "Air Guardian", new ItemStack(AMItems.AIR_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Air Guardian is a being of wind, and makes heavy use of knockback-based attacks. On top of that, its whirlwinds can blow off your armor if you are not careful enough. It would be foolish to go into battle without some form of falling protection.")
            .addEntityPage(AMEntities.AIR_GUARDIAN.getId()).setText("Recommended magic level: 30").build()
            .addSimpleMultiblockPage("Air Guardian Ritual", AMMultiblocks.AIR_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Air Guardian, build the structure seen on the previous page in any biome above Y 128. Then, drop a tarma root at the center of the circle.$(br2)The Air Guardian takes double damage from $(l:components/lightning_damage)lightning$(), and is immune to fall damage or physical projectile attacks, such as arrows.")
            .build()
            .addEntry("ice_guardian", "Ice Guardian", new ItemStack(AMItems.ICE_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The super-cold Ice Guardian does not bother with ranged attacks. Instead, it launches his arm to capture its target, then reel it in to harm it in combat. Similar to its Earth counterpart, the Ice Guardian can and will create shockwaves.")
            .addEntityPage(AMEntities.ICE_GUARDIAN.getId()).setText("Recommended magic level: 60").build()
            .addSimpleMultiblockPage("Ice Guardian Ritual", AMMultiblocks.ICE_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Ice Guardian, build the structure seen on the previous page in any frozen or snowy biome. Then, summon a snow golem at the center of the circle.$(br2)The Ice Guardian takes double damage from $(l:components/fire_damage)fire$(), and is immune to $(l:components/frost_damage)frost$().")
            .build()
            .addEntry("lightning_guardian", "Lightning Guardian", new ItemStack(AMItems.LIGHTNING_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Lightning Guardian is as unpredictable as it is powerful. Its feared telekinetic attack uses a massive amount of concentrated static electricity, which leaves the unfortunate victim open to several other effects. On rare occasions, it manages to mess with its victim's synapses, making their movement really awkward.")
            .addEntityPage(AMEntities.LIGHTNING_GUARDIAN.getId()).setText("Recommended magic level: 80").build()
            .addSimpleMultiblockPage("Lightning Guardian Ritual", AMMultiblocks.LIGHTNING_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Lightning Guardian, build the structure seen on the previous page in any biome. Then, wait for a storm.$(br2)The Lightning Guardian takes double damage from $(l:components/drowning_damage)drowning$(), and will be healed by incoming $(l:components/lightning_damage)lightning$() damage.")
            .build()
            .addEntry("nature_guardian", "Nature Guardian", new ItemStack(AMItems.NATURE_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Nature Guardian is a fearsome opponent. Boasting a deadly scythe and the skill to use it, only the most powerful or foolish would seek it out. It is just as dangerous at range as in melee, if not more.")
            .addEntityPage(AMEntities.NATURE_GUARDIAN.getId()).setText("Recommended magic level: 70").build()
            .addEntityPage(AMEntities.DRYAD.getId()).setText("Dryads spawn in forests. They can be lured with saplings and boost nearby plants' growth.").build()
            .addSimpleTextPage("Unlike all other bosses, the Nature Guardian appears to guard dryads when enough are slain in quick succession. Nature can be harsh, but it will not tolerate a massacre.$(br2)The Nature Guardian takes double damage from $(l:components/fire_damage)fire$() and $(l:components/frost_damage)frost$(), and will be healed instead of $(l:components/drowning_damage)drowned$().")
            .build()
            .addEntry("life_guardian", "Life Guardian", new ItemStack(AMItems.LIFE_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("The Life Guardian does not attack much on its own, though it may decide to nauseate you. It generally prefers to let others do the fighting for it, infusing them with its tremendous power. In return, the others will often lay down their lives for the guardian's protection. The Life Guardian is a different kind of fight than the other bosses.")
            .addEntityPage(AMEntities.LIFE_GUARDIAN.getId()).setText("Recommended magic level: 50").build()
            .addSimpleMultiblockPage("Life Guardian Ritual", AMMultiblocks.LIFE_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Life Guardian, build the structure seen on the previous page in any biome. Then, during a full moon night, kill a villager child at the center of the circle.$(br2)The Life Guardian cannot be damaged directly. Defeat its minions, and the guardian will be hurt.")
            .build()
            .addEntry("arcane_guardian", "Arcane Guardian", new ItemStack(AMItems.ARCANE_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("A mage of ancient times, the Arcane Guardian has access to a wide variety of spells. From attacks to buffs to debuffs to self-healing, prepare for a magical duel that will test your counter-spell knowledge.")
            .addEntityPage(AMEntities.ARCANE_GUARDIAN.getId()).setText("Recommended magic level: 40").build()
            .addSimpleMultiblockPage("Arcane Guardian Ritual", AMMultiblocks.ARCANE_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Arcane Guardian, build the structure seen on the previous page in any biome. Note that the lectern must face towards the sunrise (east). Then, drop an arcane compendium at the center of the circle.$(br2)The Arcane Guardian has no particular strengths or weaknesses. Attack it in any way you like. Melee is generally a bad idea, though.")
            .build()
            .addEntry("ender_guardian", "Ender Guardian", new ItemStack(AMItems.ENDER_GUARDIAN_SPAWN_EGG.get()))
            .addSimpleTextPage("Tales are told in hushed whispers of the true Ender Guardian. Of eyes glowing with an abhorrent light, and scaly skin as black as a moonless night. Alas, the tales of this waking nightmare are vague at best and always shrouded in the veil of speculation. The only thing known for sure are its fast, teleport-heavy and extremely powerful attacks.")
            .addEntityPage(AMEntities.ENDER_GUARDIAN.getId()).setText("Recommended magic level: 100").build()
            .addSimpleMultiblockPage("Ender Guardian Ritual", AMMultiblocks.ENDER_GUARDIAN_SPAWN_RITUAL)
            .addSimpleTextPage("To summon the Ender Guardian, build the structure seen on the previous page anywhere in the end. Then, drop an ender eye into the Black Aurem.$(br2)A being of dark Ender magic, $(l:components/magic_damage)light Arcane magic$() will hurt it double. As an inhabitant of the End, the same is true for $(l:components/drowning_damage)water$().")
            .build()
            .build();
*/
        TranslatedCategoryBuilder shapes = builder
            .addCategory("shapes", "Shapes", "", ArsMagicaApi.MOD_ID + ":textures/skill/touch.png")
            .setSortnum(4);
        TranslatedCategoryBuilder components = builder
            .addCategory("components", "Components", "", ArsMagicaApi.MOD_ID + ":textures/skill/dig.png")
            .setSortnum(5);
        TranslatedCategoryBuilder modifiers = builder
            .addCategory("modifiers", "Modifiers", "", ArsMagicaApi.MOD_ID + ":textures/skill/target_non_solid.png")
            .setSortnum(6);
        for (SpellPart spellPart : ArsMagicaApi.spellPartRegistry()) {
            if (spellPart == AMSpells.MELT_ARMOR.get()) continue;
            if (spellPart == AMSpells.NAUSEA.get()) continue;
            if (spellPart == AMSpells.SCRAMBLE_SYNAPSES.get()) continue;
            TranslatedCategoryBuilder b = spellPart.isShape() ? shapes : spellPart.isComponent() ? components : modifiers;
            ResourceLocation id = ArsMagicaApi.spellPartRegistry().getKey(spellPart);
            TranslatedEntryBuilder entry = b.addEntry(id.getPath(), Util.makeDescriptionId("skill", id) + ".name", id.getNamespace() + ":textures/skill/" + id.getPath() + ".png")
                .setAdvancement(ArsMagicaApi.id("book/" + id.getPath()));
            entry.addSimpleTextPage(entry.getLangKey(0) + ".text");
            if (spellPart == AMSpells.CHAIN.get()) {
                entry.addSimpleTextPage(entry.getLangKey(1) + ".text");
            }
            if (spellPart == AMSpells.SUMMON.get()) {
                entry.addSimpleTextPage(entry.getLangKey(1) + ".text");
                entry.addSimpleTextPage(entry.getLangKey(2) + ".text");
            }
            entry.addPage(new SpellPartPageBuilder(entry, id)).build();
            entry.build();
        }
        shapes.build();
        components.build();
        modifiers.build();
        TranslatedCategoryBuilder talents = builder.addCategory("talents", "Talents", "", ArsMagicaApi.MOD_ID + ":textures/skill/mana_regeneration_boost_1.png")
            .setSortnum(7);
        for (ResourceKey<Skill> talent : AMMagic.TALENTS) {
            ResourceLocation id = talent.location();
            TranslatedEntryBuilder entry = talents.addEntry(id.getPath(), Util.makeDescriptionId("skill", id) + ".name", id.getNamespace() + ":textures/skill/" + id.getPath() + ".png")
                .setAdvancement(ArsMagicaApi.id("book/" + id.getPath()));
            entry.addSimpleTextPage(entry.getLangKey(0) + ".text").build();
        }
        talents.build();
        TranslatedCategoryBuilder affinities = builder.addCategory("affinities", "Affinities", "", affinityEssence(affinityRegistry, AMMagic.WATER))
            .setSortnum(8);
        affinities.addEntry("affinities", "Affinities", affinityTome(affinityRegistry, Affinity.NONE))
            .setPriority(true)
            .addSimpleTextPage("Affinities are magical elements of sorts. All components (excluding some of the very powerful, reality-bending ones) have an affinity that is associated with them. That means that if you use spells with a certain affinity a lot, you will shift into that affinity.")
            .addSimpleTextPage("Shifting into an affinity bears unique side effects, called abilities. Each affinity has different abilities. You can read about the abilities for each affinity in the dedicated chapters for them.$(br2)If you wish to see your current shift into an affinity, you can view your shifts in the Affinity tab of the $(l:blocks/occulus)Occulus$().")
            .addSimpleTextPage("If you fully shift into an affinity, your affinities become locked. This means that your current affinity shifts are permanent and cannot be changed.$(br2)The only way to unlock them again is by using an $(l:items/affinity_tome)Affinity Tome$().")
            .addSimpleTextPage("There is also an affinity essence for each affinity, which is used in intermediate crafting for spell parts associated with that affinity. Affinity essences must be obtained from bosses, but can be duplicated through crafting later.")
            .build();
        Map<ResourceKey<Affinity>, List<Holder.Reference<Ability>>> abilitiesByAffinity = lookupProvider
            .lookupOrThrow(AMRegistries.ABILITY)
            .listElements()
            .sorted(Comparator.comparing(e -> e.key().location(), ResourceLocation::compareNamespaced))
            .sorted(Comparator.comparing(e -> AMAbilityProvider.PATCHOULI_ABILITY_DATA.get(e.getKey()).bounds().min().orElse(0.)))
            .collect(Collectors.groupingBy(e -> AMAbilityProvider.PATCHOULI_ABILITY_DATA.get(e.getKey()).affinity()));
        for (Holder<Affinity> affinity : affinityRegistry.listElements().toList()) {
            ResourceKey<Affinity> key = affinity.getKey();
            ResourceLocation id = key.location();
            if (!id.getNamespace().equals(builder.getId().getNamespace()) || id.equals(Affinity.NONE.location())) continue;
            TranslatedEntryBuilder entry = affinities.addEntry(id.getPath(), Util.makeDescriptionId("affinity", id), affinityEssence(affinityRegistry, key));
            entry.addSimpleTextPage(entry.getLangKey(0) + ".text");
            entry.addSimpleRecipePage("crafting", id.withPrefix("affinity_essence_"));
            for (Holder.Reference<Ability> ability : abilitiesByAffinity.get(key)) {
                String translationKey = Util.makeDescriptionId("ability", ability.key().location());
                entry.addSimpleTextPage(translationKey + ".description", translationKey + ".name");
            }
            entry.build();
        }
        affinities.build();
        builder.build(consumer);
    }

    private ItemStack affinityEssence(HolderLookup<Affinity> affinities, ResourceKey<Affinity> affinity) {
        return DataComponentNamedItem.set(AMItems.AFFINITY_ESSENCE.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(affinity));
    }

    private ItemStack affinityTome(HolderLookup<Affinity> affinities, ResourceKey<Affinity> affinity) {
        return DataComponentNamedItem.set(AMItems.AFFINITY_TOME.toStack(), AMDataComponents.AFFINITY.get(), affinities.getOrThrow(affinity));
    }

    private static class SpellPartPageBuilder extends AbstractPageBuilder<SpellPartPageBuilder> {
        private final ResourceLocation part;

        private SpellPartPageBuilder(EntryBuilder<?, ?, ?> builder, ResourceLocation part) {
            super(SpellPartPage.ID, builder);
            this.part = part;
        }

        @Override
        protected void serialize(JsonObject jsonObject) {
            jsonObject.addProperty("part", part.toString());
        }
    }
}
