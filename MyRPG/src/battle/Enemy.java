package battle;

import java.util.Random;

public class Enemy {
	String nom;
	int pdv;
	int attaque;
	int nbrPotion;
	int defense;
	
	public Enemy() {
		// TODO Auto-generated constructor stub
		this.pdv = generateRand(100,1000);
		this.attaque = generateRand(1,10);
		this.nbrPotion = generateRand(1,5);
		this.defense = generateRand(1, 10);
		this.nom = generateName();
	}
	String generateName(){
		int i = generateRand(0, 5);
		switch (i) {
		case 0:
			return "Goblin";
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
	int generateRand(int min, int max){
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max - min + 1) + min;
		return nombreAleatoire;
	}
	
}
