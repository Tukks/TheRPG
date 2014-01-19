/*
 * 
 */
package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;

import java.util.List;

import serializable.VisitorRPG;
import annot.Classe;
import annot.Item;

// TODO: Auto-generated Javadoc
/**
 * The Class ChargementDynamique.
 */
public abstract class ChargementDynamique extends SecureClassLoader implements
		Serializable {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5093773612781441090L;
	// faire

	/** The cl. */
	URLClassLoader cl;

	/** The class charged. */
	Class<?> classCharged;

	/** The nom class. */
	private String nomClass;

	/** The class instancie. */
	transient Object classInstancie; // a reconstruire a la deserialization

	/** The list methode. */
	private List<Method> listMethode = new ArrayList<Method>();

	/** The fichier. */
	File fichier;

	/** The jar. */
	java.util.jar.JarFile jar;

	

	/**
	 * Gets the class instancie.
	 * 
	 * @return the class instancie
	 */
	public Object getClassInstancie() {
		return classInstancie;
	}

	/**
	 * Sets the class instancie.
	 * 
	 * @param classInstancie
	 *            the new class instancie
	 */
	public void setClassInstancie(Object classInstancie) {
		this.classInstancie = classInstancie;
	}

	/**
	 * Gets the type item.
	 * 
	 * @return the type item
	 */
	public String getTypeItem() {
		return this.classCharged.getAnnotation(Item.class).type().name();
		// return this.classCharged.getAnnotation(Item.class).type().toString();

	}

	/**
	 * Gets the name item.
	 * 
	 * @return the name item
	 */
	public String getNameItem() {
		return this.classCharged.getAnnotation(Item.class).nom();

	}

	/**
	 * Gets the name classe.
	 * 
	 * @return the name classe
	 */
	public String getNameClasse() {

		return this.classCharged.getAnnotation(Classe.class).nom();

	}

	public String getIcoClasse() {
		return this.classCharged.getAnnotation(Classe.class).ico();
	}

	/**
	 * Gets the method for name.
	 * 
	 * @param name
	 *            the name
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

	/**
	 * List all method.
	 */
	void listAllMethod() {
		for (int i = 0; i < classCharged.getDeclaredMethods().length; i++) {
			listMethode.add(classCharged.getDeclaredMethods()[i]);
		}
	}

	/**
	 * Gets the classInstancié.
	 * 
	 * @return the cl
	 */
	public URLClassLoader getCl() {
		return cl;
	}

	/**
	 * Sets the cl.
	 * 
	 * @param cl
	 *            the new cl
	 */
	public void setCl(URLClassLoader cl) {
		this.cl = cl;
	}

	/**
	 * Gets the class charged.
	 * 
	 * @return the class charged
	 */
	public Class<?> getClassCharged() {
		return classCharged;
	}

	/**
	 * Sets the class charged.
	 * 
	 * @param classCharged
	 *            the new class charged
	 */
	public void setClassCharged(Class<?> classCharged) {
		this.classCharged = classCharged;
	}

	/**
	 * Gets the nom class.
	 * 
	 * @return the nom class
	 */
	public String getNomClass() {
		return nomClass;
	}

	/**
	 * Sets the nom class.
	 * 
	 * @param nomClass
	 *            the new nom class
	 */
	public void setNomClass(String nomClass) {
		this.nomClass = nomClass;
	}

	/**
	 * Gets the list methode.
	 * 
	 * @return the list methode
	 */
	public List<Method> getListMethode() {
		return listMethode;
	}

	/**
	 * Sets the list methode.
	 * 
	 * @param listMethode
	 *            the new list methode
	 */
	public void setListMethode(List<Method> listMethode) {
		this.listMethode = listMethode;
	}

	/**
	 * Gets the fichier.
	 * 
	 * @return the fichier
	 */
	public File getFichier() {
		return fichier;
	}

	/**
	 * Sets the fichier.
	 * 
	 * @param fichier
	 *            the new fichier
	 */
	public void setFichier(File fichier) {
		this.fichier = fichier;
	}

	/**
	 * Accept.
	 * 
	 * @param visitor
	 *            the visitor
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void accept(VisitorRPG visitor) throws IOException {
		visitor.visiter(this);
	}
}
