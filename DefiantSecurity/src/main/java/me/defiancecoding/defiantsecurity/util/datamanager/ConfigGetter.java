package me.defiancecoding.defiantsecurity.util.datamanager;

import org.bukkit.configuration.file.FileConfiguration;

import me.defiancecoding.defiantsecurity.DefiantSecurity;

public class ConfigGetter {

	private DefiantSecurity main;

	public ConfigGetter(DefiantSecurity main) {
		this.main = main;
	}

	/*
	 * Getter Section
	 */

	public FileConfiguration getConfig() {
		return new ConfigHandler(main, "config.yml");
	}

	public FileConfiguration getLanguage() {
		return new ConfigHandler(main, "Language.yml");
	}

	public FileConfiguration getGeoIP() {
		return new ConfigHandler(main, "GeoIP.yml");
	}

	public FileConfiguration getMySQL() {
		return new ConfigHandler(main, "MySQL.yml");
	}

	public FileConfiguration getFirewall() {
		return new ConfigHandler(main, "Firewall.yml");
	}

	public FileConfiguration getOnlineChecks() {
		return new ConfigHandler(main, "OnlineChecks.yml");
	}

	public FileConfiguration getPunishmentCommands() {
		return new ConfigHandler(main, "PunishmentCommands.yml");
	}

	public FileConfiguration getPermissions() {
		return new ConfigHandler(main, "Permissions.yml");
	}

	public FileConfiguration getWhitelist() {
		return new ConfigHandler(main, "Whitelist.yml");
	}

	public FileConfiguration getBlacklist() {
		return new ConfigHandler(main, "Blacklist.yml");
	}

	public FileConfiguration getPlayerWhitelist() {
		return new ConfigHandler(main, "PlayerWhitelist.yml");
	}

	/*
	 * Reload Section
	 */

	public void reloadConfig() {
		ConfigHandler handler = new ConfigHandler(main, "config.yml");
		handler.reload();
	}

	public void reloadLanguage() {
		ConfigHandler handler = new ConfigHandler(main, "Language.yml");
		handler.reload();
	}

	public void reloadGeoIP() {
		ConfigHandler handler = new ConfigHandler(main, "GeoIP.yml");
		handler.reload();
	}

	public void reloadMySQL() {
		ConfigHandler handler = new ConfigHandler(main, "MySQL.yml");
		handler.reload();
	}

	public void reloadFirewall() {
		ConfigHandler handler = new ConfigHandler(main, "Firewall.yml");
		handler.reload();
	}

	public void reloadOnlineChecks() {
		ConfigHandler handler = new ConfigHandler(main, "OnlineChecks.yml");
		handler.reload();
	}

	public void reloadPunishmentCommands() {
		ConfigHandler handler = new ConfigHandler(main, "PunishmentCommands.yml");
		handler.reload();
	}

	public void reloadPermissions() {
		ConfigHandler handler = new ConfigHandler(main, "Permissions.yml");
		handler.reload();
	}

	public void reloadWhitelist() {
		ConfigHandler handler = new ConfigHandler(main, "Whitelist.yml");
		handler.reload();
	}

	public void reloadBlacklist() {
		ConfigHandler handler = new ConfigHandler(main, "Blacklist.yml");
		handler.reload();
	}

	public void reloadPlayerWhitelist() {
		ConfigHandler handler = new ConfigHandler(main, "PlayerWhitelist");
		handler.reload();
	}

	/*
	 * Save Section
	 */

	public void saveConfig() {
		ConfigHandler handler = new ConfigHandler(main, "config.yml");
		handler.save();
	}

	public void saveLanguage() {
		ConfigHandler handler = new ConfigHandler(main, "Language.yml");
		handler.save();
	}

	public void saveGeoIP() {
		ConfigHandler handler = new ConfigHandler(main, "GeoIP.yml");
		handler.save();
	}

	public void saveMySQL() {
		ConfigHandler handler = new ConfigHandler(main, "MySQL.yml");
		handler.save();
	}

	public void saveFirewall() {
		ConfigHandler handler = new ConfigHandler(main, "Firewall.yml");
		handler.save();
	}

	public void saveOnlineChecks() {
		ConfigHandler handler = new ConfigHandler(main, "OnlineChecks.yml");
		handler.save();
	}

	public void savePunishmentCommands() {
		ConfigHandler handler = new ConfigHandler(main, "PunishmentCommands.yml");
		handler.save();
	}

	public void savePermissions() {
		ConfigHandler handler = new ConfigHandler(main, "Permissions.yml");
		handler.save();
	}

	public void saveWhitelist() {
		ConfigHandler handler = new ConfigHandler(main, "Whitelist.yml");
		handler.save();
	}

	public void saveBlacklist() {
		ConfigHandler handler = new ConfigHandler(main, "Blacklist.yml");
		handler.save();
	}

	public void savePlayerWhitelist() {
		ConfigHandler handler = new ConfigHandler(main, "PlayerWhitelist.yml");
		handler.save();
	}

}
