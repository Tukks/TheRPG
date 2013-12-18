package vue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;

public class GroupAppercuPerso {

	private Group thisGroup;
	private Label label;

	public GroupAppercuPerso(Shell fenetre, GridData gridData) {

		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		label = new Label(thisGroup, SWT.NONE);
		label.setText("Saisir un nom de personnage");
		label.setBounds(10, 10, 100, 25);

		Text text = new Text(thisGroup, SWT.BORDER_DOT);
		text.setSize(200, 10);

		Composite appercuPerso = new Composite(thisGroup, SWT.BORDER);
		appercuPerso.setSize(400, 400);
		appercuPerso.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.classImg));

		thisGroup.setText("Caractéristiques du personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

	}

}
