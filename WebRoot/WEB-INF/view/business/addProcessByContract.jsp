<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%
	PropertiesReader reader = PropertiesReader.getInstance();
%>
<%
	String url = "";
%>
<%
	String path = request.getContextPath();
%>


<!DOCTYPE html>
<html>
<head>
<title>业务添加</title>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript"
	src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/plugins/select2/css/select2.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/style/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/style/css/style_mnt.css" />
<link rel="stylesheet" type="text/css"
	href="<%=path%>/style/css/sxCss/css/styles.css" />
	<script type="text/javascript">
	var processId =${processId};  //模板业务id 
	var contractId =${contractId}; //合同编码 
	var orgId ;
	var contractType ;
	$(function(){
		initOrg();//加载公司信息和业务信息
		initProcessInfo();//加载业务信息
		getOrgRole(); //加载环节信息信息
	});
	
	
	
	function getOrgRole(){
		var ACC_ROLER_URL = '<%=url%>';
		var orgId = ${userInfo.orgId};
		var url = ACC_ROLER_URL+"/auth/sysauth/role/get.jspx";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "jsonp",  
	       	jsonp : "callBack",
	       	data : {"roleType":"0001","refCompanyId":orgId},
			url  : url,
	       	success : function(data){
	       		var mess = data.results;
	       		var roleStr = "<option value=''>选择角色：</option>";
	       		for(var i=0;i<mess.length;i++){
					roleStr = roleStr + "<option value='"+mess[i].rid+"'>"+mess[i].roleName+"</option>";
	       		}
	       		$("#roleSelect").html(roleStr);
	       	},
	       	error : function(XMLHttpRequest, textStatus, errorThrown) {
				parent.$.showMsg("亲!您的网络不给力哦~", 2, null, 5000);
			}
		});
	}

	
	
	function initOrg(){//加载公司信息
		//根据合同id查询公司信息和合同信息
		$.ajax( {   
			type : 'post',
			data : {contractId:contractId},
			dataType : 'json',
			url : '<%=path%>/business/asyn/getContractInfo',
			beforeSubmit : function() {
				tips = layer.msg("正在处理...",{icon : 16,time : 0, shade: [0.1]});
			},
			success : function(result) {
				
				var contractInfo =  result.contractInfo ;
				var cusId = contractInfo.cusId;
				orgId = cusId;
				var cusName = contractInfo.cusName;
				var cusId = contractInfo.cusId;
				var seqCode = contractInfo.seqCode;
				contractType =  contractInfo.contractType;
				$("#ser_type_input").val(contractType);
				// 将值赋到页面上
				$("#com_id_input1").val(seqCode);
				$("#com_id_input").val(cusId);
				$("#com_name_input").val(cusName);
			},
			error : function(result) {
				alert("亲!您的网络不给力哦~");
			}
		});
		
	}
	function initProcessInfo(){//加载业务信息
		//根据合同id查询公司信息和合同信息
		if(processId!="-1"&&processId!=""){
			$.ajax( {   
				type : 'post',
				data : {processId:processId},
				dataType : 'json',
				url : '<%=path%>/business/asyn/getProcessInfoById',
				beforeSubmit : function() {
					tips = layer.msg("正在处理...",{icon : 16,time : 0, shade: [0.1]});
				},
				success : function(result) {
					
					var ProcessInfo =  result.ProcessInfo ;
					var processName = ProcessInfo.processName;
					// 将值赋到页面上
					$("#ser_name_input").val(processName);
				},
				error : function(result) {
					alert("亲!您的网络不给力哦~");
				}
			});
			
		}
	}
	
	
	function operaFormat(cellvalue,options,rowObject) {
		return "<input id='addNode' type='button' value='修改' onclick=\"goNodePage('"+proId+"');\" style='height:23px;font-size:12px;text-align: center;padding-top:0px; padding-bottom:0px;' class='bcssbtn'/>&emsp;";
	}
	
	function showNumFormart(processType){
		if(processType==200101){
			processType='代理记账';
		}else if(processType==200100){
			processType='工商注册';
		}else if(processType==200102){
			processType='法律咨询';
		}else if(processType==200103){
			processType='人事代理';
		}else if(processType==200104){
			processType='商标专利';
		}else if(processType==200105){
			processType='其他';
		}
		return processType;
	}
	
	function saveServices(orgName,servicesType,servicesName){	
			var orgId = $("#com_id_input").val();
			var orgName = $("#com_name_input").val();
			var servicesType = $("#ser_type_input").val();
			var servicesName = $("#ser_name_input").val();
			
			
			var url ='<%=path%>/supervisory/forward/saveServices';
			var param = "{\"orgId\":" + orgId + ",\"orgName\":\"" + orgName + "\",\"servicesType\":\"" + servicesType + "\",\"servicesName\":\"" + servicesName + "\"}";
			$.ajax({
				type : "post",
				timeout : 5000,
		        dataType : "json",
		       	data : {"param":param},
				url  : url
			});		
		alert("保存成功");
	}
	//选择模板按钮跳转页面
	
		var queryBusi;
		function selModel(){//进入查看业务信息页面
			queryBusi = layer.open({
				title : '选择业务模板', 
				shade : [ 0.1, '#000' ],
				fix : true,
				type : 2,
				maxmin: true,
				area : ['850px', '580px'],
				content : '<%=path%>/business/forward/gotoBusinessView?pageName=selModelByContract&contractId='+contractId+'&processId='+processId+'&orgId='+orgId,
				end : function(){// 关闭页面执行的操作 此处先空着
					//searchfb();
				}
			});
		}
		
		
		// 业务节点操作方法 start
		function addNodeTr(){
		var addRoleText = $("#roleSelect").find("option:selected").text();
		var addRoleId = $("#roleSelect").val();
		var addNodeName = $("#addNodeName").val();
		var repeat = checkRepeat(addRoleId,addNodeName);
		if(repeat){
			parent.$.showMsg("节点名称重复!", 2);
			return;
		}
		if(!addRoleId){
			parent.$.showMsg("请选择角色!", 2);
			return;
		}
		if(!addNodeName){
			parent.$.showMsg("请填写节点名称!", 2);
			return;
		}
		var taxRow = "<tr>"+
									"<td align='left'>"+addRoleText+
										"<input mes='roldId' type='hidden' value='"+addRoleId+"'>"+
									"</td>"+
									"<td align='left'>"+addNodeName+
										"<input mes='nodeName' type='hidden' value='"+addNodeName+"'>"+
									"</td>"+
									"<td align='center'>"+
										"<input type='button' value='上移' onclick=\"up(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
										"<input type='button' value='下移' onclick=\"down(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
										"<input type='button' value='删除' onclick=\"delNodeTr(this);\" style='height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px' class='bcssbtn'/>"+
									"</td>"+
								"</tr>";
		$("#nodeTable tr:last").after(taxRow);
	}
	
	function up(obj){
		alert(1111);
		var objParentTR = $(obj).parent().parent();
		var prevTR = objParentTR.prev().children().children("input.2").val();//上一个的值
		var value=$(obj).parent().parent().children().children("input.2").val();
		var prevTRId = $(obj).parent().parent().prev().attr("id");
		if(prevTRId=="beginTr"){
			return;
		}
		if (objParentTR.prev().length > 0) {
			objParentTR.prev().children().children("input.2").val(value);
			$(obj).parent().parent().children().children("input.2").val(prevTR);
		}
	}
	
	function down(obj){
		var objParentTR = $(obj).parent().parent();
		var nextTR = objParentTR.next().children().children("input.2").val();
		var value=$(obj).parent().parent().children().children("input.2").val();
		if (objParentTR.next().length > 0) {
			objParentTR.next().children().children("input.2").val(value);
			$(obj).parent().parent().children().children("input.2").val(nextTR);
		}
	}
	
	function delNodeTr(obj,id){//点击税务的删除按钮,删除一条税务信息
		//var id = $("input[mes='id']").val();
		//alert(id);
		$.ajax({
	        url: "<%=path%>/supervisory/asyn/deleteNodeAfter?id="+id,
	        type: "GET",
	        success: function(data){
	           
	            
	            if(data.code==3){
	            	$(obj).parent().parent().remove();
					//layer.close(tips);
					parent.$.showMsg("删除成功!", 1);
					closeFrame();
				}else if(data.code==2){
					//layer.close(tips);
					parent.$.showMsg("删除失败,系统出错!", 2);
				}else if(data.code==1){
					//layer.close(tips);
					parent.$.showMsg("已存在协同关系，请重建关系后再删除", 2);
				}
	            
	        },
	        error: function(res){
	            parent.$.showMsg("亲您的网络不给力哦~", 2);
	        }
	    });
		
		
	}
	
	function initData(){
		var nodes =[];
		var nodeNameInputs = $(" input[mes='nodeName']");
		//var roldIdInputs = $(" input[mes='roldId']");
		var processId = $("#processId").val();
		var id = $("input[mes='id']");
		$.each(nodeNameInputs,function(index,item){
			var nodeObj = new Object();
			nodeObj.nodeName = $(item).val();
			//nodeObj.roleId = $(roldIdInputs[index]).val();
			nodeObj.processId = processId;
			nodeObj.id = $(id[index]).val();
			//nodeObj.orgId = orgId;
			nodeObj.orderSeq = index*1000;
			nodes.push(nodeObj);
		});
		return nodes;
	}
	
	function checkRepeat(addRoleId,addNodeName){
		var repeat = false;
		var nodeNameInputs = $(" input[mes='nodeName']");
		var roldIdInputs = $(" input[mes='roldId']");
		$.each(nodeNameInputs,function(index,item){
			var nodeName = $(item).val();
			var roleId = $(roldIdInputs[index]).val();
			if(addRoleId==roleId&&addNodeName==nodeName){
				repeat = true;
			}
		});
		return repeat;
	}
	
	function saveNode1(){
		var nodes = initData();
		if(nodes.length==0){
			parent.$.showMsg("至少存在一个节点!", 2);
			return;
		}
		tips = layer.msg("正在保存...", {icon : 16,time : 0, shade: [0.1]});
		$.ajax({
            url: "<%=path%>/supervisory/asyn/saveNodeAfter1",
            type: "POST",
            data: {"nodes":JSON.stringify(nodes)},
            success: function(data){
                if(data.code){
					layer.close(tips);
					parent.$.showMsg("保存成功!", 1);
					
					closeFrame();
				}else{
					layer.close(tips);
					parent.$.showMsg("保存失败,系统出错!", 2);
				}
            },
            error: function(res){
                parent.$.showMsg("亲您的网络不给力哦~", 2);
            }
        });
	}
	
	function closeFrame(){
		var processId = $("#processId").val();
		window.location.href='<%=path%>/supervisory/forward/getNodeInfoByContract?processId='+processId;
	}
	
	var update11;
	function orhelp(processId,nodeId){
		update11 = layer.open({
			title : ' 节点',
			shade : [ 0.1, '#000' ],
			fix : true,
			type : 2,
			shadeClose: true,
			area : ['400px', '400px'],
			content : '<%=path%>/business/forward/gotoProcessHelpTogether?processId='+processId+'&nodeId='+nodeId,
			end : function(){						 
				window.location.href='<%=path%>/supervisory/forward/getNodeInfoByContract?processId='+processId;
			}
		});
		
	}
	
	
	
	// 添加节点对话框
	var addProcessLayer;
	function addNode(){
		var processId = $("#processId").val();
		if("-1"==processId){
			parent.$.showMsg("请先保存业务!", 2);
			return;
		}
		
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
					//searchfb();
				}
			});
		
		
		
	}
	function stripscript() { 
		var s = "???";
		var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）&mdash;—|{}【】‘；：”“'。，、？]");
		var rs = "";  

		for (var i = 0; i < s.length; i++) {   

		     rs = rs + s.substr(i, 1).replace(pattern, '');  

		  }  
		alert(rs);
		return rs;

	}

	
	//添加业务提交按钮
	function saveProcessInfo(){
		//stripscript();
		var processName = $("#ser_name_input").val();
		var processId = $("#processId").val();
		var cusId = $("#com_id_input").val();
		var processType = $("#ser_type_input").val();
		
		if(""==processName){
			parent.$.showMsg("请添加业务名称!", 2);
			 $("#ser_name_input").focus(); 
			return;
		}
		
		 var reg=/[`~!@#$%^&*()_+<>?:"{},.\/;'[\]]/im;  
		 if(reg.test(processName)){  
			 parent.$.showMsg("您输入的业务名称含有非法字符！", 2);
			 $("#ser_name_input").val('');
			 $("#ser_name_input").focus(); 
			 return false;  
		 } 

		if(""==processType){
			parent.$.showMsg("请添加业务类型!", 2);
			return;
		}
		tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/business/asyn/saveProcessInfoByContract',{
			'processName':processName,
			'id':processId,
			'mngId':cusId,
			'processType':processType,
			'cusContractId':contractId
		},function(result){
			if(result.code==0){
				$("#processId").val(result.processId);
				parent.$.showMsg("保存成功!", 1, function(){
					layer.close(tips);
					//closeAddProcessLayer();
				});
			}else if(result.code==1){
				parent.$.showMsg("保存失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}else if(result.code==10){
				parent.$.showMsg("业务名称重复!", 2,function(){
					layer.close(tips);
				});
			}
		});
	}
	
	
	var updateNodeLayer1;
	//添加节点提交按钮
	function saveProcess(){
		var processName = $("#addProcessName").val();
		var processId = $("#processId").val();
		if("-1"==processId){
			parent.$.showMsg("请选择模板业务!", 2);
			return;
		}
		
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
		
		tips = layer.msg("正在保存，请稍后...", {icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/saveProcessNode',{
			'nodeName':processName,
			'processId':processId
		},function(result){
			if(result.code==0){
				parent.$.showMsg("保存成功!", 1, function(){
					window.location.href='<%=path%>/business/forward/gotoBusinessView?pageName=addProcessByContract&processId='+processId+'&contractId='+contractId;
							//layer.close(tips);
					window.layer.close(window.addProcessLayer);
				});
			}else if(result.code==1){
				parent.$.showMsg("保存失败,系统出错!", 2,function(){
					layer.close(tips);
				});
			}else if(result.code==2){
				parent.$.showMsg("环节名称已存在!", 2,function(){
					layer.close(tips);
				});
			}else if(result.code==10){
				parent.$.showMsg("已经存在协同关系,请重建关系再添加!", 2,function(){
					layer.close(tips);
				});
			}
		});
	}
	

	// 业务节点操作方法 end 
	
	</script>
</head>

<body>
	<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 10px;">
		<div id="orgPageRecover" style="width:95%;margin:25px 0 15px 20px;">
		<form id="processInfoForm" method="post">
		    <input id="processId"  name="processId" type="hidden" class="dfinput_fb" value="${processId}" />
			<div style="margin-bottom:15px;">
				<table>
					<tr>
						<td>
							公司编码:&nbsp;
						</td>
						<td>
							<input id="com_id_input1"  name="com_id_input1" type="text" class="dfinput_fb" value="<%=request.getAttribute("seqCode")==null?"":request.getAttribute("seqCode")%>" style="width:140px;height:24px;" readonly="readonly" maxlength="11" onkeyup="" placeholder=""/>
						</td>
						<input id="com_id_input"  name="com_id_input" type="hidden" class="dfinput_fb" value=<%=request.getAttribute("orgId")%> style="width:140px;height:24px;" readonly="readonly" maxlength="11" onkeyup="" placeholder=""/>
						
						<td>
							公司名称:&nbsp;
						</td>
						<td>
							<input id="com_name_input"  name="com_name_input" type="text" class="dfinput_fb" value="<%=request.getAttribute("orgName")==null?"":request.getAttribute("orgName")%>" style="width:140px;height:24px;" readonly="readonly" maxlength="11" onkeyup="" placeholder=""/>
						</td>
					</tr>
					
					<tr>
						<td>
							业务类型:&nbsp;
						</td>
						<td>
							<select id="ser_type_input" name="ser_type_input" style="border:1px solid #ccc;width:140px;height:26px;" disabled="disabled">
										<option value="200101">代理记账</option>
										<option value="200100">工商注册</option>
										<option value="200102">法律咨询</option>
										<option value="200103">人事代理</option>
										<option value="200104">商标专利</option>
										<option value="200105">其他</option>
							</select>
						</td>
						
						
						<td>
							业务名称:&nbsp;
						</td>
						<td>
							<input id="ser_name_input"  name="ser_name_input" type="text" class="dfinput_fb" value="${processName}" style="width:140px;height:24px;"  maxlength="11" onkeyup="" placeholder=""/>
						</td>
					</tr>
		
				</table>
				
				
			</div>
			<div>
				    <input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="saveProcessInfo();" value="业务保存" style="height:30px;margin-left:100px;" />
					<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="selModel();" value="选择模板" style="height:30px;margin-left:100px;" />
					<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="addNode();" value="添加节点" style="height:30px;margin-left:100px;" />
					<input class="bcssbtn" type="button" style="padding: 2px 6px 2px 6px" onclick="saveNode1();" value="清除协同关系" style="height:30px;margin-left:100px;" />
			</div>
			</form>
		</div>
	</div>
	
	<div class="nodeB"><h4><img src="<%=path%>/style/image/ico_table_01.png"/>节点列表：</h4>
   		<table id="nodeTable" class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all">
  				<tr id="beginTr">
   				<td align="center" width="30%">节点</td>
				<td align="center" width="40%">节点名称</td>
				<td align="center" width="30%">操作</td>
			</tr>
			<c:forEach items="${nodes}" var="upNode" varStatus="status">
				<tr>
						
						<td align="left">${status.index+1}
							<input id="processId" mes="processId" type="hidden" value="${upNode.processId }"/>
							<input id="id" mes="id" type="hidden" value="${upNode.id}"/>
						</td>
						<td align="left">
							<input class="2" mes="nodeName" type="text" value="${upNode.nodeName}" readonly="readonly"/>
						</td>
						<td align="center">
							<input type="button" value="上移" onclick="up(this);" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
							<input type="button" value="下移" onclick="down(this);" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
							<input type="button" value="删除" onclick="delNodeTr(this,${upNode.id});" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
						<c:if test="${upNode.orhelp!=1}">
							<input type="button" value="协同" onclick="orhelp(${upNode.processId},${upNode.id});" style="height:23px;font-size:12px;text-align: center;padding: 0px 0px 0px 0px" class="bcssbtn"/>
						</c:if>
						</td>
					</tr>
			</c:forEach>
   		</table>
   	</div>

	<div id="addProcessDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td align="left" style="border:0;"><h4>节点名称:</h4></td>
			<td align="left" style="border:0;">
				<input maxlength="12"  id="addProcessName" name="nodeName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
			</td>
			
			<td align="left" style="border:0;">
				<input class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="saveProcess();" value="提交" />
			</td>
			
		</tr>
	   
	</table>
</div>
</body>
</html>
