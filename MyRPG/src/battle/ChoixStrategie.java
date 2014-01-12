package battle;



import org.eclipse.swt.SWT;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Combo;

import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import chargementDynamique.ChargementDynamique;
import util.PathManager;

public class ChoixStrategie {
	
	private Group thisGroup;
	
	private ChargementDynamique items;
	private Combo choix;
	private Shell shell;
	public ChoixStrategie(Shell shell, ChargementDynamique items) {
		// TODO Auto-generated constructor stub
		/*
		 * Set de l'interface de definition de Strategie
		 */
		this.shell = shell;
		GridData gridData = new GridData();
		thisGroup = new Group(shell, SWT.FLAT);
		thisGroup.setText("Definir Strategie");
		thisGroup.setBackgroundImage(new Image(shell.getDisplay(),
				PathManager.bgGroup));
		thisGroup.setLayoutData(gridData);
		thisGroup.setSize(new Point(850,150));
		thisGroup.setLocation(new Point(150, 10));
		thisGroup.setLayout(new RowLayout(SWT.VERTICAL));

		this.items = items;
		this.isPotionOrAttaque();
	}
	public void isPotionOrAttaque(){
		if(items.getTypeItem() == "Potion" && !items.getNameItem().contains("auto")){
			Text text = new Text(thisGroup, SWT.NONE);
		    text.setText("Definir quand utiliser la Potion, en pourcentage par rapport au point de vie du Personnage : ");
		    text.setSize(850,15);
		    text.setLocation(15, 15);
		    text.setBackgroundImage(thisGroup.getBackgroundImage());
			choix = new Combo(thisGroup,SWT.DROP_DOWN | SWT.READ_ONLY );

			for(Integer i =1 ; i <= 100; i++){
				
				choix.add(i.toString());
			}
			choix.setSize(new Point(50,50));
			choix.setLocation(20, 30);
			
		}else if(items.getTypeItem() == "Potion" && items.getNameItem().contains("auto")){
			
		}else if(items.getTypeItem() == "Poison" && !items.getNameItem().contains("auto")){
			
			Text text = new Text(thisGroup, SWT.NONE);
		    text.setText("Definir quand utiliser le Poison, en pourcentage par rapport au point de vie du Personnage : ");
		    text.setSize(850,15);
		    text.setLocation(15, 15);
		    text.setBackgroundImage(thisGroup.getBackgroundImage());
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
	public int getChoix() {
		
	if(choix != null){
		return choix.getSelectionIndex()+1;
	}else{
		return 0;
	}
	}
	public void setChoix(Combo choix) {
		this.choix = choix;
	}
	public void disableMe(){
		this.choix.setEnabled(false);
	}
	
}
