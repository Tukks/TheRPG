package chargementDynamique;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;

import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.List;





public class ChargementDynamiqueClass extends SecureClassLoader implements ChargementDynamique {
	URLClassLoader cl;
	Class<?> classCharged;
	String nomClass;
	Object o;
	List<Method> listMethode = new ArrayList<Method>();
	File path;
	ChargementDynamiqueClass(String fileAccess) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		this.path = new File(fileAccess);
		this.classCharged =  this.loadClass(""); //Loadclass appel Loadclassdata
		this.o = classCharged.newInstance();
		this.listAllMethod();
		
	}

	@Override  
	protected Class<?> findClass(String name) throws ClassNotFoundException {  
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(path));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n;
			while ((n=in.read(buf,0,buf.length))>0){
				out.write(buf,0,n);
			}
			in.close();
			byte[] data = out.toByteArray();
			Class<?> c = defineClass(null, data, 0, data.length);
			resolveClass(c);
			return c;
		} catch (Throwable t) {
			t.printStackTrace();
			throw new ClassNotFoundException(name);
		}
	}  

	

	

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		ChargementDynamiqueClass mcl = new ChargementDynamiqueClass("./Plugin/MyPlugin.class");
		mcl.getMethodForName("run").invoke(mcl.getO());
	}  


	void getNomClasse(){
		String nom;
		nom = path.getAbsolutePath();
		System.out.println(nom);


	}
	@Override
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


	@Override
	public void listAllMethod() {
		// TODO Auto-generated method stub
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

	public File getPath() {
		return path;
	}

	public void setPath(File path) {
		this.path = path;
	}
	/*@Override  //au cas ou
	protected Class<?> findClass(String name) throws ClassNotFoundException {  
		byte[] b;
		b = loadClassData(name);

		// TODO Auto-generated catch block
		return super.defineClass(name, b, 0, b.length);  
	}
	@SuppressWarnings("resource")
	private byte[] loadClassData(String name){ 
		InputStream IS;

		try {
			IS = new FileInputStream(path);

			byte[] plug;
			plug = new byte[IS.available()];
			IS.read(plug);

			return plug;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("erreur");
			e.printStackTrace();
		}
		return null;

	} */ 
}
