/*;(function($,window,document,undefined){
	authView();
	$.extend({
		showFunction:function(jsonList){
			for(var o in jsonList){
				var obj = $("[id='" + jsonList[o].btnId + "']");
				if(obj.length > 0){
					var tagName = $(obj).prop("tagName");
					$(tagName + "[id='" + jsonList[o].btnId + "']").show();
				}
			}
		}
	});
})(jQuery,window,document);
*/
function dictionaryTranslation(key){
	var dict = 
	{
		'/Oemp/supervisory/forward/gotoCustomEdit' : '/Oemp/supervisory/forward/gotoCustomSetting',
		'/Oemp/supervisory/forward/gotoMoreNews' : '/Oemp/supervisory/forward/gotoHome',
		'/Oemp/supervisory/forward/gotoCustomCreate' : '/Oemp/supervisory/forward/gotoCustomSetting',
		'/Oemp/supervisory/forward/gotoPayAuditDetail' : '/Oemp/supervisory/forward/gotoPayAudit'
	};
	if(dict[key]){
		return dict[key];
	}else{
		return key;
	}
}

function authView(){
	var memberId = $.getCookie("memberId");
	var funUrl = $.getCookie("ACC_ROLER_URL") + "/auth/sysauth/functionBymUrlanduidCustomer/get.jspx";
	var sysType = "cwjk";
	var pathName = dictionaryTranslation(window.document.location.pathname);
	var menuUrl = pathName.substring(pathName.substr(1).indexOf('/')+1,pathName.length); 
	$.ajax({
		type : 'POST',
		url : funUrl,
		dataType : "jsonp",  
     	jsonp : "callBack",
        data :{menuUrl:menuUrl, sysType:sysType},
        success: function (resp) {
        	if(resp.returnCode != 0){
        		$.showMsg("权限数据错误:" + resp.returnMSG, 2);
        	}
        	var allFuns=resp.results;
        	if($.getCookie("isAdmin") == "true" || $.getCookie("isAdmin") == true){
        		$.showFunction(allFuns);
        	}else{
	          	$.ajax({
	            	type : 'POST',
	            	url : funUrl,
	            	dataType : "jsonp",
	            	jsonp : "callBack",
	            	data :{menuUrl:menuUrl,memberId:memberId,sysType:sysType},
	            	success : function (allowFuns) {
	            		$.showFunction(allowFuns.results);
	                 }
	          	});
        	}
        },
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			authView();
			$.showMsg("亲!您的网络不给力哦~", 2);
		}
	});
}

function menuContro(){
	if($.getCookie("isAdmin") == "true" || $.getCookie("isAdmin") == true){
		$(" li[mes='menu']").show();
		$(" td[mes='menu']").show();
		return true;
	}
	var url = $.getCookie("ACC_ROLER_URL") + '/auth/sysauth/authmenusCustomer.jspx?memberid=' + $.getCookie("memberId") + '&sysType=cwjk';
	$.ajax({
    	type : 'GET',
    	url : url,
    	dataType : "jsonp",
    	jsonp : "callBack",
    	success : function (allowFuns) {
    		var result = allowFuns.results;
    		$.each(result,function(index,item){
    			$(" li[id='" + item.url+"'][mes='menu']").show();
    			$(" td[id='" + item.url+"'][mes='menu']").show();
    		})
         }
  	});
}