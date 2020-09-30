$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});
function update() {
    $.ajax({
        cache: true,
        type: "POST",
        url: "/filepath/update",
        data: $('#signupForm').serialize(),// 你的formid
        async: false,
        error: function (request) {
            parent.layer.alert("网络错误，请稍后再试");
        },
        success: function (data) {
            if (data.code == 1) {
                parent.layer.msg(data.message);
                parent.reLoad();
                var index = parent.layer.getFrameIndex(window.name); // 获取窗口索引
                parent.layer.close(index);

            } else {
                parent.layer.msg(data.message);
            }
        }
    });
}

function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules : {
            fileName : {
                required : true
            },
            filePath : {
                required : true
            }
        },
        messages : {
            fileName : {
                required : icon + "请输入文件名"
            },
            filePath : {
                required : icon + "请输入文件地址"
            }
        }
    })
}