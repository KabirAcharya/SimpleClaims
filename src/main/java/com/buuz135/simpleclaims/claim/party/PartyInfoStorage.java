package com.buuz135.simpleclaims.claim.party;

import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

import java.util.Objects;

public class PartyInfoStorage {

    public static final BuilderCodec<PartyInfoStorage> CODEC = BuilderCodec.builder(PartyInfoStorage.class, PartyInfoStorage::new)
            .append(new KeyedCodec<>("Parties", PartyInfo.ARRAY_CODEC),
                    (partyInfo, uuid, extraInfo) -> partyInfo.infos = uuid,
                    (partyInfo, extraInfo) -> partyInfo.infos()).add()
            .build();

    private PartyInfo[] infos;


    public PartyInfoStorage(PartyInfo[] infos) {
        this.infos = infos;
    }

    public PartyInfoStorage() {
        this(new PartyInfo[0]);
    }

    public PartyInfo[] infos() {
        return infos;
    }

    @Override
    public String toString() {
        return "PartyInfoStorage[" +
                "infos=" + infos + ']';
    }


}
