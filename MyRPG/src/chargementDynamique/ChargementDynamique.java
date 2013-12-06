package chargementDynamique;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;

import annot.Classe;
import annot.Item;

public abstract class ChargementDynamique extends SecureClassLoader { // faire
	
	
	URLClassLoader cl;
	Class<?> classCharged;
	String nomClass;
	Object classInstancie;
	public Object getClassInstancie() {
		return classInstancie;
	}

	/**
	 * @param classInstancie
	 */
	public void setClassInstancie(Object classInstancie) {
		this.classInstancie = classInstancie;
	}

	List<Method> listMethode = new ArrayList<Method>();
	File fichier;

	

	public String getNameItem(){
		return this.classCharged.getAnnotation(Item.class).toString();
		
		
	}
	public String getNameClasse(){
		
		return this.classCharged.getAnnotation(Classe.class).nom();
		
		
	}
	/**
	 * 
	 * @param name
	 * @return Method 
	 */
	public Method getMethodForName(String name) {
		int i = 0;
		while (i < listMethode.size()) {
			if (listMethode.get(i).getName().equalsIgnoreCase(name)) {

				return listMethode.get(i);
			}
			i++;
		}
		return null;
	}

	public void listAllMethod() {
		for (int i = 0; i < classCharged.getDeclaredMethods().length; i++) {
			listMethode.add(classCharged.getDeclaredMethods()[i]);
		}
	}

	public URLClassLoader getCl() {
		return cl;
	}

	public void setCl(URLClassLoader cl) {
		this.cl = cl;
	}

	public Class<?> getClassCharged() {
		return classCharged;
	}

	public void setClassCharged(Class<?> classCharged) {
		this.classCharged = classCharged;
	}

	public String getNomClass() {
		return nomClass;
	}

	public void setNomClass(String nomClass) {
		this.nomClass = nomClass;
	}



	public List<Method> getListMethode() {
		return listMethode;
	}

	public void setListMethode(List<Method> listMethode) {
		this.listMethode = listMethode;
	}

	public File getFichier() {
		return fichier;
	}

	public void setFichier(File fichier) {
		this.fichier = fichier;
	}




	
}
