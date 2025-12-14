package com.buuz135.simpleclaims.commands;

import com.hypixel.hytale.server.core.Message;

public class CommandMessages {

    public static final Message NOT_IN_A_PARTY = Message.translation("commands.errors.simpleclaims.playerNotInParty");
    public static final Message IN_A_PARTY = Message.translation("commands.errors.simpleclaims.playerInParty");

    public static final Message ALREADY_CLAIMED_BY_YOU = Message.translation("commands.errors.simpleclaims.alreadyClaimedByYou");
    public static final Message ALREADY_CLAIMED_BY_ANOTHER_PLAYER = Message.translation("commands.errors.simpleclaims.alreadyClaimedByAnotherPlayer");
    public static final Message NOT_CLAIMED = Message.translation("commands.errors.simpleclaims.notClaimed");
    public static final Message NOT_YOUR_CLAIM = Message.translation("commands.errors.simpleclaims.notYourClaim");
    public static final Message UNCLAIMED = Message.translation("commands.errors.simpleclaims.unclaimed");

    public static final Message PARTY_CREATED = Message.translation("commands.simpleclaims.partyCreated");

    public static final Message NOT_ENOUGH_CHUNKS = Message.translation("commands.simpleclaims.notEnoughChunks");

    public static final Message NOW_USING_PARTY = Message.translation("commands.simpleclaims.nowUsingParty");

    public static final Message ADMIN_PARTY_NOT_SELECTED = Message.translation("commands.simpleclaims.admin.partyNotSelected");
    public static final Message PARTY_NOT_FOUND = Message.translation("commands.simpleclaims.admin.partyNotFound");
}
