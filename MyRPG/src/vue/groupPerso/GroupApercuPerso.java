package vue.groupPerso;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import util.PathManager;

public class GroupApercuPerso {

	private Group thisGroup;
	private Canvas photoPerso;
	protected Image persoImage;
	private Shell shell;
	private String nomPerso = "default";

	public GroupApercuPerso(Shell fenetre, GridData gridData) {

		thisGroup = new Group(fenetre, SWT.FLAT);
		thisGroup.setLayoutData(gridData);
		shell = fenetre;

		Text text = new Text(thisGroup, SWT.BORDER_DOT);
		text.setTextLimit(50);
		text.setText("Saisir le nom du perso");
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		thisGroup.setText("Apperçu personnage :");
		thisGroup.setLayout(new GridLayout());
		thisGroup.setBackgroundImage(new Image(fenetre.getDisplay(),
				PathManager.bgGroup));
		addPhotoApercu();

	}

	private void addPhotoApercu() {
		photoPerso = new Canvas(thisGroup, SWT.BORDER);
		GridData gridData = new GridData(GridData.FILL, GridData.FILL, true,
				true);
		gridData.widthHint = 80;
		gridData.heightHint = 80;
		gridData.verticalSpan = 3;
		photoPerso.setLayoutData(gridData);
		photoPerso.addPaintListener(new PaintListener() {
			public void paintControl(final PaintEvent event) {
				if (persoImage != null) {
					event.gc.drawImage(persoImage, 0, 0);
				}
			}
		});

		Button browse = new Button(thisGroup, SWT.PUSH);
		browse.setText("Browse...");
		gridData = new GridData(GridData.FILL, GridData.CENTER, true, false);
		gridData.horizontalIndent = 5;
		browse.setLayoutData(gridData);
		browse.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				String fileName = new FileDialog(shell).open();
				if (fileName != null) {
					persoImage = new Image(shell.getDisplay(), fileName);
				}
			}
		});

		Button delete = new Button(thisGroup, SWT.PUSH);
		delete.setText("Delete");
		gridData = new GridData(GridData.FILL, GridData.BEGINNING, true, false);
		gridData.horizontalIndent = 5;
		delete.setLayoutData(gridData);
		delete.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				if (persoImage != null) {
					persoImage.dispose();
					persoImage = null;
					photoPerso.redraw();
				}
			}
		});
	}

}
