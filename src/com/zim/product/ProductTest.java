 /**
 * @Class Name : ProductTest.java
 * @Description : 
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2019. 7. 16.           최초생성
 *
 * @author Zimzalabim
 * @since 2019. 7. 16. 
 * @version 1.0
 * @see
 *
 *  Copyright (C) by HR. KIM All right reserved.
 */
package com.zim.product;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.zim.cmn.SearchVO;
import com.zim.cmn.SearchVO2;
import com.zim.product.ProductVO;
import com.zim.product.listall.ListAllSearchVO;


/**
 * @author SIST
 *
 */
public class ProductTest {

	private final Logger LOG = Logger.getLogger(ProductTest.class);
	
	private ProductVO vo01;
	private ProductVO vo02;
	private ProductVO vo03;
	
	private ProductDao dao;
	
	
	
	public ProductTest(){
		vo01 = new ProductVO("","이미지01","001","상품01","","","","","내용01","공지01","","","","","짐살라빔01","","2500","");
		vo02 = new ProductVO("","이미지02","002","상품02","","","","","내용02","공지02","","","","","짐살라빔02","","2500","");
		vo03 = new ProductVO("","이미지03","003","상품03","","","","","내용03","공지03","","","","","짐살라빔03","","2500","");
		
		dao = new ProductDao();
	}
	
	public void do_totalSearch(){
		ListAllSearchVO vo = new ListAllSearchVO();
		vo.setPageSize(10);
		vo.setPageNum(1);
		vo.setSearchWord("공동구매");
		LOG.debug("=================");
		LOG.debug("param"+vo);
		LOG.debug("=================");
		dao.do_totalSearch_select(vo);
	}
	
	
	public void do_notice_select(){
		vo01.setProductNo("24");
		ProductVO outVO = (ProductVO) dao.do_notice_select(vo01);
		LOG.debug("=================");
		LOG.debug("outVO"+outVO);
		LOG.debug("=================");
		
	}
	
	
	
	
	public void do_detail_select(){
		vo01.setProductNo("24");
		ProductVO outVO = (ProductVO) dao.do_detail_select(vo01);
		LOG.debug("=================");
		LOG.debug("outVO"+outVO);
		LOG.debug("=================");
	}

	
	
	public void do_latest_select(){
		List<ProductVO> list = (List<ProductVO>) dao.do_latest_select();		
		LOG.debug("==============");
		for(ProductVO vo:list){
			LOG.debug("vo"+vo);
		}
		LOG.debug("==============");
	}
	
	
	

	
	
	public void do_imminent_select(){
		List<ProductVO> list = (List<ProductVO>) dao.do_imminent_select();		
		LOG.debug("==============");
		for(ProductVO vo:list){
			LOG.debug("vo"+vo);
		}
		LOG.debug("==============");
	}
	
	
	public void do_notice_update(){
		vo01.setProductNo("59");
		vo01.setRegId("아이디059");
		vo01.setNotice("공지수정테스트");
		int flag = dao.do_notice_update(vo01);
		LOG.debug("---------");
		LOG.debug("flag:"+flag);
		LOG.debug("---------");
	}
	public void retrieve(){
		SearchVO sVO = new SearchVO(10, 1, "", "아이디031");
		List<ProductVO> rala1 = dao.do_retrieve(sVO);
		LOG.debug("---------");
		LOG.debug("rala1 : "+rala1);
		LOG.debug("---------");
	}
	public void retrieve2(){
		SearchVO sVO = new SearchVO(10, 1, "", "아이디031");
		List<ProductVO> rala2 = dao.do_retrieve2(sVO);
		LOG.debug("---------");
		LOG.debug("rala2 : "+rala2);
		LOG.debug("---------");
	}
	
	
	/**
	 * @Method Name  : main
	 * @작성일   : 2019. 7. 16.
	 * @작성자   : SIST
	 * @변경이력  : 최초작성
	 * @Method 설명 :
	 * @param args
	 */
	public static void main(String[] args) {
		ProductTest productTest = new ProductTest();
		//productTest.do_imminent_select();
		//productTest.do_latest_select();
		//productTest.do_detail_select();
		//productTest.do_notice_select();
		//productTest.do_notice_update();
		//productTest.retrieve2();
		productTest.do_totalSearch();
	}
	
	
	
	
	
	

}
