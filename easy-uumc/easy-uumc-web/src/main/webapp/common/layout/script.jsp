<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%
	String easyuiThemeName = "metro";
	Cookie cookies[] = request.getCookies();
	if(cookies!=null && cookies.length>0) {
		for(Cookie cookie : cookies) {
			if(cookie.getName().equals("cookiesColor")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
%>

<link rel="stylesheet" type="text/css" href="<%=basePath%>static/easyui/themes/<%=easyuiThemeName %>/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/common/css/easy.css">
<link rel="stylesheet" type="text/css" href="<%=basePath%>static/common/css/icon.css">
<script type="text/javascript" src="<%=basePath%>static/jquery/jquery-1.8.0.min.js"></script>
<!-- <script type="text/javascript" src="<%=basePath%>static/easyui/jquery.min.js"></script> -->
<script type="text/javascript" src="<%=basePath%>static/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=basePath%>static/easyui/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="<%=basePath%>static/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>static/common/js/easy-easyui-ext.js"></script>
<script type="text/javascript" src="<%=basePath%>static/common/js/easy-utils.js"></script>

<style type="text/css">
	body {
	    font-family:helvetica,tahoma,verdana,sans-serif;
	    font-size:13px;
	    margin:0px 0px 0px 0px;
	    padding:0px 0px 0px 0px;
	}
</style>