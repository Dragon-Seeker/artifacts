package artifacts.loot;

import artifacts.Artifacts;
import artifacts.registry.ModLootConditions;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

public class EverlastingBeefChance implements LootItemCondition {

    private static final EverlastingBeefChance INSTANCE = new EverlastingBeefChance();

    private EverlastingBeefChance() {

    }

    public LootItemConditionType getType() {
        return ModLootConditions.EVERLASTING_BEEF_CHANCE.get();
    }

    public boolean test(LootContext context) {
        return context.getRandom().nextDouble() < Artifacts.CONFIG.common.getEverlastingBeefChance();
    }

    public static LootItemCondition.Builder everlastingBeefChance() {
        return () -> INSTANCE;
    }

    public static final Codec<EverlastingBeefChance> CODEC = Codec.unit(INSTANCE);
}
