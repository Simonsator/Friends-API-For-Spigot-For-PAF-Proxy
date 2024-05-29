package de.simonsator.partyandfriends.friendsapi.proxy.bungee;

import de.simonsator.partyandfriends.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.api.system.WaitForTasksToFinish;
import de.simonsator.partyandfriends.main.Main;
import de.simonsator.partyandfriends.pafplayers.mysql.PAFPlayerMySQL;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class JoinDisconnectListener extends WaitForTasksToFinish implements Listener {
	private final OnlineBridgeBungeeMySQLConnection CONNECTION;
	private final FriendsAPIBridgeMain MAIN;

	public JoinDisconnectListener(OnlineBridgeBungeeMySQLConnection connection, FriendsAPIBridgeMain pMain) {
		this.CONNECTION = connection;
		MAIN = pMain;
	}

	@EventHandler
	public void onJoin(PostLoginEvent pEvent) {
		if (Main.getInstance().isShuttingDown()) {
			return;
		}
		BukkitBungeeAdapter.getInstance().schedule(MAIN, () -> {
			if (Main.getInstance().isShuttingDown()) {
				return;
			}
			try {
				taskStarts();
				if (!pEvent.getPlayer().isConnected()) {
					return;
				}
				PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer().getUniqueId());
				if (player.doesExist()) {
					PAFPlayerMySQL pafPlayer = (PAFPlayerMySQL) player;
					CONNECTION.playerWentOnline(pafPlayer.getPlayerID());
				}
			} finally {
				taskFinished();
			}
		}, 1);
	}

	@EventHandler
	public void onPlayerDisconnect(final PlayerDisconnectEvent pEvent) {
		if (Main.getInstance().isShuttingDown()) {
			return;
		}
		BukkitBungeeAdapter.getInstance().runAsync(MAIN, () -> {
			try {
				taskStarts();
				PAFPlayer player = PAFPlayerManager.getInstance().getPlayer(pEvent.getPlayer().getUniqueId());
				if (player.doesExist()) {
					PAFPlayerMySQL pafPlayer = (PAFPlayerMySQL) player;
					CONNECTION.playerWentOffline(pafPlayer.getPlayerID());
				}
			} finally {
				taskFinished();
			}
		});
	}

}
