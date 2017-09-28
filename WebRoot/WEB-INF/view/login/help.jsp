<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>[微宝]代帐公司管理系统使用指南</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/bootstrap/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style-bootstrap.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/bootstrap/css/bootstrap.min.css">
<style type="text/css">
ul.nav-tabs li a {
    padding: 4px 16px 4px;
}
</style>
</head>
<body data-spy="scroll" data-target="#myScrollspy">
<div class="container">
	<div class="jumbotron">
		<h1>[微宝]代帐公司管理系统使用指南</h1>
	</div>
    <div class="row">
        <div class="col-xs-3" id="myScrollspy">
            <ul style="width:240px;text-align:left;top:10px;" class="nav nav-tabs nav-stacked" data-spy="affix" data-offset-top="10">
                <li><a class="active" href="#section-1">1.本系统简介</a></li>
                <li><a href="#section-2">2.本系统功能概览</a></li>
                <li><a href="#section-3">3.注册登录指导</a></li>
                <li><a href="#section-4">4.加入员工指导</a></li>
                <li><a href="#section-5">5.会计在线功能</a></li>
                <li><a href="#section-6">6.会计详细功能</a></li>
                <li><a href="#section-7">7.结账详细功能</a></li>
				<li><a href="#section-8">8.报税详细功能</a></li>
				<li><a href="#section-9">9.企业档案操作</a></li>
				<li><a href="#section-10">10.公司统计及本期工作统计</a></li>
				<li><a href="#section-11">11.个人信息及密码修改找回</a></li>
				<li><a href="#section-12">12.会计端加入监控</a></li>
				<li><a href="#section-13">13.会计端报税及通知</a></li>
				<li><a href="#section-14">14.会计端企档费用</a></li>
				<li><a href="#section-15">15.常见问题汇总</a></li>
            </ul>
        </div>
        <div class="col-xs-9" style="margin:0px 0px 20px 0px;">
           <div class="headline" id="section-1" style="margin-top:0px;"><h4 style="width:200px;">本系统简介</h4></div>
           		<p>系统简介：微宝代帐公司管理系统是由北京微宝网络技术有限公司开发的一款面向代理记账公司（以下简称代帐公司）为主的监控系统。</p>
                <p>系统功能：本系统主要对代帐公司的记账会计及其负责的被代理记账的公司（以下简称记账公司）进行工作及记账报税进度的监控。</p>
                <p>系统目的：本系统旨在提高代帐公司对其记账公司的记账报税进度的实时把控,以便更有效的分配人力物力资源。</p>
                <p>客户人群：本系统面向的客户群体为代账公司负责人（以下简称管理员）以及集团公司的财务负责人。</p>
                <p>附加功能：本系统还提供对记账公司的报税短信通知及对记账公司进行预催费。</p>
           <div class="headline" id="section-2"><h4 style="width:200px;">本系统功能概览</h4></div>
                <p>本系统主要功能包括：</p>
                <p>1.对记账会计的在线状态的监控</p>
                <p>2.对记账会计的工作进度的监控</p>
                <p>3.对记账公司记账情况的监控</p>
                <p>4.对记账公司报税情况的监控</p>
                <p>5.记账公司企业档案录入及检索</p>
                <p>6.代帐公司按期间的公司工作统计概览</p>
                <p>7.代帐公司按区间的总税金及工作量走势图</p>
                <p>8.管理员信息修改及密码修改和找回功能</p>
                <p>9.会计端配置加入代帐公司</p>
                <p>10.会计端报税及短信通知记账公司</p>
                <p>11.会计端档案录入及查询管理</p>
                <p>&emsp;</p>
                <p>系统2015年10月20日版本更新功能</p>
                <p>1.会计端：被监控员工可撤销监控功能取消了</p>
                <p>2.会计端：报税录入页面添加税费明细功能,自定义税费详细内容。修正删除税金恢复未报税状态。</p>
                <p>3.会计端：报税短信现在可发送自定义税费详细并提供app网址。</p>
                <p>4.会计端：公司详细添加收费标准、收费方式、费用余额字段。添加费用详细功能。</p>
                <p>5.会计端：公司详细列表添加费用余额、费用、记账区间、是否隔月列等并支持统计。</p>
                <p>6.会计端：公司详细列表支持查询需催费或欠费公司查询并提供本月未自动发送催费短信情况下自选发送催费短信。</p>
                <p>7.监控端：监控端个人信息处提供咨询电话及银行卡情况录入,并在录入情况下出现在催费短信中。</p>
                <p>8.监控端：监控首页走势图添加记账费用区间统计,当前工作概况提供需催费公司及欠费公司提示。</p>
                <p>9.监控端：监控首页右侧已员工或公司为条件按区间统计凭证量、税金及记账费用,一共六个页面及功能。</p>
                <p>10.监控端：一个记账系统账号现在只能被一个监控端监控,消息列表点击可锁定监控记录。</p>
                <p>11.监控端：会计登陆限制功能,及限制会计密码修改及找回。</p>
                <p>12.监控端：员工管理页面可修改员工密码。</p>
           <div class="headline" id="section-3" style="position:relative;"><h4 style="width:200px;">注册登录指导</h4></div>
		   		<p>本监控系统登录页地址：<a href="http://jk.weibaobeijing.com">http://jk.weibaobeijing.com</a></p>
		   		<p>注册入口：</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/mngRegist.jpg">
		   		<p>注册界面：</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/registMenu.jpg">
		   		<p>注册完成后输入刚刚注册成功的手机号密码进入主界面：</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/mngIndex.jpg">
		   <div class="headline" id="section-4"><h4 style="width:200px;">加入员工指导</h4></div>
		   		<p>在主页面内指向左侧菜单“设置”后选择“会计管理”</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/addClerk.jpg">
		   		<p>点击添加员工,输入记账会计在微宝记账系统中的名称和电话后即可向员工发送一条申请</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/addClerkAlert.jpg">
		   		<p>如果存在此员工,则表格中出现此请求监控的记录,记录显示为申请中,员工如何验证此申请请看<a href="#section-12">12.会计端加入监控</a></p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/quering.jpg">
		   		<p>如果记账会计接收并处理此请求,则主界面会有消息提示</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/managerMsg.jpg">
		   		<p>点击信封消息可进入消息列表,再点击此消息可进入“会计管理”列表</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/manageMsgList.jpg">
		   		<p>如果此监控请求得到了通过,则此时你就能成功的监控该员工及其负责的记账公司</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/successQuery.jpg">
			<div class="headline" id="section-5"><h4 style="width:200px;">会计在线功能指导</h4></div>
				<p>点击左侧菜单“在线状态” 可查看员工在线详情及在线统计</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/onlineState.jpg">
		   		<p>双击表格中的记录可进入<a href="#section-6">6.会计详细功能</a>,并按此会计条件统计显示</p>
			<div class="headline" id="section-6"><h4 style="width:200px;">会计详细功能指导</h4></div>
				<p>点击左侧菜单“会计详细” 可查看员工工作情况,报税结账公司数及比率,并进行本期统计显示在表格下方</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/clerkDetail.jpg">
		   		<p>点击结账数量列“查看”可进入<a href="#section-7">7.结账详细功能</a>并按此会计条件查询统计</p>
		   		<p>点击报税数量列“查看”可进入<a href="#section-8">8.报税详细功能</a>并按此会计条件查询统计</p>
			<div class="headline" id="section-7"><h4 style="width:200px;">结账详细功能指导</h4></div>
				<p>点击左侧菜单“结账详细” 可查看各个公司的结账情况并统计凭证数</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/orgDetail.jpg">
			<div class="headline" id="section-8"><h4 style="width:200px;">报税详细功能指导</h4></div>
				<p>点击左侧菜单“报税详细” 可现实各个公司的报税情况及报税短信情况并统计本期总税金</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/taxDetail.jpg">
			<div class="headline" id="section-9"><h4 style="width:200px;">企业档案操作</h4></div>
				<p>点击左侧菜单“企业档案” 可查看记账公司各项信息并统计公司数量</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/orgbakup.jpg">
			   	<p>双击表格中所在公司记录,可进入企业档案编辑,及催费月份设置,如果设置催费时间及频率,则到催费时间,将向记账公司发送催费短信。</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/orgDetailMng.jpg">
			<div class="headline" id="section-10"><h4 style="width:200px;">公司统计及本期工作统计</h4></div>
				<p>首页中上半部分可显示税金或凭证量走势及汇总</p>
				<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/trendData.jpg">
				<p>首页中下半部分可调节记账期间并先记账期间内工作情况统计</p>
				<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/statistic.jpg">
			<div class="headline" id="section-11"><h4 style="width:200px;">个人信息及密码修改找回</h4></div>
				<p>点击主界面头部横条“个人信息”,可修改个人信息</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/mngInfo.jpg">
				<p>指向左侧菜单“设置”,点击“密码修改”</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/pwdMdf.jpg">
		   		<p>在登录页点击“忘记密码”</p>
		   		<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/pwdGetback.jpg">
		   		<p>密码找回界面</p>
		   		<img width="800px" height="200px" src="<%=path%>/style/image/supervisoryHelp/pwdGetback2.jpg">
			<div class="headline" id="section-12"><h4 style="width:200px;">会计端加入监控</h4></div>
				<p>两种方式可操作监控请求：</p>
				<p>1.当代帐公司管理员发送监控申请后在主页面的信封图标处显示信息条数和红点</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/clerkQuery.jpg">
			   	<p>点击红点后进入消息列表,未读消息为蓝色,已读为灰色,刚读过的信息为褐红色</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/msgList.jpg">
			   	<p>点击申请监控的消息进入”代帐公司“标签后点击接受即可完成代帐公司的管理员监控请求</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/clerkOrgMng.jpg">
			   	<p>监控请求接受后的状态标识,同时左侧菜单出现代帐设置图标,并有3个二级菜单</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/queryConfirm.jpg">
			   	<p>2.另一种处理监控请求入口：如果已经接受过一次监控请求,可直接指向左侧菜单"代帐设置",点击“公司被监控管理”,确认请求方法同上</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/queryMngMt.jpg">
			<div class="headline" id="section-13"><h4 style="width:200px;">会计端报税及通知</h4></div>
				<p>在按<a href="#section-12">12.会计端加入监控</a>操作后,左侧菜单出现“代帐设置”,指向“代帐设置”选择“税金录入及通知”</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/taxinput.jpg">
			   	<p>税金录入后必须点击保存数据,否则录入的数据会丢失,每条记录必须有联系电话,否则不可勾选,不可发送短信。</p>
			   	<p>每条记录的联系电话应该预先在“代帐设置”->"公司详细及费用"中设置,具体见<a href="#section-14">14.会计端企业档案费用</a></p>
			<div class="headline" id="section-14"><h4 style="width:200px;">会计端报企业档案</h4></div>
				<p>在按<a href="#section-12">12.会计端加入监控</a>操作后,左侧菜单出现“代帐设置”,指向“代帐设置”选择“公司详细及费用”</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/bookbak.jpg">
			   	<p>双击表格中所在公司记录,可进入企业档案编辑,及催费月份设置,如果设置催费时间及频率,则到催费时间,将向记账公司发送催费短信。</p>
			   	<img width="800px" height="400px" src="<%=path%>/style/image/supervisoryHelp/feeAsk.jpg">
			<div class="headline" id="section-15"><h4 style="width:200px;">常见问题汇总</h4></div>
				<p>为什么员工已下线,监控端还显示在线？答：为了更好的为客户提供流畅快速的服务器环境,员工在线状态间隔几分钟一刷新,所以会出现此情况。</p>
				<p>如何对员工工作进度进行排序？答：点击表格中要排序的列即可</p>
       </div>
    </div>
</div>
</body>
</html> 