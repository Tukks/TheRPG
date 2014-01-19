package battle;

import java.io.Serializable;
import java.util.Random;

import serializable.VisitorRPG;

public class Enemy implements Serializable {
	private static final long serialVersionUID = -8487896641271550505L;
	private String nom;
	private int pdv;
	private int attaque;
	private int nbrPotion;
	private int defense;

	private boolean reculer = false;

	/**
	 * Création d'un ennemi aléatoire avec des valeurs aléatoires
	 */
	public Enemy() {
		this.pdv = generateRand(100, 300);
		this.attaque = generateRand(1, 100);
		this.nbrPotion = generateRand(1, 5);
		this.defense = generateRand(1, 10);
		this.nom = generateName();
	}

	/**
	 * Méthode de génération aléatoire de nom d'ennemis
	 * 
	 * @return nom du futur ennemi
	 */
	private String generateName() {
		int i = generateRand(0, 5);
		switch (i) {
		case 0:
			return "Gobelin";
		case 1:
			return "Archer Sauvage";
		case 2:
			return "Guerrier Sauvage";
		case 3:
			return "Pikachu";
		case 4:
			return "Ogre";
		case 5:
			return "Elf";
		default:
			break;
		}
		return "";
	}

	/**
	 * Méthode de valeur aléatoire
	 * 
	 * @param min
	 * @param max
	 * @return valeur aléatoire
	 */
	private int generateRand(int min, int max) {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max - min + 1) + min;
		return nombreAleatoire;
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

	public int getPdv() {
		return pdv;
	}

	public void setPdv(int pdv) {
		this.pdv = pdv;
	}

	public int getAttaque() {
		return attaque;
	}

	public void setAttaque(int attaque) {
		this.attaque = attaque;
	}

	public int getNbrPotion() {
		return nbrPotion;
	}

	public void setNbrPotion(int nbrPotion) {
		this.nbrPotion = nbrPotion;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public boolean isReculer() {
		return reculer;
	}

	public void setReculer(boolean reculer) {
		this.reculer = reculer;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
