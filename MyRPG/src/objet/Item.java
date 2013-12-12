package objet;

import java.util.ArrayList;

import chargementDynamique.ChargementDynamique;

public class Item {

	ChargementDynamique Arme;
	ChargementDynamique Armure;
	ChargementDynamique Potion;
	public int select(){
		return 1;
	}

	public Item(ChargementDynamique arme,
			ChargementDynamique armure,
			ChargementDynamique potion) {
		super();
		Arme = arme;
		Armure = armure;
		Potion = potion;
	}
}
