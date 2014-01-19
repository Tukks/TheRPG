package battle;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Random;

import personnage.Personnage;

public class BattleModel extends Observable {

	private Personnage perso;
	private Enemy enemy;
	private String text = new String();
	private int pdvPersoMax;
	private int utilisePotion = 0;
	private int utilisePoison = 0;

	/**
	 * @param perso
	 *            utilisation du personnage créée au préalable
	 * @param enemy
	 *            utilisation de l'ennemi créee aléatoirement
	 */
	public BattleModel(Personnage perso, Enemy enemy) {
		this.perso = perso;
		this.enemy = enemy;
		this.pdvPersoMax = perso.getPointDeVie();
	}

	public void setVal(String text) { // mode PULL
		this.text = text;
		setChanged();
		notifyObservers();
	}

	public String getImage() {
		return perso.getClassPerso().getIcoClasse();
	}

	/**
	 * méthode qui va lancer le combat et activer l'Observable
	 */
	void lancerCombat() { // mode PULL == incrementValue()
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

	/**
	 * Combat stratégique avec les différents cas, qui vont venir s'instancier
	 * au cours du combat pour répartir les elixirs ou poisons.
	 * 
	 * @param pdvMinPerso
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	void Combat(int pdvMinPerso) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		while (this.combatFinish(enemy, perso)) {
			int PourcentPerso = (int) ((this.getPerso().getPointDeVie() * 100.0f) / pdvPersoMax);

			if (PourcentPerso < pdvMinPerso
					&& perso.getItem().getPotion().getTypeItem() == "Potion"
					&& utilisePotion < 2
					&& !perso.getItem().getPotion().getNameItem()
							.contains("auto")) { // a definir par l'utilisateur
													// plus tard
				int soin = (int) perso
						.getItem()
						.getPotion()
						.getMethodForName("soigne")
						.invoke(perso.getItem().getPotion().getClassInstancie());
				perso.setPointDeVie(perso.getPointDeVie() + soin);
				utilisePotion++;
				text = "\n Le personnage utilise une potion et gagne : " + soin
						+ " il a donc : " + perso.getPointDeVie() + " pv";
				setChanged();
				notifyObservers();
			} else if (perso.getItem().getPotion().getNameItem()
					.contains("auto")
					&& perso.getItem().getPotion().getTypeItem() == "Potion"
					&& utilisePotion < 15) {
				int soin = (int) perso
						.getItem()
						.getPotion()
						.getMethodForName("soigne")
						.invoke(perso.getItem().getPotion().getClassInstancie());
				perso.setPointDeVie(perso.getPointDeVie() + soin);
				utilisePotion++;
				text = "\n Le personnage utilise une potion auto et gagne : "
						+ soin + " il a donc : " + perso.getPointDeVie()
						+ " pv";
				setChanged();
				notifyObservers();
			} else if (PourcentPerso < pdvMinPerso
					&& perso.getItem().getPotion().getTypeItem() == "Poison"
					&& utilisePoison < 2
					&& !perso.getItem().getPotion().getNameItem()
							.contains("auto")) {
				int degat = (int) perso
						.getItem()
						.getPotion()
						.getMethodForName("enlevePv")
						.invoke(perso.getItem().getPotion().getClassInstancie());
				enemy.setPdv(enemy.getPdv() + degat);
				utilisePoison++;
				text = "\n Le personnage utilise poison enlève : " + degat
						+ " il reste à l'ennemi : " + enemy.getPdv() + " pv";
				setChanged();
				notifyObservers();

			} else if (perso.getItem().getPotion().getNameItem()
					.contains("auto")
					&& perso.getItem().getPotion().getTypeItem() == "Poison"
					&& utilisePoison < 15) {
				int degat = (int) perso
						.getItem()
						.getPotion()
						.getMethodForName("enlevePv")
						.invoke(perso.getItem().getPotion().getClassInstancie());
				enemy.setPdv(enemy.getPdv() + degat);
				utilisePoison++;
				text = "\n Le personnage utilise poison auto enlève : " + degat
						+ " il reste à l'ennemi : " + enemy.getPdv() + " pv";
				setChanged();
				notifyObservers();
			}
			if (!enemy.isReculer()
					|| perso.getClassPerso().getNameClasse()
							.equalsIgnoreCase("Archer")) {
				enemy.setPdv(enemy.getPdv() - perso.getForceDeFrappe());
				int i = generateRand(0, 1);
				if (i == 1) {
					enemy.setReculer(true);
				} else {
					enemy.setReculer(false);
				}
			} else {
				enemy.setPdv(enemy.getPdv()
						- (int) (perso.getForceDeFrappe() * 0.5));
				text = "\n l'ennemi est loin, vous infligez 50% de dégat en moins";
				int i = generateRand(0, 1);
				if (i == 1) {
					enemy.setReculer(true);
				} else {
					enemy.setReculer(false);
				}
			}
			perso.setPointDeVie(perso.getPointDeVie()
					- pdvEnleverPerso(enemy.getAttaque()));
		}
	}

	/**
	 * Cette méthode définit si le combat se termine et détermine le gagnant
	 * 
	 * @param enemy
	 *            opposé dans le combat
	 * @param perso
	 *            personnage lancé dans le combat
	 * @return un booléen pour connaitre la fin d'un combat
	 */
	private boolean combatFinish(Enemy enemy, Personnage perso) {

		if (enemy.getPdv() > 0 && perso.getPointDeVie() > 0) {

			text += "\n Il reste à l'ennemi " + enemy.getPdv()
					+ " et au perso " + perso.getPointDeVie();
			setChanged();
			notifyObservers();
			return true;
		} else if (enemy.getPdv() <= 0 && perso.getPointDeVie() > 0) {
			text = "\n Il reste à l'ennemi " + 0 + " et au perso "
					+ perso.getPointDeVie();
			text += "\n Le perso " + perso.getNom() + " a gagné";
			setChanged();
			notifyObservers();
			return false;
		} else if (enemy.getPdv() > 0 && perso.getPointDeVie() <= 0) {
			text = "\n Il reste a l'ennemi " + enemy.getPdv() + " et au perso "
					+ 0;
			text += "\n L'ennemi " + enemy.getNom() + " a gagné";
			setChanged();
			notifyObservers();
			return false;
		} else if (enemy.getPdv() == 0 && perso.getPointDeVie() == 0) {
			text = "Match nul, les 2 joueurs sont KO !";
			setChanged();
			notifyObservers();
			return false;
		} else if (enemy.getPdv() <= 0 && perso.getPointDeVie() <= 0) {
			if (enemy.getPdv() < perso.getPointDeVie()) {
				text += "\n Le perso " + perso.getNom() + " a gagné";
			} else {
				text += "\n L'ennemi " + enemy.getNom() + " a gagné";
			}
			setChanged();
			notifyObservers();
			return false;
		}
		System.out.println("Erreur exception");
		return false;
	}

	/**
	 * Méthode qui va modifier la valeur de l'attaque d'un ennemi si la valeur
	 * de déf d'un personnage est supérieur à la valeur d'attaque d'un ennemi.
	 * 
	 * @param attaque
	 * @return la nouvelle valeur
	 */
	private int pdvEnleverPerso(int attaque) {
		if (perso.getDefense() > attaque) {
			text += "\n L'ennemi " + enemy.getNom()
					+ " n'a pas assez de force pour vous battre";
			text += "\n L'ennemi "
					+ enemy.getNom()
					+ " prend une SuperAttaque qui lui fait gagner (+1) en attaque";
			enemy.setAttaque(enemy.getAttaque() + 1);
			return 0;
		} else {
			return attaque - perso.getDefense();
		}
	}

	String getText() {
		return text;
	}

	public void setText(String t) {
		text = t;
	}

	/**
	 * Générateur de valeur aléatoire
	 * 
	 * @param min
	 *            valeur minimum
	 * @param max
	 *            valeur maximum
	 * @return valeur aléatoire entre min et max
	 */
	private int generateRand(int min, int max) {
		Random rand = new Random();
		int nombreAleatoire = rand.nextInt(max - min + 1) + min;
		return nombreAleatoire;
	}

}
