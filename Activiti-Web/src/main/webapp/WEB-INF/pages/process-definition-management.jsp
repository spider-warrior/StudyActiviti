<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>流程管理</title>
    <%@include file="header.jsp"%>
</head>
<body>
<%@include file="banner.jsp"%>
<h3>流程列表: <button id="queryProcessDefinitionBtn">刷新列表</button> <br/></h3>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>流程名称</th>
        <th>业务码</th>
        <th>有权限用户</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="tbody"></tbody>
    <tfoot>
    <tr>
        <td>流程总数</td>
        <td id="total"></td>
    </tr>
    </tfoot>
</table>
</body>

<hr/>

<h3>用户添加流程权限: <br/></h3>
用户id: <input name="username" id="username" type="text"/><br/>
流程id：<input name="pdid" id="pdid" type="text"/><br/>
<button id="user_add_pd_btn">添加</button>

<h3>用户组添加流程权限: <br/></h3>
用户组id: <input name="groupId" id="groupId" type="text"/><br/>
流程id：<input name="g_pdid" id="g_pdid" type="text"/><br/>
<button id="group_add_pd_btn">添加</button>


<script type="text/javascript">
    // for list
    function queryProcessDefinition() {
        var queryUrl = "/pdm/list";
        var method = POST;
        var param = {}

        var queryProcessDefinitionCallback = function (result) {
            if (result.success) {
                //{"processes":[{"id":"askForLeaveProcess:1:3","name":"Simple approval process","businessKey":"askForLeaveProcess"}]}
                console.log(JSON.stringify(result.data));
                var processes = result.data.processes;
                if (processes && processes.length > 0) {
                    $("#tbody").innerHTML = "";
                    for (var i =0; i<processes.length; i++) {
                        var pd = processes[i];
                        var tr = $.createTr();
                        var idTd = $.createTdWithText(pd.id);
                        var nameTd = $.createTdWithText(pd.name);
                        var businessKeyTd = $.createTdWithText(pd.businessKey);
                        var content = "";
                        var candidateUsers = pd.candidateUsers;
                        if (candidateUsers && candidateUsers.length > 0) {
                            var first = true;
                            for (var k=0; k<candidateUsers.length; k++) {
                                if (!first) {
                                    first = false;
                                    content += ", ";
                                }
                                else {
                                    content += ("<a href='javascript:void(0);' onclick='userDeletePdAuth(\""+ candidateUsers[k].id + "\", \"" + pd.id +"\")'>" + candidateUsers[k].id + "</a>&nbsp;&nbsp;");
                                }
                            }
                        }
                        var candidateGroups = pd.candidateGroups;
                        if (candidateGroups && candidateGroups.length > 0) {
                            var first = true;
                            for (var k=0; k<candidateGroups.length; k++) {
                                if (!first) {
                                    first = false;
                                    content += ", ";
                                }
                                else {
                                    content += ("<a href='javascript:void(0);' onclick='groupDeletePdAuth(\""+ candidateGroups[k].id + "\", \"" + pd.id +"\")'>" + candidateGroups[k].id + "</a>&nbsp;&nbsp;");
                                }
                            }
                        }
                        var authedUserTd = $.createTdWithHtml(content);
                        var operationTd = $.createTd();
                        var operationTdHtml = "<a href='javascript:void(0)' onclick='pdGenerateModel(\"" + pd.id +"\")'>生成model</a>　&nbsp;&nbsp;" +　"<a href='javascript:void(0)' onclick='deleteProcessDefinition(\"" + pd.id +"\")'>删除</a>";
                        operationTd.innerHTML = operationTdHtml;
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(businessKeyTd);
                        tr.appendChild(authedUserTd);
                        tr.appendChild(operationTd);
                        $("#tbody").appendChild(tr);
                    }
                    $("#total").innerText = processes.length;
                }
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, queryProcessDefinitionCallback);
    }
    queryProcessDefinition();
    $.registerEvent("queryProcessDefinitionBtn", "click", queryProcessDefinition);
</script>

<script type="text/javascript">
//用户添加流程权限
function userAddPdAuth() {
    var username = $("#username").value;
    var pdid = $("#pdid").value;
    var queryUrl = "/pdm/user/add/auth";
    var method = POST;
    var param = {username: username, pdid: pdid};

    var userAddPdAuthCallback = function (result) {
        if (result.success) {
            queryProcessDefinition();
            alert("成功");
        }
        else {
            dealAjaxError(result);
        }
    };
    executeRequest(queryUrl, param, method, userAddPdAuthCallback);
}
$.registerEvent("user_add_pd_btn", "click", userAddPdAuth);

function groupAddPdAuth() {
    var groupId = $("#groupId").value;
    var pdid = $("#g_pdid").value;
    var queryUrl = "/pdm/group/add/auth";
    var method = POST;
    var param = {groupId: groupId, pdid: pdid};
    var groupAddPdAuthCallback = function (result) {
        if (result.success) {
            queryProcessDefinition();
            alert("成功");
        }
        else {
            dealAjaxError(result);
        }
    };
    executeRequest(queryUrl, param, method, groupAddPdAuthCallback);
}
$.registerEvent("group_add_pd_btn", "click", groupAddPdAuth);
</script>
<script type="text/javascript">
    function userDeletePdAuth(username, pdid) {
        var queryUrl = "/pdm/user/delete/auth";
        var method = POST;
        var param = {username: username, pdid: pdid};

        var userDeletePdAuthCallback = function (result) {
            if (result.success) {
                queryProcessDefinition();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, userDeletePdAuthCallback);
    }
</script>

<script type="text/javascript">
    function groupDeletePdAuth(groupId, pdid) {
        var queryUrl = "/pdm/group/delete/auth";
        var method = POST;
        var param = {groupId: groupId, pdid: pdid};
        var groupDeletePdAuthCallback = function (result) {
            if (result.success) {
                queryProcessDefinition();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, groupDeletePdAuthCallback);
    }
</script>

<script type="text/javascript">
    function pdGenerateModel(pdid) {
        var queryUrl = "/model/import/" + pdid;
        var method = GET;
        var param = {};
        var pdGenerateModelCallback = function (result) {
            if (result.success) {
                alert("model生成成功");
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, pdGenerateModelCallback);
    }
</script>

<script type="text/javascript">
    function deleteProcessDefinition(pdid) {
        var queryUrl = "/pdm/" + pdid;
        var method = DELETE;
        var param = {};
        var deleteProcessDefinitionCallback = function (result) {
            if (result.success) {
                queryProcessDefinition();
                alert("删除流程定义成功");
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, deleteProcessDefinitionCallback);
    }
</script>
</html>
