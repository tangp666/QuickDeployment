var serverIds = "";
$().ready(function() {
    //获取服务器列表树
	getTreeData();

	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
    if(serverIds == ''){
        layer.msg("请选中服务器")
        return;
    }
	$("#serverIds").val(serverIds);
	$.ajax({
		cache : true,
		type : "POST",
		url : "/tProjectServer/save",
		data : $('#signupForm').serialize(),// 你的formid
		async : false,
		error : function(request) {
			parent.layer.alert("网络错误，请稍后再试");
		},
		success : function(data) {
			if (data.code == 1) {
				parent.layer.msg(data.message);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				parent.layer.alert(data.message)
			}

		}
	});
}
function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({

    })
}

function getTreeData() {
    $.ajax({
        type: "GET",
        url: "/server/serverTree",
        data: {projectId : $("#projectId").val()},
        success: function (tree) {
            loadTree(tree);
        }
    });

    function loadTree(tree) {
        serverTree = $('#serverTree').jstree({
            'core': {
                'data': tree
            },
            "plugins": ["search","checkbox"]
        });
        $('#serverTree').jstree().open_all();

        $('#serverTree').on("changed.jstree", function (e, data) {
            serverIds = "";
            if(data.selected){
                for(var i = 0; data.selected && i < data.selected.length; i++){
                    var id = data.selected[i];
                    if(id.startsWith("s")){
                        id = id.substring(1);
                        if(serverIds){
                            serverIds += "," + id;
                        }else{
                            serverIds = id;
                        }
                    }
                }
            }
        });
    }
}