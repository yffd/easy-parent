<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">    
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<title>欢迎</title>
<link rel="shortcut icon" href="/favicon.ico" />
<base href="<%=basePath%>">
<jsp:include page="layout/script.jsp"></jsp:include>

<style type="text/css">
	#menuAccordion a.l-btn span span.l-btn-text {
	    display: inline-block;
	    height: 14px;
	    line-height: 14px;
	    margin: 0px 0px 0px 10px;
	    padding: 0px 0px 0px 10px;
	    vertical-align: baseline;
	    width: 128px;
	}
	#menuAccordion 	a.l-btn span span.l-btn-icon-left {
	    background-position: left center;
	    padding: 0px 0px 0px 20px;
	}
	#menuAccordion .panel-body {
		padding:5px;
	}
	#menuAccordion span:focus{
		outline: none;
	}
</style>
	
<script type="text/javascript">
$(function(){
	initMenu();
// 	initMenuLocal();
	if (utils.isLessThanIe8()) {
		$.messager.show({
			title : '警告',
			msg : '您使用的浏览器版本太低！<br/>建议您使用谷歌浏览器来获得更快的页面响应效果！',
			timeout : 1000 * 30
		});
	}
});

function initMenu(){
	var $ma = $("#menuAccordion");
	$ma.accordion({animate:true,fit:true,border:false});
	$.post("uupm/menu/findMenuTree", {}, function(result) {
		if(result.status=='OK') {
			if(result.data && result.data.length>0) {
				$.each(result.data, function(i, n) {
					var menulist ="<div class=\"badge-div\">";
		            if(n.children && n.children.length>0) {
		                $.each(n.children, function(ci, cn) {
		                	var effort=cn.text+"||"+cn.menuIcons+"||"+cn.menuUrl;
							menulist+="<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'"+cn.iconCls+"'\" onclick=\"addTab('"+effort+"');\">"+cn.text+"</a><br/>";
		                });
		            }
		            menulist+="</div>";

		    		$ma.accordion('add', {
		                title: n.text,
		                content: menulist,
		                iconCls: n.iconCls,
		                selected: false
		            });
		        });
				
				//选中第一个
		    	var panels = $ma.accordion('panels');
		    	var t = panels[0].panel('options').title;
		    	$ma.accordion('select', t);
		    	
			} else {return;}
		}
	}, "json").error(function() {
// 		$.messager.alert("提示", "获取菜单出错,请重新登陆!");
	});
}

function initMenuLocal() {
	var menuJson = [{
		"id": "15",
        "pid": "-1",
        "text": "系统资源管理",
        "children": [{
        	"id": "sys-101",
            "pid": "sys",
            "text": "系统资源管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/resource/resourceMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-102",
            "pid": "sys",
            "text": "应用系统配置管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/application/appMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-103",
            "pid": "sys",
            "text": "菜单配置管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/menu/menuMain.jsp",
            "rsType": "M"
        }]
	},{
		"id": "16",
        "pid": "-1",
        "text": "租赁信息管理",
        "children": [{
        	"id": "sys-111",
            "pid": "sys",
            "text": "租户信息管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/tenant/tenantMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-113",
            "pid": "sys",
            "text": "租户资源定制管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/tenant/tenantResourceCfg.jsp",
            "rsType": "M"
        },{
        	"id": "sys-114",
            "pid": "sys",
            "text": "租户账号管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/tenantAccount/tenantAccount.jsp",
            "rsType": "M"
        }]
	},{
		"id": "17",
        "pid": "-1",
        "text": "基础信息管理",
        "children": [{
        	"id": "sys-121",
            "pid": "sys",
            "text": "菜单管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/menu/menuMain.jsp",
            "rsType": "M"
        }, {
        	"id": "sys-122",
            "pid": "sys",
            "text": "数据字典管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/dictionary/dictionaryMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-124",
            "pid": "sys",
            "text": "登录日志信息",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/dictionary/dictionaryMain.jsp",
            "rsType": "M"
        }]
	},{
		"id": "18",
        "pid": "-1",
        "text": "用户信息管理",
        "children": [{
        	"id": "sys-181",
            "pid": "sys",
            "text": "用户管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/user/userMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-182",
            "pid": "sys",
            "text": "用户角色定制管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/user/userRoleCfg.jsp",
            "rsType": "M"
        }]
	},{
		"id": "19",
        "pid": "-1",
        "text": "角色信息管理",
        "children": [{
        	"id": "sys-191",
            "pid": "19",
            "text": "角色管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/role/roleMain.jsp",
            "rsType": "M"
        },{
        	"id": "sys-192",
            "pid": "19",
            "text": "角色资源定制管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/role/roleResourceCfg.jsp",
            "rsType": "M"
        }]
	},{
		"id": "20",
        "pid": "-1",
        "text": "组织信息管理",
        "children": [{
        	"id": "sys-211",
            "pid": "20",
            "text": "机构管理",
            "state": "open",
            "iconCls": "icon-sys",
            "inUrl": "views/uupm/organization/organizationMain.jsp",
            "rsType": "M"
        }]
	}];
	var $ma = $("#menuAccordion");
	$ma.accordion({animate:true,fit:true,border:false});
	$.each(menuJson, function(i, n) {
		var menulist ="<div class=\"badge-div\">";
        if(n.children && n.children.length>0) {
            $.each(n.children, function(ci, cn) {
            	var effort=cn.text+"||"+cn.iconCls+"||"+cn.inUrl;
				menulist+="<a href=\"javascript:void(0);\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'"+cn.iconCls+"'\" onclick=\"addTab('"+effort+"');\">"+cn.text+"</a><br/>";
            });
        }
        menulist+="</div>";

		$ma.accordion('add', {
            title: n.text,
            content: menulist,
            iconCls: n.iconCls,
            selected: false
        });
    });
	
	//选中第一个
	var panels = $ma.accordion('panels');
	var t = panels[0].panel('options').title;
	$ma.accordion('select', t);
}

</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div style="position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="<%=basePath%>static/images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
	
	<div data-options="region:'north',border:false" style="overflow:hidden;height:40px;padding:10px;" href="common/layout/north.jsp">
    </div>
    
    <div data-options="region:'west',split:true,title:'导航菜单'" style="width:200px;">
		<div id="menuAccordion">
		<!--  导航内容 -->
		</div>
    </div>
    
    <div data-options="region:'south',split:true" style="height:30px;padding:2px;" href="common/layout/south.jsp">
    </div>
    
    
    <div data-options="region:'center',plain:true,title:'欢迎使用'" style="overflow:hidden;" href="common/layout/center.jsp">
    </div>
    
</body>
</html>