<%@page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page import="com.wb.framework.commonUtil.PropertiesReader"%>
<%PropertiesReader reader = PropertiesReader.getInstance();%>
<%String url = reader.getValue("/com/wb/config/application", "ACC_BILL_URL");%>
<%String path = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">  
<head>  
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />  
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8"/>
<link rel="shortcut icon" href="<%=path%>/style/image/supervisory/favicon.ico" />
<link rel="Bookmark" href="<%=path%>/style/image/supervisory/favicon.ico" />
<title>客户信息编辑</title>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/layer/layer.js"></script>
<script type="text/javascript" src="<%=path%>/script/ckUI.js"></script>
<script type="text/javascript" src="<%=path%>/script/powerAuth.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/i18n/grid.locale-cn.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/jqgrid/js/jquery.jqGrid.min.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/my97DatePicker/wDatePicker.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/select2.js"></script>
<script type="text/javascript" src="<%=path%>/plugins/select2/i18n/zh-CN.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/plugins/select2/css/select2.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style.css"/>
<link rel="stylesheet" type="text/css" href="<%=path%>/style/css/style_mnt.css"/>
<script type="text/javascript">
var countId = -1;//税务tr计数器
var fileCountId = -1;//文件tr计数器
var nameArray = [];//文件栈数组
var isAdmin = ${isAdmin};

function gotoTab(tab){
	$("#menuDiv").find("li").removeClass("selected");
	$("#" + tab + "Li").addClass("selected");
	$("#detailDiv").find("table").hide();
	$("#taxTable").show();
	$("#Tax_reminder").show();
	$("#File_List").show();
	$("#" + tab).show();
}

$(function(){//init函数,初始化页面
	if(isAdmin){
		$("#empDiv").show();
	}else{
		$("#empDiv").hide();
	}
	$("#orgNum").mustNumber();
	$("#workTel").mustNumber();
	$("#tel").mustNumber();
	$("#mobile").mustNumber();
	$("#days").mustNumber();
	$("#emp").select2({
		language: "zh-CN"
	});
	$("#emp option[value='${cusInfo.creator}']").attr("selected",true);//初始化页面下拉列表选中
	$("#cusAdmin option[value='${cusInfo.cusAdmin}']").attr("selected",true);//初始化页面下拉列表选中
	$("#taxType option[value='${cusInfo.taxType}']").attr("selected",true);//初始化页面下拉列表选中
	//根据后台获取的税务集合,生成列表
	var taxRow = "<c:forEach items='${taxInfoList}' var='taxInfo'>"+countIdPlus()+
					"<tr>"+
						"<td>"+$("#taxKind option[value='${taxInfo.taxKind}']").text()+
							"<input type='hidden' name='taxList["+countId+"].taxKind' value='${taxInfo.taxKind}'>"+
							"<input type='hidden' name='taxList["+countId+"].taxName' value='${taxInfo.taxName}'>"+
						"</td>"+
						"<td>"+$("#repeortType option[value='${taxInfo.repeortType}']").text()+
							"<input type='hidden' name='taxList["+countId+"].repeortType' value='${taxInfo.repeortType}'>"+
						"</td>"+
						"<td>满期后${taxInfo.days}天"+
							"<input type='hidden' name='taxList["+countId+"].days' value='${taxInfo.days}'>"+
						"</td>"+
						"<td>"+$("#taxPeople option[value='${taxInfo.taxPeople}']").text()+
							"<input type='hidden' name='taxList["+countId+"].taxPeople' value='${taxInfo.taxPeople}'>"+
						"</td>"+
						"<td>"+
							"<input type='button' value='删除' name='deleteTr_"+countId+"' onclick=\"deleteTaxTr(this);\" class='bcssbtn' style='padding: 0px 0px 0px 0px'/>"+
						"</td>"+
					"</tr>"+
				"</c:forEach>";
	$("#Tax_reminder tr:last").after(taxRow);
	//根据后台获取的文件集合,生成列表
	var fileRow = "<c:forEach items='${attachmentInfoList}' var='fileInfo'>"+
						"<tr>"+
							"<td align='left'>${fileInfo.fileName}</td>"+
							"<td align='left'>${fileInfo.comments}</td>"+
							"<td align='left'><font color='green'>已上传</font></td>"+
							"<td align='left'>"+
								"<input type='button' value='删除' onclick='deleteFileById(this,${fileInfo.id});' class='bcssbtn' style='padding: 0px 0px 0px 0px'/>"+
								"<a href='<%=path%>/supervisory/asyn/download?fileId=${fileInfo.id}' class='bcssbtn' style='padding: 0px 0px 0px 0px'>下载</a>"+
							"</td>"+
						"</tr>"+
					"</c:forEach>";
	$("#File_List tr:last").after(fileRow);
});

