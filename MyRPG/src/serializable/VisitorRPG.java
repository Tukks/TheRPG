package serializable;
import java.io.*;
import java.lang.reflect.InvocationTargetException;

import objet.Item;
import chargementDynamique.ChargementDynamique;
import personnage.Personnage;
import battle.Enemy;
// TODO: Auto-generated Javadoc

/**
 * The Interface VisitorRPG.
 */
public interface VisitorRPG {

	
	/**
	 * Visiter.
	 *
	 * @param e the e
	 */
	public void visiter(Enemy e);
	
	/**
	 * Devisite enemy.
	 *
	 * @return the enemy
	 */
	public Enemy devisiteEnemy();
	
	/**
	 * Visiter.
	 *
	 * @param p the p
	 */
	public void visiter(Personnage p);
	
	/**
	 * Devisite personnage.
	 *
	 * @return the personnage
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 */
	public Personnage devisitePersonnage() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException;
	
	/**
	 * Visiter.
	 *
	 * @param i the i
	 */
	public void visiter(Item i);  
	
	/**
	 * Devisite item.
	 *
	 * @return the item
	 */
	public Item devisiteItem();
	
	/**
	 * Visiter.
	 *
	 * @param cd the cd
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void visiter(ChargementDynamique cd) throws IOException;
	//public Object devisiteChargementDynamique();
	/**
	 * Devisite chargement dynamique.
	 *
	 * @return the chargement dynamique
	 * @throws InstantiationException the instantiation exception
	 * @throws IllegalAccessException the illegal access exception
	 */
	public ChargementDynamique devisiteChargementDynamique() throws InstantiationException, IllegalAccessException;

}
