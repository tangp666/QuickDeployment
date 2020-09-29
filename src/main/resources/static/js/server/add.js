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
		url : "/server/save",
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
            serverName : {
                required : true,
                remote : {
                    url : "/server/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        serverName : function() {
                            return $("#serverName").val();
                        }
                    }
                }
            },
            serverAddress : {
                required : true,
                remote : {
                    url : "/server/exit", // 后台处理程序
                    type : "post", // 数据发送方式
                    dataType : "json", // 接受数据格式
                    data : { // 要传递的数据
                        serverAddress : function() {
                            return $("#serverAddress").val();
                        }
                    }
                }
            },
            serverAccount : {
                required : true
            },
            serverPassword : {
                required : true
            }
        },
        messages : {
            serverName : {
                required : icon + "请输入服务器名",
                remote : icon + "服务器已经存在"
            },
            serverAddress : {
                required : icon + "请输入服务器地址",
                remote : icon + "服务器地址已经存在"
            },
            serverAccount : {
                required : icon + "请输入服务器账号"
            },
            serverPassword : {
                required : icon + "请输入服务器密码"
            }
        }
    })
}