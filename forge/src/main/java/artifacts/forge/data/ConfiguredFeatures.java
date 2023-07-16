package artifacts.forge.data;

import artifacts.forge.ArtifactsForge;
import artifacts.forge.registry.ModFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public class ConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> CAMPSITE = ArtifactsForge.key(Registries.CONFIGURED_FEATURE, "campsite");

    public static void create(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ConfiguredFeature<?, ?> campsite = new ConfiguredFeature<>(ModFeatures.CAMPSITE.get(), FeatureConfiguration.NONE);
        context.register(CAMPSITE, campsite);
    }
}
