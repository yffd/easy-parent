<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>shiro</title>
</head>
<body>
	
	<h4>List Page</h4>
<%-- 	欢迎${user.userName}登录 --%>
<%-- 	Welcome: <shiro:principal></shiro:principal> --%>
	
	<shiro:hasRole name="admin">
	<br><br>
	<a href="/views/shiro/admin.jsp">Admin Page</a>
	</shiro:hasRole>
	
	<shiro:hasRole name="user">
	<br><br>
	<a href="/views/shiro/user.jsp">User Page</a>
	</shiro:hasRole>
	
	<br><br>
	<a href="/shiro/testShiroAnnotation">Test ShiroAnnotation</a>
	
	<br><br>
	<a href="/shiro/logout">Logout</a>
	
</body>
</html>