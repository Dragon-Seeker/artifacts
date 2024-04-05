package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class CrossNecklaceItem extends WearableArtifactItem {

    private boolean canApplyBonus(LivingEntity entity, ItemStack stack) {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() > 0
                && !isOnCooldown(entity)
                && stack.getOrCreateTag().getBoolean("CanApplyBonus");
    }

    @Override
    public boolean isCosmetic() {
        return ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get() == 0;
    }

    private static void setCanApplyBonus(ItemStack stack, boolean canApplyBonus) {
        stack.getOrCreateTag().putBoolean("CanApplyBonus", canApplyBonus);
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        var entity = reference.entity();

        if (entity.invulnerableTime <= 10) {
            setCanApplyBonus(stack, true);
        } else if (canApplyBonus(entity, stack)) {
            entity.invulnerableTime += ModGameRules.CROSS_NECKLACE_BONUS_INVINCIBILITY_TICKS.get();
            setCanApplyBonus(stack, false);
            addCooldown(entity, ModGameRules.CROSS_NECKLACE_COOLDOWN.get());
        }
    }
}
