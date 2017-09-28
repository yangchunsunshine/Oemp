<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml"> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/common/TabPanel.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/common/Main.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/TabPanel.css" />
<%-- <script language="javascript">
var winHeight = $(window).height();
var winWidth = $(window).width();
var ifAdmin = $.getCookie('isAdmin');
var heartbeat=window.setInterval("heartbeatfunc()",parseInt('${sessionScope.beatTimeForMng}'));
$(function(){
	if(ifAdmin == 'true'){
		$("#userNameTD").append('&nbsp;&nbsp;(管理员)');
	}else{
		$("#userNameTD").append('&nbsp;&nbsp;(员工)');
	}
	menuContro();
	heartbeatfunc();
	$("#userTel").mustNumber();
	if (document.all&&document.getElementById) {
		navRoot = document.getElementById("nav");
		for (i=0; i<navRoot.childNodes.length; i++) {
			node = navRoot.childNodes[i];
			if (node.nodeName=="LI") {
				node.onmouseover=function() {this.className+=" over";}
	 			node.onmouseout=function() {this.className=this.className.replace(" over", "");}
			}
		}
	}
	
	//初始化该用户可切换的角色数据
	initRoleDate();
});

//心跳执行函数
var init = true;
function heartbeatfunc(){
	$.ajax({ 
		type:"GET", 
		url : "<%=path%>/supervisory/asyn/heartbeatForMng", 
		success : function(data) {
			var result=eval("("+data+")");
			if(result.success){
				if(parseInt(result.message)>0){
					$("#msgTip").css("display","block");
				}else{
					$("#msgTip").css("display","none");
				}
				$("#msgCountTip").html(result.message);
				if($("#frame在线状态").length>0){
					$("#frame在线状态")[0].contentWindow.reloadSelf();
				}
				if($("#iframIndex").length>0 && init == false){
					$("#iframIndex")[0].contentWindow.location.reload();
				}
				init = false;
			}
		},
		error : function(XMLHttpRequest,textStatus,errorThrown){
			layer.close(loading);
			$.showMsg("亲!您的网络不给力哦~", 5);
		} 
	});
}

