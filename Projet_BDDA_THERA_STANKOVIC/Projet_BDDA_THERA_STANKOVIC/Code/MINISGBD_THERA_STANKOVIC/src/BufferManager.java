
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;
public class BufferManager {
	//Creation de l'instance de la classe BUfferManagement
		private static BufferManager bufferManager = null ;
		
			
		//Contiendra l'ensemble des frames 
		private ArrayList <Frame> bufferPool  ;
		
		//Pour l'application de la politique LRU
		private ArrayList<Frame> pageLru ;
		
		//Constructeur
		public BufferManager(ArrayList <Frame> buffer , ArrayList<Frame> pageLru ) {
			this.bufferPool = buffer;
			this.pageLru = pageLru ;
			
		}
		BufferManager() {
	         // La présence d'un constructeur privé supprime le constructeur public par défaut.
	         // De plus, seul le singleton peut s'instancier lui-même.
			this.bufferPool = new ArrayList <Frame>();
			this.pageLru = new ArrayList <Frame>();
	     }
		public static BufferManager getInstance() {
			if (BufferManager.bufferManager == null) {
	            // Le mot-clé synchronized sur ce bloc empêche toute instanciation
	            // multiple même par différents "threads".
	            // Il est TRES important.
	            synchronized(BufferManager.class) {
	              if (BufferManager.bufferManager == null) {
	            	  BufferManager.bufferManager = new BufferManager();
	              }
	            }
	         }
	         return BufferManager.bufferManager;
		}
		
		/** 
		 * cette methode efface ainsi la frame correspondante de la lru et l'actualise(Systeme LRU)
		 * @param buff
		 * @param pageId
		 * @return
		 * @throws IOException 
		 */
		
		
		public byte[] lru(PageId pageId) throws IOException
		{
			
			Frame frame = pageLru.get(0);
			
			PageId pageASupprimer = frame.getIdDeLaPage();
			
			if(frame.getInstance().getFlagDirty() == 1)
			{
				DiskManager.getInstance().WritePage(pageASupprimer, frame.getBuff());
						
			}
					frame.getInstance().renitialiserFrame();  
					//ensuite on actualise la case
					frame.getInstance().setIdDeLaPage(pageId);
					frame.incrementerPin_count();
					frame.setEstChargee(true);
					DiskManager.getInstance().ReadPage(pageId, frame.getBuff());
					pageLru.remove(0);
					return frame.getBuff();
		}
		/**
		 * Supprime de la liste pageLru la case dont le pin_count est different de 0
		 * @param page est une pageId
		 * @param contenuCase 
		 */
		private void miseAJour(PageId page) {
			//donner une valeur a contenucase dans le for , et faire le remove apres le for et enfin
			Frame frame = new Frame();
			int contenuCase = 0;
			if(pageLru.size() == 0) {
				System.out.println("La page LRU est vide");
				
			}
			if (pageLru.isEmpty())
			{
				System.out.println("La liste est vide");
			
			}

			for(int i = 0 ; i<pageLru.size() ; i++ ) {
				frame = pageLru.get(i);
				
				//si le contenu du frame est egale a la page , on supprime le contenu de la case
				if(frame.getIdDeLaPage().equals(page))
				{
					contenuCase = pageLru.indexOf(frame);
				}
				
			}
			pageLru.remove(contenuCase);
		}
		public byte[] getPage(PageId pageId) throws IOException {
			Frame frame = new  Frame();
			frame.setIdDeLaPage(pageId);
			byte [] readBuffer = new byte[Main.getInstance().pageSize];
			
			for(int i=0 ; i<this.bufferPool.size(); i++) {
				
				//Si l'identifiant de la page est egale à celui de la page
				if(frame.getIdDeLaPage().equals(pageId))
				{
					frame.incrementerPin_count();
					
					//	Supprime de la liste pageLru la case dont le pin_count est different de 0
					miseAJour(pageId);
					
					//Verification du chargement de la case
					frame.setEstChargee(true);
					return frame.getBuff();
				}
				else System.out.println("erreur") ;
			}
				for(int j=0 ; j<this.bufferPool.size(); j++) 
				{
			
				frame = this.bufferPool.get(j);
				//Si l'identifiant de la page est null
				if(frame.getIdDeLaPage() == null) 
				{
					frame.setIdDeLaPage(pageId);
					
					//on incremente le pin count
					frame.incrementerPin_count();
					
					//On lit la case correcpondante
					DiskManager.getInstance().ReadPage(pageId,frame.getBuff());
					
					//Indique si la case a été remplis 
					frame.setEstChargee(true);
					return frame.getBuff();
				}
				}
				
				return lru(pageId);

			
			
			
	}
		
		
		/**
		 * Cette methode dois decrementer le pin_count et actualiser le flag dirty
		 * @param pageId la pageId de la page
		 * @param valdirty
		 */
		
