package chargementDynamique;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

public class ListFile {
	String root;
	String extensions;
	FilenameFilter filtre;

	public ListFile(String root, String extension) {
		this.root = root;
		this.extensions = extension;
		this.filtre = new Filtre();
	}

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

	public ArrayList<File> listeDir(String root) {
		File file = new File(root);
		ArrayList<File> nListFiles = new ArrayList(Arrays.asList(file
				.listFiles(filtre)));
		return nListFiles;

	}

	public static void main(String[] args) {
		ListFile fil = new ListFile(".", "class");
		for (int i = 0; i < fil.listFichier().size(); i++) {
			System.out.println(fil.listFichier().get(i));
		}

	}

	private class Filtre implements FilenameFilter {

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
