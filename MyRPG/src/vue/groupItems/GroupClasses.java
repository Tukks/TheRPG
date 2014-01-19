package vue.groupItems;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;

public class GroupClasses extends Observable implements Observer {

	private Group thisGroup;
	private LinkedList<ChargementDynamique> classes;
	private List listeDesClasses;
	private Shell shell;
	private String valSelection = "";
	private ListenerChargementDyn lcd;
	private Canvas photoPerso;
	protected Image persoImage;

	public GroupClasses(Shell fenetre, LinkedList<ChargementDynamique> classes,
			GridData gridData, ListenerChargementDyn listenerCD) {

		this.setShell(fenetre);
		this.classes = classes;
		lcd = listenerCD;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesClasses = new List(thisGroup, SWT.SINGLE);
		photoPerso = new Canvas(thisGroup, SWT.BORDER);
		photoPerso.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				if (persoImage != null) {
					event.gc.drawImage(persoImage, 0, 0);
				}
			}
		});

		thisGroup.setText("Choisir une classe de personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		addListener();

		fillList();

		if (listeDesClasses.getItemCount() == 0)
			listeDesClasses.setEnabled(false);

	}

	private void addListener() {
		listeDesClasses.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				// on récup la valeur selectionnée
				valSelection = listeDesClasses.getItem(listeDesClasses
						.getSelectionIndex());

				int[] carac = new int[3];

				try {
					carac[0] = (int) lcd
							.getClassForNamePluginClasse(valSelection)
							.getMethodForName("getPdv")
							.invoke(lcd.getClassForNamePluginClasse(
									valSelection).getClassInstancie());

					carac[1] = (int) lcd
							.getClassForNamePluginClasse(valSelection)
							.getMethodForName("getDefense")
							.invoke(lcd.getClassForNamePluginClasse(
									valSelection).getClassInstancie());

					carac[2] = (int) lcd
							.getClassForNamePluginClasse(valSelection)
							.getMethodForName("getForce")
							.invoke(lcd.getClassForNamePluginClasse(
									valSelection).getClassInstancie());

				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				notifyObservers(carac);
				setChanged();

				try {
					addPhotoPerso();
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	protected void addPhotoPerso() throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		String fileName = lcd.getClassForNamePluginClasse(valSelection)
				.getIcoClasse();
		persoImage = new Image(shell.getDisplay(), fileName);

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
