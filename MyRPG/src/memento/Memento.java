package memento;

import vue.InterfaceRPG;

// TODO: Auto-generated Javadoc
/**
 * The Class Memento.
 */
public class Memento {
	  
  	/** The state. */
  	private InterfaceRPG state;
	   
	  /**
  	 * Instantiates a new memento.
  	 *
  	 * @param state the state
  	 */
  	public Memento(InterfaceRPG state){
	    this.state = state;
	  }
	 
	  /**
  	 * Gets the state.
  	 *
  	 * @return the state
  	 */
  	public InterfaceRPG getState() {
	    return state;
	  }
	}