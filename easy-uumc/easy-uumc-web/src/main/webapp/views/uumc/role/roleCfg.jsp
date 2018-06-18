<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/common/taglib/taglib.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统一用户管理中心</title>
<base href="<%=basePath%>">
<jsp:include page="/common/layout/script.jsp"></jsp:include>

<script type="text/javascript">
	var $json_status = [ {id:"", text:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		$dg_left = $('#dg_id_left');
		// 初始化控件数据
		$.post('/uumc/common/listComboboxData', 
				{'keyCodes':[uidict.status].toString()},
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$json_status = $json_status.concat(jsonData[uidict.status]);
						
						makeGrid_left();	// 初始化datagrid组件
						makeGrid_right();
					}
				}, 'json');
		//搜索框
		$("#searchbox_id_left").searchbox({
			menu:"#mm_id_left",
			prompt :'请输入',
// 			height: 28,
// 			width:200,
			searcher:function(value, name) {
				var obj = {};
				obj[name] = value;
				$dg_left.datagrid('reload', obj); 
		    }
		});
		$("#searchbox_id_right").searchbox({
			menu:"#mm_id_right",
			prompt :'请输入',
// 			height: 28,
// 			width:200,
			searcher:function(value, name) {
				var obj = {};
				obj[name] = value;
				$dg_right.datagrid('reload', obj); 
		    }
		});
	});
	// 初始化组件
	function makeGrid_left() {
		$dg_left = $('#dg_id_left');
		$dg_left.datagrid({
		    url:'uumc/role/listPage',
		    width: 'auto',
		    height: 'auto',
			fit: true, rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			border: false, striped: true, singleSelect: true,
			pagination: true,
			pageSize: commonui.pageSize,
			toolbar: '#tb_id_left',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || {'total':0, 'rows':[]};
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : result.msg
					});
		    		return {'total':0, 'rows':[]};
	    		}
	    	},
	    	onDblClickRow: function(rowIndex, rowData) {
	    		findHavePmsCodes(rowIndex, rowData);
            },
	        columns: [[
						{field: 'ck', checkbox: true},
						{field: 'roleName', title: '名称', width: 200, align: 'left'},
						{field: 'roleCode', title: '编号', width: 100, align: 'left'},
						{field: 'roleStatus', title: '状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return uidict.fmtDict($json_status, value);
							}
						},
						{field: 'remark', title: '备注', width: 100, align: 'left'}
	                   ]]
		});
	}
	function makeGrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.datagrid({
		    url:'uumc/permission/listPage',
		    width: 'auto',
		    height: 'auto',
			fit: true, rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			border: false, striped: true, singleSelect: false, 
			pagination: true,
			pageSize: commonui.pageSize,
			toolbar: '#tb_id_right',
			idField: 'pmsCode',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || {'total':0, 'rows':[]};
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : result.msg
					});
		    		return {'total':0, 'rows':[]};
	    		}
	    	},
	        columns: [[
						{field: 'ck', checkbox: true},
						{field: 'pmsName', title: '权限名称', width:200,align: 'left'},
						{field: 'pmsCode', title: '权限编号', width: 200, align: 'left'},
						{field: 'appCode', title: '系统应用编号', width:200,align: 'left'},
						{field: 'rsCode', title: '系统资源编号', width: 200, align: 'left'},
	                   ]]
		});
	}
	
	// 为角色分配权限
	function saveCfg() {
		var row_left = $dg_left.datagrid('getSelected');//获取选中的行-单行
		if(!row_left) {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【角色】记录"
			});
			return;
		}
		var rows_right = $dg_right.datagrid('getSelections');//获取选中的行-多行
		var pmsCodeArr = [];
		if(rows_right) {
			$.each(rows_right, function(i, obj) {
				pmsCodeArr.push(obj['pmsCode']);
			});
		}
		parent.$.messager.confirm("提示","确定要【保存设置】吗?如果操作成功，将不可恢复。",function(r){
			if(r) {
				var roleCode = row_left.roleCode;
				var data={"roleCode":roleCode, "pmsCodes": JSON.stringify(pmsCodeArr)};
				$.post("uumc/role/saveCfg", data, function(result) {
					$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : result.msg
					});
				}, "JSON").error(function() {
					$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : "分配失败！"
					});
				});
			}
		});
	}
	//双击事件
	function findHavePmsCodes(rowIndex, rowData) {
		$.post("uumc/role/listHavePmsCodes", {'roleCode': rowData.roleCode}, function(result) {
			if(result.status=='OK') {
				$dg_right.datagrid('unselectAll');
				var data = result.data;
				if(data && data.length>0) {
					$.each(data, function(i, n) {
						$dg_right.datagrid('selectRecord', n);
					});
				} else {
					$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : "该【角色】暂无权限"
					});
				}
			} else {
				$.messager.show({
					title :commonui.msg_title,
					timeout : commonui.msg_timeout,
					msg : result.msg
				});
			}
			
		}, "json");
	}
	
	// 清除搜索条件
	function cleanSearch_left() {
		$('#searchbox_id_left').searchbox('setValue', '');
	}
	function cleanSearch_right() {
		$('#searchbox_id_right').searchbox('setValue', '');
	}
		
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search',collapsible:true" style="height:85px;">
		<div class="badge-div" >
			<span class="badge-title">提示</span>
			<p style="margin:0px;padding:2px;">
				请<span class="label-info"><strong>双击角色</strong></span>查看已授权信息！
			</p>
		</div>
	</div>
    <div data-options="region:'west',title:'角色列表',split:true,border:true" style="width:500px;">
		<table id="dg_id_left"></table>
		<div id="tb_id_left" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table>
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_left" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_left();" href="javascript:void(0);"></a>
					</td>
					<td style="padding-left:2px;padding-bottom:2px;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-config',plain:'true'" onclick="saveCfg();" href="javascript:void(0);">保存设置</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm_id_left">
			<div name="roleName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="roleCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'权限列表'" >
	    <table id="dg_id_right"></table>
	    <div id="tb_id_right" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_right" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_right();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
	    <div id="mm_id_right">
			<div name="pmsName">&nbsp;&nbsp;权限名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="pmsCode">&nbsp;&nbsp;权限编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div class="menu-sep"></div>
			<div name="appCode">&nbsp;&nbsp;系统应用编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="rsCode">&nbsp;&nbsp;系统资源编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>
		
</body>
</html>