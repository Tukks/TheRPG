package vue;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;

import javax.imageio.ImageIO;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

import battle.BattleControleur;
import battle.BattleModel;
import battle.BattleVue;
import battle.Enemy;
import personnage.Personnage;
import serializable.Serialize;
import util.PathManager;

public class HomeRPG {

	private static Display display = new Display();// new Display();
	
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);
	private Shell fenetre;

	public HomeRPG() {
		
		
		
		fenetre = new Shell(display, SWT.CLOSE | SWT.MIN);
		fenetre.setSize(1024, 700);

		// curseur
		Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
		fenetre.setCursor(cursor1);

		// bg fenetre
		Image bg_Image = new Image(display, PathManager.bgImg);
		fenetre.setBackgroundImage(bg_Image);

		// bouton
		Button buttonCreation = new Button(fenetre, SWT.PUSH);
		buttonCreation.setText("Créer un nouveau personnage");
		buttonCreation.setSize(new Point(400, 45));
		buttonCreation.setLocation(new Point(315, 250));
		buttonCreation.setFont(new Font(display, "Arial", 14, SWT.NONE));
		buttonCreation.addListener(SWT.Selection, listenCreation);

		Button buttonCharger = new Button(fenetre, SWT.PUSH);
		buttonCharger.setText("Charger un personnage");
		buttonCharger.setSize(new Point(400, 45));
		buttonCharger.setLocation(new Point(315, 300));
		buttonCharger.setFont(new Font(display, "Arial", 14, SWT.NONE));
		buttonCharger.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {

				FileDialog charge = new FileDialog(fenetre);

				charge.setText("Chargement du perso");
				charge.setFilterPath("../MyRpg");
				String[] filterExt = { "*.ser", "*.*" };
				charge.setFilterExtensions(filterExt);
				String select = charge.open();
				if (select != null) {
					try {
						// mettre un explorateur de fichier pour choisir la
						// sauvegarde a charger
						FileInputStream fichierIN = new FileInputStream(select);
						Serialize load = new Serialize(fichierIN);
						Serialize enemy = new Serialize();
						Personnage p1 = load.devisitePersonnage();
						
						Enemy e = enemy.devisiteEnemy();
						// System.out.println(p1.getDefense());
						// Enemy e = new Enemy();
						if (p1 != null) {
							display.close();
							
							BattleModel model = new BattleModel(p1, e);
							BattleVue vue = new BattleVue(model);

							BattleControleur controleur = new BattleControleur();

							controleur.addModel(model);
							controleur.addVue(vue);
							controleur.initModel("");
							vue.addControleur(controleur);
							
					} else {
							MessageBox b  = new MessageBox(fenetre, SWT.OK );
							b.setMessage("il manque des plugins pour le bon déroulement du chargement");
							b.open();
						}
					} catch (InstantiationException | IllegalAccessException
							| IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		});

		centrerSurEcran(display, fenetre);
		fenetre.open();

		while (!fenetre.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
	}

	Listener listenCreation = new Listener() {

		@Override
		public void handleEvent(Event arg0) {

			try {
				fenetre.close();
				display.close();
				
				new InterfaceRPG();
			} catch (InstantiationException | IllegalAccessException
					| ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	};

	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}

}