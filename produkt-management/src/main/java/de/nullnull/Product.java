package de.nullnull;

public interface Product {

	public String getName();
	public String getVersion();
	public ProductRepository getRepository();
	public void setRepository(ProductRepository repository);
}
