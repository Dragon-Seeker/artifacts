package artifacts.fabric;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.fabric.event.SwimEventsFabric;
import artifacts.fabric.integration.CompatHandler;
import artifacts.fabric.registry.ModFeatures;
import artifacts.fabric.registry.ModLootTables;
import artifacts.item.wearable.WearableArtifactItem;
import io.wispforest.accessories.api.AccessoriesAPI;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.registries.BuiltInRegistries;

public class ArtifactsFabric implements ModInitializer {

    public static final AttachmentType<SwimData> SWIM_DATA = AttachmentRegistry.createDefaulted(Artifacts.id("swim_data"), SwimData::new);

    @Override
    public void onInitialize() {
        Artifacts.init();
        registerTrinkets();

        SwimEventsFabric.register();
        ModFeatures.register();

        LootTableEvents.MODIFY.register((rm, lt, id, supplier, s) ->
                ModLootTables.onLootTableLoad(id, supplier));

        runCompatibilityHandlers();
    }

    private void registerTrinkets() {
        BuiltInRegistries.ITEM.stream()
                .forEach(item -> {
                    if(item instanceof WearableArtifactItem wearableArtifactItem){
                        AccessoriesAPI.registerAccessory(item, wearableArtifactItem);
                    }
                });
    }

    private void runCompatibilityHandlers() {
        FabricLoader.getInstance().getEntrypoints("artifacts:compat_handlers", CompatHandler.class).stream()
                .filter(handler -> FabricLoader.getInstance().isModLoaded(handler.getModId()))
                .forEach(handler -> {
                    String modName = FabricLoader.getInstance().getModContainer(handler.getModId())
                            .map(container -> container.getMetadata().getName())
                            .orElse(handler.getModId());
                    Artifacts.LOGGER.info("Running compat handler for " + modName);

                    handler.run();
                });
    }
}
