package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ChargementDynamiqueJar.
 */
public class ChargementDynamiqueJar extends ChargementDynamique {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2101763447674054511L;

	/**
	 * Instantiates a new chargement dynamique jar.
	 *
	 * @param fileAccess the file access
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws MalformedURLException the malformed url exception
	 */
	public ChargementDynamiqueJar(String fileAccess)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, MalformedURLException {

		this.fichier = new File(fileAccess);
		cl = new URLClassLoader(new URL[] { fichier.toURI().toURL() });
		
		

	}

	/**
	 * Chargerment jar.
	 *
	 * @return true, if successful
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public boolean ChargermentJar() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		try {
			jar = new JarFile(fichier);
			classCharged = cl.loadClass(getNomClasse(jar));
			classInstancie = classCharged.newInstance();
			this.listAllMethod();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Gets the nom classe.
	 *
	 * @param jar the jar
	 * @return the nom classe
	 */
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
