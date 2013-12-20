package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.*;

public class ChargementDynamiqueJar extends ChargementDynamique {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2101763447674054511L;

	public ChargementDynamiqueJar(String fileAccess)
			throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, MalformedURLException {

		this.fichier = new File(fileAccess);
		cl = new URLClassLoader(new URL[] { fichier.toURI().toURL() });
		
		

	}

	
	

}
