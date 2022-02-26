package artifacts.common.item;

import artifacts.common.config.ModConfig;
import artifacts.common.init.ModItems;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Rarity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class ArtifactItem extends Item {

    public ArtifactItem(Properties properties) {
        super(properties.stacksTo(1).tab(ModItems.CREATIVE_TAB).rarity(Rarity.RARE).fireResistant());
    }

    public ArtifactItem() {
        this(new Properties());
    }

    @Override
    public int getMaxDamage(ItemStack stack) {
        if (ModConfig.server != null && ModConfig.server.items.containsKey(this)) {
            return ModConfig.server.items.get(this).durability.get();
        }
        return 0;
    }

    @Override
    public boolean canBeDepleted() {
        return getMaxDamage(ItemStack.EMPTY) > 0;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment != Enchantments.MENDING && super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, World world, List<ITextComponent> tooltip, ITooltipFlag flags) {
        if (ModConfig.server != null && ModConfig.server.isCosmetic(this)) {
            tooltip.add(new TranslationTextComponent("artifacts.cosmetic.tooltip").withStyle(TextFormatting.GRAY).withStyle(TextFormatting.ITALIC));
        } else if (ModConfig.client.showTooltips.get()) {
            tooltip.add(new TranslationTextComponent(getDescriptionId() + ".tooltip").withStyle(TextFormatting.GRAY));
        }
    }
}
