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
<title>代理记账收费</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<script type="text/javascript">
var tips;
var ifBookFee = '${bookFee}';
var accNO = '${accNo}';
var conId = '${conId}';
var bMonths = formatMonth('${bMonths}');//合同起始月份
var eMonths = formatMonth('${eMonths}');//合同截止始月份
//去掉01-09月份的0
function formatMonth(month){
	if(month.length>1){//去掉01-09月份的0
		if(parseInt(month.substring(0,1))==0)
			month = month.substring(1,2);
	}
	return month;
}
$(function(){//页面初始化
	initMonthCost();
	var date = new Date();
	var months = $("#payMonths").val();
	var month = months.split(",");
	for(var i=0;i<month.length;i++){
		$("#month"+month[i]).prop("checked",true);
		$("#month"+month[i]).prop("disabled",true);
		$("#month"+month[i]).next().addClass("checkoned");
	}
	if(ifBookFee == 1){
		$("#bookFee").prop("checked",true);
		$("#bookFee").prop("disabled",true);
		$("#bookFee").next().addClass("checkoned");
	}
    $(".check_div input[type=checkbox]").prop("checked",false);
    $(".check_div input[type=checkbox]").click(function(){
    	if($(this).attr("id") != "bookFee"){
       		if($(this).attr("mCost")==0&&$(this).attr("dis")==0){
       			parent.$.showMsg("合同中无该月费用!", 2);
       			$(this).prop("checked",false);
       			return;
       		}
		}
    	var payAmount = rmoney($("#payAmount").val());
        var realAmount = rmoney($("#realAmount").val());
        var discountMoney = $("#discount").val();
        if($(this).prop("checked")==true){
        	$(this).next().addClass("checkon");
            $(this).next().prop("mes","yes");
        	if($(this).attr("id") == "bookFee"){
        		payAmount = Number(payAmount) + Number(rmoney($(this).attr("bCost")));
        		realAmount  = Number(realAmount) + Number(rmoney($(this).attr("bCost")));
        		$("#payAmount").val(fmoney(payAmount,2));
            	$("#realAmount").val(fmoney(realAmount,2));
            	ifBookFee = 1;
            	return;
        	}
            payAmount = Number(payAmount) + Number(rmoney($(this).attr("mCost")));
            realAmount = Number(realAmount) + Number(rmoney($(this).attr("mCost"))) * Number($(this).attr("dis")) ;
            discountMoney = Number(payAmount) - Number(realAmount);
            $("#payAmount").val(fmoney(payAmount,2));
            $("#realAmount").val(fmoney(realAmount,2));
            $("#discount").val(fmoney(discountMoney,2));
        }else{
        	$(this).next().removeClass("checkon");
            $(this).next().prop("mes","no");
        	if($(this).attr("id") == "bookFee"){
        		payAmount = Number(payAmount) - Number(rmoney($(this).attr("bCost")));
        		realAmount  = Number(realAmount) - Number(rmoney($(this).attr("bCost")));
        		$("#payAmount").val(fmoney(payAmount,2));
            	$("#realAmount").val(fmoney(realAmount,2));
            	ifBookFee = 0;
            	return;
        	}
            payAmount  = Number(payAmount) - Number(rmoney($(this).attr("mCost")));
            realAmount   = Number(realAmount) - Number(rmoney($(this).attr("mCost"))) * Number($(this).attr("dis")) ;
            discountMoney  = Number(payAmount) - Number(realAmount);
            $("#payAmount").val(fmoney(payAmount,2));
            $("#realAmount").val(fmoney(realAmount,2));
            $("#discount").val(fmoney(discountMoney,2));
        }
    });
});

function initMonthCost(){
	var orgId = $("#orgId").val();
	var payYear = $("#payYear").val();
	$.post('<%=path%>/supervisory/asyn/getMonthCost',{"conId":conId,"payYear":payYear},function(result){
		var mCostMap = result["mCostMap"];
		var bCostMap = result["bCostMap"];
		for(var key in mCostMap){
			var costMap = mCostMap[key].split(",");
			var mCost = costMap[0];
			var dis = costMap[1];
			$("#"+key).attr("mCost",mCost);
			$("#"+key).attr("dis",dis);
		}
		$("#bookFee").attr("bCost",bCostMap["bCost"]);
		$("#bookFeeDiv").html("账本费: " + bCostMap["bCost"] + "元");
	});
}

