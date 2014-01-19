package main;

import java.io.IOException;

import org.eclipse.swt.widgets.Display;

import memento.CareTaker;
import memento.Memento;
import memento.Originator;
import vue.HomeRPG;
import vue.InterfaceRPG;

public class Main {

	public static void main(String[] args) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException, IOException {
		new HomeRPG();
		
		
	  
		 /*
					InterfaceRPG i;
					
						i = new InterfaceRPG();
						Originator originator = new Originator();
					    originator.setState(i.clone());
					    Memento memento = originator.createMemento();
					    CareTaker caretaker = new CareTaker();
					    caretaker.addMemento(memento);
					    
					    memento = caretaker.getMemento(0);
					    originator.setMemento(memento);
					    System.out
					        .println("Originator Current State: " + originator.getState().getgArmes().getValSelection());
					    
						 InterfaceRPG newz = new InterfaceRPG(originator.getState());
					
					 
						   System.out.println( i.getgArmes().getValSelection());
						  
					*/
					
}
}
