
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
	//Creation de l'instance de la classe 
	
	private static FileManager fileManager = new FileManager();
	
	public static  FileManager getInstance()
	{
		return fileManager ;
	}
	private List <HeapFile> heapFiles;
	
	/**
	 * Initialisation
	 */
	public void init() {
		
		for(int i = 0; i<DBInfo.getInstance().getTab().size(); i++) {
			DBInfo.getInstance().tab.get(i);
			HeapFile hp = new HeapFile(DBInfo.getInstance().tab.get(i));
			this.heapFiles.add(hp);
	
		}
	
	}
	
	/**
	 * Creation d'une relation
	 *
	 */
	public void CreateRelationFile(RelationInfo relInfo) {
			//créer un nouvel objet de type HeapFile et lui attribuer relInfo
			HeapFile hp= new HeapFile(relInfo);
			
			//le rajouter à la liste heapFiles
			this.heapFiles.add(hp);
				
			//puis appeler sur cet objet la méthode createNewOnDisk.
				
				try {
					hp.createNewOnDisk();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	/**
	 * Cette méthode soccupera de linsertion de record dans la relation dont le nom est relName
	 * @param record
	 * @param relName
	 * @return
	 * @throws IOException 
	 */
	public Rid InsertRecordInRelation (Record record, char relName) throws IOException {
		for(int i =0 ; i <this.heapFiles.size(); i++) {
			if(this.heapFiles.get(i).nomRelation().equals(relName)) {
				return this.heapFiles.get(i).InsertRecord(record);
			}
		}
		return null;
		
	}
	
	/**
	 *  Lister tous les records dune relation (sélection sans conditions)
	 * @param record
	 * @param relName
	 * @return
	 */
	public List<Record> SelectAllFromRelation(Record record, char relName){
		HeapFile hp;
		boolean recherche = false;
		for(int i = 0; i<heapFiles.size(); i++) {
			if(this.heapFiles.get(i).nomRelation().equals(relName)) {
				return this.heapFiles.get(i).GetAllRecords();
			}
		}
		return null;
		
	}
	/**
	*@return la liste des des heapFile
	*/
	public ArrayList<HeapFile> getAllHeapFile()
	{
		ArrayList<HeapFile> hf= new ArrayList<>();
		for(HeapFile key: this.heapFiles) 
		{
			hf.add(key);
		}
		return hf;
	}
	
	public void reset() {
		this.heapFiles.clear();
	}

	public List<HeapFile> getHeapFiles() {
		return heapFiles;
	}

	public void setHeapFiles(List<HeapFile> heapFiles) {
		this.heapFiles = heapFiles;
	}
	
	public List<Record> SelectAll(String relName){
		boolean recherche = false;
		//Record x = null ;
		HeapFile relation;
		for(int i=0; i<this.heapFiles.size(); i++) {
			System.out.println("Le format de la commande suit:"
					+ "SELECTALL FROM nomRelation\n");
			if(this.heapFiles.get(i).nomRelation().equals(relName)) {
				System.out.println("Records n "+i);
				System.out.println(" "+this.heapFiles.get(i).GetAllRecords());
			
				//System.out.println("Total records "+x);
				
				return this.heapFiles.get(i).GetAllRecords();
				
			}
		}
		
		return null;
		
	}
	
	public ArrayList<Record> select(String relName, int idColonne, String valeur ){
		ArrayList <Record> recordSelectionner = new ArrayList<>();
		boolean recherche = false;
		//Verification de l'existances de la relation
		for(int i = 0; i<this.heapFiles.size(); i++) {
			if(this.heapFiles.get(i).nomRelation().equals(relName))
			{
				if(this.heapFiles.get(i).GetAllRecords().get(i).getValues().get(idColonne).equals(valeur)) {
					
					 recordSelectionner.add(this.heapFiles.get(i).GetAllRecords().get(i));
				}
			
			}
		}
		return recordSelectionner;
		
	}
	
	
}