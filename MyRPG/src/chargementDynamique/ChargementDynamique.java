package chargementDynamique;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.*;

public class ChargementDynamique {
	URLClassLoader cl;
	Class<?> classCharged;
	String nomClass;
	Object o;
	List<Method> listMethode = new ArrayList<Method>();

	ChargementDynamique(String fileAccess) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		File fichier = new File(fileAccess);
		cl = new URLClassLoader(new URL[] { 
				fichier.toURI().toURL() 
		});
		java.util.jar.JarFile jar = new JarFile(fichier);
	
		classCharged = cl.loadClass(getNomClasse(jar));
		o = classCharged.newInstance();
		for(int i = 0; i < classCharged.getDeclaredMethods().length;i++){
			listMethode.add(classCharged.getDeclaredMethods()[i]);
		}

	}

	public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		try {
			ChargementDynamique ch = new ChargementDynamique("./Plugin/MyPlug1.jar");
			//	ch.getListMethode().get(0).invoke(ch.getO());
			String c = (String) ch.getListMethode().get(1).invoke(ch.getO());
			System.out.println(c);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String getNomClasse(JarFile jar){
		Enumeration<JarEntry> enume = jar.entries();
		JarEntry nomFichier;
		while(enume.hasMoreElements()){
			nomFichier = enume.nextElement();
			String nomFile = nomFichier.toString();
			if(nomFile.contains(".class") && !nomFile.contains(".classpath") ){
				return nomFile.replace("/", ".").substring(0, nomFile.length()-6);
			}
		}
		return null;
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
	public Method getMethodForName(String name) {
		int i = 0;
		while(i < listMethode.size()){
			if(listMethode.get(i).getName().equalsIgnoreCase(name)){
				return listMethode.get(i);
			}
		}
		return null;
	}


	public List<Method> getListMethode() {
		return listMethode;
	}

	public void setListMethode(List<Method> listMethode) {
		this.listMethode = listMethode;
	}

}
