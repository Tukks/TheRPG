package gui;
import org.eclipse.swt.widgets.*;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.graphics.*;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.*;

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
    ImageData cursor_Image = new ImageData("C:\\Users\\Bastien\\workspace\\InterfaceRPG\\src\\cursor.png"); 

    Cursor cursor1 = new Cursor(display, cursor_Image, 1, 1);
shell.setCursor(cursor1);    

    
    Image bg_Image = new Image(display, "C:\\Users\\Bastien\\workspace\\InterfaceRPG\\src\\bg.jpg"); 
    shell.setBackgroundImage(bg_Image);

    centrerSurEcran(display, shell);

    shell.open();

    while (!shell.isDisposed())
      if (!display.readAndDispatch())
        display.sleep();

    display.dispose();
  }
}