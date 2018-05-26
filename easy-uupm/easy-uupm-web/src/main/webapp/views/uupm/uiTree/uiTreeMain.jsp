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
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		// 初始化datagrid组件
		makeGrid_left();
		makeGrid_right();
	});
	
	function makeGrid_left() {
		$dg_left = $('#dg_id_left');
		$dg_left.datagrid({
			url:'uupm/ui/tree/listRoot',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
		    fit:true,
			pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
			showHeader: true,
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
	    	onClickRow: function(rowIndex, rowData) {
				$dg_right.treegrid('options').url='uupm/ui/tree/listTree';
				$dg_right.treegrid('loadData', {'status':'OK',data:[]});
	    		$dg_right.treegrid('reload', {'treeId':rowData.treeId});
			},
			columns: [[{field: 'dataName', title: '名称', width:200},
			           {field: 'dataCode', title: '编号', width:200}
			]]
		});
	}
	function makeGrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.treegrid({
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
		    fit:true,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			fit:true,
			border: false,
			striped: true,
			singleSelect: true,
			showHeader: true,
			toolbar: '#tb_id_right',
			idField: 'id',
			treeField: 'dataName',
		    loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || [];
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : result.msg
					});
		    		return [];
	    		}
	    	},
	    	onContextMenu: function(e, node){
				e.preventDefault();
				$dg_right.treegrid('select', node.id);
				$('#mm_right').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			},
	        columns: [[
						{field: 'dataName', title: '名称', width:100,align: 'left'},
						{field: 'dataCode', title: '编号', width: 100, align: 'left'},
						{field: 'treeId', title: '树ID', width: 100, align: 'left'},
						{field: 'seqNo', title: '序号', width: 100, align: 'left'}
	                   ]]
		});
	}
	
	/************************************************/
	// 打开添加对话框
	function openAddDlg() {
		parent.$.modalDialog({
			title: "添加",
			width: 800,
			height: 400,
			href: 'views/uupm/uiTree/uiTreeEdit.jsp',
			onLoad:function() {
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find('input[name="parentCode"]').val("root");
				editForm.find('input[name="parentName"]').val("默认");
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uupm/ui/tree/addRoot', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg_left.datagrid('reload');
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
		var row = $dg_left.datagrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑",
				width: 800,
				height: 400,
				href: 'views/uupm/uiTree/uiTreeEdit.jsp',
				onLoad:function(){
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.form("load", row);
					editForm.find('input[name="dataCode"]').attr('readonly',true);
					editForm.find('input[name="parentName"]').val("默认");
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						var obj = utils.serializeObject(editForm);
						$.post('uupm/ui/tree/update', obj, function(result) {
							if("OK"==result.status) {
								parent.$.modalDialog.handler.dialog('close');
								$dg_left.datagrid('reload');
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
				timeout : commonui.msg_timeout,
				msg : "请选择一行【控件树】记录!"
			});
		}
	}
	
	// 删除
	function removeFunc() {
		var row = $dg_left.datagrid('getSelected');
		if(row) {
			parent.$.messager.confirm("提示","确定要删除该记录吗？如果该节点有子节点，将会级联删除其子节点。",function(r){  
			    if(r) {
			    	$.post("uupm/ui/tree/delTree", {'treeId': row.treeId}, function(result) {
						if(result.status=='OK') {
							var rowIndex = $dg_left.datagrid('getRowIndex', row);
							$dg_left.datagrid('deleteRow', rowIndex);
							$dg_right.treegrid('reload', {});
						}
						$.messager.show({
							title :commonui.msg_title,
							timeout : commonui.msg_timeout,
							msg : result.msg
						});
					}, "json");
			    }  
			});
		} else {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【控件树】记录!"
			});
		}
	}
	
	/*********************************************************/
	
	// 打开添加对话框
	function openAddDlg_right() {
		var row_left = $dg_left.datagrid('getSelected');
		var row_right = $dg_right.treegrid('getSelected');
		var row = row_right || row_left;
		if(row) {
			parent.$.modalDialog({
				title: "添加",
				width: 800,
				height: 400,
				href: 'views/uupm/uiTree/uiTreeEdit.jsp',
				onLoad:function() {
					if(row) {
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						var parentCode=editForm.find('input[name="parentCode"]');
						parentCode.val(row.dataCode);
						parentCode.attr('readonly',true);
						editForm.find('input[name="treeId"]').val(row.treeId);
						editForm.find('input[name="parentCode"]').val(row.dataCode);
						editForm.find('input[name="parentName"]').val(row.dataName);
					}
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						var obj = utils.serializeObject(editForm);
						$.post('uupm/ui/tree/add', obj, function(result) {
							if("OK"==result.status) {
								parent.$.modalDialog.handler.dialog('close');
								$dg_right.treegrid('reload');
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
				timeout : commonui.msg_timeout,
				msg : "请选择一行【控件树】或【树列表】记录!"
			});
		}
	}
	
	// 打开修改对话框
	function openEditDlg_right() {
		var row_left = $dg_left.datagrid('getSelected');
		var row = $dg_right.treegrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑",
				width: 800,
				height: 400,
				href: 'views/uupm/uiTree/uiTreeEdit.jsp',
				onLoad:function(){
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.form("load", row);
					var parentName = editForm.find('input[name="parentName"]');
					if(!parentName.val()) parentName.val(row_left.dataName);
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						var obj = utils.serializeObject(editForm);
						$.post('uupm/ui/tree/update', obj, function(result) {
							if("OK"==result.status) {
								parent.$.modalDialog.handler.dialog('close');
								$dg_right.treegrid('reload');
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
				timeout : commonui.msg_timeout,
				msg : "请选择一行【树列表】记录!"
			});
		}
	}
	
	// 删除
	function removeFunc_right() {
		var row = $dg_right.treegrid('getSelected');
		if(row) {
			parent.$.messager.confirm("提示","确定要删除该记录吗？如果该节点有子节点，将会级联删除其子节点。",function(r){  
			    if(r) {
			    	var arr_id = iterNode(row);
			    	var ids = arr_id.join(",");
			    	$.post("uupm/ui/tree/delByIds", {'ids': ids}, function(result) {
						if(result.status=='OK') {
							$dg_right.treegrid('remove', row.id);
						}
						$.messager.show({
							title :commonui.msg_title,
							timeout : commonui.msg_timeout,
							msg : result.msg
						});
					}, "json");
			    }  
			});
		} else {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【树列表】记录!"
			});
		}
	}
	
	// 递归获取tree的id
	function iterNode(pNode) {
		var arrs = [];
		if(pNode) {
			arrs.push(pNode.id);
			if(pNode.children) {
				$.each(pNode.children, function(i, obj){
					arrs = arrs.concat(iterNode(obj));
				});
			}
		}
		return arrs;
	}
	// 展开
	function expandAll_right() {
		var node = $dg_right.treegrid('getSelected');
		if(node) {
			$dg_right.treegrid('expandAll', node.id);
		} else {
			$dg_right.treegrid('expandAll');
		}
	}
	// 收缩
	function collapseAll_right() {
		var node = $dg_right.treegrid('getSelected');
		if(node) {
			$dg_right.treegrid('collapseAll', node.id);
		} else {
			$dg_right.treegrid('collapseAll');
		}
	}
	// 刷新
	function reloadFunc_right() {
		$dg_right.treegrid('reload');
	}
	
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'控件树',split:true,border:true" style="width:400px;">
		<table id="dg_id_left"></table>
		<div id="tb_id_left">
			<shiro:hasPermission name="org-add">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="openAddDlg();" href="javascript:void(0);">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-edit">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="openEditDlg();" href="javascript:void(0);">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-del">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'" onclick="removeFunc();" href="javascript:void(0);">删除</a>
			</shiro:hasPermission>
		</div>
		
    </div>

    <div data-options="region:'center',title:'树列表'" style="padding:5px;">
	    <table id="dg_id_right"></table>
		<div id="tb_id_right" style="border-bottom:1px solid #cccccc;">
			<shiro:hasPermission name="org-add">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="openAddDlg_right();" href="javascript:void(0);">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-edit">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="openEditDlg_right();" href="javascript:void(0);">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-del">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'" onclick="removeFunc_right();" href="javascript:void(0);">删除</a>
			</shiro:hasPermission>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="expandAll_right();">展开</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="collapseAll_right();">收缩</a>
			<a class="easyui-linkbutton" data-options="iconCls:'icon-reload',plain:'true'" onclick="reloadFunc_right();" href="javascript:void(0);">刷新</a>
		</div>
		
		<div id="mm_right" class="easyui-menu" style="width:120px;">
			<div onclick="openEditDlg_right()" data-options="iconCls:'icon-edit'">编辑</div>
			<div onclick="removeFunc_right()" data-options="iconCls:'icon-remove'">删除</div>
			<div class="menu-sep"></div>
	        <div onclick="expandAll_right()" data-options="iconCls:'icon-undo'">展开</div>
	        <div onclick="collapseAll_right()" data-options="iconCls:'icon-redo'">收缩</div>
		</div>
    </div>
		
</body>
</html>