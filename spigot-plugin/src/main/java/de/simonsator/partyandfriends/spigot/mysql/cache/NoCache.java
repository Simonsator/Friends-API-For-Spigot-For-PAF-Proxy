package de.simonsator.partyandfriends.spigot.mysql.cache;

import java.util.UUID;

public class NoCache extends PlayerCache {
	@Override
	public void add(String pName, int pPlayerID) {

	}

	@Override
	public void add(UUID pUUID, int pPlayerID) {

	}

	@Override
	public Integer getPlayerID(String pName) {
		return null;
	}

	@Override
	public Integer getPlayerID(UUID pUUID) {
		return null;
	}
}
