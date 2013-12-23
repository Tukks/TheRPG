/*
 * 
 */
package chargementDynamique;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * The Class ListFile.
 */
public class ListFile {
	
	/** The root. */
	String root;
	
	/** The extensions. */
	String extensions;
	
	/** The filtre. */
	FilenameFilter filtre;

	/**
	 * Instantiates a new list file.
	 *
	 * @param root the root
	 * @param extension the extension
	 */
	public ListFile(String root, String extension) {
		this.root = root;
		this.extensions = extension;
		this.filtre = new Filtre();
	}

	/**
	 * List fichier.
	 *
	 * @return the array list
	 */
	public ArrayList<File> listFichier() {
		File file = new File(root);

		ArrayList<File> listFiles = new ArrayList(Arrays.asList(file.listFiles(filtre)));

		for (int i = 0; i < listFiles.size(); i++) {
			if (listFiles.get(i).isDirectory()) {
				listFiles.addAll(listeDir(listFiles.get(i).getAbsolutePath()));
			}
		}
		return listFiles;

	}

	/**
	 * Liste dir.
	 *
	 * @param root the root
	 * @return the array list
	 */
	public ArrayList<File> listeDir(String root) {
		File file = new File(root);
		ArrayList<File> nListFiles = new ArrayList(Arrays.asList(file
				.listFiles(filtre)));
		return nListFiles;

	}
	
	/**
	 * Nombre fichier.
	 *
	 * @return the int
	 */
	public int nombreFichier() {
		File file = new File(root);
		ArrayList<File> listFiles = new ArrayList(Arrays.asList(file
				.listFiles()));
		int cpt = 0;
		for (int i = 0; i < listFiles.size(); i++) {
			if (!listFiles.get(i).isDirectory()) {
				cpt++;
			}
		}
		return cpt;

	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		ListFile fil = new ListFile(".", "class");
		for (int i = 0; i < fil.listFichier().size(); i++) {
			System.out.println(fil.listFichier().get(i));
		}

	}

	/**
	 * The Class Filtre.
	 */
	private class Filtre implements FilenameFilter {

		/* (non-Javadoc)
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		@Override
		public boolean accept(File dir, String name) {
			if (new File(dir, name).isDirectory()) {
				return true;

			} else {
				return name.endsWith("." + extensions);
			}

		}

	}
}
