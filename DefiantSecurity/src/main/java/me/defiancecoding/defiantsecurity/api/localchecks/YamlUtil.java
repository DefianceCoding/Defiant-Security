package me.defiancecoding.defiantsecurity.api.localchecks;

import java.util.List;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;

public class YamlUtil {

	private DefiantSecurity main;

	public YamlUtil(DefiantSecurity main) {
		this.main = main;
	}

	public boolean checkWhitelist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getWhitelist().getStringList("Whitelist");
		return list.contains(IP);
	}

	public boolean checkBlacklist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getBlacklist().getStringList("Blacklist");
		return list.contains(IP);
	}

	public void addWhitelist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getWhitelist().getStringList("Whitelist");
		if (!list.contains(IP)) {
			list.add(IP);
		}
		cfg.getWhitelist().set("Whitelist", list);
		cfg.saveWhitelist();
	}

	public void addBlacklist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getBlacklist().getStringList("Blacklist");
		if (!list.contains(IP)) {
			list.add(IP);
		}
		cfg.getWhitelist().set("Blacklist", list);
		cfg.saveWhitelist();
	}

	public void removeWhitelist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getWhitelist().getStringList("Whitelist");
		if (list.contains(IP)) {
			list.remove(IP);
			cfg.getWhitelist().set("Whitelist", list);
			cfg.saveWhitelist();
		}
	}

	public void removeBlacklist(String IP) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> list = cfg.getBlacklist().getStringList("Blacklist");
		if (list.contains(IP)) {
			list.remove(IP);
			cfg.getBlacklist().set("Blacklist", list);
			cfg.saveBlacklist();
		}
	}
}
