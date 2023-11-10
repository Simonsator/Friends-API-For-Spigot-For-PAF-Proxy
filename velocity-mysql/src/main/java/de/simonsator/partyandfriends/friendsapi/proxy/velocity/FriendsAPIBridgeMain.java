package de.simonsator.partyandfriends.friendsapi.proxy.velocity;

import de.simonsator.partyandfriends.velocity.api.PAFExtension;
import de.simonsator.partyandfriends.velocity.main.Main;

import java.nio.file.Path;
import java.sql.SQLException;

public class FriendsAPIBridgeMain extends PAFExtension {

	public FriendsAPIBridgeMain(Path folder) {
		super(folder);
	}

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

	@Override
	public String getName() {
		return "Friends-API-MySQL-Proxy-Bridge";
	}

}
