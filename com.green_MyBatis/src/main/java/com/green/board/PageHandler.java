package com.green.board;

// 페이징을 하기위한 계산식을 가지고있는 클래스
public class PageHandler {
	
	// 01. 기본 변수
	private int totalCnt; // 전체 게시글 개수
	private int pageNum; // 현재 페이지 번호
	private int pageSize; // 한 페이지에 보여줄 레코드(=행) 개수
	private int pageBlock = 3; // 한 화면의 페이지 묶음(1~3)
	
	// 02. DB조회 변수
	// Limit 1(startRow), 5(pageSize) => 1부터 시작해서 5개만 출력(※ 1부터 5까지 아님)
	private int startRow; // DB의 시작 위치
	private int endRow; // 가져올 게시글 개수 = pageSize
	
	// 03. PageBlock 부분 : [1][2][3], [4][5][6]
	private int totalPage; // 전체 페이지 수
	private int startPage; // 블럭페이지의 시작 번호 = [1]
	private int endPage; // 블럭페이지의 끝 번호 = [3]
	
	private boolean prev; // ◀ 이전
	private boolean next; // ▶ 다음
	
	// 생성자
	public PageHandler(int totalCnt, int pageNum, int pageSize) {
		this.totalCnt = totalCnt;
		this.pageNum = pageNum;
		this.pageSize = pageSize;
		
		// 계산함수 호출할 예정
		calcPaging();
	}
	
	// 페이지 계산하는 메서드
	public void calcPaging() {
		// 전체 페이지수(= totalPage)
		// 게시글 개수에 따른 증가/감소
		// 한 페이지에 5개씩, 총 게시글 11개 -> 블럭 3개 
		// 11/5 => int(2.2) -> 2 (사용X) : 소수자리까지 모두 반올림되어야 페이지가 추가로 생성됨
		// ★ Math.ceil() : 소수점을 무조건 반올림하여 정수를 출력하는 메서드
		totalPage = (int)Math.ceil(totalCnt / (double)pageSize);
		
		// DB 조회하는 범위 중 첫번째
		// 1페이지 -> 0부터 5개(0~4)
		// 2페이지 -> 6부터 5개(5~9)
		// 3페이지 -> 10부터 5개
		// pageNum = 1 : 현재 페이지 번호
		startRow = (pageNum-1)*pageSize;
		endRow = pageSize;
		
		// pageBlock =3 블럭페이지의 시작 / 끝
		// [1][2][3], [4][5][6]
		// pageNum = 1, pageBlock = 3 : (1-1)/3 = 0(int라서 0)
		// 0 * 3 = 0 + 1 -> 1
		// pageNum이 1또는 2또는 3이어도 startPage는 1이 출력되어야 함
		// 2-1 = int(1/3) => 0, 0*3 = 0, 0+1 => 시작페이지 : 1
		// 3-1 = int(2/3) => 0, 0*3 = 0, 0+1 => 시작페이지 : 1
		// 4-1 = int(3/3) => 1, 1*3 = 3, 3+1 => 시작페이지 : 4
		startPage = ((pageNum -1) / pageBlock) * pageBlock + 1;
		endPage = startPage + (pageBlock -1);
		
		// 실제 페이지는 [1] ~[8](전체 페이지 수 : totalPage)까지만 출력되어야 하는데, 위의 계산식으로는 무조건 [1] ~ [9]까지 출력됨
		// 이런 경우 가장 마지막 페이지를 강제로 endPage에 totalPage를 담아줌
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		
		// 이전/다음 페이지 버튼 여부(boolean을 이용해서 이전의 게시글이 존재하면 true, 없으면 false)
		prev = startPage > 1;
		next = endPage < totalPage; // endPage = 8, totalPage = 9 => true;
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
