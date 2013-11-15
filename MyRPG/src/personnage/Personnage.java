package personnage;

import java.util.List;

import objet.Item;

public abstract class Personnage {

	String nom;
	// coeff multiplicateur
	int pointDeVie;
	int forceDeFrappe; 
	int defense;

	public abstract int combattre(List<Item> item);

}
