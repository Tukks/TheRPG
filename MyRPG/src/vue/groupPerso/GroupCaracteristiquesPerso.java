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

		force = new Label(thisGroup, SWT.CENTER);
		force.setText("Force :       ");
		pdv = new Label(thisGroup, SWT.CENTER);
		pdv.setText("Points de vie :      ");
		def = new Label(thisGroup, SWT.CENTER);
		def.setText("Defense :      ");

		lClasse = new Label(thisGroup, SWT.CENTER);
		lClasse.setText("Classe : ");

		lArme = new Label(thisGroup, SWT.CENTER);
		lArme.setText("Arme : ");

		lArmure = new Label(thisGroup, SWT.CENTER);
		lArmure.setText("Armure : ");

		nomPerso = new Text(thisGroup, SWT.SINGLE);
		nomPerso.setTextLimit(50);
		nomPerso.setText("Saisir le nom du perso");
		nomPerso.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
		nomPerso.setFont(new Font(fenetre.getDisplay(), "Arial", 11, SWT.NORMAL));
	}

	public String getNomPerso() {
		return nomPerso.getText();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (o instanceof GroupClasses) {
			// maj des labels
			lClasse.setText("Classe : " + gClasses.getValSelection());

			int pv = 0;
			int d = 0;
			int f = 0;
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

			pdv.setText(Integer.toString(pv));
			def.setText(Integer.toString(d));
			force.setText(Integer.toString(f));
		}

		lArme.setText("Arme : " + gArmes.getValSelection());
		lArmure.setText("Armure : " + gArmures.getValSelection());
		// rafraichissement des labels
		lClasse.pack();
		lArme.pack();
		lArmure.pack();
	}
}
