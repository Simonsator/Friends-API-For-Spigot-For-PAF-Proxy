package de.simonsator.partyandfriends.spigot.mysql.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author simonbrungs
 * @version 1.0.0 21.12.16
 */
public class LocalPlayerCache extends PlayerCache {
	private final Map<String, Integer> namePlayerID = new HashMap<>();
	private final Map<UUID, Integer> uuidPlayerID = new HashMap<>();

	@Override
	public void add(String pName, int pPlayerID) {
		namePlayerID.put(pName.toLowerCase(), pPlayerID);
	}

	@Override
	public void add(UUID pUUID, int pPlayerID) {
		uuidPlayerID.put(pUUID, pPlayerID);
	}

	@Override
	public Integer getPlayerID(String pName) {
		return namePlayerID.get(pName.toLowerCase());
	}

	@Override
	public Integer getPlayerID(UUID pUUID) {
		return uuidPlayerID.get(pUUID);
	}
}
