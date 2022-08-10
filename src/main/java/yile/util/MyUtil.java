package yile.util;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import yile.model.dto.receive.ProductReq;
import yile.model.po.Product;

@Component
public class MyUtil {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private String decryptKey = "yiletestingfromjeffhuang";
	
	private String algoStr = "AES/ECB/PKCS5Padding";
	
	/**
	 * 將AES加密後的字串進行解密，並反序列化為POJO。
	 * */
	public ProductReq handleTheAESStrToObj(String encryptStr) throws Exception {  
		String decryptStr = decryptAES(encryptStr);
		return objectMapper.readValue(decryptStr, ProductReq.class);
	}
	
	private String decryptAES(String encryptStr) throws Exception {
		byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");  
        keyGen.init(128); 
        Cipher cipher = Cipher.getInstance(algoStr);  
        cipher.init(Cipher.DECRYPT_MODE, 
        		new SecretKeySpec(decryptKey.getBytes(), "AES"));  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);  
        return new String(decryptBytes);  
	}
	
	public Product convertDtoToPo(ProductReq dto) {
		Product product = new Product();
		product.setChnName(dto.getPro().getChnName());
		product.setSpec(dto.getPro().getSpec());
		return product;
	}
	
	
}
