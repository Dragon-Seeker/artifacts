package artifacts.item;

import artifacts.Artifacts;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public abstract class ArtifactItem extends Item {

    public ArtifactItem(Properties properties) {
        super(properties.stacksTo(1).rarity(Rarity.RARE).fireResistant());
    }

    public ArtifactItem() {
        this(new Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level world, List<Component> tooltipList, TooltipFlag flags) {
        if (Artifacts.CONFIG.client.showTooltips) {
            List<MutableComponent> tooltip = new ArrayList<>();
            addTooltip(tooltip);
            tooltip.forEach(line -> tooltipList.add(line.withStyle(ChatFormatting.GRAY)));
        }
    }

    protected void addTooltip(List<MutableComponent> tooltip) {
        if (isCosmetic()) {
            tooltip.add(Component.translatable("%s.tooltip.cosmetic".formatted(Artifacts.MOD_ID)).withStyle(ChatFormatting.ITALIC));
        } else {
            addEffectsTooltip(tooltip);
        }
    }

    protected void addEffectsTooltip(List<MutableComponent> tooltip) {
        tooltip.add(Component.translatable("%s.tooltip.item.%s".formatted(Artifacts.MOD_ID, getTooltipItemName())));
    }

    protected MutableComponent tooltipLine(String lineId, Object... args) {
        return Component.translatable("%s.tooltip.item.%s.%s".formatted(Artifacts.MOD_ID, getTooltipItemName(), lineId), args);
    }

    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }

    protected abstract boolean isCosmetic();
}
