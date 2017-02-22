<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Model</title>
    <%@include file="basic-static-file-import.jsp"%>
</head>
<body>
<%@include file="header.jsp"%>
流程列表: <button id="queryModelBtn">刷新列表</button> <br/>
<table width="80%" align="center" border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Model名称</th>
        <th>key</th>
        <th>category</th>
        <th>版本</th>
        <th>创建时间</th>
        <th>部署ID</th>
        <th>操作</th>
    </tr>
    </thead>
    <tbody id="tbody"></tbody>
</table>
</body>

<hr/>

添加Model: <br/>
model 名称: <input name="name" id="name" type="text"/><br/>
model 范畴: <input name="category" id="category" type="text"/><br/>
model 部署Id: <input name="deploymentId" id="deploymentId" type="text"/><br/>
model key: <input name="key" id="key" type="text"/><br/>
model metaInfo: <input name="metaInfo" id="metaInfo" type="text"/><br/>
model tenantId: <input name="tenantId" id="tenantId" type="text"/><br/>
model version: <input name="version" id="version" type="text"/><br/>
model 描述: <textarea name="description" id="description" rows="4" cols="50"></textarea><br/>
<button id="submit_model_btn" type="button">添加model</button><br/>

<script type="text/javascript">
    function queryModelList () {
        var queryUrl = "/model/list";
        var method = POST;
        var param = {}
        var queryModelListCallback = function (result) {
            if (result.success) {
                console.log(JSON.stringify(result.data));
                var models = result.data.models;
                if (models && models.length > 0) {
                    $("#tbody").innerHTML = "";
                    for (var i =0; i<models.length; i++) {
                        var model = models[i];
                        var tr = $.createTr();
                        var idTd = $.createTdWithText(model.id);
                        var nameTd = $.createTdWithText(model.name);
                        var keyTd = $.createTdWithText(model.key);
                        var categoryTd = $.createTdWithText(model.category);
                        var versionTd = $.createTdWithText(model.version);
                        var createTimeTd = $.createTdWithText(model.createTime);
                        var deploymentId = $.createTdWithText(model.deploymentId);
                        var operationId = $.createTdWithHtml("<a target='_blank' href='/process-editor/modeler.html?modelId=" + model.id + "'>" + "详情" + "</a>&nbsp;&nbsp;"
                                                                + "<a href='javascript:void(0)' onclick='alert(\"部署成功\")'>部署</a>");
                        tr.appendChild(idTd);
                        tr.appendChild(nameTd);
                        tr.appendChild(keyTd);
                        tr.appendChild(categoryTd);
                        tr.appendChild(versionTd);
                        tr.appendChild(createTimeTd);
                        tr.appendChild(deploymentId);
                        tr.appendChild(operationId);
                        $("#tbody").appendChild(tr);
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
                else {
                    alert(result.msg);
                }
            }
        }
        executeRequest(queryUrl, param, method, queryModelListCallback);
    }
    $.registerEvent("queryModelBtn", "click", queryModelList);
    queryModelList();
</script>

<script type="text/javascript">
    function addModel() {
        var name = $("#name").value;
        var description = $("#description").value;
        var queryUrl = "/model/save/quick";
        var method = POST;
        var param = {name: name, description: description}
        var addModelCallback = function (result) {
            if (result.success) {
                queryModelList();
                alert("model添加成功");
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
        executeRequest(queryUrl, param, method, addModelCallback);
    }
    $.registerEvent("submit_model_btn", "click", addModel);
</script>
</body>
</html>
