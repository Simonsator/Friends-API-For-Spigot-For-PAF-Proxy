package de.simonsator.partyandfriends.spigot.mysql;

import de.simonsator.partyandfriends.spigot.communication.sql.MySQLData;
import de.simonsator.partyandfriends.spigot.communication.sql.SQLCommunication;

import java.sql.*;
import java.util.ArrayList;
import java.util.UUID;

public class MySQL extends SQLCommunication {
	private final String TABLE_PREFIX;

	public MySQL(MySQLData pMySQLData) {
		super(pMySQLData.DATABASE, pMySQLData.DRIVER_URL + pMySQLData.HOST + ":" + pMySQLData.PORT, pMySQLData.USERNAME, pMySQLData.PASSWORD, pMySQLData.USE_SSL);
		this.TABLE_PREFIX = pMySQLData.TABLE_PREFIX;
	}

	public UUID getUUID(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_uuid from " + TABLE_PREFIX
					+ "players WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next()) {
				return UUID.fromString(rs.getString("player_uuid"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return null;
	}

	/**
	 * Returns the ID of a player
	 *
	 * @param pUuid The UUID of the player
	 * @return Returns the ID of a player
	 */
	public int getPlayerID(UUID pUuid) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from " + TABLE_PREFIX
					+ "players WHERE player_uuid='" + pUuid + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("player_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return -1;
	}

	/**
	 * Returns the ID of a player
	 *
	 * @param pPlayerName Name of the player Returns the ID of a player
	 * @return Returns the ID of a player
	 */
	public int getPlayerID(String pPlayerName) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_id from " + TABLE_PREFIX
					+ "players WHERE player_name='" + pPlayerName + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("player_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return -1;
	}


	/**
	 * Gives out the IDs of the friends of a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the IDs of the friends of a player
	 */
	public ArrayList<Integer> getFriends(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> list = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select friend2_id from " + TABLE_PREFIX
					+ "friend_assignment WHERE friend1_id='" + pPlayerID + "'");
			while (rs.next())
				list.add(rs.getInt("friend2_id"));
			stmt.close();
			rs.close();
			rs = (stmt = con.createStatement()).executeQuery("select friend1_id from " + TABLE_PREFIX
					+ "friend_assignment WHERE friend2_id='" + pPlayerID + "'");
			while (rs.next())
				list.add(rs.getInt("friend1_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return list;
	}

	/**
	 * Returns the name of a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the name of a player
	 */
	public String getName(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select player_name from " + TABLE_PREFIX
					+ "players WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				return rs.getString("player_name");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return "";
	}

	public boolean hasRequestFrom(int pReceiver, int pRequester) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select requester_id from "
					+ TABLE_PREFIX + "friend_request_assignment WHERE receiver_id='" + pReceiver + "' AND requester_id='"
					+ pRequester + "' LIMIT 1");
			if (rs.next())
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	/**
	 * Returns the IDs of the friends from a player
	 *
	 * @param pPlayerID The ID of the player
	 * @return Returns the IDs of the friends from a player
	 */
	public ArrayList<Integer> getRequests(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<Integer> requests = new ArrayList<>();
		try {
			rs = (stmt = con.createStatement()).executeQuery("select requester_id from "
					+ TABLE_PREFIX + "friend_request_assignment WHERE receiver_id='" + pPlayerID + "'");
			while (rs.next())
				requests.add(rs.getInt("requester_id"));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return requests;
	}

	public int getSettingsWorth(int pPlayerID, int pSettingsID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery(
					"select settings_worth from " + TABLE_PREFIX + "settings WHERE player_id='"
							+ pPlayerID + "' AND settings_id='" + pSettingsID + "' LIMIT 1");
			if (rs.next()) {
				return rs.getInt("settings_worth");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return 0;
	}

	public Timestamp getLastOnline(int pPlayerID) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select last_online from "
					+ TABLE_PREFIX + "players WHERE player_id='" + pPlayerID + "' LIMIT 1");
			if (rs.next())
				return rs.getTimestamp("last_online");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return null;
	}

	public boolean isAFriendOf(int pPlayerID1, int pPlayerID2) {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("Select friend1_id FROM " + TABLE_PREFIX
					+ "friend_assignment WHERE (friend1_id = '" + pPlayerID1 + "' AND friend2_id='" + pPlayerID2
					+ "') OR (friend1_id = '" + pPlayerID2 + "' AND friend2_id='" + pPlayerID1 + "') LIMIT 1");
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt);
		}
		return false;
	}

	public void setSetting(int pPlayerID, int pSettingsID, int pNewWorth) {
		removeSetting(pPlayerID, pSettingsID);
		if (pNewWorth != 0) {
			Connection con = getConnection();
			PreparedStatement prepStmt = null;
			try {
				prepStmt = con.prepareStatement(
						"insert into " + TABLE_PREFIX + "settings values (?, ?, ?)");
				prepStmt.setInt(1, pPlayerID);
				prepStmt.setInt(2, pSettingsID);
				prepStmt.setInt(3, pNewWorth);
				prepStmt.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				close(prepStmt);
			}
		}
	}

	private void removeSetting(int pPlayerID, int pSettingsID) {
		Connection con = getConnection();
		PreparedStatement prepStmt = null;
		try {
			prepStmt = con.prepareStatement("DELETE FROM " + TABLE_PREFIX
					+ "settings WHERE player_id = '" + pPlayerID + "' AND settings_id='" + pSettingsID + "' Limit 1");
			prepStmt.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(prepStmt);
		}
	}

	public int getOnlineFriendsCount(int pPlayerId) throws SQLException {
		Connection con = getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			rs = (stmt = con.createStatement()).executeQuery("select count(online.player_id) AS online_count from " + TABLE_PREFIX
					+ "online AS online LEFT JOIN " + TABLE_PREFIX +
					"settings AS settings ON online.player_id = settings.player_id AND settings_id = 3 INNER JOIN " +
					TABLE_PREFIX + "friend_assignment ON ((friend1_id = online.player_id AND friend2_id='" + pPlayerId +
					"') OR (friend2_id = online.player_id AND friend1_id='" + pPlayerId +
					"')) AND (settings_worth <> 1 or settings_worth is null)");
			if (rs.next()) {
				return rs.getInt("online_count");
			}
		} finally {
			close(rs, stmt);
		}
		return 0;
	}
}
