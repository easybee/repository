package de.nullnull.file;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.AccessMode;
import java.nio.file.CopyOption;
import java.nio.file.DirectoryStream;
import java.nio.file.DirectoryStream.Filter;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystemNotFoundException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.FileAttributeView;
import java.nio.file.spi.FileSystemProvider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class VFSFileSystemProvider extends FileSystemProvider {

	private final Map<Path, VFSFileSystem> filesystems = new HashMap<>();

	@Override
	public String getScheme() {
		return "vfs";
	}

	@Override
	public FileSystem newFileSystem(URI uri, Map<String, ?> env) throws IOException {
		Path path = uriToPath(uri);
		Path realPath = null;
		if (ensureFile(path)) {
			realPath = path.toRealPath();
			if (filesystems.containsKey(realPath))
				throw new FileSystemAlreadyExistsException();
		}
		VFSFileSystem vfs = null;
		vfs = new VFSFileSystem(this, path, env);
		filesystems.put(realPath, vfs);
		return vfs;
	}

	@Override
	public FileSystem getFileSystem(URI uri) {
		synchronized (filesystems) {
			VFSFileSystem vfs = null;
			try {
				vfs = filesystems.get(uriToPath(uri).toRealPath());
			} catch (IOException x) {
				// ignore the ioe from toRealPath(), return FSNFE
			}
			if (vfs == null)
				throw new FileSystemNotFoundException();
			return vfs;
		}
	}

	@Override
	public Path getPath(URI uri) {
		String spec = uri.getSchemeSpecificPart();
		int sep = spec.indexOf("!/");
		if (sep == -1)
			throw new IllegalArgumentException(
					"URI: " + uri + " does not contain path info ex. jar:file:/c:/foo.zip!/BAR");
		return getFileSystem(uri).getPath(spec.substring(sep + 1));
	}

	@Override
	public SeekableByteChannel newByteChannel(Path path, Set<? extends OpenOption> options, FileAttribute<?>... attrs)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DirectoryStream<Path> newDirectoryStream(Path dir, Filter<? super Path> filter) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createDirectory(Path dir, FileAttribute<?>... attrs) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Path path) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void copy(Path source, Path target, CopyOption... options) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(Path source, Path target, CopyOption... options) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isSameFile(Path path, Path path2) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isHidden(Path path) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public FileStore getFileStore(Path path) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void checkAccess(Path path, AccessMode... modes) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public <V extends FileAttributeView> V getFileAttributeView(Path path, Class<V> type, LinkOption... options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <A extends BasicFileAttributes> A readAttributes(Path path, Class<A> type, LinkOption... options)
			throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> readAttributes(Path path, String attributes, LinkOption... options) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAttribute(Path path, String attribute, Object value, LinkOption... options) throws IOException {
		// TODO Auto-generated method stub

	}

	protected Path uriToPath(URI uri) {
		String scheme = uri.getScheme();
		if ((scheme == null) || !scheme.equalsIgnoreCase(getScheme())) {
			throw new IllegalArgumentException("URI scheme is not '" + getScheme() + "'");
		}
		try {
			// only support legacy JAR URL syntax jar:{uri}!/{entry} for now
			String spec = uri.getRawSchemeSpecificPart();
			int sep = spec.indexOf("!/");
			if (sep != -1)
				spec = spec.substring(0, sep);
			return Paths.get(new URI(spec)).toAbsolutePath();
		} catch (URISyntaxException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	private boolean ensureFile(Path path) {
		try {
			BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);
			if (!attrs.isRegularFile())
				throw new UnsupportedOperationException();
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

}
