package artifacts.loot;

import artifacts.Artifacts;
import artifacts.registry.ModLootConditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.RecordBuilder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public record ConfigurableRandomChance(float defaultProbability) implements LootItemCondition {

    public LootItemConditionType getType() {
        return ModLootConditions.CONFIGURABLE_ARTIFACT_CHANCE.get();
    }

    public boolean test(LootContext context) {
        if (Artifacts.CONFIG.common.getArtifactRarity() > 9999) {
            return false;
        }
        float r = (float) Artifacts.CONFIG.common.getArtifactRarity();
        float p = defaultProbability;
        float adjustedProbability = p / (p + r - r * p);
        return context.getRandom().nextFloat() < adjustedProbability;
    }

    public static LootItemCondition.Builder configurableRandomChance(float probability) {
        return () -> new ConfigurableRandomChance(probability);
    }

    public static final Codec<ConfigurableRandomChance> CODEC = RecordCodecBuilder.create(instance -> {
        return instance.group(Codec.FLOAT.fieldOf("default_probability").forGetter(ConfigurableRandomChance::defaultProbability))
                .apply(instance, ConfigurableRandomChance::new);
    });
}
