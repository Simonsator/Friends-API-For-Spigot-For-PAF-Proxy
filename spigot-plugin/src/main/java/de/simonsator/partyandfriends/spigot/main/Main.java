package de.simonsator.partyandfriends.spigot.main;

import de.simonsator.partyandfriends.spigot.communication.sql.DriverShim;
import de.simonsator.partyandfriends.spigot.communication.sql.MySQLData;
import de.simonsator.partyandfriends.spigot.error.ErrorReporter;
import de.simonsator.partyandfriends.spigot.pafplayers.manager.PAFPlayerManagerMySQL;
import de.simonsator.partyandfriends.spigot.placeholders.placeholderapi.FriendCountPlaceHolderPlaceholderAPI;
import de.simonsator.partyandfriends.spigot.utilities.disable.Disabler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main extends JavaPlugin {
	private static Main instance;
	private MySQLData mySQLData;
	public static final String MARIADB_DRIVER_DOWNLOAD_URL = "https://repo1.maven.org/maven2/org/mariadb/jdbc/mariadb-java-client/3.2.0/mariadb-java-client-3.2.0.jar";

	public void onEnable() {
		instance = this;
		getConfig().options().copyDefaults(true);
		saveConfig();
		String driverURL = "jdbc:mysql://";
		if (getConfig().getBoolean("MySQL.UseMariaDBDriver")) {
			loadMariaDBConnector();
			driverURL = "jdbc:mariadb://";
		}
		mySQLData = new MySQLData(getConfig().getString("MySQL.Host"),
				getConfig().getString("MySQL.Username"), getConfig().getString("MySQL.Password"),
				getConfig().getInt("MySQL.Port"), getConfig().getString("MySQL.Database"),
				getConfig().getString("MySQL.TablePrefix"), getConfig().getBoolean("MySQL.UseSSL"), driverURL,
				getConfig().getBoolean("MySQL.UseIDCache"));
		new PAFPlayerManagerMySQL(mySQLData);
		final JavaPlugin plugin = this;
		getServer().getScheduler().scheduleSyncDelayedTask(this, () -> {
			if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
				new FriendCountPlaceHolderPlaceholderAPI(plugin).register();
			}
		}, 10);
	}

	private void loadMariaDBConnector() {
		File mariadbConnector = new File(getDataFolder(), "MariaDB-JDBC-Connector.jar");
		if (!mariadbConnector.exists()) {
			System.out.println("Downloading MariaDB-JDBC-Connector.jar...");
			try (FileOutputStream fos = new FileOutputStream(mariadbConnector)) {
				URL website = new URL(MARIADB_DRIVER_DOWNLOAD_URL);
				ReadableByteChannel rbc = Channels.newChannel(website.openStream());
				fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			} catch (IOException e) {
				new ErrorReporter("§cCould not download MariaDB-JDBC-Connector.jar. Please download it from " + Main.MARIADB_DRIVER_DOWNLOAD_URL + " manually and put it in the Party And Friends plugin folder under the name MariaDB-JDBC-Connector.jar. Alternatively you can set \"MySQL.UseMariaDBConnector\" to false.");
				getConfig().set("MySQL.UseMariaDBConnector", false);
			}
		}
		if (mariadbConnector.exists()) {
			try {
				URL jarUrl = mariadbConnector.toURI().toURL();
				URLClassLoader loader = new URLClassLoader(new URL[]{jarUrl}, ClassLoader.getSystemClassLoader());
				DriverManager.registerDriver(new DriverShim((Driver) Class.forName("org.mariadb.jdbc.Driver", true, loader).getDeclaredConstructor().newInstance()));
			} catch (MalformedURLException | ClassNotFoundException | SQLException | InvocationTargetException |
			         InstantiationException | IllegalAccessException | NoSuchMethodException e) {
				new ErrorReporter("§cParty and Friends was not able to load the MariaDB driver from the plugin folder called MariaDB-JDBC-Connector.jar. You should try deleting the file so Party And Friends can redownload it or download it manually from " + Main.MARIADB_DRIVER_DOWNLOAD_URL + " and put it in the Party And Friends plugin folder under the name MariaDB-JDBC-Connector.jar. Alternatively you can set \"MySQL.UseMariaDBConnector\" to false.");
				getConfig().set("MySQL.UseMariaDBConnector", false);
			}
		}
	}

	public void onDisable() {
		Disabler.getInstance().disableAll();
	}

	public MySQLData getMySQLData() {
		return mySQLData;
	}

	public static Main getInstance() {
		return instance;
	}
}
