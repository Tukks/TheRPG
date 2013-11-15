/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rpg;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     * @throws MalformedURLException 
     * @throws InvocationTargetException 
     * @throws IllegalArgumentException 
     * @throws SecurityException 
     * @throws NoSuchMethodException 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     * @throws InstantiationException 
     * @throws URISyntaxException 
     */
    public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException, URISyntaxException {
        System.out.println("Hello");
        TheRPG test = new TheRPG();
        test.ChargementDyn();
        //Un commentaire
    }
    void ChargementDyn() throws MalformedURLException, NoSuchMethodException, SecurityException, ClassNotFoundException {
    	URLClassLoader cl = new URLClassLoader(new URL[] { new File("C:\\MyPlug.jar").toURI().toURL() });
    	URL[] url = new URL[] { new File("C:\\MyPlug.jar").toURI().toURL() };
    	System.out.println(url[0].toString());
    	java.lang.Class.forName("MyPlugin", false, cl);
    }
   
}
