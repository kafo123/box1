<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>商品列表页</title>

</head>
<body>
<%@ include file="header.jsp" %>


<div class="panel panel-default" style="margin: 0 auto;width: 95%;">
    <div class="panel-heading">
        <h3 class="panel-title"><span class="glyphicon glyphicon-th-list"></span>&nbsp;&nbsp;商品列表</h3>
    </div>
    <div class="panel-body">
        <!--列表开始-->
        <div class="row" style="margin: 0 auto;">
            <c:forEach items="${pageBean.pageData}" var="g" varStatus="i">
                <div class="col-sm-3">
                    <div class="thumbnail">
                        <img src="${pageContext.request.contextPath}/${g.pImage}" width="180" height="180" alt="小米6"/>
                        <div class="caption">
                            <h4>商品名称<a href="${pageContext.request.contextPath}/goodsController?method=getProductDetail&id=${g.pId}">${g.pName}</a>
                            </h4>
                            <p>热销指数
                                <c:forEach begin="1" end="${g.pState}">
                                    <img src="image/star_red.gif" alt="star"/>
                                </c:forEach>
                            </p>
                            <p>上架日期:${g.pTime}</p>
                            <p style="color:orange">价格:${g.pPrice}</p>
                        </div>
                    </div>
                </div>
            </c:forEach>
            <c:if test="${empty typeid}">
			<div class="pager1">
                <a href="${pageContext.request.contextPath}/goodsController?method=getPageDataByName&pageIndex=1&pName=${pName}">首页</a>
                <c:if test="${pageBean.pageIndex>1}">
                    <a href="${pageContext.request.contextPath}/goodsController?method=getPageDataByName&pageIndex=${pageBean.pageIndex-1}&pName=${pName}">上一页</a>
                </c:if>
               <c:forEach begin="${pageBean.start}" end="${pageBean.end}" var="i">
                   <a href="${pageContext.request.contextPath}/goodsController?method=getPageDataByName&pageIndex=${i}&pName=${pName}">${i}</a>
               </c:forEach>
                <c:if test="${pageBean.pageIndex<pageBean.pageCount}">
                    <a href="${pageContext.request.contextPath}/goodsController?method=getPageDataByName&pageIndex=${pageBean.pageIndex+1}&pName=${pName}">下一页</a>
                </c:if>
                <a href="${pageContext.request.contextPath}/goodsController?method=getPageDataByName&pageIndex=${pageBean.pageCount}&pName=${pName}">尾页</a>
            </div>
            </c:if>
            <c:if test="${not empty typeid}">
                <div class="pager1">
                    <a href="${pageContext.request.contextPath}/goodsController?method=getPageData&pageIndex=1&typeid=${typeid}">首页</a>
                    <c:if test="${pageBean.pageIndex>1}">
                        <a href="${pageContext.request.contextPath}/goodsController?method=getPageData&pageIndex=${pageBean.pageIndex-1}&typeid=${typeid}">上一页</a>
                    </c:if>
                    <c:forEach begin="${pageBean.start}" end="${pageBean.end}" var="i">
                        <a href="${pageContext.request.contextPath}/goodsController?method=getPageData&pageIndex=${i}&typeid=${typeid}">${i}</a>
                    </c:forEach>
                    <c:if test="${pageBean.pageIndex<pageBean.pageCount}">
                        <a href="${pageContext.request.contextPath}/goodsController?method=getPageData&pageIndex=${pageBean.pageIndex+1}&typeid=${typeid}">下一页</a>
                    </c:if>
                    <a href="${pageContext.request.contextPath}/goodsController?method=getPageData&pageIndex=${pageBean.pageCount}&typeid=${typeid}">尾页</a>
                </div>
            </c:if>
        </div>
    </div>
</div>
<!-- 底部 -->
<%@ include file="footer.jsp" %>
</body>
</html>