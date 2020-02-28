package de.nullnull.product;

public interface ProductRepositoryLayout {

	String getId();

	String pathOf(ProductPackage productPackage);

	String pathOfMetadata();
}
