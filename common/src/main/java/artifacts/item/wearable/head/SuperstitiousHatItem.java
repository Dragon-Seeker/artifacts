package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import io.wispforest.accessories.api.events.extra.ImplementedEvents;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

import java.util.List;

public class SuperstitiousHatItem extends WearableArtifactItem implements ImplementedEvents.FortuneAdjustment {

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        if (ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() == 1) {
            tooltip.add(tooltipLine("single_level"));
        } else {
            tooltip.add(tooltipLine("multiple_levels", ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get()));
        }
    }

    @Override
    public int getFortuneAdjustment(ItemStack stack, SlotReference reference, LootContext context, int currentLevel) {
        return ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get();
    }
}
