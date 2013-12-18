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

public class GroupClasses {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> classes;
	private List listeDesClasses;

	public GroupClasses(Shell fenetre,
			LinkedList<ChargementDynamique> classes, GridData gridData) {

		this.classes = classes;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesClasses = new List(thisGroup, SWT.MULTI);

		thisGroup.setText("Choisir une classe de personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();

	}

	private void FillList() {
		for (ChargementDynamique classe : classes) {
			listeDesClasses.add(classe.getNameClasse());
		}
	}

}
