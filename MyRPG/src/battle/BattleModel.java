package battle;

import java.util.Observable;

import personnage.Personnage;

public class BattleModel extends Observable {

	Personnage perso;
	Enemy enemy;
	String text;

	public BattleModel() {
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

}
