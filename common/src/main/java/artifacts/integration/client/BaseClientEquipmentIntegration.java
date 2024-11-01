package artifacts.integration.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public abstract class BaseClientEquipmentIntegration {

    protected BaseClientEquipmentIntegration() {}

    public final void registerIntegration() {
        ClientEquipmentIntegrationUtils.registerIntegration(this);
    }

    public abstract void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier);

    @Nullable
    public abstract ArtifactRenderer getArtifactRenderer(Item item);

    public abstract String name();
}
