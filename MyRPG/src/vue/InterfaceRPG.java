package vue;

import java.io.IOException;
import java.util.ArrayList;

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
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;

public class InterfaceRPG {

	private static Display display = new Display();;
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);

	public InterfaceRPG() throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {

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

		Font font = new Font(display, "Arial", 15, SWT.BOLD);

		// 1 > liste des classes
		// ---------------------------------------------------------------------------------------

		ListenerChargementDyn l = new ListenerChargementDyn("./Plugin");
		Thread t = new Thread(l);
		t.start();
		ArrayList<ChargementDynamique> listeClasses = l.getPluginClasse();

		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.widthHint = 200;

		GroupDeco gd = new GroupDeco(fenetre,
				"Choisissez une classe de personnage", 1);
		Group groupeClasses = gd.getG();

		// List listeClasses = new List(groupeClasses, SWT.V_SCROLL |
		// SWT.MULTI);

		// listeClasses.setLayoutData(gridData);

		// 2 > saisie nom du perso + apperçu perso
		// ---------------------------------------------------------------------------------------

		gd = new GroupDeco(fenetre, "", 1);
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

		// 3 > caractéristiques
		// ---------------------------------------------------------------------------------------

		gd = new GroupDeco(fenetre, "Caractéristiques", 2);
		Group groupCarac = gd.getG();

		groupPerso.setLayoutData(gridData);

		// 4 > armes
		// ---------------------------------------------------------------------------------------

		gd = new GroupDeco(fenetre, "Choisissez une arme", 2);
		Group groupArmes = gd.getG();

		for (int i = 0; i < 4; i++) {

			new Button(groupArmes, SWT.PUSH).setImage(new Image(display,
					PathManager.itemImg));
		}

		groupArmes.setLayoutData(gridData);

		// 5 > armures
		// ---------------------------------------------------------------------------------------

		gd = new GroupDeco(fenetre, "Choisissez une armure", 2);
		Group groupArmures = gd.getG();
		groupArmures.setSize(400, 400);

		// 6 > items
		// ---------------------------------------------------------------------------------------

		ArrayList<ChargementDynamique> items = l.getPluginItem();
		gd = new GroupDeco(fenetre, "Choisissez 3 items", 2);
		Group groupItem = gd.getG();

		for (ChargementDynamique i : items) {

			// new Button(groupItem, SWT.PUSH).setText(i.);
		}

		// ---------------------------------------------------------------------------------------
		// FIN GROUPES
		// ---------------------------------------------------------------------------------------

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

	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		new InterfaceRPG();
	}

}