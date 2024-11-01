package artifacts.client;

import artifacts.item.ArtifactItem;
import artifacts.registry.ModDataComponents;
import net.minecraft.world.item.ItemStack;

public class CosmeticsHelper {

    public static boolean areCosmeticsToggledOffByPlayer(ItemStack stack) {
        Boolean enabled = stack.get(ModDataComponents.COSMETICS_ENABLED.get());
        return enabled != null && !enabled && !isCosmeticOnly(stack);
    }

    public static void toggleCosmetics(ItemStack stack) {
        if (!isCosmeticOnly(stack)) {
            stack.set(ModDataComponents.COSMETICS_ENABLED.get(), areCosmeticsToggledOffByPlayer(stack));
        }
    }

    private static boolean isCosmeticOnly(ItemStack stack) {
        return stack.getItem() instanceof ArtifactItem item && item.isCosmetic();
    }
}
