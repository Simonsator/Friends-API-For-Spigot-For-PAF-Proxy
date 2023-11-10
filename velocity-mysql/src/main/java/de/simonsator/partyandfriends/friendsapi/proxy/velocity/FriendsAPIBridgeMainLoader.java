package de.simonsator.partyandfriends.friendsapi.proxy.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Dependency;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import de.simonsator.partyandfriends.velocity.VelocityExtensionLoadingInfo;
import de.simonsator.partyandfriends.velocity.main.PAFPlugin;

import java.nio.file.Path;

@Plugin(id = "friends-api-mysql-proxy-bridge", name = "Friends-API-MySQL-Proxy-Bridge", version = "1.6.0-RELEASE",
		description = "Loads Friends-API-MySQL-Proxy-Bridge", authors = {"Simonsator"}, dependencies = {@Dependency(id = "partyandfriends")})
public class FriendsAPIBridgeMainLoader {
	private final Path folder;

	@Inject
	public FriendsAPIBridgeMainLoader(@DataDirectory final Path folder) {
		this.folder = folder;
	}

	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent event) {
		PAFPlugin.loadExtension(new VelocityExtensionLoadingInfo(new FriendsAPIBridgeMain(folder),
				"friends-api-mysql-proxy-bridge", "Loads Friends-API-MySQL-Proxy-Bridge", "1.6.0-RELEASE", "Simonsator"));
	}
}
