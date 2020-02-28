package de.nullnull.product;

public class ProductPackage implements Product {

	private final String nameProduct;
	private final String version;
	private ProductRepository repository = null;

	public ProductPackage(String nameProduct, String version) {
		this.nameProduct = nameProduct;
		this.version = version;
	}

	@Override
	public String getName() {
		return nameProduct;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public ProductRepository getRepository() {
		return repository;
	}

	@Override
	public void setRepository(ProductRepository repository) {
		this.repository = repository;
	}


}
