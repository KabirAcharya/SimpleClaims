package com.buuz135.simpleclaims.claim.chunk;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.codec.codecs.array.ArrayCodec;

import java.util.UUID;

public class ChunkInfo {

    public static final BuilderCodec<ChunkInfo> CODEC = BuilderCodec.<ChunkInfo>builder(ChunkInfo.class, ChunkInfo::new)
            .append(new KeyedCodec<>("UUID", Codec.UUID_STRING),
                    (chunkInfo, uuid, extraInfo) -> chunkInfo.setPartyOwner(uuid),
                    (chunkInfo, extraInfo) -> chunkInfo.getPartyOwner()).add()
            .append(new KeyedCodec<>("ChunkX", Codec.INTEGER),
                    (chunkInfo, uuid, extraInfo) -> chunkInfo.setChunkX(uuid),
                    (chunkInfo, extraInfo) -> chunkInfo.getChunkX()).add()
            .append(new KeyedCodec<>("ChunkY", Codec.INTEGER),
                    (chunkInfo, uuid, extraInfo) -> chunkInfo.setChunkZ(uuid),
                    (chunkInfo, extraInfo) -> chunkInfo.getChunkZ()).add()
            .build();
    public static ArrayCodec<ChunkInfo> CODEC_ARRAY = new ArrayCodec<>(CODEC, ChunkInfo[]::new);

    public static String formatCoordinates(int chunkX, int chunkZ){
        return chunkX + ":" + chunkZ;
    }

    private UUID partyOwner;
    private int chunkX;
    private int chunkZ;

    public ChunkInfo(UUID partyOwner, int chunkX, int chunkY) {
        this.partyOwner = partyOwner;
        this.chunkX = chunkX;
        this.chunkZ = chunkY;
    }

    public ChunkInfo() {
        this(UUID.randomUUID(), 0, 0);
    }

    public UUID getPartyOwner() {
        return partyOwner;
    }

    public void setPartyOwner(UUID partyOwner) {
        this.partyOwner = partyOwner;
    }

    public int getChunkX() {
        return chunkX;
    }

    public void setChunkX(int chunkX) {
        this.chunkX = chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void setChunkZ(int chunkZ) {
        this.chunkZ = chunkZ;
    }

    public static final class DimensionStorage {

        public static final BuilderCodec<DimensionStorage> CODEC = BuilderCodec.builder(DimensionStorage.class, DimensionStorage::new)
                .append(new KeyedCodec<>("Dimensions", ChunkInfoStorage.CODEC_ARRAY),
                        (dimensionStorage, infoStorages, extraInfo) -> dimensionStorage.setChunkInfoStorages(infoStorages),
                        (dimensionStorage, extraInfo) -> dimensionStorage.getChunkInfoStorages()).add()
                .build();

        private ChunkInfoStorage[] chunkInfoStorages;

        public DimensionStorage(ChunkInfoStorage[] chunkInfoStorages) {
            this.chunkInfoStorages = chunkInfoStorages;
        }

        public DimensionStorage() {
            this(new ChunkInfoStorage[0]);
        }

        public ChunkInfoStorage[] getChunkInfoStorages() {
            return chunkInfoStorages;
        }

        public void setChunkInfoStorages(ChunkInfoStorage[] chunkInfoStorages) {
            this.chunkInfoStorages = chunkInfoStorages;
        }
    }

    public static class ChunkInfoStorage {

        public static final BuilderCodec<ChunkInfoStorage> CODEC = BuilderCodec.builder(ChunkInfoStorage.class, ChunkInfoStorage::new)
                .append(new KeyedCodec<>("Dimension", Codec.STRING),
                        (chunkInfoStorage, string, extraInfo) -> chunkInfoStorage.setDimension(string),
                        (chunkInfoStorage, extraInfo) -> chunkInfoStorage.getDimension()).add()
                .append(new KeyedCodec<>("ChunkInfo", ChunkInfo.CODEC_ARRAY),
                        (chunkInfoStorage, chunkInfos, extraInfo) -> chunkInfoStorage.setChunkInfos(chunkInfos),
                        (chunkInfoStorage, extraInfo) -> chunkInfoStorage.getChunkInfos()).add()
                .build();
        public static ArrayCodec<ChunkInfoStorage> CODEC_ARRAY = new ArrayCodec<>(CODEC, ChunkInfoStorage[]::new);


        public String dimension;
        private ChunkInfo[] chunkInfos;

        public ChunkInfoStorage() {
            this("", new ChunkInfo[0]);
        }

        public ChunkInfoStorage(String dimension, ChunkInfo[] infos) {
            this.dimension = dimension;
            this.chunkInfos = infos;
        }

        public String getDimension() {
            return dimension;
        }

        public void setDimension(String dimension) {
            this.dimension = dimension;
        }

        public ChunkInfo[] getChunkInfos() {
            return chunkInfos;
        }

        public void setChunkInfos(ChunkInfo[] chunkInfos) {
            this.chunkInfos = chunkInfos;
        }
    }
}
