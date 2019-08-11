 /**
 * @Class Name : SearchVO.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 7. 17.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 7. 17. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.cmn;

/**
 * @author sist
 *
 */
public class SearchVO extends DTO{
	private int    pageSize   ;//지정할 페이지 사이즈:10
	private int    pageNum    ;//현재 페이지 넘버:1
	private String searchDiv  ;//검색구분:아이디,이름,주민등록번호,이메일 등등
	private String searchWord ;//검색어
	
	public SearchVO(){
		
	}
	/**
	 * 
	 * @param pageSize   : 지정할 페이지 사이즈:10 
	 * @param pageNum    : 현재 페이지 넘버:1               
	 * @param searchDiv  : 검색구분:아이디,이름,주민등록번호,이메일 등등 
	 * @param searchWord : 검색어                       
	 */
	public SearchVO(int pageSize, int pageNum, String searchDiv, String searchWord) {
		super();
		this.pageSize = pageSize;
		this.pageNum = pageNum;
		this.searchDiv = searchDiv;
		this.searchWord = searchWord;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public String getSearchDiv() {
		return searchDiv;
	}
	public void setSearchDiv(String searchDiv) {
		this.searchDiv = searchDiv;
	}
	public String getSearchWord() {
		return searchWord;
	}
	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}
	@Override
	public String toString() {
		return "SearchVO [pageSize=" + pageSize + ", pageNum=" + pageNum + ", searchDiv=" + searchDiv + ", searchWord="
				+ searchWord + ", toString()=" + super.toString() + "]";
	}
	
	

	
	
	
	
}
