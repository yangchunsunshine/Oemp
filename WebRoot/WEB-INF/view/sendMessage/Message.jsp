<%@page import="com.wb.model.entity.computer.SendMessage"%>
<%@page import="util.PageModel"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_ROLER_URL");%>
<%String path = request.getContextPath();%>
<%
	
    PageModel pageModel=(PageModel)request.getAttribute("pageModel");
    
 %>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>短信详情</title>
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
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" /><script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/zTree/js/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/util/validateSms.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/style/css/style_mnt.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqueryui/jquery-ui.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/jqgrid/css/ui.jqgrid.css"/>
<link type="text/css" rel="stylesheet" href="<%=path%>/plugins/zTree/css/zTreeStyle.css" />
<script type="text/javascript">

 
//下拉框变动的时候 改变一下 导入模板的属性
/* function modifyValue(){
	var canUse = $("#canUse").val();
	if(canUse!=0){
		$("#importModel").css('display','none'); 
	}else{
		$("#importModel").css('display','block'); 
	}
} */

function getTop(){
	window.self.location= "<%=path%>/sendmessage/forward/gotoMessage?pageNum=<%=pageModel.getTopPageNo()%>";
	}
function getBottom(){
	window.self.location= "<%=path%>/sendmessage/forward/gotoMessage?pageNum=<%=pageModel.getBottomPageNo()%>";
	}
function getPrevious(){
	window.self.location= "<%=path%>/sendmessage/forward/gotoMessage?pageNum=<%=pageModel.getPreviousPageNo()%>";
	}
function getNext(){
	window.self.location= "<%=path%>/sendmessage/forward/gotoMessage?pageNum=<%=pageModel.getNextPageNo()%>";
	}
