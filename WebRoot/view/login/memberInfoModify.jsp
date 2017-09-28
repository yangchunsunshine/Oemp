<%@page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>登陆人员信息(主页面)</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css"/>
<style type="text/css">
/*新加样式*/
.right_n1 { float:left; width:300px; height:auto; display:block; margin-right:10px;padding-left: 70px}
.right_n2 { float:right; width:300px; height:auto;}
</style>
<script type="text/javascript">
$(function(){
	var ifCanSaveMore = '${ifCanSaveMore}'
	if(ifCanSaveMore == 1){
		$("#one_btn").hide();
		$("#payDiv").show();
	}else{
		$("#one_btn").show();
		$("#payDiv").hide();
	}
	if('${isAdmin}' == 'false'){
		$("#cardInfoDiv").hide();
		$("#submit-id").hide();
		$("#cardInfo-id").hide();
		$("input").attr("readOnly","readOnly");
		$("input").attr("disabled","disabled");
		$("#orgDepict").attr("readOnly","readOnly");
		$("#orgCatchArea").attr("readOnly","readOnly");
	}
	var tips;
	//提交事件绑定
	$("#submit-id").click(function(event){
		var options = {
			type : 'post',
			url : '<%=path%>/supervisory/asyn/updateMemberInfo',
			dataType : 'json',
			beforeSubmit : function() {
				var mngName = $("#mngName").val();
				var mngEmail = $("#mngEmail").val();
				var mngOrg = $("#mngOrg").val();
				var hotline = $("#hotline").val();
				var cardMaster = $("#cardMaster").val();
				var cardNo = $("#cardNo").val();
				var payId = $("#payId").val();
				var aliPay = $("#aliPay").val();
				if(!isHaveValue(mngName)){
					parent.$.showMsg('管理员名称不能为空!', 2);
					return false;
				}
				if(!isHaveValue(mngOrg)){
					parent.$.showMsg('代帐公司名称不能为空!', 2);
					return false;
				}
				tips = layer.msg("正在保存...",{icon : 16,time : 0, shade: [0.1]});
			},
			success : function(result) {
				layer.close(tips);
				if(result.success){
					parent.$.showMsg(result.message, 6,function(){
						parent.deleteTab("个人信息");
					});
				}else{
					parent.$.showMsg(result.message, 5);
				}
			},
			error : function(result) {
				layer.close(tips);
				parent.$.showMsg("亲!您的网络不给力哦~", 2);
			}
		};
		$('#mntMemberForm').ajaxSubmit(options);
	});
	$("#one_btn").click(function(event){
		var options = {
			type : 'post',
			url : '<%=path%>/supervisory/asyn/updateMemberInfo',
			dataType : 'json',
			beforeSubmit : function() {
				var mngName = $("#mngName").val();
				var mngEmail = $("#mngEmail").val();
				var mngOrg = $("#mngOrg").val();
				var hotline = $("#hotline").val();
				var cardMaster = $("#cardMaster").val();
				var cardNo = $("#cardNo").val();
				var payId = $("#payId").val();
				var aliPay = $("#aliPay").val();
				if(!isHaveValue(mngName)){
					parent.$.showMsg('管理员名称不能为空!', 2);
					return false;
				}
				if(!isHaveValue(mngOrg)){
					parent.$.showMsg('代帐公司名称不能为空!', 2);
					return false;
				}
				tips = layer.msg("正在保存...",{icon : 16,time : 0, shade: [0.1]});
			},
			success : function(result) {
				layer.close(tips);
				if(result.success){
					parent.$.showMsg(result.message, 6,function(){
						parent.deleteTab("个人信息");
					});
				}else{
					parent.$.showMsg(result.message, 5);
				}
			},
			error : function(result) {
				layer.close(tips);
				parent.$.showMsg("亲!您的网络不给力哦~", 2);
			}
		};
		$('#mntMemberForm').ajaxSubmit(options);
	});
});

//是否有值
function isHaveValue(value){
	if(value!=null&&value!=""){
		return true;
	}else{
		return false;
	}
}

function showImage(size){
	var imgUrl;
	var html;
	if(size == "BIG"){
		imgUrl = getFileUrl("bigLogo");
	}else{
		imgUrl = getFileUrl("littleLogo");
	}
	if(imgUrl){
	    html = '<div align="center"><img height="450" width="450"  alt="LOGO预览" src="' + imgUrl + '"></div>'
	}else{
		html = '<div align="center"><img height="450" width="450"  alt="LOGO预览" src="<%=path%>/supervisory/syn/getCompanyLogo?size=' + size + '"></div>'
	}
	layer.open({
		title:'LOGO预览',
		type: 1,
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
		content: html,
		area: ['500px', '500px']
	});
}

