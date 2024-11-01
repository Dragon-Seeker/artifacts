package artifacts.integration;

import artifacts.item.WearableArtifactItem;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public abstract class BaseEquipmentIntegration {

    protected BaseEquipmentIntegration() {}

    public final void registerIntegration() {
        EquipmentIntegrationUtils.registerIntegration(this);
    }

    //--

    public abstract void setup();

    public void processWearableArtifactBuilder(WearableArtifactItem.Builder builder) { /* NO-OP */ }

    public void addCosmeticToggleTooltip(List<MutableComponent> tooltip, ItemStack stack) { /* NO-OP */ }

    //--

    public abstract Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Predicate<ItemStack> predicate);

    public abstract void iterateEquippedAccessories(LivingEntity entity, Consumer<ItemStack> consumer);

    public abstract <T> T reduceAccessories(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f);

    public abstract boolean equipAccessory(LivingEntity entity, ItemStack stack);

    public abstract boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item);

    public abstract String name();
}
