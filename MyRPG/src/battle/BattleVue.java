package battle;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;

public class BattleVue implements Observer {

	private BattleModel model; // en mode PULL
	private Display dis = new Display();
	private Button lancerComb;
	private Text text;

	public BattleVue() {
		System.out.println("Vue()");
		Shell shell = createFrame();
		lancerComb = new Button(shell, SWT.NONE);
		lancerComb.setText("Lancer combat");
		// lancerComb.addSelectionListener(new SelectionListener() {
		//
		// @Override
		// public void widgetSelected(SelectionEvent arg0) {
		// System.out.println("ok list");
		//
		// }
		//
		// @Override
		// public void widgetDefaultSelected(SelectionEvent arg0) {
		// // TODO Auto-generated method stub
		//
		// }
		// });

		text = new Text(shell, SWT.SINGLE);
		// text.insert("creation text field");

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

	@Override
	public void update(Observable obs, Object obj) {
		
		System.out.println("View      : Observable " + obs.getClass()
				+ ", object " /* + obj.getClass() */);
		if(!text.isDisposed()){
		text.setText(/* model.getVal() + */"ok new text par update");
		}}

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
		if(!lancerComb.isDisposed()){
			lancerComb.addListener(SWT.Selection, ncontroleur);
		}
	}

	private Shell createFrame() {
		Shell fenetre = new Shell(dis, SWT.CLOSE | SWT.MIN);
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
