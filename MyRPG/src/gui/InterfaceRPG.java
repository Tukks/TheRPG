package gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import util.PathManager;

public class InterfaceRPG {

	private static Display display;

	public static void centrerSurEcran(Display display, Shell shell) {
		Rectangle rect = display.getClientArea();
		Point size = shell.getSize();
		int x = (rect.width - size.x) / 2;
		int y = (rect.height - size.y) / 2;
		shell.setLocation(new Point(x, y));
	}

	public static void main(String[] args) {
		display = new Display();
		Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN);
		shell.setSize(1024, 700);
		ImageData cursor_Image = new ImageData(PathManager.cursorImg);

		Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
		shell.setCursor(cursor1);

		Image bg_Image = new Image(display, PathManager.bgImg);
		shell.setBackgroundImage(bg_Image);

		centrerSurEcran(display, shell);

		shell.open();

		while (!shell.isDisposed())
			if (!display.readAndDispatch())
				display.sleep();

		display.dispose();
	}
}