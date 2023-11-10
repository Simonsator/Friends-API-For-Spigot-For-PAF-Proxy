package de.simonsator.partyandfriends.friendsapi.proxy.velocity;


import de.simonsator.partyandfriends.friendsapi.proxy.common.OnlineBridgeProxyMySQLConnection;
import de.simonsator.partyandfriends.velocity.communication.sql.pool.PoolSQLCommunication;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OnlineBridgeBungeeMySQLConnection extends PoolSQLCommunication implements OnlineBridgeProxyMySQLConnection {
	private final String TABLE_PREFIX;

	public OnlineBridgeBungeeMySQLConnection(String pTablePrefix) throws SQLException {
		super();
		TABLE_PREFIX = pTablePrefix;
		importDatabase();
	}

	@Override
	public String getTablePrefix() {
		return TABLE_PREFIX;
	}

	@Override
	public void close(Connection con, PreparedStatement pPrepStmt) {
		super.close(con, pPrepStmt);
	}

}
