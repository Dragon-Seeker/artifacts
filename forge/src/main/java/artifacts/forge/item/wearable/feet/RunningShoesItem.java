package artifacts.forge.item.wearable.feet;

import artifacts.forge.item.wearable.WearableArtifactItem;
import artifacts.forge.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;
import java.util.UUID;

public class RunningShoesItem extends WearableArtifactItem {

    private static final AttributeModifier STEP_HEIGHT_BONUS = new AttributeModifier(UUID.fromString("4a312f09-78e0-4f3a-95c2-07ed63212472"), "artifacts:running_shoes_step_height", 0.5, AttributeModifier.Operation.ADDITION);

    public RunningShoesItem() {
        MinecraftForge.EVENT_BUS.addListener(this::onPlayerTick);
    }

    @Override
    protected boolean isCosmetic() {
        return !ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get() && ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() > 0) {
            tooltip.add(tooltipLine("movement_speed"));
        }
        if (ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get()) {
            tooltip.add(tooltipLine("step_height"));
        }
    }

    private static AttributeModifier getSpeedBonus() {
        double speedMultiplier = Math.max(0, ModGameRules.RUNNING_SHOES_SPEED_BONUS.get() / 100D);
        return new AttributeModifier(UUID.fromString("ac7ab816-2b08-46b6-879d-e5dea34ff305"), "artifacts:running_shoes_movement_speed", speedMultiplier, AttributeModifier.Operation.MULTIPLY_TOTAL);
    }

    private void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        // onUnequip does not get called on the client
        if (!isEquippedBy(event.player)) {
            AttributeInstance stepHeight = event.player.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
            AttributeInstance movementSpeed = event.player.getAttribute(Attributes.MOVEMENT_SPEED);
            AttributeModifier movementSpeedBonus = getSpeedBonus();
            if (movementSpeed != null && movementSpeed.hasModifier(movementSpeedBonus)) {
                movementSpeed.removeModifier(movementSpeedBonus);
            }
            if (stepHeight != null && stepHeight.hasModifier(STEP_HEIGHT_BONUS)) {
                stepHeight.removeModifier(STEP_HEIGHT_BONUS);
            }
        }
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void curioTick(SlotContext slotContext, ItemStack stack) {
        LivingEntity entity = slotContext.entity();
        AttributeInstance stepHeight = entity.getAttribute(ForgeMod.STEP_HEIGHT_ADDITION.get());
        AttributeInstance movementSpeed = entity.getAttribute(Attributes.MOVEMENT_SPEED);
        AttributeModifier speedBonus = getSpeedBonus();
        if (entity.isSprinting()) {
            if (!movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.addTransientModifier(speedBonus);
            }
            if (ModGameRules.RUNNING_SHOES_DO_INCREASE_STEP_HEIGHT.get() && !stepHeight.hasModifier(STEP_HEIGHT_BONUS) && entity instanceof Player) {
                stepHeight.addTransientModifier(STEP_HEIGHT_BONUS);
            }
        } else {
            if (movementSpeed.hasModifier(speedBonus)) {
                movementSpeed.removeModifier(speedBonus);
            }
            if (stepHeight.hasModifier(STEP_HEIGHT_BONUS)) {
                stepHeight.removeModifier(STEP_HEIGHT_BONUS);
            }
        }
    }
}
