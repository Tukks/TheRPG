package personnage;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import objet.Item;
import serializable.VisitorRPG;
import chargementDynamique.ChargementDynamique;

public class Personnage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 707821524617984045L;
	String nom;
	Item item;
	ChargementDynamique classPerso;
	private static Personnage pers;
	// coeff multiplicateur

	int pointDeVie;
	int forceDeFrappe;
	int defense;

	

	private Personnage() {
		pointDeVie = 0;
		forceDeFrappe = 0;
		defense = 0;
	}
	public static Personnage getInstance(){
		if (null == pers) { // Premier appel


			pers = new Personnage();

		}

		return pers;
	}
	public void init(Item item, ChargementDynamique classPerso, String nom) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.item = item;
		this.nom = nom;
		this.classPerso = classPerso;
		pointDeVie =(int) this.classPerso.getMethodForName("getPdv").invoke(this.classPerso.getClassInstancie());
		forceDeFrappe =(int) this.classPerso.getMethodForName("getForce").invoke(this.classPerso.getClassInstancie()) +
				(int) this.item.getArme().getMethodForName("getForce").invoke(this.item.getArme().getClassInstancie());
		defense = (int) this.classPerso.getMethodForName("getDefense").invoke(this.classPerso.getClassInstancie()) +
				(int) this.item.getArmure().getMethodForName("getDefense").invoke(this.item.getArmure().getClassInstancie());
		
	}
	public int combattre(List<Item> item) {
		return 0;
	}

	public void accept(VisitorRPG visitor) {
		visitor.visiter(this);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public ChargementDynamique getClassPerso() {
		return classPerso;
	}

	public void setClassPerso(ChargementDynamique classPerso) {
		this.classPerso = classPerso;
	}

	public int getPointDeVie() {
		return pointDeVie;
	}

	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}

	public int getForceDeFrappe() {
		return forceDeFrappe;
	}

	public void setForceDeFrappe(int forceDeFrappe) {
		this.forceDeFrappe = forceDeFrappe;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
