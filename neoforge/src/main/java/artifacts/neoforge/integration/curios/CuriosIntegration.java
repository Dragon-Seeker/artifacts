package artifacts.neoforge.integration.curios;

import artifacts.event.ArtifactEvents;
import artifacts.integration.BaseEquipmentIntegration;
import artifacts.item.WearableArtifactItem;
import artifacts.platform.PlatformServices;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.event.CurioChangeEvent;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class CuriosIntegration extends BaseEquipmentIntegration {

    public static final CuriosIntegration INSTANCE = new CuriosIntegration();

    @Override
    public void setup() {
        PlatformServices.platformHelper.registryEntryAddCallback(item -> {
            if (item instanceof WearableArtifactItem wearableArtifactItem) {
                CuriosApi.registerCurio(wearableArtifactItem, new WearableArtifactCurio(wearableArtifactItem));
            }
        });
        NeoForge.EVENT_BUS.addListener((CurioChangeEvent event) -> {
            ArtifactEvents.onItemChanged(event.getEntity(), event.getFrom(), event.getTo());
        });
    }

    @Override
    public Stream<ItemStack> findAllEquippedBy(LivingEntity entity, Predicate<ItemStack> predicate) {
        return CuriosApi.getCuriosInventory(entity)
                .map(inv -> inv.findCurios(predicate))
                .orElse(List.of()).stream()
                .map(SlotResult::stack);
    }

    @Override
    public void iterateEquippedAccessories(LivingEntity entity, Consumer<ItemStack> consumer) {
        Optional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosInventory(entity);
        if (itemHandler.isPresent()) {
            for (ICurioStacksHandler stacksHandler : itemHandler.get().getCurios().values()) {
                for (int i = 0; i < stacksHandler.getStacks().getSlots(); i++) {
                    ItemStack item = stacksHandler.getStacks().getStackInSlot(i);
                    if (!item.isEmpty()) {
                        consumer.accept(item);
                    }
                }
            }
        }
    }

    @Override
    public <T> T reduceAccessories(LivingEntity entity, T init, BiFunction<ItemStack, T, T> f) {
        Optional<ICuriosItemHandler> itemHandler = CuriosApi.getCuriosInventory(entity);
        if (itemHandler.isPresent()) {
            for (ICurioStacksHandler stacksHandler : itemHandler.get().getCurios().values()) {
                for (int i = 0; i < stacksHandler.getStacks().getSlots(); i++) {
                    ItemStack item = stacksHandler.getStacks().getStackInSlot(i);
                    if (!item.isEmpty()) {
                        init = f.apply(item, init);
                    }
                }
            }
        }
        return init;
    }

    @Override
    public boolean equipAccessory(LivingEntity entity, ItemStack stack) {
        Optional<ICuriosItemHandler> optional = CuriosApi.getCuriosInventory(entity);
        if (optional.isPresent()) {
            ICuriosItemHandler handler = optional.get();
            for (Map.Entry<String, ICurioStacksHandler> entry : handler.getCurios().entrySet()) {
                for (int i = 0; i < entry.getValue().getSlots(); i++) {
                    SlotContext slotContext = new SlotContext(entry.getKey(), entity, i, false, true);
                    //noinspection ConstantConditions
                    if (CuriosApi.isStackValid(slotContext, stack) && entry.getValue().getStacks().getStackInSlot(i).isEmpty()) {
                        entry.getValue().getStacks().setStackInSlot(i, stack);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isVisibleOnHand(LivingEntity entity, InteractionHand hand, Item item) {
        return CuriosApi.getCuriosInventory(entity)
                .flatMap(handler -> Optional.ofNullable(handler.getCurios().get("hands")))
                .map(stacksHandler -> {
                    int startSlot = hand == InteractionHand.MAIN_HAND ? 0 : 1;
                    for (int slot = startSlot; slot < stacksHandler.getSlots(); slot += 2) {
                        ItemStack stack = stacksHandler.getCosmeticStacks().getStackInSlot(slot);
                        if (stack.isEmpty() && stacksHandler.getRenders().get(slot)) {
                            stack = stacksHandler.getStacks().getStackInSlot(slot);
                        }

                        if (stack.getItem() == item) {
                            return true;
                        }
                    }
                    return false;
                }).orElse(false);
    }

    @Override
    public String name() {
        return "curios";
    }
}
