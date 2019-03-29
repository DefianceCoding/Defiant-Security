package me.defiancecoding.defiantsecurity;

import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.defiancecoding.defiantsecurity.commands.Commands;
import me.defiancecoding.defiantsecurity.listeners.BlockLoginEvent;
import me.defiancecoding.defiantsecurity.listeners.PlayerPreLogin;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;
import me.defiancecoding.defiantsecurity.util.maxmind.GetMaxmindDatabase;
import me.defiancecoding.defiantsecurity.util.mysql.SQLUtil;

public class DefiantSecurity extends JavaPlugin {

	@Override
	public void onEnable() {
		setupConfigs();
		prepareMySQL();
		registerListeners();
		setupCommand();
	}

	@Override
	public void onDisable() {
	}

	public void setupCommand() {
		getCommand("DefiantSecurity").setExecutor(new Commands(this));
	}

	public void prepareMySQL() {
		ConfigGetter cfg = new ConfigGetter(this);
		if (cfg.getConfig().getBoolean("Modules.MySQL")) {
			SQLUtil sql = new SQLUtil(this);
			try {
				sql.createTable("Whitelist");
				sql.createTable("Blacklist");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public void setupConfigs() {
		ConfigGetter cfg = new ConfigGetter(this);
		cfg.getBlacklist();
		cfg.getConfig();
		cfg.getFirewall();
		cfg.getGeoIP();
		cfg.getLanguage();
		cfg.getMySQL();
		cfg.getOnlineChecks();
		cfg.getPermissions();
		cfg.getPunishmentCommands();
		cfg.getWhitelist();
		GetMaxmindDatabase maxmind = new GetMaxmindDatabase(this);
		maxmind.downloadDatabase();
		maxmind.unpackDatabase();
	}

	public void registerListeners() {
		PluginManager pm = Bukkit.getServer().getPluginManager();

		pm.registerEvents(new PlayerPreLogin(this), this);
		pm.registerEvents(new BlockLoginEvent(this), this);
	}

	public String color(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

}
