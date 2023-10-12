package de.simonsator.partyandfriends.spigot.communication.sql;

/**
 * @author Simonsator
 * @version 1.0.0 on 19.07.16.
 */
public class MySQLData {
	public final String HOST;
	public final String USERNAME;
	public final String PASSWORD;
	public final int PORT;
	public final String DATABASE;
	public final String TABLE_PREFIX;
	public final boolean USE_SSL;
	public final String DRIVER_URL;

	@Deprecated
	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix) {
		this(host, username, password, port, database, pTablePrefix, "jdbc:mysql://");
	}

	@Deprecated
	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, boolean pUseSSL) {
		this(host, username, password, port, database, pTablePrefix, pUseSSL, "jdbc:mysql://");
	}

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, String pDriverURL) {
		HOST = host;
		USERNAME = username;
		PASSWORD = password;
		PORT = port;
		DATABASE = database;
		TABLE_PREFIX = pTablePrefix;
		USE_SSL = false;
		DRIVER_URL = pDriverURL;
	}

	public MySQLData(String host, String username, String password, int port, String database, String pTablePrefix, boolean pUseSSL, String pDriverURL) {
		HOST = host;
		USERNAME = username;
		PASSWORD = password;
		PORT = port;
		DATABASE = database;
		TABLE_PREFIX = pTablePrefix;
		USE_SSL = pUseSSL;
		DRIVER_URL = pDriverURL;
	}
}
