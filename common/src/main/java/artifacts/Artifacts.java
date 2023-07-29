package artifacts;

import artifacts.config.ModConfig;
import artifacts.network.NetworkHandler;
import artifacts.registry.*;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Artifacts {

    public static final String MOD_ID = "artifacts";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ModConfig CONFIG;

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

    public static ResourceLocation id(String path, String... args) {
        return new ResourceLocation(MOD_ID, String.format(path, (Object[]) args));
    }

    public static <T> ResourceKey<T> key(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, id(path));
    }

    public static void init() {
        AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new));
        CONFIG = AutoConfig.getConfigHolder(ModConfig.class).getConfig();

        NetworkHandler.register();

        ModSoundEvents.SOUND_EVENTS.register();
        ModLootConditions.LOOT_CONDITIONS.register();
        ModPlacementModifierTypes.PLACEMENT_MODIFIERS.register();
        ModItems.CREATIVE_MODE_TABS.register();
        ModItems.ITEMS.register();
        ModEntityTypes.ENTITY_TYPES.register();
        ModFeatures.FEATURES.register();


        LifecycleEvent.SERVER_STARTED.register(ModGameRules::onServerStarted);
        EntityEvent.ADD.register((entity, level) -> ModGameRules.onPlayerJoinLevel(entity));
    }
}