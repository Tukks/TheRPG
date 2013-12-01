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
			ChargementDynamiqueClass mcl = new ChargementDynamiqueClass(
					"./Plugin/MyPlugin.class"); //on charge le .class
			ChargementDynamiqueJar ch = new ChargementDynamiqueJar(
					"./Plugin/MyPlugAnno.jar"); //On charge le .jar

			ChargementDynamiqueJar jar = new ChargementDynamiqueJar(
					"./Plugin/MyPlugTest.jar"); //On charge le .jar

			/**
			 * On recupere notre classe charger MCL, 
			 * on recupere la methode par son nom "Coucou" 
			 * et on l'utilise avec invoke, en mettant en param la class Instancié
			 *  (deja presente dans la L'objet ChargementDynamiqueClass)
			 */
			System.out.println(mcl.getMethodForName("coucou").invoke(mcl.getClassInstancie()));
			/**
			 * Magique on peut recuperer directement la liste des methodes sans leur nom
			 */
			System.out.println(mcl.getListMethode().get(1).invoke(mcl.getClassInstancie()));
			/**
			 * Meme facon de faire que ce soit un class ou un jar qui a etait chargé
			 */
			System.out.println(ch.getListMethode().get(1).invoke(ch.getClassInstancie())); 

			/**
			 * et La cerise, on peut faire des liste de class chargé et utiliser leur methode
			 */
			ArrayList<ChargementDynamique> teste = new ArrayList<ChargementDynamique>();
			teste.add(mcl);
			teste.add(ch);
			teste.add(jar);
			
			System.out.println(teste.get(2).getMethodForName("run").invoke(teste.get(2).getClassInstancie()));
			
			System.out.println(teste.get(2).getMethodForName("run").getAnnotations()[0]);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
