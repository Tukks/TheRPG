package vue;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;

public class GroupCaracteristiquesPerso {

	private Group thisGroup;

	public GroupCaracteristiquesPerso(Shell fenetre, GridData gridData) {

		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);

		thisGroup.setText("Caractéristiques du personnage");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));

	}

}
