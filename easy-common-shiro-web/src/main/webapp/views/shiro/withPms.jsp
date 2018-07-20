<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>shiro</title>
</head>
<body>

欢迎${user.userName}登录

你有权限访问该页面

<a href="<c:url value='/shiro/logout'/>"><button>退出登录</button></a>

<shiro:hasRole name="admin">
    Administer the system
</shiro:hasRole>

<shiro:hasRole name="user">
    user role
</shiro:hasRole>

</body>
</html>