package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.component.SwimData;
import artifacts.config.ModConfig;
import artifacts.neoforge.event.ArtifactEventsForge;
import artifacts.neoforge.event.SwimEventsNeoForge;
import artifacts.neoforge.registry.ModItemsNeoForge;
import artifacts.neoforge.registry.ModLootModifiers;
import io.wispforest.accessories.Accessories;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.core.Registry;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@Mod(Artifacts.MOD_ID)
public class ArtifactsNeoForge {

    public static final AttachmentType<SwimData> SWIM_DATA_ATTACHMENT = Registry.register(
            NeoForgeRegistries.ATTACHMENT_TYPES,
            Accessories.of("swim_handler"),
            AttachmentType.builder(SwimData::new)
                    .build()
    );

    public ArtifactsNeoForge(IEventBus modBus) {
        Artifacts.init();

        registerConfig();

        ModLootModifiers.LOOT_MODIFIERS.register(modBus);
        modBus.addListener(this::onCommonSetup);

        ArtifactEventsForge.register();
        SwimEventsNeoForge.register();
    }

    private void registerConfig() {
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory(
                        (client, parent) -> AutoConfig.getConfigScreen(ModConfig.class, parent).get()
                )
        );
    }

    private void onCommonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModItemsNeoForge::register);
    }
}
