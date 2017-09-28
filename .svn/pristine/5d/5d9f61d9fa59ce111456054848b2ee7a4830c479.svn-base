function expendTree(url, id) {
	$.post(url, null, function(data) {
		var nodes = eval("(" + data + ")");
		var setting = {
			data : {
				simpleData : {
					enable : true,
					idKey : "id",
					pIdKey : "parentId",
					rootPId : null,
					open : true
				}
			}
		};
		var treeObj = $.fn.zTree.init($("#" + id), setting, nodes);
		var treeObj = $.fn.zTree.getZTreeObj(id);
		treeObj.expandAll(true);

	})
}