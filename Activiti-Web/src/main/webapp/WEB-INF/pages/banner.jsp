<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" isELIgnored="false"
         pageEncoding="UTF-8" %>
<div id="header">
    <div class="ib margin0-10-0-10"><a href="javascript:void(0);" id="login_username" onclick="logout();"></a></div>
    <div class="ib margin0-10-0-10"><a href="javascript:void(0);" onclick="logout();">退出</a></div>
</div>
<script type="text/javascript">
    var username = $.getCookie(USER_NAME_COOKIE_KEY);
    if (username) {
        $("#login_username").innerText = username;
    }
</script>