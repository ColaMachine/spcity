package cola.machine.util.encrypt;

import sun.misc.BASE64Decoder;

import java.io.IOException;

public class Base64Util {
	
	// 将 s 进行 BASE64 编码
		public static String getBASE64(String s) {
			if (s == null)
				return null;
			return (new sun.misc.BASE64Encoder()).encode(s.getBytes());
		}
	public static byte[] decode(String s){
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			byte[] b = decoder.decodeBuffer(s);
			return  b;
		} catch (Exception e) {
			return null;
		}
	}
	public static String  encode(byte[] b){
		if (b == null)
			return null;
		return (new sun.misc.BASE64Encoder()).encode(b);
	}
		// 将 BASE64 编码的字符串 s 进行解码
		public static String getFromBASE64(String s) {
			if (s == null)
				return null;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(s);
				return new String(b);
			} catch (Exception e) {
				return null;
			}
		}

	public static byte[] decodeBuffer(String s ) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		return decoder.decodeBuffer(s);
	}

}
