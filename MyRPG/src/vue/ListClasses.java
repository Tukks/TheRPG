package vue;

import java.util.ArrayList;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.List;

import chargementDynamique.ChargementDynamique;

public class ListClasses extends List {

	public ListClasses(Composite arg0, int arg1,
			ArrayList<ChargementDynamique> listeClasses) {
		super(arg0, arg1);

		for (ChargementDynamique classe : listeClasses) {
			this.add(classe.getNameClasse());
		}
	}

}