function initRoleDate(){
	var tel = '${sessionScope.userInfo.empInfo.telphone}'; //当前用户登录的手机号
	$.ajax({
		type:"POST",
		dataType : "json",
		data:{tel:tel},
		url : "<%=path%>/supervisory/asyn/findOtherRoleInfo",
		success : function(_result){
			var data = _result.result;
			$("#changeRoleDiv").empty();
			var ul = '<ul style="border:1px solid #ccc;">';
			var title = '<li style="background-color:#ececec;text-align:center;height:20px; line-height:20px; border-bottom:1px solid #ccc;"><font color="#666666">切换用户</font></li>';
			ul = ul + title;
			$.each(data,function(i,item){
				//var tr = '<tr><td><input type="checkbox" id="mntCheck" value="'+ item.id +'" />&nbsp&nbsp&nbsp<span style="font-size:15px;">'+ item.name +'</span></td></tr>'
				//$("#mntTable").append(tr);
				var li = '<a onclick="changRole('+item.id+','+item.userType+')" style="color:#999999"><li style="height:28px; line-height:28px;">';
				li = li+item.orgName;
				var role ="";
				if(item.userType=='1'){
					role = '<span style="float:right;">（管理员）</span>';
				}else{
					role = '<span style="float:right;">（员工）</span>';
				}
				li = li + role ;
				li = li +'</li></a>';
				ul = ul+li;
			});
			ul = ul+'</ul>';
			$("#changeRoleDiv").append(ul);
		},
		error : function(XMLHttpRequest,textStatus,errorThrown){
			$.showMsg('亲!您的网络不给力哦~', 5);
		}
	});
}
function changRole(id,userType){
	var tel = '${sessionScope.userInfo.empInfo.telphone}'; //当前用户登录的手机号
	var lloading = layer.msg("正在切换...", {icon : 16, shade: false, time : 0});
	$.ajax({ 
		type:'POST',
		url : '<%=path%>/supervisory/asyn/changLoginPlatform',
		data: {telphone:tel,userType:userType}, 
		dataType:'json',
		success : function(result) {
			layer.close(lloading);
			if(result.success){
				window.location.href='<%=path%>/supervisory/forward/gotoMainFrame';
	        }else{
	        	layer.msg(result.message, {icon : 4, time : 1500});
	        }
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
			layer.msg("亲!您的网络不给力哦~ ", {icon : 5, time : 1500});
		}
	});
	
}
//添加tab页
function addOptionTab(url,name){
	$(this).parents(".float_menu").hide();
	if($('#frame'+name).length>0){
		if($('#frame'+name).attr("src").indexOf(url)<0){
			$('#frame'+name).attr("src",url);
		}
	}
	tabpanel.addTab({id:name,title:name,html:'<iframe id="frame'+name+'" src="'+url+'" width="100%" height="100%" frameborder="0"></iframe>',closable: true});
}
//消息tab页处理函数
function MessageDispather(msgId,path,tabName,condition){
	if(tabName==null||tabName==""){
		window.open('<%=path%>/supervisory/forward/messageDispatherForMng?path='+path+'&msgId='+msgId+'&condition='+condition);
	}else{
		$(this).parents(".float_menu").hide();
		$('#frame'+tabName).attr("src",'<%=path%>/supervisory/forward/messageDispatherForMng?path='+path+'&msgId='+msgId+'&condition='+condition);
		tabpanel.addTab({id:tabName,title:tabName,html:'<iframe id="frame'+tabName+'" src="'+'<%=path%>/supervisory/forward/messageDispatherForMng?path='+path+'&msgId='+msgId+'&condition='+condition+'" width="100%" height="100%" frameborder="0"></iframe>',closable: true});
	}
}
//员工工作详细处理函数
function clerkDetailDispather(telphone,period,flag){
	$(this).parents(".float_menu").hide();
	if(flag==0){
		$('#frame结账信息').attr("src",'<%=path%>/supervisory/forward/gotoSettleDetail?telphone='+telphone+'&period='+period);
		tabpanel.addTab({id:'结账信息',title:'结账信息',html:'<iframe id="frame结账信息" src="'+'<%=path%>/supervisory/forward/gotoSettleDetail?telphone='+telphone+'&period='+period+'" width="100%" height="100%" frameborder="0"></iframe>',closable: true});
	}else if(flag==1){
		$('#frame报税信息').attr("src",'<%=path%>/supervisory/forward/gotoTaxDetail?telphone='+telphone+'&period='+period);
		tabpanel.addTab({id:'报税信息',title:'报税信息',html:'<iframe id="frame报税信息" src="'+'<%=path%>/supervisory/forward/gotoTaxDetail?telphone='+telphone+'&period='+period+'" width="100%" height="100%" frameborder="0"></iframe>',closable: true});
	}else if(flag==2){
		$('#frame工作概要').attr("src",'<%=path%>/supervisory/forward/gotoClerkDetail?telphone='+telphone+'&period='+period);
		tabpanel.addTab({id:'工作概要',title:'工作概要',html:'<iframe id="frame工作概要" src="'+'<%=path%>/supervisory/forward/gotoClerkDetail?telphone='+telphone+'&period='+period+'" width="100%" height="100%" frameborder="0"></iframe>',closable: true});
	}
}
//删除标签
function deleteTab(name){
	$("#"+name).remove();
	$("#frame"+name).parent(".html_content").remove();
	tabpanel.addTab({id:'index',title:'首页',html:'<iframe src="'+'<%=path%>/supervisory/forward/gotoHome'+'" width="100%" height="100%" frameborder="0"></iframe>',closable: false});
}
//子页面调用此方法刷新平级iframe页
function refrashSubject(name){
	if($("#frame"+name).length>0){
		$("#frame"+name)[0].contentWindow.reloadGrid();
	}
}

