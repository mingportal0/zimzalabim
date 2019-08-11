<%--
  /**
  * @Class Name : useOutObject.jsp
  * @Description : Sample Register 화면
  * @Modification Information
  *
  *   수정일                   수정자                      수정내용
  *  -------    --------    ---------------------------
  *  2019.07.01            최초 생성
  *
  * author 실행환경 개발팀
  * since 2019.07.01
  *
  * Copyright (C) 2009 by KandJang  All right reserved.
  */
--%>
<%@page import="com.zim.product.image.ImageVO"%>
<%@page import="com.zim.product.image.ImageService"%>
<%@page import="com.zim.product.detail.DetailService"%>
<%@page import="java.util.List"%>
<%@page import="com.zim.product.comment.CommentService"%>
<%@page import="com.zim.product.comment.CommentVO"%>
<%@page import="com.zim.member.MemberVO"%>
<%@page import="com.zim.join.JoinVO"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.zim.product.ProductVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%

	request.setCharacterEncoding("UTF-8");

	Logger LOG = Logger.getLogger(this.getClass());
	CommentService commentService = new CommentService();	
	DetailService detailService = new DetailService();	
	
	MemberVO memVO = (MemberVO)session.getAttribute("user");
	session.setAttribute("memVO", memVO);
	
%>    
<!-- 지역정보 바꾸기 -->
<c:choose>
	<c:when test="${lang_session=='en' }">
		<fmt:setLocale value="en"/>
	</c:when>
	<c:otherwise>
		<fmt:setLocale value="ko"/>
	</c:otherwise>
