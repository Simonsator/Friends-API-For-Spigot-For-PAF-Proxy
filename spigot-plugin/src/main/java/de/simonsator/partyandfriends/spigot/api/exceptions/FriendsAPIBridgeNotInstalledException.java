package de.simonsator.partyandfriends.spigot.api.exceptions;

public class FriendsAPIBridgeNotInstalledException extends Exception {
	public FriendsAPIBridgeNotInstalledException() {
		super("The plugin needs to be installed on the proxy.");
	}
}
