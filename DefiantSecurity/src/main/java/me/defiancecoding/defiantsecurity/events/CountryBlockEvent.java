package me.defiancecoding.defiantsecurity.events;

import org.apache.http.concurrent.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class CountryBlockEvent extends Event implements Cancellable {

	private boolean isCancelled;
	private String ip;
	private String uuid;
	private String playerName;
	private String countryName;
	private boolean isBlocked;

	public CountryBlockEvent(String ip, String uuid, String playerName, String countryName, boolean isBlocked) {
		this.ip = ip;
		this.uuid = uuid;
		this.playerName = playerName;
		this.countryName = countryName;
		this.isCancelled = false;
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

	@Override
	public boolean cancel() {
		return this.isCancelled;
	}

	public void setCancelled(boolean arg) {
		this.isCancelled = arg;
	}

	public String getIP() {
		return this.ip;
	}

	public String getUUID() {
		return this.uuid;
	}

	public String getPlayerName() {
		return this.playerName;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public boolean getIsBlocked() {
		return this.isBlocked;
	}

}
