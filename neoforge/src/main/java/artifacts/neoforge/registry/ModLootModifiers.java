package artifacts.neoforge.registry;

import artifacts.Artifacts;
import artifacts.neoforge.loot.RollLootTableModifier;
import com.mojang.serialization.Codec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModLootModifiers {

    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Artifacts.MOD_ID);

    public static final Supplier<Codec<RollLootTableModifier>> ROLL_LOOT_TABLE = LOOT_MODIFIERS.register("roll_loot_table", RollLootTableModifier.CODEC);
}
