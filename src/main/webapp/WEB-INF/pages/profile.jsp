<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="botDetect" uri="https://captcha.com/java/jsp" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/css/bootstrap.css">

    <script src="/js/jquery-3.3.1.min.js"></script>
    <script src="/js/bootstrap.min.js"></script>
    <script src="/js/lib.js"></script>
    <script type="text/javascript">
        function load() {
            showNav("profile");
            showAndDismissAlerts("${messages}");
        }

    </script>

</head>
<body onload="load()">
<div class="alert-messages text-center" style="position: fixed;
        bottom: 0;
        left: 25%;
        right: 25%;
        z-index: 7000;">
</div>
<div id="nav" align="center"></div>
<div align="center">
<c:choose>
    <c:when test="${not empty currentUserDetails}">
        <div id="infoDiv" class="w-50">
            Hello, ${currentUserDetails.name} !<BR><BR>
            <c:if test="${not empty currentUserDetails}">
                Your login is <B>${currentUserDetails.login}</B><br>
                <c:if test="${not empty currentUserDetails.email}">
                    Your email is <B>${currentUserDetails.email}</B><br>
                </c:if>
                <c:if test="${not empty currentUserDetails.emailNew}">
                        <span style="color: red;">Your
                            <c:if test="${not empty currentUserDetails.email}">new</c:if>
                            email
                            <span style="font-weight: bold;">${currentUserDetails.emailNew}</span> was not confirmed !</span><BR>
                    <div id="confirmDiv" class="w-50">
                        <form action="/emailconfirm" method="get">
                            Enter confirmation code:
                            <input type="text" name="code" maxlength="20">
                            <input type="submit" class="btn btn-outline-warning" value="Confirm">
                        </form>
                        <form action="/sendconfirmemail" method="get">
                            <input type="submit" value="Send another code" class="btn btn-outline-warning">
                        </form>
                    </div>
                </c:if>
            </c:if>
            <br>
            <form action="/logout" method="post" style="display: inline">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" value="Logout" class="btn btn-primary">
            </form>
        </div>
    </c:when>
    <c:otherwise>
        <div id="loginDiv" class="w-25">
            <form action="/login" method="post">
                <div class="form-group">
                    <label for="username">Enter login</label>
                    <input class="form-control form-control-lg" type="text" id="username" name="username"
                           placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="password">Enter password</label>
                    <input class="form-control form-control-lg" type="password" id="password" name="password"
                           placeholder="Enter password">
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                <input type="submit" class="btn btn-primary" value="Login">
            </form>
        </div>
    </c:otherwise>
</c:choose>
</div>

</body>
</html>
