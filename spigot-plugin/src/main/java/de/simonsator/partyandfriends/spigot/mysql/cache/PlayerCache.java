package de.simonsator.partyandfriends.spigot.mysql.cache;

import java.util.UUID;

public abstract class PlayerCache {

	public abstract void add(String pName, int pPlayerID);

	public abstract void add(UUID pUUID, int pPlayerID);

	public abstract Integer getPlayerID(String pName);

	public abstract Integer getPlayerID(UUID pUUID);
}
