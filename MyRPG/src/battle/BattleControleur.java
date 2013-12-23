package battle;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class BattleControleur implements Listener {

	BattleModel model;
	BattleVue vue;

	public BattleControleur() {
		System.out.println("Controleur()");
	}

	@Override
	public void handleEvent(Event e) {
		model.lancerCombat();
		System.out.println("Controller: acting on Model");
	}

	public void addModel(BattleModel bm) {
		this.model = bm;
	}

	public void addVue(BattleVue bv) {
		this.vue = bv;
	}

	public void initModel(String text) {
		model.setVal(text);
	}
}
