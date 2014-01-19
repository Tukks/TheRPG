package battle;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;

public class BattleVue implements Observer {
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);
	private BattleModel model; // en mode PULL
	private Display dis = new Display();
	private Button lancerComb;
	private Text st;
	
	private ProgressBar progressbarEnemy;
	private ProgressBar progressbarPerso;
	private double pdvEnemyMax;
	private double pdvPersoMax;
	private Shell shell;
	Group thisGroup;
	private ChoixStrategie choixStrategie;

	private Composite composite;

	/**
	 * Construction de la vue, avec les progress bars, encarts, boutons
	 * 
	 * @param model
	 */
	public BattleVue(BattleModel model) {
		this.model = model;
		model.addObserver(this);
		pdvEnemyMax = model.getEnemy().getPdv();
		pdvPersoMax = model.getPerso().getPointDeVie();
		shell = new Shell(dis, SWT.CLOSE | SWT.MIN);
		shell.setSize(1024, 700);
		Cursor cursor1 = new Cursor(dis, cursor_Image, 1, 1);
		shell.setCursor(cursor1);

		choixStrategie = new ChoixStrategie(shell, model.getPerso().getItem()
				.getPotion());
		/*
		 * Set des Bars de vie des persos
		 */
		progressbarEnemy = new ProgressBar(shell, SWT.HORIZONTAL | SWT.SMOOTH);
		progressbarPerso = new ProgressBar(shell, SWT.HORIZONTAL | SWT.SMOOTH);
		progressbarEnemy.setSize(new Point(150, 25));
		progressbarEnemy.setLocation(new Point(850, 450));
		progressbarPerso.setSize(new Point(150, 25));
		progressbarPerso.setLocation(new Point(10, 450));
		progressbarPerso.setSelection(100);
		progressbarEnemy.setSelection(100);
		/*
		 * Set du suivi de combat
		 */

		composite = new Composite(shell, SWT.BORDER);
		Color couleur = new Color(shell.getDisplay(),255,255,255);
		composite.setBackground(couleur);
		composite.setSize(1000, 200);
		composite.setLocation(10, 475);
		RowLayout rowlayout = new RowLayout();
		st = new Text(composite, SWT.V_SCROLL  | SWT.BORDER
				| SWT.READ_ONLY);
		composite.setLayout(rowlayout);
		st.setSize(1000,200);
		st.setVisible(true);
		
		
		lancerComb = new Button(shell, SWT.NONE);
		lancerComb.setLocation(new Point(17, 50));
		lancerComb.setSize(new Point(120, 60));
		lancerComb.setFont(new Font(shell.getDisplay(), "Arial", 12,
				SWT.NONE));
		lancerComb.setText("Lancer combat");
		
		// text.insert("creation text field");
		lancerComb.addListener(SWT.Selection, getListener());
		// model.addObserver(this);

		shell.setVisible(true);
		centrerSurEcran(dis, shell);
		shell.open();

		// bg fenetre
		Image bg_Image = new Image(dis, PathManager.bgImg);
		shell.setBackgroundImage(bg_Image);

		while (!shell.isDisposed())
			if (!dis.readAndDispatch())
				dis.sleep();

		dis.dispose();
	}

	/**
	 * Lancement du combat, avec la méthode "combat" du Model
	 */
	private void makeCombat() {
		try {
			lancerComb.setEnabled(false);
			this.model.Combat(choixStrategie.getChoix());
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	private Listener getListener() {
		return new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				makeCombat();

			}
		};
	}

	@Override
	public void update(Observable obs, Object obj) {
		if (!st.isDisposed()) {
			Display.getDefault().syncExec(new Runnable() {
				public void run() {
					st.append(model.getText());
					int percentEnemie = (int) ((model.getEnemy().getPdv() * 100.0f) / pdvEnemyMax);
					int percentPerso = (int) ((model.getPerso().getPointDeVie() * 100.0f) / pdvPersoMax);
					progressbarEnemy.setSelection(percentEnemie);
					progressbarPerso.setSelection(percentPerso);
					st.setTopIndex(st.getLineCount() - 1);
					st.redraw();
					composite.redraw();

					// long time = System.currentTimeMillis() + 1000;
					// while(System.currentTimeMillis() < time) {}
					model.setText("");
					long time = System.currentTimeMillis() + 1000;
					while (System.currentTimeMillis() < time) {
					}
				}
			});
		}

	}

	/**
	 * pour initialiser textfield
	 * 
	 * @param t
	 */
	public void setValue(int t) {
		st.setText("" + t);
	}

	/**
	 * le controleur utilise la vue pour initialiser le model
	 * 
	 * @param m
	 */
	public void addModel(BattleModel m) {
		this.setModel(m);
	}

	public void addControleur(BattleControleur ncontroleur) {
		if (!lancerComb.isDisposed()) {
			lancerComb.addListener(SWT.Selection, ncontroleur);
		}
	}

	/**
	 * Centrage des éléments avec les outils SWT
	 * 
	 * @param display
	 * @param shell
	 */
	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));

	}

	public BattleModel getModel() {
		return model;
	}

	public void setModel(BattleModel model) {
		this.model = model;
	}
}