</c:choose>
<fmt:bundle basename="resource.message">
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->    
<link rel="stylesheet" href="/ZIMZALABIM/js/jquery-ui.css" >
<!-- 부트스트랩 -->
<link href="/ZIMZALABIM/css/bootstrap.min.css" rel="stylesheet">
<title>게시관리</title>
<script src="/ZIMZALABIM/js/jquery-1.12.4.js"></script>
<script src="/ZIMZALABIM/js/jquery-ui.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="/ZIMZALABIM/js/bootstrap.min.js"></script>	
</head>
<body>


	<br/>
	<br/>
	
	<!-- 총대 수정삭제 버튼 -->
	<div class="container">
		<c:choose>
			<c:when test="${memVO.userId == vo.regId}">
				<div>
					<input type="button" class="btn btn-default btn-sm  btn-warning btn-hover" value="<fmt:message key="D_CONTENTS_MODI"></fmt:message>" id="modify_btn"/>
					<input type="button" class="btn btn-default btn-sm  btn-warning btn-hover" value="<fmt:message key="D_CONTENTS_DELETE"></fmt:message>" id="delete_btn"/>
				</div>
			</c:when>
			<c:otherwise>
				<div></div>
			</c:otherwise>
		</c:choose>		
		<br/>
	</div>
	<!-- //총대 수정삭제 버튼 -->
	
	
	
	<!-- 상품정보 -->
	<div class="container">
		
		<form name="frm" id="frm"  method="post">
				<input type="hidden" name="work_div" id="work_div" />
				<input type="hidden" name="product_no" id="product_no" value="${vo.productNo}" />
				<input type="hidden" name="join_id" id="join_id" value="${memVO.userId}" />
				<input type="hidden" name="price" id="price" value="${vo.price}" />
				
			<table class="table">
				<tr>
					<td class="text-left  lead  col-md-1"><fmt:message key="D_PRO_NM"></fmt:message></td>
					<td class="col-md-3" value="product_nm">${vo.productNm}</td>
					<td class="text-left  lead  col-md-1"><fmt:message key="D_PRICE"></fmt:message></td>
					<td class="col-md-1">${vo.price}</td>
					<td class="text-left  lead col-md-1"><fmt:message key="D_REMAINING"></fmt:message></td>
					<td class="col-md-1" name="rest_amount" id="rest_amount">${vo.restAmount}</td>
					<td class="text-left  lead  col-md-1"><fmt:message key="D_DEADLINE"></fmt:message></td>
					<td class="col-md-1">${vo.deadline}</td>
				</tr>
			</table>
		
			<table class="table">
				<tr>
					<td class="text-left  lead  col-md-1"><fmt:message key="D_DEL_COST"></fmt:message></td>
					<td class="col-md-3">${vo.deliveryPrice}</td>
					<td class="text-left  lead  col-md-1"><fmt:message key="D_CNT"></fmt:message></td>
					<td class="col-md-1">
						<input type="text" name="join_cnt" id="join_cnt" value="1" class="form-control input-lg" style="text-align:center; width:80px;" autocomplete="off">
					</td>
					<td class="col-md-1">
						<div>
							<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="▲" name="plus" id="plus" />
						</div>
						<div>
							<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="▼" name="minus" id="minus" />
						</div>
					</td>
					<td class="col-md-3">
						<input type="button" class="btn btn-default  btn-lg  lead  btn-warning btn-hover" style="float:left; width:160px; " value="<fmt:message key="D_WISHLIST"></fmt:message>" name="wish" id="wish" />
						<input type="button" class="btn btn-default  btn-lg  lead  btn-warning btn-hover" style="float:right; width:160px;" value="<fmt:message key="D_PAY"></fmt:message>" name="pay" id="pay" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<!-- //상품정보 -->
	<br/>
	<br/>	
	
	<!-- 상품상세정보 및 공지 -->
	<div class="container">

 		<div id="tabs" class="tab-box">
			<ul>
				<li><a href="#tabs-1" ><fmt:message key="D_DETAIL"></fmt:message></a></li>
				<li><a href="#tabs-2" ><fmt:message key="D_NOTICE"></fmt:message></a></li>
			</ul>			
			<div id="tabs-1">
				${vo.contents}
			</div> <!-- 상세보기 div -->			
			<div id="tabs-2">
				
				<c:choose>
					<c:when test="${memVO.userId == vo.regId}">
						<div>
							<input type="button"  id="notice_modi_btn" class="btn btn-default  btn-sm  btn-warning btn-hover" value="<fmt:message key="D_NOTICE_MODI_BTN"></fmt:message>"/>
						</div>
					</c:when>
				</c:choose>	
				
				<div>
					<form name="notice_frm" id="notice_frm" action="/ZIMZALABIM/detail/detail.do" method="post">
						<input type="hidden" name="work_div" id="work_div" />
						<input type="hidden" name="product_no" id="product_no" value="${vo.productNo}" />
						<input type="hidden" name="reg_id" id="reg_id" value="${memVO.userId}" />
						<input type="hidden" name="product_nm" id="product_nm" value="${vo.productNm}" />
						<input type="hidden" name="notice" id="notice" value="${vo.notice}" />
						<br/>
							<p name="notice2" id="notice2" >${vo.notice}</p>
						<br/>
					</form>	
				</div>
				
				<br/>
				<br/>
				<br/>
				
				<form name="commentfrm" id="commentfrm" action="/ZIMZALABIM/mainpage/commentcontroller.do" method="get" class="form-horizontal">
					 <input type="hidden"  name="co_work_div" id="co_work_div"/> 
					 <input type="hidden" name="product_no" id="product_no" value="${vo.productNo}" />
					 <input type="hidden" name="comment_no" id="comment_no" value="${coVO.commentNo}" />
	 				 <input type="hidden" name="reg_id" id="reg_id" value="${memVO.userId}" />
	 				 <input type="hidden" name="comm_con" id="comm_con" value="${coVO.contents}" />
	 				 <input type="hidden" name="hcomment_no" id="hcomment_no" value="${coVO.hcommentNo}" />
					 
					 <input type="text" name="contents" id="contents" placeholder="댓글을 입력하세요." style="width:100%; height:100px;" class="form-control"/>
				</form>
					<div style="margin-top:5px;">
						<input type="button" name="comments_btn" id="comments_btn" value="<fmt:message key="D_COMMENT_BTN"></fmt:message>" class="btn btn-default  btn-sm  btn-warning btn-hover" onClick="javascript:do_comment_insert(${coVO.hcommentNo});" />	
					</div>
				<br/>
				<br/>
				<br/><!-- 댓글부분 -->
				
				<c:forEach  var="coVO" items="${coList}">
					<c:choose>
						<c:when test="${coVO.hcommentNo != null}">
							<div style="margin-left: 40px">
								
								<table class="table table-striped">
									<tbody>
										<tr>
											<input type="hidden" value="${coVO.commentNo}" />
											<td>${coVO.regId} &nbsp &nbsp ${coVO.regDt} &nbsp &nbsp
												<c:choose>
													<c:when test="${memVO.userId == coVO.regId}">
														<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="수정" onClick="javascript:do_comment_modify('${coVO.commentNo}','${coVO.contents}');" />
														<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="삭제" onClick="javascript:do_comment_delete(${coVO.commentNo});"/>
													</c:when>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>${coVO.contents}</td>
										</tr>
									</tbody>
								</table>
							</div>
							<br/>
						</c:when>
						<c:otherwise>
							<div>
								<table class="table table-striped">
									<tbody>
										<tr>
											<input type="hidden" value="${coVO.commentNo}" />
											<td>${coVO.regId} &nbsp &nbsp ${coVO.regDt} &nbsp &nbsp
												<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="답글" onClick="javascript:do_re_comment(${coVO.commentNo});"/>
												<c:choose>
													<c:when test="${memVO.userId == coVO.regId}">
														<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="수정" onClick="javascript:do_comment_modify('${coVO.commentNo}','${coVO.contents}');" />
														<input type="button" class="btn btn-default  btn-xs  btn-warning btn-hover" value="삭제" onClick="javascript:do_comment_delete(${coVO.commentNo});"/>
													</c:when>
												</c:choose>
											</td>
										</tr>
										<tr>
											<td>${coVO.contents}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				
			</div> 	<!-- 상품상세정보 및 공지 -->			
		</div> <!-- 탭 div -->
	</div> <!-- 컨테이너 div -->
