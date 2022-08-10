package yile.controller;



import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import yile.model.dto.send.AllProductsRes;
import yile.service.ProductService;
import yile.serviceimpl.ProductServiceImpl;

@Controller
public class ProductController {
	
	private final ProductService pService;
	
	
	@Autowired
	public ProductController(ProductServiceImpl pServiceImpl) {
		this.pService = pServiceImpl;
	}

	
	
	@GetMapping(path = "/yile/form")
	public String formPage() throws Exception {
		return "index";
	}

	@GetMapping(path = "/yile/list")
	public String listPage() throws Exception {
		return "list";
	}
	
	
	@GetMapping(path = "/yile/prods")
	@ResponseBody
	public ResponseEntity<?> queryAllProduct() throws Exception {
		AllProductsRes res = pService.listAllProducts();
		return ResponseEntity.ok(res);
	}


	@PostMapping(path = "/yile/form")
	@ResponseBody
	public ResponseEntity<?> formApi(@RequestBody String aesStr) throws Exception {
		pService.createProduct(aesStr);
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequest()
        		.replacePath("/yile/list")
                .buildAndExpand()
                .toUri();
		return ResponseEntity.created(location).body(null);
	}
	


}
