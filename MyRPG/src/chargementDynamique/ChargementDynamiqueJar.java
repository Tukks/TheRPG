package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.*;

public class ChargementDynamiqueJar extends ChargementDynamique {
	private java.util.jar.JarFile jar;

	public ChargementDynamiqueJar(String fileAccess)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, MalformedURLException {

		this.fichier = new File(fileAccess);
		cl = new URLClassLoader(new URL[] { fichier.toURI().toURL() });

		

	}

	public boolean ChargermentJar() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			jar = new JarFile(fichier);
			classCharged = cl.loadClass(getNomClasse(jar));
			classInstancie = classCharged.newInstance();
			this.listAllMethod();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return false;
		}
		return true;
	}

	private String getNomClasse(JarFile jar) {
		Enumeration<JarEntry> enume = jar.entries();
		JarEntry nomFichier;
		while (enume.hasMoreElements()) {
			nomFichier = enume.nextElement();
			String nomFile = nomFichier.toString();
			if (nomFile.contains(".class") && !nomFile.contains(".classpath")) {
				return nomFile.replace("/", ".").substring(0,
						nomFile.length() - 6);
			}
		}
		return null;
	}

}
