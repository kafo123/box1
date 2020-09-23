<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link href="css/bootstrap.min.css" rel="stylesheet">
		<script type="text/javascript" src="js/jquery.min.js"></script>
    <script type="text/javascript">

        var websocket = null;
        function abc() {

            //var username = localStorage.getItem("name");
            // var username=document.getElementById("me").value;
            var username=${orderID}+'';//获取到订单号,以此为参数,告诉服务端我是谁
            //判断当前浏览器是否支持WebSocket
            if ('WebSocket' in window) {
                websocket = new WebSocket("ws://" + document.location.host + "/MiShop/payment/websocket/"+username);
            } else {
                alert('当前浏览器 Not support websocket')
            }

            //连接发生错误的回调方法
            websocket.onerror = function() {
                setMessageInnerHTML("WebSocket连接发生错误");
            };

            //连接成功建立的回调方法
            websocket.onopen = function() {
                setMessageInnerHTML("WebSocket连接成功");
            }

            //接收到消息的回调方法
            websocket.onmessage = function(event) {  //{"code":1}
                //setMessageInnerHTML(event.data);
				//json字符串
				console.log(event.data+"--"+(event.data.code==1));
				//json字符串--->json对象
				var data = JSON.parse(event.data);

				console.log(data);
                if (data.code==1){
                    location.href="orderController?method=updateState&oid=${orderID}";//5

				}else{
                    alert('支付失败');
				}
            }

            //连接关闭的回调方法
            websocket.onclose = function() {
                setMessageInnerHTML("WebSocket连接关闭");
            }

            //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
            window.onbeforeunload = function() {
                closeWebSocket();
            }
        }
        //关闭WebSocket连接
        function closeWebSocket() {
            if (websocket!=null) {

                websocket.close();
            }
        }

        /**
         * 直接将收到的数据显示到页面上,此处应该根据实际的业务逻辑来决定跳转页面
         * @param data
         */
        function setMessageInnerHTML(data) {
            document.getElementById("neirong").innerHTML = data;

        }
        window.onLoad=abc();

    </script>
		
<link rel="stylesheet" type="text/css" href="css/login.css">

<title>微信支付</title>
</head>
<body>
	<div class="regist">
		<div class="regist_center">
			<div class="regist_top">
				<div class="left fl"><span class="glyphicon glyphicon-user"></span>&nbsp;&nbsp;会员注册</div>
				<div class="right fr">
					<a href="index.jsp" target="_black">小米商城</a>
				</div>
				<div class="clear"></div>
				<div class="xian center"></div>
			</div>
			<div  style="margin-top: 80px;margin-left: 100px;">
					<h1>
					您支付的订单是:${orderID}
					</h1>
					请扫描支付:
					<img src="${pageContext.request.contextPath}/payment/image?method=scanCode" alt="刷新">

				  <h1 id="neirong"></h1>
					
					<a class="btn btn-default" href="${pageContext.request.contextPath }/index.jsp" target="_blank">返回主页</a>
					
			</div>
		</div>
	</div>
	
</body>
</html>