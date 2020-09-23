<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>订单列表</title>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript">
	function showOrder(orderId){
		location.href="${pageContext.request.contextPath}/orderController?method=getOrderDetail&oid="+orderId;
	}
	function changeStatus(orderId){
		location.href="${pageContext.request.contextPath}/changeStatus?oid="+orderId;
	}
</script>
</head>
<body style="background-color:#f5f5f5">
<%@ include file="header.jsp"%>
<div class="container" style="background-color: white;">
	<div class="row" style="margin-left: 40px">
		<h3>我的订单列表&nbsp;&nbsp;
		<small>温馨提示：<em>${user.uName}</em>有<font color="red">${orderList.size()}</font>个订单</small></h3>
	</div>
	<div class="row" style="margin-top: 40px;">
		<div class="col-md-12">
			<table id="tb_list" class="table table-striped table-hover table-bordered table-condensed">
			<tr>
				<th>序号</th>
				<th>订单编号</th>
				<th>总金额</th>
				<th>订单状态</th>
				<th>订单时间</th>
				<th>收货地址</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${orderList}" var="order" varStatus="i">
				<tr>
					<th>${i.count}</th>
					<th>${order.oId}</th>
					<th>${order.oCount}</th>
					<th>
						<font color="red">
							<c:if test="${order.oState eq 0 }">
								未支付
							</c:if>
							<c:if test="${order.oState eq 1 }">
								已支付,代发货
							</c:if>
							<c:if test="${order.oState eq 2 }">
								已发货,待收货
							</c:if>
							<c:if test="${order.oState eq 3 }">
								收货，代评价
							</c:if>
							<c:if test="${order.oState eq 4 }">
								订单完成
							</c:if>
							<c:if test="${order.oState eq 5 }">
								退货
							</c:if>
						</font>
					</th>
					<th>
						<fmt:formatDate value="${order.oTime}" pattern="yyyy-MM-dd HH:mm:ss" var="t"></fmt:formatDate>
						${t}
					</th>
					<th>${order.address.aDetail}</th>
					<th>
						<button type="button" class="btn btn-danger btn-sm" onclick="showOrder('${order.oId}')">订单详情</button>
						<c:if test="${order.oState eq 2 }">
							<button type="button" class="btn btn-warning btn-sm" onclick="changeStatus('${order.oId}')">确认收货</button>
						</c:if>
					</th>
				</tr>
			</c:forEach>
		</table>
		</div>
	</div>
	
</div>
	
    
<!-- 底部 -->
<%@ include file="footer.jsp"%>

</body>
</html>