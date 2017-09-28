<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_BILL_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
<meta name="description" content=""/>
<meta name="keywords" content=""/>
<meta name="viewport" content="width=device-width, initial-scale=1"/>
<title>服务跟踪</title>
<!-- Set render engine for 360 browser -->
<meta name="renderer" content="webkit"/>
<!-- No Baidu Siteapp-->
<meta http-equiv="Cache-Control" content="no-siteapp"/>
<link rel="icon" type="image/png" href="<%=path%>/style/css/fbCss/i/favicon.png"/>
<!-- Add to homescreen for Chrome on Android -->
<meta name="mobile-web-app-capable" content="yes"/>
<link rel="icon" sizes="192x192" href="<%=path%>/style/css/fbCss/i/app-icon72x72@2x.png"/>
<!-- Add to homescreen for Safari on iOS -->
<meta name="apple-mobile-web-app-capable" content="yes"/>
<meta name="apple-mobile-web-app-status-bar-style" content="black"/>
<meta name="apple-mobile-web-app-title" content="Amaze UI"/>
<link rel="apple-touch-icon-precomposed" href="<%=path%>/style/css/fbCss/i/app-icon72x72@2x.png"/>
<!-- Tile icon for Win8 (144x144 + tile color) -->
<meta name="msapplication-TileImage" content="<%=path%>/style/css/fbCss/i/app-icon72x72@2x.png"/>
<meta name="msapplication-TileColor" content="#0e90d2"/>
<link rel="stylesheet" href="<%=path%>/style/css/fbCss/css/amazeui.min.css"/>
<link rel="stylesheet" href="<%=path%>/style/css/fbCss/css/app.css"/>
<!--[if (gte IE 9)|!(IE)]><!-->
<script src="<%=path%>/style/css/fbCss/js/jquery.min.js"></script>
<!--<![endif]-->
<!--[if lte IE 8 ]>
<script src="http://libs.baidu.com/jquery/1.11.3/jquery.min.js"></script>
<script src="http://cdn.staticfile.org/modernizr/2.8.3/modernizr.js"></script>
<script src="assets/js/amazeui.ie8polyfill.min.js"></script>
<![endif]-->
<script src="<%=path%>/style/css/fbCss/js/amazeui.min.js"></script>

<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>


<script type="text/javascript">
var orgId = "${orgId}";
var orgProId = "${orgProId}";
var mngId = "${mngId}";
var tel = "${tel}";

$(function(){
	initData();
});

function initData(){
	$.post('<%=path%>/supervisory/asyn/getOrgProNodeList?orgProId='+orgProId,function(result){
		var htmlStr = "";
		for(var i=result.length-1;i>=0;i--){
			var ophId = result[i].ophId;
			var fbId = result[i].fbId;
			var nodeName = result[i].nodeName;
			var score = result[i].score;
			var detail = result[i].detail;
			if(i==0){
				if(result[i].flag==0){
					htmlStr = htmlStr + getDuring(nodeName,"firstNode");
				}else{
					htmlStr = htmlStr + getFinish(ophId,fbId,nodeName,score,detail,"firstNode");
				}
			}else{
				if(result[i].flag==0){
					htmlStr = htmlStr + getDuring(nodeName,null);
				}else{
					if(i==result.length-1){
						htmlStr = htmlStr + getFinish(ophId,fbId,nodeName,score,detail,"lastNode");
					}else{
						htmlStr = htmlStr + getFinish(ophId,fbId,nodeName,score,detail,null);
					}
				}
			}
		}
		$("#middle").html(htmlStr);
	});
}

