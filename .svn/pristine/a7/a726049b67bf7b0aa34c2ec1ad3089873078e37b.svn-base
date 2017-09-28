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
	$("#ifPushCus:checkbox").click(function(){
		 ifPushInfo();
	});
	 ifPushInfo();
});

function ifPushInfo(){
	if($("#ifPushCus:checkbox").attr("checked") == "checked"){
		$("#ifPush").text("是");
	}else{
		$("#ifPush").text("否");
	}
}

function pushMessage(){
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
		timeout:'500000',
		url:'<%=path%>/supervisory/asyn/pushMessage',
		beforeSubmit : function() {
			tips = layer.msg("正在发布,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code==1){
				parent.$.showMsg("发布成功!", 1, function(){
				closeFrame();
			}, 1000);
			}else if(result.code==2){
				parent.$.showMsg("发布失败,系统出错!", 2);
			}else if(result.code==3){
				parent.$.showMsg("请上传图片格式附件!", 2);
			}else if(result.code==4){
				parent.$.showMsg("发布重复的公告!", 2);
			}else if(result.code==5){
				parent.$.showMsg("上传图片不能大于5M!", 2);
			}
		},
		error : function(result) {
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$("#pushMessageForm").ajaxSubmit(options);
}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.getPushMessage();
	window.parent.layer.close(window.parent.loadPushMessage);
}

</script>
</head>
<body>
	<form id=pushMessageForm enctype="multipart/form-data" method="POST">
		<div>
			<table id="pushMessageTable" width="100%">
				<tr>
					<td align="left" width="15%">
						<h3 class="H3_class">标题：</h3>
					</td>
					<td align="left" width="85%">
						<input name="title" maxlength="50" type="text" id="title" autocomplete="off" class="txt450" />
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">内容：</h3>
					</td>
					<td align="left">
						<textarea id="context" name="context" class="txt450"></textarea>
					</td>
				</tr>
				<tr>
					<td align="left">
						<h3 class="H3_class">附件：</h3>
					</td>
					<td align="left">
						<div id=fileDiv style="display:inline;">
							<div style="display: inline;">
								<input type='file' id='imageFile' name='imageFile' class='FileCss'/>
								<font style="font-weight:bold;color: red">不超过5M,任意图片格式</font>
							</div>
							<div style="display: inline;padding-left: 54px">
								<input type="checkbox" id='ifPushCus' name='ifPushCus'/>
								<font>是否推送客户:</font><font id="ifPush" style="font-weight:bold;color: red">否</font>
							</div>
						</div>
					</td>
				</tr>
			</table>
		</div>
		<div align="right" style="padding-right: 15px">
		<p style="padding-top: 25px">
			<input type="button" value="保存" style="padding: 2px 6px 2px 6px" class="bcssbtn" onclick="pushMessage()" /> 
			<input type="button" value="关闭" style="padding: 2px 6px 2px 6px" class="acssbtn" onclick="closeFrame()"/>
		</p>
	</div>
	</form>
</body>
</html>