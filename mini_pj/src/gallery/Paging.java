package gallery;

public class Paging {

	private final int perPage = 8;
	private int pageCount;
	private int boardCount;
	private int offset;
	
	private int page;
	
	private final int perSection = 5;
	private int begin;
	private int end;
	private int section;
	
	private boolean prev;
	private boolean next;

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getBoardCount() {
		return boardCount;
	}

	public void setBoardCount(int boardCount) {
		this.boardCount = boardCount;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getBegin() {
		return begin;
	}

	public void setBegin(int begin) {
		this.begin = begin;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getSection() {
		return section;
	}

	public void setSection(int section) {
		this.section = section;
	}

	public boolean isPrev() {
		return prev;
	}

	public void setPrev(boolean prev) {
		this.prev = prev;
	}

	public boolean isNext() {
		return next;
	}

	public void setNext(boolean next) {
		this.next = next;
	}

	public int getPerPage() {
		return perPage;
	}

	public int getPerSection() {
		return perSection;
	}

	
	public Paging(int page, int boardCount) {	// 요청받은 페이지 번호와 총 게시글 수를 전달 받는다
		this.page = page;
		this.boardCount = boardCount;
		pageCount = boardCount / perPage;		// 총 페이지의 개수
		pageCount += (boardCount % perPage == 0) ? 0 : 1;	// 나머지가 있으면 +1
		
		offset = (page - 1) * perPage;		// 건너뛸 게시글의 개수
		
		section = (page - 1) / 5;				// 현재 페이지가 속하는 페이징 번호의 섹션(1~10, 11~20 ...)
		begin = 5 * section + 1;				// 11, 21, 31 ...
		end = begin + perSection - 1;			// 20, 30, 40 ...
		end = end > pageCount ? pageCount : end;	// 만약 총 페이지 수가 부족하다면 마지막 페이지까지만 (ex. 38)
		
		prev = section != 0;					// 현재 섹션이 0번 섹션이면 이전 페이지는 존재하지 않는다
		next = boardCount > perPage * end;		// 총 게시글이 현재 마지막 섹션 수 * 화면당 게시글 수와 비교하여 적으면 
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}
}
