$().ready(function() {
	validateRule();
});

$.validator.setDefaults({
	submitHandler : function() {
		save();
	}
});

function save() {
	$.ajax({
		cache : true,
		type : "POST",
		url : "/project/save",
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
        rules : {
            projectName : {
                required : true,
                minlength : 2,
                remote : {
                    url : "/project/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        projectName : function() {
                            return $("#projectName").val();
                        }
                    }
                }
            }
        },
        messages : {
            projectName : {
                required : icon + "请输入项目名",
                remote : icon + "项目名已经存在"
            }
        }
    })
}