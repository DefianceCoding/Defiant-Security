package me.defiancecoding.defiantsecurity.util.maxmind;

import java.io.File;
import java.io.IOException;

import org.apache.commons.compress.archivers.ArchiveException;

import me.defiancecoding.defiantsecurity.DefiantSecurity;
import me.defiancecoding.defiantsecurity.util.archiver.Untar;
import me.defiancecoding.defiantsecurity.util.downloader.FileDownloader;

public class GetMaxmindDatabase {

	private DefiantSecurity main;

	public GetMaxmindDatabase(DefiantSecurity main) {
		this.main = main;
	}

	public void downloadDatabase() {
		FileDownloader downloader = new FileDownloader(main);

		File temp = new File(main.getDataFolder(), "GeoLite2-City.tar.gz");
		if (!temp.exists()) {
			try {
				downloader.downloadFrom("https://geolite.maxmind.com/download/geoip/database/GeoLite2-City.tar.gz",
						main.getDataFolder().getAbsolutePath(), "GeoLite2-City.tar.gz", 5000);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public void unpackDatabase() {
		try {
			File temp = new File(main.getDataFolder(), "GeoLite2-City.tar.gz");
			Untar untar = new Untar(main);
			File checkList = getDatabase();
			if (checkList == null) {
				untar.unTar(temp, main.getDataFolder());
			}
		} catch (IOException | ArchiveException ex) {
			ex.printStackTrace();
			System.out.println(("Error creating Maxmind files: " + ex.getMessage()));
		}
	}

	public File getDatabase() {
		return new File(main.getDataFolder() + File.pathSeparator + "GeoLite2-City_20190319", "GeoLite2-City.mmdb");
	}

}
