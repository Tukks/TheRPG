package vue;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;

public class GroupDeco {

	List<Object> listItem;
	private GridLayout layout = new GridLayout();
	private Group group;
	private Image fond;

	public GroupDeco(Shell fenetre, String groupName, int nbColonnes) {

		group = new Group(fenetre, SWT.FLAT);
		fond = new Image(fenetre.getDisplay(), PathManager.bgGroup);
		layout.numColumns = nbColonnes;

		group.setText(groupName);
		group.setLayout(layout);
		group.setBackgroundImage(fond);

	}

	public Group getG() {
		return group;
	}

	private void FillGroup() {

	}

}
