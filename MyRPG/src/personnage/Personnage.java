package personnage;

import java.io.Serializable;
import java.util.List;

import chargementDynamique.ChargementDynamique;
import objet.Item;
import serializable.VisitorRPG;

public class Personnage implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 707821524617984045L;
	String nom;
	Item item;
	ChargementDynamique classPerso;
	// coeff multiplicateur

	int pointDeVie;
	int forceDeFrappe;
	int defense;

	public Personnage(Item item, ChargementDynamique classPerso, String nom) {
		this.item = item;
		this.nom = nom;
		this.classPerso = classPerso;
	}

	public int combattre(List<Item> item) {
		return 0;
	}
	
	public void accept(VisitorRPG visitor){
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
