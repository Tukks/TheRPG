package chargementDynamique;

import java.lang.reflect.Method;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public interface ChargementDynamique {
	
	public Method getMethodForName(String name);
	void listAllMethod();
}
