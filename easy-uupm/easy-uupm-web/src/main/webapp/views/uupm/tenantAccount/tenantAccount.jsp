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
<title>统一用户授权管理</title>
<base href="<%=basePath%>">
<jsp:include page="/common/layout/script.jsp"></jsp:include>

<script type="text/javascript">
	var $json_status = [ {id:"", text:"全部", "selected": true} ];
	var $json_tenantStatus = [ {id:"", text:"全部", "selected": true} ];
	var $json_tenantType = [ {id:"", text:"全部", "selected": true} ];
	var $json_serveType = [ {id:"", text:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_tenant;
	var $dg_account;
	$(function() {
		// 初始化控件数据
		$.post('/uupm/combox/findComboByDict', 
				{'combo':'status,tenant-status,tenant-type,serve-type'}, 
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$json_status = $json_status.concat(jsonData['combo']['status'][0]['children']);
						$json_tenantStatus = $json_tenantStatus.concat(jsonData['combo']['tenant-status'][0]['children']);
						$json_tenantType = $json_tenantType.concat(jsonData['combo']['tenant-type'][0]['children']);
						$json_serveType = $json_serveType.concat(jsonData['combo']['serve-type'][0]['children']);
						// 初始化datagrid组件
						makeGrid_left();	
						makeGrid_right();
					}
				}, 'json');
		
	});
	// 初始化datagrid组件
	function makeGrid_left() {
		$dg_tenant = $('#dg_id_left');
		$dg_tenant.datagrid({
		    url:'uupm/tenant/findPage',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-$('#tb_id_left').height(),
			pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
			toolbar: '#tb_id_left',
			idField: 'tenantCode',
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
	    	onClickRow: function(rowIndex, rowData) {
	    		$('#searchbox_id_account').searchbox('setValue', '');
	    		$dg_account.datagrid('options').url='uupm/account/findPage';
	    		$dg_account.datagrid('reload', {tenantCode:rowData.tenantCode});
            },
	        columns: [[
						{field: 'tenantName', title: '租户名称', width: 200, align: 'left'},
						{field: 'tenantCode', title: '租户编号', width: 200, align: 'left'},
						{field: 'tenantType', title: '类型', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_tenantType, value);
							}	
						},
						{field: 'tenantStatus', title: '状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_tenantStatus, value);
							}	
						},
						{field: 'serveType', title: '服务方式', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_serveType, value);
							}
						}
	                   ]]
		});
		//搜索框
		$("#searchbox_id_tenant").searchbox({
			menu:"#mm_id_left",
			prompt :'请输入',
			searcher:function(value, name) {
				var obj = {};
				obj[name] = value;
				$dg_tenant.datagrid('reload', obj); 
		    }
		});
	}
	function makeGrid_right() {
		$dg_account = $('#dg_id_right');
		$dg_account.datagrid({
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-$('#tb_id_right').height(),
		    pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
			toolbar: '#tb_id_right',
			idField: 'accountId',
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
						{field: 'accountId', title: '账号ID', width: 200, align: 'left'},
						{field: 'accountStatus', title: '账号状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_status, value);
							}	
						}
	                   ]]
		});
		
		//搜索框
		$("#searchbox_id_account").searchbox({
			menu:"#mm_id_right",
			prompt :'请输入',
			searcher:function(value, name) {
				var row = $dg_tenant.datagrid('getSelected');
				if(!row) {
					$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : "请选择一行【租户】记录!"
					});
					return;
				}
				$dg_account.datagrid('options').url='uupm/account/findPage';
				var obj = {tenantCode:row.tenantCode};
				obj[name] = value;
				$dg_account.datagrid('reload', obj); 
		    }
		});
	}
	
	// 清除搜索条件
	function cleanSearch_user() {
		$('#searchbox_id_account').searchbox('setValue', '');
	}
	function cleanSearch_role() {
		$('#searchbox_id_tenant').searchbox('setValue', '');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		var row = $dg_tenant.datagrid('getSelected');
		if(!row) {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【租户】记录!"
			});
			return;
		}
		parent.$.modalDialog({
			title: "添加账号",
			width: 800,
			height: 400,
			href: 'views/uupm/tenantAccount/tenantAccountEditDlg.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="tenantName"]').val(row.tenantName);
				editForm.find('input[name="tenantCode"]').val(row.tenantCode);
				setComboForSelected(editForm);
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					parent.$.modalDialog.openWindow = $openWindow;//定义打开对话框的窗口
					parent.$.modalDialog.openner = $dg_account;//定义对话框关闭要刷新的grid
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.attr("action", "uupm/account/add");
					editForm.submit();
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
		var row = $dg_account.datagrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑账号",
				width: 600,
				height: 400,
				href: 'views/uupm/tenantAccount/tenantAccountEditDlg.jsp',
				onLoad:function(){
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.form("load", row);
					setComboForSelected(editForm);
					editForm.find("input[name='accountId']").attr('readonly',true);
					var row_tenant = $dg_tenant.datagrid('getSelected');
					editForm.find('input[name="tenantName"]').val(row_tenant.tenantName);
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						parent.$.modalDialog.openWindow = $openWindow;//定义打开对话框的窗口
						parent.$.modalDialog.openner = $dg_account;//定义对话框关闭要刷新的grid
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						editForm.attr("action", "uupm/account/edit");
						editForm.submit();
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
				timeout : commonui.msg_timeout,
				msg : "请选择一行【账号】记录!"
			});
		}
	}
	
	// 删除
	function removeFunc() {
		var row = $dg_account.datagrid('getSelected');
		if(row) {
			parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
			    if(r) {
			    	$.post("uupm/account/delById", {id:row.id}, function(result) {
						if(result.status=='OK') {
							var rowIndex = $dg_account.datagrid('getRowIndex', row);
							$dg_account.datagrid('deleteRow', rowIndex);
						}
						$.messager.show({
							title :commonui.msg_title,
							msg : result.msg,
							timeout : commonui.msg_timeout
						});
					}, "JSON").error(function() {
						$.messager.show({
							title :commonui.msg_title,
							msg : result.msg,
							timeout : commonui.msg_timeout
						});
					});
			    }  
			});
		} else {
			$.messager.show({
				title :commonui.msg_title,
				msg : "请选择一行记录!",
				timeout : commonui.msg_timeout
			});
		}
	}
	// 设置控件选中
	function setComboForSelected(selectForm) {
		selectForm.find('#accountStatus_id').combobox({
			editable:false,
			panelHeight: 120,
			valueField:'id',
		    textField:'text',
		    data: $.grep($json_status, function(n,i){
		    	if(i==1) n['selected']=true;
		    	return i > 0;	//返回true，进行选取
		    })
		});
	}	
	
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'租户列表',split:true,border:true" style="width:600px;">
    	<table id="dg_id_left"></table>
    	<div id="tb_id_left" style="display:none;padding:5px;height:auto">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_tenant" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_user();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm_id_left" class="easyui-menu" style="width:120px;">
	        <div name="tenantName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="tenantCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'账号列表'">
    	<table id="dg_id_right"></table>
    	<div id="tb_id_right" style="display:none;padding:5px;height:auto">
			<table cellpadding="0" cellspacing="0">
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
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_account" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_user();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
	
		<div id="mm_id_right" class="easyui-menu" style="width:120px;">
	        <div name="accountId">&nbsp;&nbsp;账号ID&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>
		
</body>
</html>