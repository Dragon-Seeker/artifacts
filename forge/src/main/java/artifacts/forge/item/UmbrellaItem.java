package artifacts.forge.item;

import artifacts.forge.capability.SwimHandler;
import artifacts.forge.registry.ModGameRules;
import artifacts.item.ArtifactItem;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.List;
import java.util.UUID;

public class UmbrellaItem extends ArtifactItem {

    public static final AttributeModifier UMBRELLA_SLOW_FALLING = new AttributeModifier(UUID.fromString("a7a25453-2065-4a96-bc83-df600e13f390"), "artifacts:umbrella_slow_falling", -0.875, AttributeModifier.Operation.MULTIPLY_TOTAL);

    public UmbrellaItem() {
        DispenserBlock.registerBehavior(this, ArmorItem.DISPENSE_ITEM_BEHAVIOR);
        MinecraftForge.EVENT_BUS.addListener(this::onLivingUpdate);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.UMBRELLA_IS_GLIDER.get() && !ModGameRules.UMBRELLA_IS_SHIELD.get();
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.UMBRELLA_IS_GLIDER.get()) {
            tooltip.add(tooltipLine("glider"));
        }
        if (ModGameRules.UMBRELLA_IS_SHIELD.get()) {
            tooltip.add(tooltipLine("shield"));
        }
    }

    private void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        AttributeInstance gravity = entity.getAttribute(ForgeMod.ENTITY_GRAVITY.get());
        if (gravity != null) {
            boolean isInWater = entity.isInWater() && !SwimHandler.isSinking(entity);
            if (ModGameRules.UMBRELLA_IS_GLIDER.get() && !entity.onGround() && !isInWater && event.getEntity().getDeltaMovement().y < 0 && !entity.hasEffect(MobEffects.SLOW_FALLING)
                    && (entity.getOffhandItem().getItem() == this
                    || entity.getMainHandItem().getItem() == this) && !(entity.isUsingItem() && !entity.getUseItem().isEmpty() && entity.getUseItem().getItem().getUseAnimation(entity.getUseItem()) == UseAnim.BLOCK)) {
                if (!gravity.hasModifier(UMBRELLA_SLOW_FALLING)) {
                    gravity.addTransientModifier(UMBRELLA_SLOW_FALLING);
                }
                entity.fallDistance = 0;
            } else if (gravity.hasModifier(UMBRELLA_SLOW_FALLING)) {
                gravity.removeModifier(UMBRELLA_SLOW_FALLING);
            }
        }
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ToolActions.DEFAULT_SHIELD_ACTIONS.contains(toolAction) && ModGameRules.UMBRELLA_IS_SHIELD.get();
    }

    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BLOCK;
    }

    public int getUseDuration(ItemStack stack) {
        return 72000;
    }

    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!canPerformAction(stack, ToolActions.SHIELD_BLOCK)) {
            return super.use(level, player, hand);
        }
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(stack);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity, InteractionHand hand) {
        return entity.getItemInHand(hand).getItem() instanceof UmbrellaItem && (!entity.isUsingItem() || entity.getUsedItemHand() != hand);
    }

    public static boolean isHoldingUmbrellaUpright(LivingEntity entity) {
        return isHoldingUmbrellaUpright(entity, InteractionHand.MAIN_HAND) || isHoldingUmbrellaUpright(entity, InteractionHand.OFF_HAND);
    }
}
