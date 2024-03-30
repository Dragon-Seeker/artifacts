package artifacts.item.wearable.belt;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class ObsidianSkullItem extends WearableArtifactItem {

    @Override
    public boolean isCosmetic() {
        return ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_DURATION.get() == 0;
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_IRON;
    }

    public static void onLivingDamage(LivingEntity entity, DamageSource damageSource, float amount) {
        if (
                ModItems.OBSIDIAN_SKULL.get().isEquippedBy(entity)
                && !entity.level().isClientSide
                && amount >= 1
                && damageSource.is(DamageTypeTags.IS_FIRE)
                && entity instanceof Player
                && !ModItems.OBSIDIAN_SKULL.get().isOnCooldown(entity)
        ) {
            int fireResistanceDuration = ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_DURATION.get();

            if (fireResistanceDuration > 0) {
                entity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, fireResistanceDuration, 0, false, false, true));
                ModItems.OBSIDIAN_SKULL.get().addCooldown(entity, ModGameRules.OBSIDIAN_SKULL_FIRE_RESISTANCE_COOLDOWN.get());
            }
        }
    }
}
