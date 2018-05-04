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
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		// 初始化控件数据
		$.post('/uupm/combox/findComboByDict', 
				{'combo':'status'}, 
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$json_status = $json_status.concat(jsonData['combo']['status'][0]['children']);
						// 初始化datagrid组件
						makeGrid_left();	
						makeGrid_right();
					}
				}, 'json');
		
	});
	// 初始化datagrid组件
	function makeGrid_left() {
		$dg_left = $('#dg_id_left');
		$dg_left.datagrid({
		    url:'uupm/user/findPage',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20-$('#tb_id_left').height(),
		    pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: true,
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
	    		findRoleByUser(rowIndex, rowData);
            },
	        columns: [[
						{field: 'userName', title: '名称', width: 100, align: 'left'},
						{field: 'userCode', title: '编号', width: 100, align: 'left'},
						{field: 'orgName', title: '机构名称', width: 100, align: 'left'},
						{field: 'accountStatus', title: '账户状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_status, value);
							}	
						}
	                   ]]
		});
		
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
	}
	function makeGrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.datagrid({
		    url:'uupm/role/findPage',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20-$('#tb_id_right').height(),
			pagination: true,
			pageSize: commonui.pageSize,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: false,
			cascadeCheck: true,
			idField: 'roleCode',
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
						{field: 'roleName', title: '名称', width: 200, align: 'left'},
						{field: 'roleCode', title: '编号', width: 100, align: 'left'},
						{field: 'roleStatus', title: '状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_status, value);
							}
						},
						{field: 'remark', title: '备注', width: 100, align: 'left'}
	                   ]]
		});
		//搜索框
		$("#searchbox_id_rirht").searchbox({
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
	}
	
	// 为用户分配角色
	function saveUserRole() {
		var rows_role = $dg_right.datagrid('getSelections');//获取选中的行-多行
		var roleCodeArr = [];
		if(rows_role) {
			$.each(rows_role, function(i, obj) {
				roleCodeArr.push({'roleCode': obj['roleCode']});
			});
		}
		if(roleCodeArr.length==0) {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择角色"
			});
			return;
		}
		var row_user = $dg_left.datagrid('getSelected');//获取选中的行-单行
		if(row_user) {
			var tenantCode = row_user.tenantCode;
			var userCode = row_user.userCode;
			var data={"tenantCode": tenantCode, "userCode":userCode, "roleCodes": JSON.stringify(roleCodeArr)};
			$.post("uupm/user/role/saveUserRole", data, function(result) {
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
		} else {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择用户"
			});
		}
	}
	//双击事件
	function findRoleByUser(rowIndex, rowData) {
		$.post("uupm/user/role/findRoleByUserCode", {'tenantCode': rowData.tenantCode, userCode:rowData.userCode}, function(result) {
			if(result.status=='OK') {
				$dg_right.datagrid('unselectAll');
				var data = result.data;
				if(data.length>0) {
					$.each(data, function(i, n) {
						$dg_right.datagrid('selectRecord', n.roleCode);
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
	function cleanSearch_user() {
		$('#searchbox_id_left').searchbox('setValue', '');
	}
	function cleanSearch_role() {
		$('#searchbox_id_rirht').searchbox('setValue', '');
	}
		
</script>
</head>
<body class="easyui-layout">
<!-- 	<div class="search-form-div" data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search',collapsible:true"> -->
<!-- 		<div class="badge-div-hidden" > -->
<!-- 			<span class="badge-title">提示</span> -->
<!-- 			<p style="margin:0px;padding:2px;"> -->
<!-- 				请<span class="label-info"><strong>双击角色</strong></span>查看所属资源！ -->
<!-- 				超级管理员默认拥有<span class="label-info"><strong>所有资源！</strong></span> -->
<!-- 			</p> -->
<!-- 		</div> -->
<!-- 	</div> -->
    <div data-options="region:'west',title:'用户列表',split:true,border:true" style="width:500px;">
    	<div id="tb_id_left" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px;padding-bottom:2px;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-config',plain:'true'" onclick="saveUserRole();" href="javascript:void(0);">保存设置</a>
					</td>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_left" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_user();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
    	<table id="dg_id_left"></table>
		<div id="mm_id_left" class="easyui-menu" style="width:120px;">
	        <div name="userName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="userCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'角色列表'" style="">
	    <div id="tb_id_right" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id_rirht" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch_role();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
		<table id="dg_id_right"></table>
		<div id="mm_id_right">
			<div name="roleName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="roleCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>
		
</body>
</html>