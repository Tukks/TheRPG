package memento;

import java.util.List;
import java.util.ArrayList;

import vue.InterfaceRPG;

	// TODO: Auto-generated Javadoc
/**
	 * The Class Originator.
	 */
	public class Originator {
		   
		  //this String is just for example
		  //in real world application this 
		  //will be a java class - the object
		  //for which the state to be stored
		  /** The state. */
  		private InterfaceRPG state;
		 
		  /**
  		 * Sets the state.
  		 *
  		 * @param state the new state
  		 */
  		public void setState(InterfaceRPG state) {
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
		 
		  /**
  		 * Creates the memento.
  		 *
  		 * @return the memento
  		 */
  		public Memento createMemento() {
		    return new Memento(state);
		  }
		 
		  /**
  		 * Sets the memento.
  		 *
  		 * @param memento the new memento
  		 */
  		public void setMemento(Memento memento) {
		    state = memento.getState();
		  }
		}
 