function deleteFileById(O,fileId){//删除文件（落地）并局部刷新列表
	layer.confirm('确认删除?', {icon: 3, title:'提示'}, function(){
		var tips = layer.msg("正在删除...",{icon : 16,time : 0, shade: [0.1]});
		$.post('<%=path%>/supervisory/asyn/deleteFileById',{'fileId' : fileId},function(result){
			layer.close(tips);
			if(result.code){
				parent.$.showMsg("删除成功!", 1);
				$(O).parent().parent().remove();
			}else{
				parent.$.showMsg("删除失败,系统出错!", 2);
			}
		});
	});
}

function countIdPlus(){//当点击税务的添加按钮时,使税务计数器+1
	countId = countId + 1;
}

function saveCusInfo(){//点保存按钮,更新客户信息
	//var hiddenValue=chose();
	
	var orgNum = $("#orgNum").val();
	var orgName = $("#orgName").val();
	var days = $("#days").val();
	var mobile = $("#mobile").val();
	var mobile1 = $("#mobile1").val();
	if(mobile!=mobile1){
		if(mobile1.indexOf("***")==-1){
			$("#mobile").val(mobile1);
			mobile=$("#mobile").val();
		}else{
			//把****替换成原来数字
			
		}
	}
	if(isAdmin&&$("#emp").val()==0){
		parent.$.showMsg("请选择客户主管!", 2);
		return;
	}
	if(orgNum==null||orgNum==""){
		$("#orgNum").focus();
		parent.$.showMsg("请填写客户编号!", 2, null, 3000);
		return;
	}
	if(orgName==null||orgName==""){
		$("#orgName").focus();
		parent.$.showMsg("请填写公司名称!", 2, null, 3000);
		return;	
	}
	if(!isNaN(orgName)){
		$("#orgNum").focus();
		parent.$.showMsg("公司名称不可以为纯数字!", 2, null, 3000);
		return;	
	}
	if(mobile==null||mobile==""){
		$("#mobile").focus();
		parent.$.showMsg("请填写手机号码!", 2, null, 4000);
		return;	
	}
	if ($.isMobile(mobile) == false) {
        parent.$.showMsg("手机号码格式有误!", 2, null, 4000);
		return;	
	}
	if(days==null||days==""){
		$("#days").val("0");
		return;
	}
	if(!isEmail()){
		return;
	}
	/* //alert(mobile);
	if(mobile!=mobile1 && mobile1.indexOf("***")==-1){
		$("#mobile").val(mobile1);
	} */
	
	if(fileCountId!=-1){
		for(var i=0;i<nameArray.length;i++){
			var f = $("#file_"+nameArray[i]).val();
			var m = $("#comments_"+nameArray[i]).val();
			if(f==null||f==""||m==null||m==""){
				parent.$.showMsg("请检查所上传的附件信息是否有遗漏（文件说明必填）!", 2);
				return;
			}
		}
	}
	var tips;
	var options = {
		type : 'post',
		dataType : 'json',
		url : '<%=path%>/supervisory/asyn/editCustom',
		beforeSubmit : function() {
			tips = layer.msg("正在更新...",{icon : 16,time : 0, shade: [0.1]});
		},
		success : function(result) {
			layer.close(tips);
			if(result.code==1){
				parent.$.showMsg("更新成功!", 1, function(){
					closeFrame();
				});
			}else if(result.code==2){
				parent.$.showMsg("更新失败,客户编号重复!", 2);
			}else if(result.code==3){
				parent.$.showMsg("更新失败,公司名称重复!", 2);
			}else if(result.code==4){
				parent.$.showMsg("更新失败,客户数据生成失败!", 2);
			}else if(result.code==5){
				parent.$.showMsg("更新失败,税务提醒生成失败!", 2);
			}else if(result.code==6){
				parent.$.showMsg("更新失败,附件上传失败!", 2);
			}else if(result.code==7){
				parent.$.showMsg("保存失败,公司数据生成失败!", 2);
			}else{
				parent.$.showMsg("更新失败,系统出错!", 2);
			}
		},
		error : function(result) {
			parent.$.showMsg("亲!您的网络不给力哦~", 2);
		}
	};
	$('#cusInfoForm').ajaxSubmit(options);
}

