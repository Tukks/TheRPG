package chargementDynamique;

import java.io.IOException;
import java.lang.annotation.Annotation;

import java.net.MalformedURLException;

import java.util.LinkedList;
import java.util.Observable;

// TODO: Auto-generated Javadoc
/**
 * The Class ListenerChargementDyn.
 *
 * @author giuse_000
 */
public final class ListenerChargementDyn extends Observable {

	/** The plugin item. */
	private LinkedList<ChargementDynamique> pluginItem = new LinkedList<ChargementDynamique>();
	
	/** The plugin classe. */
	private LinkedList<ChargementDynamique> pluginClasse = new LinkedList<ChargementDynamique>();
	
	/** The size plug. */
	private int sizePlug;
	
	/** The folder. */
	private String folder;
	
	/** The lcd. */
	private static ListenerChargementDyn lcd;

	/**
	 * Instantiates a new listener chargement dyn.
	 *
	 * @param folder the folder
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private ListenerChargementDyn(String folder) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		this.folder = folder;
		this.countAllClass();
		this.ChargerAllClass();
		this.ChargerAllJar();
	}

	/**
	 * Charger all class dans le dossier courant.
	 *
	 * @throws MalformedURLException the malformed url exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	private void ChargerAllClass() throws MalformedURLException,
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

	/**
	 * Charger un .class en particulier.
	 *
	 * @param root the root
	 * @throws MalformedURLException the malformed url exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 */
	void ChargerClass(String root) throws MalformedURLException,
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
		
		this.setChanged();
		this.notifyObservers();
	}

	/**
	 * Charger un .jar en particulier.
	 *
	 * @param root the root
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws MalformedURLException the malformed url exception
	 */
	void ChargerJar(String root) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			MalformedURLException {
		ChargementDynamiqueJar cdc = new ChargementDynamiqueJar(root);
		boolean testIfValide = cdc.ChargermentJar();

		if (testIfValide && this.isClass(cdc)) {
			pluginClasse.add(cdc);

		} else if (testIfValide && this.isItem(cdc)) {
			pluginItem.add(cdc);
			

		}
		this.setChanged();
		this.notifyObservers();


	}

	/**
	 * Charger all jar.
	 *
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void ChargerAllJar() throws ClassNotFoundException,
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

	/**
	 * Count all class.
	 */
	private void countAllClass() {
		ListFile lf = new ListFile(folder, "");

		this.sizePlug = lf.nombreFichier();
	}

	

	/**
	 * Checks if is item.
	 *
	 * @param cd the cd
	 * @return the boolean
	 */
	private Boolean isItem(ChargementDynamique cd) {
		// TODO Definir Grace au annotation si c'est un Plugin item

		Annotation[] anno = cd.getClassCharged().getAnnotations();
		for (int i = 0; i < anno.length; i++) {
			if (anno[i].toString().contains("Item")) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if is class.
	 *
	 * @param cd the cd
	 * @return the boolean
	 */
	private Boolean isClass(ChargementDynamique cd) {
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

	/**
	 * Gets the single instance of ListenerChargementDyn.
	 *
	 * @return single instance of ListenerChargementDyn
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws ClassNotFoundException the class not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static ListenerChargementDyn getInstance()
			throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {
		if (null == lcd) { // Premier appel
			synchronized (ListenerChargementDyn.class) {
				if (null == lcd) {
					lcd = new ListenerChargementDyn("C:/Users/giuse_000/Desktop/rpg/Plugin");
				}
			}
		}
		return lcd;
	}
	
	/**
	 * Gets the class for name plugin classe.
	 *
	 * @param name the name
	 * @return the class for name plugin classe
	 */
	public ChargementDynamique getClassForNamePluginClasse(String name) {
		int i = 0;
		while (i < this.pluginClasse.size()) {
			if (this.pluginClasse.get(i).getNameClasse().equalsIgnoreCase(name)) {
				return this.pluginClasse.get(i);
			}
			i++;
		}
		return null;
	}

	/**
	 * Gets the class for name plugin item.
	 *
	 * @param name the name
	 * @return the class for name plugin item
	 */
	public ChargementDynamique getClassForNamePluginItem(String name) {
		int i = 0;
		while (i < this.pluginItem.size()) {
			if (this.pluginItem.get(i).getNameItem().equalsIgnoreCase(name)) {
				return this.pluginItem.get(i);
			}
			i++;
		}
		return null;
	}

	/**
	 * Gets the plugin item.
	 *
	 * @return the plugin item
	 */
	public LinkedList<ChargementDynamique> getPluginItem() {
		return pluginItem;
	}

	/**
	 * Sets the plugin item.
	 *
	 * @param pluginItem the new plugin item
	 */
	public void setPluginItem(LinkedList<ChargementDynamique> pluginItem) {
		this.pluginItem = pluginItem;
	}

	/**
	 * Gets the plugin classe.
	 *
	 * @return the plugin classe
	 */
	public LinkedList<ChargementDynamique> getPluginClasse() {
		return pluginClasse;
	}

	/**
	 * Sets the plugin classe.
	 *
	 * @param pluginClasse the new plugin classe
	 */
	public void setPluginClasse(LinkedList<ChargementDynamique> pluginClasse) {
		this.pluginClasse = pluginClasse;
	}

	/**
	 * Gets the folder.
	 *
	 * @return the folder
	 */
	public String getFolder() {
		return folder;
	}

	/**
	 * Sets the folder.
	 *
	 * @param folder the new folder
	 */
	public void setFolder(String folder) {
		this.folder = folder;
	}

	/**
	 * Gets the size plug.
	 *
	 * @return the size plug
	 */
	public int getSizePlug() {
		return sizePlug;
	}

	/**
	 * Sets the size plug.
	 *
	 * @param sizePlug the new size plug
	 */
	public void setSizePlug(int sizePlug) {
		this.sizePlug = sizePlug;
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observable#setChanged()
	 */
	public void setChanged(){
	    super.setChanged();
	}
	
	/* (non-Javadoc)
	 * @see java.util.Observable#notifyObservers()
	 */
	public void notifyObservers(){
		super.notifyObservers();
	}
	
}