package vue;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class GroupDeco {

	List<Object> listItem;
	private GridLayout layout = new GridLayout();;
	private Group group;

	public GroupDeco(Composite fenetre, String groupName, Image fond,
			int nbColonnes) {

		group = new Group(fenetre, SWT.FLAT);
		layout.numColumns = nbColonnes;

		group.setText(groupName);
		group.setLayout(layout);
		// group.setBackgroundImage(fond);

	}

	public Group getG() {
		return group;
	}

	private void FillGroup() {

	}

}
