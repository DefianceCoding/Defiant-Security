package me.defiancecoding.defiantsecurity.util.datamanager;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.defiancecoding.defiantsecurity.DefiantSecurity;

public class ConfigHandler extends YamlConfiguration {

	private File file;
	private String defaults;
	private DefiantSecurity plugin;

	/**
	 * Creates new PluginFile, without defaults
	 * 
	 * @param plugin   - Your plugin
	 * @param fileName - Name of the file
	 */
	public ConfigHandler(DefiantSecurity plugin, String fileName) {
		this.plugin = plugin;
		this.file = new File(plugin.getDataFolder(), fileName);
		this.defaults = null;
		plugin.saveResource(fileName, false);
		this.reload();
	}

	/**
	 * Creates new PluginFile, with defaults
	 * 
	 * @param plugin       - Your plugin
	 * @param fileName     - Name of the file
	 * @param defaultsName - Name of the defaults
	 */
	public ConfigHandler(DefiantSecurity plugin, String fileName, String defaultsName) {
		this.plugin = plugin;
		this.defaults = defaultsName;
		this.file = new File(plugin.getDataFolder(), fileName);
		plugin.saveResource(fileName, false);
		this.reload();
	}

	/**
	 * Reload configuration
	 */
	public void reload() {
		try {
			load(file);

			if (defaults != null) {
				InputStreamReader reader = new InputStreamReader(plugin.getResource(defaults));
				FileConfiguration defaultsConfig = YamlConfiguration.loadConfiguration(reader);

				setDefaults(defaultsConfig);
				options().copyDefaults(true);

				reader.close();
				save();
			}

		} catch (IOException exception) {
			plugin.getLogger().severe("Error while loading file " + file.getName());

		} catch (InvalidConfigurationException exception) {
			plugin.getLogger().severe("Error while loading file " + file.getName());

		}

	}

	/**
	 * Save configuration
	 */
	public void save() {

		try {
			options().indent(2);
			save(file);

		} catch (IOException exception) {
			plugin.getLogger().severe("Error while saving file " + file.getName());
		}

	}

}