package vue.group;

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

public class GroupArmures {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> armures;
	private List listeDesItems;

	public GroupArmures(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.armures = items;
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
		for (ChargementDynamique armure : armures) {
			if (armure.getTypeItem() == "Armure")
				listeDesItems.add(armure.getNameItem());
		}
	}

}
