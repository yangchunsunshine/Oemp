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
	var haveImg = $("#haveImg").val();
	if(haveImg == 1){
		$("#fileNone").hide();
		$("#fileHave").show();
	}else{
		$("#fileNone").show();
		$("#fileHave").hide();
	}
});
function updateNew(){
	var title = $("#title").val();
	var context = $("#context").val();
	if(title==null||title==""){
		parent.$.showMsg("请输入标题!", 2);
		return;
	}
	if(context==null||context==""){
		parent.$.showMsg("请输入公告内容!", 2);
		return;
	}
	if(context.length > 450){
		parent.$.showMsg("公告内容最大长度为450字!", 2);
		return;
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url:'<%=path%>/supervisory/asyn/editMessage',
		beforeSubmit : function() {
			tips = layer.msg("正在修改,请稍后...", {icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code==1){
				parent.$.showMsg("修改成功!", 1, function(){
					closeFrame();
				}, 1000);
			}else if(result.code==2){
				parent.$.showMsg("修改失败,系统出错!", 2);
			}else if(result.code==3){
				parent.$.showMsg("请上传图片格式附件!", 2);
			}
		},
		error : function(result) {
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$("#editMessageForm").ajaxSubmit(options);
}

function deleteImageFile(){
	var newId = $("#id").val();
	layer.confirm("确认删除?", {icon: 3, title:"提示"}, function(){
		var tips = layer.msg("正在删除...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteImageFile',{'newId' : newId},function(result){
			layer.close(tips);
			if(result.code){
				$("#fileNone").show();
				$("#fileHave").hide();
				parent.$.showMsg("删除成功!", 1);
			}else{
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
		});
	});
}

function downLoadImageFile(){
	var newId = $("#id").val();
	$("#hrefDownLoad").attr("href","<%=path%>/supervisory/asyn/downLoadImageFile?newId="+newId);
}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.queryMessage();
	window.parent.layer.close(window.parent.loadEditMessage);
}

</script>
</head>
<body>
	<form id=editMessageForm enctype="multipart/form-data" method="POST">
		<div>
			<table id="editMessageTable" width="100%">
				<tr>
					<td align="left" width="15%">
						<h3 class="H3_class">标题：</h3>
					</td>
					<td align="left" width="85%">
						<input maxlength="50" type="text" id="title" name="title" value="${newInfo[0].title}" autocomplete="off" class="txt450" />
						<input type="hidden" id="id" name="id" value="${newInfo[0].newId}">
						<input type="hidden" id="haveImg" name="haveImg" value="${newInfo[0].haveImg}">
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">内容：</h3>
					</td>
					<td align="left">
						<textarea id="context" name="context" class="txt450">${newInfo[0].context}</textarea>
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">附件：</h3>
					</td>
					<td align="left">
						<div id=fileNone style="display:inline;">
							<input type='file' id='imageFile' name='imageFile' class='FileCss'/>
							<font style="font-weight:bold;color: red">不超过5M,任意图片格式</font>
						</div>
						<div id="fileHave" style="display:inline;">
							<a id="hrefDownLoad" onclick="downLoadImageFile();" class="bcssbtn" style="height:17px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px">下载</a>
							<input type="button" value="删除" onclick="deleteImageFile();" class="bcssbtn" style="height:20px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px"/>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div align="right" style="padding-right: 15px">
		<p style="padding-top: 25px">
			<input type="button" value="保存" style="padding: 2px 6px 2px 6px" class="bcssbtn" onclick="updateNew()" /> 
			<input type="button" value="关闭" style="padding: 2px 6px 2px 6px" class="acssbtn" onclick="closeFrame()"/>
		</p>
	</div>
	</form>
</body>
</html>