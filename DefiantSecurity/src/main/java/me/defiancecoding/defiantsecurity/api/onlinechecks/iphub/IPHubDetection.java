package me.defiancecoding.defiantsecurity.api.onlinechecks.iphub;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by DefianceCoding on 11/8/2017.
 */
public final class IPHubDetection {

	/**
	 * Allows you to lookup and determine info of a specified IPv4 or IPv6 Address
	 * and check if it belongs to a VPN Network, Proxy Server, Tor Network, and or
	 * Mobile Network
	 * <p>
	 * <p>
	 * This class facilitates and simplifies using the web API, and allows you to
	 * easily implement the functionality in your applications.
	 * <p>
	 * API Homepage: https://ipqualityscore.com
	 *
	 * @author DefianceCoding
	 */

	private String api_key;
	private String api_url = "http://v2.api.iphub.info/ip/";
	private int api_timeout = 5000;

	public IPHubDetection(String key) {
		this.api_key = key;
	}

	public IPHubDetection(String key, int timeout) {
		this.api_key = key;
		this.api_timeout = timeout;
	}

	/**
	 * Here is where you can set your API Key from your user dashboard
	 *
	 * @param key API Key from users IPHub Account
	 */

	public void set_api_key(String key) {
		this.api_key = key;
	}

	/**
	 * Units are measured in milliseconds and is the max time the API will try to
	 * poll info.
	 *
	 * @param timeout time in milliseconds till a connection will drop and consider
	 *                failed.
	 */

	public void set_api_timeout(int timeout) {
		this.api_timeout = timeout;
	}

	/**
	 * Determines weather or not to use SSL when querying from the API Host
	 */

	public void useSSL() {
		this.api_url = this.api_url.replace("http://", "https://");
	}

	/**
	 * Queries the API server to get results via IPHubResponse and pulls data via
	 * GSON API
	 *
	 * @param ip
	 * @return
	 * @throws IOException
	 */

	public IPHubResponse getResponse(String ip) throws IOException {
		String query_url = this.get_query_url(ip);
		String query_result = this.query(query_url, this.api_timeout, "IPHub Java-Library");

		return new Gson().fromJson(query_result, IPHubResponse.class);
	}

	/**
	 * Generates the URL used for Query
	 *
	 * @param ip
	 * @return
	 */

	public String get_query_url(String ip) {
		return this.api_url + ip;
	}

	/**
	 * Function that will pull data from the API URL.
	 *
	 * @param url
	 * @param timeout
	 * @param userAgent
	 * @return
	 * @throws MalformedURLException
	 * @throws IOException
	 */

	public String query(String url, int timeout, String userAgent) throws MalformedURLException, IOException {
		StringBuilder response = new StringBuilder();
		URL website = new URL(url);
		URLConnection connection = website.openConnection();
		connection.setConnectTimeout(timeout);
		connection.setDoInput(true);
		connection.setDoOutput(true);
		connection.setRequestProperty("User-Agent", userAgent);
		connection.setRequestProperty("X-Key", this.api_key);
		try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
			while ((url = in.readLine()) != null) {
				response.append(url);
			}
		}
		return response.toString();
	}
}