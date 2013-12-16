package vue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;
import chargementDynamique.WatchDir;

public class InterfaceRPG implements Observer {

	private static Display display = new Display();;
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);
	private Font font = new Font(display, "Arial", 15, SWT.BOLD);
	private List listeClasses;

	public InterfaceRPG() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {

		Shell fenetre = createFrame();

		GridData gridData = getGridData();

		Path dir = Paths.get("./Plugin");
		WatchDir watchDirectories = new WatchDir(dir, false);
		ListenerChargementDyn l = ListenerChargementDyn.getInstance();
		Thread t = new Thread(watchDirectories);
		t.start();

		l.addObserver(this);
		// 1 > liste des classes
		createListClasses(fenetre, l, gridData);

		// 2 > saisie nom du perso + apperçu perso
		createGroupPerso(fenetre, gridData);

		// 3 > caractéristiques
		createGroupCarac(fenetre, gridData);

		// 4 > armes
		createGroupArms(fenetre, gridData);

		// 5 > armures
		createGroupArmures(fenetre, gridData);

		// 6 > items
		createGroupItems(fenetre, l, gridData);

		// FIN GROUPES

		// curseur
		// Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
		// fenetre.setCursor(cursor1);

		// bg fenetre
		Image bg_Image = new Image(display, PathManager.bgImg);
		fenetre.setBackgroundImage(bg_Image);

		centrerSurEcran(display, fenetre);
		fenetre.open();

		while (!fenetre.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
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

	private void createGroupItems(Shell fenetre, ListenerChargementDyn l,
			GridData gridData) {
		LinkedList<ChargementDynamique> items = l.getPluginItem();
		GroupDeco gd = new GroupDeco(fenetre, "Choisissez 3 items", 2);
		Group groupItem = gd.getG();

		for (ChargementDynamique i : items) {

			// new Button(groupItem, SWT.PUSH).setText(i.);
		}

		groupItem.setLayoutData(gridData);
	}

	private void createGroupArmures(Shell fenetre, GridData gridData) {
		GroupDeco gd = new GroupDeco(fenetre, "Choisissez une armure", 2);
		Group groupArmures = gd.getG();
		groupArmures.setSize(400, 400);
		groupArmures.setLayoutData(gridData);
	}

	private void createGroupArms(Shell fenetre, GridData gridData) {
		GroupDeco gd = new GroupDeco(fenetre, "Choisissez une arme", 2);
		Group groupArmes = gd.getG();

		for (int i = 0; i < 4; i++) {

			new Button(groupArmes, SWT.PUSH).setImage(new Image(display,
					PathManager.itemImg));
		}

		groupArmes.setLayoutData(gridData);
	}

	private void createGroupCarac(Shell fenetre, GridData gridData) {
		GroupDeco gd = new GroupDeco(fenetre, "Caractéristiques", 2);
		Group groupCarac = gd.getG();

		groupCarac.setLayoutData(gridData);
	}

	private Group createGroupPerso(Shell fenetre, GridData gridData) {
		GroupDeco gd = new GroupDeco(fenetre, "", 1);
		Group groupPerso = gd.getG();

		Label label = new Label(groupPerso, SWT.NONE);
		label.setText("Saisir un nom de personnage");
		label.setBounds(10, 10, 100, 25);

		Text text = new Text(groupPerso, SWT.BORDER_DOT);
		text.setSize(200, 10);

		Composite appercuPerso = new Composite(groupPerso, SWT.BORDER);
		appercuPerso.setSize(400, 400);
		appercuPerso
				.setBackgroundImage(new Image(display, PathManager.classImg));

		groupPerso.setLayoutData(gridData);
		return groupPerso;
	}

	private void createListClasses(Shell fenetre, ListenerChargementDyn l,
			GridData gridData) {
		LinkedList<ChargementDynamique> classes = l.getPluginClasse();

		GroupDeco gd = new GroupDeco(fenetre,
				"Choisissez une classe de personnage", 1);
		Group groupeClasses = gd.getG();

		listeClasses = new List(groupeClasses, SWT.V_SCROLL | SWT.MULTI);

		for (ChargementDynamique classe : classes) {
			listeClasses.add(classe.getNameClasse());
		}
		groupeClasses.setLayoutData(gridData);
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
		InterfaceRPG rpg =  new InterfaceRPG();
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		
		System.out.println("coucou");
		/*listeClasses.add("coucou");
		listeClasses.redraw();
		listeClasses.update();*/
	}

}