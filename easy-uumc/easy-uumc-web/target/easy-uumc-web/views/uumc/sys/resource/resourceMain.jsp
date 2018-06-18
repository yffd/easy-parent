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
	var $arr_rsType = [ {value:"", label:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		// 初始化控件数据
		$.post('/uumc/common/listComboboxData', 
				{'keyCodes':[uidict.rsType].toString()}, 
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$arr_rsType = $arr_rsType.concat(jsonData[uidict.rsType]);
						// 初始化datagrid组件
						makeGrid_left();
						makeGrid_right();
					}
				}, 'json');
		
		//搜索框
		$("#searchbox_id_left").searchbox({
			menu:"#mm_id_left",
			prompt :'请输入',
			searcher:function(value, name) {
				var obj = {};
				obj[name] = value;
				$dg_left.datagrid('reload', obj); 
		    }
		});
	});
	// 初始化datagrid组件
	function makeGrid_left() {
		$dg_left = $('#dg_id_left');
		$dg_left.datagrid({
			url:'uumc/sys/application/listPage',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
		    fit:true, rownumbers: true, animate: true, collapsible: true, fitColumns: true, 
		    border: false, striped: true, singleSelect: true, showHeader: true,
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
			onClickRow: function(rowIndex, rowData) {
				$dg_right.treegrid('options').url='uumc/sys/resource/listTree';
	    		$dg_right.treegrid('loadData', {'status':'OK',data:[]});
	    		$dg_right.treegrid('reload', {'appCode':rowData.appCode});
			},
			columns: [[{field: 'appName', title: '名称', width:200},
			           {field: 'appCode', title: '编号', width:200}
			]]
		});
	}
	function makeGrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.treegrid({
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
		    fit:true, rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			fit:true, border: false, striped: true, singleSelect: true, showHeader: true,
			toolbar: '#tb_id_right',
			idField: 'id',
			treeField: 'rsName',
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
	                   	{field: 'rsName', title: '名称', width:200,align: 'left'},
						{field: 'rsCode', title: '编号', width: 200, align: 'left'},
						{field: 'rsType', title: '类型', width: 100, align: 'left',
							formatter: function(value, row) {
								return uidict.fmtDict($arr_rsType, value);
							}	
						},
						{field: 'seqNo', title: '序号', width: 100, align: 'left'},
						{field: 'shortUrl', title: '短链接', width: 200, align: 'left'}
	                   ]]
		});
	}
	
	/************************************************/
	// 打开添加对话框
	function openAddDlg_right() {
		var row_left = $dg_left.datagrid('getSelected');
		var row_right = $dg_right.treegrid('getSelected');
		if(!row_left) {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【系统应用】记录!"
			});
			return;
		}
		parent.$.modalDialog({
			title: "添加",
			width: 800,
			height: 400,
			href: 'views/uumc/sys/resource/resourceEdit.jsp',
			onLoad:function() {
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				$.initCombobox(editForm.find('input[name="rsType"]'), {data: $arr_rsType, skipValues:[""], selectValues:[]});
				editForm.find('input[name="appCode"]').val(row_left.appCode);
				editForm.find('input[name="appName"]').val(row_left.appName);
				if(row_right) {
					editForm.find('input[name="parentCode"]').val(row_right.rsCode);
					editForm.find('input[name="parentName"]').val(row_right.rsName);
				} else {
					editForm.find('input[name="parentCode"]').val(row_left.appCode);
					editForm.find('input[name="parentName"]').val(row_left.appName);
				}
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/sys/resource/add', obj, function(result) {
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
	}
	
	// 打开修改对话框
	function openEditDlg_right() {
		var row_left = $dg_left.datagrid('getSelected');
		var row_right = $dg_right.treegrid('getSelected');
		if(!row_left || !row_right) {
			$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【系统应用】和【系统资源】记录!"
			});
			return;
		}
		parent.$.modalDialog({
			title: "编辑",
			width: 800,
			height: 400,
			href: 'views/uumc/sys/resource/resourceEdit.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				$.initCombobox(editForm.find('input[name="rsType"]'), {data: $arr_rsType, skipValues:[""], selectValues:[]});
				editForm.form("load", row_right);
				editForm.find('input[name="appCode"]').val(row_left.appCode);
				editForm.find('input[name="appName"]').val(row_left.appName);
				editForm.find('input[name="rsCode"]').attr('readonly', true);
				var parentName = editForm.find('input[name="parentName"]');
				if(!parentName.val()) parentName.val(row_left.appName);
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					var obj = utils.serializeObject(editForm);
					$.post('uumc/sys/resource/update', obj, function(result) {
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
	}
	
	// 删除
	function removeFunc_right() {
		var row = $dg_right.treegrid('getSelected');
		if(!row) {
			$.messager.show({
				title :commonui.msg_title,
				msg : "请选择一行【资源】记录!",
				timeout : commonui.msg_timeout
			});
			return;
		}
		parent.$.messager.confirm("提示","确定要删除该记录吗？如果该节点有子节点，将会级联删除其子节点。",function(r){  
		    if(r) {
		    	var idArr = iterNode(row, 'id');
		    	$.post("uumc/sys/resource/delByIds", {'ids': JSON.stringify(idArr)}, function(result) {
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
	// 清除搜索条件
	function cleanSearch_left() {
		$('#searchbox_id_left').searchbox('setValue', '');
	}
	
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'系统应用列表',split:true,border:true" style="width:400px;">
		<table id="dg_id_left"></table>
		<div id="tb_id_left" style="display:none;padding:5px;height:auto">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_left" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_left();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm_id_left" class="easyui-menu" style="width:120px;">
	        <div name="appName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="appCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'系统资源列表'" style="padding:5px;">
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