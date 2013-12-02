package chargementDynamique;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListenerChargementDyn implements ActionListener {
	ArrayList<ChargementDynamique> pluginItem = new ArrayList<ChargementDynamique>();
	ArrayList<ChargementDynamique> pluginClasse = new ArrayList<ChargementDynamique>();

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Regarder a intervalleregulier dans le dossier Plugin
		
	}
	public Boolean isItem(ChargementDynamique plugin){
		// TODO Definir Grace au annotation si c'est un Plugin item
		plugin.classCharged.getAnnotations();
		return null;
		
	}
	public Boolean isClass(ChargementDynamique plugin){
		// TODO Definir Grace au annotation si c'est un Plugin Classe de personnage
		return null;
		
	}
	public void getClassForName(String name){
		
	}
	
}
