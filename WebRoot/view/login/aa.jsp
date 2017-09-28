<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<div >
	<div>
        <ul id="nav"> 
         <li id="onLineState" mes="menu"><a href="javascript:void(0)" class="menu_first menu_list" id="menu_one" url="/supervisory/forward/gotoOnlineState">在线状态</a></li> 
         <li id="cusManage" mes="menu" mes="menu"><a href="#" class="menu_first" id="menu_four">客户管理 </a>
         	<ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu"> 
                 <li id="/supervisory/forward/gotoCustomSetting" mes="menu" style="display: none;"> <a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoCustomSetting">客户信息</a></li>
                 <li id="/supervisory/forward/gotoCustomPayLook" mes="menu" style="display: none;"> <a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoCustomPayLook">缴费管理</a></li>
                  <!--  <li id="/supervisory/forward/gotoFeedBackManage" mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoFeedBackManage">客户评价</a></li> -->
             </ul>
         </li>
          <li id="empWorkDetail" mes="menu"><a href="#" class="menu_first" id="menu_five">员工绩效</a>
         	<ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu"> 
		         <li id="/supervisory/forward/gotoClerkDetail"  mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoClerkDetail">工作概要</a></li>
		         <li id="/supervisory/forward/gotoSettleDetail" mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoSettleDetail">结账信息</a></li>
		         <li id="/supervisory/forward/gotoTaxDetail"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoTaxDetail?telphone=&period=">报税信息</a></li> 
			</ul>
         </li>
         <li id="busFrameWork" mes="menu"><a href="#" class="menu_first" id="menu_seven">公司管理</a>
             <ul style="width:212px; border:1px #bfbfbf solid; background-color:#FFF;" class="float_menu"> 
                 <li id="/supervisory/forward/gotoPayAudit"        mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoPayAudit">收费审批</a></li>
                 <li id="/supervisory/forward/gotoClerkFeeList"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoClerkFeeList">收费统计</a></li>
                 <li id="/supervisory/forward/gotoFrameworkManage" mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoFrameworkManage">组织架构</a></li>
                 <li id="/supervisory/forward/gotoClerkTaxList"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoClerkTaxList">税金统计</a></li>
                 <li id="/supervisory/forward/gotoPowerManage"     mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoPowerManage">角色权限</a></li>
                 <li id="/supervisory/forward/gotoClerkVchList"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoClerkVchList">凭证统计</a></li>
                 <!-- <li id="/supervisory/forward/gotoFeedBackQuery"   mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoFeedBackQuery">评价查询</a></li> -->
             	 <li id="/supervisory/forward/gotoWithdrawalManage"     mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoWithdrawalManage">发起提现</a></li>
             	 <li id="/supervisory/forward/gotoWithdrawalRecList"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoWithdrawalRecList">提现记录</a></li>
             	 <li id="/supervisory/forward/gotoSettlementManage"     mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoSettlementManage">代账订单</a></li>
             </ul>
         </li> 
        <!-- add by hechunyang 20160913 begin -->
         <li id="businessManager" mes="menu"><a href="#" class="menu_first" id="menu_seven">业务管理</a>
             <ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu">  
                 <li id="/supervisory/forward/gotoBusinessModel"   mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/business/forward/gotoBusinessView?pageName=model">业务模板</a></li>
                <li id="/business/forward/gotoProgressList"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/business/forward/gotoBusinessView?pageName=progress">进度查询</a></li>
             </ul>
         </li> 
         <!-- add by hechunyang 20160913 end -->
        <!-- add by hechunyang 20161017 增加发送短信按钮 begin-->
         <li id="smsManager" mes="menu"><a href="#" class="menu_first" id="menu_seven">短信管理</a>
             <ul style="width:105px; border:1px #bfbfbf solid;" class="float_menu">  
                 <li id="/sendmessage/forward/gotoSendMessageMain"   mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/sendmessage/forward/gotoSendMessageMain">发送短信</a></li>
                <li id="/sendmessage/forward/gotoMessage"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/sendmessage/forward/gotoMessage">短信记录</a></li>
<%--                 <li id="/sendmessage/forward/gotoMessageFenye"    mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/sendmessage/forward/gotoMessageFenye">短信记录2</a></li>--%>             </ul>
         </li> 
         <!-- add by hechunyang 增加发送短信按钮 end -->
         <li id="sysSetting" mes="menu"><a href="#" class="menu_first" id="menu_six">系统设置</a>
             <ul style="width:212px; border:1px #bfbfbf solid; background-color:#FFF;" class="float_menu"> 
                 <li id="function_raw"                             mes="menu" style="display: none;"><div align="center"><a href="" onclick="rawOrgToOrther();return false" class="menu_second menu" >权限交接</a></div></li>
                 <li id="/supervisory/forward/gotoAuditSettings"   mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoAuditSettings">审批设置</a></li>
                 <li id="/supervisory/forward/gotoEmpLoginLimited" mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoEmpLoginLimited">登陆权限</a></li>
                 <li id="/supervisory/forward/gotoPasswordModify"  mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoPasswordModify">密码修改</a></li>
            <%-- <li id="/supervisory/forward/gotoProcessSetting"  mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoProcessSetting">业务管理</a></li>--%>             	 
                 <li id="/supervisory/forward/gotoUpdateLog"  mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoUpdateLog">更新日志</a></li>
             	 <li id="/supervisory/forward/gotoAlipaySetting"  mes="menu" style="display: none;"><a href="#" class="menu_second menu_list" url="/supervisory/forward/gotoAlipaySetting">支付宝设置</a></li>
             </ul>
         </li> 
        </ul>
  	</div>
	<div id="main_right" class="main_right"></div>
</div>
</body>
</html>