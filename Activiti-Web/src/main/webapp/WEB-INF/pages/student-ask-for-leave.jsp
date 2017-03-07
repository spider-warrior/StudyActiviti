<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>请假流程(key: student-ask-for-leave)</title>
    <%@include file="header.jsp" %>
</head>
<body id="pageBody">
<%@include file="banner.jsp" %>
<h2>process key: student-ask-for-leave</h2>
天数： <input name="time" id="time" type="text"/><br/>
事由: <input name="reason" id="reason" type="text"/><br/>
<button id="submit_btn">提交申请</button>

<hr/>
流程图: <br/>
<img id="processDefinition_img" src="">
</body>

<script type="text/javascript">
    var uri = document.location.pathname;
    var processDefinitionId = uri.substr(uri.lastIndexOf("/") + 1);
    $("#processDefinition_img").src = "/processdefinition/" + processDefinitionId + "/image";
</script>
<script type="text/javascript">
    function submitFormData() {
        var time = $("#time").value;
        var reason = $("#reason").value;
        var queryUrl = "/student-ask-for-leave";
        var uri = document.location.pathname;
        var processDefinitionId = uri.substr(uri.lastIndexOf("/") + 1);
        var method = POST;
        var param = {time: time, reason: reason, processDefinitionId: processDefinitionId};

        var submitFormDataCallback = function (result) {
            if (result.success) {
                alert("流程申请成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, submitFormDataCallback);
    }

    $.registerEvent("submit_btn", "click", submitFormData);
    $.registerEvent("pageBody", "keydown", function (e) {
        var theEvent = e || window.event;
        var code = theEvent.keyCode || theEvent.which || theEvent.charCode;
        if (code == 13) {
            submitFormData();
        }
    });
</script>
</html>
