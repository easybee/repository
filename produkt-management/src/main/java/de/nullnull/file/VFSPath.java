package de.nullnull.file;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Arrays;
import java.util.Iterator;

public class VFSPath implements Path {

	private final VFSFileSystem vfs;
	private final byte[] path;
	private volatile int[] offsets;
	private int hashcode = 0; // cached hashcode (created lazily)

	public VFSPath(VFSFileSystem vfsFileSystem, byte[] path) {
		this(vfsFileSystem, path, false);
	}

	public VFSPath(VFSFileSystem vfsFileSystem, byte[] path, boolean normalized) {
		this.vfs = vfsFileSystem;
		if (normalized)
			this.path = path;
		else
			this.path = normalize(path);
	}

	@Override
	public FileSystem getFileSystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAbsolute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Path getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getFileName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path getParent() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNameCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Path getName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path subpath(int beginIndex, int endIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean startsWith(Path other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean startsWith(String other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endsWith(Path other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean endsWith(String other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Path normalize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path resolve(Path other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path resolve(String other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path resolveSibling(Path other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path resolveSibling(String other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path relativize(Path other) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URI toUri() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path toAbsolutePath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Path toRealPath(LinkOption... options) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public File toFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public WatchKey register(WatchService watcher, Kind<?>... events) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<Path> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(Path other) {
		// TODO Auto-generated method stub
		return 0;
	}

	// removes redundant slashs, replace "\" to zip separator "/"
	// and check for invalid characters
	private byte[] normalize(byte[] path) {
		if (path.length == 0)
			return path;
		byte prevC = 0;
		for (int i = 0; i < path.length; i++) {
			byte c = path[i];
			if (c == '\\')
				return normalize(path, i);
			if (c == (byte) '/' && prevC == '/')
				return normalize(path, i - 1);
			if (c == '\u0000')
				throw new InvalidPathException(path.toString(), "Path: nul character not allowed");
			prevC = c;
		}
		return path;
	}

	private byte[] normalize(byte[] path, int off) {
		byte[] to = new byte[path.length];
		int n = 0;
		while (n < off) {
			to[n] = path[n];
			n++;
		}
		int m = n;
		byte prevC = 0;
		while (n < path.length) {
			byte c = path[n++];
			if (c == (byte) '\\')
				c = (byte) '/';
			if (c == (byte) '/' && prevC == (byte) '/')
				continue;
			if (c == '\u0000')
				throw new InvalidPathException(path.toString(), "Path: nul character not allowed");
			to[m++] = c;
			prevC = c;
		}
		if (m > 1 && to[m - 1] == '/')
			m--;
		return (m == to.length) ? to : Arrays.copyOf(to, m);
	}

}
