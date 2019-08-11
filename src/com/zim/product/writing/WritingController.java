package com.zim.product.writing;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.omg.CORBA.Request;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.sun.jimi.core.Jimi;
import com.sun.jimi.core.JimiUtils;
import com.zim.cmn.MessageVO;
import com.zim.cmn.SearchVO;
import com.zim.cmn.StringUtil;
import com.zim.code.CodeDao;
import com.zim.code.CodeVO;
import com.zim.product.ProductDao;
import com.zim.product.ProductVO;
import com.zim.product.image.ImageDao;
import com.zim.product.image.ImageService;
import com.zim.product.image.ImageVO;
import com.zim.product.listall.ListAllSearchVO;

import oracle.net.aso.p;

/**
 * Servlet implementation class WritingController
 */
@WebServlet(description = "게시글관리", urlPatterns = { "/product/writing.do", "/product/writing.json" })
public class WritingController extends HttpServlet {
	private final Logger LOG = Logger.getLogger(WritingController.class);
    private WritingService service;
    private ImageService imageService;
    String savePath = "";

    public WritingController() {
    	LOG.debug("0-------------------");
    	service = new WritingService();
    	LOG.debug("-service-"+service);
    	LOG.debug("0-------------------");
    	imageService = new ImageService();
    }
    
    public void do_insert(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	LOG.debug("03.1 do_write");
    	
    	ProductVO inVO = new ProductVO();
    	String productNm = StringUtil.nvl(request.getParameter("product_nm"), "");
    	String price = StringUtil.nvl(request.getParameter("price"), "");
    	String category = StringUtil.nvl(request.getParameter("category"), "");
    	String contents = StringUtil.nvl(request.getParameter("contents"), "");
    	String targetCnt = StringUtil.nvl(request.getParameter("target_cnt"), "");
    	String deadline = StringUtil.nvl(request.getParameter("deadline"), "");
    	String regId = StringUtil.nvl(request.getParameter("reg_id"), "");
    	String deliveryPrice = StringUtil.nvl(request.getParameter("delivery_price"), "");

    	contents = StringUtil.contentsReplace(contents);
    	
    	inVO.setProductNm(productNm);
    	inVO.setPrice(price);
    	inVO.setCategory(category);
    	inVO.setContents(contents);
    	inVO.setTargetCnt(targetCnt);
    	inVO.setDeadline(deadline);
    	inVO.setRegId(regId);
    	inVO.setDeliveryPrice(deliveryPrice);
    	LOG.debug("03. param:"+inVO);
    	
    	int flag = this.service.do_insert(inVO);
    	
    	
    	//첨부된 이미지의 원본파일명
    	List<String> orgFileNameList = StringUtil.originalFileName(contents);
    	//첨부된 이미지의 경로
    	List<String> savePathList = StringUtil.imageSavePath(contents);
    	//첨부된 이미지의 확장자명
    	List<String> extNameList = StringUtil.extName(contents);    	
    	//첨부된 이미지의 갯수
    	int imageCnt = orgFileNameList.size();
    	
    	for(int i = 0; i<imageCnt; i++){
    		if(flag == 1){
    			do_image_insert(orgFileNameList.get(i), savePathList.get(i), extNameList.get(i));
        	}
    	}
    	String ip = StringUtil.getIP();
		String path = "http://"+ip+":8080/ZIMZALABIM/SE2/multiupload/default.png";
		do_image_insert("default.png", path, "png");
    	
    	response.sendRedirect("http://localhost:8080/ZIMZALABIM/listall/listall.do?work_div=do_totalCategory_select");
    }
    
