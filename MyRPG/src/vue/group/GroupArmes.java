package vue.group;

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

public class GroupArmes implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> armes;
	private List listeDesItems;
	protected Object valSelection;

	public GroupArmes(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.armes = items;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesItems = new List(thisGroup, SWT.MULTI);

		thisGroup.setText("Choisir une arme");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();

	}

	private void addListener() {
		listeDesItems.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				valSelection = listeDesItems.getItem(listeDesItems
						.getSelectionIndex());
				System.out.println(valSelection);
			}
		});
	}

	private void FillList() {
		for (ChargementDynamique arme : armes) {
			if (arme.getTypeItem() == "Arme")
				listeDesItems.add(arme.getNameItem());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				listeDesItems.add(armes.getLast().getNameClasse());
				// pb pour raffraichir la liste
				// listeDesClasses.update();
				// thisGroup.update();
				// shell.pack(true);
				// shell.layout();
			}
		});
	}

}
