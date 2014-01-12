package battle;

import java.awt.event.MouseAdapter;
import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import chargementDynamique.ChargementDynamique;
import util.PathManager;

public class ChoixStrategie {
	
	private Group thisGroup;
	private List listeDesArmes;
	private String valSelection = "";
	private ChargementDynamique items;
	private Combo choix;
	
	public ChoixStrategie(Shell shell, ChargementDynamique items) {
		// TODO Auto-generated constructor stub
		/*
		 * Set de l'interface de definition de Strategie
		 */
		GridData gridData = new GridData();
		thisGroup = new Group(shell, SWT.FLAT);
		thisGroup.setText("Definir Strategie");
		thisGroup.setBackgroundImage(new Image(shell.getDisplay(),
				PathManager.bgGroup));
		thisGroup.setLayoutData(gridData);
		thisGroup.setSize(new Point(850,150));
		thisGroup.setLocation(new Point(150, 10));
		this.items = items;
		this.isPotionOrAttaque();
	}
	public void isPotionOrAttaque(){
		if(items.getTypeItem() == "Potion" && !items.getNameItem().contains("auto")){
			choix = new Combo(thisGroup,SWT.DROP_DOWN | SWT.READ_ONLY );
			choix.addMouseListener(new org.eclipse.swt.events.MouseAdapter() {

				@Override
				public void mouseDown(MouseEvent arg0) {
					// TODO Auto-generated method stub
					super.mouseDown(arg0);
					//pourcentagePdvPerso = (int) choix.getSelectionIndex();
				}
			
			});
			for(Integer i =1 ; i <= 100; i++){
				
				choix.add(i.toString());
			}
			choix.setSize(new Point(50,50));
			choix.setLocation(20, 10);
			
		}else if(items.getTypeItem() == "Potion" && items.getNameItem().contains("auto")){
			
		}else if(items.getTypeItem() == "Poison" && !items.getNameItem().contains("auto")){
			choix = new Combo(thisGroup,SWT.DROP_DOWN | SWT.READ_ONLY );
			for(Integer i =1 ; i <= 100; i++){
				
				choix.add(i.toString());
			}
			choix.setSize(new Point(50,50));
			choix.setLocation(20, 30);
		}else if(items.getTypeItem() == "Poison" && items.getNameItem().contains("auto")){
			
		}else if(items == null){
			//do nothing
		}
	}
	public Combo getChoix() {
		return choix;
	}
	public void setChoix(Combo choix) {
		this.choix = choix;
	}
	
}
