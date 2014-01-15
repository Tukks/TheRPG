package memento;

import vue.InterfaceRPG;

public class Memento {
	  private InterfaceRPG state;
	   
	  public Memento(InterfaceRPG state){
	    this.state = state;
	  }
	 
	  public InterfaceRPG getState() {
	    return state;
	  }
	}