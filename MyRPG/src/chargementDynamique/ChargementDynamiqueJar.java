package chargementDynamique;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.Enumeration;

import java.util.jar.*;

public class ChargementDynamiqueJar extends ChargementDynamique {

	ChargementDynamiqueJar(String fileAccess) throws IOException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		this.fichier = new File(fileAccess);
		cl = new URLClassLoader(new URL[] { fichier.toURI().toURL() });
		java.util.jar.JarFile jar = new JarFile(fichier);
		classCharged = cl.loadClass(getNomClasse(jar));
		o = classCharged.newInstance();
		this.listAllMethod();

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
