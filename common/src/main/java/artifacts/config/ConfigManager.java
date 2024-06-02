package artifacts.config;

import artifacts.Artifacts;
import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.config.value.type.EnumValueType;
import artifacts.config.value.type.NumberValueType;
import artifacts.config.value.type.ValueType;
import artifacts.platform.PlatformServices;
import com.electronwill.nightconfig.core.ConfigSpec;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.file.FileWatcher;
import com.electronwill.nightconfig.core.io.ParsingException;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.util.StringRepresentable;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

// see Neoforge ConfigFileTypeHandler
public abstract class ConfigManager {

    protected CommentedFileConfig config;
    protected ConfigSpec spec = new ConfigSpec();
    private final Path configPath;
    private final String name;

    private final Map<String, Value.ConfigValue<?>> values = new HashMap<>();
    private final Map<String, List<String>> tooltips = new HashMap<>();

    private final Map<ValueType<?, ?>, ValueMap<?>> typeToValues = new HashMap<>();

    protected ConfigManager(String fileName) {
        this.name = fileName;
        this.configPath = Path.of(Artifacts.MOD_ID, "%s.toml".formatted(fileName));
    }

    protected void setup() {
        config = CommentedFileConfig.builder(PlatformServices.platformHelper.getConfigDir().resolve(configPath))
                .sync()
                .preserveInsertionOrder()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        Path path = PlatformServices.platformHelper.getConfigDir().resolve(configPath);

        try {
            config.load();
        } catch (ParsingException exception) {
            Artifacts.LOGGER.warn("Failed to load config file {}, attempting to recreate", configPath);
            try {
                backUpConfig(config.getNioPath(), 5);
                Files.delete(config.getNioPath());
                config.load();
            } catch (Throwable t) {
                exception.addSuppressed(t);
                throw new RuntimeException("Failed to load config file " + configPath, exception);
            }
        }

        if (!spec.isCorrect(config)) {
            correctConfigAndSave();
        }

        Artifacts.LOGGER.debug("Loaded config file {}", configPath);
        try {
            FileWatcher.defaultInstance().addWatch(path, new ConfigWatcher());
            Artifacts.LOGGER.debug("Watching config file {} for changes", configPath);
        } catch (IOException exception) {
            throw new RuntimeException("Couldn't watch config file", exception);
        }
    }

    private void correctConfigAndSave() {
        addMissingKeys();
        spec.correct(config);
        config.save();
    }

    public String getName() {
        return name;
    }

    public Map<String, Value.ConfigValue<?>> getValues() {
        return values;
    }

    @SuppressWarnings("unchecked")
    public <T> Map<String, Value.ConfigValue<T>> getValues(ValueType<T, ?> type) {
        ValueMap<T> valueMap = (ValueMap<T>) typeToValues.get(type);
        return valueMap.getMap();
    }

    public List<String> getDescription(String key) {
        return tooltips.get(key);
    }

    public <T, C> T read(ValueType<T, C> type, String key) {
        return type.read(config.get(key));
    }

    public <T, C> void write(ValueType<T, C> type, String key, T value) {
        config.set(key, type.write(value));
    }

    private <T> void reset(String key, Value.ConfigValue<T> value) {
        config.add(key, value.type().write(value.getDefaultValue()));
    }

    protected <T> void readValueFromConfig(String key, Value.ConfigValue<T> value) {
        value.set(read(value.type(), key));
    }

    public void readValuesFromConfig() {
        getValues().forEach(this::readValueFromConfig);
    }

    protected void addMissingKeys() {
        Map<String, Value.ConfigValue<?>> values = getValues();

        List<String> keys = new ArrayList<>(values.keySet());
        Collections.sort(keys);
        for (String key : keys) {
            if (!config.contains(key)) {
                Value.ConfigValue<?> value = values.get(key);
                reset(key, value);
                StringBuilder builder = new StringBuilder();
                for (String tooltip : getDescription(key)) {
                    builder.append(tooltip).append('\n');
                }
                builder.append(value.type().getAllowedValuesComment());
                config.setComment(key, builder.toString());
            }
        }
    }

    public void onConfigChanged() {
        readValuesFromConfig();
    }

