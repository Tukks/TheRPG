package battle;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;


import java.util.Random;

import personnage.Personnage;

public class BattleModel extends Observable {

	Personnage perso;
	Enemy enemy;
	String text = new String();
	int pdvPersoMax;
 int utilisePotion = 0;
 int utilisePoison = 0;
	public BattleModel(Personnage perso, Enemy enemy) {
		this.perso = perso;
		this.enemy = enemy;
		//setVal("debut by Model");
		this.pdvPersoMax = perso.getPointDeVie();
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
	public void Combat(int pdvMinPerso) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		
		while(this.combatFinish(enemy,perso)){
			int PourcentPerso = (int)((this.getPerso().getPointDeVie() * 100.0f) /pdvPersoMax);
			
			if(PourcentPerso < pdvMinPerso  && perso.getItem().getPotion().getTypeItem() == "Potion" && utilisePotion < 2 && !perso.getItem().getPotion().getNameItem().contains("auto")){ //a definir par l'utilisateur plus tard
				int soin = (int) perso.getItem().getPotion().getMethodForName("soigne").invoke(perso.getItem().getPotion().getClassInstancie());
				perso.setPointDeVie(perso.getPointDeVie() + soin);
				utilisePotion++;
				text = "\n Le personnage utilise une potion et gagne : " + soin + " il a donc : " +perso.getPointDeVie() +" pv";
				setChanged();
				notifyObservers();
			}else if(perso.getItem().getPotion().getNameItem().contains("auto") && perso.getItem().getPotion().getTypeItem() == "Potion" && utilisePotion < 15){
				int soin = (int) perso.getItem().getPotion().getMethodForName("soigne").invoke(perso.getItem().getPotion().getClassInstancie());
				perso.setPointDeVie(perso.getPointDeVie() + soin);
				utilisePotion++;
				text = "\n Le personnage utilise une potion auto et gagne : " + soin + " il a donc : " +perso.getPointDeVie() +" pv";
				setChanged();
				notifyObservers();
			}else if(PourcentPerso < pdvMinPerso && perso.getItem().getPotion().getTypeItem() == "Poison" && utilisePoison < 2 && !perso.getItem().getPotion().getNameItem().contains("auto")){
				int degat = (int) perso.getItem().getPotion().getMethodForName("enlevePv").invoke(perso.getItem().getPotion().getClassInstancie());
				enemy.setPdv(enemy.getPdv() + degat);	
				utilisePoison++;
				text = "\n Le personnage utilise poison enleve : " + degat + " il rest a l'enemi : " +enemy.getPdv() +" pv";
				setChanged();
				notifyObservers();

			}else if(perso.getItem().getPotion().getNameItem().contains("auto") && perso.getItem().getPotion().getTypeItem() == "Poison" && utilisePoison < 15){
				int degat = (int) perso.getItem().getPotion().getMethodForName("enlevePv").invoke(perso.getItem().getPotion().getClassInstancie());
				enemy.setPdv(enemy.getPdv() + degat);	
				utilisePoison++;
				text = "\n Le personnage utilise poison auto enleve : " + degat + " il reste a l'enemi : " +enemy.getPdv() +" pv";
				setChanged();
				notifyObservers();
			}
				if(!enemy.isReculer() || perso.getClassPerso().getNameClasse().equalsIgnoreCase("Archer")){
					enemy.setPdv(enemy.getPdv() - perso.getForceDeFrappe());
					int i = generateRand(0, 1);
					if( i == 1){
						enemy.setReculer(true);
					}else{
						enemy.setReculer(false);
					}
				}else{
					enemy.setPdv(enemy.getPdv() - (int)(perso.getForceDeFrappe() * 0.5));
					text = "\n l'enemie est loin, vous infligez 50% de dégat en moins";
					int i = generateRand(0, 1);
					if( i == 1){
						enemy.setReculer(true);
					}else{
						enemy.setReculer(false);
					}
				}
				perso.setPointDeVie(perso.getPointDeVie() - pdvEnleverPerso(enemy.getAttaque()));
				
			//a traiter enemy et perso == 0 pv
		}
		
	}
	public boolean combatFinish(Enemy enemy, Personnage perso){
		
		if(enemy.getPdv() > 0 && perso.getPointDeVie() > 0){
			
			text += "\n Il reste a l'enemie " + enemy.getPdv() + " et au perso " + perso.getPointDeVie();
			setChanged();
			notifyObservers();
			return true;
		}else if(enemy.getPdv() <= 0 && perso.getPointDeVie() > 0){
			text = "\n Il reste a l'enemie " + 0 + " et au perso " + perso.getPointDeVie();
			text +="\n Le perso " + perso.getNom() + " a Gagner";
			setChanged();
			notifyObservers();
			return false;
		}else if(enemy.getPdv() >= 0 && perso.getPointDeVie() < 0){
			text ="\n Il reste a l'enemie " + enemy.getPdv() + " et au perso " + 0;
			text +="\n L'enemie " + enemy.getNom() + " a Gagner";
			setChanged();
			notifyObservers();
			return false;
		}else if(enemy.getPdv() == 0 && perso.getPointDeVie() == 0){
			text ="Match Nul, tout les 2 a 0";
			setChanged();
			notifyObservers();
			return false;
		}
		System.out.println("Erreur exception");
		return true;
	}
	public int pdvEnleverPerso(int attaque){
		if(perso.getDefense() > attaque){
			text +="\n L'enemie " + enemy.getNom() + " n'a pas assez de force pour vous battre";
			text +="\n L'enemie " + enemy.getNom() + " prend un SuperAttaque qui lui fait gagner 1 en attaque";
			enemy.setAttaque(enemy.getAttaque() + 1);
			return 0;
		
		}else{
			return attaque - perso.getDefense();
		}
	}
	String getText(){
		return text;
	}
	public void setText(String t){
		text = t;
	}

	int generateRand(int min, int max){
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max - min + 1) + min;
		return nombreAleatoire;
	}
	
}

