<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>新增公告信息</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<script type="text/javascript">
$(function(){
	$("#mesImage").prop("src",'<%=path%>/supervisory/asyn/getMessageImage?id=' + ${result.id});
})

function closeFrame(){
	window.parent.layer.close(window.parent.showNoticeWindow);
}

function showBigImage(){
	var imgUrl = $("#mesImage").attr("src");
	var html = '<div align="center"><img height="450" width="450"  alt="图片预览" src="' + imgUrl + '"></div>'
	window.parent.layer.open({
		title:'附件预览',
		type: 1,
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		content: html,
		area: ['600px', '500px']
	});
}

</script>
</head>
<body>
	<form id=pushMessageForm enctype="multipart/form-data" method="POST">
		<div style="padding:0px 15px;">
			<table id="pushMessageTable" width="100%">
				<tr>
					<td align="left" width="15%">
						<h3 class="H3_class">标题：</h3>
					</td>
					<td align="left" width="85%">
						<input name="title" style="border: 0px" readonly="readonly" maxlength="50" type="text" id="title" autocomplete="off" class="txt450" value="${result.title}" />
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">内容：</h3>
					</td>
					<td align="left">
						<textarea id="context" style="height: 200px" readonly="readonly" name="context" class="txt450">${result.context}</textarea>
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">附件：</h3>
					</td>
					<td align="left" style="height: 150px">
						<img id="mesImage" alt=" " onclick="showBigImage()" width="150px" height="150px">
					</td>
				</tr>
			</table>
		</div>
		<div align="right" style="padding-right: 15px;text-align:center;">
		<p>
			<input type="button" value="关闭" style="padding: 2px 6px 2px 6px" class="acssbtn" onclick="closeFrame()"/>
		</p>
	</div>
	</form>
</body>
</html>