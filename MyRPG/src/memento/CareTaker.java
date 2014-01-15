package memento;

import java.util.ArrayList;
import java.util.List;


public class CareTaker {
	  private List<Memento> statesList = new ArrayList<Memento>();
	 
	  public void addMemento(Memento m) {
	    statesList.add(m);
	  }
	 
	  public Memento getMemento(int index) {
	    return statesList.get(index);
	  }
	}
