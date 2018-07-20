<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>shiro</title>
</head>
<body>
	
	<h4>Login Page</h4>
	
	<form action="/shiro/login" method="POST">
		账户: <input type="text" name="username"/>
		<br><br>
		
		密码: <input type="password" name="password"/>
		<br><br>
		
		验证码: <input name="captchaCode" type="text" class="captcha"/>
		<img id="Kaptcha" style="width:85px;height:35px;margin-top: -10px;" src="<%=basePath %>Kaptcha.jpg" title="点击更换验证码"/>
		<br><br>
		
		<input type="submit" value="Submit"/>
	</form>
	<dir>
		${msg}
	</dir>
	
</body>
</html>