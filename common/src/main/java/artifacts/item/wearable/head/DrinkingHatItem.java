package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;

import java.util.List;

public class DrinkingHatItem extends WearableArtifactItem {

    private final ModGameRules.DoubleValue drinkingDurationMultiplier;
    private final ModGameRules.DoubleValue eatingDurationMultiplier;
    private final boolean hasSpecialTooltip;

    public DrinkingHatItem(ModGameRules.DoubleValue drinkingDurationMultiplier, ModGameRules.DoubleValue eatingDurationMultiplier, boolean hasSpecialTooltip) {
        this.drinkingDurationMultiplier = drinkingDurationMultiplier;
        this.eatingDurationMultiplier = eatingDurationMultiplier;
        this.hasSpecialTooltip = hasSpecialTooltip;
    }

    @Override
    public boolean hasNonCosmeticEffects() {
        return !drinkingDurationMultiplier.fuzzyEquals(1) || !eatingDurationMultiplier.fuzzyEquals(1);
    }

    @Override
    protected void addTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (hasSpecialTooltip) {
            tooltip.add(tooltipLine("special").withStyle(ChatFormatting.ITALIC));
            addEffectsTooltip(stack, tooltip);
        } else {
            super.addTooltip(stack, tooltip);
        }
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (!drinkingDurationMultiplier.fuzzyEquals(1)) {
            tooltip.add(tooltipLine("drinking"));
        }
        if (!eatingDurationMultiplier.fuzzyEquals(1)) {
            tooltip.add(tooltipLine("eating"));
        }
    }

    @Override
    protected String getTooltipItemName() {
        return "drinking_hat";
    }

    public double getDurationMultiplier(UseAnim action) {
        if (action == UseAnim.DRINK) {
            return drinkingDurationMultiplier.get();
        }
        return eatingDurationMultiplier.get();
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.BOTTLE_FILL;
    }

    public static int getDrinkingHatUseDuration(LivingEntity entity, UseAnim action, int duration) {
        return Math.min(
                getDrinkingHatUseDuration(entity, action, duration, ModItems.PLASTIC_DRINKING_HAT.get()),
                getDrinkingHatUseDuration(entity, action, duration, ModItems.NOVELTY_DRINKING_HAT.get())
        );
    }

    private static int getDrinkingHatUseDuration(LivingEntity entity, UseAnim action, int duration, DrinkingHatItem drinkingHat) {
        if (!drinkingHat.isEquippedBy(entity) || action != UseAnim.EAT && action != UseAnim.DRINK || drinkingHat.getDurationMultiplier(action) == 1) {
            return duration;
        }
        return Math.max(1, (int) (duration * drinkingHat.getDurationMultiplier(action)));
    }
}
