package battle;

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;

import objet.Item;
import chargementDynamique.ChargementDynamiqueJar;
import personnage.Personnage;

public class RunMVCBattle {

	public RunMVCBattle(Personnage perso, Enemy enemy) {
		BattleModel model = new BattleModel(perso, enemy);
		// model.lancerCombat();

		BattleVue vue = new BattleVue(model);

		BattleControleur controleur = new BattleControleur();
		controleur.addModel(model);
		controleur.addVue(vue);
		controleur.initModel("texte de base (init) - DEBUT");
		model.addObserver(vue);

		vue.addControleur(controleur);
		vue.addModel(model);
		
	}

	public static class Main {

		public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, MalformedURLException, IllegalArgumentException, InvocationTargetException {
			Enemy e = new Enemy();
			ChargementDynamiqueJar epee = new ChargementDynamiqueJar(
					"./Plugin/Epee.jar");
			ChargementDynamiqueJar bouclier = new ChargementDynamiqueJar(
					"./Plugin/Bouclier.jar");
			epee.ChargermentJar();
			bouclier.ChargermentJar();
			Item item = new Item(epee, bouclier, epee);
			ChargementDynamiqueJar cd = new ChargementDynamiqueJar(
					"./Plugin/Guerrier.jar");
			cd.ChargermentJar();
			Personnage p = Personnage.getInstance();
			p.init(item, cd, "Le gueurier");
			/*BattleModel bm = new BattleModel(p, e);
			bm.Combat();
*/			new RunMVCBattle(p,e);

		}
	}
}
