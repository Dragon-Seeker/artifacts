package artifacts.platform;

import artifacts.Artifacts;
import artifacts.client.CosmeticsHelper;
import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.component.AbilityToggles;
import artifacts.component.SwimData;
import artifacts.integration.EquipmentIntegrationUtils;
import artifacts.integration.client.ClientEquipmentIntegrationUtils;
import artifacts.integration.impl.accessories.AccessoriesClientIntegration;
import artifacts.integration.impl.accessories.AccessoriesIntegration;
import artifacts.integration.impl.trinkets.TrinketClientIntegration;
import artifacts.integration.impl.trinkets.TrinketIntegration;
import artifacts.item.WearableArtifactItem;
import artifacts.registry.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Path;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface PlatformHelper {

    @Nullable
    AbilityToggles getAbilityToggles(LivingEntity entity);

    @Nullable
    SwimData getSwimData(LivingEntity entity);

    Holder<Attribute> getSwimSpeedAttribute();

    // TODO register attributes properly
    Holder<Attribute> registerAttribute(String name, Supplier<? extends Attribute> supplier);

    default void processWearableArtifactBuilder(WearableArtifactItem.Builder builder) {
        builder.properties(properties -> properties.component(ModDataComponents.COSMETICS_ENABLED.get(), true));
    }

    default void addCosmeticToggleTooltip(List<MutableComponent> tooltip, ItemStack stack) {
        if (CosmeticsHelper.areCosmeticsToggledOffByPlayer(stack)) {
            tooltip.add(
                    Component.translatable("%s.tooltip.cosmetics_disabled".formatted(Artifacts.MOD_ID))
                            .withStyle(ChatFormatting.ITALIC)
            );
        } else {
            tooltip.add(
                    Component.translatable("%s.tooltip.cosmetics_enabled".formatted(Artifacts.MOD_ID))
                            .withStyle(ChatFormatting.ITALIC)
            );
        }
    }

    boolean isEyeInWater(Player player);

    default boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item) {
        return EquipmentIntegrationUtils.isVisibleOnHand(entity, hand, item);
    }

    boolean areBootsHidden(LivingEntity entity);

    boolean isFishingRod(ItemStack stack);

    Path getConfigDir();

    void registryEntryAddCallback(Consumer<Item> consumer);

    boolean isModLoaded(String modid);

    default void setupIntegrations() {
        if (PlatformServices.platformHelper.isModLoaded("trinkets") && !PlatformServices.platformHelper.isModLoaded("tclayer")) {
            TrinketIntegration.INSTANCE.registerIntegration();
        }

    }

    default void setupClientIntegratons() {
        if (PlatformServices.platformHelper.isModLoaded("trinkets") && !PlatformServices.platformHelper.isModLoaded("tclayer")) {
            TrinketClientIntegration.INSTANCE.registerIntegration();
        }

    }
}
