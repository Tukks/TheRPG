package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import serializable.VisitorRPG;
import annot.Classe;
import annot.Item;

public abstract class ChargementDynamique extends SecureClassLoader implements Serializable{ /**
	 * 
	 */
	private static final long serialVersionUID = 5093773612781441090L;
// faire

	URLClassLoader cl;
	Class<?> classCharged;
	String nomClass;
	transient Object classInstancie; //a reconstruire a la deserialization
	List<Method> listMethode = new ArrayList<Method>();
	File fichier;
	private java.util.jar.JarFile jar;

	public static ChargementDynamique rebuildClass(Object classCharged) throws InstantiationException, IllegalAccessException{
		ChargementDynamique newClass = new ChargementDynamique() {

			private static final long serialVersionUID = 1L;
		};
		Class<?> cc = (Class<?>) classCharged;
		newClass.setClassCharged(cc);
		newClass.setClassInstancie(cc);
		newClass.listAllMethod();
		return newClass;
		
	}
	
	public boolean ChargementClass() throws InstantiationException, IllegalAccessException {
		try {
			this.classCharged = this.loadClass("");
			this.classInstancie = classCharged.newInstance();
			this.listAllMethod();
		} catch (ClassNotFoundException e) {
			return false;
		}

		return true;
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
	public Object getClassInstancie() {
		return classInstancie;
	}

	/**
	 * @param classInstancie
	 */
	public void setClassInstancie(Object classInstancie) {
		this.classInstancie = classInstancie;
	}

	

	public String getTypeItem() {
		return this.classCharged.getAnnotation(Item.class).type().name();
		// return this.classCharged.getAnnotation(Item.class).type().toString();

	}

	public String getNameItem() {
		return this.classCharged.getAnnotation(Item.class).nom();

	}

	public String getNameClasse() {

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
	public void accept(VisitorRPG visitor){
		visitor.visiter(this);
	}
}
