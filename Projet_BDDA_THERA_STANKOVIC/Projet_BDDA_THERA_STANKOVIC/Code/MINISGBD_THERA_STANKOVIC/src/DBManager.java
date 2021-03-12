import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.Scanner;
public class DBManager {
		//private static int dbManager;
		
		//private static DBManager DBManager = new DBManager();
		private static DBManager dBManager = null ;
		private String cheminFileCsv;
		private DBManager() {
			super();
		}
		public final static DBManager getInstance() {
			if (DBManager.dBManager == null) {
	            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
	            // multiple même par différents "threads".
	            // Il est TRES important.
	            synchronized(DBManager.class) {
	              if (DBManager.dBManager == null) {
	            	  DBManager.dBManager = new DBManager();
	              }
	            }
	         }
			
			return DBManager.dBManager;
		}

		//public static int getDbManager() {
		//return dbManager;
		//}

		//public void setDbManager(int dbManager) {
		//this.dbManager = dbManager;
		//}
		public static void init() throws IOException {
		DBInfo.getInstance().init();
		}
		public String getCheminFileCsv() {
			return  cheminFileCsv;
		}
		public void setCheminFileCsv(String s) {
			this.cheminFileCsv = s;
		}
		public static void finish() throws IOException {
		DBInfo.getInstance().finish();
		BufferManager.getInstance().flushAll();
		}
		public void processCommand(String commande) {
			/*« parsing » de la commande en utilisant par
			exemple la méthode split de la classe String, détection du mot clé CREATEREL, etc., pour finir
			sur un appel de la methode CreateRelation avec les bons arguments
			*/
			String[] mots;
			String commande2 = commande.replaceAll(":"," ");
			mots = commande2.split(" ");
			
			if(mots[0].equals("CREATEREL")) {
				String nomRelation = mots[1];
				ArrayList <String> nomColonne = new ArrayList <String>();
				ArrayList <String> typeColonne = new ArrayList <String> ();
				
				
				for(int i=2 ; i<mots.length ; i++) {
					nomColonne.add(mots[i]);
					typeColonne.add(mots[i+1]);
					i++;
				}
				int nbrColonne = nomColonne.size();
				RelationInfo relationInfo = new RelationInfo (nomRelation,nbrColonne, nomColonne, typeColonne);
				System.out.println("A été crée la relation suivante"+relationInfo.toString());
				DBInfo.getInstance().tab.add(relationInfo);
				DBInfo.getInstance().setCompteurRelations(DBInfo.getInstance().getCompteurRelations()+1);
			}
			
		}

		public void clean() {
			System.out.println("Renitialisation");
			BufferManager.getInstance().rest();
			FileManager.getInstance().reset();
			DBInfo.getInstance().reset();
		}
		
