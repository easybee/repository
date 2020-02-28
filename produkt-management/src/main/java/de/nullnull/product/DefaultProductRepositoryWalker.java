package de.nullnull.product;

import java.util.List;

public class DefaultProductRepositoryWalker implements ProductRepositoryWalker {

	private ProductRepository productRepository;

	public DefaultProductRepositoryWalker(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Override
	public List<ProductPackage> list() {
		return null;
	}
}
