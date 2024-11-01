package artifacts.integration;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class EquipmentIntegrationUtils {

    private static final Map<String, BaseEquipmentIntegration> INTEGRATIONS = new LinkedHashMap<>();

    public static void setupIntegrations() {
        INTEGRATIONS.values().forEach(BaseEquipmentIntegration::setup);
    }

    public static void registerIntegration(BaseEquipmentIntegration integration) {
        var name = integration.name();

        if (INTEGRATIONS.containsKey(name)) throw new IllegalStateException("Duplicate Equipment Integration detected! [Name: " + name + "]");

        INTEGRATIONS.put(name, integration);
    }

    public static Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Predicate<ItemStack> predicate) {
        var allEquippedStacks = Stream.<ItemStack>of();

        for (var integration : INTEGRATIONS.values()) {
            allEquippedStacks = Stream.concat(allEquippedStacks, integration.findAllEquippedBy(entity, predicate));
        }

        return allEquippedStacks;
    }

    public static void iterateEquippedAccessories(LivingEntity entity, Consumer<ItemStack> consumer) {
        for (var integration : INTEGRATIONS.values()) {
            integration.iterateEquippedAccessories(entity, consumer);
        }
    }

    public static <T> T reduceAccessories(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
        for (var integration : INTEGRATIONS.values()) {
            init = integration.reduceAccessories(entity, init, f);
        }

        return init;
    }

    public static boolean equipAccessory(LivingEntity entity, ItemStack stack) {
        for (var integration : INTEGRATIONS.values()) {
            if (integration.equipAccessory(entity, stack)) return true;
        }

        return false;
    }

    public static boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item) {
        for (var integration : INTEGRATIONS.values()) {
            if (integration.isVisibleOnHand(entity, hand, item)) return true;
        }

        return false;
    }
}
