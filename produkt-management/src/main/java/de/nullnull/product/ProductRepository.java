package de.nullnull.product;

import java.util.List;

public interface ProductRepository {

	String getId();

	String getUrl();

	String getBasedir();

	String getProtocol();

	ProductRepositoryLayout getLayout();

	ProductRepositoryWalker getWalker();
	
	List<ProductPackage> list();
}
