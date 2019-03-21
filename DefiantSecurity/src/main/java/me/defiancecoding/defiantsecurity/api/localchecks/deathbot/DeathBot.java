package me.defiancecoding.defiantsecurity.api.localchecks.deathbot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class DeathBot {

	// Add note - use these URLS
	// http://pcdomain.tk/DeathBot/files/S5Proxies.txt
	// http://pcdomain.tk/DeathBot/files/S4Proxies.txt
	// http://pcdomain.tk/DeathBot/files/SSLProxies.txt
	private String IP;
	private String type;

	public DeathBot(String IP, String type) {
		this.IP = IP;
		this.type = type;
	}

	/*
	 * @Author DefianceCoding
	 */

	public boolean isDeathBotProxy() throws IOException {
		String S5ProxyList = "http://pcdomain.tk/DeathBot/files/S5Proxies.txt";
		String S4ProxyList = "http://pcdomain.tk/DeathBot/files/S4Proxies.txt";
		String SSLProxyList = "http://pcdomain.tk/DeathBot/files/SSLProxies.txt";
		URL url = null;
		if (type.equalsIgnoreCase("ssl")) {
			url = new URL(SSLProxyList);
		}
		if (type.equalsIgnoreCase("S5")) {
			url = new URL(S5ProxyList);
		}
		if (type.equalsIgnoreCase("S4")) {
			url = new URL(S4ProxyList);
		}
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			System.out.println(inputLine);
			in.close();
			return inputLine.contains(IP);
		}
		return false;
	}

}
