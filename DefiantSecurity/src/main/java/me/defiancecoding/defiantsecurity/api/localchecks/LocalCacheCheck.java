package me.defiancecoding.defiantsecurity.api.localchecks;

import java.io.IOException;
import java.util.List;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.api.localchecks.deathbot.DeathBot;
import me.defiancecoding.defiantsecurity.api.localchecks.geoip.GeoTracking;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;

public class LocalCacheCheck {

	private DefiantSecurity main;

	public LocalCacheCheck(DefiantSecurity main) {
		this.main = main;
	}

	private GeoTracking geo = new GeoTracking(main);
	private DeathBot dbs4;
	private DeathBot dbs5;
	private DeathBot dbssl;

	public boolean checkDeathBot(String ip) {
		try {
			this.dbs4 = new DeathBot(ip, "S4");
			dbs5 = new DeathBot(ip, "S5");
			dbssl = new DeathBot(ip, "SSL");
			if (dbs4.isDeathBotProxy() || dbs5.isDeathBotProxy() || dbssl.isDeathBotProxy()) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean isFromBlockedCountry(String ip) {
		ConfigGetter cfg = new ConfigGetter(main);
		List<String> blocked = cfg.getGeoIP().getStringList("BlockedCountries");
		return blocked.contains(geo.getInfo(ip));
	}

	public String getCountry(String ip) {
		return geo.getInfo(ip);
	}

}
