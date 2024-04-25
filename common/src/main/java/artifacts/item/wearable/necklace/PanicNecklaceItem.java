package artifacts.item.wearable.necklace;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

public class PanicNecklaceItem extends WearableArtifactItem {

    public PanicNecklaceItem() {
        EntityEvent.LIVING_HURT.register(this::onLivingHurt);
    }

    @Override
    public boolean hasNonCosmeticEffects() {
        return ModGameRules.PANIC_NECKLACE_SPEED_DURATION.get() > 0 && ModGameRules.PANIC_NECKLACE_SPEED_LEVEL.get() > 0;
    }

    private EventResult onLivingHurt(LivingEntity entity, DamageSource damageSource, float amount) {
        if (isEquippedBy(entity) && !entity.level().isClientSide() && amount >= 1 && !isOnCooldown(entity)) {
            int duration = ModGameRules.PANIC_NECKLACE_SPEED_DURATION.get();
            int level = ModGameRules.PANIC_NECKLACE_SPEED_LEVEL.get();

            if (duration > 0 && level > 0) {
                entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, duration, level - 1, false, false));
                addCooldown(entity, ModGameRules.PANIC_NECKLACE_COOLDOWN.get());
            }
        }
        return EventResult.pass();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_DIAMOND;
    }
}
