package artifacts.ability;

import artifacts.component.SwimData;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.platform.PlatformServices;
import artifacts.registry.ModAbilities;
import artifacts.registry.ModKeyMappings;
import artifacts.registry.ModSoundEvents;
import artifacts.util.AbilityHelper;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public record SwimInAirAbility(Value<Integer> flightDuration, Value<Integer> rechargeDuration) implements ArtifactAbility {

    public static final MapCodec<SwimInAirAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ValueTypes.DURATION.codec().fieldOf("flight_duration").forGetter(SwimInAirAbility::flightDuration),
            ValueTypes.DURATION.codec().fieldOf("recharge_duration").forGetter(SwimInAirAbility::rechargeDuration)
    ).apply(instance, SwimInAirAbility::new));

    public static final StreamCodec<ByteBuf, SwimInAirAbility> STREAM_CODEC = StreamCodec.composite(
            ValueTypes.DURATION.streamCodec(),
            SwimInAirAbility::flightDuration,
            ValueTypes.DURATION.streamCodec(),
            SwimInAirAbility::rechargeDuration,
            SwimInAirAbility::new
    );

    public static void onHeliumFlamingoTick(Player player) {
        SwimData swimData = PlatformServices.platformHelper.getSwimData(player);
        if (swimData == null) {
            return;
        }
        int maxFlightTime = getFlightDuration(player);
        boolean shouldSink = AbilityHelper.hasAbilityActive(ModAbilities.SINKING.value(), player);
        boolean canFly = maxFlightTime > 0;
        if (swimData.isSwimming()) {
            if (swimData.getSwimTime() > maxFlightTime
                    || player.isInWater() && !player.isSwimming() && !shouldSink
                    || (!player.isInWater() || shouldSink) && player.onGround()
            ) {
                swimData.setSwimming(player, false);
                if (!player.onGround() && !player.isInWater()) {
                    player.playSound(ModSoundEvents.POP.value(), 0.5F, 0.75F);
                }
            }

            if (canFly && !PlatformServices.platformHelper.isEyeInWater(player)) {
                if (!player.getAbilities().invulnerable) {
                    swimData.setSwimTime(swimData.getSwimTime() + 1);
                }
            }
        } else if (swimData.getSwimTime() < 0) {
            int rechargeTime = getRechargeDuration(player);
            swimData.setSwimTime(
                    swimData.getSwimTime() < -rechargeTime ? -rechargeTime : swimData.getSwimTime() + 1
            );
        }
    }

    public static int getFlightDuration(LivingEntity entity) {
        return AbilityHelper.maxInt(ModAbilities.SWIM_IN_AIR.value(), entity, ability -> ability.flightDuration().get() * 20, false);
    }

    public static int getRechargeDuration(LivingEntity entity) {
        return Math.max(20, AbilityHelper.maxInt(ModAbilities.SWIM_IN_AIR.value(), entity, ability -> ability.rechargeDuration().get() * 20, false));
    }

    @Override
    public Type<?> getType() {
        return ModAbilities.SWIM_IN_AIR.value();
    }

    @Override
    public boolean isNonCosmetic() {
        return flightDuration().get() > 0;
    }

    @Override
    public void addAbilityTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("swimming"));
    }

    @Override
    public void addToggleKeyTooltip(List<MutableComponent> tooltip) {
        tooltip.add(tooltipLine("keymapping", ModKeyMappings.getHeliumFlamingoKey().getTranslatedKeyMessage()));
    }
}