function getFinish(ophId,fbId,nodeName,score,detail,cmd){
	var line = "<div class='am-margin-top-sm'><img src='<%=path%>/style/css/fbCss/images/xian.png' width='3' height='129'/></div>";
	var divHeadClass = "<div class='am-g am-margin-top'>";
	var fbDiv = 	"<div class='am-g am-padding-left-sm am-padding-right-sm'>"+
							"<div class='am-u-sm-7 am-text-left am-text-xs am-padding-top-sm' style='cursor:pointer'>"+
							"打分评价："+
							"<img id='"+ophId+"_0' onclick=\"changeStar('"+ophId+"','0');\" mess='n' src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img id='"+ophId+"_1' onclick=\"changeStar('"+ophId+"','1');\" mess='n' src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img id='"+ophId+"_2' onclick=\"changeStar('"+ophId+"','2');\" mess='n' src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img id='"+ophId+"_3' onclick=\"changeStar('"+ophId+"','3');\" mess='n' src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img id='"+ophId+"_4' onclick=\"changeStar('"+ophId+"','4');\" mess='n' src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"</div>"+
							"<div class='am-u-sm-5 am-text-right am-padding-left-sm am-text-xs am-padding-top-sm'>"+
							"<button onclick=\"saveFeedBack('"+ophId+"');\" class='am-btn am-btn-primary am-btn-xs'>评价此环节服务</button>"+
							"</div>"+
							"<div><textarea id='detail_"+ophId+"'></textarea></div>"+
							"</div>";
	if(cmd == "firstNode"){
		line = "";
	}
	if(cmd == "lastNode"){
		divHeadClass = "<div class='am-g wb-margin'>";
	}
	if(fbId != "no"){
		var starImg = "";
		for(var i=0;i<5;i++){
			if(i<score){
				starImg = starImg + "<img src='<%=path%>/style/css/fbCss/images/hx.png' style='cursor:pointer;width: 14px;'/>";
			}else{
				starImg = starImg + "<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>";
			}
		}
		fbDiv = 	"<div class='am-g am-padding-left-sm am-padding-right-sm'>"+
						"<div class=' am-padding-top-xs am-padding-bottom-xs am-padding-left-sm am-margin-top-sm wb-bg_1'>"+
						"分数："+starImg+"</div>"+
						"</div>"+
						"<div class='am-g am-padding-left-sm am-padding-right-sm'>"+
						"<div class=' am-padding-top-xs am-padding-bottom-xs am-padding-left-sm am-margin-top-sm wb-bg_1'>"+
						"评价内容："+detail+"</div>"+
						"</div>";
	}
	var finish = 	divHeadClass+
							"<div class='am-u-sm-3 am-text-center'>"+
							"<div class='am-margin-top-sm'><img src='<%=path%>/style/css/fbCss/images/touxiang_1.png' width='50'/></div>"+line+
							"</div>"+
							"<div class='am-u-sm-9'>"+
							"<div class='am-text-xs am-padding-top-sm am-padding-left-sm am-padding-bottom-sm wb-bg'>"+
							"<div class='am-text-default'><strong>"+nodeName+"</strong></div>"+
							"业务办理完成"+
							"</div>"+fbDiv+
							"</div></div>";
	return finish;
}

function getDuring(nodeName,cmd){
	var line = "<div class='am-margin-top-sm'><img src='<%=path%>/style/css/fbCss/images/xian.png' width='3' height='129'/></div>";
	if(cmd == "firstNode"){
		line = "";
	}
	var during = 	"<div class='am-g wb-margin'>"+
							"<div class='am-u-sm-3 am-text-center'>"+
							"<div class='am-margin-top-sm'><img src='<%=path%>/style/css/fbCss/images/touxiang.png' width='50'/></div>"+line+
							"</div>"+
							"<div class='am-u-sm-9'>"+
							"<div class='am-text-xs am-padding-top-sm am-padding-left-sm am-padding-bottom-sm wb-bg'>"+
							"<div class='am-text-default'><strong>"+nodeName+"</strong></div>"+
							"业务正在办理"+
							"</div>"+
							"<div class='am-u-sm-7 am-text-left am-padding-left-sm am-text-xs am-padding-top-sm' style='cursor:pointer'>"+
							"打分评价："+
							"<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"<img src='<%=path%>/style/css/fbCss/images/hx_1.png' style='cursor:pointer;width: 14px;'/>"+
							"</div>"+
							"<div class='am-u-sm-5 am-text-right am-padding-left-sm am-text-xs am-padding-top-sm'>"+
							"<button class='am-btn am-btn-default am-btn-xs wb-font-color'>评价此环节服务</button>"+
							"</div>"+
							"</div>"+
							"</div>";
	return during;
}

