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
	var $combo_data = {};
	var $dg;
	$(function() {
		// 初始化控件数据
		$.post('/uumc/common/listComboboxData', 
				{'keyCodes':[easyUtils.enums.status,easyUtils.enums.acntType].toString()},
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$combo_data[easyUtils.enums.status] = jsonData[easyUtils.enums.status];
						$combo_data[easyUtils.enums.acntType] = jsonData[easyUtils.enums.acntType];
						// 初始化datagrid组件
						makeGrid();
						$('#acntStatus_id').combobox({data: $combo_data[easyUtils.enums.status], all:true, skipValues:[], selectValues:[]});
						$('#acntType_id').combobox({data: $combo_data[easyUtils.enums.acntType], all:true, skipValues:[], selectValues:[]});
					}
				}, 'json');
		
	});
	
	// 初始化datagrid组件
	function makeGrid() {
		$dg = $('#dg_id');
		$dg.datagrid({
		    url:'uumc/account/listPage',
		    width: 'auto',
		    height: 'auto',
			fit: true, rownumbers: true, animate: true, collapsible: false, fitColumns: true,
			border: false, striped: true, singleSelect: true,
			pagination: true,
			pageSize: easyuiExt.pageSize,
			toolbar: '#tb_id',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || {'total':0, 'rows':[]};
		    	} else {
		    		easyuiExt.showMsg(result.msg);
		    		return {'total':0, 'rows':[]};
	    		}
	    	},
	    	onDblClickRow: function(rowIndex, rowData) {
	    		findDetail(rowIndex, rowData);
            },
            frozenColumns: [[
							 {field: 'operate', title: '操作', width: 200, align: 'center',
								 formatter: function(value, row) {
									 var text = " 激活账号 ";
									 var style = "color: green";
									 if('1'==row.acntStatus) {
										 text = " 冻结账号 ";
										 style = "color: red";
									 }
									 var acntId = row.acntId;
									 var acntStatus = row.acntStatus;
									 var a1 = '[<a href="javascript:void(0);" onClick="updateStatus(\''+acntId+'\',\''+acntStatus+'\');" width="100" style="'+style+'">'+text+'</a>]';
									 var a2 = '[<a href="javascript:void(0);" onClick="resetPwd(\''+acntId+'\');" width="100" >密码重置</a>]';
									 return a1 + '&nbsp;' + a2;
								 }	
							 }
	    	                 ]],
	        columns: [[
						{field: 'acntId', title: '账号ID', width: 100, align: 'left'},
						{field: 'acntStatus', title: '账号状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return easyUtils.getText($combo_data[easyUtils.enums.status], value);
							}
						},
						{field: 'acntType', title: '账号类型', width: 100, align: 'left',
							formatter: function(value, row) {
								return easyUtils.getText($combo_data[easyUtils.enums.acntType], value);
							}
						},
						{field: 'nickName', title: '账号昵称', width: 100, align: 'left'},
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
		$('#acntStatus_id').combobox('setValue', '');
		$('#acntType_id').combobox('setValue', '');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		parent.$.modalDialog({
			title: "添加",
			href: 'views/uumc/account/accountEdit.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="acntStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntType"]').combobox({data: $combo_data[easyUtils.enums.acntType], all: false, selectValues:['user']});
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/account/add', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.datagrid('reload');
				    	}
						easyuiExt.showMsg(result.msg);
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
		if(!row) {
			easyuiExt.showMsg('请选择一行记录!'); return;
		}
		parent.$.modalDialog({
			title: "编辑",
			href: 'views/uumc/account/accountEdit.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="acntStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntType"]').combobox({data: $combo_data[easyUtils.enums.acntType], all: false, selectValues:['user']});
				editForm.form("load", row);
				editForm.find("input[name='acntId']").attr('readonly',true);
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/account/update', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.datagrid('reload');
				    	}
						easyuiExt.showMsg(result.msg);
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
	
	// 删除
	function removeFunc() {
		var row = $dg.datagrid('getSelected');
		if(!row) {
			easyuiExt.showMsg('请选择一行记录!'); return;
		}
		parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
		    if(r) {
		    	$.post("uumc/account/delById", {id: row.id}, function(result) {
					if(result.status=='OK') {
						var rowIndex = $dg.datagrid('getRowIndex', row);
						$dg.datagrid('deleteRow', rowIndex);
					}
					easyuiExt.showMsg(result.msg);
				}, "JSON");
		    }  
		});
	}
	// 查看详细
	function findDetail(rowIndex, rowData) {
		console.info(">>>>>>>>>>>>>")
	}
	
	// 修改用户状态
	function updateStatus(acntId, acntStatus) {
		var tmp='1';
		if('1'==acntStatus) tmp='0';
		$.post("uumc/account/updateStatus", {'acntId':acntId, 'acntStatus':tmp}, function(result) {
			if(result.status=='OK') {
				$dg.datagrid('reload');
			}
			easyuiExt.showMsg(result.msg);
		}, "JSON");
	}
	// 密码重置
	function resetPwd(acntId) {
		$.post("uumc/account/resetPwd", {'acntId':acntId}, function(result) {
			if(result.status=='OK') {
				$dg.datagrid('reload');
			}
			easyuiExt.showMsg(result.msg);
		}, "JSON");
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search'" style="overflow:hidden;">
		<div class="badge-div-hidden">
			<span class="badge-title">提示</span>
			<p style="margin:0px;padding:2px;">
				<span class="badge-info"><strong>双击行</strong></span>可以查看详细信息！
			</p>
		</div>
		<div class="form-div">
			<form id="searchForm_id">
				<table>
					<tr>
						<th>账号ID：</th>
						<td><input name="acntId" type="text" /></td>
						<th>账号状态：</th>
						<td><input id="acntStatus_id" name="acntStatus" type="text" /></td>
						<th>账号类型：</th>
						<td><input id="acntType_id" name="acntType" type="text" /></td>
					</tr>
					<tr>
						<td colspan="5"></td>
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
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>