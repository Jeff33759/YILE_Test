package yile.util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import org.springframework.stereotype.Component;

@Component
public class MyRSAUtil {
	
	private String RSA_PUB_KEY = "-----BEGIN PUBLIC KEY-----\r\n"
			+ "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDS2IQCkGpa+zw4SSofNKVd95qu\r\n"
			+ "5cZ9dXTzNQA2yY9pfCRhlFWa/kZMuThPLFyxuIEyeqaBrya4VVT3bFuRdHzGVnKx\r\n"
			+ "8TXV8U0txrmVSfZBFBuYJBrOTTcxV+CVt1yI6mSezysjA6dkOF+m3z+xAIE7PvYB\r\n"
			+ "LLWkZ8BKxxxHmqOdAwIDAQAB\r\n"
			+ "-----END PUBLIC KEY-----";

	private String RSA_PRIV_KEY = "-----BEGIN PRIVATE KEY-----\r\n"
			+ "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANLYhAKQalr7PDhJ\r\n"
			+ "Kh80pV33mq7lxn11dPM1ADbJj2l8JGGUVZr+Rky5OE8sXLG4gTJ6poGvJrhVVPds\r\n"
			+ "W5F0fMZWcrHxNdXxTS3GuZVJ9kEUG5gkGs5NNzFX4JW3XIjqZJ7PKyMDp2Q4X6bf\r\n"
			+ "P7EAgTs+9gEstaRnwErHHEeao50DAgMBAAECgYA3pd+JkbYQVzi4nk7Yt5thpqUT\r\n"
			+ "vh9aI3L/caCh1Sbdr18mxfCmHLBPNZ9v/gRk/45R1V824RR2GEd5mga86CK593vd\r\n"
			+ "i1BCnyVAHwDthKyoB8AD7kz22K+utJuONyFPp9iT/RyH9gzMT0v3a7vHJWPqzxVm\r\n"
			+ "HAhfRPaj/ADbXy/pYQJBAPKzbICgvhTh0xnZIWPYsX15EMP9SNGNxVbc+5k7Oij7\r\n"
			+ "V0XFzw6pzwuo0B+jelgp5ILUqyJJkPhCwQ9OQFlfinMCQQDeZjuFXWabbu6SCw4Q\r\n"
			+ "cQ2yA8GjrhaPcSF3MLN127SI11nz65wOI8bfXS7fplp8i9jfnj9UqvzBrXJ42Mwg\r\n"
			+ "lS8xAkEA7906eCnlkvfZTiuVX3vzdyGm2zzBsjHefknq83dgtM4rY87rI//ZnBlc\r\n"
			+ "kFheezH0IQWUmU+B0osPCVR6LppJVwJAc+v1ul4kzX/U+mqougkzikZK+HYVbE8b\r\n"
			+ "knTgFd6fC3S0fl+gx/39+w8nD4w2PhTmxihF+mvls04lDLh0LceVAQJBANWDlO8z\r\n"
			+ "w2NHy6Gi9yMDyPCU+2LvMVJcBYxCyYAYL7HQP9d76HkcFgiCktfWhOcjQShGxZZy\r\n"
			+ "C3MorsXbSmAf5Hw=\r\n"
			+ "-----END PRIVATE KEY-----";
	
	/**
	 * Cipher預設是1024密鑰長度。
	 * */
	private String RSA_ALGO = "RSA";

	
	private RSAPrivateKey privKey = getRSAPrivKey();

	
	/**
	 * 生成RSA私鑰物件。
	 * */
	private RSAPrivateKey getRSAPrivKey() {
		String privateKeyContent = 
				this.RSA_PRIV_KEY.replaceAll("\\r\\n", "")
							.replace("-----BEGIN PRIVATE KEY-----", "")
							.replace("-----END PRIVATE KEY-----", "");
        KeyFactory kf;
		try {
			kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
			RSAPrivateKey rsaPrivKey = (RSAPrivateKey)kf.generatePrivate(keySpecPKCS8);
			return rsaPrivKey;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成RSA公鑰物件(目前沒用到。)
	 * */
	@SuppressWarnings("unused")
	private RSAPublicKey getRSAPubKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		String publicKeyContent = 
				this.RSA_PUB_KEY.replaceAll("\\r\\n", "")
							.replace("-----BEGIN PUBLIC KEY-----", "")
							.replace("-----END PUBLIC KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyContent));
        RSAPublicKey rsaPubKey = (RSAPublicKey) kf.generatePublic(keySpecX509);
		return rsaPubKey;
	}
	
	
	/**
	 * 得到RSA的公鑰，傳給前端。</p>
	 * 前端需要前綴與後綴，不能刪掉。
	 * */
	protected String getRsaPubKeyStr() {
		return this.RSA_PUB_KEY;
	}
	
	
	/**
	 * 使用RSA的私鑰解密。
	 * </p>
	 * 受限於RSA特性，所以加密的字串內容不可以大於1024bits，一定要比密鑰小，
	 * 否則會解密失敗，所以要不傳的資料不能太大，要不就是可以做成分段加密、分段解密。
	 * </p>
	 * RSA不適合拿來傳輸大量資料，太慢，所以一般以AES+RSA來實作加密傳輸，RSA加密的是AES的密鑰，
	 * 讓前端把AES密鑰傳給後端，之後兩端溝通還是用AES來實作加密傳輸。
	 * 
	 * @return 編碼為UTF-8的JSON字串。
	 * */
	protected String decryptRSA(String encryptStr) throws Exception {
		byte[] decode = Base64.getDecoder().decode(encryptStr);

        Cipher cipher = Cipher.getInstance(RSA_ALGO);  
        cipher.init(Cipher.DECRYPT_MODE, this.privKey);
        byte[] decryptBytes = cipher.doFinal(decode);
        
        return new String(decryptBytes, "UTF-8"); 
	}
	
}
