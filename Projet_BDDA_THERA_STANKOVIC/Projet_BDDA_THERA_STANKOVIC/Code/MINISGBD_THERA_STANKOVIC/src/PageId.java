
public class PageId {
	
	private static int fileIdx ;
	private static int  pageIdx ;
	private static PageId pageId = new PageId();
	
	public PageId(int fileIdx, int pageIdx) {
		this.fileIdx = fileIdx;
		this.pageIdx = pageIdx;
	}
	public PageId() {
		// TODO Auto-generated constructor stub
	}
	
	public void setPageIdx(PageId pageIdx) {
		this.pageId = pageId;
	}
	// les getters et les setters des attributs fileIdx et pageIdx
	public static int getFileIdx() {
		return fileIdx;
	}
	
	public void setFileIdx(int fileIdx) {
		this.fileIdx = fileIdx;
	}
	
	public static int getPageIdx() {
		return pageIdx;
	}
	
	public void setPageIdx(int pageIdx) {
		this.pageIdx = pageIdx;
	}
}