</fmt:bundle>
	
	
	
	
	<script>		
		//탭 ok
		//수량 증감 ok
		//게시물 수정ok
		//게시글 삭제 ok
		//관심상품 등록 ok
		//결제하기 ok
		//공지수정 ok
		//댓글 입력버튼 ok
		//댓글 삭제 ok
		//댓글 수정 팝업 ok
		//댓글 답글(대댓글)ok
		//남은수량 조건주기
		
		
		
		//엔터키로 버튼 동작
		//댓글등록
		$("#contents").keydown(function(key) {
            //키의 코드가 13번일 경우 (13번은 엔터키)
            if (key.keyCode == 13) {
                //ID가 alpreah_btn을 찾아 클릭해준다.
                //버튼 말고도 p태그나 다른 태그도 다 응용 가능 합니다.
                //대신 이벤트 발생을 위해서는 29번쨰 줄 코드처럼 이벤트를 걸어줘야 합니다.
                $("#comments_btn").click();
            }
        });


		

		
		//대댓글 등록
		function do_re_comment(hcommentNo){
			
			var hcomment_no = hcommentNo;
			//alert(hcomment_no);
			
			var title = "대댓글창";
			var xWidth = window.screen.width/2;	
			var xHeight = window.screen.height/2;
			console.log("xWidth:"+xWidth);
			console.log("xHeight:"+xHeight);
			
			
			var pX = xWidth/2;
			var pY = xHeight/2;
			
			
 			var status = 'toolbar=no,directories=no,scrollabs=no,resizable=no,status=no,menubar=no,width=700,height=350,top='+pY+',left='+pX;
			window.open("", title, status);
			
			var frm = document.commentfrm;
			frm.hcomment_no.value = hcomment_no;
			frm.target = title;
			frm.method = "post";
			frm.action = "<c:url value='/popup/detail_recomment_popup_view.jsp' />;"
			frm.submit(); 
			
		}
	
		
		
		//댓글 수정 팝업
		function do_comment_modify(commentNo,commentCon){
			
			var comment_no = commentNo;
			var comm_con = commentCon;
			//alert(comment_no);
			//alert(comm_con);
			
			var title = "수정창";
			var xWidth = window.screen.width/2;	
			var xHeight = window.screen.height/2;
			console.log("xWidth:"+xWidth);
			console.log("xHeight:"+xHeight);
			
			
			var pX = xWidth/2;
			var pY = xHeight/2;
			
			
 			var status = 'toolbar=no,directories=no,scrollabs=no,resizable=no,status=no,menubar=no,width=700,height=350,top='+pY+',left='+pX;
			window.open("", title, status);
			
			var frm = document.commentfrm;
			frm.comment_no.value = comment_no;
			frm.comm_con.value = comm_con;
			frm.target = title;
			frm.method = "post";
			frm.action = "<c:url value='/popup/detail_co_popup_view.jsp' />;"
			frm.submit(); 
			
			
		}
		
		
		
		
		//댓글삭제
		function do_comment_delete(comment){
			
			var comment_no = comment;
			var product_no = $("#product_no").val();
			var reg_id = $("#reg_id").val();
			
			
 			var frm = document.commentfrm;
			frm.co_work_div.value = 'do_delete';
			frm.comment_no.value = comment_no;
 			frm.action         = '/ZIMZALABIM/mainpage/commentcontroller.do';
			frm.submit();
			
		}
		
		
		
		//댓글등록
		function do_comment_insert(){
			
			//validation : 필수 check
			var contents = $("#contents").val();
			if(null == contents || contents.trim().length == 0){
				$("#contents").focus();
				alert('내용을 입력하세요.');
				return ;
			}
			
			//로그인 validation
			var join_id = $("#join_id").val();
			if(null == join_id || join_id.trim().length == 0){
				alert('로그인을 해주세요.');
				return ;
			}
			
			
			var product_no = $("#product_no").val();
			var frm = document.commentfrm;
			frm.co_work_div.value = 'do_insert';
			frm.action         = "/ZIMZALABIM/mainpage/commentcontroller.do";
			frm.submit();
			
		}
		
		
		
		
	
		//탭 설정
		$("#tabs").tabs();
		
		
		//새로고침 시 탭 고정
