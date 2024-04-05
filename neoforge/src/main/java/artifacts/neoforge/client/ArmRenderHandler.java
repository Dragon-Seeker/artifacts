package artifacts.neoforge.client;

import net.neoforged.neoforge.client.event.RenderArmEvent;

public abstract class ArmRenderHandler {

    public static void setup() {
        //NeoForge.EVENT_BUS.addListener(EventPriority.LOW, ArmRenderHandler::onRenderArm);
    }

    public static void onRenderArm(RenderArmEvent event) {
//        if (!Artifacts.CONFIG.client.showFirstPersonGloves || event.isCanceled()) {
//            return;
//        }
//
//        InteractionHand hand = event.getArm() == event.getPlayer().getMainArm() ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
//
//        CuriosApi.getCuriosHelper().getCuriosHandler(event.getPlayer()).ifPresent(handler -> {
//            ICurioStacksHandler stacksHandler = handler.getCurios().get("hands");
//            if (stacksHandler != null) {
//                IDynamicStackHandler stacks = stacksHandler.getStacks();
//                IDynamicStackHandler cosmeticStacks = stacksHandler.getCosmeticStacks();
//
//                for (int slot = hand == InteractionHand.MAIN_HAND ? 0 : 1; slot < stacks.getSlots(); slot += 2) {
//                    ItemStack stack = cosmeticStacks.getStackInSlot(slot);
//                    if (stack.isEmpty() && stacksHandler.getRenders().get(slot)) {
//                        stack = stacks.getStackInSlot(slot);
//                    }
//
//                    if (stack.getItem() instanceof WearableArtifactItem) {
//                        GloveArtifactRenderer renderer = GloveArtifactRenderer.getGloveRenderer(stack);
//                        if (renderer != null) {
//                            renderer.renderFirstPersonArm(event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), stack.hasFoil());
//                        }
//                    }
//                }
//            }
//        });
    }
}