    protected void do_image_insert(String orgFileNm, String saveFileNm, String ext) throws ServletException, IOException {
    	LOG.debug("03.1.2 do_image_insert");
    	try{
				ImageVO imageVO = new ImageVO();

				imageVO.setOrgFileNm(orgFileNm);
				imageVO.setSaveFileNm(saveFileNm);
				imageVO.setFileSize(ext);

				
				//이미지번호 구하기
				ProductVO outVO = new ProductVO();
	    		String productNo = this.service.do_select_productNo(outVO).getProductNo();
				imageVO.setProductNo(productNo);//파일번호를 구해와서				
				String imageId = this.service.do_select_imageNo(imageVO).getImageId();//해당 파일번호에 이미지가 있는지 확인 
				if(imageId == null || imageId.equals("")){//이미지가 없으면 이미지번호는 1
					imageId="1";
				}else{//이미지가 있으면 가장큰 이미지번호+1
					imageId = Integer.toString((Integer.parseInt(imageId))+1);
				}
				imageVO.setImageId(imageId);
				
				//썸네일
				String thumbnail="0";
				if(imageId.equals("1")){//이미지ID가 1이면 썸네일로 지정 -> 수정필요,, 있는 이미지중 가장 작은숫자로 지정
					
//					String thumbSaveName = StringUtil.makeThumb(saveFileNm);
//					ImageVO thumbVO = new ImageVO();
//					thumbVO.setImageId("0");
//					thumbVO.setOrgFileNm(orgFileNm);
//					thumbVO.setSaveFileNm(thumbSaveName);
//					thumbVO.setProductNo(productNo);
//					thumbVO.setThumbnail("1");
//					
//					int thumbFlag = imageService.do_insert(thumbVO);
					
					thumbnail = "1";
				}
				imageVO.setThumbnail(thumbnail);//썸네일 여부

				
				//이미지 insert
				int imageFlag = imageService.do_insert(imageVO);
				System.out.println("===imageFlag==="+imageFlag);
			//}
			
		}catch(Exception e){
			LOG.debug("2. Exception:--------------");
			LOG.debug(e.toString());
			LOG.debug("2. Exception:--------------");
		}
    }
    
    protected void do_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	LOG.debug("03.1 do_update");
    	ProductVO inVO = new ProductVO();
    	    	
    	String productNo = StringUtil.nvl(request.getParameter("product_no"), "");
    	String productNm = StringUtil.nvl(request.getParameter("product_nm"), "");
    	String price = StringUtil.nvl(request.getParameter("price"), "");
    	String category = StringUtil.nvl(request.getParameter("category"), "");
    	String contents = StringUtil.nvl(request.getParameter("contents"), "");
    	String targetCnt = StringUtil.nvl(request.getParameter("target_cnt"), "");
    	String deadline = StringUtil.nvl(request.getParameter("deadline"), "");
    	String regId = StringUtil.nvl(request.getParameter("reg_id"), "");
    	String deliveryPrice = StringUtil.nvl(request.getParameter("delivery_price"), "");

    	deadline = deadline.replace("-", ".").substring(0,10);
    	contents = StringUtil.contentsReplace(contents);
    	
    	inVO.setProductNo(productNo);
    	inVO.setProductNm(productNm);
    	inVO.setPrice(price);
    	inVO.setCategory(category);
    	inVO.setContents(contents);
    	inVO.setTargetCnt(targetCnt);
    	inVO.setDeadline(deadline);
    	inVO.setRegId(regId);
    	inVO.setDeliveryPrice(deliveryPrice);
    	LOG.debug("03. param:"+inVO);
    	//--param
//    	int flag = this.service.do_update(inVO);
    	ProductDao productDao = new ProductDao();
    	int flag =  productDao.do_update(inVO);
    	LOG.debug("03.3 flag:"+flag);

    	int deleteFlag = imageService.do_delete(inVO);
    	
    	//첨부된 이미지의 원본파일명
    	List<String> orgFileNameList = StringUtil.originalFileName(contents);
    	//첨부된 이미지의 경로
    	List<String> savePathList = StringUtil.imageSavePath(contents);
    	//첨부된 이미지의 확장자명
    	List<String> extNameList = StringUtil.extName(contents);    	
    	//첨부된 이미지의 갯수
    	int imageCnt = orgFileNameList.size();
    	
    	for(int i = 0; i<imageCnt; i++){
    		if(flag == 1){
    			do_image_insert(orgFileNameList.get(i), savePathList.get(i), extNameList.get(i));
        	}
    	}
    	
    	if(imageCnt==0){
    		String ip = StringUtil.getIP();
    		String path = "http://"+ip+":8080/ZIMZALABIM/SE2/multiupload/default.png";
    		do_image_insert("default.png", path, "png");
    	}
    	
    	Gson gson = new Gson();
    	response.setContentType("text/html;charset=utf-8");
    	PrintWriter out = response.getWriter();
    	
