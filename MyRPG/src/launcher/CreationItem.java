package launcher;

import objet.Item;
import objet.Type;

public class CreationItem {

	public static void main (String[] args){
		
		Item epee = new Item(1, "Epee", "file:img/epee.png", Type.Arme);
				
		System.out.println(epee.armement(15) + " " + epee.armement(15));
	}
}
