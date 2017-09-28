<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>提 示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<meta content="all" name="robots" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<style>
.error_500 {
	width: 500px;
	margin: 100px auto;
	background: #fff;
	box-shadow: 1px 1px 2px 0 rgba(0, 0, 0, .3);
	padding: 30px 50px 50px;
	font-family: "microsoft yahei";
	font-size: 14px;
	color: #404040;
}

.error_500 h1 {
	padding-bottom: 20px;
	font-size: 26px;
}

.error_500 p {
	line-height: 30px;
}

.error_500 h1 span {
	font-weight: bold;
	font-size: 60px;
	padding-right: 15px;
}
</style>
</head>

<body bgcolor="#f3f3f3">
	<div class="error_500">
		<h2>
			<span>该功能未授权</span>
		</h2>
		<p>☉ 出现该错误的原因是：</p>
		<UL>
			<LI>该功能当前用户不能访问,请联系管理员。</LI>
			<LI>退出系统清空浏览器缓存并重新登录。</LI>
			<LI>单击<A href="javascript:history.back(1)"><FONT
					color=#ff0000>后退</FONT>
			</A>按钮尝试另一个链接。</LI>
		</UL>
		<p>☉代帐公司后台管理系统</p>
		<p>版权所有：微宝科技</p>
	</div>
</body>
</html>