//公司交接
function rawOrgToOrther(){
	var memberId = '${sessionScope.userInfo.orgId}';
	var mntName  = '${sessionScope.userInfo.orgName}';
	$("#memberId").val(memberId);
	$("#userTel").val("");
	$("#userInfo").remove();
	$("#mntName").text(mntName);
	$("#mntName").css("color","red");
	layer.open({
		type:1,
		title : '用户信息查询:',
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
	    area: ['350px', '250px'],
	    content: $('#serchDiv'),
	    success : function(){
	    	$("#userTel").focus();
	    	$(document).keydown(function(event){ 
				if(event.keyCode==13){ 
					searchUser();
				} 
			});
			findControEmpInfo(memberId);
	    }
	})
}
function searchUser(){
	var tel = $("#userTel").val();
	if(!tel || tel.length != 11){
		$.showMsg("请输入11位手机号!", 2);
		return false;
	}
	var tips = layer.msg("检索中...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({ 
		type:"POST", 
		dataType : "json",
		url : "<%=path%>/supervisory/asyn/getUserInfo",
		data: {tel:tel},
		success : function(result) {
			layer.close(tips);
			if(result.success){
				//有结果,显示查询人信息
				var data = result.data;
				$("#userInfo").remove();
				var inHtml = "<div id='userInfo'><br/><span style=\"font-size: 15px;\">用&nbsp;户&nbsp;名&nbsp;：</span><span style=\"color: red;font-size: 15px;\">"+data.userName+"</span>";
				if(${sessionScope.userInfo.orgId} == data.id){
					inHtml+="（当前用户）";
				}else{
					var memberId = $("#memberId").val();
					inHtml+="<br/><br/><button onclick='rowOrgToOrther("+data.id+")' style='margin-left: 120px;height:26px;width: 80px;background-color: #c0c0c0;'>确认交接</button>";
				}
				inHtml+="</div>";
				$("#serchDiv").append(inHtml);
			}else{
				$.showMsg(result.message, 5);
			}
		},
		error:function(XMLHttpRequest,textStatus,errorThrown){
			layer.close(loading);
			$.showMsg("亲!您的网络不给力哦~", 5);
		}
	});
}
//交接
function rowOrgToOrther(toId){
	var checkBox = $("input[id='mntCheck']:checked");
	var ids = "";
	$.each(checkBox,function(i,item){
		ids = ids + $(item).val() + "@!$@";
	});
	layer.confirm('您将失去被交接员工的所有监控权限!', 
		{
		    icon : 2,
		    title : "提示信息",
	        btn: ['确定交接','取消']
	    },
        function(){
			var tips = layer.msg("正在处理...", {icon : 16,time : 0, shade: [0.1]});
			$.ajax({
				type:"POST", 
				url : "<%=path%>/supervisory/asyn/rawOrgToOther",
				data: {toId:toId,ids : ids},
				success : function(result){
					$.showMsg(result.msg, 6, function(){
						layer.close(tips);
						window.location.reload();
					});
				},
				error : function(XMLHttpRequest,textStatus,errorThrown){
					layer.close(tips);
					$.showMsg('亲!您的网络不给力哦~', 5);
				}
			});
        }, 
        function(){
            return;
        }
    );
}

function findControEmpInfo(memberId){
	$.ajax({
		type:"POST",
		dataType : "json",
		url : "<%=path%>/supervisory/asyn/findControEmpInfo",
		success : function(_result){
			var data = _result.result;
			$("#mntTable").empty();
			$.each(data,function(i,item){
				var tr = '<tr><td><input type="checkbox" id="mntCheck" value="'+ item.id +'" />&nbsp&nbsp&nbsp<span style="font-size:15px;">'+ item.name +'</span></td></tr>'
				$("#mntTable").append(tr);

			});
		},
		error : function(XMLHttpRequest,textStatus,errorThrown){
			$.showMsg('亲!您的网络不给力哦~', 5);
		}
	});
}

function choseMnt(){
	var _thisTips = layer.open({
		type:1,
		title : '交接员工选择:',
		border: [ 0 ],
		shade : [ 0.1, '#000' ],
		fix : true,
		maxmin: true,
		shadeClose: true,
        btn: ['确定','清空'],
	    area: ['200px', '230px'],
	    content: $('#mntSearch'),
	    yes : function(){
			layer.close(_thisTips);
	    },
	    cancel : function(){
	    	$("input[id='mntCheck']:checked").prop("checked",false);
	    }
	});
}

function autoSub(obj){
    if(/^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/.test(obj.value)){
    	searchUser();
    }
}

function logOut(){
	$.delCookie("ACC_ROLER_URL");
	$.delCookie("memberId");
	$.delCookie("isAdmin");
}

