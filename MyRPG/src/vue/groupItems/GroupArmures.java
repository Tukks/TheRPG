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

	@Override
	public void update(final Observable arg0, final Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				
				if (arg0 instanceof ListenerChargementDyn) {
					listeDesArmures.add(armures.getLast().getNameClasse());
					listeDesArmures.pack();

				} else if (arg0 instanceof GroupClasses) {
					listeDesArmures.setEnabled(true);
					listeDesArmures.removeAll();
					ChargementDynamique cd = (ChargementDynamique) arg1;
					Classe Classe = (Classe) cd.getClassCharged()
							.getAnnotations()[0];
					String ClassString = Classe.nom();

					for (ChargementDynamique arme : armures) {
						Item item = (Item) arme.getClassCharged()
								.getAnnotations()[0];
						String[] itemFor = item.classe().split(",");

						if (item.classe().equalsIgnoreCase("all")
								&& item.type() == TypeItem.Armure) {
							listeDesArmures.add(arme.getNameItem());

						}
						for (int i = 0; i < itemFor.length; i++) {
							if (itemFor[i].equalsIgnoreCase(ClassString) && item.type() == TypeItem.Armure) {
								
								listeDesArmures.add(arme.getNameItem());
							}
						}

					}

					listeDesArmures.pack();
				}

			}
		});
	}

	public LinkedList<ChargementDynamique> getArmures() {
		return armures;
	}

	public void setArmures(LinkedList<ChargementDynamique> armures) {
		this.armures = armures;
	}

	public String getValSelection() {
		return valSelection;
	}

}
