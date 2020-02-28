package de.nullnull;

public interface ProductRepositoryLayout {

	String getId();

	String pathOf(ProductPackage productPackage);

	String pathOfMetadata();
}
