package vue;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;
import chargementDynamique.ChargementDynamique;

public class GroupPotions {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> items;
	private List listeDesItems;

	public GroupPotions(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.items = items;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesItems = new List(thisGroup, SWT.MULTI);

		thisGroup.setText("Choisir une armure");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();

	}

	private void FillList() {
		for (ChargementDynamique item : items) {
			listeDesItems.add(item.getNameItem());
		}
	}

}
