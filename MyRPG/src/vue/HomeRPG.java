package vue;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;
import vue.group.GroupApercuPerso;
import vue.group.GroupArmes;
import vue.group.GroupArmures;
import vue.group.GroupCaracteristiquesPerso;
import vue.group.GroupClasses;
import vue.group.GroupPotions;
import chargementDynamique.ChargementDynamique;
import chargementDynamique.ListenerChargementDyn;
import chargementDynamique.WatchDir;

public class HomeRPG {

	private static Display display = new Display();
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);

	private GroupClasses gc;

	public HomeRPG() throws InstantiationException, IllegalAccessException,
			ClassNotFoundException, IOException {

		Shell fenetre = new Shell(display, SWT.CLOSE | SWT.MIN);
		fenetre.setSize(1024, 700);
			
		// curseur
		Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
		fenetre.setCursor(cursor1);

		// bg fenetre
		Image bg_Image = new Image(display, PathManager.bgImg);
		fenetre.setBackgroundImage(bg_Image);

		// bouton
		Button buttonCreation = new Button(fenetre, SWT.PUSH);
		buttonCreation.setText("Cr�er un nouveau personnage");
		buttonCreation.setSize(new Point(400, 45));
		buttonCreation.setLocation(new Point(315,250));
		buttonCreation.setFont(new Font(display,"Arial",14,SWT.NONE));
		buttonCreation.addListener(SWT.Selection, getListenerCreation());		
		
		Button buttonCharger = new Button(fenetre, SWT.PUSH);
		buttonCharger.setText("Charger un personnage");
		buttonCharger.setSize(new Point(400, 45));		
		buttonCharger.setLocation(new Point(315,300));
		buttonCharger.setFont(new Font(display,"Arial",14,SWT.NONE));
		
		Button buttonSupprimer = new Button(fenetre, SWT.PUSH);
		buttonSupprimer.setText("Supprimer un personnage");
		buttonSupprimer.setSize(new Point(400, 45));		
		buttonSupprimer.setLocation(new Point(315,350));
		buttonSupprimer.setFont(new Font(display,"Arial",14,SWT.NONE));
		
		centrerSurEcran(display, fenetre);
		fenetre.open();

		while (!fenetre.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
	}

	private Listener getListenerCreation() {
		return new Listener() {
			@Override
			public void handleEvent(Event arg0) {

				System.out.println("Coucou");
			}

		};
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
		HomeRPG rpg = new HomeRPG();

	}

}