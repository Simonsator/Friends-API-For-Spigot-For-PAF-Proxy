package de.simonsator.partyandfriends.friendsapi.proxy.bungee;

import de.simonsator.partyandfriends.api.PAFExtension;
import de.simonsator.partyandfriends.main.Main;

import java.sql.SQLException;

public class FriendsAPIBridgeMain extends PAFExtension {

	@Override
	public void onEnable() {
		try {
			OnlineBridgeBungeeMySQLConnection connection = new OnlineBridgeBungeeMySQLConnection(Main.getInstance().getGeneralConfig().getString("MySQL.TablePrefix"));
			connection.cleanTable();
			getAdapter().registerListener(new JoinDisconnectListener(connection, this), this);
			registerAsExtension();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
