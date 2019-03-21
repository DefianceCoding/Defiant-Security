package me.defiancecoding.defiantsecurity.util.archiver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.io.IOUtils;
import org.bukkit.plugin.Plugin;

public class Untar {

	private Plugin plugin;

	public Untar(Plugin plugin) {
		this.plugin = plugin;
	}

	public List<File> unTar(final File inputFile, final File outputDir) throws IOException, ArchiveException {

		Logger LOG = plugin.getLogger();
		final List<File> untaredFiles = new LinkedList<File>();
		final InputStream is = new FileInputStream(inputFile);
		final TarArchiveInputStream debInputStream = (TarArchiveInputStream) new ArchiveStreamFactory()
				.createArchiveInputStream("tar", is);
		TarArchiveEntry entry = null;
		while ((entry = (TarArchiveEntry) debInputStream.getNextEntry()) != null) {
			final File outputFile = new File(outputDir, entry.getName());
			if (entry.isDirectory()) {
				if (!outputFile.exists()) {
					LOG.info(String.format("Attempting to create output directory %s.", outputFile.getAbsolutePath()));
					if (!outputFile.mkdirs()) {
						throw new IllegalStateException(
								String.format("Couldn't create directory %s.", outputFile.getAbsolutePath()));
					}
				}
			} else {
				LOG.info(String.format("Creating output file %s.", outputFile.getAbsolutePath()));
				final OutputStream outputFileStream = new FileOutputStream(outputFile);
				IOUtils.copy(debInputStream, outputFileStream);
				outputFileStream.close();
			}
			untaredFiles.add(outputFile);
		}
		debInputStream.close();

		return untaredFiles;
	}

}
