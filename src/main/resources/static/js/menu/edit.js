$(function() {
	validateRule();
	//打开图标列表
    $("#ico-btn").click(function(){
        layer.open({
            type: 2,
			title:'图标列表',
            content: '/fontIcoList',
            area: ['480px', '90%'],
            success: function(layero, index){

            }
        });
    });
});
$.validator.setDefaults({
	submitHandler : function() {
		submit();
	}
});
function submit() {
	$.ajax({
		type : "POST",
		url :  "/menu/update",
		data : $('#signupForm').serialize(),
		error : function() {
			layer.alert("系统异常")
		},
		success : function(data) {
			if (data.code == 1) {
				parent.layer.msg(data.message);
				parent.reLoad();
				var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
				parent.layer.close(index);
			} else {
				layer.alert(data.message)
			}
		}
	});
}

function validateRule() {
	var icon = "<i class='fa fa-times-circle'></i> ";
	$("#signupForm").validate({
		rules : {
			name : {
				required : true
			},
			type : {
				required : true
			}
		},
		messages : {
			name : {
				required : icon + "请输入菜单名"
			},
			type : {
				required : icon + "请选择菜单类型"
			}
		}
	})
}