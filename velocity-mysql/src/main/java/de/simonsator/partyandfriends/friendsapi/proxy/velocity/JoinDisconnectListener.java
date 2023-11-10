package de.simonsator.partyandfriends.friendsapi.proxy.velocity;

import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import com.velocitypowered.api.event.connection.PostLoginEvent;
import de.simonsator.partyandfriends.velocity.api.adapter.BukkitBungeeAdapter;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayer;
import de.simonsator.partyandfriends.velocity.api.pafplayers.PAFPlayerManager;
import de.simonsator.partyandfriends.velocity.api.system.WaitForTasksToFinish;
import de.simonsator.partyandfriends.velocity.main.Main;
import de.simonsator.partyandfriends.velocity.pafplayers.mysql.PAFPlayerMySQL;

public class JoinDisconnectListener extends WaitForTasksToFinish {
	private final OnlineBridgeBungeeMySQLConnection CONNECTION;
	private final FriendsAPIBridgeMain MAIN;

	public JoinDisconnectListener(OnlineBridgeBungeeMySQLConnection connection, FriendsAPIBridgeMain pMain) {
		this.CONNECTION = connection;
		MAIN = pMain;
	}

	@Subscribe
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

	@Subscribe
	public void onPlayerDisconnect(final DisconnectEvent pEvent) {
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
