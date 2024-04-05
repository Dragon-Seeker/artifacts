package artifacts.neoforge.client;

import artifacts.Artifacts;
import artifacts.item.wearable.WearableArtifactItem;
import io.wispforest.accessories.api.AccessoriesCapability;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.gui.overlay.ExtendedGui;

import java.util.Optional;

public class ArtifactCooldownOverlayRenderer {

    @SuppressWarnings("unused")
    public static void render(ExtendedGui gui, GuiGraphics guiGraphics, float partialTick, int screenWidth, int screenHeight) {
        var minecraft = gui.getMinecraft();
        var entity = minecraft.getCameraEntity();
        if (!Artifacts.CONFIG.client.enableCooldownOverlay || !(entity instanceof Player player)) {
            return;
        }

        Optional.ofNullable(AccessoriesCapability.get(player)).ifPresent((AccessoriesCapability capability) -> {
            int y = screenHeight - 16 - 3;
            int cooldownOverlayOffset = Artifacts.CONFIG.client.cooldownOverlayOffset;
            int step = 20;
            int start = screenWidth / 2 + 91 + cooldownOverlayOffset;

            if (cooldownOverlayOffset < 0) {
                step = -20;
                start = screenWidth / 2 - 91 - 16 + cooldownOverlayOffset;
            }

            int k = 0;

            for (var reference : capability.getAllEquipped()) {
                var stack = reference.stack();

                if (!stack.isEmpty() && stack.getItem() instanceof WearableArtifactItem && player.getCooldowns().isOnCooldown(stack.getItem())) {
                    int x = start + step * k++;
                    guiGraphics.renderItem(player, stack, x, y, k + 1);
                    guiGraphics.renderItemDecorations(minecraft.font, stack, x, y);
                }
            }
        });
    }
}
