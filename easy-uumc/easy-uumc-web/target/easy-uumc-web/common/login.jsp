<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	/*response.setHeader("Pragma","No-cache"); 
	response.setHeader("Cache-Control","no-cache"); 
	response.setDateHeader("Expires", 0); 
	response.flushBuffer();*/
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎登陆</title>
<link rel="shortcut icon" href="/favicon.ico" />
<base href="<%=basePath%>">
<link rel="stylesheet" type="text/css" href="static/css/login.css">
<script type="text/javascript" src="static/js/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="static/js/jquery-jrumble.js"></script>
<script type="text/javascript" src="static/js/easy.common.login.js"></script>
<script type="text/javascript">
$(function() {
	$('#login').show().animate({
		opacity : 1
	}, 2000);
	$('.toplogo').show().animate({
		opacity : 1,
		top : '32%'
	}, 800, function() {
		$('.toplogo').show().delay(1200).animate({
			opacity : 1,
			top : '1%'
		}, 300, function() {
			$('.formLogin').animate({
				opacity : 1,
				left : '0'
			}, 300);
		});
	});
	//点击消息关闭提示
	$('#alertMessage').click(function() {
		hideTop();
	});
	// 重置
	$('#btn_reset').click(function(e) {
		$("input[name='userCode']").val("");
		$("input[name='userPwd']").val("");
		$("input[name='captchaCode']").val("");
	});
	// 点击登录
	$('#btn_login').click(function(e) {
		submitForm();
	});
	// 回车登录
	$(document).keydown(function(e){
		if(e.keyCode == 13) {
			submitForm();
		}
	});
	// 获取验证码
	$("#Kaptcha").click(function() {
		$(this).attr("src", '<%=basePath %>Kaptcha.jpg?' + new Date().getTime());
	});
});
</script>
</head>
<body>
	<div id="alertMessage"></div>
	<div id="successLogin"></div>
	<div class="text_success">
		<img src="static/images/loader_green.gif" alt="Please wait" /> <span>登陆成功!请稍后....</span>
	</div>
	<div id="login">
		<div class="logo" style="background-image:url(static/images/typelogin.png);"></div>
		<div class="inner">
			<div class="toplogo">
				<img src="static/images/toplogo-jeecg.png" />
			</div>
			<div class="formLogin">
				<form id="formLogin" action="login/" method="post">
					<div>
						<input name="username" type="text"/>
					</div>
					<div>
						<input name="password" type="password"/>
					</div>
					<div style="margin-left: 88px;">
						<input name="captchaCode" type="text" class="captcha"/>
						<img id="Kaptcha" style="width:85px;height:35px;margin-top: -10px;" src="<%=basePath %>Kaptcha.jpg" title="点击更换验证码"/>
					</div>
					<div class="loginButton">
<!-- 						<div style="float: left; margin-left: -9px;"> -->
<!-- 							<span style="display:block;font-size:10px;padding:4px 0;color:#a3a3a3;height:18px;"> -->
<!-- 								<input name="remember" type="checkbox" style="width:18px;height:18px"/>是否记住用户名? -->
<!-- 							</span> -->
<!-- 						</div> -->
						<div style="float: right; padding: 3px 0; margin-right: -12px;">
							<div>
								<ul class="uibutton-group">
									<li><a id="btn_login" class="uibutton normal" href="javascript:void(0);">登陆</a></li>
									<li><a id="btn_reset" class="uibutton normal" href="javascript:void(0);">重置</a></li>
								</ul>
							</div>
						</div>
						<div class="clear"></div>
					</div>
				</form>
			</div>
		</div>
		<div class="shadow"></div>
	</div>
	<div class="clear"></div>
	<div id="bottomBar">
		<div style="text-align: center;font-size: 10px;color: #CCC;">
			Copyright &copy; 版权所有 
			<span><a style="color: #A31F1A;text-decoration: none" href="javascript:void(0);" title="sysErp">yffd</a></span>
		</div>
	</div>
	
</body>
</html>