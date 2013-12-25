package vue.groupItems;

import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;
import chargementDynamique.ChargementDynamique;

public class GroupArmures extends Observable implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> armures;
	private List listeDesArmures;
	private String valSelection = "";

	public GroupArmures(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.armures = items;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesArmures = new List(thisGroup, SWT.MULTI);

		thisGroup.setText("Choisir une armure");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();
		addListener();

		if (listeDesArmures.getItemCount() == 0)
			listeDesArmures.setEnabled(false);

	}

	private void addListener() {
		listeDesArmures.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseUp(MouseEvent arg0) {
				super.mouseUp(arg0);
				notifyObservers();
				setChanged();
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				valSelection = listeDesArmures.getItem(listeDesArmures
						.getSelectionIndex());
				notifyObservers();
				setChanged();
			}
		});
	}

	private void FillList() {
		for (ChargementDynamique armure : armures) {
			if (armure.getTypeItem() == "Armure")
				listeDesArmures.add(armure.getNameItem());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				listeDesArmures.add(armures.getLast().getNameClasse());
				listeDesArmures.pack();
			}
		});
	}

	public String getValSelection() {
		return valSelection;
	}

}
