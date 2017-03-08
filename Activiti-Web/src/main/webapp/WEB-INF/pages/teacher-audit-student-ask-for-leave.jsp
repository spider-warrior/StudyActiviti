<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>老师处理学生请假</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h2>审核学生请假<br/></h2>
请假人: <span id="applyUser"></span><br/>
请假时长: <span id="applyTime"></span>天<br/>
请假原因: <span id="applyReason"></span><br/>
创建时间: <span id="createTime"></span><br/>
<hr/>
<h2>历史评论<br/></h2>
<div style="width: auto" id="comment_div"></div>
<hr/>
意见:<br/>
<textarea id="comment" cols="50" rows="5"></textarea><br/>
<button id="agreeBtn">同意</button>

<script type="text/javascript">
    function queryTaskById() {
        var uri = document.location.pathname;
        var taskId = uri.substr(uri.lastIndexOf("/") + 1);
        var queryUrl = "/task/" + taskId;
        var method = GET;
        var param = {};
        var queryTaskByIdCallback = function (result) {
            if (result.success) {
                var task = result.data.task;
                if (task) {
                    $("#createTime").innerText = task.createTime;
                    var variables = task.variables;
                    $("#applyUser").innerText = variables.applyUserId;
                    $("#applyTime").innerText = variables.time;
                    $("#applyReason").innerText = variables.reason;
                    var comments = task.comments;
                    if (comments && comments.length > 0) {
                        var commentContent = '<h3>';
                        for (var i=0; i<comments.length; i++) {
                            var comment = comments[i];
                            var userId = comment.commentUser.id;
                            var content = comment.content;
                            commentContent += userId;
                            commentContent += " : ";
                            commentContent += content;
                            commentContent += "<br/>";
                        }
                        commentContent += "</h3>";
                        $("#comment_div").innerHTML = commentContent;
                    }
                }
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, queryTaskByIdCallback);
    }
    queryTaskById();
</script>
<script type="text/javascript">
    function agree() {
        var uri = document.location.pathname;
        var taskId = uri.substr(uri.lastIndexOf("/") + 1);
        var comment = $("#comment").value;
        var queryUrl = "/user/task/audit/student-ask-for-leave/agree";
        var method = POST;
        var param = {taskId: taskId, comment: comment};
        var agreeCallback = function (result) {
            if (result.success) {
                queryTaskById();
                alert("成功");
            }
            else {
                dealAjaxError(result);
            }
        };
        executeRequest(queryUrl, param, method, agreeCallback);
    }
    $.registerEvent('agreeBtn', 'click', agree);
</script>
</body>
</html>
