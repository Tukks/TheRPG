package battle;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.Observable;

import objet.Item;
import chargementDynamique.ChargementDynamiqueJar;
import personnage.Personnage;

public class BattleModel extends Observable {

	Personnage perso;
	Enemy enemy;
	String text;
	private static BattleModel bm;

	public BattleModel(Personnage perso, Enemy enemy) {
		this.perso = perso;
		this.enemy = enemy;
		System.out.println("Model()");
		setVal("debut by Model");
	}
	
	public void setVal(String text) { // mode PULL
		this.text = text;
		setChanged();
		notifyObservers();
	}

	public void lancerCombat() { // mode PULL == incrementValue()
		setChanged();
		notifyObservers();
	}

	public String getVal() {
		return this.text;
	}

	public Personnage getPerso() {
		return perso;
	}

	public void setPerso(Personnage perso) {
		this.perso = perso;
	}

	public Enemy getEnemy() {
		return enemy;
	}
	
	public void setEnemy(Enemy enemy) {
		this.enemy = enemy;
	}
	public void Combat() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		while(perso.getPointDeVie() >= 0 && enemy.getPdv() >= 0){
			enemy.setPdv(enemy.getPdv() - perso.getForceDeFrappe());
			perso.setPointDeVie(perso.getPointDeVie() - pdvEnleverPerso(enemy.getAttaque()));
			System.out.println("Il reste a l'enemie " + enemy.getPdv() + " et au perso " + perso.getPointDeVie());
		}
		
	}
	
	public int pdvEnleverPerso(int attaque){
		return attaque - perso.getDefense();
		
	}
	
	
}