function getFileUrl(sourceId) {
	var url;
	if (navigator.userAgent.indexOf("MSIE")>=1){
		url = document.getElementById(sourceId).value;
	}else if(navigator.userAgent.indexOf("Firefox")>0){
		var file = document.getElementById(sourceId).files.item(0)
		if(!file) return false;
		url = window.URL.createObjectURL(file);
	}else if(navigator.userAgent.indexOf("Chrome")>0){
		var file = document.getElementById(sourceId).files.item(0);
		if(!file) return false;
		url = window.URL.createObjectURL(file);
	}
	return url;
}
</script>
</head>
<body>
	<div class="well" style="margin:20px 50px 20px 20px;height: 92%;">
	  	<form id="mntMemberForm" class="form-horizontal" enctype="multipart/form-data">
	  	<div style="display: block;float: left;margin:0px 50px 0px 00px;">
	  		<div><label class="control-label" >姓名:</label>
				<div><input id="mngName" name="userName" value="${userInfo.userName}" maxlength="5" placeholder="管理员真实姓名" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >联系方式:</label>
				<div><input id="mngTel" name="telphone" disabled="disabled" value="${userInfo.telphone}" maxlength="15" placeholder="管理员登录电话" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >公司名称</label>
				<div><input id="mngOrg" name="orgName" value="${userInfo.orgName}" maxlength="40" placeholder="所在代帐公司" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >公司咨询电话</label>
				<div><input id="hotline" name="hotline" value="${userInfo.hotline}" maxlength="20" placeholder="业务咨询电话" type="text" style="width:270px;"/></div>
			</div>
			<div id="cardInfoDiv">
			<div><label class="control-label" >收款持卡人</label>
				<div><input id="cardMaster" name="cardMaster" value="${userInfo.cardMaster}" maxlength="20" placeholder="催费短信中收款持卡人" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >收款银行卡号</label>
				<div><input id="cardNo" name="cardNo" value="${userInfo.cardNo}" maxlength="20" placeholder="催费短信中收款银行卡" type="text" style="width:270px;"/></div>
			</div>
			</div>
			<div><label class="control-label" >邮箱</label>
				<div><input id="mngEmail" name="email" value="${userInfo.email}" maxlength="40" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >商户号</label>
				<div><input id="payId" name="payId" value="${userInfo.payId}" maxlength="40" type="text" style="width:270px;"/></div>
			</div>
			<div><label class="control-label" >支付宝账号</label>
				<div><input id="aliPay" name="aliPay" value="${userInfo.aliPay}" maxlength="40" type="text" style="width:270px;"/></div>
			</div>
		</div>
		<div>
            <div><input id="one_btn" type="button" style="margin-top:328px;margin-left: 10px; width:70px;" class="btn-sm btn-primary" value="保存信息"/></div>
        </div>
		<div id="payDiv" style="display: none;height: 80%">
        	<div class="right_n1">
                <div><label class="control-label" >公司描述信息</label>
                    <div><textarea id="orgDepict" name="orgDepict" maxlength="500" placeholder="描述企业基本信息" style="width:270px; height:90px;">${userInfo.orgDepict}</textarea></div>
                </div>
                <div style="margin-top:25px;"><label class="control-label" >公司服务范围</label>
                    <div><textarea id="orgCatchArea" name="orgCatchArea"  maxlength="500" placeholder="企业经营项目描述" style="width:270px;height:90px;">${userInfo.orgCatchArea}</textarea></div>
                </div>
                <div style="margin-top:46px;"><label class="control-label" >公司简称</label>
                    <div><input id="orgSortName" name="orgSortName" value="${userInfo.orgSortName}" maxlength="10" placeholder="代帐公司简称" type="text" style="width:270px;"/></div>
                </div>
            </div>
            <!--右侧第二部分-->
            <div class="right_n2">
                <div><label class="control-label" >公司LOGO(大)</label>
                    <div>
                        <input id="bigLogo" style="display: inline-block;" name="bigLogo" placeholder="公司LOGO(大图标)" type="file" style="width:270px;"/>
                        <span style="cursor: pointer;" onclick="showImage('BIG')">预览</span>
                    </div>
                </div>
                <div><label class="control-label" >公司LOGO(小)</label>
                    <div>
                        <input id="littleLogo" style="display: inline-block;" name="littleLogo"  placeholder="公司LOGO(小图标)" type="file" style="width:270px;"/>
                        <span style="cursor: pointer;" onclick="showImage('LIT')">预览</span>
                    </div>
                </div>
                <div>
                    <div><input id="submit-id" type="button" style="margin-top:225px;margin-left: 200px; width:70px;" class="btn-sm btn-primary" value="保存信息"/></div>
                </div>
            </div>
            <!--右二结束-->
		</div>
		</form>
	</div>
</body>
</html>
