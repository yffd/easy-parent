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
						$('#userStatus_id').combobox({data: $combo_data[easyUtils.enums.status], all:true, skipValues:[], selectValues:[]});
					}
				}, 'json');
		
	});
	$.post('/uumc/common/listCombotreeData', 
			{'keyCodes':[easyUtils.enums.org].toString()}, 
			function(result) {
				if("OK"==result.status) {
					var jsonData = result.data;
					$combo_data[easyUtils.enums.org] = jsonData[easyUtils.enums.org];
				}
			}, 'json');
	
	// 初始化datagrid组件
	function makeGrid() {
		$dg = $('#dg_id');
		$dg.datagrid({
		    url:'uumc/user/listPage',
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
							 {field: 'operate', title: '操作', width: 100, align: 'center',
								 formatter: function(value, row) {
									 var text = " 激活账号 ";
									 var style = "color: green";
									 if('1'==row.userStatus) {
										 text = " 冻结账号 ";
										 style = "color: red";
									 }
									 var userCode = row.userCode;
									 var userStatus = row.userStatus;
									 var a1 = '[<a href="javascript:void(0);" onClick="updateStatus(\''+userCode+'\',\''+userStatus+'\');" width="100" style="'+style+'">'+text+'</a>]';
									 return a1 + '&nbsp;';
								 }	
							 }
	    	                 ]],
	        columns: [[
						{field: 'userName', title: '用户名称', width: 100, align: 'left'},
						{field: 'userCode', title: '用户编号', width: 100, align: 'left'},
						{field: 'userStatus', title: '用户状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return easyUtils.getText($combo_data[easyUtils.enums.status], value);
							}
						},
						{field: 'orgCode', title: '机构编号', width: 100, align: 'left'},
						{field: 'orgName', title: '机构名称', width: 100, align: 'left',
							formatter: function(value, row) {
								return easyUtils.getTextFromTree($combo_data[easyUtils.enums.org], row.orgCode);
							}
						},
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
		$('#userStatus_id').combobox('setValue', '');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		parent.$.modalDialog({
			title: "添加",
			href: 'views/uumc/user/userAdd.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="userStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntType"]').combobox({data: $combo_data[easyUtils.enums.acntType], all: false, selectValues:['user']});
				editForm.find('input[name="orgCode"]').combotree({data: $combo_data[easyUtils.enums.org], value: $combo_data[easyUtils.enums.org][0]['id']});
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/user/add', obj, function(result) {
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
			href: 'views/uumc/user/userEdit.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="userStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntStatus"]').combobox({data: $combo_data[easyUtils.enums.status], all: false});
				editForm.find('input[name="acntType"]').combobox({data: $combo_data[easyUtils.enums.acntType], all: false, selectValues:['user']});
				editForm.form("load", row);
				editForm.find("input[name='userCode']").attr('readonly',true);
				editForm.find('input[name="orgCode"]').combotree({data: $combo_data[easyUtils.enums.org], value: row.orgCode});
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/user/update', obj, function(result) {
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
		    	$.post("uumc/user/delByUserCode", {userCode: row.userCode}, function(result) {
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
		easyuiExt.showMsg('尚未实现'); return;
	}
	
	// 修改用户状态
	function updateStatus(userCode, userStatus) {
		var tmp='1';
		if('1'==userStatus) tmp='0';
		$.post("uumc/user/updateStatus", {'userCode':userCode, 'userStatus':tmp}, function(result) {
			if(result.status=='OK') {
				$dg.datagrid('reload');
			}
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : result.msg
			});
		}, "JSON");
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search'" style="overflow:hidden;">
		<div class="badge-div" >
			<span class="badge-title">提示</span>
			<p style="margin:0px;padding:2px;">
				<span class="badge-info"><strong>双击行</strong></span>查看详细信息！
			</p>
		</div>
		<div class="form-div">
			<form id="searchForm_id" >
				<table>
					<tr>
						<th>名称：</th>
						<td><input name="userName" type="text" /></td>
						<th>编号：</th>
						<td><input name="userCode" type="text" /></td>
						<th>状态：</th>
						<td><input id="userStatus_id" name="userStatus" type="text" /></td>
					</tr>
					<tr>
<!-- 						<th>创建时间：</th> -->
<!-- 						<td> -->
<!-- 							<input type="text" name="sCreateTime" id="sStartTime_id" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'eStartTime_id\',{d:0});}'})"/> -->
<!-- 							~ -->
<!-- 							<input type="text" name="eCreateTime" id="eStartTime_id" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sStartTime_id\',{d:0});}'})"/> -->
<!-- 						</td> -->
						<th>创建时间(s)：</th>
						<td><input type="text" name="sCreateTime" id="sStartTime_id" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'eStartTime_id\',{d:0});}'})"/></td>
						<th>创建时间(e)：</th>
						<td><input type="text" name="eCreateTime" id="eStartTime_id" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sStartTime_id\',{d:0});}'})"/></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th colspan="5"></th>
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