package com.buuz135.simpleclaims.codecs;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

import java.util.UUID;

public class CustomCodecs {

    public static ArrayCodec<UUID> CODEC_ARRAY = new ArrayCodec<>(Codec.UUID_STRING, UUID[]::new);

}