function closeFrame(){//点关闭按钮,关闭当前页面,并刷新父级页面
	window.parent.thisGrid.trigger("reloadGrid");
	window.parent.layer.close(window.parent.editCus);
}

function addTaxTable(){//点税务的添加按钮,添加一条税务信息
	countId ++;
	var taxKindText = $("#taxKind").find("option:selected").text();
	var taxKind = $('#taxKind').val();
	var repeortTypeText = $("#repeortType").find("option:selected").text();
	var repeortType = $('#repeortType').val();
	var days = $('#days').val();
	var taxPeopleText = $("#taxPeople").find("option:selected").text();
	var taxPeople = $('#taxPeople').val();
	var taxRow = "<tr>"+
					"<td>"+taxKindText+
						"<input type='hidden' name='taxList["+countId+"].taxKind' value='"+taxKind+"'>"+
						"<input type='hidden' name='taxList["+countId+"].taxName' value='"+taxKindText+"'>"+
					"</td>"+
					"<td>"+repeortTypeText+
						"<input type='hidden' name='taxList["+countId+"].repeortType' value='"+repeortType+"'>"+
					"</td>"+
					"<td>满期后"+days+"天<input type='hidden' name='taxList["+countId+"].days' value='"+days+"'>"+
					"</td>"+
					"<td>"+taxPeopleText+
						"<input type='hidden' name='taxList["+countId+"].taxPeople' value='"+taxPeople+"'>"+
					"</td>"+
					"<td>"+
						"<input type='button' value='删除' class='bcssbtn' style='padding: 0px 0px 0px 0px' onclick=\"deleteTaxTr(this);\" />"+
					"</td>"+
				"</tr>";
	$("#Tax_reminder tr:last").after(taxRow);
}

function deleteTaxTr(k){//点税务后删除按钮,删除一行税务信息
	$(k).parent().parent().remove();
}

function addFileTable(){//点文件添加按钮,添加一条文件信息
	fileCountId ++;
	nameArray.push(fileCountId);
	var fileRow = "<tr>"+
						"<td align='left'>"+
							"<input type='file' id='file_"+fileCountId+"' name='fileListInfo["+fileCountId+"].file' class='FileCss' onchange='fileInfoCheck($(this),this,"+fileCountId+");'/>"+
						"</td>"+
						"<td align='left'>"+
							"<input type='text' id='comments_"+fileCountId+"' name='fileListInfo["+fileCountId+"].comments' class='FileCss' />"+
						"</td>"+
						"<td align='left'><font color='red'>未上传</font></td>"+
						"<td align='left'>"+
							"<input type='button' value='删除' class='bcssbtn' style='padding: 0px 0px 0px 0px' onclick=\"deleteFileTr(this,'" + fileCountId + "');\"/>"+
						"</td>"+
					"</tr>";
	$("#File_List tr:last").after(fileRow);
}

function deleteFileTr(k,fileCountId){//点文件删除按钮,删除一条文件信息
	for(var i=0;i<nameArray.length;i++){
		if(nameArray[i] == fileCountId){
			nameArray.splice(i,1);
		}
	}
	$(k).parent().parent().remove();
}

function fileInfoCheck(_this,ele,fileCountId){//验证上传文件格式
	var filepath = $("input[name='fileListInfo["+fileCountId+"].file']").val();
	var extStart = filepath.lastIndexOf(".");
	var ext = filepath.substring(extStart, filepath.length).toUpperCase();
	if (ext != ".TXT" && ext != ".XLS" && ext != ".DOC" && ext != ".JPG" && ext != ".GIF"&& ext != ".PDF") {
		parent.$.showMsg("文件限于txt、xls、doc、jpg、gif、pdf格式", 2);
		_this.prop("value","");
		return;
	}
	var fileSize = (ele.files[0].size / 1024).toFixed(2);
	if(fileSize>500){
		parent.$.showMsg("文件大小限于500KB以内", 2);
		_this.prop("value","");
		return;
	}
}

function isEmail(){//正则校验email合法性
	var email = $("#email").val();
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	if(!reg.test(email) && isEmpty(email) != ""){
		parent.$.showMsg("e-mail格式不合法,请校验!", 2);
		return false;
	}
	return true;
}

