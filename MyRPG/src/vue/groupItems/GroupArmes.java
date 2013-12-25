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

public class GroupArmes extends Observable implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> items;
	private List listeDesArmes;
	private String valSelection = "";

	public GroupArmes(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.items = items;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesArmes = new List(thisGroup, SWT.SINGLE);

		thisGroup.setText("Choisir une arme");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();
		addListener();

		if (listeDesArmes.getItemCount() == 0)
			listeDesArmes.setEnabled(false);

	}

	public String getValSelection() {
		return valSelection;
	}

	private void addListener() {
		listeDesArmes.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				valSelection = listeDesArmes.getItem(listeDesArmes
						.getSelectionIndex());

				notifyObservers();
				setChanged();
			}
		});
	}

	private void FillList() {
		for (ChargementDynamique arme : items) {
			if (arme.getTypeItem() == "Arme")
				listeDesArmes.add(arme.getNameItem());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				listeDesArmes.add(items.getLast().getNameClasse());
				listeDesArmes.pack();
			}
		});
	}

}
