
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Record {
	//la relation à laquelle ppartient le record
		private RelationInfo relInfo;
		
		//la liste correspondant aux valeurs du record	
		private List <String> values;
		
		/** Constructeur qui prend en param une relInfo 
		 * @param relInfo
		 */
		public Record(RelationInfo relInfo) {
			this.relInfo = relInfo;
			this.values = new ArrayList<String>();
		
		}
		public Record(RelationInfo relInfo, List<String> values) {
			this.relInfo = relInfo;
			values = new ArrayList<>();
		}
		
		/**
		 * 
		 * @param buff
		 * @param position
		 */
		
		public void writeToBuffer(ByteBuffer buff,  int position) {
			//Definition du type pour quon puisse evaluer si l'on doit le convertir ou pas
		
			
			//On se place sur la position ensuite
			buff.position(position);
			
			//on va pouvoir verifier les types et ecrire dans les records dans le buffer 
			for(int i =0; i<values.size(); i++) 
			{
				//Verification de : si c'est un int , si oui, on le convertir en Integer avc un attribut intermediaire
				if(relInfo.getTypeColonne().get(i).equals("int")) {
					//Definition de la variable intermediaire pour faire la convertion en Integer
					int convertion = Integer.parseInt(values.get(i));
					
					//on le met ensuite dans le buffer
					buff.putInt(convertion);
				}
				//Verification de : si c'est un float , si oui , on le met dans le buffer
				if(relInfo.getTypeColonne().get(i).equals("Float")) {
					//Definition de la variable intermediaire pour faire la convertion en Float
					float convertion = Float.parseFloat(values.get(i));
					
					//On le met ensuite dans le buffer
					buff.putFloat(convertion);
				}
				
				//Si c'est un String
				int j = 0;
				String valeur = values.get(j);
				for( j = 0; j <valeur.length() ; j++) {
					buff.putChar(valeur.charAt(j));
				}
			
			
			
			}
		}
		/**
		 * On commence d'abord par calculer la position de lecture , qu'on utilisera dans un ByteBuffer 
		 * Par la suite , on utilise un  StringBuilder pour la recuperation de la chaine
		 */
		public void readFromBuffer(byte [] buff, int position)
		{	
			
			//Calcul de la position de lecture 
			int positionDeLaLecture = RelationInfo.getInstance().getSlotCount() + (position * RelationInfo.getInstance().getRecordSize());
			
			ByteBuffer bb = ByteBuffer.wrap(buff, positionDeLaLecture, buff.length-positionDeLaLecture);
			StringBuilder c = new StringBuilder ();
			int i = 0;
			String valeur = relInfo.getTypeColonne().get(i);
			for(i=0; i < valeur.length() ; i++) {
				//On va recuperer la taille de la chaine
				String str = RelationInfo.getInstance().getTypeColonne().get(i).replace("string", "");
				
				// on lit et ajoute caracactère par caractère
				c.append((char) (bb.getChar()));
			}
			
			//metttre le contenu de StringBuffer dans la liste
			values.add(bb.toString());
		}
		
		//les getters and setter
		public RelationInfo getRelInfo() {
			return relInfo;
		}
		public void setRelInfo(RelationInfo relInfo) {
			this.relInfo = relInfo;
		}
		public List<String> getValues() {
			return values;
		}
		public void setValues(List<String> values) {
			this.values = values;
		}
	
	@Override
	public String toString() {
		return "Record [relInfo=" + relInfo + ", values=" + values + "]";
	}
	public static void main(String [] args) {
		String nomRel = "relation";
		ArrayList <String> nomCol = new ArrayList <String> ();
		ArrayList<String> typeCol = new ArrayList <String> ();
		nomCol.add("11");
		nomCol.add("2");
		nomCol.add("4");
		nomCol.add("10");
		typeCol.add("int");
		typeCol.add("int");
		typeCol.add("int");
		typeCol.add("int");
		nomCol.size();
		ByteBuffer buffer = ByteBuffer.allocate(11);
		byte [] buff = null ;
		
		RelationInfo rel = new RelationInfo (nomRel,nomCol.size(),nomCol, typeCol);
		Record record = new Record( rel);
		record.writeToBuffer(buffer, 0);
		
		//record.readFromBuffer(buffer, 0);
		System.out.println(""+buffer.toString());

		System.out.println("Original ByteBuffer:  " + Arrays.toString(buffer.array())); 
	

		//System.out.println(buffer.get(13));
		
		System.out.println(record.toString());
		record.readFromBuffer(buff, 2);
		System.out.println("Original ByteBuffer:  " + Arrays.toString(buffer.array()));
		System.out.println(record.toString());
	}
}