package chargementDynamique;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class ChargementDynamiqueClass extends ChargementDynamique {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ChargementDynamiqueClass(String fileAccess)
			throws MalformedURLException, InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		this.fichier = new File(fileAccess);

	}

	

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(
					fichier));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
			while ((n = in.read(buf, 0, buf.length)) > 0) {
				out.write(buf, 0, n);
			}
			in.close();
			byte[] data = out.toByteArray();
			Class<?> c = defineClass(null, data, 0, data.length);
			resolveClass(c);
			return c;
		} catch (ClassFormatError | IOException t) {
			throw new ClassNotFoundException("nop");
		}
	}

	/*
	 * @Override //au cas ou protected Class<?> findClass(String name) throws
	 * ClassNotFoundException { byte[] b; b = loadClassData(name);
	 * 
	 * // TODO Auto-generated catch block return super.defineClass(name, b, 0,
	 * b.length); }
	 * 
	 * @SuppressWarnings("resource") private byte[] loadClassData(String name){
	 * InputStream IS;
	 * 
	 * try { IS = new FileInputStream(path);
	 * 
	 * byte[] plug; plug = new byte[IS.available()]; IS.read(plug);
	 * 
	 * return plug; } catch (IOException e) { // TODO Auto-generated catch block
	 * System.out.println("erreur"); e.printStackTrace(); } return null;
	 * 
	 * }
	 */
}
