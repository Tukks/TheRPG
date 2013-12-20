package vue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;
import chargementDynamique.WatchDir;

public class InterfaceRPG implements Observer {

	private static Display display = new Display();;
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);
	private List listeClasses;
	private LinkedList<ChargementDynamique> classes;
	private LinkedList<ChargementDynamique> items;
	private List listeItems;

	public int getSizeListeClasses() {
		return sizeListeClasses;
	}

	public void setSizeListeClasses(int sizeListeClasses) {
		this.sizeListeClasses = sizeListeClasses;
	}

	protected int sizeListeClasses;
	private GroupClasses gc;

	public InterfaceRPG() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {

		Shell fenetre = createFrame();

		GridData gridData = getGridData();

		Path dir = Paths.get("./Plugin");
		WatchDir watchDirectories = new WatchDir(dir, false);
		ListenerChargementDyn l = ListenerChargementDyn.getInstance();
		Thread t = new Thread(watchDirectories);
		t.start();
		classes = l.getPluginClasse();
		// récupération de la taille de la liste
		setSizeListeClasses(classes.size());
		items = l.getPluginItem();

		// 1 > liste des classes
		gc = new GroupClasses(fenetre, classes, gridData);
		l.addObserver(gc);

		// 2 > saisie nom du perso + apperçu perso
		new GroupAppercuPerso(fenetre, gridData);

		// 3 > caractéristiques
		new GroupCaracteristiquesPerso(fenetre, gridData);

		// 4 > items
		new GroupItems(fenetre, items, gridData);

		// 5 > armures
		new GroupArmures(fenetre, items, gridData);

		// 6 > potion
		new GroupPotions(fenetre, items, gridData);

		// FIN GROUPES

		// curseur
		Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
		fenetre.setCursor(cursor1);

		// bg fenetre
		Image bg_Image = new Image(display, PathManager.bgImg);
		fenetre.setBackgroundImage(bg_Image);

		// bouton lancer jeu
		Button button = new Button(fenetre, SWT.NONE);
		button.setText("Lancer");
		button.addListener(SWT.Selection, getListener());

		centrerSurEcran(display, fenetre);
		fenetre.open();

		while (!fenetre.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
	}

	private Listener getListener() {
		return new Listener() {
			@Override
			public void handleEvent(Event arg0) {

				System.out.println("classes selectionnee : "
						+ gc.getValSelection());
			}

		};
	}

	private GridData getGridData() {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.widthHint = 200;
		return gridData;
	}

	private Shell createFrame() {
		Shell fenetre = new Shell(display, SWT.CLOSE | SWT.MIN);
		fenetre.setSize(1024, 700);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 20;
		layout.marginTop = 20;
		layout.verticalSpacing = 25;
		layout.horizontalSpacing = 25;
		layout.makeColumnsEqualWidth = true;

		fenetre.setLayout(layout);
		return fenetre;
	}

	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		InterfaceRPG rpg = new InterfaceRPG();

	}

	@Override
	public void update(Observable arg0, Object arg1) {

		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if (classes.size() != sizeListeClasses)
					listeClasses.add(classes.getLast().getNameClasse());
				else
					listeItems.add(items.getLast().getNameItem());
			}
		});

	}

}