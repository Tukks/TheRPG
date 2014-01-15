package memento;

import java.util.List;
import java.util.ArrayList;

import vue.InterfaceRPG;

	public class Originator {
		   
		  //this String is just for example
		  //in real world application this 
		  //will be a java class - the object
		  //for which the state to be stored
		  private InterfaceRPG state;
		 
		  public void setState(InterfaceRPG state) {
		    this.state = state;
		  }
		 
		  public InterfaceRPG getState() {
		    return state;
		  }
		 
		  public Memento createMemento() {
		    return new Memento(state);
		  }
		 
		  public void setMemento(Memento memento) {
		    state = memento.getState();
		  }
		}
 

