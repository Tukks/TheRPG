package objet;

import java.io.Serializable;
import java.util.ArrayList;

import serializable.VisitorRPG;
import chargementDynamique.ChargementDynamique;

public class Item implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2613658683071421393L;
	ChargementDynamique Arme;
	ChargementDynamique Armure;
	ChargementDynamique Potion;
	public int select(){
		return 1;
	}

	public Item(ChargementDynamique arme,
			ChargementDynamique armure,
			ChargementDynamique potion) {
		super();
		Arme = arme;
		Armure = armure;
		Potion = potion;
	}
	public void accept(VisitorRPG visitor){
		visitor.visiter(this);
	}

	public ChargementDynamique getArme() {
		return Arme;
	}

	public void setArme(ChargementDynamique arme) {
		Arme = arme;
	}

	public ChargementDynamique getArmure() {
		return Armure;
	}

	public void setArmure(ChargementDynamique armure) {
		Armure = armure;
	}

	public ChargementDynamique getPotion() {
		return Potion;
	}

	public void setPotion(ChargementDynamique potion) {
		Potion = potion;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
