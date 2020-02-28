package de.nullnull.product;

import java.io.File;
import java.util.List;

public class DefaultProductRepository implements ProductRepository {

	private String id;
	private String url;
	private ProductRepositoryLayout layout;
	private String basedir;

	private String protocol;

	public DefaultProductRepository(String id, String url, ProductRepositoryLayout layout) {
		this.setId(id);
		this.url = url;
		this.layout = layout;
		this.protocol = protocol(url);
		this.basedir = basedir(url);
	}

	public DefaultProductRepository(String id, String url) {
		this(id, url, new DefaultProductRepositoryLayout());
	}

	/**
	 * Return the protocol name. <br>
	 * E.g: for input <code>http://www.codehaus.org</code> this method will return
	 * <code>http</code>
	 *
	 * @param url the url
	 * @return the host name
	 */
	private static String protocol(final String url) {
		final int pos = url.indexOf(':');

		if (pos == -1) {
			return "";
		}
		return url.substring(0, pos).trim();
	}

	/**
	 * Derive the path portion of the given URL.
	 *
	 * @param url the repository URL
	 * @return the basedir of the repository TODO need to URL decode for spaces?
	 */
	private String basedir(String url) {
		String retValue = null;

		if (protocol.equalsIgnoreCase("file")) {
			retValue = url.substring(protocol.length() + 1);
			retValue = decode(retValue);
			// special case: if omitted // on protocol, keep path as is
			if (retValue.startsWith("//")) {
				retValue = retValue.substring(2);

				if (retValue.length() >= 2 && (retValue.charAt(1) == '|' || retValue.charAt(1) == ':')) {
					// special case: if there is a windows drive letter, then keep the original
					// return value
					retValue = retValue.charAt(0) + ":" + retValue.substring(2);
				} else {
					// Now we expect the host
					int index = retValue.indexOf('/');
					if (index >= 0) {
						retValue = retValue.substring(index + 1);
					}

					// special case: if there is a windows drive letter, then keep the original
					// return value
					if (retValue.length() >= 2 && (retValue.charAt(1) == '|' || retValue.charAt(1) == ':')) {
						retValue = retValue.charAt(0) + ":" + retValue.substring(2);
					} else if (index >= 0) {
						// leading / was previously stripped
						retValue = "/" + retValue;
					}
				}
			}

			// special case: if there is a windows drive letter using |, switch to :
			if (retValue.length() >= 2 && retValue.charAt(1) == '|') {
				retValue = retValue.charAt(0) + ":" + retValue.substring(2);
			}

			// normalize separators
			retValue = new File(retValue).getPath();
		}

		if (retValue == null) {
			retValue = "/";
		}
		return retValue.trim();
	}

	/**
	 * Decodes the specified (portion of a) URL. <strong>Note:</strong> This decoder
	 * assumes that ISO-8859-1 is used to convert URL-encoded bytes to characters.
	 *
	 * @param url The URL to decode, may be <code>null</code>.
	 * @return The decoded URL or <code>null</code> if the input was
	 *         <code>null</code>.
	 */
	private static String decode(String url) {
		String decoded = url;
		if (url != null) {
			int pos = -1;
			while ((pos = decoded.indexOf('%', pos + 1)) >= 0) {
				if (pos + 2 < decoded.length()) {
					String hexStr = decoded.substring(pos + 1, pos + 3);
					char ch = (char) Integer.parseInt(hexStr, 16);
					decoded = decoded.substring(0, pos) + ch + decoded.substring(pos + 3);
				}
			}
		}
		return decoded;
	}

	@Override
	public ProductRepositoryLayout getLayout() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProductRepositoryWalker getWalker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getBasedir() {
		return basedir;
	}

	@Override
	public String getProtocol() {
		return protocol;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public List<ProductPackage> list() {
		// TODO Auto-generated method stub
		return null;
	}

}
