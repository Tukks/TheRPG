package chargementDynamique;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * @author giuse_000
 *
 */
public class ListenerChargementDyn extends Thread {
	ArrayList<ChargementDynamique> pluginItem = new ArrayList<ChargementDynamique>();
	ArrayList<ChargementDynamique> pluginClasse = new ArrayList<ChargementDynamique>();
	int sizePlug;
	String folder;

	ListenerChargementDyn(String folder) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		this.folder = folder;
		this.countAllClass();
		this.ChargerAllClass();
		this.ChargerAllJar();
	}

	public void run() {
		
		while (true) {
			ListFile lf = new ListFile(folder, "");
			Integer sizeTemp = lf.nombreFichier();
			if (sizeTemp.intValue() != sizePlug) {
				
				if (sizeTemp.intValue() < sizePlug) {
					System.out.println("une classe en moins dude");

					sizePlug = sizeTemp.intValue();
					try {
						this.ChargerAllClass();
						this.ChargerAllJar();
					} catch (MalformedURLException | InstantiationException
							| IllegalAccessException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// on va supprimer celui qui est plus la
				} else if (sizeTemp.intValue() > sizePlug) {
					sizePlug = sizeTemp.intValue();
					System.out.println("une classe en plus dude");
					try {
						this.ChargerAllClass();
						this.ChargerAllJar();
					} catch (MalformedURLException | InstantiationException
							| IllegalAccessException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
					// on va ajouter celui qui est nouveaux

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

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
		ListenerChargementDyn lcd = new ListenerChargementDyn("./Plugin");
		
		Thread test = new Thread(lcd);
		test.start();
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

	public void getClassForName(String name) {

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