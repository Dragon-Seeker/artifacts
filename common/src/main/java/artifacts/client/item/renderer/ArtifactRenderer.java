package artifacts.client.item.renderer;

import io.wispforest.accessories.api.client.AccessoryRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;

public interface ArtifactRenderer extends AccessoryRenderer {

    static void followBodyRotations(final LivingEntity livingEntity, final HumanoidModel<LivingEntity> model) {
        EntityRenderer<? super LivingEntity> renderer = Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(livingEntity);

        if (renderer instanceof LivingEntityRenderer) {
            @SuppressWarnings("unchecked")
            LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>> livingRenderer = (LivingEntityRenderer<LivingEntity, EntityModel<LivingEntity>>) renderer;
            EntityModel<LivingEntity> entityModel = livingRenderer.getModel();

            if (entityModel instanceof HumanoidModel<LivingEntity> bipedModel) {
                bipedModel.copyPropertiesTo(model);
            }
        }
    }
}
