package serializable;
import java.io.*;

import objet.Item;
import chargementDynamique.ChargementDynamique;
import personnage.Personnage;
import battle.Enemy;
public interface VisitorRPG {

	
	public void visiter(Enemy e);
	public Enemy devisiteEnemy();
	public void visiter(Personnage p);
	public Personnage devisitePersonnage() throws InstantiationException, IllegalAccessException;
	public void visiter(Item i);  
	public Item devisiteItem();
	public void visiter(ChargementDynamique cd) throws IOException;
	//public Object devisiteChargementDynamique();
	public ChargementDynamique devisiteChargementDynamique() throws InstantiationException, IllegalAccessException;

}
