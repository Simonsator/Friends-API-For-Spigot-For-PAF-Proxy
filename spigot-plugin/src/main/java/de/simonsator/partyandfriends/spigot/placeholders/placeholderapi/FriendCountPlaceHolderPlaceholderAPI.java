package de.simonsator.partyandfriends.spigot.placeholders.placeholderapi;

import de.simonsator.partyandfriends.spigot.api.FriendCountPlaceHolder;
import de.simonsator.partyandfriends.spigot.api.FriendOnlineCountPlaceholder;
import de.simonsator.partyandfriends.spigot.api.FriendRequestCountPlaceHolder;
import de.simonsator.partyandfriends.spigot.api.exceptions.FriendsAPIBridgeNotInstalledException;
import de.simonsator.partyandfriends.spigot.error.ErrorReporter;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FriendCountPlaceHolderPlaceholderAPI extends PlaceholderExpansion implements FriendCountPlaceHolder, FriendRequestCountPlaceHolder, FriendOnlineCountPlaceholder {
	private final boolean IS_ONLINE_SERVER;
	private boolean onlineCountErrorRegistered = false;

	@Override
	public boolean persist() {
		return true;
	}

	@Override
	public boolean canRegister() {
		return true;
	}

	@Override
	public String getAuthor() {
		return "Simonsator";
	}

	@Override
	public String getIdentifier() {
		return "friendsapi";
	}

	@Override
	public String getVersion() {
		return "1.0";
	}

	public FriendCountPlaceHolderPlaceholderAPI(Plugin pPlugin) {
		IS_ONLINE_SERVER = pPlugin.getConfig().getBoolean("IsOnlineServer");
	}

	@Override
	public String onPlaceholderRequest(Player pPlayer, String pIdentifier) {
		switch (pIdentifier) {
			case "friendcount":
				if (IS_ONLINE_SERVER) {
					return getFriendCount(pPlayer.getUniqueId()).toString();
				} else {
					return getFriendCount(pPlayer.getName()).toString();
				}
			case "friendrequestcount":
				if (IS_ONLINE_SERVER) {
					return getFriendRequestCount(pPlayer.getUniqueId()).toString();
				} else {
					return getFriendRequestCount(pPlayer.getName()).toString();
				}
			case "onlinefriendcount":
				try {
					if (IS_ONLINE_SERVER) {
						return getOnlineFriendCount(pPlayer.getUniqueId()).toString();
					} else {
						return getOnlineFriendCount(pPlayer.getName()).toString();
					}
				} catch (FriendsAPIBridgeNotInstalledException e) {
					if (!onlineCountErrorRegistered) {
						onlineCountErrorRegistered = true;
						new ErrorReporter(e.getMessage());
					}
				}
			default:
				return null;
		}
	}
}

