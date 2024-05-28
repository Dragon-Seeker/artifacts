package artifacts.neoforge.data;

import artifacts.Artifacts;
import artifacts.ability.ArtifactAbility;
import artifacts.config.ItemConfigs;
import artifacts.config.value.type.ValueType;
import artifacts.registry.*;
import com.google.common.base.CaseFormat;
import dev.architectury.registry.registries.RegistrySupplier;
import joptsimple.internal.Strings;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;

import java.util.List;

public class Language extends LanguageProvider {

    public Language(PackOutput output) {
        super(output, Artifacts.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        misc();
        abilities();
        advancements();
        attributes();
        config();
        entities();
        itemConfigs();
        itemConfigTooltips();
        items();
        tooltips();
    }

    @Override
    public void add(String key, String value) {
        try {
            super.add(key, value);
        } catch (IllegalStateException ignored) {

        }
    }

    private void misc() {
        add("artifacts.creative_tab", "Artifacts");
        add("artifacts.key.helium_flamingo.activate", "Activate Helium Flamingo");
        add("artifacts.key.night_vision_goggles.toggle", "Toggle Night Vision Goggles");
        add("artifacts.key.universal_attractor.toggle", "Toggle Universal Attractor");
        add("artifacts.key_category", "Artifacts");
        add(ModSoundEvents.FART.get(), "Fart");
        add("curios.identifier.feet", "Feet");
        add("curios.modifiers.feet", "When on feet:");
    }

    private void abilities() {
        abilityTooltip(ModAbilities.APPLY_MOB_EFFECT_AFTER_DAMAGE, MobEffects.FIRE_RESISTANCE, "Applies a temporary fire resistance effect after taking fire damage");
        abilityTooltip(ModAbilities.APPLY_MOB_EFFECT_AFTER_DAMAGE, MobEffects.MOVEMENT_SPEED, "Increases the wearer's movement speed after taking damage");
        abilityTooltip(ModAbilities.APPLY_MOB_EFFECT_AFTER_EATING, MobEffects.DIG_SPEED, "Grants a temporary boost to mining speed after eating food");
        abilityTooltip(ModAbilities.ATTRACT_ITEMS, "Attracts nearby items");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.ATTACK_BURNING_DURATION, "Causes the wearer's melee attacks to deal fire damage");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.ATTACK_DAMAGE, "Increases damage dealt by the wearer");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.ATTACK_DAMAGE_ABSORPTION, "Causes the wearer's melee attacks to absorb health");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.ATTACK_KNOCKBACK, "Increases knockback dealt by the wearer");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.ATTACK_SPEED, "Increases the wearer's attack speed");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.DRINKING_SPEED, "Decreases the time it takes to drink items");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.EATING_SPEED, "Decreases the time it takes to eat items");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.FALL_DAMAGE_MULTIPLIER, "Reduces fall damage taken by the wearer");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.FLATULENCE, "Increases the wearer's flatulence");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.INVINCIBILITY_TICKS, "Increases the length of invincibility after taking damage");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.JUMP_STRENGTH, "Increases the wearer's jump height");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.KNOCKBACK_RESISTANCE, "Grants immunity to knockback");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.MAX_HEALTH, "Increases the wearer's maximum health");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.MOUNT_SPEED, "Increases the speed of ridden mounts");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.SAFE_FALL_DISTANCE, "Increases the wearer's maximum safe fall distance");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.SCALE, "Shrinks the wearer");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.SLIP_RESISTANCE, "Makes ice less slippery to walk on");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.MOVEMENT_SPEED_ON_SNOW, "Increases the wearer's walking speed on snow");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.SPRINTING_SPEED, "Increases the wearer's movement speed while sprinting");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.SPRINTING_STEP_HEIGHT, "Increases the wearer's step height while sprinting");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, "generic.swim_speed", "Improves agility in water");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, Attributes.BLOCK_BREAK_SPEED, "Increases the wearer's mining speed");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.ENTITY_EXPERIENCE, "Increases experience dropped by creatures");
        abilityTooltip(ModAbilities.ATTRIBUTE_MODIFIER, ModAttributes.VILLAGER_REPUTATION, "Decreases the trading prices of villagers");
        abilityTooltip(ModAbilities.DOUBLE_JUMP, "Allows the wearer to double jump");
        abilityTooltip(ModAbilities.GROW_PLANTS_AFTER_EATING, "Plants grow after eating when standing on grass");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "fortune", "multiple_levels", "Applies %s extra levels of fortune to mined blocks");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "fortune", "single_level", "Applies an extra level of fortune to mined blocks");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "looting", "multiple_levels", "Applies %s extra levels of looting to killed entities");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "looting", "single_level", "Applies an extra level of looting to killed entities");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "luck_of_the_sea", "multiple_levels", "Applies %s extra levels of Luck of the Sea when fishing");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "luck_of_the_sea", "single_level", "Applies an extra Luck of the Sea when fishing");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "lure", "multiple_levels", "Applies %s extra levels of Lure when fishing");
        abilityTooltip(ModAbilities.INCREASE_ENCHANTMENT_LEVEL, "lure", "single_level", "Applies an extra level of Lure when fishing");
        abilityTooltip(ModAbilities.LIGHTNING_IMMUNITY, "Grants protection against lightning strikes");
        abilityTooltip(ModAbilities.LIMITED_WATER_BREATHING, "infinite", "Allows the wearer to breathe underwater");
        abilityTooltip(ModAbilities.LIMITED_WATER_BREATHING, "limited", "Allows the wearer to breathe underwater for a limited amount of time");
        abilityTooltip(ModAbilities.MOB_EFFECT, "invisibility", "Turns the wearer invisible");
        abilityTooltip(ModAbilities.NIGHT_VISION, "full", "Allows the wearer to see in the dark");
        abilityTooltip(ModAbilities.NIGHT_VISION, "partial", "Allows the wearer to see in the dark slightly");
        abilityTooltip(ModAbilities.REMOVE_BAD_EFFECTS, "Greatly reduces the duration of negative effects");
        abilityTooltip(ModAbilities.REPLENISH_HUNGER_ON_GRASS, "Slowly replenishes hunger while walking on grass");
        abilityTooltip(ModAbilities.SCARE_CREEPERS, "Creepers avoid the wearer");
        abilityTooltip(ModAbilities.SET_ATTACKERS_ON_FIRE, "fire_resistance", "Grants fire resistance after lighting an attacker on fire");
        abilityTooltip(ModAbilities.SET_ATTACKERS_ON_FIRE, "strike_chance", "Has a chance to light attackers on fire");
        abilityTooltip(ModAbilities.SINKING, "Allows the wearer to move freely in water");
        abilityTooltip(ModAbilities.SMELT_ORES, "Automatically smelts mined ores");
        abilityTooltip(ModAbilities.SPRINT_ON_FLUIDS, "Allows the wearer to walk on fluids while sprinting");
        abilityTooltip(ModAbilities.STRIKE_ATTACKERS_WITH_LIGHTNING, "Has a chance to strike attackers with lightning");
        abilityTooltip(ModAbilities.SWIM_IN_AIR, "keymapping", "Press %s while in the air to start swimming");
        abilityTooltip(ModAbilities.SWIM_IN_AIR, "swimming", "Allows the wearer to swim in the air for a limited period of time");
        abilityTooltip(ModAbilities.TELEPORT_ON_DEATH, "chance", "A fatal hit has a chance to teleport you somewhere else instead");
        abilityTooltip(ModAbilities.TELEPORT_ON_DEATH, "constant", "A fatal hit teleports you somewhere else instead");
        abilityTooltip(ModAbilities.TELEPORT_ON_DEATH, "not_consumed", "Not consumed on use");
        abilityTooltip(ModAbilities.THORNS, "Has a chance to damage attackers");
        abilityTooltip(ModAbilities.UPGRADE_TOOL_TIER, "Increases the wearer's base mining level to %s");
        abilityTooltip(ModAbilities.WALK_ON_POWDER_SNOW, "Allows the wearer to walk on Powder Snow");
    }

    private void advancements() {
        add("artifacts.advancements.adventurous_eater.description", "Eat an Artifact");
        add("artifacts.advancements.adventurous_eater.title", "Adventurous Eater");
        add("artifacts.advancements.amateur_archaeologist.description", "Find an Artifact");
        add("artifacts.advancements.amateur_archaeologist.title", "Amateur Archaeologist");
        add("artifacts.advancements.chest_slayer.description", "Kill a Mimic");
        add("artifacts.advancements.chest_slayer.title", "Chest Slayer");
    }

    private void attributes() {
        for (RegistrySupplier<Attribute> attribute : ModAttributes.ATTRIBUTES) {
            add(attribute.get().getDescriptionId(), fromSnakeCasedString(attribute.getId().getPath().split("\\.")[1]));
        }
        add("generic.swim_speed", "Swim Speed");
    }

    private void config() {
        add("artifacts.config.title", "Artifacts Config");
        add("text.autoconfig.artifacts.category.client", "Client");
        add("text.autoconfig.artifacts.category.common", "Common");
        add("text.autoconfig.artifacts.option.client.alwaysShowCosmeticsToggleTooltip", "Always show cosmetics toggle tooltip");
        add("text.autoconfig.artifacts.option.client.alwaysShowCosmeticsToggleTooltip.@Tooltip[0]", "Whether the cosmetics toggle tooltip should");
        add("text.autoconfig.artifacts.option.client.alwaysShowCosmeticsToggleTooltip.@Tooltip[1]", "be shown even when cosmetics are toggled on");
        add("text.autoconfig.artifacts.option.client.cooldownOverlayOffset", "Cooldown overlay offset");
        add("text.autoconfig.artifacts.option.client.cooldownOverlayOffset.@Tooltip[0]", "Location of the artifact cooldown gui element");
        add("text.autoconfig.artifacts.option.client.cooldownOverlayOffset.@Tooltip[1]", "Distance from the hotbar measured in pixels");
        add("text.autoconfig.artifacts.option.client.cooldownOverlayOffset.@Tooltip[2]", "Negative values place the element left of the hotbar");
        add("text.autoconfig.artifacts.option.client.enableCooldownOverlay", "Enable cooldown overlay");
        add("text.autoconfig.artifacts.option.client.enableCooldownOverlay.@Tooltip", "Display artifacts on cooldown next to the hotbar");
        add("text.autoconfig.artifacts.option.client.showFirstPersonGloves", "Show first person gloves");
        add("text.autoconfig.artifacts.option.client.showFirstPersonGloves.@Tooltip", "Whether models for gloves are shown in first person");
        add("text.autoconfig.artifacts.option.client.showTooltips", "Show item tooltips");
        add("text.autoconfig.artifacts.option.client.showTooltips.@Tooltip", "Whether artifacts have tooltips explaining their effects");
        add("text.autoconfig.artifacts.option.client.useModdedMimicTextures", "Use modded chest textures for Mimics");
        add("text.autoconfig.artifacts.option.client.useModdedMimicTextures.@Tooltip", "Whether mimics can use textures from Lootr or Quark");
        add("text.autoconfig.artifacts.option.common.archaeologyChance", "Archaeology chance");
        add("text.autoconfig.artifacts.option.common.archaeologyChance.@Tooltip", "The chance that an artifact generates in suspicious sand or gravel");
        add("text.autoconfig.artifacts.option.common.artifactRarity", "Artifact rarity");
        add("text.autoconfig.artifacts.option.common.artifactRarity.@Tooltip[0]", "Affects how common artifacts are in chests");
        add("text.autoconfig.artifacts.option.common.artifactRarity.@Tooltip[1]", "Values above 1 will make artifacts rarer");
        add("text.autoconfig.artifacts.option.common.artifactRarity.@Tooltip[2]", "Values between 0 and 1 will make artifacts more common");
        add("text.autoconfig.artifacts.option.common.artifactRarity.@Tooltip[3]", "Set this to 10000 to remove all artifacts from chest loot");
        add("text.autoconfig.artifacts.option.common.campsite", "Campsite");
        add("text.autoconfig.artifacts.option.common.campsite.allowLightSources", "Allow light sources");
        add("text.autoconfig.artifacts.option.common.campsite.allowLightSources.@Tooltip", "Whether campsites can contain blocks that emit lights");
        add("text.autoconfig.artifacts.option.common.campsite.count", "Campsite count");
        add("text.autoconfig.artifacts.option.common.campsite.count.@Tooltip[0]", "Amount of campsite generation attempts per chunk");
        add("text.autoconfig.artifacts.option.common.campsite.count.@Tooltip[1]", "Set this to 0 to prevent campsites from generating");
        add("text.autoconfig.artifacts.option.common.campsite.maxY", "Maximum Y-level");
        add("text.autoconfig.artifacts.option.common.campsite.maxY.@Tooltip", "The maximum height campsites can spawn at");
        add("text.autoconfig.artifacts.option.common.campsite.mimicChance", "Mimic chance");
        add("text.autoconfig.artifacts.option.common.campsite.mimicChance.@Tooltip", "Probability that a campsite contains a mimic");
        add("text.autoconfig.artifacts.option.common.campsite.minY", "Minimum Y-level");
        add("text.autoconfig.artifacts.option.common.campsite.minY.@Tooltip", "The minimum height campsites can spawn at");
        add("text.autoconfig.artifacts.option.common.campsite.useModdedChests", "Use modded chests");
        add("text.autoconfig.artifacts.option.common.campsite.useModdedChests.@Tooltip", "Whether chests from other mods generate in campsites");
        add("text.autoconfig.artifacts.option.common.entityEquipmentChance", "Entity Equipment Chance");
        add("text.autoconfig.artifacts.option.common.entityEquipmentChance.@Tooltip[0]", "The chance that a skeleton, zombie or piglin");
        add("text.autoconfig.artifacts.option.common.entityEquipmentChance.@Tooltip[1]", "spawns with an artifact equipped");
        add("text.autoconfig.artifacts.option.common.everlastingBeefChance", "Everlasting Beef chance");
        add("text.autoconfig.artifacts.option.common.everlastingBeefChance.@Tooltip[0]", "The chance everlasting beef drops when a cow");
        add("text.autoconfig.artifacts.option.common.everlastingBeefChance.@Tooltip[1]", "or mooshroom is killed by a player");
        add("text.autoconfig.artifacts.option.common.modifyHurtSounds", "Modify player hurt sounds");
        add("text.autoconfig.artifacts.option.common.modifyHurtSounds.@Tooltip[0]", "Whether the Kitty Slippers and Bunny Hoppers");
        add("text.autoconfig.artifacts.option.common.modifyHurtSounds.@Tooltip[1]", "change the player's hurt sounds");
        add("text.autoconfig.artifacts.title", "Artifacts Config");
    }

    private void entities() {
        for (RegistrySupplier<EntityType<?>> entityType : ModEntityTypes.ENTITY_TYPES) {
            add(entityType.get().getDescriptionId(), fromSnakeCasedString(entityType.getId().getPath()));
        }
        add(ModSoundEvents.MIMIC_CLOSE.get(), "Mimic closes");
        add(ModSoundEvents.MIMIC_DEATH.get(), "Mimic dies");
        add(ModSoundEvents.MIMIC_HURT.get(), "Mimic hurts");
        add(ModSoundEvents.MIMIC_OPEN.get(), "Mimic hops");
    }

    private void itemConfigs() {
        for (ValueType<?, ?> type : ItemConfigs.getValueTypes()) {
            for (String key : ItemConfigs.getValues(type).keySet()) {
                String[] words = key.split("\\.");
                String name = fromSnakeCasedString(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, words[1]));
                key = words[1].equals("cooldown") || words[1].equals("enabled") ? words[1] : key;
                add("%s.config.items.%s.title".formatted(Artifacts.MOD_ID, key), name);
            }
        }
    }

    private void itemConfigTooltips() {
        for (ValueType<?, ?> type : ItemConfigs.getValueTypes()) {
            for (String key : ItemConfigs.getValues(type).keySet()) {
                List<String> tooltips = ItemConfigs.getTooltips(key);
                if (tooltips.size() == 1) {
                    add("%s.config.items.%s.description".formatted(Artifacts.MOD_ID, key), tooltips.get(0));
                    continue;
                }
                for (int i = 0; i < tooltips.size(); i++) {
                    add("%s.config.items.%s.description.%s".formatted(Artifacts.MOD_ID, key, i), tooltips.get(i));
                }
            }
        }
    }

    private void items() {
        for (RegistrySupplier<Item> item : ModItems.ITEMS) {
            add(item.get(), fromSnakeCasedString(item.getId().getPath()));
        }
        add(ModItems.ANGLERS_HAT.get(), "Angler's Hat");
        add(ModItems.AQUA_DASHERS.get(), "Aqua-Dashers");
    }

    private void tooltips() {
        tooltip("cooldown", "+Cooldown (%s)");
        tooltip("cosmetic", "Cosmetic");
        tooltip("cosmetics_disabled", "Cosmetics disabled (right-click to toggle)");
        tooltip("cosmetics_enabled", "Cosmetics enabled (right-click to toggle)");
        tooltip("item.everlasting_food", "Not consumed when eaten");
        tooltip("item.novelty_drinking_hat", "'Hey! I'm #1, and I let gravity do my drinking!'");
        tooltip("item.umbrella.glider", "Slows your fall when held");
        tooltip("item.umbrella.shield", "Can be used as a shield");
        tooltip("per_food_point_restored", "For every food point restored:");
        tooltip("plus_mob_effect", "+%s");
        tooltip("toggle_keymapping", "Press %s to toggle");
        tooltip("tool_tier.none", "none");
        tooltip("tool_tier.wood", "wood");
        tooltip("tool_tier.stone", "stone");
        tooltip("tool_tier.iron", "iron");
        tooltip("tool_tier.diamond", "diamond");
        tooltip("tool_tier.netherite", "netherite");
        tooltip("when_hurt", "When hurt:");
        tooltip("when_hurt.is_fire", "When hurt by Fire:");
    }

    private void add(SoundEvent soundEvent, String value) {
        //noinspection ConstantConditions
        add("%s.subtitles.%s", BuiltInRegistries.SOUND_EVENT.getKey(soundEvent), value);
    }

    private void add(String key, ResourceLocation id, String value) {
        add(key.formatted(id.getNamespace(), id.getPath()), value);
    }

    private void abilityTooltip(Holder<? extends ArtifactAbility.Type<?>> type, Holder<?> holder, String... s) {
        List<String> list = new java.util.ArrayList<>(List.of(s));
        //noinspection OptionalGetWithoutIsPresent
        list.add(0, holder.unwrapKey().get().location().getPath());
        abilityTooltip(type, list.toArray(String[]::new));
    }

    private void abilityTooltip(Holder<? extends ArtifactAbility.Type<?>> type, String... s) {
        StringBuilder key = new StringBuilder("%s.tooltip.ability.%s");
        for (int i = 0; i < s.length - 1; i++) {
            key.append('.').append(s[i]);
        }
        //noinspection OptionalGetWithoutIsPresent
        add(key.toString(), type.unwrapKey().get().location(), s[s.length - 1]);
    }

    private void tooltip(String key, String value) {
        add("%s.tooltip.%s".formatted(Artifacts.MOD_ID, key), value);
    }

    private static String fromSnakeCasedString(String string) {
        String[] words = string.split("_");
        for (int i = 0; i < words.length; i++) {
            words[i] = Character.toUpperCase(words[i].charAt(0)) + words[i].substring(1);
        }
        return Strings.join(words, " ")
                .replace(" A ", " a ")
                .replace(" An ", " an ")
                .replaceFirst(" In ", " in ")
                .replace(" Of ", " of ")
                .replace(" On ", " on ")
                .replaceFirst(" Per ", " per ")
                .replace(" The ", " the ");
    }
}
