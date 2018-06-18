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
	var $openWindow = this;// 当前窗口
	var $dg;
	$(function() {
		// 初始化datagrid组件
		makeGrid();
		
	});
	// 初始化datagrid组件
	function makeGrid() {
		$dg = $('#dg_id');
		$dg.datagrid({
		    url:'uumc/sys/application/listPage',
		    width: 'auto',
		    height: 'auto',
			rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			fit: true, border: false, striped: true, singleSelect: true,
			pagination: true,
			pageSize: commonui.pageSize,
			toolbar: '#tb_id',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || {'total':0, 'rows':[]};
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						msg : result.msg,
						timeout : commonui.msg_timeout
					});
		    		return {'total':0, 'rows':[]};
	    		}
	    	},
	        columns: [[
						{field: 'appName', title: '名称', width:200,align: 'left'},
						{field: 'appCode', title: '编号', width: 100, align: 'left'},
						{field: 'appContextPath', title: '应用上下', width:200,align: 'left'},
						{field: 'appDomain', title: '域名', width: 100, align: 'left'},
						{field: 'appPort', title: '端口', width: 100, align: 'left'},
						{field: 'createTime', title: '创建时间', width: 100, align: 'center',
							formatter: function(value, row) {
								return value?new Date(value).format("yyyy-MM-dd HH:mm:ss"):"";
							}	
						},
	                   ]]
		});
	}
	// 高级查询
	function _search() {
		var params = {};
		var inputs = $('#searchForm_id input');
		if(inputs) {
			$.each(inputs, function(i, obj) {
				if(obj.name) params[obj.name] = obj.value;
			});
		}
		$dg.datagrid('reload', params);
	}
	
	// 清除搜索条件
	function cleanSearch() {
		$('#searchForm_id input').val('');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		parent.$.modalDialog({
			title: "添加",
			href: 'views/uumc/sys/application/applicationEdit.jsp',
			onLoad:function(){
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/sys/application/add', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.datagrid('reload');
				    	}
						$.messager.show({
							title :commonui.msg_title,
							timeout : commonui.msg_timeout,
							msg : result.msg
						});
					}, 'json');
				}
			},{
				text: '取消',
				iconCls: 'icon-cancel',
				handler: function() {
					parent.$.modalDialog.handler.dialog('destroy');
					parent.$.modalDialog.handler = undefined;
				}
			}]
		});
	}
	
	// 打开修改对话框
	function openEditDlg() {
		var row = $dg.datagrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑",
				href: 'views/uumc/sys/application/applicationEdit.jsp',
				onLoad:function(){
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.form("load", row);
					editForm.find("input[name='appCode']").attr('readonly', true);
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						var obj = utils.serializeObject(editForm);
						$.post('uumc/sys/application/update', obj, function(result) {
							if("OK"==result.status) {
								parent.$.modalDialog.handler.dialog('close');
								$dg.datagrid('reload');
					    	}
							$.messager.show({
								title :commonui.msg_title,
								timeout : commonui.msg_timeout,
								msg : result.msg
							});
						}, 'json');
					}
				},{
					text: '取消',
					iconCls: 'icon-cancel',
					handler: function() {
						parent.$.modalDialog.handler.dialog('destroy');
						parent.$.modalDialog.handler = undefined;
					}
				}]
			});
		} else {
			$.messager.show({
				title :commonui.msg_title,
				msg : "请选择一行记录!",
				timeout : commonui.msg_timeout
			});
		}
	}
	
	// 删除
	function removeFunc() {
		var row = $dg.datagrid('getSelected');
		if(row) {
			parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
			    if(r) {
			    	$.post("uumc/sys/application/delById", {id: row.id}, function(result) {
						if(result.status=='OK') {
							var rowIndex = $dg.datagrid('getRowIndex', row);
							$dg.datagrid('deleteRow', rowIndex);
						}
						$.messager.show({
							title :commonui.msg_title,
							timeout : commonui.msg_timeout,
							msg : result.msg
						});
					}, "JSON").error(function() {
						$.messager.show({
							title :commonui.msg_title,
							timeout : commonui.msg_timeout,
							msg : result.msg
						});
					});
			    }  
			});
		} else {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行记录!"
			});
		}
	}
</script>
</head>
<body class="easyui-layout">
	<div class="search-form-div" data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search',collapsible:true" style="overflow:hidden;">
		<div class="badge-div-hidden" >
			<span class="badge-title">提示</span>
			<p style="margin:0px;padding:2px;">
				xxx<span class="badge-info"><strong>yyy</strong></span>，
			</p>
		</div>
		<div class="form-div">
			<form id="searchForm_id">
				<table>
					<tr>
						<th>名称：</th>
						<td><input name="appName" type="text" /></td>
						<th>编号：</th>
						<td><input name="appCode" type="text" /></td>
					</tr>
					<tr>
						<td colspan="3"></td>
						<td style="text-align:right;padding-right:20px;">
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">查询</a> 
							<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">重置</a>
						</td>
					</tr>
					<tr></tr>
				</table>
			</form>
		</div>
	</div>
	
	<div data-options="region:'center',border:false" style="overflow:hidden;">
		<table id="dg_id" title="查询列表"></table>
		<div id="tb_id" style="display:none;padding:5px;height:auto">
			<table>
				<tr>
					<td style="padding-left:2px;padding-bottom:2px;">
						<shiro:hasPermission name="user-add">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="openAddDlg();" href="javascript:void(0);">添加</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="user-edit">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="openEditDlg();" href="javascript:void(0);">编辑</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="user-del">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'" onclick="removeFunc();" href="javascript:void(0);">删除</a>
						</shiro:hasPermission>
					</td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>