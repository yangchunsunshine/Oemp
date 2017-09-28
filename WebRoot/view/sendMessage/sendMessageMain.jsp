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
<title>发送短信</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">
var flag = "1"; //标识是否已经提示过字数超过70
var thisGrid;
var tips;
$(function(){
	$("#canUse").select2({
		language: "zh-CN"
	});
	thisGrid = $("#processTable").jqGrid({
		url: '<%=path%>/sendmessage/forward/showorganization',
		datatype: 'json',
		colNames: ['选择','公司名称','电话'],
		colModel: [
			{name:'ORGID',index:'ORGID',sortable:true,width:13,align:"center",formatter:addCheckBox},
			{name:'ORGNAME',index:'ORGNAME',sortable:true,width:29,align:"center"},
		    {name:'mobile',index:'mobile',sortable:true,width:30,align:"center"}
		],
		rowNum: 50,
		rowList: [50,100,200],
		pager: '#gridPager',
		sortname: 'ownerId',
		sortorder: "asc",
		rownumbers: true,
		rownumWidth: 50,
		caption: '业务列表',
		mtype: "POST",
		width:$(window).width()-40,
        height:$(window).height()-250,
		viewrecords: true,
		loadComplete: function (data){
			authView();
		},
		hidegrid: false
	});
	jQuery("#processTable").jqGrid('navGrid',"#gridPager",{search:false,add:false,edit:false,view:false,del:false,refresh:true,refreshtext:'<spring:message code="REFRASH_TIP"/>',alerttext:"<spring:message code="ALERT_TEXT_NONE_ROW_SELECT"/>"});
	jQuery("#jqgh_processTable_rn").prepend("序号");
});


//初始化textarea的事件
$(document).bind('propertychange input', function () {
	var counter = $('#sendContent').val().length;
	if(flag!="2"){
		if(counter>=71){
			flag = "2";
			parent.$.showMsg("温馨提示:短信发送内容分为2条发送",1);
		}
	}
	if(counter>300){
		var text = $('#sendContent').val().substring(0,300);
		$('#sendContent').val(text);
		counter = $('#sendContent').val().length;
	}
	$("#num").text(counter);
	
});

 
//下拉框变动的时候 改变一下 导入模板的属性
/* function modifyValue(){
	var canUse = $("#canUse").val();
	if(canUse!=0){
		$("#importModel").css('display','none'); 
	}else{
		$("#importModel").css('display','block'); 
	}
} */




// 添加复选框
function addCheckBox(cellvalue,options,rowObject){
	var mobile=rowObject.mobile;
	var ORGID=rowObject.ORGID;
	var divH = "<div>";
	var divB = "</div>";
	var c="<input id='checkNAme' type='checkbox' name='checkName' onclick='chosenum();' value=\""+mobile+'|'+ORGID+"\"/>";
	return divH+c+divB;
}

// 点中复选框计算有多少公司
function chosenum(){
	var arr=document.getElementsByName("checkName");
	var num=0;
	for(var i=0;i<arr.length;i++){
	    if(arr[i].checked){
			num++;
	    	}
	}
	
	var dv=document.getElementById("yixuan");
    dv.innerText=num;	
}




function canUseFormat(cellvalue,options,rowObject){
	if(cellvalue == 0){
		return"<font color='red'>【未启用】</font>";
	}else{
		return"<font color='green'>【已启用】</font>";
	}
}

function openWin(id,obj)
{
    obj.target="_blank";
    obj.href = "/supervisory/forward/gotoAddNode?proId='+proId"+id;
    obj.click();
} 



var addNodeLayer;
function gotoAddNode(proId){
	addNodeLayer = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoAddNode?proId='+proId,
		end : function(){
			searchfb();
		}
	});
}

var updateNodeLayer;
function gotoUpdateNode(modelId){
	updateNodeLayer = layer.open({
		title : ' 查看模板节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/business/forward/gotoBusinesslNode?processTmpId='+modelId,
       end : function(){
			searchfb();
		}
	});
}

var updateNodeLayer1;
function gotoUpdateNode1(){
	var flag = false;
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true)
		  {
			 flag = true;
			 val=checkName[i].value;
			 ary.push(val);
		  }
	
	}
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	 
	 if(ary.length>1){
		 parent.$.showMsg("只能选择一项业务模板", 2);
		  return;
	 }
	
	updateNodeLayer1 = layer.open({
		title : ' 添加节点',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/supervisory/forward/gotoUpdateNodeAfter?processTmpId='+val,
		end : function(){						 
			searchfb();
		}
	});
}

