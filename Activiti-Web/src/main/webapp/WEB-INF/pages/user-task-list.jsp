<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户任务</title>
    <%@include file="header.jsp"%>
</head>
<body>
<%@include file="banner.jsp"%>
<h3>可领取的任务:
    <button id="queryAvailableTaskBtn">刷新列表</button>
    <br/>
</h3>
<table>
    <thead>
        <tr>
            <th></th>
            <th></th>
            <th></th>
            <th></th>
        </tr>
    </thead>
    <tbody id="userTaskToBeClaim">
    </tbody>
</table>

<script type="text/javascript">
    function queryAvailableTask() {
        var queryUrl = "/user/task/list";
        var method = GET;
        var param = {};

        var queryAvailableTaskCallback = function (result) {
            if (result.success) {
                console.log(result.data);
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, queryAvailableTaskCallback);
    }
    queryAvailableTask();
    $.registerEvent("queryAvailableTaskBtn", "click", queryAvailableTask);
</script>
</body>
</html>
