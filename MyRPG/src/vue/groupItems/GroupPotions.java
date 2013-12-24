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

public class GroupPotions extends Observable implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> potions;
	private List listeDesPotions;
	private String valSelection;

	public GroupPotions(Shell fenetre, LinkedList<ChargementDynamique> items,
			GridData gridData) {

		this.potions = items;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesPotions = new List(thisGroup, SWT.SINGLE);

		thisGroup.setText("Choisir trois potions");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		FillList();
		addListener();

	}

	private void addListener() {
		listeDesPotions.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				valSelection = listeDesPotions.getItem(listeDesPotions
						.getSelectionIndex());
				System.out.println(valSelection);
				notifyAll();
				setChanged();
			}
		});
	}

	private void FillList() {
		for (ChargementDynamique potion : potions) {
			if (potion.getTypeItem() == "Potion")
				listeDesPotions.add(potion.getNameItem());
		}
		listeDesPotions.pack();
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				listeDesPotions.add(potions.getLast().getNameClasse());
				thisGroup.pack();
			}
		});
	}

}
