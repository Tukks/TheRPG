package vue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
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

public class InterfaceRPG {

	private static Display display = new Display();;
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);

	public InterfaceRPG() {

		Shell fenetre = new Shell(display, SWT.CLOSE | SWT.MIN);
		fenetre.setSize(1024, 700);

		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		layout.marginWidth = 20;
		layout.marginTop = 20;

		fenetre.setLayout(layout);

		Font font = new Font(display, "Arial", 20, SWT.BOLD);

		// 1 > liste des classes

		List listeClasses = new List(fenetre, SWT.V_SCROLL | SWT.MULTI);
		listeClasses.setFont(font);
		for (int i = 0; i < 10; i++) {
			listeClasses.add("element " + i);
		}

		// 2 > saisie nom du perso + apperçu perso

		Group groupPerso = new Group(fenetre, SWT.SHADOW_IN);
		groupPerso.setSize(500, 500);
		GridLayout lPerso = new GridLayout();
		groupPerso.setLayout(lPerso);

		Label label = new Label(groupPerso, SWT.NONE);
		label.setText("Saisir un nom de personnage");
		label.setBounds(10, 10, 100, 25);

		Text text = new Text(groupPerso, SWT.BORDER_DOT);
		text.setSize(200, 10);

		Composite appercuPerso = new Composite(groupPerso, SWT.BORDER);
		appercuPerso.setSize(400, 400);
		appercuPerso
				.setBackgroundImage(new Image(display, PathManager.classImg));
		// 3 > caractéristiques

		Composite carac = new Composite(fenetre, SWT.BORDER_DOT);
		carac.setSize(400, 400);

		// 4 > armes

		GroupDeco gd = new GroupDeco(fenetre, "Choisissez une arme", new Image(
				display, PathManager.bgGroup), 2);
		Group groupArmes = gd.getG();

		for (int i = 0; i < 4; i++) {

			new Button(groupArmes, SWT.FLAT).setImage(new Image(display,
					PathManager.itemImg));
		}

		// 5 > armures

		gd = new GroupDeco(fenetre, "Choisissez une armure", null, 2);
		Group groupArmures = gd.getG();
		groupArmures.setSize(400, 400);

		// 6 > items

		gd = new GroupDeco(fenetre, "Choisissez 3 items", null, 2);
		Group groupItem = gd.getG();
		groupArmures.setSize(400, 400);

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

	public static void main(String[] args) {
		new InterfaceRPG();
	}

}