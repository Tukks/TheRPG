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

import annot.Classe;
import annot.Item;
import annot.TypeItem;
import util.PathManager;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;

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
		// FillList();
		addListener();
		listeDesArmes.setEnabled(false);
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
	public void update(final Observable arg0, final Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (arg0 instanceof ListenerChargementDyn) {
					listeDesArmes.add(items.getLast().getNameClasse());
					listeDesArmes.pack();

				} else if (arg0 instanceof GroupClasses) {
					listeDesArmes.setEnabled(true);
					listeDesArmes.removeAll();
					ChargementDynamique cd = (ChargementDynamique) arg1;
					Classe Classe = (Classe) cd.getClassCharged()
							.getAnnotations()[0];
					String ClassString = Classe.nom();

					for (ChargementDynamique arme : items) {
						Item item = (Item) arme.getClassCharged()
								.getAnnotations()[0];
						String[] itemFor = item.classe().split(",");

						if (item.classe().equalsIgnoreCase("all")
								&& item.type() == TypeItem.Arme) {
							listeDesArmes.add(arme.getNameItem());

						}
						for (int i = 0; i < itemFor.length; i++) {
							if (itemFor[i].equalsIgnoreCase(ClassString) && item.type() == TypeItem.Arme) {
								
								listeDesArmes.add(arme.getNameItem());
							}
						}

					}

					listeDesArmes.pack();
				}

			}
		});

	}

	public LinkedList<ChargementDynamique> getItems() {
		return items;
	}

	public void setItems(LinkedList<ChargementDynamique> items) {
		this.items = items;
	}

}
