package artifacts.config.value.type;

import artifacts.config.screen.ConfigEntries;
import artifacts.config.value.Value;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class BooleanValueType extends ValueType<Boolean, Boolean> {

    @Override
    protected Codec<Boolean> valueCodec() {
        return Codec.BOOL;
    }

    @Override
    public StreamCodec<ByteBuf, Boolean> valueStreamCodec() {
        return ByteBufCodecs.BOOL;
    }

    @Override
    public boolean isCorrect(Boolean value) {
        return true;
    }

    @Override
    public String makeError(Boolean value) {
        return "";
    }

    @Override
    public String getAllowedValuesComment() {
        return "Allowed Values: true, false";
    }

    @Override
    public Boolean read(Boolean value) {
        return value;
    }

    @Override
    public Boolean write(Boolean value) {
        return value;
    }

    public MapCodec<Value<Boolean>> enabledField() {
        return codec().optionalFieldOf("enabled", Value.Constant.TRUE);
    }

    @Override
    public ConfigEntries.ConfigEntryFactory<Boolean> getConfigEntryFactory() {
        return ConfigEntries.booleanConfigEntryFactory();
    }
}
