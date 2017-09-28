<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<title>Blueprint: Vertical Timeline</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0"> 
<meta name="description" content="Blueprint: Vertical Timeline" />
<meta name="keywords" content="timeline, vertical, layout, style, component, web development, template, responsive" />
<meta name="author" content="Codrops" />
<link rel="shortcut icon" href="../favicon.ico">
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/logCss/css/default.css" />
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/logCss/css/component.css" />
<script src="<%=path%>/style/css/logCss/js/modernizr.custom.js"></script>
</head>
<body>
	<div class="container">
			<header class="clearfix">
				<span>UpdateLog</span>
				<h1>更新日志</h1>
				
			</header>	
			<div class="main">
				<ul class="cbp_tmtimeline">
				
					<li>
						<time class="cbp_tmtime" datetime="2016-06-22 03:36"><span>6/22/16</span> <span>03:36</span></time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>Version-1.3.1</h2>
							<p>
								1.新增功能导出客户相关信息。<br/>
								2.新增功能派工查询功能。<br/>
								3.修复了这个首页的客户缴费/报税/工作量(图表)，换了其它月份就不能正确显示了。<br/>
								4.修复了引入注册员工，总账那边的监控管理，过了日期，还可以进行操作。<br/>
								5.修复了先给会计分配总账的权限，然后再分配管理平台的权限，然后再回来点总账的权限就没有了。<br/>
								6.修复了未完成的跟进信息应该一直在待办事项下显示。<br/>
								7.修复了业务名称不存在，添加后显示业务名称已存在。<br/>
								8.修复了一个管理员不能监控另一个管理员。<br/>
							</p>
						</div>
					</li>
				
					<li>
						<time class="cbp_tmtime" datetime="2016-06-18 01:22"><span>6/18/2016</span> <span>01:22</span></time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>Version-1.2.1</h2>
							<p>
								1.新增功能合加同类型应该加一个“全部”。<br/>
								2.修复了报税金额可以是任意字符。<br/>
								3.修复了编辑报税明细窗口的“清空”按钮，只清空金额，没有清空记录。<br/>
								4.修复了缴费时，点击“全选”按钮，会有提示信息“选择一条客户信息”。<br/>
								5.修复了创建合同时，会计提成和折扣率可以输入任意字符。<br/>
								6.修复了合同里的代账费和账本费的值是零，然后缴费的时候缴不上。<br/>
								7.修复了同一级别的审批重复添加没有提示信息。<br/>
								8.修复了缴费之后的客户，合同就不能修改了。<br/>
								9.修复了签约日期应该在合同之前。<br/>
								10.修复了第一次缴费后，关闭缴费窗口之后，再重新缴费，账本费没有清空。<br/>
								11.修复了未欠费的客户，如果点击催费，不应该有提示信息“发送成功”然后再显示催费结果明细。<br/>
							</p>
						</div>
					</li>
				
					<li>
						<time class="cbp_tmtime" datetime="2016-06-10 00:28"><span>6/10/2016</span> <span>00:28</span></time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>Version-1.1.2</h2>
							<p>
								1.修复了员工电话的文本框与其他列表框没有对齐。<br/>
								2.修复了存在没有创建客户公司的名称。<br/>
								3.修复了按审批级别查询不到内容。<br/>
								4.修复了合同里的代账费和账本费应以0.00的格式显示。<br/>
								5.修复了创建客户信息时，只有一个关闭按扭。<br/>
								6.修复了绩效统计里的“工作概要“与角色权限里的”工作概览“功能名称不一致。<br/>
								7.修复了业务名称的对话框与业务名称的下拉列表没有对齐。<br/>
								8.修复了既存的业务存在添加业务对话框里。<br/>
								9.修复了在客户缴费信息栏上加几个标识说明，以便用户更直观地看缴费信息。<br/>
								10.修复了有两个消息标题。<br/>
								11.修复了按合同类型查询不对。<br/>
								12.修复了所有窗口都是点空白关闭。<br/>
								13.修复了创业平台注册的密码是6位，代账和总账的密码位数没有限制，这三个的平台的密码是不是应该保持一致。<br/>
								14.修复了代账这边刚开始创建客户的时候，暂时不想要账套，然后来又想要这个账套了，然后在总账这边新建一个账套，然后总账这边显示该公司已经存在了。<br/>
								15.修复了代账这边引入员工姓名汉字16位，英文8位总账那边没有限制。<br/>
								16.修复了公司部门编号和公司部门名称可以重复。<br/>
								17.修复了新增员工信息时，输入已存在的账号时，应先提示“该账号已注册”然后发送验证码。<br/>
							</p>
						</div>
					</li>
				
					<li>
						<time class="cbp_tmtime" datetime="2016-06-3 00:16"><span>6/3/2016</span> <span>00:16</span></time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>Version-1.1.1</h2>
							<p>
								1.修复了编辑员工信息时，帐号停用之后，此帐号仍然可以登陆。<br/>
								2.修复了标签文字不正确。<br/>
								3.修复了创建客户时，联系方式里的E-mail的内容长度限制过短。<br/>
								4.修复了创建客户时，联系方式里的E-mail没有输入内容时，也会有校验。<br/>
								5.修复了创建客户时选择了“保存时同步创建会计账套”，单机“记账”后，进到总账后没有账套。<br/>
								6.修复了缴费管理没有客户公司的记录。<br/>
								7.修复了创建合同时，签约日期在服务期之后，没有提示信息。<br/>
								8.修复了组织架构里的部门的子集可以无限制建立。<br/>
								9.修复了引入注册员工时，员工姓名长度短，电话可以输入任意字符。<br/>
								10.修复了公司的员工登录后，应该只能看到他所负责的客户信息，不能看到其他员工所负责的客户信息。<br/>
								11.修复了客户手机号码的位数没有校验。<br/>
								12.修复了客户缴费信息有无合同的记录能不能做出标记，减少用户繁琐工作。<br/>
								13.修复了客户缴费信息没有合同的记录，单机“催费”时，不应该显示提示信息“已发送成功”。<br/>
								14.修复了客户缴费信息没有合同的记录和缴费的记录标记颜色一样。<br/>
								15.修复了员工电话的文本框与其他列表框没有对齐。<br/>
							</p>
						</div>
					</li>
				
					<li>
						<time class="cbp_tmtime" datetime="2016-05-31 00:18"><span>5/31/2016</span> <span>00:56</span></time>
						<div class="cbp_tmicon cbp_tmicon-phone"></div>
						<div class="cbp_tmlabel">
							<h2>Version-1.1.0</h2>
							<p>
								1.修复了新增员工信息时，角色的内容不能输入。<br/>
								2.修复了新增角色后，没有记录。<br/>
								3.修复了同一家公司建两个服务期限相同的合同时，没有提示信息。<br/>
								4.修复了记账按钮无效。<br/>
								5.修复了报税金额不能修改。<br/>
								6.修复了派工发送消息通知无效。<br/>
								7.修复了授权客户查看时，输入手机号码，授权成功后，授权信息没有显示。<br/>
								8.修复了按公司统计查询报税金无效。<br/>
								9.修复了返回会计统计的按钮上的文字不正确。<br/>
								10.修复了编辑客户信息电话号码和手机号码文字重复。<br/>
								11.修复了编辑客户信息办公电话格式不正确。<br/>
								12.修复了编辑客户信息图片不能上传。<br/>
								13.修复了新密码的对话框中提示“16位以下的英文或数字”但特殊字符也可以输入。<br/>
								14.修复了禁止员工修改以及找回密码时，员工可以修改密码，也可以找回密码，但登陆之后，显示密码错误。<br/>
								15.修复了账号绑定机器员工账号登录权限没有实现。<br/>
								16.修复了公司报税信息详细信息显示不正确。<br/>
								17.修复了公告列表下的公告条数统计不正确。<br/>
								18.修复了创建合同时，合同列表下的合同条数统计不正确。<br/>
								19.修复了新增跟进记录时，跟进列表的记录条数统计不正确。<br/>
							</p>
						</div>
					</li>
					
				</ul>
			</div>
		</div>
</body>