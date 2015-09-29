<%-- 
    Document   : login
    Created on : Sep 17, 2015, 9:32:44 AM
    Author     : Robert
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Dit is de beveiligde pagina voor de fotograaf.</h1>
        <c:url var="logoutUrl" value="/logout_photograph"/>
        <form action="${logoutUrl}" method="post">
            <input type="submit" value="Log out" />
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </body>
</html>