var updateNodeLayer2;
function gotoUpdateNode2(){
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var value=getAllTemp();
	 $.post('<%=path%>/supervisory/asyn/addUserTempAndNode',
			 {
			'array':value.join(",")
		    },function(result){
			if(result.code==2){
				parent.$.showMsg("保存成功!", 1, function(){
					layer.close(tips);
					closeAddProcessLayer();
					searchfb();
				});
			}else if(result.code==3){
				parent.$.showMsg("保存失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}else if(result.code==1){
				parent.$.showMsg("模板业务已经存在!", 2,function(){
					layer.close(tips);
					closeAddProcessLayer();
					searchfb();
				});
			}
		});
 
}


var addProcessLayer1;
function gotoAddProcess1(){
	addProcessLayer1= layer.open({
		title : '添加业务',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['500px', '300px'],
		content : $("#addProcessDiv1"),
		end : function(){
			$("#addProcessName1").val("");
			$("#canUse1").val("");
			searchfb();
		}
	});
}

function closeAddProcessLayer(){
	window.layer.close(window.addProcessLayer);
}

function closeAddProcessLayer1(){
	window.layer.close(window.search);
}


function saveProcess(){
	var processName = $("#addProcessName").val();
	if($.ckTrim(processName) == ""){
		parent.$.showMsg("请填写节点名称!", 2);
		return;
	}
	 var reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
	 if(reg.test(processName)){  
		 parent.$.showMsg("您输入的节点名称含有非法字符！", 2);
		 $("#addProcessName").val('');
		 $("#addProcessName").focus(); 
		 return false;  
	 } 
	var checkName = document.getElementsByName("checkName");
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true){
			 val= checkName[i].value;
			 break;
		 }
	}
	
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/supervisory/asyn/SaveNode',{
		'nodeName':processName,
		'processTmpId':val
	},function(result){
		if(result.code==0){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer();
			});
		}else if(result.code==1){
			parent.$.showMsg("保存失败,系统出错!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==2){
			parent.$.showMsg("业务名称已存在!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==10){
			parent.$.showMsg("已经存在协同关系,请重建关系再添加!", 2,function(){
				layer.close(tips);
			});
		}else if(result.code==11){
			parent.$.showMsg("节点名称含有特殊字符！", 2,function(){
				layer.close(tips);
			});
		}
	});
}

function deleteProcess(processId){
	layer.confirm("确认删除!", {icon: 3, title:"提示"}, function(index){
		tips = layer.msg("正在删除,请稍后...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteProcessTemp',
		{
			'id': processId
		},
		function(result){
			if(result.code){
				layer.close(tips);
				searchfb();
				$.showMsg("删除成功!",1);
			}else{
				layer.close(tips);
				$.showMsg("删除失败,系统出错!",2);
			}
		});
		layer.close(index);
	});
}
var update;
function deleteProcessSoft(processId){
	
	update = layer.open({
		title : ' 修改业务模板',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['600px', '450px'],
		content : '<%=path%>/business/forward/gotoUpdateProceTemp?processTmpId='+processId,
		end : function(){
			//window.layer.close(window.update);
			window.layer.close(window.update);
			searchfb();
			//关闭页面
		}
	});
	
}

//查询
function searchfb(){
	var processName = $("#processName").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/sendmessage/forward/showorganization?canUse='+canUse, page : 1}).trigger("reloadGrid");
}

// 提交节点
var addProcessLayer;
function addNode(){
	var flag = false;
	var checkName = document.getElementsByName("checkName");
	var ary = new Array();
	var val;
	for(var i=0;i<checkName.length;i++){
		 if(checkName[i].checked == true)
		  {
			 flag = true;
			 val=checkName[i].value;
			 ary.push(checkName[i].value);
		  }

	}
	
	 if(!flag){
		  //alert("请至少选择一项业务模板");
		  parent.$.showMsg("请选择一项业务模板", 2);
		  return;
		 }
	if(ary.length>1){
		 parent.$.showMsg("只能选择一项模板", 2);
		 return;
	}else{
		addProcessLayer= layer.open({
			title : '添加节点',
			shade : [ 0.1, '#000' ],
			fix : true,
			type : 1,
			shadeClose: true,
			area : ['300px', '100px'],
			content :$("#addProcessDiv"),
			end : function(){
				$("#addProcessName").val("");
				searchfb();
			}
			});
	}
	
}


