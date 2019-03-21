package me.defiancecoding.defiantsecurity.listeners;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.events.CountryBlockEvent;
import me.defiancecoding.defiantsecurity.events.DeathBotDetectionEvent;
import me.defiancecoding.defiantsecurity.events.JoinBlacklistedEvent;
import me.defiancecoding.defiantsecurity.events.RestfulAPIDetectionEvent;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;

public class BlockLoginEvent implements Listener {

	private DefiantSecurity main;

	public BlockLoginEvent(DefiantSecurity main) {
		this.main = main;
	}

	@EventHandler
	public void CountryBlocker(CountryBlockEvent e) {
		ConfigGetter cfg = new ConfigGetter(main);
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if (e.getIsBlocked()) {
			Bukkit.getServer().dispatchCommand(console,
					cfg.getPunishmentCommands().getString("Commands.BlockedCountryCommand")
							.replace(String.format("%s", "{PlayerName}"), e.getPlayerName())
							.replace(String.format("%s", "{UUID}"), e.getUUID())
							.replace(String.format("%s", "{IP}"), e.getIP())
							.replace(String.format("%s", "{CountryName}"), e.getCountryName())
							.replace(String.format("%s", "{NewLine}"), System.lineSeparator()));
		}
	}

	@EventHandler
	public void DeathBotBlocker(DeathBotDetectionEvent e) {
		ConfigGetter cfg = new ConfigGetter(main);
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if (e.getIsDeathbot()) {
			Bukkit.getServer().dispatchCommand(console, cfg.getPunishmentCommands().getString("DeathBotBlockedCommand.")
					.replace(String.format("%s", "{UUID}"), e.getUUID()).replace(String.format("%s", "{IP}"), e.getIP())
					.replace(String.format("%s", "{NewLine}"), System.lineSeparator()));
		}
	}

	@EventHandler
	public void ProxyBlocker(RestfulAPIDetectionEvent e) {
		ConfigGetter cfg = new ConfigGetter(main);
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if (e.getIsProxy()) {
			Bukkit.getServer().dispatchCommand(console,
					cfg.getPunishmentCommands().getString("Commands.RestfulAPIDetectionCommand")
							.replace(String.format("%s", "{UUID}"), e.getUUID())
							.replace(String.format("%s", "{IP}"), e.getIP())
							.replace(String.format("%s", "{UUID}"), e.getUUID())
							.replace(String.format("%s", "{PlayerName}"), e.getPlayerName())
							.replace(String.format("%s", "{NewLine}"), System.lineSeparator()));
		}
	}

	@EventHandler
	public void BlacklistBlocker(JoinBlacklistedEvent e) {
		ConfigGetter cfg = new ConfigGetter(main);
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		if (e.getIsBlocked()) {
			Bukkit.getServer().dispatchCommand(console,
					cfg.getPunishmentCommands().getString("Commands.BlacklistHitCommand")
							.replace(String.format("%s", "{IP}"), e.getIP())
							.replace(String.format("%s", "{UUID}"), e.getUUID())
							.replace(String.format("%s", "{PlayerName}"), e.getPlayerName())
							.replace(String.format("%s", "{NewLine}"), System.lineSeparator()));
		}
	}

}
