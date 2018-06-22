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
		makeGrid();	// 初始化datagrid组件
	});
	// 初始化datagrid组件
	function makeGrid() {
		$dg = $('#dg_id');
		$dg.treegrid({
			url: 'uumc/organization/listTree',
		    width: 'auto',
		    height: 'auto',
			fit: true, rownumbers: true, animate: true, collapsible: false, fitColumns: true,
			border: false, striped: true, singleSelect: true,
			toolbar: '#tb_id',
			idField: 'id',
			treeField: 'orgName',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || [];
		    	} else {
		    		easyuiExt.showMsg(result.msg);
		    		return [];
	    		}
	    	},
	    	onContextMenu: function(e, node){
				e.preventDefault();
				$dg.treegrid('select', node.id);
				$('#mm_id').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			},
	    	frozenColumns: [[
	    	                 {field: 'orgName', title: '名称', width: 200, align: 'left'}
	    	                 ]],
	        columns: [[
						{field: 'orgCode', title: '编号', width: 100, align: 'left'},
						{field: 'parentCode', title: '父编号', width: 100, align: 'left'},
						{field: 'parentName', title: '父名称', width: 100, align: 'left'},
						{field: 'seqNo', title: '类型', width: 100, align: 'left'}
	                   ]]
		});
	}
	
	// 清除搜索条件
	function cleanSearch() {
		$('#searchbox_id').searchbox('setValue', '');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		var row = $dg.treegrid('getSelected');
		parent.$.modalDialog({
			title: "添加",
			width: 800,
			height: 400,
			href: 'views/uumc/organization/organizationEdit.jsp',
			onLoad:function() {
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				if(row) {
					editForm.find('input[name="parentCode"]').val(row.orgCode);
					editForm.find('input[name="parentName"]').val(row.orgName);
				} else {
					editForm.find('input[name="parentCode"]').val("root");
					editForm.find('input[name="parentName"]').val("默认");
				}
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/organization/add', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.treegrid('reload');
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
		var row = $dg.treegrid('getSelected');
		if(!row) {
			easyuiExt.showMsg('请选择一行记录!'); return;
		}
		parent.$.modalDialog({
			title: "编辑",
			width: 800,
			height: 400,
			href: 'views/uumc/organization/organizationEdit.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.form("load", row);
				editForm.find('input[name="orgCode"]').attr('readonly',true);
				if(!row.parentName) editForm.find('input[name="parentName"]').val("默认");
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/organization/update', obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.treegrid('reload');
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
		var row = $dg.treegrid('getSelected');
		if(!row) {
			easyuiExt.showMsg('请选择一行记录!');
			return;
		}
		parent.$.messager.confirm("提示","确定要删除该记录吗？如果该节点有子节点，将会级联删除其子节点。",function(r){  
		    if(r) {
		    	var idArr = iterNode(row, 'id');
		    	$.post("uumc/organization/delByIds", {'ids': JSON.stringify(idArr)}, function(result) {
					if(result.status=='OK') {
						$dg.treegrid('remove', row.id);
					}
					easyuiExt.showMsg(result.msg);
				}, "json");
		    }  
		});
	}
	// 递归获取tree的propName
	function iterNode(pNode, propName) {
		var arrs = [];
		if(pNode) {
			arrs.push(pNode[propName]);
			if(pNode.children) {
				$.each(pNode.children, function(i, obj){
					arrs = arrs.concat(iterNode(obj, propName));
				});
			}
		}
		return arrs;
	}
	
	// 展开
	function expandAll() {
		var node = $dg.treegrid('getSelected');
		if(node) {
			$dg.treegrid('expandAll', node.id);
		} else {
			$dg.treegrid('expandAll');
		}
	}
	// 收缩
	function collapseAll() {
		var node = $dg.treegrid('getSelected');
		if(node) {
			$dg.treegrid('collapseAll', node.id);
		} else {
			$dg.treegrid('collapseAll');
		}
	}
		
</script>
</head>
<body class="easyui-layout">

    <div data-options="region:'center',title:'机构列表'" style="padding:5px;">
	    <table id="dg_id"></table>
		<div id="tb_id" style="border-bottom:1px solid #cccccc;">
			<shiro:hasPermission name="org-add">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:'true'" onclick="openAddDlg();" href="javascript:void(0);">添加</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-edit">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:'true'" onclick="openEditDlg();" href="javascript:void(0);">编辑</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="org-del">
			<a class="easyui-linkbutton" data-options="iconCls:'icon-remove',plain:'true'" onclick="removeFunc();" href="javascript:void(0);">删除</a>
			</shiro:hasPermission>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="expandAll();">展开</a>
			<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-redo" plain="true" onclick="collapseAll();">收缩</a>
		</div>
		<div id="mm_id" class="easyui-menu" style="width:120px;">
			<div onclick="openEditDlg()" data-options="iconCls:'icon-edit'">编辑</div>
			<div onclick="removeFunc()" data-options="iconCls:'icon-remove'">删除</div>
			<div class="menu-sep"></div>
	        <div onclick="expandAll()" data-options="iconCls:'icon-undo'">展开</div>
	        <div onclick="collapseAll()" data-options="iconCls:'icon-redo'">收缩</div>
		</div>
    </div>
		
</body>
</html>