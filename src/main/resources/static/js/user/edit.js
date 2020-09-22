$().ready(function () {
    validateRule();
});

$.validator.setDefaults({
    submitHandler: function () {
        update();
    }
});
function update() {
    $("#roleIds").val(getCheckedRoles("role"));
    $.ajax({
        cache: true,
        type: "POST",
        url: "/user/update",
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
function getCheckedRoles(name) {
    var adIds = "";
    $("input:checkbox[name="+name+"]:checked").each(function (i) {
        if (0 == i) {
            adIds = $(this).val();
        } else {
            adIds += ("," + $(this).val());
        }
    });
    return adIds;
}
function validateRule() {
    var icon = "<i class='fa fa-times-circle'></i> ";
    $("#signupForm").validate({
        rules: {
            username: {
                required: true,
                minlength: 2
            }
        },
        messages: {
            username: {
                required: icon + "请输入您的用户名",
                minlength: icon + "用户名必须两个字符以上"
            }
        }
    })
}