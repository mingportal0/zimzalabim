<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
 <style type="text/css">
   body {
    font-family: Arial, Verdana, sans-serif;
    color: #111111;}
   table {
    width: 90%;}
   th, td {
    padding: 7px 10px 10px 10px;}
   th {
    text-transform: uppercase;
    letter-spacing: 0.1em;
    font-size: 90%;
    border-bottom: 2px solid #111111;
    border-top: 1px solid #999;
    text-align: left;}
   tr.even {
    background-color: #efefef;}
   tr:hover {
    background-color: #c3e6e5;}
   .money {
    text-align: right;}
  </style>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/WEB_EX01/js/jquery-ui.css" >
<title>Insert title here</title>
<script src="/WEB_EX01/js/jquery-1.12.4.js"></script>
<script src="/WEB_EX01/js/jquery-ui.js"></script>
</head>
<body>
	    	<div class="form-group">
	    		<label class="col-sm-2 control-label">배송진행상태 변경</label>
	    		<div class="col-sm-8">
	    			<label class="radio-inline">
	    				<input type="radio" name="radio" id="radio" value="100">결제완료
	    			</label>	    		
	    			<label class="radio-inline">
	    				<input type="radio" name="radio" id="radio" value="200">공구진행
	    			</label>
	    			<label class="radio-inline">
	    				<input type="radio" name="radio" id="radio" value="300">배송준비
	    			</label>
	    			<label class="radio-inline">
	    				<input type="radio" name="radio" id="radio" value="400">배송완료
	    			</label>
	    			<label class="button-inline">
	    				<input type="button" name="statebtn" id="statebtn" value="결정"/>
	    			</label>	    				    			
	    		</div>
	    	</div>	 
			<form name="searchFrm" action="/ZIMZALABIM/host/host.do" method="post">
			<input type="hidden" name="work_div" />
			<input type="hidden" name="pageNum"  />
		<script>
		$("#statebtn").on('click',function(){
			//alert('paymentcomplete');
	    	var delivery_status = $("#delivery_status").val();
			if(null == delivery_status || delivery_status.trim().length == 0){
				$("#delivery_status").focus();
				alert('배송상태를 업데이트 하시겠습니까?');
				return ;
			}        	
        	
			$("#work_div").val("do_update");//"do_update" set
			var param = $("#frmMng").serialize();
			//alert(param);
			
        	if( false==confirm(delivery_status+'배송상태를 업데이트 하시겠습니까?'))return;
        	
        	//ajax
            $.ajax({
               type:"POST",
               url:"/ZIMZALABIM/host/host.do",
               dataType:"html",
               data:param, 
            success: function(data){
              var jData = JSON.parse(data);
              if(null != jData && jData.msgId=="1"){
                alert(jData.msgContents);
                window.location.href="/ZIMZALABIM/member/host_status_view.jsp";
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
        });
// 		$("#progress").on('click',function(){
// 			//alert('progress');
// 	    	var userId = $("#HOST_STATUS").val();
// 			if(null == userId || userId.trim().length == 0){
// 				$("#HOST_STATUS").focus();
// 				alert('배송상태를 업데이트 하시겠습니까?');
// 				return ;
// 			}        	
        	
// 			$("#work_div").val("do_update");//"do_update" set
// 			var param = $("#frmMng").serialize();
// 			//alert(param);
			
//         	if( false==confirm(HOST_STATUS+'배송상태를 업데이트 하시겠습니까?'))return;
        	
//         	//ajax
//             $.ajax({
//                type:"POST",
//                url:"/ZIMZALABIM/host/host.do",
//                dataType:"html",
//                data:param, 
//             success: function(data){
//               var jData = JSON.parse(data);
//               if(null != jData && jData.msgId=="1"){
//                 alert(jData.msgContents);
//                 window.location.href="/ZIMZALABIM/member/host_status_view.jsp";
//               }else{
//                 alert(jData.msgId+"|"+jData.msgContents);
//               }
//             },
//             complete:function(data){
             
//             },
//             error:function(xhr,status,error){
//                 alert("error:"+error);
//             }
//            }); 
// 		});
// 		$("#shippingpreparation").on('click',function(){
// 			//alert('shippingpreparation');
// 	    	var userId = $("#HOST_STATUS").val();
// 			if(null == userId || userId.trim().length == 0){
// 				$("#HOST_STATUS").focus();
// 				alert('배송상태를 업데이트 하시겠습니까?');
// 				return ;
// 			}        	
        	
// 			$("#work_div").val("do_update");//"do_update" set
// 			var param = $("#frmMng").serialize();
// 			//alert(param);
			
//         	if( false==confirm(HOST_STATUS+'배송상태를 업데이트 하시겠습니까?'))return;
        	
//         	//ajax
//             $.ajax({
//                type:"POST",
//                url:"/ZIMZALABIM/host/host.do",
//                dataType:"html",
//                data:param, 
//             success: function(data){
//               var jData = JSON.parse(data);
//               if(null != jData && jData.msgId=="1"){
//                 alert(jData.msgContents);
//                 window.location.href="/ZIMZALABIM/member/host_status_view.jsp";
//               }else{
//                 alert(jData.msgId+"|"+jData.msgContents);
//               }
//             },
//             complete:function(data){
             
//             },
//             error:function(xhr,status,error){
//                 alert("error:"+error);
//             }
//            }); 
// 		});
// 		$("#deliverycompleted").on('click',function(){
// 			//alert('deliverycompleted');
// 	    	var userId = $("#HOST_STATUS").val();
// 			if(null == userId || userId.trim().length == 0){
// 				$("#HOST_STATUS").focus();
// 				alert('배송상태를 업데이트 하시겠습니까?');
// 				return ;
// 			}        	
        	
// 			$("#work_div").val("do_update");//"do_update" set
// 			var param = $("#frmMng").serialize();
// 			alert(param);
			
//         	if( false==confirm(HOST_STATUS+'배송상태를 업데이트 하시겠습니까?'))return;
        	
//         	//ajax
//             $.ajax({
//                type:"POST",
//                url:"/ZIMZALABIM/host/host.do",
//                dataType:"html",
//                data:param, 
//             success: function(data){
//               var jData = JSON.parse(data);
//               if(null != jData && jData.msgId=="1"){
//                 alert(jData.msgContents);
//                 window.location.href="/ZIMZALABIM/host/host_status_view.jsp";
//               }else{
//                 alert(jData.msgId+"|"+jData.msgContents);
//               }
//             },
//             complete:function(data){
             
//             },
//             error:function(xhr,status,error){
//                 alert("error:"+error);
//             }
//            }); 
// 		});
		</script>
</body>
</html>

		