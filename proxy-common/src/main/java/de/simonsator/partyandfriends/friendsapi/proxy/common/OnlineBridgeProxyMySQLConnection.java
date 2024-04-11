package de.simonsator.partyandfriends.friendsapi.proxy.common;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public interface OnlineBridgeProxyMySQLConnection {
	String getTablePrefix();

	Connection getConnection();

	void close(Connection con, PreparedStatement pPrepStmt);

	default void importDatabase() throws SQLException {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("CREATE TABLE IF NOT EXISTS `" + getTablePrefix() +
					"online` (`player_id` INT(8) NOT NULL, PRIMARY KEY (`player_id`));");
			prepStmt.executeUpdate();
		} finally {
			close(con, prepStmt);
		}
	}

	default void cleanTable() {
		try (Statement statement = getConnection().createStatement()) {
			statement.executeUpdate("TRUNCATE TABLE " + getTablePrefix() + "online");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	default void playerWentOnline(int pPlayerId) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("insert into `" + getTablePrefix() + "online` (`player_id`) values (?) ON DUPLICATE KEY UPDATE player_id = player_id");
			prepStmt.setInt(1, pPlayerId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

	default void playerWentOffline(int pPlayerId) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM `" + getTablePrefix() + "online` WHERE player_id=? LIMIT 1");
			prepStmt.setInt(1, pPlayerId);
			prepStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(con, prepStmt);
		}
	}

}