function showRole(){
	$("#changeRoleDiv").show();
}
function hideRole(){
	$("#changeRoleDiv").hide();
}
</script> --%>
</head>
<body>
<div class="top_black">
	<div class="top_left">
        <table cellpadding="0" cellspacing="0" class="table_logo">
            <tr>
                <%-- <td width="87"><img src="<%=path%>/style/image/top_logo.png"/></td> --%>
                <td class="company">${sessionScope.userInfo.orgName} </td>         
            </tr>
        </table>
    </div>
	<div class="top_right">
      <table cellpadding="0" cellspacing="0" class="table_top" style="width:568px;">
            <tr>
                <td id="userNameTD" width="200">您好！${sessionScope.userInfo.userName}</td>
                
                <td width="18" align="center"><img src="<%=path%>/style/image/strip_black.png"/></td>
                <td width="14"><img src="<%=path%>/style/image/change_role.png"/></td>
                <td width="60"><a href="javascript:void(0);" onmouseover="showRole();" >切换用户</a></td>
                
                <td width="18" align="center"><img src="<%=path%>/style/image/strip_black.png"/></td>
                <td width="14"><img src="<%=path%>/style/image/ss-01.png"/></td>
                
                <td width="60"><a href="javascript:void(0);" onclick="addOptionTab('<%=path%>/supervisory/forward/gotoManagerInfoModify','个人信息');">个人信息</a></td>
                <td width="18" align="center"><img src="<%=path%>/style/image/strip_black.png"/></td>  
                       
                <td width="24" valign="middle"><a href="javascript:void(0);" onclick="addOptionTab('<%=path%>/supervisory/forward/gotoLookMail','消息列表');" class="red-dot_black"><img id="msgTip" class="img_reddot"/></a></td>
                <td width="10" style="text-align:left;"><div id="msgCountTip">0</div></td>
                <td width="18" align="center"><img src="<%=path%>/style/image/strip_black.png"/></td>
                <td width="20"><img src="<%=path%>/style/image/ss-03.png"/></td>
                <td width="30"><a href="<%=path%>/supervisory/forward/gotoHelp" target="_blank">帮助</a></td>
                <td width="18" align="center"><img src="<%=path%>/style/image/strip_black.png"/></td>
                <td width="20"><img src="<%=path%>/style/image/ss-04.png"/></td>
                <td width="30"><a href="<%=path%>/supervisory/forward/logout" onclick="logOut()">退出</a></td>
                <td width="10"></td>
            </tr>
        </table>
    </div>
    		
	</div>
</div>
<div class="content" >
	<div id="leftmenu" class="main_menu">
        <ul id="nav"> 
         <li id="cusManage" mes="menu" mes="menu" ><a href="#" class="menu_first" id="menu_four">模板 </a>
         	<ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu"> 
                 <li id="/supervisory/forward/gotoCustomSetting" mes="menu" "> <a href="#" class="menu_second menu_list" url="<%=path%>/kpi/forward/model">模板列表</a></li>
<%--                  <li id="/supervisory/forward/gotoCustomPayLook" mes="menu" "> <a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoCustomPayLook">缴费管理</a></li>
 --%>                  <!--  <li id="/supervisory/forward/gotoFeedBackManage" mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoFeedBackManage">客户评价</a></li> -->
             </ul>
         </li>
          <li id="empWorkDetail" mes="menu" ><a href="#" class="menu_first" id="menu_five">指标</a>
         	<ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu"> 
<%-- 		         <li id="/kpi/kpi/gotokpi"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/kpi/forward/gotokpi">指标</a></li>
 --%>		         <li id="/kpi/kpi/gotokpi2"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/kpi/forward/xinzengzhibiao">指标集</a></li>