/*  		$(function(){
			$("#tabs").tabs({
				select:function(event,ui){
					window.location.replace(ui.tab.hash);
				}//이걸 추가하면 선택 탭 계속 유지				
			});
		}); */
				
		
		
		
		//공지 수정
		$("#notice_modi_btn").click(function(){
			
			var product_no = $("#product_no").val();
			var notice = $("#notice2").text();
			//alert("product_no:"+product_no);
			//alert("notice:"+notice);
			
 			var frm = document.notice_frm;
 			frm.work_div.value = "do_selectOne"; //controller에 있는 메소드 이름이랑 같이 줘야함. 그걸 동작하게 하는 것.
 			frm.notice.value = notice;
 			frm.action = '/ZIMZALABIM/detail/detail.do?product_no='+product_no;
 			frm.submit();
			
		 });
		
		
		
	
		
		//수량 증감
		$(function(){ 
			
		  $("#plus").click(function(){ 
			  //alert($("#rest_amount").text());
		   	 var join_cnt = document.frm.join_cnt;
		   	 join_cnt.value++; 
		   	 
		   	 if(join_cnt>$("#rest_amount").text()){
			   	 join_cnt.value++; 
			 }else{
				 alert("남은 수량을 확인하세요.");
			 }
		   	 
		   	 
		  });
		  
		  
		  
		  $("#minus").click(function(){ 
			  var join_cnt = document.frm.join_cnt;
			  if (join_cnt.value > 1) {
				  join_cnt.value -- ;
			  }
		  });
		  
		}); 
		
		
	
		//게시물 수정
		$("#modify_btn").click(function(){
			
			var product_no = $("#product_no").val();
			console.log("product_no"+product_no);
			
 			var frm = document.frm;
 			frm.work_div.value = "do_selectOne"; //controller에 있는 메소드 이름이랑 같이 줘야함. 그걸 동작하게 하는 것.
//  			frm.action = '/ZIMZALABIM/product/writing.do?product_no='+product_no;
 			frm.action = 'http://211.238.142.124:8080/ZIMZALABIM/product/writing.do?productt_no='+product_no;
 			frm.submit();
			
		 });
		
		
		
		//게시물 삭제
		$('#delete_btn').on('click',function(){
			console.log('delete_btn');
			console.log($("#product_no").val());
			
			if( false == confirm('상품을 삭제하시겠습니까?')) return;			
			
			//ajax
			$.ajax({
					type: "POST",
					url:"/ZIMZALABIM/product/writing.json",
					dataType:"html",
					data:{
						"work_div":"do_delete",
						"product_no":$("#product_no").val()
				},
				success: function(data){
					console.log(data);
					var jData = JSON.parse(data); //스트링을 json데이터로 바꿔주는 것
					console.log(jData.msgId+"|"+jData.msgContents);
					if(null != jData && jData.msgId == "1"){
						alert(jData.msgContents);	
// 						window.location.href="/ZIMZALABIM/mainpage/mainpage.do?work_div=do_move_to_main"; // 화면은 그대로 있고 데이터의 이동만 있음. 
						window.location.href="/ZIMZALABIM/listall/listall.do?work_div=do_totalCategory_select";
					}else{
						alert(jData.msgId+"|"+jData.msgContents);
					}
				},
				complete: function(data){
					
				},
				error:function(xhr,status,error){
					alert("error"+error);
				}
				
			});//--ajax
			
		});
		
		
		
	
	
		
		
		//관심상품 등록
		$('#wish').on('click',function(){
			
			//로그인 validation
			var join_id = $("#join_id").val();
			if(null == join_id || join_id.trim().length == 0){
				alert('로그인을 해주세요.');
				return ;
			}
			
			
			if( false == confirm('위시리스트에 추가하시겠습니까?')) return;
			
			
			//ajax
			$.ajax({
					type: "POST",
					url:"/ZIMZALABIM/wishlist/wishlist.json",
					dataType:"html",
					data:{
						"work_div":"do_insert",
						"user_id":$("#join_id").val(),
						"product_no":$("#product_no").val()
				},
				success: function(data){
					
					var jData = JSON.parse(data); //스트링을 json데이터로 바꿔주는 것
					console.log(jData.msgId+"|"+jData.msgContents);
					if(null != jData && jData.msgId == "1"){
						alert(jData.msgContents);
						//window.location.href="/ZIMZALABIM/mainpage/mainpage.do?work_div=do_detail_select"; // 화면은 그대로 있고 데이터의 이동만 있음. 
						location.reload();	
					}else{
						alert(jData.msgId+"|"+jData.msgContents);
					}
				},
				complete: function(data){
					
				},
				error:function(xhr,status,error){
					alert("error"+error);
				}
				
			});//--ajax
			
		});
			
			
		
		
		
		
		//결제하기
		$("#pay").click(function(){
			
			
			//로그인 validation
			var join_id = $("#join_id").val();
			if(null == join_id || join_id.trim().length == 0){
				alert('로그인을 해주세요.');
				return ;
			}
			
			//결제 수량 validation
			if($("#join_cnt").val()>$("#rest_amount").text()){
				alert("남은 수량을 확인하세요.");
				return ;
			}
			
			
			$('#work_div').val('do_insert');
			var param = $('#frm').serialize();
			if( false == confirm('결제하시겠습니까?')) return;
			
			
			//ajax
			$.ajax({
					type: "POST",
					url:"/ZIMZALABIM/join/join.json",
					dataType:"html",
					data:param,
				success: function(data){
					
					var jData = JSON.parse(data); //스트링을 json데이터로 바꿔주는 것
					console.log(jData.msgId+"|"+jData.msgContents);
					if(null != jData && jData.msgId == "1"){
						alert(jData.msgContents);	
						//window.location.href="/ZIMZALABIM/detail/detail.do?work_div=do_detail_select"; // 화면은 그대로 있고 데이터의 이동만 있음.
						location.reload();
					}else{
						alert(jData.msgId+"|"+jData.msgContents);
					}
				},
				complete: function(data){
					
				},
				error:function(xhr,status,error){
					alert("error"+error);
				}
				
			});//--ajax
			
			
		 });
	
		
		
		
		
		
	
	
	
 	$(document).ready(function(){
  	
	});
  
  
 </script>
</body>
</html>