package me.defiancecoding.defiantsecurity.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class JoinBlacklistedEvent extends Event implements Cancellable {

	private boolean isCancelled;
	private String ip;
	private String uuid;
	private String playerName;
	private boolean isBlocked;

	public JoinBlacklistedEvent(String ip, String uuid, String playerName, boolean isBlocked) {
		this.ip = ip;
		this.uuid = uuid;
		this.playerName = playerName;
		this.isCancelled = false;
	}

	@Override
	public void setCancelled(boolean arg0) {
		this.isCancelled = arg0;
	}

	@Override
	public boolean isCancelled() {
		return isCancelled;
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

	public boolean getIsBlocked() {
		return this.isBlocked;
	}

	private static final HandlerList handlers = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return handlers;
	}

	public static HandlerList getHandlerList() {
		return handlers;
	}

}
