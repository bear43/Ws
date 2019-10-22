<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Login</title>
    <link rel="stylesheet" href=<c:url value="/css/bootstrap.min.css"/>>
    <link rel="stylesheet" href=<c:url value="/css/bodymargin.css"/>>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script src=<c:url value="/js/bootstrap.min.js"/>></script>
</head>
<body>
<nav class="navbar navbar-dark bg-dark navbar-expand-lg">
    <div class="navbar-header">
        <a class="navbar-brand" href="${pageContext.servletContext.contextPath}/"><spring:message code="site.title"/></a>
    </div>
    <div class="navbar-collapse">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item"><a class="nav-link" href="${pageContext.servletContext.contextPath}/registration"><spring:message code="login.button.registration"/></a></li>
        </ul>
    </div>
</nav>
<div class="container">
    <c:if test="${message != null}">
        <p class="text-info">${message}</p>
    </c:if>
    <c:if test="${param.error != null}">
        <p class="text-danger"><spring:message code="login.wrong"/></p>
    </c:if>
        <c:if test="${param.logout != null}">
            <p class="text-success"><spring:message code="login.logout"/></p>
        </c:if>
    <form action="${pageContext.servletContext.contextPath}/login" method="post">
        <div class="form-group">
            <input class="form-control" type="text" name="username" required placeholder=<spring:message code="login.field.login"/>>
        </div>
        <div class="form-group">
            <input class="form-control" type="password" name="password" required placeholder=<spring:message code="login.field.password"/>>
        </div>
        <div class="form-group text-center">
            <input class="btn btn-primary" type="submit" value=<spring:message code="login.button.signin"/>>
        </div>
    </form>
</div>
</body>
</html>
