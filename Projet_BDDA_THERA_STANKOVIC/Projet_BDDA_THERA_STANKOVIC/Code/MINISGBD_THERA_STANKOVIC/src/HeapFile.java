import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class HeapFile {
	private RelationInfo relInfo;
	
	//constructeur
	public HeapFile(RelationInfo relInfo) {
		this.relInfo = relInfo; 
	}
	public String nomRelation() {
		return this.relInfo.getNomRelation();
	}
	
	public void createNewOnDisk() throws IOException {
		//Creation du fichier disque correspondant a HeapFile
		int fileIdx = 0;
		int position = this.relInfo.getFileIdx();
		DiskManager.getInstance().CreateFile(position);
		
		//Rajout d'une Header Page vide au fichier
		PageId newPage = new PageId();
		newPage = DiskManager.getInstance().AddPage(position);
		
		//Recuperation de la page dans le buffer manager
		BufferManager.getInstance().getPage(newPage);
		
		//Ecrire pageSize octect de valeur 0 dedans
		//puis écrire pageSize octets de valeur 0 dedans (Personnellement j'ai pas d'idee)

		byte [] buff = null ;
		DiskManager.getInstance().WritePage(newPage, buff);
		
		//faire un free avec le flagdirty 
		BufferManager.getInstance().freePage(newPage,true);
		
	}
	/**
	 * D'abord on lit les informations de la Header Page , ensuite on doit obtenir le HeadPage via le BufferManager
	 * 
	 * @return le pageId d'une page de données sur laquelle il reste des cases libres
	 * @throws IOException
	 */
	public PageId getFreeDataPageId() throws IOException {
		
		int idFichier =  RelationInfo.getInstance().getFileIdx();
		PageId pageId = new PageId(idFichier, 0);
		
		//on recupere le headerPage via le BufferManager 
		byte [] page = BufferManager.getInstance().getPage(pageId);
		
		ByteBuffer bb = null;
		bb.wrap(page);
		
		//Pour pouvoir l'utiliser dans la condition du for
		int nbrPages = bb.getInt(0);
		for(int i = 1 ; i<nbrPages; i++) {
			if (bb.getInt(i*Integer.BYTES)>0) 
			{
				//System.out.println("PageId de la page i "+i);
				//on return le pageId d'une page ou il reste des cases libres
				return new PageId(RelationInfo.getRelationInfo().getFileIdx(), i);
				
			}
			else {
				return null;
			}
			
		}
		//Gestion de l'erreur
		throw new RuntimeException("");
	
	}
	
	

	
	/**
	 *  la méthode doit écrire lenregistrement record dans la page de données identifiée par pageId, et renvoyer son rid
	 * 
	 * @param record
	 * @param pageId
	 * @return son Rid
	 */
	
	public Rid writeRecordToDataPage (Record record, PageId pageId) {
		int i =0;
		//On commence par recuperer le contenu de buffer via le BufferManager
		
		try 
		{
			byte[] page = BufferManager.getInstance().getPage(pageId);
			ByteBuffer bb = ByteBuffer.wrap(page);
			int nbrSlots = RelationInfo.getInstance().getSlotCount();
			//System.out.println("Le nombre de slot"+RelationInfo.getInstance().getSlotCount());
			boolean recherche = false;
			
			//On recherche le slot libre dans la page
			while (i < nbrSlots && !recherche) {
				if(page[i] ==1) {
					recherche = true;
				}
				else {
					i++;
				}
			}
			
			record.writeToBuffer(bb, nbrSlots + i * RelationInfo.getInstance().getRecordSize());
			RelationInfo.getInstance().setSlotCount(nbrSlots - 1);
			page[i]--;
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return new Rid(pageId, i);
		
	}
	
	/**
	 * Cette methode recupère la liste des records stokés dans la page identifiée par pageId
	 *Dans un premier temps on recupère le headerPage dans le BufferManager, ensuite je parcours
	 *le bitMap pour ensuite retourner les records stokés dans la page identifiée
	 * @param pageId
	 * @return la liste des records stokés dans la page identifiée par pageId
	 */
	
	public List <Record>  getRecordsInDataPage (PageId pageId) {
		byte[] page;
		List <Record> pageStokee = new ArrayList<>();
		try {
			page = BufferManager.getInstance().getPage(pageId);
			ByteBuffer bb = null;
			bb.wrap(page);
			
			for(int i=0; i<this.relInfo.getSlotCount(); i++) {
				if (bb.get() == (byte) 1) {
					Record record1  = new Record(this.relInfo);
					record1.readFromBuffer(page, i);
					pageStokee.add(record1);
					
				}
			}
					
				} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		
		return pageStokee;
		
	}
	/**
	 * Methode permettant l'insertion 
	 * Tout d'abord on recupère une page libre ensuite on ecrit le record dans la page recuperer
	 *  
	 * @param record
	 * @return le rid 
	 */
	public Rid InsertRecord(Record record) throws IOException {
		PageId pageLibre = getFreeDataPageId();
		return writeRecordToDataPage(record, pageLibre);
		
	}
	/**
	 * Lister tout les records dans le HeapFile
	 * @return
	 */
	public List <Record> GetAllRecords(){
		//Pour l'identidiant du fichier
		int fileIdx = this.relInfo.getFileIdx();
		
		//On initialise la liste
		ArrayList <Record> listeDeRecords = new ArrayList<>();
		
		//Le nombre de page dans le headerPage
		
		
		//Recuperation de la Header Page
		PageId pageHeader = new PageId (fileIdx , 0);
		
				
		return null;
		
	}
	public RelationInfo getRelInfo() {
		return relInfo;
	}
	public void setRelInfo(RelationInfo relInfo) {
		this.relInfo = relInfo;
	}
	
	
	
	
	

}