var createBookLayer;
function gotoCreateBook(){
	createBookLayer= layer.open({
		title : '创建账套',
		shade : [ 0.1, '#000' ],
		fix : true,
		type : 1,
		shadeClose: true,
		area : ['300px', '100px'],
		content : $("#bookDiv"),
		end : function(){
			$("#createBookName").val("");
		}
	});
}

function createBook(orgid,tel,createDate){
		$("#createBookButton").attr("disabled",true);
		var name = $("#createBookName").val();
		if($.ckTrim(name)==""){
			parent.$.showMsg("请输入账套名称!", 2);
			return;
		}
		var ACC_BILL_URL = '<%=url%>';
		var url = ACC_BILL_URL+"/financeCreateBook.jspx";
		var param = "{orgid:" + orgid + ",tel:" + tel + ",name:" + name + ",createDate:" + createDate + "}";
		$.ajax({
			type : "get",
			timeout : 5000,
	        dataType : "json",
	       	data : {"param":param},
			url  : url
		});
		parent.$.showMsg("创建成功!", 1, function(){
			$("#createBookButton").removeAttr("disabled");
			layer.close(createBookLayer);
		});
	}

 $(function(){
    var chose=document.getElementById("chose1");
	var hiddenValue=$("#hiddenValue").val();
	var mobile1 = $("#mobile1").val();
	var mobile = $("#mobile").val();
	if(mobile==null||mobile==""){
		$("#value1").val(1);
	}
	var str=$("#mobile").val().substring(4,8);
	if(hiddenValue==1){
		chose.checked=true;
		$("#mobile1").val(mobile.replace(str,"****"));	
	}
	
});
 


// 点击复选框 隐藏电话	
var mobileModel;
function chose(){
	var chose=document.getElementById("chose1");
	var mobile = $("#mobile").val();// 提交后台的
	var mobile1 = $("#mobile1").val();//显示的
	var value1=$("#value1").val();
	var value2=$("#value2").val();
	var sts=$("#mobile1").val().substring(8,11);
	if(value1==1){
		 mobile=$("#mobile1").val();
		 if(mobile1.indexOf("****")>-1){
			 mobile1=mobile1.replace("****",value2);
			 mobile=mobile1;
		 }
		 if ($.isMobile(mobile) == false) {
	        parent.$.showMsg("手机号码格式有误!", 2, null, 4000);
	        chose.checked=false;
			return;	
		} 
	    if($("#mobile1").val().indexOf("****")>-1){
	    	$("#mobile1").val(mobile);
	    	hiddenValue = 0;
		 }else{
			var str1 = $("#mobile1").val().substring(4, 8);
			    $("#value2").val( str1);
				if (chose.checked == true) {
					$("#mobile1").val(mobile1.replace(str1, "****"));
					hiddenValue = 1;
				} else if (chose.checked == false) {
					$("#mobile1").val(mobile);
					hiddenValue = 0;

				}
			}
	    $("#mobile").val(mobile);
	    $("#hiddenValue").val(hiddenValue);
	    return;
		}

		var hiddenValue = $("#hiddenValue").val();
		var str = $("#mobile1").val().substring(4, 8);
		if (chose.checked == true) {
			$("#mobile").val(mobile1);
			var aa=$("#mobile").val();
			$("#mobile1").val(aa.replace(str, "****"));
			hiddenValue = 1;
		} else if (chose.checked == false) {
			$("#mobile1").val(mobile);
			hiddenValue = 0;

		}
		// return hiddenValue;
	    $("#hiddenValue").val(hiddenValue);

	}
</script>
</head>
<body>

