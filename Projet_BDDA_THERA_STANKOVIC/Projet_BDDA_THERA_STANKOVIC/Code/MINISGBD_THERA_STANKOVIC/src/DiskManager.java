import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.Buffer;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class DiskManager {
	private static final String Data_ = null;
	private static final long pageSize = 4096;
	
	//Creation d'une instance de la classe DiskManager
		private static  DiskManager diskManager = new  DiskManager();
		private DiskManager() {
		}
		public static DiskManager getInstance() {
			return diskManager;
		}
		
	
	/**
	 * crée (dans le sous-dossier DB) un fichier Data_fileIdx.rf initialement vide 
	 * @param fileIdx identifiant de fichier
	 * @throws IOException 
	 */
	
	public static void CreateFile (int fileIdx) throws IOException {
		//try {
			//Creation du fichier
			File f = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/Data_"+ fileIdx+".rf");
			RandomAccessFile file = new RandomAccessFile(f, "rw") ;
			
			//fermeture du fichier
			file.close();
		//} catch (FileNotFoundException e) {
		//	System.out.println("Fichier non trouvé"+ e.getMessage());
		//	e.printStackTrace();
		//}
	}
	/**
	 * rajoute une pageSize
			for(int i=0 ; i<this.bufferPool.size(); i++) {
				
				//Si l'identifiant de la page est egale à celui de la page
				if(frame.getIdDeLaPage().equals(pageId))
				{
					frame.incrementerPin_count();
					
					//	Supprime de la liste pageLru la case dont le pin_count est different de 0
					miseAJour(page au fichier spécifié par fileIdx
	 * 
	 * @param fileIdx identifiant de fichier
	 * @return un pageId correspondant à la page nouvelllement rajoutée
	 * @throws IOException 
	 */
	
	public static PageId AddPage(int fileIdx) throws IOException
	{
		PageId pageIdx = new PageId();
	   // try {	
		//Ouverture du fichier en lecture et ecriture
		File f = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/Data_"+ PageId.getPageIdx()+".rf");
		RandomAccessFile file = new RandomAccessFile (f , "rw");
		
		//Se positionner à la fin du fichier
	
		file.seek(file.length());
		
		//Calcul de l'identidiant de la page
		// int idDeLaPage = (int)( (file.length()) / pageSize - 1 ) ; 
	
		 //Ecriture dans le fichier file
		 file.write(new byte[Main.getPageSize]);
		 file.write(new byte[Main.pageSize]);
		 //Fermeture du fichier
		 file.close();
		 
	    //}catch(FileNotFoundException e) {
	    //	System.out.println(e.getMessage());
	    //}
		
	    //Remplir la variable pageId avec les valeurs correspondant
		pageIdx.setFileIdx(fileIdx);
		pageIdx.setPageIdx(pageIdx);
		System.out.println("Page ajoutée \n");
		//retourne un PageId correspondant à la page nouvellement rajoutée
		return pageIdx;
	}
	/*
	* 
	*/
	/**
	 * Cette méthode doit remplir largument buff avec le contenu disque de la page identifiée
	 * 
	 * @param idDeLaPage identifiant de la page
	 * 
	 * @param buffer le buffer 
	 * 
	 * @param ligne aide à la lecture de file
	 * 
	 */
	public static void ReadPage (PageId idDeLaPage, byte[] buffer) throws IOException {
		//try {	
			//Ouverture du fichier en lecture
			File f = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/Data_"+ idDeLaPage.getPageIdx()+".rf");
			RandomAccessFile file = new RandomAccessFile (f , "rw");
			
			
			
			//Accès à la position de la pageId
			//for(int i = 0 + idDeLaPage.getPageIdx() ; i< idDeLaPage.getPageIdx() +8 ; i++) {
			//file.seek( idDeLaPage.getPageIdx() * pageSize);
			/**
			FileReader fReader = new FileReader(f.getAbsoluteFile()) ;
			BufferedReader bReader = new BufferedReader(fReader) ;
			String ligne = null;
			
			while((ligne = bReader.readLine()) != null) {		
				System.out.println(ligne);
			}
			*/
			//lecture de la page dans le buff
			
			
			
			//file.seek(i);
			//System.out.println(""+file.read());
			
			//}
			
			//écrit dans un buff le contenu de la page
			file.seek( idDeLaPage.getFileIdx() * pageSize);
			
			System.out.println(file.read());
			
			file.seek( idDeLaPage.getFileIdx() * pageSize);
			buffer[0] = file.readByte();
			
			
			//file.seek( idDeLaPage.getFileIdx() * pageSize + i);
			//buffer[] = (byte) file.read();
		    //}
			
			
			//fermeture du fichier
			file.close();

		//}catch(FileNotFoundException e) {
		//	System.out.println(e.getMessage());
		
		//}
		
	}
	/**
	 *  écrit le contenu de largument buff dans le fichier 
	 * 
	 * @throws IOException 
	 * 
	 */
	
	public static void WritePage (PageId idDeLaPage, byte[] buffer) throws IOException {
		try {
			//Ouverture du fichier en lecture
			File f = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/Data_"+ idDeLaPage.getPageIdx()+".rf");
			RandomAccessFile file = new RandomAccessFile(f, "rw") ;
			
			//Acces à la position de la pageId
			//for(int i = 0+ idDeLaPage.getPageIdx() ; i< idDeLaPage.getPageIdx()+8 ; i++) {
			//	file.seek(i);
			//	file.write(buffer);
			//}
			
			int i = 0;
			
			//eécrit tout le contenu du buffer dans la page
			while(i < buffer.length) {
			file.seek(idDeLaPage.getFileIdx() *pageSize + i );
			
		    
			//ecriture dans le buff
			file.write(buffer[i]);
			i++;
			}
			
			//FileWriter fichier = new FileWriter ("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/Data_"+ idDeLaPage.getPageIdx()+".rf");
			//fichier.write(buffer[0]);
			
			//Fermeture du fichier
			file.close();
		}catch(FileNotFoundException e) {
			
			System.out.println(e.getMessage()+"\n");
		}
		
		
	}
	public static void main(String [] args) throws IOException {
		CreateFile(10);
		PageId page = new PageId(0,10);
		AddPage(0);
		byte[] buffer = new byte[10] ;
		buffer[0] =7;
		buffer[2] =8;
		WritePage(page,buffer);
		ReadPage(page,buffer);
		
		//for(int i = 0; i< buffer.length ; i++) {
		//	System.out.println(""+buffer[i] + "\n");
		//	}
		byte[] buffer2 = new byte[10];
		buffer2[0]=4;
		buffer2[5]=3;
		PageId page2 = new PageId(1,10);
		
		byte[] buffer3 = new byte[10];
		AddPage(1);
		WritePage(page2, buffer2);
		ReadPage(page2,buffer3);
		buffer2[0]=3;
		buffer2[9]=7;
		WritePage(page2, buffer2);
		
		ReadPage(page2,buffer3);
		//vérifie que le buffer entré en paramètre à bien récupéré la valeur
		for(int i = 0; i< buffer3.length ; i++) {
		System.out.println(""+buffer3[i] + "\n");
		}
		//System.out.println(buffer3[0]);
		String moi = "okkkkk";
		moi.length();
		System.out.println(""+moi.charAt(moi.length()-1));
	}
}

