package personnage;

import java.util.List;

import objet.Item;

public abstract class Personnage {
	int pointDeVie;
	int forceDeFrappe; //coeff multiplicateur
	int defense;
	String nom;
	
	
	public abstract int combattre(List<Item> item);
	
}
