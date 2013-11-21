package chargementDynamique;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public abstract class ChargementDynamique {

	ChargementDynamique(){
		
	}
	
	void charger(String fileAccess) throws MalformedURLException{
		//test if annotatin = item
		URLClassLoader cl = new URLClassLoader(new URL[] { 
				new File(fileAccess).toURI().toURL() 
			});
		
		//Class<?> plugin = 
		/**Class<?> cl1 = cl.loadClass("plugin.MyPlugin");
		Object o = cl1.newInstance();
		Method m = cl1.getMethod("run");
		
		//Method m = cl1.getDeclaredMethod("run");
		m.invoke(o);*/
		//return plugin.newInstance();
	}
}
