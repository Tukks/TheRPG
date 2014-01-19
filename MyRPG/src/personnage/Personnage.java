package personnage;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.List;

import objet.Item;
import serializable.VisitorRPG;
import chargementDynamique.ChargementDynamique;

// TODO: Auto-generated Javadoc
/**
 * The Class Personnage.
 */
public class Personnage implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 707821524617984045L;
	
	/** The nom. */
	String nom;
	
	/** The item. */
	Item item;
	
	/** The class perso. */
	ChargementDynamique classPerso;
	
	/** The pers. */
	private static Personnage pers;
	// coeff multiplicateur
	/** The point de vie. */
	int pointDeVie;
	
	/** The force de frappe. */
	int forceDeFrappe;
	
	/** The defense. */
	int defense;


	/**
	 * Instantiates a new personnage.
	 */
	private Personnage() {
		nom = "";
		pointDeVie = 0;
		forceDeFrappe = 0;
		defense = 0;
	}
	
	/**
	 * Gets the single instance of Personnage.
	 *
	 * @return single instance of Personnage
	 */
	public static Personnage getInstance(){
		if (null == pers) { // Premier appel


			pers = new Personnage();

		}

		return pers;
	}
	
	/**
	 * Inits the.
	 *
	 * @param item the item
	 * @param classPerso the class perso
	 * @param nom the nom
	 * @throws IllegalAccessException the illegal access exception
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws InvocationTargetException the invocation target exception
	 */
	public void init(Item item, ChargementDynamique classPerso, String nom) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		this.item = item;
		this.nom = nom;
		this.classPerso = classPerso;
		int m = this.classPerso.getMethodForName("getPdv").getModifiers();
		System.out.println(Modifier.isPublic(m));
		pointDeVie =(int) this.classPerso.getMethodForName("getPdv").invoke(this.classPerso.getClassInstancie());
		forceDeFrappe =(int) this.classPerso.getMethodForName("getForce").invoke(this.classPerso.getClassInstancie()) +
				(int) this.item.getArme().getMethodForName("getForce").invoke(this.item.getArme().getClassInstancie());
		defense = (int) this.classPerso.getMethodForName("getDefense").invoke(this.classPerso.getClassInstancie()) +
				(int) this.item.getArmure().getMethodForName("getDefense").invoke(this.item.getArmure().getClassInstancie());
		
	}
	
	/**
	 * Combattre.
	 *
	 * @param item the item
	 * @return the int
	 */
	public int combattre(List<Item> item) {
		return 0;
	}

	/**
	 * Accept.
	 *
	 * @param visitor the visitor
	 */
	public void accept(VisitorRPG visitor) {
		visitor.visiter(this);
	}

	/**
	 * Gets the nom.
	 *
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Sets the nom.
	 *
	 * @param nom the new nom
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * Gets the item.
	 *
	 * @return the item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * Sets the item.
	 *
	 * @param item the new item
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * Gets the class perso.
	 *
	 * @return the class perso
	 */
	public ChargementDynamique getClassPerso() {
		return classPerso;
	}

	/**
	 * Sets the class perso.
	 *
	 * @param classPerso the new class perso
	 */
	public void setClassPerso(ChargementDynamique classPerso) {
		this.classPerso = classPerso;
	}

	/**
	 * Gets the point de vie.
	 *
	 * @return the point de vie
	 */
	public int getPointDeVie() {
		return pointDeVie;
	}

	/**
	 * Sets the point de vie.
	 *
	 * @param pointDeVie the new point de vie
	 */
	public void setPointDeVie(int pointDeVie) {
		this.pointDeVie = pointDeVie;
	}

	/**
	 * Gets the force de frappe.
	 *
	 * @return the force de frappe
	 */
	public int getForceDeFrappe() {
		return forceDeFrappe;
	}

	/**
	 * Sets the force de frappe.
	 *
	 * @param forceDeFrappe the new force de frappe
	 */
	public void setForceDeFrappe(int forceDeFrappe) {
		this.forceDeFrappe = forceDeFrappe;
	}

	/**
	 * Gets the defense.
	 *
	 * @return the defense
	 */
	public int getDefense() {
		return defense;
	}

	/**
	 * Sets the defense.
	 *
	 * @param defense the new defense
	 */
	public void setDefense(int defense) {
		this.defense = defense;
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
