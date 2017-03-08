var SESSION_KEY = "login_session";
var USER_NAME_COOKIE_KEY = "username_key";

function logout() {
    var queryUrl = "/logout";
    var method = GET;
    var param = null;
    var logoutCallback = function (result) {
        if (result.success) {
            $.redirectRoot();
        }
        else {
            dealAjaxError(result);
        }
    };
    executeRequest(queryUrl, param, method, logoutCallback);
}

function dealAjaxError(response) {
    if (response.code == SERVER_INTERNAL_EXCEPTION_CODE) {
        alert("服务器内部异常");
    }
    else if (response.code == REQUEST_PARAM_ERROR) {
        alert("流程申参数格式不正确");
    }
    else if (response.code == REQUEST_NOT_ALLOWED) {
        alert("无权限操作");
    }
    else if (response.code == RESOURCE_NOT_FOUND_EXCEPTION_CODE) {
        if (response.msg == "user") {
            alert("用户不存在");
        }
        else if (response.msg == "group") {
            alert("用户组不存在");
        }
        else if (response.msg == "processdefinition") {
            alert("流程定义不存在");
        }
        else if (response.msg == 'processinstance') {
            alert("流程实例不存在");
        }
        else if (response.ms == 'historic_process_instance') {
            alert("历史流程实例不存在");
        }
        else if (response.ms == 'user_task') {
            alert("任务不存在");
        }
        else {
            alert(response.msg);
        }
    }
    else {
        alert(response.msg);
    }

}
