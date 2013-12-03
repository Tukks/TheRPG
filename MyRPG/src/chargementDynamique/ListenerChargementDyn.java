package chargementDynamique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;


public class ListenerChargementDyn implements ActionListener {
	ArrayList<ChargementDynamique> pluginItem = new ArrayList<ChargementDynamique>();
	ArrayList<ChargementDynamique> pluginClasse = new ArrayList<ChargementDynamique>();
	String folder;
	ListenerChargementDyn(String folder){
		this.folder = folder;
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Regarder a intervalleregulier dans le dossier Plugin
		
	}
	public void ChargerAllClass() throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		ListFile lf = new ListFile(folder, "class");
		ArrayList<File> path = new ArrayList<File>();  

		for (int i = 0; i < lf.listFichier().size(); i++) {
			if(!lf.listFichier().get(i).isDirectory()){
				ChargementDynamiqueClass cdc = new ChargementDynamiqueClass(lf.listFichier().get(i).getAbsolutePath());
				if(cdc.isClass()){
					pluginClasse.add(cdc);
				}else if(cdc.isItem()){
					pluginItem.add(cdc);

				}
			}
		}
	}
	public void ChargerAllJar() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException{
		ListFile lf = new ListFile(folder, "jar");
		ArrayList<File> path = new ArrayList<File>();  

		for (int i = 0; i < lf.listFichier().size(); i++) {
			if(!lf.listFichier().get(i).isDirectory()){
				ChargementDynamiqueJar cdc = new ChargementDynamiqueJar(lf.listFichier().get(i).getAbsolutePath());
				if(cdc.isClass()){
					pluginClasse.add(cdc);
				}else if(cdc.isItem()){
					pluginItem.add(cdc);

				}
			}
		}
	}
	public static void main(String[] args) throws MalformedURLException, InstantiationException, IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException{
		ListenerChargementDyn lcd = new ListenerChargementDyn("./Plugin");
		lcd.ChargerAllClass();
		System.out.println(lcd.getPluginClasse().size());
		lcd.getPluginClasse().get(0).getListMethode().get(0).invoke(lcd.getPluginClasse().get(0).getClassInstancie());
	}
	
	public void getClassForName(String name){
		
	}
	public ArrayList<ChargementDynamique> getPluginItem() {
		return pluginItem;
	}
	public void setPluginItem(ArrayList<ChargementDynamique> pluginItem) {
		this.pluginItem = pluginItem;
	}
	public ArrayList<ChargementDynamique> getPluginClasse() {
		return pluginClasse;
	}
	public void setPluginClasse(ArrayList<ChargementDynamique> pluginClasse) {
		this.pluginClasse = pluginClasse;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	
}