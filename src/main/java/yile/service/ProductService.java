package yile.service;

import yile.model.dto.send.AllProductsRes;

public interface ProductService {
	
	public AllProductsRes listAllProducts() throws Exception;

	public void createProductWithAES(String aesStr) throws Exception;

	public void createProductWithAES2(String aesStr2) throws Exception;

}
