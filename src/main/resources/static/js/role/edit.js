var menuIds;
$(function() {
	getMenuTreeData();
	validateRule();
});
$.validator.setDefaults({
	submitHandler : function() {
		getAllSelectNodes();
		update();
	}
});
function loadMenuTree(menuTree) {
	$('#menuTree').jstree({
		"plugins" : [ "wholerow", "checkbox" ],
		'core' : {
			'data' : menuTree
		},
		"checkbox" : {
		}
	});
	$('#menuTree').jstree('open_all');
}
function getAllSelectNodes() {
	var ref = $('#menuTree').jstree(true); // 获得整个树
	menuIds = ref.get_selected(); // 获得所有选中节点的，返回值为数组
	/*$("#menuTree").find(".jstree-undetermined").each(function(i, element) {
		menuIds.push($(element).closest('.jstree-node').attr("id"));
	});*/
}
function getMenuTreeData() {
	var roleId = $('#id').val();
	$.ajax({
		type : "GET",
        url : "/menu/menuTree/" + roleId,
		success : function(obj) {
            if(obj.code == 0){
                loadMenuTree(obj.data.data);
            }else{
                layer.error("菜单树结构加载失败")
            }
		}
	});
}
function update() {
	$('#menuIds').val(menuIds);
	var role = $('#signupForm').serialize();
	$.ajax({
		cache : true,
		type : "POST",
		url : "/role/update",
		data : role, // 你的formid
		async : false,
		error : function(request) {
			alert("Connection error");
		},
		success : function(r) {
			if (r.code == 0) {
				parent.layer.msg(r.message);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);

			} else {
				parent.layer.msg(r.message);
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			roleName : {
				required : true
			}
		},
		messages : {
			roleName : {
				required : icon + "请输入角色名"
			}
		}
	});
}