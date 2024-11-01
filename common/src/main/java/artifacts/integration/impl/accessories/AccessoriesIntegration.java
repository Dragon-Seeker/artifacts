package artifacts.integration.impl.accessories;

import artifacts.event.ArtifactEvents;
import artifacts.integration.BaseEquipmentIntegration;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.util.DamageSourceHelper;
import io.wispforest.accessories.api.*;
import io.wispforest.accessories.api.events.AccessoryChangeCallback;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class AccessoriesIntegration extends BaseEquipmentIntegration {

    public static final AccessoriesIntegration INSTANCE = new AccessoriesIntegration();

    @Override
    public void setup() {
        PlatformServices.platformHelper.registryEntryAddCallback(item -> {
            if (item instanceof WearableArtifactItem wearableArtifactItem) {
                AccessoriesAPI.registerAccessory(item, new WearableArtifactAccessory(wearableArtifactItem));
            }
        });

        AccessoryChangeCallback.EVENT.register((prevStack, currentStack, slotReference, slotStateChange) -> {
            ArtifactEvents.onItemChanged(slotReference.entity(), prevStack, currentStack);
        });
    }

    @Override
    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Predicate<ItemStack> predicate) {
        var capability = AccessoriesCapability.get(entity);

        var stacks = Stream.<ItemStack>empty();

        if (capability != null) {
            stacks = capability.getEquipped(predicate).stream().map(SlotEntryReference::stack);
        }

        return stacks;
    }

    @Override
    public void iterateEquippedAccessories(LivingEntity entity, Consumer<ItemStack> consumer) {
        var capability = AccessoriesCapability.get(entity);

        if(capability == null) return;

        capability.getAllEquipped().forEach(slotEntryReference -> consumer.accept(slotEntryReference.stack()));
    }

    @Override
    public <T> T reduceAccessories(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
        var capability = AccessoriesCapability.get(entity);

        if (capability != null) {
            for (var slotEntryReference : capability.getAllEquipped()) {
                init = f.apply(slotEntryReference.stack(), init);
            }
        }

        return init;
    }

    @Override
    public boolean equipAccessory(LivingEntity entity, ItemStack stack) {
        var capability = AccessoriesCapability.get(entity);

        if (capability != null) {
            var possibleLocation = capability.canEquipAccessory(stack, false);

            if (possibleLocation != null) {
                possibleLocation.second().equipStack(stack);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item) {
        var capability = AccessoriesCapability.get(entity);

        if (capability != null) {
            var container = capability.getContainers().get("hand");

            if (container != null) {
                var accessories = container.getAccessories();
                var cosmetics = container.getCosmeticAccessories();

                int startSlot = hand == InteractionHand.MAIN_HAND ? 0 : 1;

                for (int slot = startSlot; slot < container.getSize(); slot += 2) {
                    if (container.shouldRender(slot)) continue;

                    var stack = cosmetics.getItem(slot);

                    if (stack.isEmpty()) stack = accessories.getItem(slot);

                    if (stack.getItem() == item) return true;
                }
            }
        }

        return false;
    }

    @Override
    public String name() {
        return "accessories";
    }

    public record WearableArtifactAccessory(WearableArtifactItem item) implements Accessory {
        @Override
        public DropRule getDropRule(ItemStack stack, SlotReference reference, DamageSource source) {
            if (DamageSourceHelper.shouldDestroyWornItemsOnDeath(reference.entity())) {
                return DropRule.DESTROY;
            }
            return Accessory.super.getDropRule(stack, reference, source);
        }

        @Override
        @Nullable
        public SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
            return new SoundEventData(Holder.direct(item.getEquipSound()), 1, item.getEquipSoundPitch());
        }

        @Override
        public boolean canEquipFromUse(ItemStack stack) {
            return stack.get(DataComponents.FOOD) == null;
        }
    }
}