function checkAuditIntegrity(){
	var auditFlag = $("#auditFlag").is(":checked");
	var payAmount = $("#payAmount").val();
	var payMonthsArray = $("[name='months']:checked");
	//if(payMonthsArray.length == 0){
	//	parent.$.showMsg("请至少选择一个月份!", 2);
	//	return false;
	//}
	if(payAmount == ""||payAmount == 0||payAmount == null){
		parent.$.showMsg("需要有金额的合同才允许缴费!", 2);
		return false;
	}
	/*
	if(checkPayMonthIfInARow(payMonthsArray)){
		if(auditFlag){
			tips = layer.msg("正在验证审批流...", {icon : 16,time : 0, shade: [0.1]});
			$.post('<%=path%>/supervisory/asyn/checkAuditIntegrity',{'auditType':1},function(result){
				if(result.code){
					saveCustomPay();
				}else{
					layer.close(tips);
					parent.$.showMsg("审批流未构建成功,引入失败!", 2);
				}
			});
		}else{
			saveCustomPay();
		}	
	}
	*/
	if(auditFlag){
		tips = layer.msg("正在验证审批流...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/checkAuditIntegrity',{'auditType':1},function(result){
			if(result.code){
				saveCustomPay();
			}else{
				layer.close(tips);
				parent.$.showMsg("审批流未构建成功,引入失败!", 2);
			}
		});
	}else{
		saveCustomPay();
	}
}

function checkPayMonthIfInARow(payMonthsArray){
	//除掉只缴账本费的情况
	if(payMonthsArray.length != 0){
		//将之前缴费月份与本次选择全部存到数组中排序
		var months = $("#payMonths").val();
		if(months.length!=0){
			months = months.split(",");
		}else{
			months = new Array();
		}
		var index = months.length;
		for(var i=0;i<payMonthsArray.length;i++){
			months[index+i]=payMonthsArray[i].value;
		}
		months.sort(function (a,b){return a - b});
	
		//只判断合同起始截至月份之间是否连续，是则可以缴费，不是则必须连续月份才可缴费
		var flag=1;//1是连续月份 0不是连续月份
		var ifContStarM = false;
		for(var i=0;i<months.length;i++){
			if(i!=months.length-1){
				if(months[i]==''){//去掉空值
					months.splice(i,1);
				}
				if(months[i]==bMonths){
					ifContStarM=true;//说明已包含了起始月份
				}
				//从起始月开始在合同期限范围内的才判断缴费月份的连续性
				if(ifContStarM&&(parseInt(months[i+1])<parseInt(eMonths)||months[i+1]==eMonths)){
					if(parseInt(months[i+1])-parseInt(months[i])!=1){
						flag=0;
					}
				}
			}
		}
		if(flag!=0){
			//没进入上面循环有3种可能，一种是缴费的合同起始月份与截至月份相等，另一种就是不包含该合同的起始月份,一种只缴一个月的
			 if(bMonths != eMonths&&!ifContStarM&&payMonthsArray.length>1){//既不是合同起始截至月份相同又没包含起始月份又不是指缴一个月
					parent.$.showMsg("合同的起始月份为【"+bMonths+"】,请选择连续月份进行缴费!", 2);
					return false;
				}
		}
		//判断缴费月份是否是合同的起始月
		if(flag==0){//1是连续月份 0不是连续月份
			parent.$.showMsg("请选择连续月份进行缴费!", 2);
			return false;
		}
	}
	return true;
}

function saveCustomPay(){
	var auditFlag = $("#auditFlag").is(":checked");
	var orgId = $("#orgId").val();
	var realAmount = rmoney($("#realAmount").val());
	var payAmount = rmoney($("#payAmount").val());
	var discount = rmoney($("#discount").val());
	var payment = $("#payment").val();
	var demo = $("#demo").val();
	var payYear = $("#payYear").val();
	var payMonthsArray = $("[name='months']:checked");
	var payMonth = "";
	for(var i=0;i<payMonthsArray.length;i++){
		if(i==(payMonthsArray.length-1)){
			payMonth = payMonth + payMonthsArray[i].value;
			break;
		}
		payMonth = payMonth + payMonthsArray[i].value +  ",";
	}
	tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/saveCustomPay',{
		"orgId":orgId,
		"realAmount":realAmount,
		"payAmount":payAmount,
		"payment":payment,
		"discount":discount,
		"demo":demo,
		"payYear":payYear,
		"payMonth":payMonth,
		"ifBookFee":ifBookFee,
		"auditFlag":auditFlag,
		"accNO":accNO,
		"conId":conId
	},function(result){
		if(result.code){
			layer.close(tips);
			parent.$.showMsg("保存成功!", 1);
			closeFrame();
		}else{
			layer.close(tips);
			parent.$.showMsg("保存失败!", 2);
		}
	});
}

function chengeText(){
	var auditFlag = $("#auditFlag").is(":checked");
	if(auditFlag){
		$("#auditText").html("<font color='green'>：已加入</font>");
	}else{
		$("#auditText").html("<font color='red'>：加入审批流程</font>");
	}
}

