package personnage;

import java.util.List;

import chargementDynamique.ChargementDynamique;
import objet.Item;

public class Personnage {

	String nom;
	Item item;
	ChargementDynamique classPerso;
	// coeff multiplicateur

	int pointDeVie;
	int forceDeFrappe;
	int defense;

	Personnage(Item item, ChargementDynamique classPerso, String nom) {
		this.item = item;
		this.nom = nom;
		this.classPerso = classPerso;
	}

	public int combattre(List<Item> item) {
		return 0;
	}

}
