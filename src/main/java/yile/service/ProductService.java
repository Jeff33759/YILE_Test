package yile.service;

import yile.model.dto.send.AllProductsRes;

public interface ProductService {
	
	public AllProductsRes listAllProducts() throws Exception;

	public void createProduct(String aesStr) throws Exception;

}