    	String msg = "";
    	String gsonString = "";
    	if(flag>0){
    		msg = inVO.getProductNm()+"이(가)\n등록되었습니다.";
    	}else{
    		msg = "등록실패.";
    	}
    	gsonString = gson.toJson(new MessageVO(String.valueOf(flag),msg));
    	LOG.debug("03.4 gsonString:"+gsonString);
    	
    	
    	response.sendRedirect("http://localhost:8080/ZIMZALABIM/detail/detail.do?work_div=do_detail_select&productNo="+productNo);
    }
    
    public void do_selectOne(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	LOG.debug("03.1 do_selectOne");
    	ProductVO inVO = new ProductVO(); //jhee1
		String productNo = StringUtil.nvl(request.getParameter("product_no"),"");
		inVO.setProductNo(productNo);
		
		LOG.debug("03.2 inVO:"+inVO);
		ProductVO selectVO = (ProductVO) this.service.do_selectOne(inVO);
		LOG.debug("03.3 outVO:"+selectVO);
		request.setAttribute("selectVO", selectVO);
		
		//code관련
		//--code
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/product/writing_view.jsp");
		dispatcher.forward(request, response);
    
    }
    
    public void do_retrieve(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	LOG.debug("03.1 do_retrieve");
    	ListAllSearchVO inVO = new ListAllSearchVO();
    	//검색조건
    	String pageNum = StringUtil.nvl(request.getParameter("page_num"),"1");
    	String searchDiv = StringUtil.nvl(request.getParameter("search_div"),"");
    	String searchWord = StringUtil.nvl(request.getParameter("search_word"),"");
    	String pageSize = StringUtil.nvl(request.getParameter("page_size"),"10");
    	String category = StringUtil.nvl(request.getParameter("category"),"10");
    	inVO.setPageNum(Integer.parseInt(pageNum));
    	inVO.setSearchDiv(searchDiv);
    	inVO.setSearchWord(searchWord);
    	inVO.setPageSize(Integer.parseInt(pageSize));
    	inVO.setCategory(category);
    	
    	LOG.debug("03.2 inVO:"+inVO);
    	List<ProductVO> list = (List<ProductVO>) this.service.do_category_select(inVO);
    	LOG.debug("--------------------");
    	for(ProductVO vo:list){
    		LOG.debug(vo);
    	}
    	LOG.debug("--------------------");
    	int totalCnt = 0;
    	//총 글수
    	if(null != list && list.size()>0){
    		ProductVO totalVO = list.get(0);
    		totalCnt = totalVO.getTotal();
    	}

    	request.setAttribute("totalCnt", totalCnt);
    	request.setAttribute("list", list);
    	request.setAttribute("paramVO", inVO);
    	
    	//여기의 "list"와 member_list.jsp의 <%%>부분의 get.Attribute("list")가 같아야 한다.
    	request.setAttribute("list", list);
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/product/writing_list.jsp");
    	dispatcher.forward(request, response);
    }
    
    public void do_save_move(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    	LOG.debug("03.1 do_save_move");
		CodeVO codeVO=new CodeVO();
	    CodeDao dao  =new CodeDao();
		codeVO.setCodeTypeId("CATEGORY");
		List<CodeVO> list =(List<CodeVO>) dao.do_retrieve(codeVO);
		request.setAttribute("categoryList", list);
		//user_id 화면 제어:readonly
		request.setAttribute("mode", "insert");
    	RequestDispatcher dispatcher = request.getRequestDispatcher("/product/writing_mng.jsp");
    	dispatcher.forward(request, response);
    }

