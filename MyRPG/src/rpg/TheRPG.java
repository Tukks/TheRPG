/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rpg;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.SecureClassLoader;
import java.util.ArrayList;


/**
 *
 * @author giuse_000
 */
public class TheRPG extends SecureClassLoader {
	public ArrayList<File> path = new ArrayList<File>();  
    /**
     * @param args the command line arguments
     * 
     */
    public static void main(String[] args) {
        System.out.println("Hello");
        TheRPG test = new TheRPG();
        try {
			test.ChargementDyn();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //Un commentaire
    }
    void ChargementDyn() throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
    	URLClassLoader cl = new URLClassLoader(new URL[] { new File("C:\\MyPlug.jar").toURI().toURL() });
    	Class<?> cl1 =  cl.loadClass("plugin.MyPlugin");
    	Object o =  cl1.newInstance(); 
    	Method m = cl1.getMethod("run");
    	m.invoke(o);
    	
    	//Method m = cl1.getDeclaredMethod("run");
    	

    	
    }
   
}
