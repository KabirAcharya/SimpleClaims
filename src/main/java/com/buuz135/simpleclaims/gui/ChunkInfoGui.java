package com.buuz135.simpleclaims.gui;

import com.buuz135.simpleclaims.claim.ClaimManager;
import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;
import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.protocol.CustomPageLifetime;
import com.hypixel.hytale.server.core.Message;
import com.hypixel.hytale.server.core.asset.util.ColorParseUtil;
import com.hypixel.hytale.server.core.entity.entities.player.pages.InteractiveCustomUIPage;
import com.hypixel.hytale.server.core.ui.builder.UICommandBuilder;
import com.hypixel.hytale.server.core.ui.builder.UIEventBuilder;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.awt.*;
import java.util.ArrayList;

public class ChunkInfoGui extends InteractiveCustomUIPage<ChunkInfoGui.ChunkInfoData> {

    private final int chunkX;
    private final int chunkZ;
    private final String dimension;

    public ChunkInfoGui(@NonNullDecl PlayerRef playerRef, String dimension, int chunkX, int chunkZ) {
        super(playerRef, CustomPageLifetime.CanDismiss, ChunkInfoData.CODEC);
        this.chunkX = chunkX;
        this.chunkZ = chunkZ;
        this.dimension = dimension;
    }

    @Override
    public void handleDataEvent(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl Store<EntityStore> store, @NonNullDecl ChunkInfoData data) {
        super.handleDataEvent(ref, store, data);

        this.sendUpdate();
    }

    @Override
    public void build(@NonNullDecl Ref<EntityStore> ref, @NonNullDecl UICommandBuilder uiCommandBuilder, @NonNullDecl UIEventBuilder uiEventBuilder, @NonNullDecl Store<EntityStore> store) {
        uiCommandBuilder.append("Pages/ChunkVisualizer.ui");
        var player = store.getComponent(ref, PlayerRef.getComponentType());
        var playerParty = ClaimManager.getInstance().getPartyFromPlayer(player.getUuid());
        for (int z = 0; z <= 8*2; z++) {
            uiCommandBuilder.appendInline("#ChunkCards", "Group { LayoutMode: Left; Anchor: (Bottom: 0); }");
            for (int x = 0; x <= 8*2; x++) {
                uiCommandBuilder.append("#ChunkCards[" + z  + "]", "Pages/ChunkEntry.ui");
                var chunk = ClaimManager.getInstance().getChunk(dimension, chunkX + x - 8, chunkZ + z - 8);
                if ((z - 8) == 0 && (x - 8) == 0) {
                    uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].Text", "+");
                }
                if (chunk != null) {
                    var partyInfo = ClaimManager.getInstance().getPartyById(chunk.getPartyOwner());
                    if (partyInfo != null) {
                        var color = new Color(partyInfo.getColor());
                        color = new Color(color.getRed(), color.getGreen(), color.getBlue(), 128);
                        var messageList = new ArrayList<>();
                        messageList.add(Message.raw("Found <color is='#a020f0'><b>Cursed</b></color> items are lost on <color is='#9e0b0b'><b>Death</b></color>"));
                        uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].Background.Color", ColorParseUtil.colorToHexAlpha(color));
                        uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].OutlineColor", ColorParseUtil.colorToHexAlpha(color));
                        uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].OutlineSize", 1);
                        //uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].TooltipTextSpans", "Found <color is='#a020f0'><b>Cursed</b></color> items are lost on <color is='#9e0b0b'><b>Death</b></color>");
                        var tooltipLines = "Owner: " + partyInfo.getName() + "\nDescription: " + partyInfo.getDescription() ;
                        if (playerParty != null && playerParty.getId().equals(partyInfo.getId())) {
                            tooltipLines += "\n\n*Right Click to Unclaim*";
                        }
                        uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].TooltipText", tooltipLines);
                    }
                } else {
                    var tooltipLines = "Wilderness" ;
                    if (playerParty != null) {
                        tooltipLines += "\n\n*Left Click to claim*";
                    }
                    uiCommandBuilder.set("#ChunkCards[" + z + "][" + x + "].TooltipText", tooltipLines);
                }
            }
        }
    }

    public static class ChunkInfoData {
        static final String KEY_NAME = "@Name";
        static final String KEY_DESCRIPTION = "@Description";
        static final String KEY_SAVE = "Save";
        static final String KEY_CANCEL = "Cancel";
        static final String KEY_REMOVE_BUTTON_ACTION = "RemoveButtonAction";
        public static final BuilderCodec<ChunkInfoData> CODEC = BuilderCodec.<ChunkInfoData>builder(ChunkInfoData.class, ChunkInfoData::new)
                .addField(new KeyedCodec<>(KEY_NAME, Codec.STRING), (searchGuiData, s) -> searchGuiData.name = s, searchGuiData -> searchGuiData.name)
                .addField(new KeyedCodec<>(KEY_DESCRIPTION, Codec.STRING), (searchGuiData, s) -> searchGuiData.description = s, searchGuiData -> searchGuiData.description)
                .addField(new KeyedCodec<>(KEY_SAVE, Codec.STRING), (searchGuiData, s) -> searchGuiData.save = s, searchGuiData -> searchGuiData.save)
                .addField(new KeyedCodec<>(KEY_CANCEL, Codec.STRING), (searchGuiData, s) -> searchGuiData.cancel = s, searchGuiData -> searchGuiData.cancel)
                .addField(new KeyedCodec<>(KEY_REMOVE_BUTTON_ACTION, Codec.STRING), (searchGuiData, s) -> searchGuiData.removeButtonAction = s, searchGuiData -> searchGuiData.removeButtonAction)

                .build();

        private String name;
        private String description;
        private String save;
        private String cancel;
        private String removeButtonAction;
    }
}
