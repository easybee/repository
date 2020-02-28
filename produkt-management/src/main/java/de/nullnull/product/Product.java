package de.nullnull.product;

public interface Product {

	public String getName();
	public String getVersion();
	public ProductRepository getRepository();
	public void setRepository(ProductRepository repository);
}
