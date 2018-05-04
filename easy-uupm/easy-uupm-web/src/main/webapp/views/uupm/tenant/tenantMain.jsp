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
	var $json_tenantStatus = [ {id:"", text:"全部", "selected": true} ];
	var $json_tenantType = [ {id:"", text:"全部", "selected": true} ];
	var $json_serveType = [ {id:"", text:"全部", "selected": true} ];

	var $openWindow = this;// 当前窗口
	var $dg;
	$(function() {
		$dg = $('#dg_id');
		// 初始化控件数据
		$.post('/uupm/combox/findComboByDict', 
				{'combo':'tenant-status,tenant-type,serve-type'}, 
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$json_tenantStatus = $json_tenantStatus.concat(jsonData['combo']['tenant-status'][0]['children']);
						$json_tenantType = $json_tenantType.concat(jsonData['combo']['tenant-type'][0]['children']);
						$json_serveType = $json_serveType.concat(jsonData['combo']['serve-type'][0]['children']);
						// 初始化datagrid组件
						makeGrid();	
						
						$('#tenantType_id').combobox({
							editable:false,
							panelHeight: 120,
						    valueField:'id',
						    textField:'text',
						    data: $json_tenantType
						});
						$('#tenantStatus_id').combobox({
							editable:false,
							panelHeight: 120,
							valueField:'id',
						    textField:'text',
						    data: $json_tenantStatus
						});
						$('#serveType_id').combobox({
							editable:false,
							panelHeight: 80,
							valueField:'id',
						    textField:'text',
						    data: $json_serveType
						});
						
					}
				}, 'json');
		
	});
	// 初始化datagrid组件
	function makeGrid() {
		$dg.datagrid({
		    url:'uupm/tenant/findPage',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-$('.search-form-div').height(),
			pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
			toolbar: '#tb_id',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || {'total':0, 'rows':[]};
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : result.msg
					});
		    		return [];
	    		}
	    	},
	    	frozenColumns: [[
	    	                 {field: 'tenantName', title: '租户名称', width: 200, align: 'left'}
	    	                 ]],
	        columns: [[
						{field: 'tenantCode', title: '租户编号', width: 100, align: 'left'},
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
						},
						{field: 'startTime', title: '服务开始时间', width: 100, align: 'center',
							formatter: function(value, row) {
								return value?new Date(value).format("yyyy-MM-dd HH:mm:ss"):"";
							}	
						},
						{field: 'endTime', title: '服务结束时间', width: 100, align: 'center',
							formatter: function(value, row) {
								return value?new Date(value).format("yyyy-MM-dd HH:mm:ss"):"";
							}	
						}
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
		$('#tenantType_id').combobox('select','');
		$('#tenantStatus_id').combobox('select','');
		$('#serveType_id').combobox('select','');
	}
	
	// 打开添加对话框
	function openAddDlg() {
		parent.$.modalDialog({
			title: "添加",
			width: 800,
			height: 400,
			href: 'views/uupm/tenant/tenantEditDlg.jsp',
			onLoad:function(){
				var editForm = parent.$.modalDialog.handler.find("#form_id");
				editForm.find("#startTime").val(new Date().format("yyyy-MM-dd HH:mm:ss"));
				setComboForSelected(editForm);
			},
			buttons: [{
				text: '确定',
				iconCls: 'icon-ok',
				handler: function() {
					parent.$.modalDialog.openWindow = $openWindow;//定义打开对话框的窗口
					parent.$.modalDialog.openner = $dg;//定义对话框关闭要刷新的grid
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					editForm.attr("action", "uupm/tenant/add");
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
		var row = $dg.datagrid('getSelected');
		if(row) {
			parent.$.modalDialog({
				title: "编辑",
				width: 600,
				height: 400,
				href: 'views/uupm/tenant/tenantEditDlg.jsp',
				onLoad:function(){
					var editForm = parent.$.modalDialog.handler.find("#form_id");
					setComboForSelected(editForm);
					editForm.form("load", row);
					editForm.find("#tenantCode_id").attr('readonly',true);
					if(row.startTime) editForm.find("#startTime").val(new Date(row.startTime).format("yyyy-MM-dd HH:mm:ss"));
					if(row.endTime) editForm.find("#endTime").val(new Date(row.endTime).format("yyyy-MM-dd HH:mm:ss"));
				},
				buttons: [{
					text: '确定',
					iconCls: 'icon-ok',
					handler: function() {
						parent.$.modalDialog.openWindow = $openWindow;//定义打开对话框的窗口
						parent.$.modalDialog.openner = $dg;//定义对话框关闭要刷新的grid
						var editForm = parent.$.modalDialog.handler.find("#form_id");
						editForm.attr("action", "uupm/tenant/edit");
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
			    	$.post("uupm/tenant/delById", {id:row.id}, function(result) {
						if(result.status=='OK') {
							var rowIndex = $dg.datagrid('getRowIndex', row);
							$dg.datagrid('deleteRow', rowIndex);
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
		selectForm.find('#tenantType_id').combobox({
			editable:false,
			panelHeight: 120,
			valueField:'id',
		    textField:'text',
		    data: $.grep($json_tenantType, function(n,i){
		    	if(i==1) n['selected']=true;
		    	return i > 0;
		    })
		});
		selectForm.find('#tenantStatus_id').combobox({
			editable:false,
			panelHeight: 120,
			valueField:'id',
		    textField:'text',
		    data: $.grep($json_tenantStatus, function(n,i){
		    	if(i==1) n['selected']=true;
		    	return i > 0;
		    })
		});
		selectForm.find('#serveType_id').combobox({
			editable:false,
			panelHeight: 80,
			valueField:'id',
		    textField:'text',
		    data: $.grep($json_serveType, function(n,i){
		    	if(i==1) n['selected']=true;
		    	return i > 0;
		    })
		});
	}	
</script>
</head>
<body class="easyui-layout,fit:true">
	<div class="search-form-div" data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search',collapsible:true">
		<div class="badge-div-hidden" >
			<span class="badge-title">提示</span>
			<p style="margin:0px;padding:2px;">
				xxx<span class="badge-info"><strong>yyy</strong></span>，
			</p>
		</div>
		<form id="searchForm_id">
			<table class="search-form-table">
				<tr>
					<th>租户名称：</th>
					<td>
						<input name="tenantName" type="text" />
					</td>
					<th>租户编号：</th>
					<td>
						<input name="tenantCode" type="text" />
					</td>
				</tr>
				<tr>
					<th>类型：</th>
					<td>
						<input id="tenantType_id" name="tenantType" type="text" />
					</td>
					<th>状态：</th>
					<td>
						<input id="tenantStatus_id" name="tenantStatus" type="text" />
					</td>
				</tr>
				<tr>
					<th>服务方式：</th>
					<td>
						<input id="serveType_id" name="serveType" type="text" />
					</td>
					<th>服务开始时间：</th><td>
						<input type="text" name="sStartTime" id="sStartTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'eStartTime\',{d:0});}'})"/>
						~
						<input type="text" name="eStartTime" id="eStartTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'sStartTime\',{d:0});}'})"/>
					</td>
				</tr>
				<tr>
					<td colspan="3"></td>
					<td style="text-align:right;padding-right:20px;">
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="_search();">查询</a> 
						<a href="javascript:void(0);" class="easyui-linkbutton" onclick="cleanSearch();">重置</a>
					</td>
				</tr>
				
			</table>
		</form>
	</div>
	
	<div data-options="region:'center',border:false" style="overflow:hidden;">
		<table id="dg_id" title="查询列表"></table>
	</div>
	
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
		
</body>
</html>