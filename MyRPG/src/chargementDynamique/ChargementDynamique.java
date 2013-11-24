package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;

public abstract class ChargementDynamique extends SecureClassLoader { // faire en class abastract
	URLClassLoader cl;
	Class<?> classCharged;
	String nomClass;
	Object o;
	List<Method> listMethode = new ArrayList<Method>();
	File fichier;
	ChargementDynamique(){
		
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ChargementDynamiqueClass mcl = new ChargementDynamiqueClass("./Plugin/MyPlugin.class");
		Annotation[] test  =mcl.getMethodForName("coucou").getAnnotations();
		System.out.println(test.length);
		try {
			ChargementDynamiqueJar ch = new ChargementDynamiqueJar("./Plugin/MyPlug1.jar");
			//	ch.getListMethode().get(0).invoke(ch.getO());
			//String c = (String) ch.getListMethode().get(1).invoke(ch.getO());
			Class<?> t =  ch.getMethodForName("coucou").getReturnType();
			System.out.println(t);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}  
	public Method getMethodForName(String name) {
		int i = 0;
		while(i < listMethode.size()){
			if(listMethode.get(i).getName().equalsIgnoreCase(name)){
				
				return listMethode.get(i);
			}
			i++;
		}
		return null;
	}
	
	public void listAllMethod(){
		for(int i = 0; i < classCharged.getDeclaredMethods().length;i++){
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

	public Object getO() {
		return o;
	}

	public void setO(Object o) {
		this.o = o;
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
