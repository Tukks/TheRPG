package battle;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Observable;

import objet.Item;
import chargementDynamique.ChargementDynamiqueJar;
import personnage.Personnage;

public class BattleModel extends Observable {

	Personnage perso;
	Enemy enemy;
	String text = new String();

	public BattleModel(Personnage perso, Enemy enemy) {
		this.perso = perso;
		this.enemy = enemy;
		
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
		while(combatFinish(enemy,perso)){
			enemy.setPdv(enemy.getPdv() - perso.getForceDeFrappe());
			perso.setPointDeVie(perso.getPointDeVie() - pdvEnleverPerso(enemy.getAttaque()));
			
			//a traiter enemy et perso == 0 pv
		}
	}
	public boolean combatFinish(Enemy enemy, Personnage perso){
		
		if(enemy.getPdv() > 0 && perso.getPointDeVie() > 0){
			text = "\n Il reste a l'enemie " + enemy.getPdv() + " et au perso " + perso.getPointDeVie();
			setChanged();
			notifyObservers();
			return true;
		}else if(enemy.getPdv() < 0 && perso.getPointDeVie() > 0){
			text = "\n Il reste a l'enemie " + 0 + " et au perso " + perso.getPointDeVie();
			text +="\n Le perso " + perso.getNom() + " a Gagner";
			setChanged();
			notifyObservers();
			return false;
		}else if(enemy.getPdv() > 0 && perso.getPointDeVie() < 0){
			text ="\n Il reste a l'enemie " + enemy.getPdv() + " et au perso " + 0;
			text +="\n L'enemie " + enemy.getNom() + " a Gagner";
			setChanged();
			notifyObservers();
			return false;
		}
		System.out.println("Erreur exception");
		return false;
	}
	public int pdvEnleverPerso(int attaque){
		if(perso.getDefense() > attaque){
			return 0;
		}else{
			return attaque - perso.getDefense();
		}
	}
	String getText(){
		return text;
	}
	
}

