package com.buuz135.simpleclaims.claim.party;

import com.buuz135.simpleclaims.util.TypeConversion;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

import java.util.Optional;

public class PartyOverride {

    public static final BuilderCodec<PartyOverride> CODEC = BuilderCodec.<PartyOverride>builder(PartyOverride.class, PartyOverride::new)
            .addField(new KeyedCodec<>("Type", Codec.STRING), (partyOverrideValue, s) -> partyOverrideValue.type = s, searchGuiData -> searchGuiData.type)
            .addField(new KeyedCodec<>("Value", PartyOverrideValue.CODEC), (partyOverrideValue, s) -> partyOverrideValue.value = s, searchGuiData -> searchGuiData.value)
            .build();

    public static final ArrayCodec<PartyOverride> ARRAY_CODEC = new ArrayCodec<>(CODEC, PartyOverride[]::new);

    private String type;
    private PartyOverrideValue value;

    public PartyOverride(String type, PartyOverrideValue value) {
        this.type = type;
        this.value = value;
    }

    public PartyOverride() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PartyOverrideValue getValue() {
        return value;
    }

    public void setValue(PartyOverrideValue value) {
        this.value = value;
    }

    public static class PartyOverrideValue{
        
        public static final BuilderCodec<PartyOverrideValue> CODEC = BuilderCodec.<PartyOverrideValue>builder(PartyOverrideValue.class, PartyOverrideValue::new)
                .addField(new KeyedCodec<>("Type", Codec.STRING), (partyOverrideValue, s) -> partyOverrideValue.type = s, searchGuiData -> searchGuiData.type)
                .addField(new KeyedCodec<>("Value", Codec.STRING), (partyOverrideValue, s) -> partyOverrideValue.value = s, searchGuiData -> searchGuiData.value)
                .build();

        private String type;
        private String value;

        public PartyOverrideValue(String type, String value) {
            this.type = type;
            this.value = value;
        }

        public PartyOverrideValue(String type, int value) {
            this(type, String.valueOf(value));
        }

        public PartyOverrideValue(String type, boolean value) {
            this(type, String.valueOf(value));
        }

        public PartyOverrideValue() {
        }

        public String getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public Object getTypedValue() {
            return TypeConversion.convert(this.type, this.value);
        }

        public Optional<Object> tryGetTypedValue() {
            return TypeConversion.tryConvert(this.type, this.value);
        }

        public void setValue(String value) {
            this.value = value;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
