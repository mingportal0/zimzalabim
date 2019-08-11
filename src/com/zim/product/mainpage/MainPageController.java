package com.zim.product.mainpage;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.zim.cmn.DTO;
import com.zim.cmn.StringUtil;
import com.zim.product.ProductDao;
import com.zim.product.ProductVO;

/**
 * Servlet implementation class MainPageController
 */
@WebServlet(description = "메인페이지관리", urlPatterns = { "/mainpage/mainpage.do" })
public class MainPageController extends HttpServlet {
       
	private final Logger LOG=Logger.getLogger(ProductDao.class);
	
	private MainPageService mainPageService;
	
    public MainPageController() {
    	mainPageService = new MainPageService();
    }



  	
  	//최근등록조회
    public void do_latest_select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	LOG.debug("03.1 do_latest_select");
    	ProductVO inVO = new ProductVO();
    	
		List<ProductVO> list = (List<ProductVO>) this.mainPageService.do_latest_select();
		LOG.debug("----------------------");
		for(ProductVO vo: list){
			LOG.debug(vo);
		}
		LOG.debug("----------------------");
		
	
		request.setAttribute("list", list);
		request.setAttribute("paramVO", inVO);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainpage/mainpage_view.jsp");
		dispatcher.forward(request, response);
  	}
  	
    
    
    
  	//베스트 상품 조회
    public void do_best_select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	LOG.debug("03.1 do_best_select");
    	ProductVO inVO = new ProductVO();
    	
		List<ProductVO> list = (List<ProductVO>) this.mainPageService.do_best_select(inVO);
		LOG.debug("----------------------");
		for(ProductVO vo: list){
			LOG.debug(vo);
		}
		LOG.debug("----------------------");
	
		request.setAttribute("list", list);
		request.setAttribute("paramVO", inVO);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainpage/mainpage_view.jsp");
		dispatcher.forward(request, response);
  	}
  	
    
    
  	//마감임박
    public void do_imminent_select(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	LOG.debug("03.1 do_imminent_select");
    	ProductVO inVO = new ProductVO();
    	
		List<ProductVO> list = (List<ProductVO>) this.mainPageService.do_imminent_select();
		LOG.debug("----------------------");
		for(ProductVO vo: list){
			LOG.debug(vo);
		}
		LOG.debug("----------------------");
		
	
		request.setAttribute("list", list);
		request.setAttribute("paramVO", inVO);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainpage/mainpage_view.jsp");
		dispatcher.forward(request, response);
    	
    	
  	}
    
    
    public void do_move_to_main(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		LOG.debug("03 do_move_to_main");
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/mainpage/mainpage_view.jsp");
		dispatcher.forward(request, response);
    	
    }
    
    
    
    public void doServiceHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
    	//기능 : do_retrieve, do_insert, do_update, do_selectOne, do_delete
    	//work_div
    	LOG.debug("02 doServiceHandler()");
    	request.setCharacterEncoding("UTF-8"); 	//리퀘스트 설정하기
    	
    	
    	//work_div : read
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
    	LOG.debug("02.1 workDiv:"+workDiv);
    	
    	
    	//Switch문으로 분기처리
    	switch(workDiv){
    		//메인으로 이동
	    	case "do_move_to_main":
	    		do_move_to_main(request,response);
			break;
    	
    		case "do_latest_select":
    			do_latest_select(request,response);
    		break;
    		
    		case "do_best_select":
    			do_best_select(request,response);
    		break;
    		
    		case "do_imminent_select":
    			do_imminent_select(request,response);
    		break;
    	}
    	
    }
    
    
    
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("01 doGet()");
		LOG.debug("01.1 mainPageService:"+mainPageService);
		doServiceHandler(request, response);
	}


	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LOG.debug("01 doPost()");
		LOG.debug("01.1 mainPageService:"+mainPageService);
		doServiceHandler(request, response);
	}

}