		/**
		 *La methode doit gerer l'insertion  dun record dans une relation, en indiquant les valeurs 
		 * On recherche d'abord le nom de la relation dans le FileManager, sil y ait une cree une nouveau
		 * record et on l'insère sinn pas la peine 
		 * @param nomRelation
		 * @param nbreColonnes
		 * @param nomsColonnes
		 * @param typesColonnes
		 */
		public void insert(String nomRelation, List <String> values)
		{
			String convertionNomRealation = nomRelation.toLowerCase();
		
			System.out.println("Insertion du record dans la relation "+nomRelation);
		
			for(int i =0; i<FileManager.getInstance().getAllHeapFile().size(); i++) {
				if(FileManager.getInstance().getHeapFiles().get(i).getRelInfo().getNomRelation().toLowerCase().equals(convertionNomRealation)) {
					Record nouveauRecord = new Record(DBInfo.getInstance().getTab().get(i), values);
					try {
						FileManager.getInstance().getAllHeapFile().get(i).InsertRecord(nouveauRecord);
					} catch (IOException e) {
						System.out.println("Erreur"+e.getMessage());
						e.printStackTrace();
					}

				}
			}
		
		}
		/**
		 * Ajout de plusieur record dans une relation
		 * @param nomRelation
		 * @param values
		 */
		public void batchInsert(String nomRelation, List<String> values) 
		{
			String convertionNomRealation = nomRelation.toLowerCase();
		
			System.out.println("Insertion du record dans la relation "+nomRelation);
			for(int i =0; i<FileManager.getInstance().getAllHeapFile().size(); i++) {
				if(FileManager.getInstance().getHeapFiles().get(i).getRelInfo().getNomRelation().toLowerCase().equals(convertionNomRealation)) {
					List <Record> nouveauRecord = new ArrayList<>();
					Record tmp = nouveauRecord.get(i);
					Record recordAAjoute = new Record(DBInfo.getInstance().getTab().get(i), values);
					try {
						FileManager.getInstance().getAllHeapFile().get(i).InsertRecord(recordAAjoute);
					} catch (IOException e) {
					
						e.printStackTrace();
					}
				}
			}	
		}
		/**
		 * Elle suprime les fichiers .rf du repertoire Db ainsi que le fichier Catalog.def
		 */
				public void CLEAN() 
				{
					File repertoire = new File(Main.getInstance().DBPath);
					File[] lesFichiers = repertoire.listFiles();
					
					for(File file : lesFichiers)
					{
						if(file.getAbsolutePath().endsWith(".rf") || file.getAbsolutePath().endsWith("Catalog.def")) 
						{
						if(file.delete()) {
							System.out.println("Fichiers"+file+"a bien été suprimé");
						}else {
							System.out.println("Erreur : "+file+"n'a pas été suprimé");
						}
						BufferManager.getInstance().rest();
						FileManager.getInstance().reset();
						DBInfo.getInstance().reset();
						}
					
					}
				}
	/**
	 * 
	 * @param nomRelation
	 * @param values
	 */
	public void INSERT(String nomRelation, ArrayList<String> values)
	{
		String nomRelationLowerCase = nomRelation.toLowerCase();
		//Il faut rechercher le nom de la relation dans le fileManager
		for(int i =0; i <FileManager.getInstance().getAllHeapFile().size(); i++)
		{
			//Si on trouve la relation, on cree une nouveau record et on fait l'insertion
			if(FileManager.getInstance().getAllHeapFile().get(i).getRelInfo().getNomRelation().toLowerCase().equals(nomRelationLowerCase)) {
				Record nouveauRecord = new  Record(DBInfo.getInstance().getTab().get(i), values);
				try {
					FileManager.getInstance().getAllHeapFile().get(i).InsertRecord(nouveauRecord);
				} catch (IOException e) {
					
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
	 * @param nomRelation
	 * @param nomFichierCsv
	 */
	public void INSERTALL(String nomRelation , String nomFichierCsv) {
		String cheminFileCsv =Main.getInstance().DBPath+ nomFichierCsv;
		this.setCheminFileCsv(nomFichierCsv);
		String ligne = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(cheminFileCsv));
			while((ligne = br.readLine() ) != null) {
				ArrayList<String> values = new ArrayList<>();
				StringTokenizer st = new StringTokenizer(ligne, ",");
				while (st.hasMoreTokens()) {
					values.add(st.nextToken());
				}
				INSERT(nomRelation,values);
			}
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
	/**
	 * 
	 * @param nomRelation
	 */
	public void SELECTALL(String nomRelation) 
	{
		String nomRelationLowerCase = nomRelation.toLowerCase();
		for (int i = 0; i < FileManager.getInstance().getAllHeapFile().size(); i++) {
			if (FileManager.getInstance().getAllHeapFile().get(i).getRelInfo().getNomRelation().toLowerCase()
					.equals(nomRelationLowerCase)) {
				// on recupere la liste de record associée à cette relation
				List<Record> listDeRecords = FileManager.getInstance().getAllHeapFile().get(i).GetAllRecords();
				int n =0;
				for(Record item: listDeRecords) {	
				String ligneDeRecord = "";
				for (int j = 0; j < item.getValues().size(); j++) 
				{
					if (j == (item.getValues().size() - 1))
					{
						// pour eviter d'avoir un point virgule à la fin du record
						ligneDeRecord = ligneDeRecord + item.getValues().get(j);
					} else {
						ligneDeRecord = ligneDeRecord + item.getValues().get(j) + ";";
					}
			
				}
				System.out.println("record n°" + (n + 1) + " " + ligneDeRecord);
				n++;
		
				}
				System.out.println("Total des records = "+ n);
			}
	}
}
	
	public void SELECT(String nomRelation, int idColonne, String valeur) {
		ArrayList<Record> selectionRecord = FileManager.getInstance().select(nomRelation, idColonne, valeur);

		int n = 0;
		for (Record tmp : selectionRecord) 
		{
			String ligneRecord = "";
			for (int j = 0; j < tmp.getValues().size(); j++) 
			{
				if (j == (tmp.getValues().size() - 1)) 
				{
					//eviter d'avoir un point virgule à la fin du record
					ligneRecord = ligneRecord + tmp.getValues().get(j);
				} else {
					ligneRecord = ligneRecord + tmp.getValues().get(j) + ";";
				}
			}

			System.out.println("record n°" + (n + 1) + " " + ligneRecord);
			n++;
		}

		System.out.println("Total records = " + n);
		}
	
	
	public void DELETE(String nomRelation, int idColone, String valeur)
	{
		String nomRelationLowerCase=nomRelation.toLowerCase();
		
		//on cherche le nom de la relation dans le FileManager 
		for(int i=0;i<FileManager.getInstance().getAllHeapFile().size();i++)
		{
			if(FileManager.getInstance().getAllHeapFile().get(i).getRelInfo().getNomRelation().toLowerCase().equals(nomRelationLowerCase)) 
				{
				//on recupere la liste de record associee cette relation
				List<Record> listDeRecords=FileManager.getInstance().getAllHeapFile().get(i).GetAllRecords();
				
				//on supprime le record correspondant à la valeur donnee et sur l'indice de la colonne
				for(int j=0; j<listDeRecords.size(); j++) 
				{
					if(listDeRecords.get(j).getValues().get(idColone).equals(valeur))
					{
						listDeRecords.remove(j);
					}
				}
			}
	
		}

	}
}

