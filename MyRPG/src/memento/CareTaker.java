package memento;

import java.util.ArrayList;
import java.util.List;


// TODO: Auto-generated Javadoc
/**
 * The Class CareTaker.
 */
public class CareTaker {
	  
  	/** The states list. */
  	private List<Memento> statesList = new ArrayList<Memento>();
	   
	  
	  /**
  	 * Adds the memento.
  	 *
  	 * @param m the m
  	 */
  	public void addMemento(Memento m) {
	    statesList.add(m);
	  }
	 
	  /**
  	 * Gets the memento.
  	 *
  	 * @param index the index
  	 * @return the memento
  	 */
  	public Memento getMemento(int index) {
	    return statesList.get(index);
	  }
	}