// 添加复选框
function addCheckBox(cellvalue,options,rowObject){
	var mobile=rowObject.mobile;
	var divH = "<div>";
	var divB = "</div>";
	var c="<input id='checkNAme' type='checkbox' name='checkName' onclick='chosenum();' value=\""+mobile+"\"/>";
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




function canUseat(cellvalue,options,rowObject){
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
	var mytext=$("#mytext").val();
	var arr=document.getElementsByName("checkName");
	var array=[];
	for(var i=0;i<arr.length;i++){
	    if(arr[i].checked){
	    	array.push(arr[i].value);
	    }
	}
	tips = layer.msg("正在发送，请稍后...", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
			url : '<%=path%>/sendmessage/forward/rightNowsendmessage',     // 跳转到 action  
			data : {
				mytext : mytext,
				array  : array.join(",")
			},
			type : 'post',
			cache : false,
			dataType : 'json',
			success : function(result) {
				if (result.code ==1) {
					parent.$.showMsg("发送成功!", 1, function(){
						layer.close(tips);
						closeAddProcessLayer1();
						//searchOragname();
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
	var mytext=$("#mytext").val();
	if($.ckTrim(mytext) == ""){
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
		var mytext=$("#mytext").val();
		var arr=document.getElementsByName("checkName");
		var array=[];
		for(var i=0;i<arr.length;i++){
		    if(arr[i].checked){
		    	array.push(arr[i].value);
		    }
		}
		tips = layer.msg("正在保存短信，请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.ajax({
			url : '<%=path%>/sendmessage/forward/waitsendMessage',     // 跳转到 action  
			data : {
				mytext : mytext,
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
						//searchOragname();
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

	jQuery(document).ready(function() {
		var mytext = document.getElementsByName("textAre");
		var content = document.getElementsByName("content");
		for(var i=0;i<mytext.length;i++){
			mytext[i].innerText=content[i].value;
		}
	});
	
	var search3;
	var id;
	function  downLoadExcel(id1) {
		search3= layer.open({
			title : '编辑短信',
			shade : [ 0.1, '#000' ],
			fix : true,
			type : 1,
			shadeClose: true,
			area : ['400px', '450px'],
			content : $("#showtime"),
			end : function(){
				
			}
		});
		id=id1;
		$("#hiddenid").val(id);
		$.ajax( {
		    type : "POST",
		    url : "${pageContext.request.contextPath}/sendmessage/forward/findSendMessage?time="+new Date().getTime(),

		    data : {id:id}, //参数
			success : function(data) {
				$("#messageContent").val(data);
		    }
		   
	});
	}
	//修改短信

function updateMessage() {
	var b =validateSms.split("|");
	$("#hiddenContent").val($("#messageContent").val());
	var senContent = $("#hiddenContent").val();
	for(var i=0;i<b.length;i++){
		var substr =b[i];
		if(senContent.indexOf(substr)>=0){
			parent.$.showMsg("短信内容包含非法内容:"+substr,2); 
			return ;
		}
	}
		
	$("#hiddenContent").val($("#messageContent").val());
	tips = layer.msg("正在修改 ,请稍后......", {icon : 16,time : 0, shade: [0.1]});
	$.ajax({
		url : '<%=path%>/sendmessage/forward/updateMessage',     // 跳转到 action  
		data : {
			content :$("#hiddenContent").val() ,
			id  : $("#hiddenid").val()
		},
		type : 'post',
		cache : false,
		dataType : 'json',
		success : function(result) {
			if (result.code ==1) {
				parent.$.showMsg("修改成功！", 1, function(){
					layer.close(tips);
					window.layer.close(window.search3);
					
					var PageNum=$("#PageNum").val();
					window.self.location= "<%=path%>/sendmessage/forward/gotoMessage?pageNum="+PageNum;

				});
			}
		},
		error : function() {
			parent.$.showMsg("修改失败！", 2,function(){
				layer.close(tips);
			});
		}
	});
	
	
	}
	
	
	
var showmessges;
function smsId(id){
	showmessges = layer.open({
		title : ' 短信详情',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 2,
		shadeClose: true,
		area : ['400px', '400px'],
		content : '<%=path%>/sendmessage/forward/showMessages?id='+id,
       end : function(){
			
		}
	});
}


</script>
</head>
<body>
   	<div class="nodeB">
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
			<c:forEach items="${list}" var="upNode" varStatus="status">
				<div>
						<tr>
						<td align="left">
							<input class="2" name="content" id="content" mes="content" type="hidden" value="${upNode.content}"/>
							<label>
							<c:if test="${upNode.status!=2}">
								将于${upNode.sendtime} 发送   
							</c:if>
							<c:if test="${upNode.status==2}">
							  	${upNode.sendtime}
						  	</c:if>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<font>
							<c:if test="${upNode.status==2}">
								<span style="color: black">已发送</span>
							</c:if>
							<c:if test="${upNode.status!=2}">
							  	<span style="color: red">未发送</span>
						  	</c:if>
						  	</font>
						  	</label>
						</td>
						</tr>
						<tr>
						<td align="left" style="height: auto;">
							<textarea rows="3" name="textAre" cols="100" id="textAre" readonly="readonly"></textarea>
							<c:if test="${upNode.status!=2}">
							  	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  	<a id="editId" onclick="downLoadExcel(${upNode.id});" class="acssbtn" style="display:none;padding: 3px 4px 2px 4px;">编辑</a>
				
						  	</c:if>
						  	<input type="button" class="acssbtn" value="短信详情" onclick="smsId(${upNode.id});"  style="padding: 3px 4px 2px 4px;"/>
						</td>
						<td>
							
						</td>
						</tr>
						<tr>
						<td></td>
						</tr>
				</div>
			</c:forEach>
   		</table>
   	</div>
   	 
   	 <table align="center">
     <tr>
     	<td>
     		<div>
     			<font>共有<%=pageModel.getTotalPages()%>页</font>&nbsp;&nbsp;&nbsp;
     			<font>当前第<%=pageModel.getPageNum() %>页</font>&nbsp;&nbsp;&nbsp;
     			<input type="hidden" id="PageNum" name="PageNum" value="<%=pageModel.getPageNum() %>"/>
     		</div>
     	</td>
     	     
     		<td>
     			<div>
         		<input type="button" value="首页" onclick="getTop()"/>&nbsp;&nbsp;&nbsp;
         		<input type="button" value="尾页" onclick="getBottom()"/>&nbsp;&nbsp;&nbsp;
         		<input type="button" value="上一页" onclick="getPrevious()"/>&nbsp;&nbsp;&nbsp;
         		<input type="button" value="下一页" onclick="getNext()"/>
         		</div>
         	</td>
           		
     </tr>
     </table>
     
     <div style="display:none;width: 400px;height: 600px" id="showtime" align="center">
    
    	<br /><br />
     	<textarea rows="15" cols="40" style="border: solid 1px #006DCC;" id="messageContent"></textarea>
		<br />
		<input type="hidden" id="hiddenContent" name="content"/>
		<input type="hidden" id="hiddenid" name="id"/>
		<input id="saveId" type="button"  value="保存"  onclick="updateMessage()" class="acssbtn" style="padding: 2px 6px 2px 6px"/>	
	</div>
	
	
	 <div style="display:none;width: 400px;height: 600px" id="showmessage" align="center">
		<input type="hidden" id="hiddenContent" name="content"/>
		<input type="hidden" id="hiddenid" name="id"/>
	</div>
	
	
     
     
</body> 
</html>