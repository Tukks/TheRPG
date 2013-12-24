package battle;

public class RunMVCBattle {

	private String start_value = "DEBUT";

	public RunMVCBattle() {
		BattleModel model = new BattleModel();
		BattleVue vue = new BattleVue();

		model.addObserver(vue);
		// model.lancerCombat();

		model.setVal(start_value);

		BattleControleur controleur = new BattleControleur();
		controleur.addModel(model);
		controleur.addVue(vue);
		controleur.initModel("texte de base (init) - DEBUT");

		vue.addControleur(controleur);
		vue.addModel(model);
	}

	public static class Main {

		public static void main(String[] args) {

			new RunMVCBattle();

		}
	}
}
