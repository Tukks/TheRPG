package serializable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import objet.Item;
import personnage.Personnage;
import battle.Enemy;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ChargementDynamiqueClass;
import chargementDynamique.ChargementDynamiqueJar;

// TODO: Auto-generated Javadoc
/**
 * The Class Serialize.
 */
public class Serialize implements VisitorRPG {
	
	/** The fichier out. */
	FileOutputStream fichierOUT;
	
	/** The fichier in. */
	FileInputStream fichierIN;
	
	/** The ois. */
	ObjectInputStream ois;
	
	/** The oos. */
	ObjectOutputStream oos;

	/**
	 * Instantiates a new serialize.
	 *
	 * 
	 * Instantiates a new serialize for load
	 *
	 * @param fichierIN the fichier in
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	 
	public Serialize(FileInputStream fichierIN) throws IOException {
		// TODO Auto-generated constructor stub
		
		this.fichierIN = fichierIN; 
		//this.oos = new ObjectOutputStream(fichierOUT);
		this.ois = new ObjectInputStream(fichierIN);
		
	}

	/**
	 * Instantiates a new serialize for Save
	 *
	 * @param fichierOUT the fichier out
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public Serialize(FileOutputStream fichierOUT) throws IOException {
		// TODO Auto-generated constructor stub
		//fichierOUT = new FileOutputStream("./SaveJeux.ser");
		this.fichierOUT = fichierOUT; 
		//this.oos = new ObjectOutputStream(fichierOUT);
		this.oos = new ObjectOutputStream(fichierOUT);
		
	}
	public Serialize(){
		
	}
	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#visiter(battle.Enemy)
	 */
	@Override
	public void visiter(Enemy e) {
		// TODO Auto-generated method stub
		
		try {
			FileOutputStream fichier = new FileOutputStream("enemy.ser");
			
			ObjectOutputStream oos = new ObjectOutputStream(fichier);
			
			oos.writeObject(e);
			
			oos.flush();
			
			oos.close();
		fichier.close();
		} catch (java.io.IOException io) {
			//io.printStackTrace();
			System.out.println("rater");
		}
	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#devisiteEnemy()
	 */
	@Override
	public Enemy devisiteEnemy() {

		try {
			FileInputStream fichier = new FileInputStream("enemy.ser");
			
			ObjectInputStream ois = new ObjectInputStream(fichier);
			return (Enemy) ois.readObject();

		} catch (java.io.IOException e) {
			
			return new Enemy();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#visiter(personnage.Personnage)
	 */
	@Override
	public void visiter(Personnage p) {
		// TODO Auto-generated method stub

		try {
			this.visiter(p.getItem());
			// Sauvegarde Classe de personnage
			this.visiter(p.getClassPerso());
			oos.writeUTF(p.getNom());
			// sauvegarde Etat personnage
			oos.writeInt(p.getPointDeVie());
			oos.writeInt(p.getForceDeFrappe());
			oos.writeInt(p.getDefense());

			oos.flush();

			
			oos.close();
			fichierOUT.close();
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#visiter(objet.Item)
	 */
	@Override
	public void visiter(Item i) {
		// TODO Auto-generated method stub
		try {
			// Sauvegarde Item
			this.visiter(i.getArme());
			this.visiter(i.getArmure());
			this.visiter(i.getPotion());

		} catch (java.io.IOException io) {
			io.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#visiter(chargementDynamique.ChargementDynamique)
	 */
	@Override
	public void visiter(ChargementDynamique cd) throws IOException {
		oos.writeUTF(cd.getFichier().getPath());
		
	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#devisiteChargementDynamique()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ChargementDynamique devisiteChargementDynamique()
			throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		try {
			String tmp;
			tmp = ois.readUTF();
			if (this.isClass(tmp)) {
				ChargementDynamiqueClass tmpClass = new ChargementDynamiqueClass(
						tmp);
				tmpClass.ChargementClass();
				return tmpClass;
			} else {
				ChargementDynamiqueJar tmpJar = new ChargementDynamiqueJar(tmp);
				tmpJar.ChargermentJar();
				return tmpJar;
			}
		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#devisitePersonnage()
	 */
	@Override
	public Personnage devisitePersonnage() throws InstantiationException,
	IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		try {
			Personnage p;
			Item i;
			ChargementDynamique potion;
			ChargementDynamique armure;
			ChargementDynamique arme;
			ChargementDynamique classPersonnage;

			// Get Arme

			arme = this.devisiteChargementDynamique();

			// Get Armure

			armure = this.devisiteChargementDynamique();

			// Get Potion

			potion = this.devisiteChargementDynamique();

			i = new Item(arme, armure, potion);
			//Get class de personnage
			classPersonnage = this.devisiteChargementDynamique();
//recreer Pers
			p = Personnage.getInstance();
			p.init(i, classPersonnage, ois.readUTF());
		
			ois.close();
			fichierIN.close();
			return p;

		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * Checks if is class.
	 *
	 * @param path the path
	 * @return true, if is class
	 */
	private boolean isClass(String path) {
		if (path.contains("class")) {
			return true;
		} else {
			return false;
		}

	}

	/* (non-Javadoc)
	 * @see serializable.VisitorRPG#devisiteItem()
	 */
	@Override
	public Item devisiteItem() {
		try {
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fichierIN);
			return (Item) ois.readObject();

		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws ClassNotFoundException the class not found exception
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InvocationTargetException the invocation target exception
	 * @throws NoSuchMethodException the no such method exception
	 * @throws SecurityException the security exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void main(String[] args) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException,
	NoSuchMethodException, SecurityException, IOException {
		
		
		ChargementDynamiqueJar epee = new ChargementDynamiqueJar(
				"./Plugin/Epee.jar");
		ChargementDynamiqueJar bouclier = new ChargementDynamiqueJar(
				"./Plugin/Bouclier.jar");
		ChargementDynamiqueJar potion = new ChargementDynamiqueJar("./Plugin/Elixir.jar");
		epee.ChargermentJar();
		bouclier.ChargermentJar();
		potion.ChargermentJar();
		Item item = new Item(epee, bouclier, potion);
		ChargementDynamiqueJar cd = new ChargementDynamiqueJar(
				"./Plugin/Guerrier.jar");
		cd.ChargermentJar();
		Personnage p = Personnage.getInstance();
		p.init(item, cd, "Le gueurier");
		/*FileOutputStream fichierOUT = new FileOutputStream("./SaveJeux.ser");
		Serialize save = new Serialize(fichierOUT);
		save.visiter(p);*/
		FileInputStream fichierIN = new FileInputStream("./SaveJeux.ser");

		Serialize load = new Serialize(fichierIN);

		
		Personnage p1 = load.devisitePersonnage();
		System.out.println(p1.getNom());
	

	}
}
