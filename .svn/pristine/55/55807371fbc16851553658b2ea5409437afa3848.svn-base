;(function($){
	$.fn.extend({
		/**
		 * 下拉框插件
		 * 
		 * @param option 配置参数
		 * @returns 下拉框
		 */
		ckCombox : function(option) {
			var ck = $(this);
			ck.empty();
			ck.off();
			var tempOption = {
				url : "testUrl",
				data : null,
				autoSel : false,
				defaultSel : true,
				requestType : "GET",
				dataType : "json",
				width : "100px",
				height : "30px",
				formatter : {
					root : "ckUIRoot",
					id : "id",
					value : "value",
					select : "select"
				},
				onSuccess : function(resultJson) {
					// do somthing
				},
				onSelect : function(obj) {
					// do somthing
				}
			}
			var ckOption = $.extend(true, {}, tempOption, option);
			var root = ckOption.formatter.root;
			var ckId = ckOption.formatter.id;
			var ckValue = ckOption.formatter.value;
			var ckSelect = ckOption.formatter.select;
			ck.css("width", ckOption.width);
			ck.css("height", ckOption.height);
			var resultArray = [];
			$.ajax({
				type : ckOption.requestType,
				dataType : ckOption.dataType,
				data : ckOption.data,
				url : ckOption.url,
				success : function(result) {
					if(root != "ckUIRoot"){
						result = result[root];
					}
					if(ckOption.defaultSel == true || ckOption.defaultSel == 'true'){
						ck.append('<option value="-1" selected="selected">全部</option>');
					}
					for ( var i in result) {
						var tempId = result[i][ckId];
						var tempValue = result[i][ckValue];
						var tempJson = "{" + ckId + " : \"" + tempId + "\" ," + ckValue + " : \"" + tempValue + "\"}";
						resultArray.push(tempJson);
						if (ckOption.autoSel == true && result[i][ckSelect] == 'true') {
							ck.append("<option selected=\"selected\" value=" + tempId + ">" + tempValue + "</option>");
							continue;
						}
						ck.append("<option value=" + tempId + ">" + tempValue + "</option>");
					}
					ck.change(function() {
						ckOption.onSelect(ck);
					});
					if (ckOption.onSuccess) {
						return ckOption.onSuccess(eval("([" + resultArray + "])"));
					}
				},
				error : function(XMLHttpRequest, errorMSG, Exception) {
					alert("与服务器通讯出现错误!");
				}
			});
			return ck;
		},
		/**
		 * 合并jqgrid单元格插件(必须和  cellattr:$.insertAttr  配合使用)
		 * 
		 * @param option
		 * 
		 * 说明 : 需要在jqgrid上添加两个属性: 
		 * 			judgeId : "id",根据该属性来判断上下行是否属于同类型的数据
		 * 			mergeCellFild : ["xx","xx"] 如果是同类型的数据,那么合并哪个ID的单元格,数组
		 */
		jqCellMerge : function(option){
			var ck = $(this);
			var tempOption = {
				judgeId : "id",
				mergeCellFild : ["id","name"]
			};
			if(!option){
				tempOption.judgeId = ck.getGridParam("judgeId");
				tempOption.mergeCellFild = ck.getGridParam("mergeCellFild");
			}else{
				tempOption.judgeId = option.judgeId;
				tempOption.mergeCellFild = option.mergeCellFild;
			}
			var ids = ck.getDataIDs();
			var spanNum = 1;
			for(var i = 0; i < ids.length; i = i + 1){
				var id = ids[i];
				var thisRow = ck.getRowData(id);
				for(var j = i + 1; j < ids.length; j = j + 1){
					var nextId = ids[j];
					var nextRow = ck.getRowData(nextId);
					if(thisRow[tempOption.judgeId] == nextRow[tempOption.judgeId]){
						spanNum = spanNum + 1;
						for(var fildId = 0;fildId < tempOption.mergeCellFild.length;fildId = fildId + 1){
							var fildName = tempOption.mergeCellFild[fildId];
							$("#row_" + id + "_" + thisRow[fildName].trim()).attr("rowspan",spanNum);
						}
						for(var fildId = 0;fildId < tempOption.mergeCellFild.length;fildId = fildId + 1){
							var fildName = tempOption.mergeCellFild[fildId];
							$("#row_" + nextId + "_" + nextRow[fildName].trim()).css("display","none");
						}
						continue;
					}else{
						i = j - 1;
						spanNum = 1;
						break;
					}
				}
			}
		},
		/**
		 * 必须输入0-9之间的数字
		 */
		mustNumber: function(){
			$(this).keyup(function(){
				$(this).val($(this).val().replace(/\D/g,''));
			});
		},
		/**
		 * 必须输入0-9之间的数字
		 */
		mustMoney: function(e){
			$(this).keyup(function(e){
				var str = $(this).val();
				if(e.keyCode == 190){
					if(str.indexOf(".")!=str.lastIndexOf(".")){
						var firstStr = str.substring(0,str.lastIndexOf('.'));
						firstStr = firstStr.substring(0,(firstStr.indexOf(".")+3));
						$(this).val(firstStr);
						return;
					}
				}
				if(str.indexOf(".")!=-1){
					str = str.substring(0,(str.indexOf(".")+3));
				}
				$(this).val(str.replace(/[^0-9.]*/g,''));
			});
		},
		/**
		 * 邮箱校验
		 * @returns {Boolean}
		 */
		isEmail: function(){
			var mailContext = $(this).val();
			var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
			if(!reg.test(mailContext) && $.trim(mailContext) != ""){
				return false;
			}
			return true;
		},
		numLimit: function(min, max){
			$(this).keyup(function(){
				$(this).val($(this).val().replace(/\D/g,''));
				var thisVal = $(this).val();
				if(Number(thisVal) > Number(max)){
					$(this).val(max);
				}
				if(Number(thisVal) < Number(min)){
					$(this).val(min);
				}
			});
		}
	});
	$.extend({
		/**
		 * 去前后空格插件
		 * 
		 * @param param
		 * @returns 去掉前后空格后的字符串
		 */
		ckTrim : function(param){
			if(param == "" || param == null || param == "null"){
				return "";
			}
			return param.replace(/(^\s*)|(\s*$)/g,'').replace("&#160;","");
		},
		/**
		 * jqgrid单元格添加属性插件,参数默认jqgrid传入
		 * 
		 * @param rowId
		 * @param val
		 * @param rawObject
		 * @param cm
		 * @param rdata
		 * @returns {String} 插入的字符串
		 */
		insertAttr : function(rowId,val,rawObject,cm,rdata){
			return "id = \"row_" + rowId + "_" + $.ckTrim(val) + "\"";
		},
		/**
		 * layer插件扩展功能
		 */
		showMsg : function(tips, icon, func,time){
			if(time){
				layer.msg(tips, {title:"提示:", icon : icon, time : time, area: ['275px', '140px'], offset: 'rb', shift :2}, func);
				return;
			}
			layer.msg(tips, {title:"提示:", icon : icon, time : 2500, area: ['275px', '140px'], offset: 'rb', shift :2}, func);
		},
		isMobile :　function(tel){
			var reg = /^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\d{8}$/;
			if(reg.test(tel)){
				return true;
			}
			return false;
		},
		/**
		 * 存储Cookie
		 */
		setCookie : function(name, value){
			document.cookie = name + "=" + escape(value) + ";";
		},
		/**
		 * 获得Cookie
		 */
		getCookie : function(name){
			var arr,reg=new RegExp("(^| )"+name+"=([^;]*)(;|$)");
			if(arr=document.cookie.match(reg)){
				return unescape(arr[2]).replace("\"","").replace("\"","");
			}else{
				return null;
			}
		},
		/**
		 * 删除Cookie
		 */
		delCookie : function(name){
			var exp = new Date();
		    exp.setTime(exp.getTime() - 1);
		    var cval=getCookie(name);
		    if(cval!=null){
		    	document.cookie= name + "="+cval+";expires="+exp.toGMTString();
		    }
		},
		ckJson : function(obj){
			return JSON.stringify(obj);
		}
	});
})(jQuery);


