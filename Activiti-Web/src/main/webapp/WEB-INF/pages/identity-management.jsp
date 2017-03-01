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
<h3>用户组列表: <button id="queryGroupsBtn">刷新列表</button> <br/></h3>
<table border="1">
    <thead>
    <th>id</th>
    <th>name</th>
    <th>操作</th>
    </thead>
    <tbody id="groupListBody">
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
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, queryUsersCallback);
    }
    queryUsers();
    $.registerEvent("queryUsersBtn", "click", queryUsers);
</script>

<script type="text/javascript">
    function queryGroups() {
        var queryUrl = "/identity/groups";
        var method = POST;
        var param = {};
        var queryGroupsCallback = function (result) {
            if (result.success) {
                $("#userListBody").innerHTML = "";
                var groups = result.data.groups;
                if (groups) {
                    var tbody = $("#groupListBody");
                    for (var i=0; i<groups.length; i++) {
                        var group = groups[i];
                        var idTd = $.createTdWithText(group.id);
                        var nameTd = $.createTdWithText(group.name);
                        var operationTd = $.createTd();
                        var row = $.createTr();
                        row.appendChild(idTd);
                        row.appendChild(nameTd);
                        row.appendChild(operationTd);
                        tbody.appendChild(row);
                    }
                }
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, queryGroupsCallback);
    }
    queryUsers();
    $.registerEvent("queryGroupsBtn", "click", queryGroups);
</script>
</body>
</html>
