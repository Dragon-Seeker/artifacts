package artifacts.item.wearable.head;

import artifacts.item.wearable.WearableArtifactItem;
import artifacts.registry.ModGameRules;
import net.minecraft.network.chat.MutableComponent;

import java.util.List;

public class SuperstitiousHatItem extends WearableArtifactItem {

    @Override
    protected boolean isCosmetic() {
        return ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() <= 0;
    }

    @Override
    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        if (ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get() == 1) {
            tooltip.add(tooltipLine("single_level"));
        } else {
            tooltip.add(tooltipLine("multiple_levels", ModGameRules.SUPERSTITIOUS_HAT_LOOTING_LEVEL_BONUS.get()));
        }
    }
}