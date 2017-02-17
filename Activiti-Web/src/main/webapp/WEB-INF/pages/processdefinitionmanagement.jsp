<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>流程管理</title>
</head>
<body>
流程列表: <button id="queryProcessDefinitionBtn">刷新列表</button> <br/>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>流程名称</th>
        <th>业务码</th>
        <th>有权限用户</th>
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

用户添加流程权限:<br/>
用户名: <input name="username" id="username" type="text"/><br/>
流程id：<input name="pdid" id="pdid" type="text"/><br/>
<button id="user_add_pd_btn">添加</button>

<script src="/static/js/common.js"></script>
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
                        var owners = pd.owners;
                        var content = "";
                        if (owners && owners.length > 0) {
                            var first = true;
                            for (var k=0; k<owners.length; k++) {
                                if (first) {
                                    first = false;
                                    content += owners[k].id;
                                }
                                else {
                                    content += (", " + owners[k].id);
                                }
                            }
                        }
                        var authedUserTd = $.createTdWithText(content);
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(businessKeyTd);
                        tr.appendChild(authedUserTd);
                        $("#tbody").appendChild(tr);
                    }
                    $("#total").innerText = processes.length;
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
                else {
                    alert(result.msg);
                }
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
            alert("成功");
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
            else {
                alert(result.msg);
            }
        }
    }
    executeRequest(queryUrl, param, method, userAddPdAuthCallback);
}
$.registerEvent("user_add_pd_btn", "click", userAddPdAuth);
</script>
</html>
