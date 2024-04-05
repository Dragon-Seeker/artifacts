package artifacts.neoforge.registry;

import artifacts.Artifacts;
import artifacts.item.wearable.ArtifactAttributeModifier;
import artifacts.registry.ModGameRules;
import artifacts.registry.ModItems;
import net.neoforged.neoforge.common.NeoForgeMod;

import java.util.UUID;

public class ModItemsNeoForge {

    public static void register() {
        ModItems.FLIPPERS.get().addAttributeModifier(ArtifactAttributeModifier.create(
                NeoForgeMod.SWIM_SPEED.value(),
                UUID.fromString("83f4e257-cd5c-4a36-ba4b-c052422ce7cf"),
                Artifacts.id("flippers_swim_speed_bonus").toString(),
                ModGameRules.FLIPPERS_SWIM_SPEED_BONUS
        ));
    }
}
