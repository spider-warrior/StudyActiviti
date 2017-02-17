<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>请假流程</title>
</head>
<body>
天数： <input name="time" id="time" type="text"/><br/>
事由: <input name="reason" id="reason" type="text"/><br/>
<button id="submit_btn">提交申请</button>
</body>

<script src="/static/js/common.js"></script>
<script type="text/javascript">
$.registerVLCEvent("submit_btn", "click", function() {
    alert("已提交申请");
});
</script>
</html>
