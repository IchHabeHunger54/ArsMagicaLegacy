package at.minecraftschurli.arsmagicalegacy.datagen;

import at.minecraftschurli.arsmagicalegacy.api.ArsMagicaApi;
import at.minecraftschurli.arsmagicalegacy.init.AMCreativeTabs;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.BookBuilder;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.PatchouliBookProvider;
import com.github.minecraftschurlimods.easydatagenlib.mods.patchouli.translated.TranslatedBookBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

final class AMPatchouliBookProvider extends PatchouliBookProvider {
    private final BiConsumer<String, String> translationConsumer;

    public AMPatchouliBookProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, BiConsumer<String, String> translationConsumer, boolean includeClient, boolean includeServer) {
        super(output, lookupProvider, ArsMagicaApi.MOD_ID, includeClient, includeServer);
        this.translationConsumer = translationConsumer;
    }

    @Override
    protected void addBooks(HolderLookup.Provider lookupProvider, Consumer<BookBuilder<?, ?, ?>> consumer) {
        TranslatedBookBuilder builder = createBookBuilder("arcane_compendium", "Arcane Compendium", "A renewed look into Minecraft with a splash of magic...", translationConsumer, lookupProvider)
            .setBookTexture(ResourceLocation.fromNamespaceAndPath("patchouli", "textures/gui/book_purple.png"))
            .setCreativeTab(AMCreativeTabs.MAIN.getId())
            .setModel(ArsMagicaApi.modLoc("arcane_compendium"))
            .setVersion("1");
        builder.build(consumer);
    }
}
