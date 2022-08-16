package yile.util;

import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

@Component
public class MyAESUtil {

	/**
	 * BouncyCastle是開源的第三方演算法提供商，
	 * 提供一些Java標準函式庫沒有提供的哈希演算法或加密演算法，
	 * 例如AES的PKCS7填充模式。
	 * 
	 * 使用第三方演算法，需要通過Security.addProvider()，
	 * 寫在static區塊，於程式啟動時先註冊。
	 * */
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	/**
	 * AES密鑰，前後端都要存放一樣的。
	 * 字元數必須要是16(128bits)/24(192bits)/32(256bits)
	 * */
	private String AES_SECRET = "yiletestingfromjeffhuang";

	/**
	 * 用於RSA表單頁面。
	 * 由RSA_FORM頁面已非同步請求方式經交握後，才會賦值。
	 * */
	private String AES_SECRET2;

	/**
	 * 因為前端那邊是PKCS7填充模式去加密，所以後端也要PKCS7去解密。
	 * */
	private String AES_PKCS7_ALGO = "AES/ECB/PKCS7Padding";
	
	
	/**
	 *	前端是PKCS7填充模式，所以後端也要PKCS7填充模式去解密，但JAVA
	 *	自帶的API只有支援PKCS5，所以要額外引入函式庫bcprov-jdk15on。
	 *	雖然經測，前端PKCS7加密，後端PKCS5也能正常解密，不需要額外引入函式庫，
	 *	但還是保守一點好。
	 *
	 *	@return 以UTF-8編碼生成的JSON字串(前端那邊也以UTF-8對原始資料進行編碼過)
	 * */
	protected String decryptAES(String encryptStr) throws Exception {
		byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);
		SecretKeySpec sKeySpec = 
				new SecretKeySpec(this.AES_SECRET.getBytes(),"AES");

		Cipher cipher = Cipher.getInstance(AES_PKCS7_ALGO);  
        cipher.init(Cipher.DECRYPT_MODE, sKeySpec);  
        byte[] decryptBytes = cipher.doFinal(encryptBytes);
        
        return new String(decryptBytes, "UTF-8");  
	}

	/**
	 * 用第二個密鑰解析AES，屬於RSA表單頁面的DEMO。
	 * */
	protected String decryptAESBySecret2(String encryptStr) throws Exception {
		byte[] encryptBytes = Base64.getDecoder().decode(encryptStr);
		SecretKeySpec sKeySpec = 
				new SecretKeySpec(this.AES_SECRET2.getBytes(),"AES");
		
		Cipher cipher = Cipher.getInstance(AES_PKCS7_ALGO);  
		cipher.init(Cipher.DECRYPT_MODE, sKeySpec);  
		byte[] decryptBytes = cipher.doFinal(encryptBytes);
		
		return new String(decryptBytes, "UTF-8");  
	}
	
	protected void setAESSecret2(String Secret2Str) {
		this.AES_SECRET2 = Secret2Str;
	}
	
}
