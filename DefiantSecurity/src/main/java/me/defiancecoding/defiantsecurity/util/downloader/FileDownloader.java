package me.defiancecoding.defiantsecurity.util.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

import me.defiancecoding.defiantsecurity.DefiantSecurity;

public class FileDownloader {

	private DefiantSecurity main;

	public FileDownloader(DefiantSecurity main) {
		this.main = main;
	}

	public void downloadFrom(String Url, String saveDir, String outPutfile, int timeout) throws IOException {
		File file = new File(saveDir, outPutfile);
		if (!file.exists()) {
			file.createNewFile();
			URL url = new URL(Url);
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(timeout);
			connection.setRequestProperty("User-Agent", "DefianceDownloadAPI");
			connection.setDoInput(true);
			connection.setDoOutput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			input = new GZIPInputStream(input);

			main.getDataFolder().mkdir();

			OutputStream output = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int length = input.read(buffer);
			while (length >= 0) {
				output.write(buffer, 0, length);
				length = input.read(buffer);
			}
			output.close();
			input.close();
		}
	}

}