<div>
	<div id="menuDiv" class="tab_menu" >
		<ul class="TabClass">
			<li id="cusInfoLi" class="selected">
				<span onclick="gotoTab('cusInfo')">基本资料</span>
			</li>
			<li id="contactLi" >
				<span onclick="gotoTab('contact')">联系方式</span>
			</li>
			<li id="taxInfoLi" >
				<span onclick="gotoTab('taxInfo')">税务资料</span>
			</li>
			<li id="attachmentLi">
				<span onclick="gotoTab('attachment')">附件</span>	
			</li>
		</ul>
	</div>
	<form id="cusInfoForm" enctype="multipart/form-data" method="POST">
	<div id="detailDiv" class="tab_box">
		<table id="cusInfo" width="100%" border="0">
			<tr>
				<td width="80">公司编号：</td>
				<td colspan="3">
				<input name="orgNum" type="text" id="orgNum" maxlength="9" autocomplete="off" class="txt450" value="${cusInfo.orgNum}"/> <span style="color: red">*</span>
				<input name="cusId" type="hidden" id="cusId" value="${cusInfo.tempId}"/>
				</td>
			</tr>
			<tr>
				<td>公司名称：</td>
				<td colspan="3">
				<input name="orgName" type="text" maxlength="20" id="orgName" autocomplete="off" class="txt450" value="${cusInfo.orgName}"/> <span style="color: red">*</span>
				</td>
			</tr>
			<tr>
				<td>公司简称：</td>
				<td>
				<input name="orgShortName" type="text" maxlength="20" id="orgShortName" class="txt170" autocomplete="off" value="${cusInfo.orgShortName}"/>
				</td>
				<td>行业性质：</td>
				<td>
				<input name="bussType" type="text" maxlength="20" id="bussType" class="txt170" autocomplete="off" value="${cusInfo.bussType}"/>
				</td>
			</tr>
			<tr>
				<td>法人代表：</td>
				<td>
				<input name="rawPeople" type="text" maxlength="20" id="rawPeople" class="txt170" autocomplete="off" value="${cusInfo.rawPeople}"/>
				</td>
				<td>手机号码：</td>
				<td>
				<input name="mobile1" type="text" id="mobile1" class="txt170" placeholder="填写准确的联系人电话" autocomplete="off" value="${cusInfo.mobile}"  maxlength="11" style="width: 80px;"/>
				<input name="mobile" type="hidden" id="mobile" value="${cusInfo.mobile}"/>
				<input type="checkbox"  id="chose1" onclick="chose()"/>(是否隐藏手机)
				<input type="hidden" value="${cusInfo.hiddenValue}" id="hiddenValue" name="hiddenValue"/>
				<input type="hidden" name="value1" id="value1"/>
				<input type="hidden" name="value2" id="value2"/>
				</td>
			</tr>
			<tr>
				<td>营业执照号：</td>
				<td>
				<input name="bussLicence" type="text" maxlength="20" id="bussLicence" class="txt170" autocomplete="off" value="${cusInfo.bussLicence}"/>
				</td>
				<td>组织机构代码：</td>
				<td>
				<input name="institutionNo" type="text" maxlength="20" id="institutionNo" class="txt170" autocomplete="off" value="${cusInfo.institutionNo}"/>
				</td>
			</tr>
			<tr>
				<td>成立日期：</td>
				<td>
				<input name="createDate" type="text" id="createDate" onfocus="WdatePicker()" class="Wdate" value="${cusInfo.createDate}"/>
				</td>
				<td>开始代账日期：</td>
				<td>
				<input name="startAcc" type="text" id="startAcc" onfocus="WdatePicker()" class="Wdate" value="${cusInfo.startAcc}"/>
				</td>
			</tr>
			<tr>
				<td>备注：</td>
				<td colspan="3">
				<textarea name="demo" id="demo"	width="420" class="txt450">${cusInfo.demo}</textarea>
				</td>
			</tr>
			<tr>
			<tr>
				<td colspan="2">
					<div id="empDiv"><span style="margin-right: 15.6px">客户主管：</span>
						<select name="emp" id="emp" style="width: 100px;">
							<option value="0">请选择员工</option>
								<c:forEach items='${empList}' var='emp'>
								<c:choose>
							        <c:when test="${emp.name==memberName}">
							        	<option value="${emp.id}" selected="selected">${emp.name}</option>
							        </c:when>
							        <c:otherwise>
							       		<option value="${emp.id}">${emp.name}</option>
							        </c:otherwise>
								</c:choose>
							</c:forEach>
						</select>
					</div>
				</td>
				<td colspan="2">
					<input type="button" value="创建账套" onclick="gotoCreateBook();" class="bcssbtn" style="padding: 0px 0px 0px 0px;">
				</td>
			</tr>
		</table>
		<table id="contact" class="hide">
			<tr>
				<td width="70">联系人：</td>
				<td width="180">
				<input name="contact" type="text" id="contact" class="txt180" autocomplete="off" maxlength="10" value="${cusInfo.contact}"/>
				</td>
				<td width="70">办公电话：</td>
				<td width="180">
				<input name="workTel" type="text" id="workTel" class="txt180" autocomplete="off" value="${cusInfo.workTel}" maxlength="12"/>
				</td>
			</tr>
			<tr>
				<td>座机号码：</td>
				<td>
				<input name="tel" type="text" id="tel" class="txt180"	autocomplete="off" value="${cusInfo.tel}" maxlength="12"/>
				</td>
				<td>身份证号：</td>
				<td>
				<input name="cardNo" type="text" maxlength="18" id="cardNo" class="txt180" autocomplete="off" value="${cusInfo.cardNo}"/>
				</td>
			</tr>
			<tr>
				<td>传真号码：</td>
				<td>
				<input name="fax" type="text" maxlength="20" id="fax" class="txt180" autocomplete="off" value="${cusInfo.fax}"/>
				</td>
				<td>QQ:</td>
				<td>
				<input name="qq" type="text" maxlength="20" id="qq" class="txt180" autocomplete="off" value="${cusInfo.qq}"/>
				</td>
			</tr>
			<tr>
				<td>E-mail:</td>
				<td colspan="3">
				<input name="email" type="text" maxlength="30" id="email" class="txt450" autocomplete="off" value="${cusInfo.email}" onblur="isEmail();"/>
				</td>
			</tr>
			<tr>
				<td>其他：</td>
				<td colspan="3">
				<input name="orther" type="text" maxlength="50" id="orther" class="txt450" autocomplete="off" value="${cusInfo.orther}"/>
				</td>
			</tr>
			<tr>
				<td valign="top" style="padding:10px 0 0 0;">详细地址：</td>
				<td colspan="3">
				<textarea name="addr" id="addr" class="txt450">${cusInfo.addr}</textarea>
				</td>
			</tr>
		</table>
		<table id="taxInfo" class="hide">
			<tr>
				<td>纳税人类别：</td>
				<td>
				<select name="taxType" id="taxType" class="Taxes" style="width:163px">
					<option value="1">一般纳税人</option>
					<option value="2">小规模纳税人</option>
				</select>
				</td>
				<td>税率：</td>
				<td>
				<input name="taxRates" type="text" id="taxRates" autocomplete="off" class="txt180" style="width: 125px" maxlength="3" value="${cusInfo.taxRates}" onkeypress="return IsNum(event,$(this));"/>%
				</td>
			</tr>
			<tr>
				<td>国税税务登记号：</td>
				<td>
				<input name="nationalTaxNo" maxlength="20" type="text" id="nationalTaxNo" autocomplete="off" value="${cusInfo.nationalTaxNo}" class="txt160" />
				</td>
				<td>电脑编号：</td>
				<td>
				<input name="nationalTaxCpu" maxlength="20" type="text" id="nationalTaxCpu" autocomplete="off" value="${cusInfo.nationalTaxCpu}" class="txt140" />
				</td>
			</tr>
			<tr>
				<td>国税主管税务分局(科)：</td>
				<td>
				<input name="nationalTaxAddr" maxlength="20" type="text" id="nationalTaxAddr" autocomplete="off" value="${cusInfo.nationalTaxAddr}" class="txt160" />
				</td>
				<td>国税备注：</td>
				<td>
				<input name="nationalTaxDemo" maxlength="50" type="text" id="nationalTaxDemo" autocomplete="off" value="${cusInfo.nationalTaxDemo}" class="txt140" />
				</td>
			</tr>
			<tr>
				<td>地税税务登记号：</td>
				<td>
				<input name="landTaxNo" type="text" maxlength="20" id="landTaxNo" class="txt160" autocomplete="off" value="${cusInfo.landTaxNo}"/>
				</td>
				<td>电脑编号：</td>
				<td>
				<input name="landTaxNoCpu" type="text" maxlength="20" id="landTaxNoCpu" class="txt140" autocomplete="off" value="${cusInfo.landTaxNoCpu}"/>
				</td>
			</tr>
			<tr>
				<td width="136" nowrap="nowrap">地税主管税务分局(科)：</td>
				<td>
				<input name="landTaxNoAddr" type="text" maxlength="20" id="landTaxNoAddr" class="txt160" autocomplete="off" value="${cusInfo.landTaxNoAddr}"/>
				</td>
				<td width="70" nowrap="nowrap">地税备注：</td>
				<td>
				<input name="landTaxNoDemo" maxlength="50" type="text" id="landTaxNoDemo" class="txt140" autocomplete="off" value="${cusInfo.landTaxNoDemo}"/>
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<p>
					<b>税务提醒：</b>
					</p>
					<table id="taxTable" width="98%" class="TaxReminder">
						<tr>
							<td width="200">税种</td>
							<td width="100">申报周期</td>
							<td width="150">到期日</td>
							<td width="140">报税人</td>
							<td width="50"></td>
						</tr>
						<tr>
							<td class="Td_Taxes">
							<select name="taxKind" id="taxKind" class="Taxes">
								<option value="1">增值税（一般纳税人）</option>
								<option value="2">增值税（小规模）</option>
								<option value="3">消费税</option>
								<option value="4">营业税</option>
								<option value="5">企业所得税</option>
								<option value="6">城市维护建设税</option>
								<option value="7">教育费附加税</option>
								<option value="8">个人所得税</option>
								<option value="9">房产税</option>
								<option value="10">资源税</option>
								<option value="11">社保费</option>
								<option value="12">印花税</option>
							</select>
							</td>
							<td class="Td_Period">
							<select name="repeortType" id="repeortType" class="Period">
								<option value="1">月报</option>
								<option value="2">季报</option>
								<option value="3">半年报</option>
								<option value="4">年报</option>
							</select>
							</td>
							<td class="Td_Deadline">
							期满后<input name="days" maxlength="3" type="text" id="days" value="0" class="Deadline"/>天
							</td>
							<td class="Td_Taxpeople">
							<select name="taxPeople" id="taxPeople" class="Period" style="width: 100px;">
								<c:forEach items='${empList}' var='emp'>
									<option value="${emp.id}" selected="selected">${emp.name}</option>
								</c:forEach>
							</select>
							</td>
							<td>
								<input type="button" id="editCus_addTax" value="添加" class="bcssbtn" style="padding: 0px 0px 0px 0px;display: none" id="reset" onclick="addTaxTable();"/>
							</td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="5">
					<h3 class="H3_class">已添加税务提醒：</h3>
					<div class="wrapTaxTable">
						<table id="Tax_reminder" width="98%">
							<tr>
								<td>税种</td>
								<td>申报周期</td>
								<td>到期日</td>
								<td>报税人</td>
							</tr>
							
						</table>
					</div>
				</td>
			</tr>
		</table>
		<table id="attachment" class="hide" width="100%" border="2">
			<tr>
				<td colspan="6">
					<span>支持txt、xls、doc、jpg、gif、pdf等文件格式,大小不超过500K</span>
					<input type="button" id="editCus_addAtta" value="添加" class="bcssbtn" style="padding: 0px 0px 0px 0px;display: none;" id="reset" onclick="addFileTable();"/>
				</td>
			</tr>
			<tr>
				<td colspan="7">
					<h3 class="H3_class">附件列表：</h3>
					<div class="wrapTaxTable" style="height: 280px;">
						<table id="File_List" width="100%">
							<tr>
								<td width="30%" align="left">附件</td>
								<td width="30%" align="left">文件说明</td>
								<td width="20%" align="left">状态</td>
								<td width="20%" align="left">操作</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</div>
	</form>
	<div align="right" style="padding-right: 5px;margin-top: 30px">
		<p>
			<input name="txtTaxInfo" type="hidden" id="txtTaxInfo" /> 
			<input name="txtRecorder" type="hidden" id="txtRecorder" /> 
			<input name="saveCus"  type="submit" id="saveCus"  value="保存" class="bbtn" style="display: inline;" onclick="saveCusInfo()" /> 
			<input name="closeTab" type="button" id="closeTab" value="关闭"  class="abtn" style="display: inline;margin-right: 8px" onclick="closeFrame()"/>
		</p>
	</div>
</div>

<div id="bookDiv" style="display: none;">
	<table class="table_query" border="0" cellpadding="0" cellspacing="0" rules="all" style="margin-top: 15px">
		<tr>
			<td align="left" style="border:0;"><h4>账套名称:</h4></td>
			<td align="left" style="border:0;">
				<input maxlength="12"  id="createBookName" name="createBookName" type="text" class="dfinput_fb" style="width:140px;height:24px;"/>
			</td>
			<td align="left" style="border:0;">
				<input id="createBookButton" class="acssbtn" type="button" style="padding: 2px 6px 2px 6px;" onclick="createBook('${bookInfo[0].orgId}','${bookInfo[0].tel}','${bookInfo[0].createDate}');" value="创建" />
			</td>
		</tr>
	</table>
</div>
</body>
</html>