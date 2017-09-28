<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登录限制界面</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css"/>
<script type="text/javascript">
$(function(){
	//提交事件绑定
	$("#submit-id").click(function(event){
		var startTime = $("#startTime").val();
		var endTime = $("#endTime").val();
		var sMethod = $("#selectMethod").val();
		var isModify = ($("#isCanBeModify").is(":checked"))?1:0;
		if(!isHaveValue(startTime)){
			parent.$.showMsg("起始时间不能为空!", 2);
			return false;
		}
		if(!isHaveValue(endTime)){
			parent.$.showMsg("结束时间不能为空!", 2);
			return false;
		}
		if(startTime>=endTime){
			parent.$.showMsg("开始时间要小于结束时间!", 2);
			return false;
		}
		var loading = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			url:'<%=path%>/supervisory/asyn/modifyLimitInfo',
			data:{startTime:startTime,endTime:endTime,icbm:isModify,sMethod:sMethod},
			type:'post',
			success:function(data){
				var result=eval("(" + data + ")");
				if(result.success){
					parent.$.showMsg(result.message, 6, function(){
						parent.deleteTab("会计登录限制");
					}, 1000);
				}else{
					parent.$.showMsg(result.message, 5);
				}
			},
			error:function(jqXHR, textStatus, errorThrown ){
				parent.$.showMsg("亲!您的网络不给力哦~", 5)
			}
		});
	});
	$("#selectMethod").change(function(){
		isSelectAct($(this).val());
	});
	$("#selectMethod").val("${requestScope.selectMethod}");
	isSelectAct("${requestScope.selectMethod}");
	("${requestScope.isCanBeModify}"=="1")?$("#isCanBeModify").attr("checked",true):$("#isCanBeModify").attr("checked",false);
});

//时间区间状态控制
function isSelectAct(flag){
	var sVal = parseInt(flag);
	if(isNaN(sVal)){
		$("#selectMethod").val(0);
		$("#addEle").css("display","none");
	}else{
		if(sVal==1){
			$("#addEle").css("display","");
		}else{
			$("#addEle").css("display","none");
		}
	}
}

//是否有值
function isHaveValue(value){
	if(value!=null&&value!=""){
		return true;
	}else{
		return false;
	}
}
</script>
</head>
<body>
	<div class="well" width="80%" style="margin:20px 50px 20px 20px;">
	  	<form id="form-PWModify" class="form-horizontal" >
	  		<div class="control-group"><label class="control-label"  for="limitTimeTip">员工账号登陆权限设置:</label>
				<div class="controls" style="margin-top:10px;">
					<span style="display:inline;">
						<select id="selectMethod" style="width:120px;">
			  				<option value="0">不做任何限制</option>
			  				<option value="-1">禁止员工登录</option>
			  				<option value="1">限制登陆时间段</option>
			  			</select>
			  			<span id="addEle" style="display:none;">>>> (开始于: <input id="startTime" maxlength="5" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'HH:mm'});" value="${requestScope.startTime}" style="width:60px;height:20px;line-height:22px;"/>
			  			-截止于: <input id="endTime" maxlength="5" type="text" readonly="readonly" class="Wdate dfinput_fb" onFocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'HH:mm'});" value="${requestScope.endTime}" style="width:60px;height:20px;line-height:22px;"/>)</span>
					</span>
				</div>
			</div>
			<div class="control-group">&emsp;</div>
			<div class="control-group"><label class="control-label"  for="limitPwdModify">员工密码权限设置:</label>
				<div class="controls" style="margin-top:10px;">
					<input id="isCanBeModify" type="checkbox"/> 禁止员工修改以及找回密码
				</div>
			</div>
			<div class="control-group">
				<div class="controls"><input id="submit-id" type="button" style="margin-top:15px;width:70px;padding: 2px 6px 2px 6px" class="btn-sm btn-primary" value="提交"/></div>
			</div>
		</form>
	</div>
</body>
</html>