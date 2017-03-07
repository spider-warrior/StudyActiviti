<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>流程实例详情</title>
    <%@include file="header.jsp" %>
</head>
<body>
<%@include file="banner.jsp" %>
<h2>流程详情: </h2>
<img id="instance_img" src="">

<script type="text/javascript">
    var uri = document.location.pathname;
    var instanceId = uri.substr(uri.lastIndexOf("/") + 1);
    $("#instance_img").src = "/processinstance/" + instanceId + "/image";
</script>
</body>
</html>
