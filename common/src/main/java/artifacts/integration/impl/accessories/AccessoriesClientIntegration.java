package artifacts.integration.impl.accessories;

import artifacts.client.CosmeticsHelper;
import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.integration.client.BaseClientEquipmentIntegration;
import com.mojang.blaze3d.vertex.PoseStack;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class AccessoriesClientIntegration extends BaseClientEquipmentIntegration {

    public static final AccessoriesClientIntegration INSTANCE = new AccessoriesClientIntegration();

    @Override
    public void registerArtifactRenderer(Item item, Supplier<ArtifactRenderer> rendererSupplier) {
        AccessoriesRendererRegistry.registerRenderer(item, () -> new ArtifactAccessoryRenderer(rendererSupplier.get()));
    }

    @Override
    public @Nullable ArtifactRenderer getArtifactRenderer(Item item) {
        var renderer = AccessoriesRendererRegistry.getRender(item);
        if (renderer instanceof ArtifactAccessoryRenderer artifactAccessoryRenderer) {
            return artifactAccessoryRenderer.renderer();
        }
        return null;
    }

    @Override
    public String name() {
        return "accessories";
    }

    public record ArtifactAccessoryRenderer(ArtifactRenderer renderer) implements AccessoryRenderer {
        @Override
        public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (CosmeticsHelper.areCosmeticsToggledOffByPlayer(stack)) return;
            renderer.render(stack, reference.entity(), reference.slot(), matrices, multiBufferSource, light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
        }
    }
}
