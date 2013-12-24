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

public class GroupClasses extends Observable implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> classes;
	private List listeDesClasses;
	private Shell shell;
	private String valSelection = " ";

	public GroupClasses(Shell fenetre, LinkedList<ChargementDynamique> classes,
			GridData gridData) {

		this.setShell(fenetre);
		this.classes = classes;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesClasses = new List(thisGroup, SWT.SINGLE);

		thisGroup.setText("Choisir une classe de personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

		fillList();
		addListener();

	}

	private void addListener() {
		listeDesClasses.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				// on récup la valeur selectionnée
				valSelection = listeDesClasses.getItem(listeDesClasses
						.getSelectionIndex());

				notifyObservers();
				setChanged();
			}
		});
	}

	private void fillList() {
		for (ChargementDynamique classe : classes) {
			listeDesClasses.add(classe.getNameClasse());
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				listeDesClasses.add(classes.getLast().getNameClasse());
				listeDesClasses.pack();
			}
		});
	}

	public String getValSelection() {
		return valSelection;
	}

	public void setValSelection(String valSelection) {
		this.valSelection = valSelection;

	}

	public Shell getShell() {
		return shell;
	}

	public void setShell(Shell shell) {
		this.shell = shell;
	}

}
