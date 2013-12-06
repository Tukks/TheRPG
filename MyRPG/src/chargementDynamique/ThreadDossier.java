package chargementDynamique;

import java.io.IOException;
import java.net.MalformedURLException;



public class ThreadDossier extends Thread{
	ListenerChargementDyn lcd ;
	ThreadDossier(String folder) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException{
		lcd = new ListenerChargementDyn(folder);
	}
	public void run() {

		while (true) {
			ListFile lf = new ListFile(lcd.getFolder(), "");
			Integer sizeTemp = lf.nombreFichier();
			if (sizeTemp.intValue() != lcd.getSizePlug()) {

				if (sizeTemp.intValue() < lcd.getSizePlug()) {
					System.out.println("une classe en moins dude");

					lcd.setSizePlug(sizeTemp.intValue());
					try {
						lcd.ChargerAllClass();
						lcd.ChargerAllJar();
					} catch (MalformedURLException | InstantiationException
							| IllegalAccessException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					// on va supprimer celui qui est plus la
				} else if (sizeTemp.intValue() > lcd.getSizePlug()) {
					lcd.setSizePlug(sizeTemp.intValue());
					System.out.println("une classe en plus dude");
					try {
						lcd.ChargerAllClass();
						lcd.ChargerAllJar();
					} catch (MalformedURLException | InstantiationException
							| IllegalAccessException | ClassNotFoundException e) {
						// TODO Auto-generated catch block
					} catch (IOException e) {
						// TODO Auto-generated catch block
					}
					// on va ajouter celui qui est nouveaux

				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

}