    public static void backUpConfig(final Path commentedFileConfig, final int maxBackups) {
        Path bakFileLocation = commentedFileConfig.getParent();
        String bakFileName = FilenameUtils.removeExtension(commentedFileConfig.getFileName().toString());
        String bakFileExtension = FilenameUtils.getExtension(commentedFileConfig.getFileName().toString()) + ".bak";
        Path bakFile = bakFileLocation.resolve(bakFileName + "-1" + "." + bakFileExtension);
        try {
            for (int i = maxBackups; i > 0; i--) {
                Path oldBak = bakFileLocation.resolve(bakFileName + "-" + i + "." + bakFileExtension);
                if (Files.exists(oldBak)) {
                    if (i >= maxBackups)
                        Files.delete(oldBak);
                    else
                        Files.move(oldBak, bakFileLocation.resolve(bakFileName + "-" + (i + 1) + "." + bakFileExtension));
                }
            }
            Files.copy(commentedFileConfig, bakFile);
        } catch (IOException exception) {
            Artifacts.LOGGER.warn("Failed to back up config file {}", commentedFileConfig, exception);
        }
    }

    protected Value.ConfigValue<Boolean> defineBool(String key, String... tooltips) {
        return defineBool(key, true, tooltips);
    }

    protected Value.ConfigValue<Boolean> defineBool(String key, boolean defaultValue, String... tooltips) {
        Value.ConfigValue<Boolean> value = createValue(key, ValueTypes.BOOLEAN, defaultValue, tooltips);
        spec.define(key, defaultValue);
        return value;
    }

    protected Value.ConfigValue<Integer> defineNonNegativeInt(String key, int defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.NON_NEGATIVE_INT, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Integer> defineInt(String key, int defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.INT, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Double> defineAttributeModifier(String key, double defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.ATTRIBUTE_MODIFIER_AMOUNT, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Double> defineNonNegativeDouble(String key, double defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.NON_NEGATIVE_DOUBLE, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Double> defineFraction(String key, double defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.FRACTION, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Integer> defineDuration(String key, int defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.DURATION, defaultValue, tooltips);
    }

    @SuppressWarnings("SameParameterValue")
    protected Value.ConfigValue<Integer> defineEnchantmentLevel(String key, int defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.ENCHANTMENT_LEVEL, defaultValue, tooltips);
    }

    protected Value.ConfigValue<Integer> defineMobEffectLevel(String key, int defaultValue, String... tooltips) {
        return defineNumber(key, ValueTypes.MOB_EFFECT_LEVEL, defaultValue, tooltips);
    }

    private <T extends Number & Comparable<T>> Value.ConfigValue<T> defineNumber(String key, NumberValueType<T> type, T defaultValue, String... tooltips) {
        Value.ConfigValue<T> value = createValue(key, type, defaultValue, tooltips);
        spec.defineInRange(key, defaultValue, type.getMin(), type.getMax());
        return value;
    }

    @SuppressWarnings("SameParameterValue")
    protected <T extends Enum<T> & StringRepresentable> Value.ConfigValue<T> defineEnum(String key, EnumValueType<T> type, T defaultValue, String... tooltips) {
        Value.ConfigValue<T> value = createValue(key, type, defaultValue, tooltips);
        List<String> allowedValues = new ArrayList<>();
        allowedValues.addAll(type.getValues().stream().map(StringRepresentable::getSerializedName).toList());
        allowedValues.addAll(type.getValues().stream().map(StringRepresentable::getSerializedName).map(String::toUpperCase).toList());
        spec.defineInList(key, value.getDefaultValue().getSerializedName(), allowedValues);
        return value;
    }

    protected <T> Value.ConfigValue<T> createValue(String key, ValueType<T, ?> type, T defaultValue, String... tooltips) {
        Value.ConfigValue<T> value = new Value.ConfigValue<>(type, key, defaultValue);
        values.put(key, value);
        this.tooltips.put(key, List.of(tooltips));
        if (!this.typeToValues.containsKey(type)) {
            typeToValues.put(type, new ValueMap<>());
        }
        //noinspection unchecked
        ((ValueMap<T>) typeToValues.get(type)).getMap().put(key, value);
        return value;
    }

    private static class ValueMap<T> {
        private final Map<String, Value.ConfigValue<T>> map = new HashMap<>();

        public Map<String, Value.ConfigValue<T>> getMap() {
            return map;
        }
    }

    private class ConfigWatcher implements Runnable {

        @Override
        public void run() {
            try {
                config.load();
                if (!spec.isCorrect(config)) {
                    Artifacts.LOGGER.warn("Configuration file {} is not correct. Correcting", configPath);
                    correctConfigAndSave();
                }
            } catch (ParsingException exception) {
                throw new RuntimeException("Failed to load config file " + configPath, exception);
            }
            Artifacts.LOGGER.info("Config file {} changed", configPath);
            onConfigChanged();
        }
    }
}
