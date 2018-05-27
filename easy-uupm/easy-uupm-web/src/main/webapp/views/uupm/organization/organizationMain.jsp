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
	var $dg;
	$(function() {
		makeGrid();	// 初始化datagrid组件
	});
	// 初始化datagrid组件
	function makeGrid() {
		$dg = $('#dg_id');
		$dg.treegrid({
			url: 'uupm/organization/listTree',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20-$('#tb_id').height(),
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
			toolbar: '#tb_id',
			idField: 'id',
			treeField: 'orgName',
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
						{field: 'treeId', title: '树ID', width: 100, align: 'left'},
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
			href: 'views/uupm/organization/organizationEdit.jsp',
			onLoad:function() {
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				if(row) {
					editForm.find('input[name="treeId"]').val(row.treeId);
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
					var actionUrl = 'uupm/organization/addRoot';
					if(row) actionUrl = 'uupm/organization/add';
					$.post(actionUrl, obj, function(result) {
						if("OK"==result.status) {
							parent.$.modalDialog.handler.dialog('close');
							$dg.treegrid('reload');
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
		var row = $dg.treegrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑",
				width: 800,
				height: 400,
				href: 'views/uupm/organization/organizationEdit.jsp',
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
						$.post('uupm/organization/update', obj, function(result) {
							if("OK"==result.status) {
								parent.$.modalDialog.handler.dialog('close');
								$dg.treegrid('reload');
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
		var row = $dg.treegrid('getSelected');
		if(row) {
			parent.$.messager.confirm("提示","确定要删除记录吗?",function(r){  
			    if(r) {
			    	var actionUrl = 'uupm/organization/delTree';
			    	var params = {};
			    	if(row.parentCode=='root') {
			    		params['treeId'] = row.treeId;
			    	} else {
			    		actionUrl = 'uupm/organization/delByIds';
			    		var arr_id = iterNode(row);
				    	var ids = arr_id.join(",");
				    	params['ids'] = ids;
			    	}
			    	$.post(actionUrl, params, function(result) {
						if(result.status=='OK') {
							$dg.treegrid('remove', row.id);
						}
						$.messager.show({
							title :commonui.msg_title,
							msg : result.msg,
							timeout : commonui.msg_timeout
						});
					}, "json");
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
    </div>
		
</body>
</html>