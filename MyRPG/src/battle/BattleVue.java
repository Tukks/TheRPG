package battle;

import java.awt.TextArea;
import java.io.Console;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ThreadLocalRandom;

import objet.Item;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.internal.ole.win32.DVTARGETDEVICE;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import personnage.Personnage;
import util.PathManager;

public class BattleVue implements Observer {
	private ImageData cursor_Image = new ImageData(PathManager.cursorImg);

	private BattleModel model; // en mode PULL
	private Display dis = new Display();
	private Button lancerComb;
	private Text text;
	StyledText st;
	ProgressBar progressbarEnemy;
	ProgressBar progressbarPerso;
	double pdvEnemyMax;
	double pdvPersoMax;
	Shell shell;
	Group thisGroup;
	ChoixStrategie choixStrategie;
	
	public BattleVue(BattleModel model) {
		this.model = model;
		model.addObserver(this);
		pdvEnemyMax = model.getEnemy().getPdv();
		pdvPersoMax = model.getPerso().getPointDeVie();
		shell = new Shell(dis, SWT.CLOSE | SWT.MIN);
		shell.setSize(1024, 700);
		Cursor cursor1 = new Cursor(dis, cursor_Image, 1, 1);
		shell.setCursor(cursor1);
		
		choixStrategie = new ChoixStrategie(shell, model.getPerso().getItem().getPotion());
		/*
		 * Set des Bar de vie des perso
		 */
		progressbarEnemy = new ProgressBar(shell, SWT.HORIZONTAL
				| SWT.SMOOTH);
		progressbarPerso = new ProgressBar(shell, SWT.HORIZONTAL
				| SWT.SMOOTH);
		
		progressbarEnemy.setSize(new Point(150, 25));
		progressbarEnemy.setLocation(new Point(850, 450));
		progressbarPerso.setSize(new Point(150, 25));
		progressbarPerso.setLocation(new Point(10, 450));
		progressbarPerso.setSelection(100);
		progressbarEnemy.setSelection(100);
		/*
		 * set du suivi de combat
		 */
		st = new StyledText(shell, SWT.V_SCROLL | SWT.WRAP | SWT.BORDER | SWT.READ_ONLY);
		st.setSize(new Point(1000, 200));
		st.setLocation(new Point(10,475 ));
		st.setVisible(true);
		lancerComb = new Button(shell, SWT.NONE);
		lancerComb.setLocation(new Point(10,0));
		lancerComb.setSize(new Point(100,20));
		lancerComb.setText("Lancer combat");
		text = new Text(shell, SWT.SINGLE);
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
		void makeCombat() {
		try {

			this.model.Combat(choixStrategie.getChoix().getSelectionIndex()+1);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Listener getListener() {
		return new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				// TODO Auto-generated method stub
				System.out.println("hey");
				makeCombat();
			}
		};

	}
	
	@Override
	public void update(Observable obs, Object obj) {
		if(!st.isDisposed()){
		    		
		    		st.append(model.getText());
					int percentEnemie = (int)((model.getEnemy().getPdv() * 100.0f) /pdvEnemyMax);
					int percentPerso = (int)((model.getPerso().getPointDeVie() * 100.0f) /pdvPersoMax);

					progressbarEnemy.setSelection(percentEnemie) ;
					progressbarPerso.setSelection(percentPerso) ;
					st.setTopIndex(st.getLineCount() - 1);
					st.redraw();
					long time = System.currentTimeMillis() + 1000; 
					while(System.currentTimeMillis() < time) {}	
					model.setText("");
		        // do any work that updates the screen
		        // e.g., call browser.setURL(...);
		    
		
		}
    
}
	
// pour initialiser textfield
	public void setValue(int t) {
		text.setText("" + t);
	}

	// le controleur utilise la vue pour initialiser le model
	public void addModel(BattleModel m) {
		System.out.println("View      : adding model");
		this.setModel(m);
	}

	public void addControleur(BattleControleur ncontroleur) {
		System.out.println("View      : adding controller");
		if (!lancerComb.isDisposed()) {
			lancerComb.addListener(SWT.Selection, ncontroleur);
		}
	}

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

	// public static void main(String args[]) {
	// new BattleVue();
	// }
}
