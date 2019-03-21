package me.defiancecoding.defiantsecurity.api.onlinechecks;

import java.io.IOException;

import org.json.simple.parser.ParseException;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.api.onlinechecks.iphub.IPHubDetection;
import me.defiancecoding.defiantsecurity.api.onlinechecks.iphub.IPHubResponse;
import me.defiancecoding.defiantsecurity.api.onlinechecks.ipqualityscore.IPQSDetection;
import me.defiancecoding.defiantsecurity.api.onlinechecks.ipqualityscore.IPQSResponse;
import me.defiancecoding.defiantsecurity.api.onlinechecks.proxycheck.PCDetection;
import me.defiancecoding.defiantsecurity.util.datamanager.ConfigGetter;

public class OnlineChecks {

	private DefiantSecurity main;

	public OnlineChecks(DefiantSecurity main) {
		this.main = main;
	}

	private boolean useIPQS, useIPQSSSL, useIPHub, useIPHubSSL, useProxyCheck, useProxyCheckSSL;

	private int useIPQSFast, IPQSStrictness, IPQSTimeout, IPHubTimeout, ProxyCheckTimeout;

	private String IPQSKey, IPHubKey, ProxyCheckKey;

	private void setupConfigValues() {
		ConfigGetter cfg = new ConfigGetter(main);

		useIPQS = cfg.getOnlineChecks().getBoolean("IPQualityScore.UseAPI");
		useIPQSSSL = cfg.getOnlineChecks().getBoolean("IPQualityScore.UseSSL");
		useIPQSFast = cfg.getOnlineChecks().getInt("IPQualityScore.UseFast");
		IPQSStrictness = cfg.getOnlineChecks().getInt("IPQualityScore.Strictness");
		IPQSTimeout = cfg.getOnlineChecks().getInt("IPQualityScore.Timeout");
		IPQSKey = cfg.getOnlineChecks().getString("IPQualityScore.APIKey");

		useIPHub = cfg.getOnlineChecks().getBoolean("IPHub.UseAPI");
		useIPHubSSL = cfg.getOnlineChecks().getBoolean("IPHub.UseSSL");
		IPHubKey = cfg.getOnlineChecks().getString("IPHub.APIKey");
		IPHubTimeout = cfg.getOnlineChecks().getInt("IPHub.Timeout");

		useProxyCheck = cfg.getOnlineChecks().getBoolean("ProxyCheck.UseAPI");
		useProxyCheckSSL = cfg.getOnlineChecks().getBoolean("ProxyCheck.UseSSL");
		ProxyCheckKey = cfg.getOnlineChecks().getString("ProxyCheck.APIKey");
		ProxyCheckTimeout = cfg.getOnlineChecks().getInt("ProxyCheck.Timeout");
	}

	public boolean checkIP(String IP) {
		setupConfigValues();
		if (useIPQS) {
			return checkIPQuality(IP);
		} else if (useIPHub) {
			return checkIPHub(IP);
		} else if (useProxyCheck) {
			return checkProxyCheck(IP);
		} else {
			return false;
		}
	}

	private boolean checkProxyCheck(String IP) {
		setupConfigValues();
		PCDetection detection = new PCDetection(null);
		detection.setTag("Defiant-Security.v.2.0.1");
		if (useProxyCheckSSL) {
			detection.useSSL();
		}
		detection.setUseVpn(true);
		detection.set_api_timeout(ProxyCheckTimeout);
		detection.set_api_key(ProxyCheckKey);
		try {
			detection.parseResults(IP);
			if (detection.status.equalsIgnoreCase("ok") || detection.status.equalsIgnoreCase("Warning")) {
				if (detection.status.equalsIgnoreCase("warning")) {
					main.getLogger().info("Warning from api provider: " + detection.message);
				}
				return detection.proxy.equalsIgnoreCase("yes");
			}
			if (detection.status.equalsIgnoreCase("denied")) {
				main.getLogger().info("Error getting API Response: " + detection.message);
				return false;
			}
		} catch (IOException | ParseException ex) {
			ex.printStackTrace();
			main.getLogger().info("Error getting API Response: " + ex.getMessage());
			return false;
		}
		return false;
	}

	private boolean checkIPHub(String IP) {
		setupConfigValues();
		IPHubDetection detection = new IPHubDetection(null);
		if (useIPHubSSL) {
			detection.useSSL();
		}
		detection.set_api_key(IPHubKey);
		detection.set_api_timeout(IPHubTimeout);

		try {
			IPHubResponse response = detection.getResponse(IP);
			if ((response.block == 1) || (response.block == 2)) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			main.getLogger().info("Error getting API Response: " + e.getMessage());
		}
		return false;
	}

	private boolean checkIPQuality(String IP) {
		setupConfigValues();
		IPQSDetection detection = new IPQSDetection(null);
		if (useIPQSFast == 1) {
			detection.setUseFast(1);
		}
		if (useIPQSSSL) {
			detection.useSSL();
		}
		detection.set_api_key(IPQSKey);
		detection.set_strictness_level(IPQSStrictness);
		detection.set_api_timeout(IPQSTimeout);

		try {
			IPQSResponse resp = detection.getResponse(IP);
			if (resp.success) {
				if (resp.tor || resp.proxy || resp.vpn || resp.is_crawler) {
					// main.getEventCaller().callEvent(new RestfulAPIDetectionEvent(IP, null));
					return true;
				}
			}
			if (!resp.success) {
				main.getLogger().info("Error getting API Response: " + resp.message);
				main.getLogger().info("Lookup Error Code: " + resp.request_id);
				return false;
			}

		} catch (IOException e) {
			e.printStackTrace();
			main.getLogger().info("Error getting API Response: " + e.getMessage());
		}
		return false;
	}

}
