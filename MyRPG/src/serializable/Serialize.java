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

public class Serialize implements VisitorRPG {
	FileOutputStream fichierOUT;
	FileInputStream fichierIN;
	ObjectInputStream ois;
	ObjectOutputStream oos;

	Serialize() throws IOException {
		// TODO Auto-generated constructor stub
		fichierOUT = new FileOutputStream("SaveJeux.ser");
		fichierIN = new FileInputStream("SaveJeux.ser");
		this.oos = new ObjectOutputStream(fichierOUT);
		this.ois = new ObjectInputStream(fichierIN);
	}

	@Override
	public void visiter(Enemy e) {
		// TODO Auto-generated method stub
		try {
			oos.writeObject(e);
			oos.flush();
			oos.close();
		} catch (java.io.IOException io) {
			io.printStackTrace();
		}
	}

	@Override
	public Enemy devisiteEnemy() {

		try {

			return (Enemy) ois.readObject();

		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

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
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}

	}

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

	@Override
	public void visiter(ChargementDynamique cd) throws IOException {
		oos.writeUTF(cd.getFichier().getPath());
	}

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

	@Override
	public Personnage devisitePersonnage() throws InstantiationException,
	IllegalAccessException {
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
			p = new Personnage(i, classPersonnage, ois.readUTF());

			return p;

		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	private boolean isClass(String path) {
		if (path.contains("class")) {
			return true;
		} else {
			return false;
		}

	}

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

	public static void main(String[] args) throws ClassNotFoundException,
	InstantiationException, IllegalAccessException,
	IllegalArgumentException, InvocationTargetException,
	NoSuchMethodException, SecurityException, IOException {
		Enemy e = new Enemy();
		ChargementDynamiqueJar epee = new ChargementDynamiqueJar(
				"./Plugin/Epee.jar");
		ChargementDynamiqueJar bouclier = new ChargementDynamiqueJar(
				"./Plugin/Bouclier.jar");
		epee.ChargermentJar();
		bouclier.ChargermentJar();
		Item item = new Item(epee, bouclier, epee);
		ChargementDynamiqueJar cd = new ChargementDynamiqueJar(
				"./Plugin/Guerrier.jar");
		cd.ChargermentJar();
		Personnage p = new Personnage(item, cd, "Le gueurier");

		Serialize s = new Serialize();
		s.visiter(p);

		Personnage p1 = s.devisitePersonnage();
		System.out.println(p1.getItem().getArme().getMethodForName("getForce")
				.invoke(p1.getItem().getArme().getClassInstancie()));

	}
}
