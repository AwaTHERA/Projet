import java.util.ArrayList;
import java.util.List;
public class RelationInfo {
	
	private static RelationInfo INSTANCE = null ;
	private static RelationInfo relationInfo;
	RelationInfo() {
		
	}
	public static RelationInfo getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new RelationInfo();
			return INSTANCE;
		} else
			return INSTANCE;
	}
	private String nomRelation;
	private int nbrColonne;
	private ArrayList <String> nomColonne;
	private ArrayList <String> typeColonne ;
	private int fileIdx;
	private int recordSize;
	private int slotCount;
	/**
	 * Constructeur
	 * @param nomRelation
	 * @param nbrColonne
	 * @param nomColonne
	 * @param typeColonne
	 */
	public RelationInfo(String nomRelation,int nbrColonne, ArrayList <String> nomColonne, ArrayList <String> typeColonne  ) {
		this.nomRelation =nomRelation;
		this.nbrColonne = nbrColonne;
		this.nomColonne = nomColonne;
		this.typeColonne = typeColonne;
		
	}
	public RelationInfo(String nomRelation,int nbrColonne, List<String> nomsColonnes, List <String> typeColonne) {
		this.nomRelation = nomRelation;
		this.nbrColonne = nbrColonne;
		this.nomColonne = new ArrayList<>();
		this.nbrColonne = nbrColonne;
		this.typeColonne = new ArrayList<>();
	}
	public RelationInfo(String nomRelation,int nbrColonne, List <String> nomColonne, List <String> typeColonne, int fileIdx, int recordsize, int slotCount) {
		this.nomRelation =nomRelation;
		this.nbrColonne = nbrColonne;
		this.nomColonne = new ArrayList<>();
		this.typeColonne = new ArrayList<>();
		this.fileIdx = fileIdx;
		this.recordSize = recordSize;
		this.slotCount = slotCount;
	}
	//Les getters & setters
	
	public static RelationInfo getRelationInfo() {
		return getRelationInfo();
	}
	public static RelationInfo getINSTANCE() {
		return INSTANCE;
	}
	public static void setINSTANCE(RelationInfo iNSTANCE) {
		INSTANCE = iNSTANCE;
	}
	public int getFileIdx() {
		return fileIdx;
	}
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	public int getRecordSize() {
		return recordSize;
	}
	public void setRecordSize(int recordSize) {
		this.recordSize = recordSize;
	}
	public int getSlotCount() {
		return slotCount;
	}
	public void setSlotCount(int slotCount) {
		this.slotCount = slotCount;
	}
	public void setRelationInfo(RelationInfo relationInfo) {
		RelationInfo.relationInfo = relationInfo;
	}
	public String getNomRelation() {
		return nomRelation;
	}
	public void setNomRelation(String nomRelation) {
		this.nomRelation = nomRelation;
	}
	public int getNbrColonne() {
		return nbrColonne;
	}
	public void setNbrColonne(int nbrColonne) {
		this.nbrColonne = nbrColonne;
	}
	public ArrayList<String> getNomColonne() {
		return nomColonne;
	}
	public void setNomColonne(ArrayList<String> nomColonne) {
		this.nomColonne = nomColonne;
	}
	public ArrayList<String> getTypeColonne() {
		return typeColonne;
	}
	public void setTypeColonne(ArrayList<String> typeColonne) {
		this.typeColonne = typeColonne;
	}
	@Override
	public String toString() {
		return "RelationInfo [nomRelation=" + nomRelation + ", nbrColonne=" + nbrColonne + ", nomColonne=" + nomColonne
				+ ", typeColonne=" + typeColonne + "]";
	}

}