/**
 * 输入框获取焦点触发事件
 * 
 * @param obj 当前对象
 * @returns 格式化好的金额
 */
function balanceGoBack(obj){
	var inputValue = obj.val();
	return obj.val(rmoney(inputValue));
}

/**
 * 格式化金额
 * 
 * @param s 金额
 * @param n 保留的小数点位数
 * @returns 格式化好的金额
 */
function fmoney(s, n){
	var rep= /^-?[1-9]\d*.?\d*$/g;
	if(isEmpty(s) == "" || rep.test(s) == false || s == 0) return "";
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	var thisLength = l.length
	for (i = 0; i < thisLength; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return (t.split("").reverse().join("") + "." + r).replace("-,", "-");
}

/**
 * 还原金额
 * 
 * @param s 金额
 * @returns 如果没有小数则去掉小数点后面的金额
 */
function rmoney(s){
	if(isEmpty(s) == "" || s == 0) return "";
	return parseFloat((s + "").replace(/[^\d\.-]/g, "")); 
}

/**
 * 是否是空的字段
 * 
 * @param s 字符串
 * @returns 为空则返回"";
 */
function isEmpty(s){
	if(s == undefined || s == 'undefined' || s == null || s == 'null' || s == '') {
		return ""
	}
	return s;
}

/**
 * 限制只能输入数字(现金流量表专用)
 * 
 * @param e event
 * @param obj 当前对象
 */
function IsNum(e, obj) {
	var k = window.event ? e.keyCode : e.which;
	if (((k >= 48) && (k <= 57)) || k == 8 || k == 0 || k == 45 || k == 46) {
		var tempVal = obj.val();
		if(tempVal.indexOf(".") != -1 && tempVal.substring(tempVal.indexOf(".")).length > 2)
		{
			obj.val(tempVal.substring(0,tempVal.indexOf(".") + 2));
		} else{
			obj.val(tempVal);
		}
		window.event.returnValue = true;
	} else {
		if (window.event) {
			window.event.returnValue = false;
		} else {
			e.preventDefault();
		}
	}
}