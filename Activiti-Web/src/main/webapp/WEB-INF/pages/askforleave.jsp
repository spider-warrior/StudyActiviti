<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>请假流程</title>
</head>
<body id="pageBody">
天数： <input name="time" id="time" type="text"/><br/>
事由: <input name="reason" id="reason" type="text"/><br/>
<button id="submit_btn">提交申请</button>
</body>

<script src="/static/js/common.js"></script>
<script type="text/javascript">

function submitFormData() {

    var time = $("#time").value;
    var reason = $("#reason").value;
    var queryUrl = "/askforleave";
    var method = POST;
    var param = {time: time, reason: reason}

    var submitFormDataCallback = function (result) {
        if (result.success) {
            alert("流程申请成功");
        }
        else {
            if (result.code == SERVER_INTERNAL_EXCEPTION_CODE) {
                alert("服务器内部异常");
            }
            else if(result.code == REQUEST_PARAM_ERROR) {
                alert("流程申参数格式不正确")
            }
            else {
                alert(result.msg);
            }
        }
    }
    executeRequest(queryUrl, param, method, submitFormDataCallback);
}

$.registerVLCEvent("#submit_btn", "click", submitFormData);
$.registerVLCEvent("#pageBody", "keydown", function(e){
    var theEvent = e || window.event;
    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
    if (code == 13) {
        submitFormData();
    }
});
</script>
</html>
