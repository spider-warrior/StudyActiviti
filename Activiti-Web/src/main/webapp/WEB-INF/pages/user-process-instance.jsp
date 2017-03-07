<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户流程查询</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h3>我发起的流程:
    <button id="queryProcessInstanceBtn">刷新列表</button>
    <br/></h3>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>businessKey</th>
        <th>流程ID</th>
        <th>开始时间</th>
        <th>结束时间</th>
        <th>发起人</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="tbody"></tbody>
</table>
</body>

<script type="text/javascript">
    //用户添加流程权限
    function queryProcessInstance() {
        var queryUrl = "/processinstance/user/list";
        var method = GET;
        var param = {};
        var queryProcessInstanceCallback = function (result) {
            if (result.success) {
                $("#tbody").innerHTML = "";
                var processinstances = result.data.processinstances;
                if (processinstances && processinstances.length > 0) {
                    var tbody = $("#tbody");
                    for (var i = 0; i < processinstances.length; i++) {
                        var instance = processinstances[i];
                        var idTd = $.createTdWithText(instance.id);
                        var businessKeyTd = $.createTdWithText(instance.businessKey);
                        var processDefinitionTd = $.createTdWithText(instance.processDefinitionId);
                        var startTimeTd = $.createTdWithText(instance.startTime);
                        var endTime = $.createTdWithText(instance.endTime);
                        var starterTd = $.createTdWithText(instance.starter.id);
                        var operationTdContent = "<a href='/home/user/processinstance/" + instance.id + "'>详情</a>";
                        var operationTd = $.createTdWithHtml(operationTdContent);
                        var tr = $.createTr();
                        tr.appendChild(idTd);
                        tr.appendChild(businessKeyTd);
                        tr.appendChild(processDefinitionTd);
                        tr.appendChild(startTimeTd);
                        tr.appendChild(endTime);
                        tr.appendChild(starterTd);
                        tr.appendChild(operationTd);
                        tbody.appendChild(tr);
                    }
                }
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, queryProcessInstanceCallback);
    }
    ;
    queryProcessInstance();
    $.registerEvent("queryProcessInstanceBtn", "click", queryProcessInstance);
</script>
</html>
