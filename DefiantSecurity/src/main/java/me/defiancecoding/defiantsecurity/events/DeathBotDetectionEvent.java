package me.defiancecoding.defiantsecurity.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DeathBotDetectionEvent extends Event implements Cancellable {

	private boolean isCancelled;
	private String ip;
	private String uuid;
	private boolean isProxy;

	public DeathBotDetectionEvent(String ip, String uuid, boolean isProxy) {
		this.ip = ip;
		this.uuid = uuid;
		this.isProxy = isProxy;
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
	public boolean isCancelled() {
		return this.isCancelled;
	}

	@Override
	public void setCancelled(boolean arg) {
		this.isCancelled = arg;
	}

	public String getIP() {
		return this.ip;
	}

	public String getUUID() {
		return this.uuid;
	}

	public boolean getIsDeathbot() {
		return this.isProxy;
	}
}
