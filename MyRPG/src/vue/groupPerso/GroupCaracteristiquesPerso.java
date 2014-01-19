package vue.groupPerso;

import java.lang.reflect.InvocationTargetException;
import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import personnage.Personnage;
import util.PathManager;
import vue.groupItems.GroupArmes;
import vue.groupItems.GroupArmures;
import vue.groupItems.GroupClasses;
import chargementDynamique.ChargementDynamique;

public class GroupCaracteristiquesPerso implements Observer {

	private Group thisGroup;

	private Label lClasse;
	private Label lArmure;
	private Label lArme;
	private GroupArmes gArmes;
	private GroupArmures gArmures;
	private GroupClasses gClasses;

	private Label force;

	private Label pdv;

	private Label def;

	private Text nomPerso;
	int pv = 0;
	int d = 0;
	int f = 0;

	public GroupCaracteristiquesPerso(Shell fenetre, GridData gridData,
			Personnage perso, GroupClasses gClasses, GroupArmures gArmures,
			GroupArmes gArmes) {

		this.gClasses = gClasses;
		this.gArmes = gArmes;
		this.gArmures = gArmures;

		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);
		thisGroup.setText("Caractéristiques du personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		thisGroup.setFont(new Font(fenetre.getDisplay(), "Arial", 12, SWT.BOLD));

		nomPerso = new Text(thisGroup, SWT.BORDER);
		nomPerso.setTextLimit(50);
		nomPerso.setText("Saisir le nom du perso");
		nomPerso.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		nomPerso.setFont(new Font(fenetre.getDisplay(), "Arial", 12, SWT.NORMAL));
	
		pdv = new Label(thisGroup, SWT.CENTER);
		pdv.setText("Points de vie :      ");
		pdv.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		pdv.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		force = new Label(thisGroup, SWT.CENTER);
		force.setText("Force :       ");
		force.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		force.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		def = new Label(thisGroup, SWT.CENTER);
		def.setText("Defense :      ");
		def.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		def.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		lClasse = new Label(thisGroup, SWT.CENTER);
		lClasse.setText("Classe : ");
		lClasse.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		lClasse.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

		lArme = new Label(thisGroup, SWT.CENTER);
		lArme.setText("Arme : ");
		lArme.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		lArme.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

		lArmure = new Label(thisGroup, SWT.CENTER);
		lArmure.setText("Armure : ");
		lArmure.setFont(new Font(fenetre.getDisplay(), "Arial", 12,
				SWT.ITALIC));
		lArmure.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

	}

	public String getNomPerso() {
		return nomPerso.getText();
	}

	@Override
	public void update(Observable o, Object arg) {
		int defTmp = 0;
		int attTmp = 0;
		if (o instanceof GroupClasses) {
			// maj des labels
			lClasse.setText("Classe : " + gClasses.getValSelection());

			try {
				pv = (int) ((ChargementDynamique) arg).getMethodForName(
						"getPdv").invoke(
						((ChargementDynamique) arg).getClassInstancie());
				d = (int) ((ChargementDynamique) arg).getMethodForName(
						"getDefense").invoke(
						((ChargementDynamique) arg).getClassInstancie());
				f = (int) ((ChargementDynamique) arg).getMethodForName(
						"getForce").invoke(
						((ChargementDynamique) arg).getClassInstancie());

			} catch (IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (o instanceof GroupArmures) {
			lArmure.setText("Armure : " + gArmures.getValSelection());
			for (int i = 0; i < gArmures.getArmures().size(); i++) {
				if (gArmures.getArmures().get(i).getNameItem()
						.equalsIgnoreCase(gArmures.getValSelection())) {
					try {
						defTmp = (int) gArmures
								.getArmures()
								.get(i)
								.getMethodForName("getDefense")
								.invoke(gArmures.getArmures().get(i)
										.getClassInstancie());
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}else if (o instanceof GroupArmes) {
			lArme.setText("Arme : " + gArmes.getValSelection());
			for (int i = 0; i < gArmes.getItems().size(); i++) {
				if (gArmes.getItems().get(i).getNameItem()
						.equalsIgnoreCase(gArmes.getValSelection())) {
					try {
						attTmp = (int) gArmes.getItems()
								.get(i)
								.getMethodForName("getForce")
								.invoke(gArmes.getItems().get(i)
										.getClassInstancie());
					} catch (IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		int tmpDef = d + defTmp;
		int tmpAtt = f + attTmp;
		pdv.setText("Point de vie : " + Integer.toString(pv));
		def.setText("Defense : " + Integer.toString(tmpDef));
		force.setText("Force : " + Integer.toString( tmpAtt));
		// rafraichissement des labels
		lClasse.pack();
		lArme.pack();
		lArmure.pack();
	}
}
