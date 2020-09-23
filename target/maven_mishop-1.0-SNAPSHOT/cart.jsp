<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>购物车</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function pNum(pid,p,no){
		var nums = $("#num_count"+no).val();
		$.ajax({
			url:"${pageContext.request.contextPath}/cartController?method=updateAddNum&pid="+pid+"&price="+p,
			method:"get",
			success:function(data){
				//location.href = "getCart";
				//console.info(data.code)
				if(data.code==1){
					$("#num_count"+no).prop("value",Number($("#num_count"+no).prop("value"))+1);
					$("#cCount"+no).text(eval($("#cCount"+no).text()+"+"+$("#pPrice"+no).text()));
					$("#sum").text(eval($("#sum").text()+"+"+$("#pPrice"+no).text()))
					return;
				}
				if(data.code==-1){
					location.href="${pageContext.request.contextPath}/login.jsp";
					return;
				}
				if(data.code==0){
					alert("新增失败")
				}
			},
			error:function(){
				alert("服务器异常");
			}
		})
	}
	function mNum(pid,p,no){
		var num = -1; //数量
		var nums = $("#num_count"+no).val();
		if(Number(nums)<=1){
			if(confirm("确认要删除吗?")){
				//num = 0;
				$.ajax({
					url:"${pageContext.request.contextPath}/cartController?method=deleteCart&pid="+pid,
					success:function (data) {
						if(data.code==1){
							$("#sum").text(eval($("#sum").text()+"-"+$("#cCount"+no).text()))
							$("#tr"+no).remove();
							return;
						}
						if(data.code==-1){
							location.href="${pageContext.request.contextPath}/login.jsp";
							return;
						}
						if(data.code==0){
							alert("删除失败");
							return;
						}
					}
				})
				return;
			}else{
				return;
			}
		}else{
		$.ajax({
			url:"${pageContext.request.contextPath}/cartController?method=updateNum&pid="+pid+"&price="+p,
			method:"get",
			success:function(data){
				//location.href = "getCart";
				if(data.code==-1){
					location.href="${pageContext.request.contextPath}/login.jsp";
					return;
				}
				if(data.code==1){
					$("#num_count"+no).prop("value",$("#num_count"+no).prop("value")-1);
					$("#cCount"+no).text(eval($("#cCount"+no).text()+"-"+$("#pPrice"+no).text()));
					$("#sum").text(eval($("#sum").text()+"-"+$("#pPrice"+no).text()))
					return;
				}
				if(data.code==0){
					alert("更新失败");
					return;
				}
			},
			error:function(){
				alert("服务器异常");
			}
		})
		}
	}
	function deleteCart(pid,no) {
		if(confirm("确认要删除吗?")){
		$.ajax({
			url:"${pageContext.request.contextPath}/cartController?method=deleteCart&pid="+pid,
			success:function (data) {
				if(data.code==1){
					$("#sum").text(eval($("#sum").text()+"-"+$("#cCount"+no).text()))
					$("#tr"+no).remove();
					return;
				}
				if(data.code==-1){
					location.href="${pageContext.request.contextPath}/login.jsp";
					return;
				}
				if(data.code==0){
					alert("删除失败");
					return;
				}
			}
		})
	}
	}
	function clearCart(){
		if(confirm("确认要删除吗")){
			location.href="${pageContext.request.contextPath}/cartController?method=deleteAllCart";

		}
	}
</script>
</head>
<body style="background-color:#f5f5f5">
<%@ include file="header.jsp"%>
<div class="container" style="background-color: white;">
	<div class="row" style="margin-left: 40px">
		<h3>我的购物车<small>温馨提示：产品是否购买成功，以最终下单为准哦，请尽快结算</small></h3>
	</div>
	<div class="row" style="margin-top: 40px;">
		<div class="col-md-10 col-md-offset-1">
			<table class="table table-bordered table-striped table-hover">
 				<tr>
 					<th>序号</th>
 					<th>商品名称</th>
 					<th>价格</th>
 					<th>数量</th>
 					<th>小计</th>
 					<th>操作</th>
 				</tr>
 				<c:set value="0" var="sum"></c:set>
 				<c:forEach items="${carts}" var="c" varStatus="i">
	 				<tr id="tr${i.count}">
	 					<th>${i.count}</th>
	 					<th>${c.product.pName}</th>
	 					<th id="pPrice${i.count}">${c.product.pPrice}</th>
	 					<th width="100px">
		 					<div class="input-group">
		 						<span class="input-group-btn">
		 							<button class="btn btn-default" type="button" onclick="mNum(${c.pId},${c.product.pPrice},${i.count})">-</button>
		 						</span>
		 						<input type="text" class="form-control" id="num_count${i.count}" value="${c.cNum}" readonly="readonly" style="width:40px">
		 						<span class="input-group-btn">
		 							<button class="btn btn-default" type="button" onclick="pNum(${c.pId},${c.product.pPrice},${i.count})">+</button>
		 						</span>
	 						</div>
	 					</th>
	 					<th class="cCount">¥&nbsp;<b id="cCount${i.count}">${c.cCount}</b></th>
	 					<th>
	 						<button type="button" class="btn btn-default" onclick="deleteCart(${c.pId},${i.count})">删除</button>
	 					</th>
	 				</tr>
	 				<c:set var="sum" value="${sum+c.cCount}"></c:set>
 				</c:forEach>
			</table>
		</div>
	</div>
	<hr>
	<div class="row">
		<div class="pull-right" style="margin-right: 40px;">
			
	            <div>
	            	<a id="removeAllProduct" href="javascript:clearCart()" class="btn btn-default btn-lg">清空购物车</a>
	            	&nbsp;&nbsp;
	            	<a href="${pageContext.request.contextPath}/orderController?method=getOrderView" class="btn  btn-danger btn-lg">购买</a>
	            	
	            </div>
	            <br><br>
	            <div style="margin-bottom: 20px;">        		  
	            	商品金额总计：<span id="total" class="text-danger"><b>￥&nbsp;&nbsp;</b><b id="sum">${sum}</b></span>
	            </div>
		</div>
	</div>
</div>
	
    
<!-- 底部 -->
<%@ include file="footer.jsp"%>

</body>
</html>