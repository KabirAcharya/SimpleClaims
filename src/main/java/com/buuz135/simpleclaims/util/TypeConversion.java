package com.buuz135.simpleclaims.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;


public final class TypeConversion {

    private TypeConversion() {}

    private static final ConcurrentHashMap<String, Function<String, Object>> REGISTRY = new ConcurrentHashMap<>();

    static {
        Function<String, Object> intParser = Integer::parseInt;
        register("int", intParser);
        register("integer", intParser);
        register("java.lang.integer", intParser);

        Function<String, Object> longParser = Long::parseLong;
        register("long", longParser);
        register("java.lang.long", longParser);

        Function<String, Object> shortParser = Short::parseShort;
        register("short", shortParser);
        register("java.lang.short", shortParser);

        Function<String, Object> byteParser = Byte::parseByte;
        register("byte", byteParser);
        register("java.lang.byte", byteParser);

        Function<String, Object> doubleParser = Double::parseDouble;
        register("double", doubleParser);
        register("java.lang.double", doubleParser);

        Function<String, Object> floatParser = Float::parseFloat;
        register("float", floatParser);
        register("java.lang.float", floatParser);

        Function<String, Object> booleanParser = TypeConversion::parseBooleanFlexible;
        register("bool", booleanParser);
        register("boolean", booleanParser);
        register("java.lang.boolean", booleanParser);

        Function<String, Object> stringParser = s -> s; // pass-through
        register("string", stringParser);
        register("java.lang.string", stringParser);

        Function<String, Object> uuidParser = UUID::fromString;
        register("uuid", uuidParser);
        register("java.util.uuid", uuidParser);

        Function<String, Object> bigIntegerParser = BigInteger::new;
        register("biginteger", bigIntegerParser);
        register("java.math.biginteger", bigIntegerParser);

        Function<String, Object> bigDecimalParser = BigDecimal::new;
        register("bigdecimal", bigDecimalParser);
        register("java.math.bigdecimal", bigDecimalParser);
    }


    public static void register(String typeName, Function<String, Object> parser) {
        if (typeName == null || parser == null) return;
        REGISTRY.put(normalize(typeName), parser);
    }

    public static void unregister(String typeName) {
        if (typeName == null) return;
        REGISTRY.remove(normalize(typeName));
    }


    public static Map<String, Function<String, Object>> getRegistryView() {
        return Collections.unmodifiableMap(REGISTRY);
    }

    public static Object convert(String typeName, String value) {
        return tryConvert(typeName, value)
                .orElseThrow(() -> new IllegalArgumentException("Unsupported type or invalid value: type=" + typeName + ", value='" + value + "'"));
    }

    public static Optional<Object> tryConvert(String typeName, String value) {
        if (typeName == null) return Optional.empty();
        String key = normalize(typeName);
        Function<String, Object> parser = REGISTRY.get(key);
        if (parser == null) return Optional.empty();
        try {
            return Optional.ofNullable(parser.apply(value));
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    private static String normalize(String typeName) {
        return typeName.trim().toLowerCase(Locale.ROOT);
    }

    private static boolean parseBooleanFlexible(String value) {
        if (value == null) return false;
        String v = value.trim().toLowerCase(Locale.ROOT);
        return switch (v) {
            case "true", "t", "yes", "y", "1" -> true;
            case "false", "f", "no", "n", "0" -> false;
            default ->
                // Fall back to Boolean.parseBoolean behavior
                    Boolean.parseBoolean(v);
        };
    }
}
