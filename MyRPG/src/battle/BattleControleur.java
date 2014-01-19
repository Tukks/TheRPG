package battle;

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class BattleControleur implements Listener {

	private BattleModel model;
	BattleVue vue;

	public BattleControleur() {
	}

	@Override
	public void handleEvent(Event e) {
		model.lancerCombat();
	}

	/**
	 * @param bm
	 *            Battle Model
	 */
	public void addModel(BattleModel bm) {
		this.model = bm;
	}

	/**
	 * @param bv
	 *            Battle Vue
	 */
	public void addVue(BattleVue bv) {
		this.vue = bv;
	}

	public void initModel(String text) {
		model.setVal(text);
	}
}
