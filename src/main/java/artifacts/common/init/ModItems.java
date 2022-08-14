package artifacts.common.init;

import artifacts.Artifacts;
import artifacts.common.config.ModConfig;
import artifacts.common.item.EverlastingFoodItem;
import artifacts.common.item.UmbrellaItem;
import artifacts.common.item.curio.CurioItem;
import artifacts.common.item.curio.WhoopeeCushionItem;
import artifacts.common.item.curio.belt.*;
import artifacts.common.item.curio.feet.*;
import artifacts.common.item.curio.hands.*;
import artifacts.common.item.curio.head.DrinkingHatItem;
import artifacts.common.item.curio.head.NightVisionGogglesItem;
import artifacts.common.item.curio.head.SnorkelItem;
import artifacts.common.item.curio.head.SuperstitiousHatItem;
import artifacts.common.item.curio.necklace.*;
import net.minecraft.core.Registry;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {

    public static final DeferredRegister<Item> REGISTRY = DeferredRegister.create(Registry.ITEM_REGISTRY, Artifacts.MODID);

    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(Artifacts.MODID) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(BUNNY_HOPPERS.get());
        }
    };

    public static final RegistryObject<Item> MIMIC_SPAWN_EGG = REGISTRY.register("mimic_spawn_egg", () -> new ForgeSpawnEggItem(ModEntityTypes.MIMIC, 0x805113, 0x212121, new Item.Properties().tab(CreativeModeTab.TAB_MISC)));
    public static final RegistryObject<Item> UMBRELLA = REGISTRY.register("umbrella", UmbrellaItem::new);
    public static final RegistryObject<Item> EVERLASTING_BEEF = REGISTRY.register("everlasting_beef", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(3).saturationMod(0.3F).build(), () -> ModConfig.server.everlastingBeef.cooldown.get()));
    public static final RegistryObject<Item> ETERNAL_STEAK = REGISTRY.register("eternal_steak", () -> new EverlastingFoodItem(new FoodProperties.Builder().nutrition(8).saturationMod(0.8F).build(), () -> ModConfig.server.eternalSteak.cooldown.get()));

    // head
    public static final RegistryObject<CurioItem> PLASTIC_DRINKING_HAT = REGISTRY.register("plastic_drinking_hat", DrinkingHatItem::new);
    public static final RegistryObject<CurioItem> NOVELTY_DRINKING_HAT = REGISTRY.register("novelty_drinking_hat", DrinkingHatItem::new);
    public static final RegistryObject<CurioItem> SNORKEL = REGISTRY.register("snorkel", SnorkelItem::new);
    public static final RegistryObject<CurioItem> NIGHT_VISION_GOGGLES = REGISTRY.register("night_vision_goggles", NightVisionGogglesItem::new);
    public static final RegistryObject<CurioItem> VILLAGER_HAT = REGISTRY.register("villager_hat", CurioItem::new);
    public static final RegistryObject<CurioItem> SUPERSTITIOUS_HAT = REGISTRY.register("superstitious_hat", SuperstitiousHatItem::new);

    // necklace
    public static final RegistryObject<CurioItem> LUCKY_SCARF = REGISTRY.register("lucky_scarf", LuckyScarfItem::new);
    public static final RegistryObject<CurioItem> SCARF_OF_INVISIBILITY = REGISTRY.register("scarf_of_invisibility", ScarfOfInvisibilityItem::new);
    public static final RegistryObject<CurioItem> CROSS_NECKLACE = REGISTRY.register("cross_necklace", CrossNecklaceItem::new);
    public static final RegistryObject<CurioItem> PANIC_NECKLACE = REGISTRY.register("panic_necklace", PanicNecklaceItem::new);
    public static final RegistryObject<CurioItem> SHOCK_PENDANT = REGISTRY.register("shock_pendant", ShockPendantItem::new);
    public static final RegistryObject<CurioItem> FLAME_PENDANT = REGISTRY.register("flame_pendant", FlamePendantItem::new);
    public static final RegistryObject<CurioItem> THORN_PENDANT = REGISTRY.register("thorn_pendant", ThornPendantItem::new);
    public static final RegistryObject<CurioItem> CHARM_OF_SINKING = REGISTRY.register("charm_of_sinking", CharmOfSinkingItem::new);

    // belt
    public static final RegistryObject<CloudInABottleItem> CLOUD_IN_A_BOTTLE = REGISTRY.register("cloud_in_a_bottle", CloudInABottleItem::new);
    public static final RegistryObject<CurioItem> OBSIDIAN_SKULL = REGISTRY.register("obsidian_skull", ObsidianSkullItem::new);
    public static final RegistryObject<CurioItem> ANTIDOTE_VESSEL = REGISTRY.register("antidote_vessel", AntidoteVesselItem::new);
    public static final RegistryObject<CurioItem> UNIVERSAL_ATTRACTOR = REGISTRY.register("universal_attractor", UniversalAttractorItem::new);
    public static final RegistryObject<CurioItem> CRYSTAL_HEART = REGISTRY.register("crystal_heart", CrystalHeartItem::new);
    public static final RegistryObject<CurioItem> HELIUM_FLAMINGO = REGISTRY.register("helium_flamingo", HeliumFlamingoItem::new);

    // hands
    public static final RegistryObject<CurioItem> DIGGING_CLAWS = REGISTRY.register("digging_claws", DiggingClawsItem::new);
    public static final RegistryObject<CurioItem> FERAL_CLAWS = REGISTRY.register("feral_claws", FeralClawsItem::new);
    public static final RegistryObject<CurioItem> POWER_GLOVE = REGISTRY.register("power_glove", PowerGloveItem::new);
    public static final RegistryObject<CurioItem> FIRE_GAUNTLET = REGISTRY.register("fire_gauntlet", FireGauntletItem::new);
    public static final RegistryObject<CurioItem> POCKET_PISTON = REGISTRY.register("pocket_piston", PocketPistonItem::new);
    public static final RegistryObject<CurioItem> VAMPIRIC_GLOVE = REGISTRY.register("vampiric_glove", VampiricGloveItem::new);
    public static final RegistryObject<CurioItem> GOLDEN_HOOK = REGISTRY.register("golden_hook", GoldenHookItem::new);

    // feet
    public static final RegistryObject<AquaDashersItem> AQUA_DASHERS = REGISTRY.register("aqua_dashers", AquaDashersItem::new);
    public static final RegistryObject<CurioItem> BUNNY_HOPPERS = REGISTRY.register("bunny_hoppers", BunnyHoppersItem::new);
    public static final RegistryObject<CurioItem> KITTY_SLIPPERS = REGISTRY.register("kitty_slippers", KittySlippersItem::new);
    public static final RegistryObject<CurioItem> RUNNING_SHOES = REGISTRY.register("running_shoes", RunningShoesItem::new);
    public static final RegistryObject<CurioItem> STEADFAST_SPIKES = REGISTRY.register("steadfast_spikes", SteadfastSpikesItem::new);
    public static final RegistryObject<CurioItem> FLIPPERS = REGISTRY.register("flippers", FlippersItem::new);

    // curio
    public static final RegistryObject<CurioItem> WHOOPEE_CUSHION = REGISTRY.register("whoopee_cushion", WhoopeeCushionItem::new);
}
