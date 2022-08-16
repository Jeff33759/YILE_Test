package yile.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import yile.model.dto.receive.ProductReq;
import yile.model.po.Product;

@Component
public class MyUtil {

	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MyAESUtil aesUtil;
	
	@Autowired
	private MyRSAUtil rsaUtil;
	
	
	
	/**
	 * 將AES加密後的字串進行解密，並反序列化為POJO。
	 * */
	public ProductReq handleTheAESStrToObj(String encryptStr) throws Exception {  
		String decryptStr = aesUtil.decryptAES(encryptStr);
		return objectMapper.readValue(decryptStr, ProductReq.class);
	}
	
	/**
	 * 得到RSA的公鑰。
	 * */
	public String getRSAPubKey() {
		return rsaUtil.getRsaPubKeyStr();
	}
	
	/**
	 * 使用AES密鑰2解密，然後轉化為POJO。
	 * */
	public ProductReq handleTheAESStr2ToObj(String encryptStr) throws Exception {  
		String decryptStr = aesUtil.decryptAESBySecret2(encryptStr);
		return objectMapper.readValue(decryptStr, ProductReq.class);
	}	

	/**
	 * 將RSA加密後的字串進行解密，並反序列化為AES密鑰字串，
	 * 並賦值給AESUtil的成員變數供使用。
	 * */
	public void handleTheRSAStrToAESSecretAndSetIt(String encryptStr) throws Exception {  
		String decryptAESKeyStr = rsaUtil.decryptRSA(encryptStr);
		aesUtil.setAESSecret2(decryptAESKeyStr);
	}	

	
	public Product convertDtoToPo(ProductReq dto) {
		Product product = new Product();
		product.setChnName(dto.getPro().getChnName());
		product.setSpec(dto.getPro().getSpec());
		return product;
	}
	
	
}