//    protected void do_update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	LOG.debug("03.1 do_update");
//    	ProductVO inVO = new ProductVO();
//    	//param
//
//    	List<ImageVO> imageList = (List<ImageVO>)request.getAttribute("imageInfo");
//    	LOG.debug("======================================================================================");
//    	LOG.debug("=03.1 do_insert()_imageList="+imageList);
//    	LOG.debug("======================================================================================");
//    	
//    	String productNo = StringUtil.nvl(request.getParameter("product_no"), "");
//    	String productNm = StringUtil.nvl(request.getParameter("product_nm"), "");
//    	String price = StringUtil.nvl(request.getParameter("price"), "");
//    	String category = StringUtil.nvl(request.getParameter("category"), "");
//    	String contents = StringUtil.nvl(request.getParameter("contents"), "");
//    	String targetCnt = StringUtil.nvl(request.getParameter("target_cnt"), "");
//    	String deadline = StringUtil.nvl(request.getParameter("deadline"), "");
//    	String regId = StringUtil.nvl(request.getParameter("reg_id"), "");
//    	String deliveryPrice = StringUtil.nvl(request.getParameter("delivery_price"), "");
//
//    	deadline = deadline.replace("-", ".").substring(0,10);
//    	
//    	inVO.setProductNo(productNo);
//    	inVO.setProductNm(productNm);
//    	inVO.setPrice(price);
//    	inVO.setCategory(category);
//    	inVO.setContents(contents);
//    	inVO.setTargetCnt(targetCnt);
//    	inVO.setDeadline(deadline);
//    	inVO.setRegId(regId);
//    	inVO.setDeliveryPrice(deliveryPrice);
//    	LOG.debug("03. param:"+inVO);
//    	//--param
////    	int flag = this.service.do_update(inVO);
//    	ProductDao productDao = new ProductDao();
//    	int flag =  productDao.do_update(inVO);
//    	LOG.debug("03.3 flag:"+flag);
//
//    	if(flag == 1){
//    		
////    		do_image_insert(request, response);
//    	}
//    	
//    	Gson gson = new Gson();
//    	response.setContentType("text/html;charset=utf-8");
//    	PrintWriter out = response.getWriter();
//    	
//    	String msg = "";
//    	String gsonString = "";
//    	if(flag>0){
//    		msg = inVO.getProductNm()+"이(가)\n등록되었습니다.";
//    	}else{
//    		msg = "등록실패.";
//    	}
//    	gsonString = gson.toJson(new MessageVO(String.valueOf(flag),msg));
//    	LOG.debug("03.4 gsonString:"+gsonString);
//    }
    
    protected void do_delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	LOG.debug("03 do_delete()");
    	ProductVO inVO = new ProductVO();
    	String productNo = StringUtil.nvl(request.getParameter("product_no"), "");
    	LOG.debug("=============================================");
    	LOG.debug("productNo="+productNo);
    	LOG.debug("=============================================");
    	
    	inVO.setProductNo(productNo);
    	int flag = this.service.do_delete(inVO);
    	int imageFlag = this.imageService.do_delete(inVO);
    	LOG.debug("03.2 flag"+flag);
    	LOG.debug("03.2 imageFlag"+imageFlag);
    	
    	//JSON
	    Gson gson = new Gson();
	    response.setContentType("text/html;charset=utf-8");
	    PrintWriter out = response.getWriter();
	    String msg = "";
	    String gsonString = "";
	    
	    if(flag>0){
			msg = "삭제되었습니다.";
		}else{
			msg = "삭제실패.";
		}
		gsonString = gson.toJson(new MessageVO(String.valueOf(flag),msg));
		LOG.debug("03.3 gsonString"+gsonString);
		out.print(gsonString);
    }
    

    protected void doServiceHandler(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	LOG.debug("02 doServiceHandler()");
    	request.setCharacterEncoding("UTF-8");
    	String workDiv = StringUtil.nvl(request.getParameter("work_div"), "");
    	LOG.debug("02.1 workDiv"+workDiv);
//    	MultipartRequest multi = null;
//
//    	
//    	if(workDiv==null || workDiv.equals("")){//request로 work_div가 안넘어오면(=multi방식이면)  
//        	savePath = makePath();
//    		int uploadFileSizeLimit = 1024*1024*50; //50M
//    		String encType = "UTF-8";
//
//    		multi = new MultipartRequest(request, savePath, uploadFileSizeLimit, encType, new DefaultFileRenamePolicy());
// 		
//    		workDiv = StringUtil.nvl(multi.getParameter("work_div"), "");
//    		LOG.debug("04.1 workDiv"+workDiv);
//    	}
		
    	/*
    	 * do_retrieve : 목록
    	 * do_insert : 등록
    	 * do_update : 수정
    	 * do_selectone : 단건조회
    	 * do_delete : 삭제
    	 */
    	switch(workDiv){
    		//do_save_move : 등록화면으로 이동
    		case "do_save_move":
    			do_save_move(request, response);
    		break;
    		case "do_insert":
    			do_insert(request, response);
    		break;
//    		case "do_update_test":
//    			do_update_test(request, response);
//    		break;
    		case "do_update":
    			do_update(request, response);
    		break;
    		case "do_delete":
    			do_delete(request, response);
    		break;
    		case "do_selectOne":
    			do_selectOne(request, response);
    		break;
    		case "do_retrieve":
    			do_retrieve(request, response);
    		break;
    	}
   	
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doServiceHandler(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doServiceHandler(request, response);
	}

}
