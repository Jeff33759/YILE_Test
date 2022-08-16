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
import yile.util.MyUtil;

@Controller
public class ProductController {
	
	private final ProductService pService;
	
	@Autowired
	private MyUtil util;
	
	@Autowired
	public ProductController(ProductServiceImpl pServiceImpl) {
		this.pService = pServiceImpl;
	}

	/**
	 * AES表單畫面，輸入完資料後，使用AES對稱型加密。
	 * */
	@GetMapping(path = "/yile/aesform")
	public String aesFormPage() throws Exception {
		return "aes_form";
	}

	/**
	 * RSA表單畫面。
	 * */
	@GetMapping(path = "/yile/rsaform")
	public String rsaFormPage() throws Exception {
		return "rsa_form";
	}
	
	/**
	 * RSA表單畫面一進去，要非同步請求一個rsa公鑰用於表單加密。
	 * */
	@GetMapping(path = "/yile/rsapubkey")
	@ResponseBody
	public ResponseEntity<?> getPubkey() throws Exception {
		String rsaPubKeyStr = util.getRSAPubKey();
		return ResponseEntity.ok(rsaPubKeyStr);
	}
	
	/**
	 * 前端得到RSA公鑰後將AES密鑰加密後端，後端以此API接收後，存放並使用。
	 * */
	@PostMapping(path = "/yile/aessecret")
	@ResponseBody
	public ResponseEntity<?> setSecretKey(@RequestBody String rsaEncStr) throws Exception {
		util.handleTheRSAStrToAESSecretAndSetIt(rsaEncStr);
		return ResponseEntity.ok(null);
	}
	
	

	/**
	 * AES列出資料庫資料的View。
	 * */
	@GetMapping(path = "/yile/aeslist")
	public String aesListPage() throws Exception {
		return "aes_list";
	}

	/**
	 * RSA列出資料庫資料的View。
	 * */
	@GetMapping(path = "/yile/rsalist")
	public String rsaListPage() throws Exception {
		return "rsa_list";
	}
	
	/**
	 * 查詢所有表單資料的API，用於兩個List的View。
	 * */
	@GetMapping(path = "/yile/prods")
	@ResponseBody
	public ResponseEntity<?> queryAllProductApi() throws Exception {
		AllProductsRes res = pService.listAllProducts();
		return ResponseEntity.ok(res);
	}


	/**
	 * 接收AES加密後表單資料的API。
	 * */
	@PostMapping(path = "/yile/aesform")
	@ResponseBody
	public ResponseEntity<?> aesFormApi(@RequestBody String aesStr) throws Exception {
		pService.createProductWithAES(aesStr);
        URI location = ServletUriComponentsBuilder
        		.fromCurrentRequest()
        		.replacePath("/yile/aeslist")
                .buildAndExpand()
                .toUri();
		return ResponseEntity.created(location).body(null);
	}
	
	/**
	 * 接收RSA加密後表單資料的API。
	 * */
	@PostMapping(path = "/yile/rsaform")
	@ResponseBody
	public ResponseEntity<?> rsaFormApi(@RequestBody String aesStr) throws Exception {
		pService.createProductWithAES2(aesStr);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.replacePath("/yile/rsalist")
				.buildAndExpand()
				.toUri();
		return ResponseEntity.created(location).body(null);
	}


}
