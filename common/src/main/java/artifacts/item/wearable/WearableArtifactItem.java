package artifacts.item.wearable;

import artifacts.Artifacts;
import artifacts.client.ToggleKeyHandler;
import artifacts.item.ArtifactItem;
import artifacts.platform.PlatformServices;
import com.google.common.collect.Multimap;
import io.wispforest.accessories.Accessories;
import io.wispforest.accessories.api.AccessoriesCapability;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.SoundEventData;
import io.wispforest.accessories.api.slot.SlotEntryReference;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.api.slot.SlotType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public abstract class WearableArtifactItem extends ArtifactItem implements Accessory {

    private final List<ArtifactAttributeModifier> attributeModifiers = new ArrayList<>();

    public void addAttributeModifier(ArtifactAttributeModifier attributeModifier) {
        attributeModifiers.add(attributeModifier);
    }

    public List<ArtifactAttributeModifier> getAttributeModifiers() {
        return attributeModifiers;
    }

    public boolean isEquippedBy(@Nullable LivingEntity entity) {
        return entity != null && Optional.ofNullable(AccessoriesCapability.get(entity))
                .map(capability -> capability.isEquipped(this))
                .orElse(false);
    }

    public static Stream<ItemStack> findAllEquippedBy(LivingEntity entity) {
        return Optional.ofNullable(AccessoriesCapability.get(entity))
                .map(AccessoriesCapability::getAllEquipped)
                .stream()
                .flatMap(references -> references.stream().map(SlotEntryReference::stack));
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference reference) {
        var entity = reference.entity();

        if (entity.level().isClientSide()) {
            return;
        }
        for (ArtifactAttributeModifier modifier : attributeModifiers) {
            AttributeInstance attributeInstance = entity.getAttribute(modifier.getAttribute());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifier.getModifierId());
                AttributeModifier attributeModifier = modifier.createModifier();
                attributeInstance.addPermanentModifier(attributeModifier);
                modifier.onAttributeUpdated(entity);
            }
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference reference) {
        var entity = reference.entity();

        if (entity.level().isClientSide()) {
            return;
        }
        for (ArtifactAttributeModifier modifier : attributeModifiers) {
            AttributeInstance attributeInstance = entity.getAttribute(modifier.getAttribute());
            if (attributeInstance != null) {
                attributeInstance.removeModifier(modifier.getModifierId());
                modifier.onAttributeUpdated(entity);
            }
        }
    }

    @Override
    public void tick(ItemStack stack, SlotReference reference) {
        var entity = reference.entity();

        if (entity.level().isClientSide()) {
            return;
        }
        for (ArtifactAttributeModifier modifier : attributeModifiers) {
            AttributeInstance attributeInstance = entity.getAttribute(modifier.getAttribute());
            if (attributeInstance != null) {
                AttributeModifier existingModifier = attributeInstance.getModifier(modifier.getModifierId());
                if (existingModifier == null || existingModifier.getAmount() != modifier.getAmount()) {
                    attributeInstance.removeModifier(modifier.getModifierId());
                    attributeInstance.addPermanentModifier(modifier.createModifier());
                    modifier.onAttributeUpdated(entity);
                }
            }
        }
    }

    @Override
    public void getExtraTooltip(ItemStack stack, List<Component> tooltips) {
        if (Artifacts.CONFIG.client.showTooltips) {
            List<MutableComponent> tooltip = new ArrayList<>();
            addTooltip(stack, tooltip);
            tooltip.forEach(line -> tooltips.add(1, line.withStyle(ChatFormatting.GRAY)));
        }
    }

    @Override
    public void getAttributesTooltip(ItemStack stack, SlotType type, List<Component> tooltips) {
        var attributeModifiers = this.getAttributeModifiers();
        if (attributeModifiers.isEmpty() || !Artifacts.CONFIG.client.showTooltips) {
            return;
        }

        for (ArtifactAttributeModifier modifier : this.getAttributeModifiers()) {
            double amount = modifier.getAmount();

            if (modifier.getOperation() == AttributeModifier.Operation.ADDITION) {
                if (modifier.getAttribute().equals(Attributes.KNOCKBACK_RESISTANCE)) {
                    amount *= 10;
                }
            } else {
                amount *= 100;
            }

            Component text = Component.translatable(modifier.getAttribute().getDescriptionId());
            if (amount > 0) {
                tooltips.add(Component.translatable("attribute.modifier.plus." + modifier.getOperation().toValue(),
                        ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(amount), text).withStyle(ChatFormatting.BLUE));
            }
        }
    }

    @Override
    public SoundEventData getEquipSound(ItemStack stack, SlotReference reference) {
        return new SoundEventData(getEquipSound(), 1.0f, 1.0f);
    }

    public SoundEvent getEquipSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC;
    }

    public void toggleItem(ServerPlayer player) {
        findAllEquippedBy(player).forEach(stack -> setActivated(stack, !isActivated(stack)));
    }

    public static boolean isActivated(ItemStack stack) {
        return !stack.hasTag()
                || !stack.getOrCreateTag().contains("isActivated")
                || stack.getOrCreateTag().getBoolean("isActivated");
    }

    public static void setActivated(ItemStack stack, boolean active) {
        stack.getOrCreateTag().putBoolean("isActivated", active);
    }

    @Override
    protected void addEffectsTooltip(ItemStack stack, List<MutableComponent> tooltip) {
        super.addEffectsTooltip(stack, tooltip);
        KeyMapping key = ToggleKeyHandler.getToggleKey(this);
        if (key != null && (!key.isUnbound() || !isActivated(stack))) {
            tooltip.add(Component.translatable("%s.tooltip.toggle_keymapping".formatted(Artifacts.MOD_ID), key.getTranslatedKeyMessage()));
        }
    }
}
