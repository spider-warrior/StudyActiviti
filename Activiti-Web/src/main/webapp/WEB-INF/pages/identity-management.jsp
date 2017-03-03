<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>用户管理页面</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h3>用户列表:
    <button id="queryUsersBtn">刷新列表</button>
    <br/></h3>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>groups</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="userListBody">
    </tbody>
</table>
<h3>添加用户: <br/></h3>
用户id: <input name="userId" id="userId" type="text"/> <br/>
用户密码: <input name="password" id="password" type="password"/> <br/>
<button id="addUserBtn">添加用户</button>
<br/>

<h3>用户组列表:
    <button id="queryGroupsBtn">刷新列表</button>
    <br/></h3>
<table border="1">
    <thead>
    <tr>
        <th>id</th>
        <th>name</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="groupListBody">
    </tbody>
</table>

<h3>添加用户组: <br/></h3>
用户组id: <input name="groupId" id="groupId" type="text"/><br/>
用户组名称: <input name="groupName" id="groupName" type="text"/><br/>
<button id="addGroupBtn">添加用户组</button>
<br/>

<h3>用户组添加成员: <br/></h3>
用户id: <input name="linkUserId" id="linkUserId" type="text"/><br/>
用户组id: <input name="linkGroupId" id="linkGroupId" type="text"/><br/>
<button id="linkUserGroupBtn">建立关系</button>
<br/>

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
                    for (var i = 0; i < users.length; i++) {
                        var user = users[i];
                        var idTd = $.createTdWithText(user.id);
                        var nameTd = $.createTdWithText(user.name);
                        var groupsContent = "";
                        if (user.userGroups) {
                            var userGroups = user.userGroups;
                            for (var k = 0; k < userGroups.length; k++) {
                                var userGroup = userGroups[k];
                                groupsContent = groupsContent.concat("<a href='javascript:void(0);' onclick='cancelMemberShip(\"" + user.id + "\", \"" + userGroup.id + "\");'>", userGroup.name, "</a>", "&nbsp;&nbsp;");
                            }
                        }
                        var groupsTd = $.createTdWithHtml(groupsContent);
                        var operationHtml = "<a href='javascript:void(0);' onclick='deleteUser(\"" + user.id + "\")'>" + "删除" + "</a>&nbsp;&nbsp;";
                        var operationTd = $.createTdWithHtml(operationHtml);
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
        };
        executeRequest(queryUrl, param, method, queryUsersCallback);
    }
    queryUsers();
    $.registerEvent("queryUsersBtn", "click", queryUsers);
</script>

<script type="text/javascript">
    function addUser() {
        var id = $("#userId").value;
        var password = $("#password").value;
        var queryUrl = "/identity/user";
        var method = POST;
        var param = {id: id, password: password};
        var addUserCallback = function (result) {
            if (result.success) {
                queryUsers();
                alert("添加用户成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, addUserCallback);
    }
    $.registerEvent("addUserBtn", "click", addUser);
</script>

<script type="text/javascript">
    function deleteUser(userId) {
        var queryUrl = "/identity/user/" + userId;
        var method = DELETE;
        var param = {};
        var deleteUserCallback = function (result) {
            if (result.success) {
                queryUsers();
                alert("删除用户成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, deleteUserCallback);
    }
</script>

<script type="text/javascript">
    function queryGroups() {
        var queryUrl = "/identity/groups";
        var method = POST;
        var param = {};
        var queryGroupsCallback = function (result) {
            if (result.success) {
                $("#groupListBody").innerHTML = "";
                var groups = result.data.groups;
                if (groups) {
                    var tbody = $("#groupListBody");
                    for (var i = 0; i < groups.length; i++) {
                        var group = groups[i];
                        var idTd = $.createTdWithText(group.id);
                        var nameTd = $.createTdWithText(group.name);
                        var operationHtml = "<a href='javascript:void(0);' onclick='deleteGroup(\"" + group.id + "\")'>" + "删除" + "</a>&nbsp;&nbsp;";
                        var operationTd = $.createTdWithHtml(operationHtml);
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
        };
        executeRequest(queryUrl, param, method, queryGroupsCallback);
    }
    queryGroups();
    $.registerEvent("queryGroupsBtn", "click", queryGroups);
</script>

<script type="text/javascript">
    function addUserGroup() {
        var id = $("#groupId").value;
        var groupName = $("#groupName").value;
        var queryUrl = "/identity/group";
        var method = POST;
        var param = {id: id, name: groupName};
        var addUserGroupCallback = function (result) {
            if (result.success) {
                queryGroups();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, addUserGroupCallback);
    }
    $.registerEvent("addGroupBtn", "click", addUserGroup);
</script>

<script type="text/javascript">
    function deleteGroup(groupId) {
        var queryUrl = "/identity/group/" + groupId;
        var method = DELETE;
        var param = {};
        var deleteGroupCallback = function (result) {
            if (result.success) {
                queryGroups();
                alert("删除用户组成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, deleteGroupCallback);
    }
</script>

<script type="text/javascript">
    function createMemberShip() {
        var userId = $("#linkUserId").value;
        var groupId = $("#linkGroupId").value;
        var queryUrl = "/identity/group/membership";
        var method = POST;
        var param = {userId: userId, groupId: groupId};
        var createMemberShipCallback = function (result) {
            if (result.success) {
                queryUsers();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, createMemberShipCallback);
    }
    $.registerEvent("linkUserGroupBtn", "click", createMemberShip);
</script>

<script type="text/javascript">
    function cancelMemberShip(userId, groupId) {
        var queryUrl = "/identity/group/membership";
        var method = DELETE;
        var param = {userId: userId, groupId: groupId};
        var cancelMemberShipCallback = function (result) {
            if (result.success) {
                queryUsers();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, cancelMemberShipCallback);
    }
</script>

</body>
</html>
