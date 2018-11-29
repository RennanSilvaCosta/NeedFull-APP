package tcc.etec.needful.view.view.util;

import java.security.MessageDigest;

public class CriptografaSenha {
	

	public static String criptografaSenha(String senha) {
		return md5(sha_256(senha));
	}

	private static String sha_256(String u) {
		String senhaCrip = null;

		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte messageDigest[] = md.digest(u.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (Byte b : messageDigest) {
				sb.append(String.format("%02x", 0xFF & b));
			}
			senhaCrip = sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return senhaCrip;
	}

	private static String md5(String u) {
		String senhaCrip = null;

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte messageDigest[] = md.digest(u.getBytes("UTF-8"));
			StringBuilder sb = new StringBuilder();
			for (Byte b : messageDigest) {
				sb.append(String.format("%02x", 0xFF & b));
			}
			senhaCrip = sb.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return senhaCrip;
	}

}