<%-- 		         <li id="/kpi/kpi/gotokpi1"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/kpi/show/kpis">指标2</a></li>
 --%>		         <li id="/supervisory/forward/gotoSettleDetail" mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoSettleDetail">公式</a></li>
		         <li id="/supervisory/forward/gotoTaxDetail"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoTaxDetail?telphone=&period=">数据验证</a></li> 
			</ul>
         </li>
         <li id="busFrameWork" mes="menu" ><a href="#" class="menu_first" id="menu_seven">工具</a>
             <ul style="width:212px; border:1px #bfbfbf solid; background-color:#FFF;" class="float_menu"> 
                 <li id="/supervisory/forward/gotoPayAudit"        mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoPayAudit">收费审批</a></li>
                 <li id="/supervisory/forward/gotoClerkFeeList"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoClerkFeeList">收费统计</a></li>
                 <li id="/supervisory/forward/gotoFrameworkManage" mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoFrameworkManage">组织架构</a></li>
                 <li id="/supervisory/forward/gotoClerkTaxList"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoClerkTaxList">税金统计</a></li>
                 <li id="/supervisory/forward/gotoPowerManage"     mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoPowerManage">角色权限</a></li>
                 <li id="/supervisory/forward/gotoClerkVchList"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoClerkVchList">凭证统计</a></li>
                 <!-- <li id="/supervisory/forward/gotoFeedBackQuery"   mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoFeedBackQuery">评价查询</a></li> -->
             	 <li id="/supervisory/forward/gotoWithdrawalManage"     mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoWithdrawalManage">发起提现</a></li>
             	 <li id="/supervisory/forward/gotoWithdrawalRecList"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoWithdrawalRecList">提现记录</a></li>
             	 <li id="/supervisory/forward/gotoSettlementManage"     mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoSettlementManage">代账订单</a></li>
             </ul>
         </li> 
        <!-- add by hechunyang 20160913 begin -->
         <li id="businessManager" mes="menu" "><a href="#" class="menu_first" id="menu_seven">业务管理</a>
             <ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu">  
                 <li id="/supervisory/forward/gotoBusinessModel"   mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/business/forward/gotoBusinessView?pageName=model">业务模板</a></li>
                <li id="/business/forward/gotoProgressList"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/business/forward/gotoBusinessView?pageName=progress">进度查询</a></li>
             </ul>
         </li> 
         <!-- add by hechunyang 20160913 end -->
        <!-- add by hechunyang 20161017 增加发送短信按钮 begin-->
         <%-- <li id="smsManager" mes="menu" "><a href="#" class="menu_first" id="menu_seven">短信管理</a>
             <ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu">  
                 <li id="/sendmessage/forward/gotoSendMessageMain"   mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/sendmessage/forward/gotoSendMessageMain">发送短信</a></li>
                <li id="/sendmessage/forward/gotoMessage"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/sendmessage/forward/gotoMessage">短信记录</a></li>
                <li id="/sendmessage/forward/gotoMessageFenye"    mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/sendmessage/forward/gotoMessageFenye">短信记录2</a></li>             </ul>
         </li>  --%>
         <!-- add by hechunyang 增加发送短信按钮 end -->
         <li id="sysSetting" mes="menu" ><a href="#" class="menu_first" id="menu_six">系统设置</a>
             <ul style="width:212px; border:1px #bfbfbf solid; background-color:#FFF;" class="float_menu"> 
                 <li id="function_raw"                             mes="menu" "><div align="center"><a href="" onclick="rawOrgToOrther();return false" class="menu_second menu" >权限交接</a></div></li>
                 <li id="/supervisory/forward/gotoAuditSettings"   mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoAuditSettings">审批设置</a></li>
                 <li id="/supervisory/forward/gotoEmpLoginLimited" mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoEmpLoginLimited">登陆权限</a></li>
                 <li id="/supervisory/forward/gotoPasswordModify"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoPasswordModify">密码修改</a></li>
            <%-- <li id="/supervisory/forward/gotoProcessSetting"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoProcessSetting">业务管理</a></li>--%>             	 
                 <li id="/supervisory/forward/gotoUpdateLog"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoUpdateLog">更新日志</a></li>
             	 <li id="/supervisory/forward/gotoAlipaySetting"  mes="menu" "><a href="#" class="menu_second menu_list" url="<%=path%>/supervisory/forward/gotoAlipaySetting">支付宝设置</a></li>
             </ul>
         </li> 
        </ul>
  	</div>
	<div id="main_right" class="main_right"></div>
</div>
<!-- <div id="serchDiv" style="display:none;padding: 15px 0px 0px 20px">
    	<span style="font-size: 15px;">当前交接监控系统&nbsp;：</span><span id="mntName" style="font-size: 15px;"></span>
    	<br/><br/>
    	<span style="font-size: 15px;">手&nbsp;机&nbsp;号&nbsp;：</span><input id="userTel" onkeyup="autoSub(this);" maxlength="11" style="border: 1px solid #CCC;height: 24px"/>
    	<button onclick="searchUser()" style="height:26px;width: 50px;background-color: #c0c0c0;">查询</button>
    	<br/><br/>
    	<button onclick="choseMnt()" style="height:26px;width: 50px;background-color: #c0c0c0;">点击</button><span style="font-size: 15px;color: red">选择交接员工(默认全部,请谨慎操作)</span>
    	<input id="memberId" type="hidden"/>
</div> -->
<!-- <div id="mntSearch" style="display:none;padding: 15px 0px 0px 20px">
	<table id="mntTable">
	
	</table>
</div> -->
</body>
</html>

