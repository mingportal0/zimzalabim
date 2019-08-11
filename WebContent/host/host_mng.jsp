<%@page import="java.util.List"%>
<%@page import="com.zim.code.CodeVO"%>
<%@page import="com.zim.product.ProductVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<%
	
	List<ProductVO> list = (List<ProductVO>)request.getAttribute("lvlList");
	ProductVO   vo   = (ProductVO)request.getAttribute("vo");
%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 위 3개의 메타 태그는 *반드시* head 태그의 처음에 와야합니다; 어떤 다른 콘텐츠들은 반드시 이 태그들 *다음에* 와야 합니다 -->    
<link rel="stylesheet" href="/WEB_EX01/js/jquery-ui.css" >
<!-- 부트스트랩 -->
<link href="/WEB_EX01/css/bootstrap.min.css" rel="stylesheet">
<title>Insert title here</title>
<script src="/WEB_EX01/js/jquery-1.12.4.js"></script>
<script src="/WEB_EX01/js/jquery-ui.js"></script>
<!-- 모든 컴파일된 플러그인을 포함합니다 (아래), 원하지 않는다면 필요한 각각의 파일을 포함하세요 -->
<script src="/WEB_EX01/js/bootstrap.min.js"></script>	
</head>
<body>

			<input type="button" class="btn btn-warning" value="주최취소" id="update_btn" />
			<input type="button" class="btn btn-warning" value="주최완료" id="update_btn2" />
			<input type="button" class="btn btn-warning" value="조회" id="listTo_btn" />
			<input type="button" class="btn btn-warning" value="배송정보수정" id="update_btn3" />
			<input type="hidden"  name="Join_id" id="Join_id" value="${vo.regId}" />
	
			<form name="frmMng" id="frmMng" 
              action="/ZIMZALABIM/join/join.do" method="post" >
		        <input type="hidden"  name="work_div" id="work_div" />  
		        <input type="hidden"  name="RegId" id="RegId" value="${vo.regId}"/>    
		    	<label>상품번호</label>
		    	<div><input type="text" readonly="readonly" value="${vo.getProductNo()}"  name="ProductNo" id="ProductNo" size="20"  maxlength="20" /></div>
		    	<label>조최일</label>
		    	<div><input type="text" readonly="readonly" value="${vo.getRegDt()}"  name="join_dt" id="join_dt" size="20"  maxlength="20" /></div>
				<label>상품이름</label>
		    	<div><input type="text" readonly="readonly" value="${vo.getProductNm()}"  name="ProductNm" id="ProductNm" size="30" maxlength="30"  /></div>
				<label>주문수량</label>
		    	<div><input type="text" readonly="readonly" value="${vo.getTargetCnt()}" name="Join_cnt" id="Join_cnt" size="20" maxlength="20"  /></div>
		    	<label>주최상태</label>
		    	<div><input type="text" readonly="readonly" value="<%if(vo.getHostStatus().equals("20")){out.print("주최취소");}else if(vo.getHostStatus().equals("30")){out.print("주최완료");}else{out.print("주최진행");} %>"  name="hostStatus" id="hostStatus" size="20"  maxlength="20" /></div>
				<label>배송상태</label>
				<div>
					<input type = "radio" name = "radioBtn" value = "100" checked = "<% if(vo.getDeliveryStatus().equals("100"))out.print("checked"); %>" />결제 완료
					<input type = "radio" name = "radioBtn" value = "200" checked = "<% if(vo.getDeliveryStatus().equals("200"))out.print("checked"); %>" />공구 진행
					<input type = "radio" name = "radioBtn" value = "300" checked = "<% if(vo.getDeliveryStatus().equals("300"))out.print("checked"); %>" />배송 준비
					<input type = "radio" name = "radioBtn" value = "400" checked = "<% if(vo.getDeliveryStatus().equals("400"))out.print("checked"); %>" />배송 완료
				</div>
			</form>
	<script>
	
		$(document).ready(function(){
			//ProductVO에서 배송정보를 가져옴
			alert(vo.getDeliveryStatus());
			
			//라디오버튼을 체크
			
			
		});
		
		$('#update_btn3').click(function (){
			
			
			
			//radioval를 ProductVO에 Update
			
			
			if( false == confirm('배송상태를 \n변경 하시겠습니까?'))return;
			
			var DeliveryStatus = $('input[name="radioBtn"]:checked').val();
			var ProductNo = $('#ProductNo').val();
			var RegId = $('#RegId').val();
			
			//param
			console.log("DeliveryStatus : "+DeliveryStatus);
			console.log("ProductNo : "+ProductNo);
			console.log("RegId : "+RegId);
			
			//ajax
	        $.ajax({
	           type:"POST",
	           url:"/ZIMZALABIM/host/host.json",
	           dataType:"html",
	           data:{"work_div" : "do_update3",
	        	   	"DeliveryStatus" : DeliveryStatus,
					"ProductNo": ProductNo,
					"RegId" : RegId},					
				success: function(data){
					//{"msgId":"1","msgContents":"삭제되었습니다.","total":0,"num":0}
					var jData = JSON.parse(data);
					console.log(jData.msgId+"|"+jData.msgContents);
					if(null != jData && jData.msgId=="1"){
						alert(jData.msgContents);
						window.location.href="/ZIMZALABIM/host/host.do?work_div=do_retrieve";
					}else{
						alert(jData.msgId+"|"+jData.msgContents);
					}
				},
				complete:function(data){
					
				},
				error:function(xhr,status,error){
					alert("error:"+error);
				}
		    }); 
			//--ajax
			
			
			//배송정보 변경 완료 메세지
			if(DeliveryStatus == "100"){
				alert("결제 완료로 변경되었습니다.");
			}else if(DeliveryStatus == "200"){
				alert("공구 진행으로 변경되었습니다.");
			}else if(DeliveryStatus == "300"){
				alert("배송 준비로 변경되었습니다.");
			}else if(DeliveryStatus == "400"){
				alert("결제완료로 변경되었습니다.");
			}
		});
	  $("#update_btn").on('click',function(){

	       	
	    	
			$("#work_div").val("do_update2");//"do_update" set
			var param = $("#frmMng").serialize();
			//alert(param);
			
	    	if( false==confirm('상품 주최를 \n취소 하시겠습니까?'))return;
	    	
	    	//ajax
	        $.ajax({
	           type:"POST",
	           url:"/ZIMZALABIM/host/host.json",
	           dataType:"html",
	           data:param,
	        success: function(data){
	          var jData = JSON.parse(data);
	          if(null != jData && jData.msgId=="1"){
	            alert(jData.msgContents);
	            window.location.href="/ZIMZALABIM/host/host.do?work_div=do_retrieve";
	          }else{
	            alert(jData.msgId+"|"+jData.msgContents);
	          }
	        },
	        complete:function(data){
	         
	        },
	        error:function(xhr,status,error){
	            alert("error:"+error);
	        }
	       });
	    }); 
   
	  $("#update_btn2").on('click',function(){

	       	
	    	
			$("#work_div").val("do_update4");//"do_update" set
			var param = $("#frmMng").serialize();
			//alert(param);
			
	    	if( false==confirm('상품 주최를 완료 하시겠습니까? \n완료하시면 돌이킬수 없습니다.'))return;
	    	
	    	//ajax
	        $.ajax({
	           type:"POST",
	           url:"/ZIMZALABIM/host/host.json",
	           dataType:"html",
	           data:param,
	        success: function(data){
	          var jData = JSON.parse(data);
	          if(null != jData && jData.msgId=="1"){
	            alert(jData.msgContents);
	            window.location.href="/ZIMZALABIM/host/host.do?work_div=do_retrieve";
	          }else{
	            alert(jData.msgId+"|"+jData.msgContents);
	          }
	        },
	        complete:function(data){
	         
	        },
	        error:function(xhr,status,error){
	            alert("error:"+error);
	        }
	       });
	    }); 
	     
		
        function do_move_to_list(){
            if(confirm('공구목록으로 이동 하시겠습니까?')==false)return;
            
            var frm = document.frmMng;
            frm.work_div.value = "do_move_to_list";
            frm.action = "/ZIMZALABIM/host/host.do"
            frm.submit();
         }
         //조회로 이동
       $("#listTo_btn").on("click",function(){
    	   do_move_to_list();
   		 });
	    
		$(document).ready(function(){
		});
	</script>
</body>
</html>