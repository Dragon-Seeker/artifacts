package artifacts.neoforge;

import artifacts.Artifacts;
import artifacts.ArtifactsClient;
import artifacts.client.item.ArtifactRenderers;
import artifacts.neoforge.client.ArmRenderHandler;
import artifacts.neoforge.client.ArtifactCooldownOverlayRenderer;
import artifacts.neoforge.client.HeliumFlamingoOverlayRenderer;
import artifacts.neoforge.client.UmbrellaArmPoseHandler;
import artifacts.registry.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiOverlaysEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

import static io.wispforest.accessories.Accessories.MODID;

@Mod.EventBusSubscriber(modid = MODID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ArtifactsNeoForgeClient {

    public ArtifactsNeoForgeClient() {
        ArtifactsClient.init();

        IEventBus modBus = ModLoadingContext.get().getActiveContainer().getEventBus();

        modBus.addListener(this::onClientSetup);
        modBus.addListener(this::onRegisterGuiOverlays);

        ArmRenderHandler.setup();
    }

    public void onClientSetup(FMLClientSetupEvent event) {
        ArtifactsClient.init();
        event.enqueueWork(
                () -> ItemProperties.register(
                        ModItems.UMBRELLA.get(),
                        Artifacts.id("blocking"),
                        (stack, level, entity, seed) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1 : 0
                )
        );
        ArtifactRenderers.register();
        UmbrellaArmPoseHandler.setup();
    }

    public void onRegisterGuiOverlays(RegisterGuiOverlaysEvent event) {
        event.registerAbove(VanillaGuiOverlay.AIR_LEVEL.id(), Artifacts.id("helium_flamingo_charge"), HeliumFlamingoOverlayRenderer::render);
        event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), Artifacts.id("artifact_cooldowns"), ArtifactCooldownOverlayRenderer::render);
    }
}
