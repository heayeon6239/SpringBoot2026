package reviewboard.dto;

// 페이징 핸들러
public class PageHandler {
	
	// 멤버변수
	// 01. 기본 변수
	private int totalCnt; // 전체 게시글 개수
	private int pageNum; // 현재 페이지 번호
	private int pageSize; // 한 페이지에 보여줄 게시글 개수
	private int pageSet=3; // 한번에 보여줄 페이지 묶음(1-3)
	
	// 02. DB조회 변수(limit start,pageSize(end)) 몇번째 리뷰부터 몇개까지 보여줄지
	private int startRow; // 각 페이지에서 시작되는 게시글의 DB번호
	private int endRow; // 한 페이지에 보여줄 게시글의 개수 (=pageSize)
	
	// 03. PageSet 부분 : [1][2][3], [4][5][6]
	private int totalPage; // 전체 페이지 개수
	private int startPage; // 페이지 세트에서 시작하는 페이지 번호
	private int endPage; // 페이지 세트에서 끝나는 페이지 번호
	
	// 04. 이전, 다음 버튼
	private boolean prev; // 이전
	private boolean next; // 다음
	
	// 매개변수 생성자
	public PageHandler(int totalCnt, int pageNum, int pageSize) {
		this.totalCnt = totalCnt;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		
		// 생성되면서 계산함수 바로 실행되도록 호출
		calcPaging();
	}
	
	// 페이지 계산 메서드
	public void calcPaging() {
		// (1) 전체 페이지
		// double로 형변환 안해주면 나머지 소수점을 그냥 버림
		totalPage = (int) Math.ceil( totalCnt / (double)pageSize);
		
		// (2) DB 조회하는 범위 (limit 몇번부터 보여주고, 얼마만큼 보여줄건지)
		// startRow 0,5,10,15 ... 
		startRow = (pageNum - 1) * pageSize;
		// endRow 4,9,14,19 ...
		endRow = pageSize;
		
		// pageSet
		startPage = ((pageNum - 1) / pageSet) * pageSet + 1;
		endPage = startPage + (pageSet - 1);
		
		// 게시글 유무에 따른 페이징 조절
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 이전/다음 페이지 버튼 여부(boolean 이용)
		prev = startPage > 1;
		next = endPage < totalPage;
	}
	

	public int getTotalCnt() {
		return totalCnt;
	}

	public void setTotalCnt(int totalCnt) {
		this.totalCnt = totalCnt;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageSet() {
		return pageSet;
	}

	public void setPageSet(int pageSet) {
		this.pageSet = pageSet;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
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
}