		void freePage(PageId pageId, boolean valdirty) {
			Frame frame = new Frame ();
			for(int i= 0; i< bufferPool.size(); i++) {
				frame = this.bufferPool.get(i);
				if(frame.getInstance().getIdDeLaPage() == null) {
					System.out.println("Aucune case à liberer");
				}
				else if(frame.getInstance().getIdDeLaPage().equals(pageId)){
					if(frame.getInstance().getPin_count() >0) {
						System.out.println("test1");
						//On decremente le pin count si est à 1
						frame.getInstance().decrementerPin_count();
						if(valdirty ==true) {
							frame.getInstance().setDirty(1);
							System.out.println("test2");
						}
						else {
							frame.getInstance().setDirty(0);
							System.out.println("test3");
						}
					}
					}
					if(frame.getInstance().getPin_count() == 0) {
						this.pageLru.add(frame);
						System.out.println("test4");
					}
				}
			}
			
		
		/**
		 * Cette methode s'occupe de l'ecriture dans les pages et la mise à 0 de toutes les infos et contenus des buffers
		 * @throws IOException 
		 */
		void flushAll() throws IOException {
			if(BufferManager.getInstance() != null) {
				
				for(  Frame frame  : this.bufferPool) {
					if(frame.getInstance().getFlagDirty()==1) {
						DiskManager.getInstance().WritePage(frame.getInstance().getIdDeLaPage(), frame.getInstance().getBuff());
						System.out.println("test5");
					}
				}
			}	
		}
		/**
		 * Pour renitiliser tout les valeurs
		 */
		public void rest() {
			for(Frame frame : this.bufferPool) {
				frame.renitialiserFrame();
			}
		}
		
		@Override
		public String toString() {
			return "BufferManager [bufferPool=" + bufferPool + ", pageLru=" + pageLru + "]";
		}
		@SuppressWarnings("null")
		public static void main (String [] args) throws IOException {
			
			//DiskManager.AddPage(6);
			DiskManager.getInstance().CreateFile(11);
			PageId page = new PageId(0,11);
			PageId page2 = new PageId(1,11);
			DiskManager.getInstance().AddPage(0);
			DiskManager.getInstance().AddPage(1);
			//getPage(page);
			// this.getInstance().getPage(page);
			PageId page1 = new PageId(3,11);
			PageId page3 = new PageId(2,11);
			PageId page6= new PageId(4,11);
			DiskManager.getInstance().AddPage(2);
			DiskManager.getInstance().AddPage(3);
			DiskManager.getInstance().AddPage(4);
			Frame f1 = new Frame() ;
			f1.setPageId(page);
			Frame f2 = new Frame() ;
			f2.setPageId(page1);
			Frame f3 = new Frame() ;
			f3.setPageId(page3);
			ArrayList <Frame> bufferPool = new ArrayList <Frame> () ;
			bufferPool.add(f1);
			bufferPool.add(f2);
			bufferPool.add(f3);
			
			Frame f4 = new Frame() ;
			Frame f5 = new Frame() ;
			Frame f6 = new Frame() ;
			Frame f7 = new Frame() ;
;			ArrayList<Frame> pageLru  = new ArrayList <Frame> ();
			pageLru.add(f4);
			pageLru.add(f5);
			pageLru.add(f6);
			pageLru.add(f7);
			BufferManager buffer = new BufferManager(bufferPool, pageLru);
			//System.out.println(""+buffer.getPage(page1).toString());
			System.out.println(buffer.getPage(page).toString());
			//buffer.freePage(page, true);
			System.out.println(buffer.getPage(page1).toString());
			System.out.println(buffer.getPage(page).toString());
			System.out.println(buffer.getPage(page6).toString());
			//buffer.getPage(page);
			//buffer.getPage(page2);
			//buffer.getPage(page6);
			buffer.flushAll();
			
			} 	
		
		
} 
