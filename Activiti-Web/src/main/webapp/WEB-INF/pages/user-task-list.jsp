<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户任务</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h3>可领取的任务:
    <button id="queryAvailableTaskBtn">刷新列表</button>
    <br/>
</h3>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>category</th>
        <th>processDefinitionId</th>
        <th>executionId</th>
        <th>owner</th>
        <th>assignee</th>
        <th>createTime</th>
        <th>description</th>
        <th>操作</th>
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
                var tasks = result.data.tasks;
                if (tasks && tasks.length > 0) {
                    var tobody = $("#userTaskToBeClaim");
                    for (var i=0; i<tasks.length; i++) {
                        var task = tasks[i];
                        var idTd = $.createTdWithText(task.id);
                        var nameTd = $.createTdWithText(task.name);
                        var categoryTd = $.createTdWithText(task.category);
                        var processDefinitionIdTd = $.createTdWithText(task.processDefinitionId);
                        var executionIdTd = $.createTdWithText(task.executionId);
                        var ownerTd = $.createTdWithText(task.owner ? task.owner.id : null);
                        var assigneeTd = $.createTdWithText(task.assignee);
                        var createTimeTd = $.createTdWithText(task.createTime);
                        var descriptionTd = $.createTdWithText(task.description);
                        var operationTd = $.createTd();
                        var tr = $.createTr();
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(categoryTd);
                        tr.appendChild(processDefinitionIdTd);
                        tr.appendChild(executionIdTd);
                        tr.appendChild(ownerTd);
                        tr.appendChild(assigneeTd);
                        tr.appendChild(createTimeTd);
                        tr.appendChild(descriptionTd);
                        tr.appendChild(operationTd);
                        tobody.appendChild(tr);
                    }
                }
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
