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
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import annot.Classe;
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
	private boolean isNew = true;
	public GroupClasses(Shell fenetre, LinkedList<ChargementDynamique> classes,
			GridData gridData, ListenerChargementDyn listenerCD) {

		this.setShell(fenetre);
		this.classes = classes;
		lcd = listenerCD;
		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		listeDesClasses = new List(thisGroup, SWT.BORDER);

		// Color blue = shell.getDisplay().getSystemColor(SWT.T);
		// listeDesClasses.setBackground(blue);

		photoPerso = new Canvas(thisGroup, SWT.BORDER);
		photoPerso.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				if (persoImage != null) {
					GC gc = event.gc;
					gc.drawImage(persoImage, 0, 0);
					gc.dispose();
				}
			}
		});

		photoPerso.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

		thisGroup.setText("Choisir une classe de personnage");
		thisGroup.setFont(new Font(fenetre.getDisplay(), "Arial", 12, SWT.BOLD));
		FillLayout fl = new FillLayout(SWT.VERTICAL);
		fl.marginHeight = 10;
		fl.marginWidth = 55;
		fl.spacing = 5;

		thisGroup.setLayout(fl);
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		addListener();
		
		fillList();
		listeDesClasses.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		listeDesClasses.setFont(new Font(shell.getDisplay(), "Arial", 12,
				SWT.NONE));
		
			listeDesClasses.setEnabled(true);
	}

	private void addListener() {
		listeDesClasses.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDown(MouseEvent arg0) {
				super.mouseDown(arg0);
				// on récup la valeur selectionnée
				valSelection = listeDesClasses.getItem(listeDesClasses
						.getSelectionIndex());

				notifyObservers(lcd.getClassForNamePluginClasse(valSelection));

				// notifyObservers(carac);
				setChanged();

				try {
					addPhotoPerso();
					photoPerso.redraw();
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
	public void update(final Observable arg0, Object arg1) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				if(arg0 instanceof ListenerChargementDyn){
					/*Classe classe = null;
					if(classes.size() != 0){
					classe = (Classe) classes.getLast().getClassCharged().getAnnotations()[0];
					}
					if(isNew != true){		
						String [] items = listeDesClasses.getItems();
					if(!classes.getLast().getNameClasse().equalsIgnoreCase(items[items.length-1])){
					
					
					
					}else if(classe != null){
						isNew = false;
						listeDesClasses.add(classes.getLast().getNameClasse());
						listeDesClasses.pack();
					}*/
					if(classes.size() != 0){
						listeDesClasses.add(classes.getLast().getNameClasse());
						listeDesClasses.pack();
						String [] items = listeDesClasses.getItems();
						if(items[items.length-1].equalsIgnoreCase(listeDesClasses.getItem(items.length-2)) ){
							listeDesClasses.remove(items[items.length-1]);
						}
					}
					//}
				}
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
