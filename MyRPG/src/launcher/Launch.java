package launcher;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import chargementDynamique.ChargementDynamique;
import chargementDynamique.ChargementDynamiqueClass;
import chargementDynamique.ChargementDynamiqueJar;

public class Launch {

	public static void main(String[] args) throws InstantiationException,
	IllegalAccessException, ClassNotFoundException, IOException,
	IllegalArgumentException, InvocationTargetException,
	NoSuchMethodException, SecurityException {
		try {
			
			ChargementDynamiqueJar Classe = new ChargementDynamiqueJar("./Plugin/MyPlugClasse.jar");
			Classe.ChargermentJar();
			Classe.getName();
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
