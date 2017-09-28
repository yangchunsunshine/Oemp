<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>支付宝支付设置</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var tips;
var paramStr = new Array("app_id","method","charset","sign_type","sign","timestamp","version","out_trade_no","scene","auth_code","subject");

$(function(){
	$.post("<%=path%>/supervisory/asyn/getAlipayInfo",function(result){
		var aliPayInfo = result;
		for(var i=0;i<aliPayInfo.length;i++){
			$("#"+aliPayInfo[i].payKey).val(aliPayInfo[i].payValue);
		}
	});
});

function saveAlipaySetting(){
	for(var i=0;i<paramStr.length;i++){
		var tempValue = $("#"+paramStr[i]).val();
		if(tempValue==null||tempValue==""){
			parent.$.showMsg("请选择输入"+paramStr[i]+"!", 2);
			return;
		}
	}
	var payKey = $("input[name='payKey']");
	var payValue = $("input[name='payValue']");
	var requestType = $("input[name='requestType']");
	var isMustBe = $("input[name='isMustBe']");
	var recommend = $("input[name='recommend']");
	var aliParams =[];
	for(var i=0;i<payKey.length;i++){
		var aliPayInfo = new Object();
		aliPayInfo.payKey = $(payKey[i]).val();
		aliPayInfo.payValue = $(payValue[i]).val();
		aliPayInfo.requestType = $(requestType[i]).val();
		aliPayInfo.isMustBe = $(isMustBe[i]).val();
		aliPayInfo.recommend = $(recommend[i]).val();
		aliParams.push(aliPayInfo);
	}
	var aliParams = JSON.stringify(aliParams);
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
	    url: "<%=path%>/supervisory/asyn/saveAlipaySetting",
	    type: "POST",
	    contentType : "application/json",
	    dataType:"json",
	    data: aliParams,
	    success: function(result){
	       if(result.code){
				layer.close(tips);
				parent.$.showMsg("保存成功!", 1);
			}else{
				layer.close(tips);
				parent.$.showMsg("保存失败!", 2);
			}
	    },
	    error: function(res){
	        parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
	    }
	});
}
</script>
</head>
  
<body>
		<table class="table_query"  cellpadding="0" cellspacing="0" rules="all">
			<tr>
				<th width="20%">参数</th>
				<th width="10%">是否必填</th>
				<th width="70%">描述</th>
			</tr>
			<tr>
				<td colspan="3" align="center" bgcolor="#DCDCDC"><font size="5">公共请求参数<font/></td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="app_id" id="app_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="app_id"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>支付宝分配给开发者的应用ID</span>
					<input type="hidden" name="recommend" value="支付宝分配给开发者的应用ID"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="method" id="method" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="method"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>接口名称</span>
					<input type="hidden" name="recommend" value="接口名称"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="format" id="format" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="format"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>仅支持JSON</span>
					<input type="hidden" name="recommend" value="仅支持JSON"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="charset" id="charset" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="charset"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>请求使用的编码格式，如utf-8,gbk,gb2312等</span>
					<input type="hidden" name="recommend" value="请求使用的编码格式，如utf-8,gbk,gb2312等"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="sign_type" id="sign_type" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="sign_type"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>商户生成签名字符串所使用的签名算法类型，目前支持RSA</span>
					<input type="hidden" name="recommend" value="商户生成签名字符串所使用的签名算法类型，目前支持RSA"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="sign" id="sign" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="sign"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>商户请求参数的签名串</span>
					<input type="hidden" name="recommend" value="商户请求参数的签名串"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="timestamp" id="timestamp" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="timestamp"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>发送请求的时间，格式'yyyy-MM-dd HH:mm:ss'</span>
					<input type="hidden" name="recommend" value="发送请求的时间，格式'yyyy-MM-dd HH:mm:ss'"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="version" id="version" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="version"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>调用的接口版本，固定为：1.0</span>
					<input type="hidden" name="recommend" value="调用的接口版本，固定为：1.0"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="notify_url" id="notify_url" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="notify_url"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>支付宝服务器主动通知商户服务器里指定的页面http路径</span>
					<input type="hidden" name="recommend" value="支付宝服务器主动通知商户服务器里指定的页面http路径"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="app_auth_token" id="app_auth_token" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="app_auth_token"/>
					<input type="hidden" name="requestType" value="0"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>第三方应用授权</span>
					<input type="hidden" name="recommend" value="第三方应用授权"/>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center" bgcolor="#DCDCDC"><font size="5">请求参数</font></td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="out_trade_no" id="out_trade_no" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="out_trade_no"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复</span>
					<input type="hidden" name="recommend" value="商户订单号,64个字符以内、可包含字母、数字、下划线；需保证在商户端不重复"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="scene" id="scene" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="scene"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code</span>
					<input type="hidden" name="recommend" value="支付场景 条码支付，取值：bar_code 声波支付，取值：wave_code"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="auth_code" id="auth_code" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="auth_code"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>支付授权码</span>
					<input type="hidden" name="recommend" value="支付授权码"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="seller_id" id="seller_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="seller_id"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>如果该值为空，则默认为商户签约账号对应的支付宝用户ID</span>
					<input type="hidden" name="recommend" value="如果该值为空，则默认为商户签约账号对应的支付宝用户ID"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="total_amount" id="total_amount" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="total_amount"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]</span>
					<input type="hidden" name="recommend" value="订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="discountable_amount" id="discountable_amount" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="discountable_amount"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>	参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]</span>
					<input type="hidden" name="recommend" value="	参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="undiscountable_amount" id="undiscountable_amount" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="undiscountable_amount"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]</span>
					<input type="hidden" name="recommend" value="不参与优惠计算的金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="subject" id="subject" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="subject"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>是</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>订单标题</span>
					<input type="hidden" name="recommend" value="订单标题"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="body" id="body" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="body"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>订单描述</span>
					<input type="hidden" name="recommend" value="订单描述"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="operator_id" id="operator_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="operator_id"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>商户操作员编号</span>
					<input type="hidden" name="recommend" value="商户操作员编号"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="store_id" id="store_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="store_id"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>商户门店编号</span>
					<input type="hidden" name="recommend" value="商户门店编号"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="terminal_id" id="terminal_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="terminal_id"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>商户机具终端编号</span>
					<input type="hidden" name="recommend" value="商户机具终端编号"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="alipay_store_id" id="alipay_store_id" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="alipay_store_id"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="1"/>
				</td>
				<td align="left">
					<span>支付宝的店铺编号</span>
					<input type="hidden" name="recommend" value="支付宝的店铺编号"/>
				</td>
			</tr>
			<tr>
				<td align="center">
					<input placeholder="timeout_express" id="timeout_express" name="payValue" type="text" class="dfinput_fb" style="margin-left: 0px;margin-right: 0px;border:none;"/>
					<input type="hidden" name="payKey" value="timeout_express"/>
					<input type="hidden" name="requestType" value="1"/>
				</td>
				<td align="center">
					<span>否</span>
					<input type="hidden" name="isMustBe" value="0"/>
				</td>
				<td align="left">
					<span>该笔订单允许的最晚付款时间，逾期将关闭交易</span>
					<input type="hidden" name="recommend" value="该笔订单允许的最晚付款时间，逾期将关闭交易"/>
				</td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="button" value="提交参数" onclick="saveAlipaySetting();" class="acssbtn" style="height:30px;font-size:15px;text-align: center;padding-top:0px; padding-bottom:0px"/> 
				</td>
			</tr>
		</table>
</body>
</html>
