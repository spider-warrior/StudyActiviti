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

<iframe id="hiddenframe" name="hiddenframe" style="display: none;"></iframe>
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
                                                                + "<a href='javascript:void(0)' onclick='deleteModel(\"" + model.id +"\")'>删除</a>&nbsp;&nbsp;"
                                                                + "<a href='javascript:void(0)' onclick='deployModel(\"" + model.id +"\")'>部署</a>&nbsp;&nbsp;"
                                                                + "<a href='/model/export/" + model.id + "' target='hiddenframe'>导出流程文件</a>&nbsp;&nbsp;"
                                                                + "<a href='/model/image/" + model.id + "' target='_blank'>查看流程图</a>&nbsp;&nbsp;");
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
                dealAjaxError(result);
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
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, addModelCallback);
    }
    $.registerEvent("submit_model_btn", "click", addModel);
</script>

<script type="text/javascript">
    function deleteModel(modelId) {
        var queryUrl = "/model/" + modelId;
        var method = DELETE;
        var param = {};
        var deleteModelCallback = function (result) {
            if (result.success) {
                queryModelList();
                alert("model删除成功");
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, deleteModelCallback);
    }
</script>

<script type="text/javascript">
    function deployModel(modelId) {
        var queryUrl = "/model/deploy/" + modelId;
        var method = GET;
        var param = {};
        var deployModelCallback = function (result) {
            if (result.success) {
                alert("model部署成功");
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, deployModelCallback);
    }
</script>

<script type="text/javascript">
    function exportModel(modelId) {
        var queryUrl = "/model/export/" + modelId;
        var method = GET;
        var param = {};
        var exportModelCallback = function (result) {
            if (result.success) {
                queryModelList();
                alert("model删除成功");
            }
            else {
                dealAjaxError(result);
            }
        }
        executeRequest(queryUrl, param, method, exportModelCallback);
    }
</script>
</body>
</html>