function savebusiness(){
	var orgId=$("#orgId").val();
	var topname=$("#topname").val();
	tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.post('<%=path%>/sendmessage/forward/savemessage',{
		'orgId':orgId,
		'topname':topname
	},function(result){
		if(result.code==2){
			parent.$.showMsg("保存成功!", 1, function(){
				layer.close(tips);
				closeAddProcessLayer1();
				searchOragname();
			});
		}else if(result.code==1){
			parent.$.showMsg("抬头已经改过，不能再次修改！", 2,function(){
				layer.close(tips);
			});
		}else{
			layer.close(tips);
			$.showMsg("删除失败,系统出错!",2);
		}
	});
}
// 全选
function selectAll(){
	var arr=document.getElementsByName("checkName");
	var num=0;
	for(var i=0;i<arr.length;i++){
		arr[i].checked=true;
		num++;
		} 
	var dv=document.getElementById("yixuan");
    dv.innerText=num;
}
// 取消
function cancleAll(){
    var num=0;
	var arr=document.getElementsByName("checkName");
	for(var i=0;i<arr.length;i++){
		arr[i].checked=false;
		} 
	var dv=document.getElementById("yixuan");
    dv.innerText=num;
}

//反选
function fanxuan(){
	var count=0;
	var sum=0;
	var arr=document.getElementsByName("checkName");
	for(var i=0;i<arr.length;i++){
	    if(arr[i].checked){
			arr[i].checked=false;
		                  }
		else{
			arr[i].checked=true;
			count++;
			   }
	}
	sum=count;
	var dv=document.getElementById("yixuan");
    dv.innerText=sum;
	
}
// 点击所搜查询
function searchOragname(){
	var orgname=$("#orgname").val();
	thisGrid.jqGrid('setGridParam',{url:'<%=path%>/sendmessage/forward/showorganizationByChose?orgName='+encodeURIComponent(encodeURIComponent(orgname)), page : 1}).trigger("reloadGrid");
}
// 点击抬头
var search;
function subgo(){
	search= layer.open({
		title : '申请变更短信抬头',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['400px', '300px'],
		content : $("#addProcessDiv1"),
		end : function(){
			$("#addProcessName1").val("");
			searchfb();
		}
	});
	
}
// 立即发送
function rightNowSend(){
	var arr=document.getElementsByName("checkName");
	var array=[];
	for(var i=0;i<arr.length;i++){
	    if(arr[i].checked){
	    	array.push(arr[i].value);
	    }
	}
	
	if(array.length==0){
		parent.$.showMsg("请选择手机号发送",2);
		return ;
	}
	var senContent = $("#sendContent").val();
	if(senContent==""||senContent==null){
		parent.$.showMsg("短信内容不能为空",2);
		return ;
	}
	if(senContent.length>=300){
		$("#checkCount").text("");
		$("#checkCount").append("<font color='red'>限300字</font>"); 
		return ;
	}
	
	tips = layer.msg("正在发送，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
			url : '<%=path%>/sendmessage/forward/rightNowsendmessage',     // 跳转到 action  
			data : {
				mytext : senContent,
				array  : array.join(",")
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(result) {
				if (result.code =="success") {
					parent.$.showMsg("发送成功!", 1, function(){
						layer.close(tips);
						// closeAddProcessLayer1();
						//searchOragname();
						$("#sendContent").val("");
					});
				}else{
					parent.$.showMsg(result.code, 2, function(){
						layer.close(tips);
						// closeAddProcessLayer1();
						//searchOragname();
						$("#sendContent").val("");
					});
				}
			},
			error : function() {
				parent.$.showMsg("发送失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}
		});

	}


// 定时发送
var search1;
function waitSend(){
	var senContent = $("#sendContent").val();
	if($.ckTrim(senContent) == ""){
		parent.$.showMsg("请编辑短信内容！", 2);
		return;
	}
		search1= layer.open({
			title : '选择时间',
			shade : [ 0.1, '#000' ],
			fix : true,
			type : 1,
			shadeClose: true,
			area : ['300px', '150px'],
			content : $("#showtime"),
			end : function(){
				
			}
		});
		
	}
	// 提交时间
	function submittime(){
		var time=$("#payYear").val();
		var senContent = $("#sendContent").val();
		var arr=document.getElementsByName("checkName");
		var array=[];
		for(var i=0;i<arr.length;i++){
		    if(arr[i].checked){
		    	array.push(arr[i].value);
		    }
		}
		
		if(array.length==0){
			parent.$.showMsg("请选择手机号发送",2);
			return ;
		}
		tips = layer.msg("正在保存短信，请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			url : '<%=path%>/sendmessage/forward/waitsendMessage',     // 跳转到 action  
			data : {
				mytext : senContent,
				array  : array.join(","),
				dateTime   : time
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(result) {
				if (result.code ==1) {
					parent.$.showMsg("发送成功!", 1, function(){
						layer.close(tips);
						closeAddProcessLayer1();
						window.layer.close(window.search1);
						//searchOragname();
						$("#sendContent").val("");
					});
				}
			},
			error : function() {
				parent.$.showMsg("发送失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}
		});
		
		
	}

	/* jQuery(document).ready(function() {
		var mytext = document.getElementById("mytext");
		var orgName = $("#orgName").val();
		mytext.innerText = '【理财金服】' + orgName;
	}); */
	

	
</script>
</head>
<body>

<div id="mainDiv" style="padding-top: 10px">
	<div>
		<input maxlength="18" id="orgname" placeholder="请输入公司名称进行查询" name="orgname" type="text" class="dfinput_fb" value="" style="width:180px;height:24px;"/>
		<input class="bcssbtn" type="button" style="display:none;padding: 2px 6px 2px 6px" onclick="searchOragname();" id="sou_search" value="搜索" style="display:none;height:30px;margin-left:100px;" />
		<input class="bcssbtn" id="selectAll" type="button" onclick="selectAll(this);" style="display:none;padding: 2px 6px 2px 6px;" value="全选"/>
		<input class="bcssbtn" id="cancleAll" type="button" onclick="cancleAll(this);" style="display:none;padding: 2px 6px 2px 6px;" value="取消"/>
		<input class="bcssbtn" id="fanxuan" type="button" onclick="fanxuan(this);" style="display:none;padding: 2px 6px 2px 6px;" value="反选"/> 
		已选择<span id="yixuan">0</span>个公司 &nbsp;&nbsp;&nbsp; 共有<span id="yixuan">${requestScope.num}</span>个公司
		<table id="processTable"></table>
		<div id="gridPager"></div>
	</div>
</div>


<input type="hidden" id="orgId" name="adminOrgId" value="${requestScope.orgId}" />
<input type="hidden" id="orgName" name="orgName" value="${requestScope.orgName}" />


<div style="padding-top: 20px">
	<div><a href="javascript:void(0)" onclick="subgo();">【理财金服】</a>&nbsp;&nbsp;&nbsp;&nbsp;${requestScope.orgName}</div>

	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<div style="width:80px;float:left;">
			<span>发送内容:</span>
		</div>
		<div style="">
			<textarea id="sendContent" name="mytext"  style="width:300px;height:200px;border:grey solid 1px;"></textarea>
		</div>
		<div style="margin-left:340px;font-size:9px;" id="checkCount"><span id="num">1</span><span >/300字</span></div>
		
		<div style="margin-top:5px;">
			<input type="button" id="right_now" class="bcssbtn" style="padding: 2px 6px 2px 6px;margin-left:150px;" onclick="rightNowSend();" value="立即发送" />
			<input type="button" id="wait_send" class="bcssbtn" style="padding: 2px 6px 2px 6px" onclick="waitSend();" value="定时发送" />
		</div>
	</div>
	

</div>


<div id="addProcessDiv" style="display:none;padding-bottom: 80px">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 50px">
		<tr>
			<td><a href="javascript:void(0)" onclick="subgo();">【理财金服】</a></td>
			<td>${requestScope.orgName}</td>
		</tr>
	</table>
</div>  
		
		<div style="display:none;" id="showtime" align="center">
			<table>
				<tr>
					<td>发送时间:</td>
					<td>
					<input type="text" id="payYear" name="payYear" onfocus="WdatePicker({isShowClear:false,readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'});"  class="Wdate" style="margin-top:5px;height: 25px" />
					</td>
				</tr>
				
				<tr>
					<td>
					<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="submittime();" value="提交" />
					</td>
				</tr>
			
			
			
			</table>
			
			
		</div>
	

<div id="addProcessDiv1" style="display:none;width: 400px;height: 150px;" align="center">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td>代理公司名称：</td>
			<td>${requestScope.orgName}</td>
		</tr>
		
		<tr>
			<td>代理公司账号：</td>
			<td> ${requestScope.mobile}</td>
		</tr>
		
		<tr>
			<td>短信抬头：</td>
			<td><input type="hidden" id="topname" name="topname" value="理财金服"/>理财金服</td>
		</tr>
		
		<tr>
			<td colspan="2" style="color: red;">仅能变更一次短信抬头，请谨慎使用</td>
		</tr>
	
	<tr>
		<td align="left" style="border:0;">
				<input class="acssbtn" type="hidden" style="padding: 2px 6px 2px 6px;" onclick="savebusiness();" value="提交" />
			</td>
	</tr>
	</table>
</div>



</body>  
</html>