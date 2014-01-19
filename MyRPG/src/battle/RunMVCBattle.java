package battle;

import personnage.Personnage;

public class RunMVCBattle {

	public RunMVCBattle(Personnage perso, Enemy enemy) {
		BattleModel model = new BattleModel(perso, enemy);

		BattleVue vue = new BattleVue(model);

		BattleControleur controleur = new BattleControleur();
		controleur.addModel(model);
		controleur.addVue(vue);
		controleur.initModel("texte de base (init) - DEBUT");
		model.addObserver(vue);

		vue.addControleur(controleur);
		vue.addModel(model);

	}

}
