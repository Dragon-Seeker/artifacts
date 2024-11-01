package artifacts.integration.client;

import artifacts.client.item.renderer.ArtifactRenderer;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ClientEquipmentIntegrationUtils {

    private static final Map<String, BaseClientEquipmentIntegration> INTEGRATIONS = new LinkedHashMap<>();

    public static void registerIntegration(BaseClientEquipmentIntegration integration) {
        var name = integration.name();

        if (INTEGRATIONS.containsKey(name)) throw new IllegalStateException("Duplicate Equipment Integration detected! [Name: " + name + "]");

        INTEGRATIONS.put(name, integration);
    }

    public static void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        for (var value : INTEGRATIONS.values()) {
            value.registerArtifactRenderer(item, rendererSupplier);
        }
    }

    @Nullable
    public static ArtifactRenderer getArtifactRenderer(Item item) {
        for (var value : INTEGRATIONS.values()) {
            var renderer = value.getArtifactRenderer(item);

            if (renderer != null) return renderer;
        }

        return null;
    }
}
