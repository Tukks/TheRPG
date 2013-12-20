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

	public Serialize() throws FileNotFoundException {
		// TODO Auto-generated constructor stub
		fichierOUT = new FileOutputStream("SaveJeux.ser");
		fichierIN = new FileInputStream("SaveJeux.ser");
	}

	@Override
	public void visiter(Enemy e) {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oos = new ObjectOutputStream(fichierOUT);
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
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fichierIN);
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

			ObjectOutputStream oos = new ObjectOutputStream(fichierOUT);
			// Sauvegarde Item
			oos.writeUTF(p.getItem().getArme().getFichier().getPath());
			oos.writeUTF(p.getItem().getArmure().getFichier().getPath());
			oos.writeUTF(p.getItem().getPotion().getFichier().getPath());
			// Sauvegarde Classe de personnage
			oos.writeUTF(p.getClassPerso().getFichier().getPath());
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
			ObjectOutputStream oos = new ObjectOutputStream(fichierOUT);
			oos.writeObject(i);
			oos.flush();
			oos.close();
		} catch (java.io.IOException io) {
			io.printStackTrace();
		}
	}

	@Override
	public void visiter(ChargementDynamique cd) {
		// TODO Auto-generated method stub
		try {
			ObjectOutputStream oos = new ObjectOutputStream(fichierOUT);
			oos.writeObject(cd.getFichier().getPath());
			oos.flush();
			oos.close();
		} catch (java.io.IOException io) {
			io.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String devisiteChargementDynamique() {
		// TODO Auto-generated method stub
		try {
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fichierIN);
			return (String) ois.readObject();

		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Personnage devisitePersonnage() throws InstantiationException, IllegalAccessException {
		try {
			Personnage p;
			Item i;
			ChargementDynamique potion;
			ChargementDynamique armure;
			ChargementDynamique arme;
			ChargementDynamique classPersonnage;
			String tmp;
			@SuppressWarnings("resource")
			ObjectInputStream ois = new ObjectInputStream(fichierIN);
			//Get Arme
			tmp = ois.readUTF();
			if(this.isClass(tmp)){
				arme = new ChargementDynamiqueClass(tmp);
				arme.ChargementClass();
				
			}else{
				arme = new ChargementDynamiqueJar(tmp);
				arme.ChargermentJar();
			}
			//Get Armure
			tmp = ois.readUTF();
			if(this.isClass(tmp)){
				armure = new ChargementDynamiqueClass(tmp);
				armure.ChargementClass();
				
			}else{
				armure = new ChargementDynamiqueJar(tmp);
				armure.ChargermentJar();
			}
			//Get Potion
			tmp = ois.readUTF();
			if(this.isClass(tmp)){
				potion = new ChargementDynamiqueClass(tmp);
				potion.ChargementClass();
				
			}else{
				potion = new ChargementDynamiqueJar(tmp);
				potion.ChargermentJar();
			}
			i = new Item(arme, armure, potion);
			tmp = ois.readUTF();
			if(this.isClass(tmp)){
				classPersonnage = new ChargementDynamiqueClass(tmp);
				classPersonnage.ChargementClass();
				
			}else{
				classPersonnage = new ChargementDynamiqueJar(tmp);
				classPersonnage.ChargermentJar();
			}
			p = new Personnage(i, classPersonnage, ois.readUTF());
			
			return p;

		} catch (java.io.IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;

	}

	private boolean isClass(String path){
		if(path.contains("class")){
			return true;
		}else{
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

	public static void main(String[] args) throws FileNotFoundException,
	ClassNotFoundException, InstantiationException,
	IllegalAccessException, MalformedURLException,
	IllegalArgumentException, InvocationTargetException,
	NoSuchMethodException, SecurityException {
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

		// System.out.println(p.getItem().getArme().getMethodForName("getForce").invoke(p.getItem().getArme().getClassInstancie()));

		Serialize s = new Serialize();
		s.visiter(p);

		// ChargementDynamique t = new
		// ChargementDynamiqueJar(s.devisiteChargementDynamique());

		Personnage p1 = s.devisitePersonnage();
		System.out.println(p1.getItem().getArme().getMethodForName("getForce").invoke(p1.getItem().getArme().getClassInstancie()));

	}
}
