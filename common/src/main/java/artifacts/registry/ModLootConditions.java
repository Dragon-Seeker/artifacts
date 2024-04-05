package artifacts.registry;

import artifacts.Artifacts;
import artifacts.loot.ConfigurableRandomChance;
import artifacts.loot.EverlastingBeefChance;
import com.mojang.serialization.Codec;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import java.util.function.Supplier;

public class ModLootConditions {

    public static final DeferredRegister<LootItemConditionType> LOOT_CONDITIONS = DeferredRegister.create(Artifacts.MOD_ID, Registries.LOOT_CONDITION_TYPE);

    public static final RegistrySupplier<LootItemConditionType> CONFIGURABLE_ARTIFACT_CHANCE = register("configurable_random_chance", () -> ConfigurableRandomChance.CODEC);
    public static final RegistrySupplier<LootItemConditionType> EVERLASTING_BEEF_CHANCE = register("everlasting_beef_chance", () -> EverlastingBeefChance.CODEC);

    private static RegistrySupplier<LootItemConditionType> register(String name, Supplier<Codec<? extends LootItemCondition>> serializer) {
        return RegistrySupplier.of(LOOT_CONDITIONS.register(name, () -> new LootItemConditionType(serializer.get())));
    }
}
