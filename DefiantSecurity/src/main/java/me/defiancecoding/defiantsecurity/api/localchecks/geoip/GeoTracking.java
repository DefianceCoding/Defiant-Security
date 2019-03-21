package me.defiancecoding.defiantsecurity.api.localchecks.geoip;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Country;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.util.maxmind.GetMaxmindDatabase;

public class GeoTracking {

	private DefiantSecurity main;

	public GeoTracking(DefiantSecurity main) {
		this.main = main;
	}

	public String getInfo(String IP) {
		GetMaxmindDatabase db = new GetMaxmindDatabase(main);

		DatabaseReader reader = null;
		try {
			reader = new DatabaseReader.Builder(db.getDatabase()).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		InetAddress ipAddress = null;
		try {
			ipAddress = InetAddress.getByName(IP);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		CityResponse response = null;
		try {
			response = reader.city(ipAddress);
		} catch (IOException | GeoIp2Exception e) {
			e.printStackTrace();
		}
		Country country = response.getCountry();
		String countryName = country.getIsoCode();
		return countryName;
	}

}
