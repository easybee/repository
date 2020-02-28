package de.nullnull.product;

public class DefaultProductRepositoryLayout implements ProductRepositoryLayout {

	private static final char PATH_SEPARATOR = '/';

	private static final char GROUP_SEPARATOR = '.';

	private static final char ARTIFACT_SEPARATOR = '-';

	private ProductRepository productRepository;

	@Override
	public String getId() {
		return "default";
	}

	@Override
	public String pathOf(ProductPackage productPackage) {
		return productPackage.getName() + PATH_SEPARATOR + productPackage.getVersion();
	}

	@Override
	public String pathOfMetadata(ProductPackage productPackage) {
		return productPackage.getName() + PATH_SEPARATOR + productPackage.getVersion() + PATH_SEPARATOR
				+ productPackage.getName() + ARTIFACT_SEPARATOR + productPackage.getVersion() + ".meta";
	}

}
