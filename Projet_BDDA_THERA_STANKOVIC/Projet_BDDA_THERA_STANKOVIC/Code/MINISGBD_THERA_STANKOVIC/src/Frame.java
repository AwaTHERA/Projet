
public class Frame {
	
		private static Frame frame = null ;
	   //le buffer de la case
		private byte [] buff;
		
		//Indicateur de si la page a été chargé dans la page ou pas 
		private boolean estChargee;

		//Identifiant de la page 
		private PageId idDeLaPage ;
		
		//Pin_count de la case
		private int pinCount;
		
		//On va l'utiliser pour copier ou pas une page avant de la remplacer
		private int flagDirty;
		
		//Creation d'une instance pour la classe Frame
		//public static Frame frame = new Frame();
		public Frame getInstance() {
			 //Le "Double-Checked Singleton"/"Singleton doublement vérifié" permet 
	         //d'éviter un appel coûteux à synchronized, 
	         //une fois que l'instanciation est faite.
	         if (Frame.frame == null) {
	            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
	            // multiple même par différents "threads".
	            // Il est TRES important.
	            synchronized(Frame.class) {
	              if (Frame.frame == null) {
	                Frame.frame = new Frame();
	              }
	            }
	         }
	         return Frame.frame;
		}
		
		/**
		 * Constructeur
		 */
		public Frame() {
			buff= new byte[Main.getInstance().pageSize];
			estChargee = false;
			idDeLaPage = new PageId() ;
			pinCount = 0;
			flagDirty = 0 ;
			
		}
		//les getters et setters
		
		public void setPageId(PageId idDeLaPage) {
			this.idDeLaPage  = idDeLaPage ;
		}
		public int getPin_count() {
			return pinCount;
		}
		public void setPin_count(int pin_count) {
			this.pinCount = pin_count;
		}
		
		
		public boolean getEstChargee() {
			return estChargee;
		}
		public void setEstChargee(boolean estChargee) {
			this.estChargee = estChargee ;
		}
		
		
		public int getDirty() {
			return getDirty();
		}
		public void setDirty(int dirty) {
			this.flagDirty = dirty;
		}


		public byte[] getBuff() {
			return buff;
		}


		public void setBuff(byte[] buff) {
			this.buff = buff;
		}


		public PageId getIdDeLaPage() {
			return idDeLaPage;
		}


		public void setIdDeLaPage(PageId idDeLaPage) {
			this.idDeLaPage = idDeLaPage;
		}


		public int getFlagDirty() {
			return flagDirty;
		}


		public void setFlagDirty(int flagDirty) {
			this.flagDirty = flagDirty;
		}
		
		/**
		 *Cette methode permettra de d'incrementer le pin_count
		 */
		public void incrementerPin_count() {
			pinCount += pinCount;

		}
		/**
		 *Cette methode permettra de decrementer le pin_count
		 */
		public void decrementerPin_count() {
			pinCount -= pinCount;
		}

		public void renitialiserFrame() {
			buff = new byte[Main.getInstance().getPageSize()];
			estChargee = false;
			idDeLaPage = null;
			pinCount = 0;
			flagDirty = 0;
			
		}
}
