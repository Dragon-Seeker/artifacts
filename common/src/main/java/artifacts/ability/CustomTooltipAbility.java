package artifacts.ability;

import artifacts.registry.ModAbilities;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.List;

public record CustomTooltipAbility(Component tooltip) implements ArtifactAbility {

    public static final MapCodec<CustomTooltipAbility> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            ComponentSerialization.FLAT_CODEC.fieldOf("tooltip").forGetter(CustomTooltipAbility::tooltip)
    ).apply(instance, CustomTooltipAbility::new));

    public static final StreamCodec<ByteBuf, CustomTooltipAbility> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(ComponentSerialization.CODEC),
            CustomTooltipAbility::tooltip,
            CustomTooltipAbility::new
    );

    @Override
    public Type<?> getType() {
        return ModAbilities.CUSTOM_TOOLTIP.get();
    }

    @Override
    public boolean isNonCosmetic() {
        return false;
    }

    @Override
    public void addTooltipIfNonCosmetic(List<MutableComponent> tooltip) {
        tooltip.add(this.tooltip.copy());
    }
}
