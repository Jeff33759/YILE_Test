package yile.serviceimpl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import yile.dao.ProductRepo;
import yile.model.dto.receive.ProductReq;
import yile.model.dto.send.AllProductsRes;
import yile.model.po.Product;
import yile.service.ProductService;
import yile.util.MyUtil;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private MyUtil myUtil;
	
	@Autowired
	private ProductRepo productRepo;
	
	
	@Override
	public AllProductsRes listAllProducts() throws Exception {
		List<Product> prods = productRepo.findAll();
		AllProductsRes res = new AllProductsRes();
		res.setProArr(prods);
		return res;
	}

	@Override
	public void createProduct(String aesStr) throws Exception {
		ProductReq prodReq = myUtil.handleTheAESStrToObj(aesStr);
		Product newProd = myUtil.convertDtoToPo(prodReq);
		productRepo.save(newProd);
	}

}
