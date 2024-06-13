package artifacts.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.registry.ModAbilities;
import artifacts.registry.ModTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Holder;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public record RemoveBadEffectsAbility(Value<Boolean> enabled, Value<Integer> maxEffectDuration) implements ArtifactAbility {

    public static final MapCodec<RemoveBadEffectsAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ValueTypes.enabledField().forGetter(RemoveBadEffectsAbility::enabled),
            ValueTypes.DURATION.codec().fieldOf("duration").forGetter(RemoveBadEffectsAbility::maxEffectDuration)
    ).apply(instance, RemoveBadEffectsAbility::new));

    public static final StreamCodec<ByteBuf, RemoveBadEffectsAbility> STREAM_CODEC = StreamCodec.composite(
            ValueTypes.BOOLEAN.streamCodec(),
            RemoveBadEffectsAbility::enabled,
            ValueTypes.DURATION.streamCodec(),
            RemoveBadEffectsAbility::maxEffectDuration,
            RemoveBadEffectsAbility::new
    );

    @Override
    public Type<?> getType() {
        return ModAbilities.REMOVE_BAD_EFFECTS.value();
    }

    @Override
    public boolean isNonCosmetic() {
        return enabled().get();
    }

    @Override
    public void wornTick(LivingEntity entity, boolean isOnCooldown, boolean isActive) {
        if (!isActive) {
            return;
        }
        Map<Holder<MobEffect>, MobEffectInstance> effects = new HashMap<>();

        int maxEffectDuration = maxEffectDuration().get() * 20;
        entity.getActiveEffectsMap().forEach((effect, instance) -> {
            if (ModTags.isInTag(effect.value(), ModTags.ANTIDOTE_VESSEL_CANCELLABLE) && instance.getDuration() > maxEffectDuration) {
                effects.put(effect, instance);
            }
        });

        effects.forEach((effect, instance) -> {
            entity.removeEffectNoUpdate(effect);
            if (maxEffectDuration > 0) {
                entity.addEffect(new MobEffectInstance(effect, maxEffectDuration, instance.getAmplifier(), instance.isAmbient(), instance.isVisible(), instance.showIcon()));
            }
        });
    }
}
