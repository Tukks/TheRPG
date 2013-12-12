package chargementDynamique;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Observable;

/**
 * @author giuse_000
 * 
 */
public final class ListenerChargementDyn extends Observable {

	private LinkedList<ChargementDynamique> pluginItem = new LinkedList<ChargementDynamique>();
	private LinkedList<ChargementDynamique> pluginClasse = new LinkedList<ChargementDynamique>();
	private int sizePlug;
	private String folder;
	private static ListenerChargementDyn lcd;
 
	private ListenerChargementDyn(String folder) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		this.folder = folder;
		this.countAllClass();
		this.ChargerAllClass();
		this.ChargerAllJar();
	}

	public void ChargerAllClass() throws MalformedURLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		ListFile lf = new ListFile(folder, "class");

		for (int i = 0; i < lf.listFichier().size(); i++) {
			if (!lf.listFichier().get(i).isDirectory()) {
				ChargementDynamiqueClass cdc = new ChargementDynamiqueClass(lf
						.listFichier().get(i).getAbsolutePath());
				boolean testIfValide = cdc.ChargementClass();

				if (testIfValide && this.isClass(cdc)) {
					pluginClasse.add(cdc);
				} else if (testIfValide && this.isItem(cdc)) {
					pluginItem.add(cdc);

				}
			}
		}
	}

	public void ChargerClass(String root) throws MalformedURLException,
			InstantiationException, IllegalAccessException,
			ClassNotFoundException {
		System.out.println(this.getPluginClasse().size());
		System.out.println("This size Item" + this.getPluginItem().size());
		ChargementDynamiqueClass cdc = new ChargementDynamiqueClass(root);
		boolean testIfValide = cdc.ChargementClass();

		if (testIfValide && this.isClass(cdc)) {
			pluginClasse.add(cdc);
		} else if (testIfValide && this.isItem(cdc)) {
			pluginItem.add(cdc);

		}
	}

	public void ChargerJar(String root) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			MalformedURLException {
		ChargementDynamiqueJar cdc = new ChargementDynamiqueJar(root);
		boolean testIfValide = cdc.ChargermentJar();

		if (testIfValide && this.isClass(cdc)) {
			pluginClasse.add(cdc);
		} else if (testIfValide && this.isItem(cdc)) {
			pluginItem.add(cdc);

		}

	}

	public void ChargerAllJar() throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, IOException {
		ListFile lf = new ListFile(folder, "jar");
		for (int i = 0; i < lf.listFichier().size(); i++) {
			if (!lf.listFichier().get(i).isDirectory()) {
				ChargementDynamiqueJar cdc = new ChargementDynamiqueJar(lf
						.listFichier().get(i).getAbsolutePath());
				boolean testIfValide = cdc.ChargermentJar();

				if (testIfValide && this.isClass(cdc)) {
					pluginClasse.add(cdc);
				} else if (testIfValide && this.isItem(cdc)) {
					pluginItem.add(cdc);

				}
			}
		}
	}

	public void countAllClass() {
		ListFile lf = new ListFile(folder, "");

		this.sizePlug = lf.nombreFichier();
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException,
			IllegalArgumentException, InvocationTargetException, IOException {
		// ListenerChargementDyn lcd = new ListenerChargementDyn("./Plugin");
		Path dir = Paths.get("./Plugin");
		WatchDir WD = new WatchDir(dir, false);
		WD.start();

		// lcd.getPluginClasse().get(0).getListMethode().get(0).invoke(lcd.getPluginClasse().get(0).getClassInstancie());
	}

	public Boolean isItem(ChargementDynamique cd) {
		// TODO Definir Grace au annotation si c'est un Plugin item

		Annotation[] anno = cd.getClassCharged().getAnnotations();
		for (int i = 0; i < anno.length; i++) {
			if (anno[i].toString().contains("Item")) {
				return true;
			}
		}

		return false;
	}

	public Boolean isClass(ChargementDynamique cd) {
		// TODO Definir Grace au annotation si c'est un Plugin Classe de
		// personnage
		Annotation[] anno = cd.getClassCharged().getAnnotations();

		for (int i = 0; i < anno.length; i++) {
			if (anno[i].toString().contains("Classe")) {
				return true;
			}
		}

		return false;

	}

	public static ListenerChargementDyn getInstance()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {
		if (null == lcd) { // Premier appel
			synchronized (ListenerChargementDyn.class) {
				if (null == lcd) {
					lcd = new ListenerChargementDyn("./Plugin");
				}
			}
		}
		return lcd;
	}

	public ChargementDynamique getClassForNamePluginClasse(String name) {
		int i = 0;
		while(i < this.pluginClasse.size()){
			if(this.pluginClasse.get(i).getNameClasse().equalsIgnoreCase(name)){
				return this.pluginClasse.get(i);
			}
		}
		return null;
	}

	public ChargementDynamique getClassForNamePluginItem(String name) {
		int i = 0;
		while(i < this.pluginItem.size()){
			if(this.pluginItem.get(i).getNameItem().equalsIgnoreCase(name)){
				return this.pluginItem.get(i);
			}
		}
		return null;
	}

	public LinkedList<ChargementDynamique> getPluginItem() {
		return pluginItem;
	}

	public void setPluginItem(LinkedList<ChargementDynamique> pluginItem) {
		this.pluginItem = pluginItem;
	}

	public LinkedList<ChargementDynamique> getPluginClasse() {
		return pluginClasse;
	}

	public void setPluginClasse(LinkedList<ChargementDynamique> pluginClasse) {
		this.pluginClasse = pluginClasse;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public int getSizePlug() {
		return sizePlug;
	}

	public void setSizePlug(int sizePlug) {
		this.sizePlug = sizePlug;
	}
}