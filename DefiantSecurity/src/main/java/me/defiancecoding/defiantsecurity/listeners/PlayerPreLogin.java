package me.defiancecoding.defiantsecurity.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.api.localchecks.LocalCacheCheck;
import me.defiancecoding.defiantsecurity.api.localchecks.YamlUtil;
import me.defiancecoding.defiantsecurity.api.onlinechecks.OnlineChecks;
import me.defiancecoding.defiantsecurity.api.validation.IPValidation;
import me.defiancecoding.defiantsecurity.events.CountryBlockEvent;
import me.defiancecoding.defiantsecurity.events.DeathBotDetectionEvent;
import me.defiancecoding.defiantsecurity.events.JoinBlacklistedEvent;
import me.defiancecoding.defiantsecurity.events.RestfulAPIDetectionEvent;
import me.defiancecoding.defiantsecurity.exceptions.InvalidIPException;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;
import me.defiancecoding.defiantsecurity.util.mysql.SQLUtil;

public class PlayerPreLogin implements Listener {

	private DefiantSecurity main;

	public PlayerPreLogin(DefiantSecurity main) {
		this.main = main;
	}

	@EventHandler
	public void onLogin(AsyncPlayerPreLoginEvent e) {
		String ip = e.getAddress().getHostAddress();
		IPValidation validator = new IPValidation(ip);
		try {
			if (validator.check()) {
				ConfigGetter cfg = new ConfigGetter(main);
				OnlineChecks online = new OnlineChecks(main);
				LocalCacheCheck localCache = new LocalCacheCheck(main);
				boolean isBlacklisted, isWhitelisted, isDeathBot, isBlockedCountry = false;

				String playerName = e.getName();
				String uuid = e.getUniqueId().toString();
				String countryName = null;

				if (cfg.getConfig().getBoolean("Modules.MySQL")) {
					SQLUtil sql = new SQLUtil(main);
					isWhitelisted = sql.valueExists("Whitelist", ip);
					isBlacklisted = sql.valueExists("Blacklist", ip);
				} else {
					YamlUtil yaml = new YamlUtil(main);
					isWhitelisted = yaml.checkWhitelist(ip);
					isBlacklisted = yaml.checkBlacklist(ip);
				}
				if (isWhitelisted) {
					System.out.println("IP: " + ip + " Is listed in the Whitelist");
					return;
				} else if (isBlacklisted) {
					System.out.println("IP: " + ip + " Is listed in the Blacklist");
					main.getServer().getPluginManager().callEvent(new JoinBlacklistedEvent(ip,
							e.getUniqueId().toString(), Bukkit.getServer().getPlayer(e.getUniqueId()).getName(), true));
					return;
				}
				isDeathBot = localCache.checkDeathBot(ip);
				if (cfg.getConfig().getBoolean("Modules.GeoIP")) {
					isBlockedCountry = localCache.isFromBlockedCountry(ip);
					countryName = localCache.getCountry(ip);
					if (isBlockedCountry) {
						// System.out.println("IP: " + ip + " Is from a blocked Country");
						main.getServer().getPluginManager()
								.callEvent(new CountryBlockEvent(ip, uuid, playerName, countryName, false));
						return;
					}
				}
				if (isDeathBot) {
					// System.out.println("IP: " + ip + " has reported under section: DeathBot");
					main.getServer().getPluginManager()
							.callEvent(new DeathBotDetectionEvent(ip, e.getUniqueId().toString(), true));
					return;
				}
				boolean isProxy = online.checkIP(ip);
				if (isProxy) {
					// System.out.println("IP: " + ip + " is reported under the section: Proxy");
					main.getServer().getPluginManager().callEvent(new RestfulAPIDetectionEvent(ip,
							e.getUniqueId().toString(), Bukkit.getServer().getPlayer(e.getUniqueId()).getName(), true));
					return;
				}
			}
		} catch (InvalidIPException ex) {
			ex.printStackTrace();
		}

	}

}