function closeFrame(){//点击关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.pay);
}
</script>
</head>
<body>
<div style="padding:10px 15px">
    <table id="payTable" width="100%">
        <tr>
            <td width="18%" align="left"><font>公司名称：</font></td>
            <td width="80%" align="left"><font>${orgName}</font>
                <input type="hidden" id="orgId" name="orgId" value="${orgId}">
                <input type="hidden" id="payMonths" name="payMonths" value="${payMonths}">
                <input type="hidden" id="payYear" name="payYear" value="${payYear}">
                <div id="bookFeeDiv" align="right" style=" float:right;"></div>
            </td>
        </tr>
        <tr>
            <td align="left" width="18%"><font>缴费年份：</font></td>
            <td align="left" width="80%">
            	<span>${payYear}年</span>
            </td>
        </tr>
        <tr>
            <td align="left" width="18%" style="vertical-align:top;"><font>收费月份：</font></td>
            <td align="left" width="80%">
                <div class="check_div">
                    <input type="checkbox" value="1" id="month1" name="months" mCost="0" dis="0"/>
                    <label for="month1" class="label_mon">1</label>
                    <input type="checkbox" value="2" id="month2" name="months" mCost="0" dis="0"/>
                    <label for="month2" class="label_mon">2</label>
                    <input type="checkbox" value="3" id="month3" name="months" mCost="0" dis="0"/>
                    <label for="month3" class="label_mon">3</label>
                    <input type="checkbox" value="4" id="month4" name="months" mCost="0" dis="0"/>
                    <label for="month4" class="label_mon">4</label>
                    <input type="checkbox" value="5" id="month5" name="months" mCost="0" dis="0"/>
                    <label for="month5" class="label_mon">5</label>
                    <input type="checkbox" value="6" id="month6" name="months" mCost="0" dis="0"/>
                    <label for="month6" class="label_mon">6</label>
                    <input type="checkbox" value="7" id="month7" name="months" mCost="0" dis="0"/>
                    <label for="month7" class="label_mon">7</label>
                    <input type="checkbox" value="8" id="month8" name="months" mCost="0" dis="0"/>
                    <label for="month8" class="label_mon">8</label>
                    <input type="checkbox" value="9" id="month9" name="months" mCost="0" dis="0"/>
                    <label for="month9" class="label_mon">9</label>
                    <input type="checkbox" value="10" id="month10" name="months" mCost="0" dis="0"/>
                    <label for="month10" class="label_mon">10</label>
                    <input type="checkbox" value="11" id="month11" name="months" mCost="0" dis="0"/>
                    <label for="month11" class="label_mon">11</label>
                    <input type="checkbox" value="12" id="month12" name="months" mCost="0" dis="0"/>
                    <label for="month12" class="label_mon">12</label>
                    <input type="checkbox" value="13" id="bookFee" name="bookFee" bCost="0"/>
                    <label for="bookFee" class="label_mon" style="width:60px">账本费</label>
                </div>
                <font>(请选择需要缴费的月份)</font></td>
        </tr>
        <tr>
            <td align="left" width="18%"><font>付款方式：</font></td>
            <td align="left" width="80%">
                <select id="payment" class="Taxes" style="width:143px;">
                    <option value="1">现金</option>
                    <option value="2">转账</option>
                    <option value="3">其他</option>
                </select>
            </td>
        </tr>
        <tr>
            <td align="left" width="18%"><font>应收金额：</font></td>
            <td align="left" width="80%"><input type="text" id="payAmount" name="payAmount" readonly="readonly" class="txt140">(RMB)</td>
        </tr>
        <tr>
            <td align="left" width="18%"><font>实收金额：</font></td>
            <td align="left" width="80%"><input type="text" id="realAmount" name="realAmount" readonly="readonly" class="txt140">(RMB)</td>
        </tr>
        <tr>
            <td align="left" width="18%"><font>缴费折扣：</font></td>
            <td align="left" width="80%"><input type="text" id="discount" name="discount" readonly="readonly" class="txt140">(RMB)</td>
        </tr>
        <tr>
            <td align="left" width="18%" style="vertical-align:top;"><font>补充说明：</font></td>
            <td align="left" width="80%"><textarea name="demo" id="demo" class="txt450"></textarea></td>
        </tr>
        <tr>
        	<td align="center" width="18%"><input id="auditFlag" name="auditFlag" type="checkbox" onchange="chengeText();"></td>
            <td align="left" width="80%" id="auditText"><font color="red">：加入审批流程</font></td>
        </tr>
    </table>
    <div align="right" style="padding-right: 5px;">
		<input name="saveCus"  type="button" id="saveCus" style="padding: 3px 7px 3px 7px;"  value="保存" class="bcssbtn" onclick="checkAuditIntegrity()" /> 
		<input name="closeTab" type="button" id="closeTab" style="padding: 3px 7px 3px 7px" value="关闭"  class="acssbtn" onclick="closeFrame()"/>
	</div>
</div>
</body>
</html>