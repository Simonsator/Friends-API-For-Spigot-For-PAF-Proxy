package de.simonsator.partyandfriends.spigot.api;

import de.simonsator.partyandfriends.spigot.api.exceptions.FriendsAPIBridgeNotInstalledException;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.spigot.api.pafplayers.PAFPlayerManager;

import java.util.UUID;

public interface FriendOnlineCountPlaceholder {
	default Integer getOnlineFriendCount(UUID pPlayer) throws FriendsAPIBridgeNotInstalledException {
		return getOnlineFriendCount(PAFPlayerManager.getInstance().getPlayer(pPlayer));
	}

	default Integer getOnlineFriendCount(String pPlayer) throws FriendsAPIBridgeNotInstalledException {
		return getOnlineFriendCount(PAFPlayerManager.getInstance().getPlayer(pPlayer));
	}

	default Integer getOnlineFriendCount(PAFPlayer pPlayer) throws FriendsAPIBridgeNotInstalledException {
		return pPlayer.getOnlineFriendsCount();
	}

}
