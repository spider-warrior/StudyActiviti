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
            if (result.code == SERVER_INTERNAL_EXCEPTION_CODE) {
                alert("服务器内部异常");
            }
            else if (result.code == REQUEST_PARAM_ERROR) {
                alert("流程申参数格式不正确");
            }
            else if (result.code == REQUEST_NOT_ALLOWED) {
                alert("无权限操作");
            }
            else if (result.code == RESOURCE_NOT_FOUND_EXCEPTION_CODE) {
                if (result.msg == "user") {
                    alert("用户不存在");
                }
                else if (result.msg == "processdefinition") {
                    alert("流程定义不存在");
                }
                else {
                    alert(result.msg);
                }
            }
            else {
                alert(result.msg);
            }
        }
    }
    executeRequest(queryUrl, param, method, logoutCallback);
}
