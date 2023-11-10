package de.simonsator.partyandfriends.spigot.api.exceptions;

public class FriendsAPIBridgeNotInstalledException extends Exception {
	public FriendsAPIBridgeNotInstalledException() {
		super("§cTo use the %friendsapi_onlinefriendcount% placeholder you have to have this plugin installed on the spigot server and the bungeecord/velocity server. If you need further help contact Simonsator via Discord (@Simonsator#5834), PM him (https://www.spigotmc.org/conversations/add?to=simonsator ) or write an email to him (support@simonsator.de). Please don't forget to send him the latest.log file. Also please don't write a bad review without giving him 24 hours time to fix the problem.");
	}
}
