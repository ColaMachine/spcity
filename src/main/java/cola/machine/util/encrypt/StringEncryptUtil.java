package cola.machine.util.encrypt;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import cola.machine.util.CommonUtils;


/**
 * 字符串的加密和解密
 * @author awifi-core
 * @date 2015年1月6日 下午6:07:31
 */
public final class StringEncryptUtil {
	/**
	 * 
	 */
	private static final String ALGORITHM = "DES";

	/**
	 * The Default Key.
	 */
	public static final String DEFAULT_KEY = "asdfsadf@#$%^$%^%^&*&asdf24243423234";

	/**
	 * 说明:加密
	 * @param originalString 原始字符串（待加密）
	 * @return 
	 * @throws Exception
	 */
	public static String encrypt(final String originalString) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), DEFAULT_KEY.getBytes());
		return CommonUtils.parseHexStringFromBytes(bEn);
	}

	/**
	 * 说明:加密
	 * @param originalString 原始字符串（待加密）
	 * @param key 按自定义键值加密
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(final String originalString, final String key) throws Exception {
		byte[] bEn = encrypt(originalString.getBytes(), key.getBytes());
		return CommonUtils.parseHexStringFromBytes(bEn);
	}

	/**
	 * 加密实际方法
	 * @param originalByte 原始字符数组（待加密）
	 * @param key 加密键值
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] originalByte, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源 
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象 
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成 
		// 一个SecretKey对象 
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作 
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, keySpec, sr);
		//执行加密操作
		return cipher.doFinal(originalByte);

	}

	/**
	 * 解密
	 * @param encryptedString 密文字符串（待解密）
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedString) throws Exception {
		byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, DEFAULT_KEY.getBytes());
		return new String(orginal);
	}

	/**
	 * 解密
	 * @param encryptedString 密文字符串（待解密）
	 * @param key 解密键值
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(final String encryptedString, final String key) throws Exception {
		byte[] bEn = CommonUtils.parseBytesByHexString(encryptedString);
		byte[] orginal = decrypt(bEn, key.getBytes());
		return new String(orginal);
	}

	/**
	 * 解密实际方法
	 * @param encryptedByte 密文字符数组（待解密）
	 * @param key 解密键值
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] encryptedByte, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
		SecretKey keySpec = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		//加密和解密的区别在第一个参数
		cipher.init(Cipher.DECRYPT_MODE, keySpec, sr);
		return cipher.doFinal(encryptedByte);
	}
	
	public static void main(String[] args) {
		String username_id = "石鹏皮皮@126.com";
		try { 
			String cookieValue = StringEncryptUtil.encrypt(username_id);
			System.out.println(cookieValue);
			String value = StringEncryptUtil.decrypt(cookieValue);
			System.out.println(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
