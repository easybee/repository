package de.nullnull.product;

import java.io.IOException;
import java.net.URI;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.spi.FileSystemProvider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DemoVFS {

	public static void main(String[] args) throws IOException {
		FileSystemProvider provider = getVFSProvider();
		if (provider == null) {
			System.err.println("ZIP filesystem provider is not installed");
			System.exit(1);
		}
		Path path = provider.getFileSystem(URI.create("vfs-file")).getPath("/");
		Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				print(file);
				return FileVisitResult.CONTINUE;
			}

			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				print(dir);
				return FileVisitResult.CONTINUE;
			}

			/**
			 * prints out details about the specified path such as size and modification
			 * time
			 * 
			 * @param file
			 * @throws IOException
			 */
			private void print(Path file) throws IOException {
				final DateFormat df = new SimpleDateFormat("MM/dd/yyyy-HH:mm:ss");
				final String modTime = df.format(new Date(Files.getLastModifiedTime(file).toMillis()));
				System.out.printf("%d  %s  %s\n", Files.size(file), modTime, file);
			}
		});
		System.out.println(provider.getScheme());
	}

	private static FileSystemProvider getVFSProvider() {
		for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
			if ("vfs".equals(provider.getScheme()))
				return provider;
		}
		return null;
	}

}
