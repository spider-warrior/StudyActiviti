<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户管理页面</title>
    <%@include file="basic-static-file-import.jsp"%>
</head>
<body>
<%@include file="header.jsp"%>
<h3>用户列表: <button id="queryUsersBtn">刷新列表</button> <br/></h3>
<table border="1">
    <thead>
        <th>id</th>
        <th>name</th>
        <th>groups</th>
        <th>操作</th>
    </thead>
    <tbody id="userListBody">
    </tbody>
</table>

<script type="text/javascript">
    function queryUsers() {
        var queryUrl = "/identity/users";
        var method = POST;
        var param = {};
        var queryUsersCallback = function (result) {
            if (result.success) {
                $("#userListBody").innerHTML = "";
                var users = result.data.users;
                if (users) {
                    var tbody = $("#userListBody");
                    for (var i=0; i<users.length; i++) {
                        var user = users[i];
                        var idTd = $.createTdWithText(user.id);
                        var nameTd = $.createTdWithText(user.name);
                        var groupsContent = "";
                        if (users.userGroups) {
                            var userGroups = users.userGroups;
                            for (var k=0; k<userGroups.length; k++) {
                                var userGroup = userGroups[k];
                                groupsContent.concat("<a href='javascript:void(0);' onclick='alert(1)'>", userGroup.name, "</a>", "&nbsp;&nbsp;");
                            }
                        }
                        var groupsTd = $.createTdWithText(groupsContent);
                        var operationTd = $.createTd();
                        var row = $.createTr();
                        row.appendChild(idTd);
                        row.appendChild(nameTd);
                        row.appendChild(groupsTd);
                        row.appendChild(operationTd);
                        tbody.appendChild(row);
                    }
                }
            }
            else {
                if (result.code == SERVER_INTERNAL_EXCEPTION_CODE) {
                    alert("服务器内部异常");
                }
                else if(result.code == REQUEST_PARAM_ERROR) {
                    alert("流程申参数格式不正确");
                }
                else if (result.code == REQUEST_NOT_ALLOWED) {
                    alert("无权限操作");
                }
                else if (result.code == RESOURCE_NOT_FOUND_EXCEPTION_CODE) {
                    if(result.msg == "user") {
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
        executeRequest(queryUrl, param, method, queryUsersCallback);
    }
    queryUsers();
    $.registerEvent("queryUsersBtn", "click", queryUsers);
</script>
</body>
</html>