function saveFeedBack(ophId){
	var score = 0;
	var detail = $("#detail_"+ophId).val();
	for(var i=0;i<5;i++){
		var mess = $("#"+ophId+"_"+i).attr("mess");
		if(mess=="y"){
			score = i+1;
		}
	}
	if(score==0){
		$.showMsg("请打分!",2);
		return;
	}
	if(detail==""){
		$.showMsg("请填写评论内容!",2);
		return;
	}
	tips = layer.msg("正在保存评价,请稍后...",{icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveFeedBack',
	{
		'mngId': mngId,
		'ophId': ophId,
		'orgId': orgId,
		'score': score,
		'detail': detail
	},
	function(result){
		if(result.code){
			layer.close(tips);
			$.showMsg("保存评论成功!",1);
			initData();
		}else{
			layer.close(tips);
			$.showMsg("保存评论失败,系统出错!",2);
			initData();
		}
	});
}

function changeStar(starKey,starValue){
	var pathA = "<%=path%>/style/css/fbCss/images/hx.png";
	var pathB = "<%=path%>/style/css/fbCss/images/hx_1.png";
	for(var i=0;i<5;i++){ 
		$("#"+starKey+"_"+i).attr("src",pathB); 
	}
	for(var i=0;i<5;i++){
		if(i<=starValue){
			$("#"+starKey+"_"+i).attr("src",pathA);
			$("#"+starKey+"_"+i).attr("mess","y"); 
		}else{
			$("#"+starKey+"_"+i).attr("src",pathB);
			$("#"+starKey+"_"+i).attr("mess","n"); 
		}
	}
}

function taxQuery(){
	var ACC_BILL_URL = '<%=url%>';
	location.href= ACC_BILL_URL + "/weibaoApp/shuju.jspx?tel="+tel+"&orgid="+orgId+"&orgName=";
}

function balance(){
	var ACC_BILL_URL = '<%=url%>';
	location.href= ACC_BILL_URL + "/weibaoApp/zijin.jspx?tel="+tel+"&orgid="+orgId+"&orgName=";
}
</script>
</head>
<body>
<!--在这里编写你的代码-->
<!--头部代码开始-->
    <header class="wb-nav">
        <div class="wb-header am-text-center am-btn-primary am-btn-block">
            服务跟踪
        </div>
        <div class="am-g wb-header-one">
            <div class="am-u-sm-4 am-text-center">
                通知提醒：
            </div>
             <div class="am-u-sm-8  am-text-sm">
                【通知提醒区域】
            </div>
        </div>
    </header>
<!--头部代码结束-->
<!--中间代码开始-->
	<div id="middle"></div>
    <br/><br/><br/><br/>
<!--中间代码结束-->
<!--低部代码开始-->
<div class="am-g am-text-center wb-border-top wb-top am-padding-top-sm am-text-sm wb-footer">
    <div class="am-u-sm-4" onclick="taxQuery();">
        <img src="<%=path%>/style/css/fbCss/images/footer.png" width="30"/>
        <div class="am-padding-top-sm">报税查询</div>
    </div>
    <div class="am-u-sm-4">
        <img src="<%=path%>/style/css/fbCss/images/footer2_1.png" width="30"/>
        <div class="am-padding-top-sm">服务环节</div>
    </div>
    <div class="am-u-sm-4" onclick="balance();">
        <img src="<%=path%>/style/css/fbCss/images/footer_2.png" width="30"/>
        <div class="am-padding-top-sm">资金利润</div>
    </div>
</div>
<!--低部代码结束-->
</body>
</html>
