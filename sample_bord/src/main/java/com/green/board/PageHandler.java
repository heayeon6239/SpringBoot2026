package com.green.board;

public class PageHandler {
	// 멤버변수 파트
	
	// 01. 기본 변수
	private int totalCnt; // 전체 게시글 개수(DB에서 값을 매개변수로 들고옴)
	private int pageNum; // 현재 페이지 번호(Controller에서 매개변수로 값을 지정)
	private int onePageSize; // 한 페이지에서 출력되는 게시글 개수(Controller에서 매개변수로 값을 지정)
	private int pageBlock = 3; // 한번에 보여줄 페이지 개수 3개로 지정(1~3)
	
	// 02. DB조회 변수
	private int startRow; // 화면에 출력할 게시글의 DB 시작 위치
	private int endRow; // = pageSize
	
	// 03. pageBlock 부분 : [1][2][3], [4][5][6]
	private int totalPage; // 전체 페이지 수
	private int startBlockPage; // 한번에 보여줄 페이지 블럭의 시작 번호 = [1],[4]...
	private int endBlockPage; // 한번에 보여줄 페이지 블럭의 끝 번호 = [3],[6]...
	
	// 04. 이전, 다음 버튼
	private boolean prev; // ◀ 이전
	private boolean next; // ▶ 다음
	
	// ---------------------------------------------------------------------
	
	// 매개변수가 존재하는 기본 생성자(가장 먼저 실행되는 부분)
	// 매개변수로 (전체 게시글 개수, 현재 페이지 번호, 한 페이지에 출력될 게시글 개수)를 받음
	public PageHandler(int totalCnt, int pageNum, int onePageSize) {
		this.totalCnt = totalCnt;
		this.pageNum = pageNum;
		this.onePageSize = onePageSize;
		
		// 계산 함수 호출(PageHandler가 실행되자마자 계산 함수도 함께 바로 실행)
		calcPaging();
	}
	
	// ---------------------------------------------------------------------
	
	// 페이지 계산 메서드
	public void calcPaging() {
		// 01. 전체 페이지 수(게시글 수 변경에 따라 전체 페이지 수도 변경) ???????????????
		// ★ Math.ceil() : 무조건 반올림하여 정수로 출력시키는 메서드
		// 전체 게시글 수 ÷ 한 페이지에 출력되는 게시글 수 -> 나머지까지 반올림한 몫 : 총 페이지 수
		totalPage = (int)Math.ceil(totalCnt / (double)onePageSize); 
		// ※ 12개의 게시글을 5개로 나누면 2.4 int로 바로 2가 되는 문제가 존재
		// (1) totalCnt나 pageSize 둘중에 하나를 double로 명시적 형변환시켜,
		//     나누었을 때 나머지가 존재할 수 있도록 소수자리를 만들어줌
		// (2) totalPage는 int타입이므로, double타입인 결과값을 다시 int로 명시적 형변환해줌
		
		// 02. DB 조회하는 범위
		// 1페이지 -> 0번째 게시글 부터 5개(0~4)
		// 2페이지 -> 5번째 게시글 부터 5개(5~9)
		// 3페이지 -> 10번째 게시글 부터 5개(10~14) ...
		startRow = (pageNum-1)*onePageSize; // ex) 2번째 페이지 -> (2-1)*5 = 5, 5번째 게시글부터 5개 출력
		endRow = onePageSize; // 5개
		
		// 03. 블럭 페이지의 시작과 끝 [1][2][3], [4][5][6]
		
		// startBlockPage : [1],[4],[7],[10]... 
		// ((4-1)÷3)×3 -> startBlockPage 이전의 페이지가 몇개 있는지
		// 페이지 블럭의 시작 페이지 번호 = ((현재페이지 - 1) ÷ 한번에 보여줄 페이지 블럭 수) × 한번에 보여줄 페이지 블럭 수 + 1
		// 현재 1페이지 : ((1-1)÷3)×3+1 = [1] 부터 출력, 현재 페이지가 1,2,3 -> 3으로 나누었을 때 0×3+1=1
		// 현재 2페이지 : ((2-1)÷3)×3+1 = [1] 부터 출력
		// 현재 4페이지 : ((4-1)÷3)×3+1 = [4] 부터 출력, 현재 페이지가 4,5,6 -> 3으로 나누었을 때 1×3+1=2
		// 현재 7페이지 : ((7-1)÷3)×3+1 = [7] 부터 출력, 현재 페이지가 7,8,9 -> 3으로 나누었을 때 2×3+1=3
		
		startBlockPage = ((pageNum-1)/pageBlock)*pageBlock+1;
		
		// endPage : [3],[6],[9]...
		// 페이지 블럭의 끝 페이지 번호 = 페이지블럭의 시작 페이지 번호 + (한번에 보여줄 페이지 블럭 수 - 1)
		// 현재 1페이지 : 1+(3-1) = 3
		// 현재 4페이지 : 4+(3-1) = 6
		// 현재 7페이지 : 7+(3-1) = 9
		endBlockPage = startBlockPage + (pageBlock - 1);
		
		// 실제로는 [1]~[8](totalPage : 전체 페이지 수)까지 출력되어야 하는데, 
		// 위 계산식으로는 [9](endBlockPage : 페이지 블럭의 끝 페이지 번호)까지 다 출력됨
		if(endBlockPage > totalPage) {
			endBlockPage = totalPage;
		}
		
		//04. 이전/다음 페이지 버튼(비교 연산자를 사용하면 결과값은 boolean으로만 나옴)
		prev = startBlockPage > 1; 
		next = endBlockPage < totalPage;
	}
	
	// ---------------------------------------------------------------------

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

	public int getOnePageSize() {
		return onePageSize;
	}

	public void setOnePageSize(int pageSize) {
		this.onePageSize = pageSize;
	}

	public int getPageBlock() {
		return pageBlock;
	}

	public void setPageBlock(int pageBlock) {
		this.pageBlock = pageBlock;
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

	public int getStartBlockPage() {
		return startBlockPage;
	}

	public void setStartBlockPage(int startBlockPage) {
		this.startBlockPage = startBlockPage;
	}

	public int getEndBlockPage() {
		return endBlockPage;
	}

	public void setEndBlockPage(int endBlockPage) {
		this.endBlockPage = endBlockPage;
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
