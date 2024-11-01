package artifacts.integration.impl.trinkets;

import artifacts.client.CosmeticsHelper;
import artifacts.event.ArtifactEvents;
import artifacts.integration.BaseEquipmentIntegration;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import artifacts.util.DamageSourceHelper;
import dev.emi.trinkets.api.*;
import dev.emi.trinkets.api.event.TrinketEquipCallback;
import dev.emi.trinkets.api.event.TrinketUnequipCallback;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TrinketIntegration extends BaseEquipmentIntegration {

    public static final TrinketIntegration INSTANCE = new TrinketIntegration();

    @Override
    public void setup() {
        PlatformServices.platformHelper.registryEntryAddCallback(item -> {
            if (item instanceof WearableArtifactItem wearableArtifactItem) {
                TrinketsApi.registerTrinket(item, new WearableArtifactTrinket(wearableArtifactItem));
            }
        });

        TrinketEquipCallback.EVENT.register((stack, slot, entity) -> ArtifactEvents.onItemChanged(entity, ItemStack.EMPTY, stack));
        TrinketUnequipCallback.EVENT.register((stack, slot, entity) -> ArtifactEvents.onItemChanged(entity, stack, ItemStack.EMPTY));
    }

    @Override
    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Predicate<ItemStack> predicate) {
        return TrinketsApi.getTrinketComponent(entity)
                .map(TrinketComponent::getAllEquipped)
                .orElse(List.of())
                .stream()
                .map(Tuple::getB);
    }

    @Override
    public void iterateEquippedAccessories(LivingEntity entity, Consumer<ItemStack> consumer) {
        TrinketsApi.getTrinketComponent(entity).ifPresent(component -> {
            for (Map<String, TrinketInventory> map : component.getInventory().values()) {
                for (TrinketInventory inventory : map.values()) {
                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        ItemStack item = inventory.getItem(i);
                        if (!item.isEmpty()) {
                            consumer.accept(item);
                        }
                    }
                }
            }
        });
    }

    @Override
    public <T> T reduceAccessories(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(entity);
        if (component.isPresent()) {
            for (Map<String, TrinketInventory> map : component.get().getInventory().values()) {
                for (TrinketInventory inventory : map.values()) {
                    for (int i = 0; i < inventory.getContainerSize(); i++) {
                        ItemStack item = inventory.getItem(i);
                        if (!item.isEmpty()) {
                            init = f.apply(item, init);
                        }
                    }
                }
            }
        }
        return init;
    }

    @Override
    public boolean equipAccessory(LivingEntity entity, ItemStack stack) {
        return TrinketItem.equipItem(entity, stack);
    }

    @Override
    public boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item) {
        return TrinketsApi.getTrinketComponent(entity).stream()
                .flatMap(component -> component.getAllEquipped().stream())
                .filter(tuple -> tuple.getA().inventory().getSlotType().getGroup().equals(
                        hand == InteractionHand.MAIN_HAND ? "hand" : "offhand"
                )).map(Tuple::getB)
                .filter(stack -> stack.is(item))
                .filter(stack -> !CosmeticsHelper.areCosmeticsToggledOffByPlayer(stack))
                .anyMatch(tuple -> true);
    }

    @Override
    public String name() {
        return "trinkets";
    }

    public static record WearableArtifactTrinket(WearableArtifactItem item) implements Trinket {

        @Override
        public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
            if (DamageSourceHelper.shouldDestroyWornItemsOnDeath(entity)) {
                return TrinketEnums.DropRule.DESTROY;
            }
            return Trinket.super.getDropRule(stack, slot, entity);
        }
    }
}
