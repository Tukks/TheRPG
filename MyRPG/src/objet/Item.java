package objet;

import java.io.Serializable;

import serializable.VisitorRPG;
import chargementDynamique.ChargementDynamique;

// TODO: Auto-generated Javadoc
/**
 * The Class Item.
 */
public class Item implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2613658683071421393L;
	
	/** The Arme. */
	ChargementDynamique Arme;
	
	/** The Armure. */
	ChargementDynamique Armure;
	
	/** The Potion. */
	ChargementDynamique Potion;
	
	/**
	 * Select.
	 *
	 * @return the int
	 */
	public int select(){
		return 1;
	}

	/**
	 * Instantiates a new item.
	 *
	 * @param arme the arme
	 * @param armure the armure
	 * @param potion the potion
	 */
	public Item(ChargementDynamique arme,
			ChargementDynamique armure,
			ChargementDynamique potion) {
		super();
		Arme = arme;
		Armure = armure;
		Potion = potion;
	}
	
	/**
	 * Accept.
	 *
	 * @param visitor the visitor
	 */
	public void accept(VisitorRPG visitor){
		visitor.visiter(this);
	}

	/**
	 * Gets the arme.
	 *
	 * @return the arme
	 */
	public ChargementDynamique getArme() {
		return Arme;
	}

	/**
	 * Sets the arme.
	 *
	 * @param arme the new arme
	 */
	public void setArme(ChargementDynamique arme) {
		Arme = arme;
	}

	/**
	 * Gets the armure.
	 *
	 * @return the armure
	 */
	public ChargementDynamique getArmure() {
		return Armure;
	}

	/**
	 * Sets the armure.
	 *
	 * @param armure the new armure
	 */
	public void setArmure(ChargementDynamique armure) {
		Armure = armure;
	}

	/**
	 * Gets the potion.
	 *
	 * @return the potion
	 */
	public ChargementDynamique getPotion() {
		return Potion;
	}

	/**
	 * Sets the potion.
	 *
	 * @param potion the new potion
	 */
	public void setPotion(ChargementDynamique potion) {
		Potion = potion;
	}

	/**
	 * Gets the serialversionuid.
	 *
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
