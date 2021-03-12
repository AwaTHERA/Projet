import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

//contient les infos de schéma pour l'ensemble de la bdd
public class DBInfo implements Serializable {
	ArrayList <RelationInfo> tab = new ArrayList<RelationInfo>();
	private static DBInfo DBInfo = new DBInfo();
	private DBInfo() {
		
	}
	public static DBInfo getInstance() {
		return DBInfo;
	}

	private int compteurRelations=0 ;
	

	public  void init() throws IOException {
		File fichier = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/catalogue.def");
		RandomAccessFile file = new RandomAccessFile (fichier , "rw");
		FileReader fReader = new FileReader(fichier.getAbsoluteFile());
		BufferedReader bReader = new BufferedReader(fReader);
		String ligne = null;
		System.out.print("On travaille sur le fichier : " + fichier.getName() + "\n");
		while ((ligne = bReader.readLine()) != null) {
			if(ligne.startsWith("RelationInfo")){
				String l = ligne.replaceAll(",", "");
				String m =l.replaceAll("\\[", "");
				String n = m.replaceAll("\\]", "");
				String o = n.replaceAll("nomRelation=", "");
				String p = o.replaceAll("nbrColonne=", "");
				String q = p.replaceAll("nomColonne=", "");
				String r = q.replaceAll("typeColonne=", "");
				//System.out.println(r);
				String [] tabRel = r.split(" ");
				
				//for(int i = 0 ; i<tabRel.length ; i++) {
				//System.out.println(tabRel[i]);
				//}
				String nomRel = tabRel[1];
				//ArrayList <RelationInfo> tab ;
				int nbrColonne = Integer.parseInt(tabRel[2]);
				//System.out.println(nbrColonne);
				ArrayList <String> nomColonne = new ArrayList <String>();
				ArrayList <String> typeColonne = new ArrayList <String>() ;
				for(int i = 3 ;i <(nbrColonne+3) ; i++) {
					nomColonne.add(tabRel[i]);
					typeColonne.add(tabRel[i+nbrColonne]);
				}
				RelationInfo rel = new RelationInfo (nomRel,nbrColonne,nomColonne, typeColonne);
				this.tab.add(rel);
			}
			if(ligne.startsWith("compteurRelations")) {
				String l = ligne.replaceAll(":", "");
				//System.out.println(l);
				String [] tabCompteur = l.split(" ");
				this.setCompteurRelations(Integer.parseInt(tabCompteur[1]));
			}
			
		}
		
	}
	
	public void finish() throws IOException {
		File fichier = new File("/home/stankovic/Projet_BDDA_THERA_STANKOVIC/Projet_BDDA_THERA_STANKOVIC/DB/catalogue.def");
		RandomAccessFile file = new RandomAccessFile (fichier , "rw");
		FileWriter fWriter = new FileWriter(fichier);
		BufferedWriter bWriter = new BufferedWriter(fWriter) ;
		for(RelationInfo rel : tab) {
			bWriter.write(rel.toString()+"\n");
		}
		bWriter.write("compteurRelations :"+this.getCompteurRelations());
		bWriter.close();
	}
	public void AddRelation(RelationInfo ajout) {
		int n;
		Scanner sc = new Scanner(System.in);
		n=sc.nextInt();
		for(int i=0; i<n; i++) {
		tab.add(i, ajout);
		}
		compteurRelations++ ;
	}
	public int getCompteurRelations() {
		return compteurRelations;
	}
	public void setCompteurRelations(int compteurRelations) {
		this.compteurRelations = compteurRelations;
	}
	public ArrayList<RelationInfo> getTab() {
		return tab;
	}
	public void setTab(ArrayList<RelationInfo> tab) {
		this.tab = tab;
	}
	public static DBInfo getDBInfo() {
		return DBInfo;
	}
	public static void setDBInfo(DBInfo dBInfo) {
		DBInfo = dBInfo;
	}
	public void reset() {
		this.compteurRelations= 0;
		this.tab.clear();
	}

	
}
