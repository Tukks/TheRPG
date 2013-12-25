package vue;

import java.util.Observer;

import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.widgets.Display;

public abstract class AGroup implements Observer {

	Display display;
	Font font;

	public AGroup(Display display) {
		this.display = display;
	}
}
