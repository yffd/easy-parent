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
	var $arr_statusStyle = [ {id:"", text:"全部", "selected": true} ];
	var $arr_rsType = [ {id:"", text:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		$dg_left = $('#dg_id_left');
		// 初始化控件数据
		$.post('/uupm/common/listComboboxData', 
				{'keyCodes':[uidict.statusStyle,uidict.rsType].toString()},
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$arr_statusStyle = $arr_statusStyle.concat(jsonData['statusStyle']);
						$arr_rsType = $arr_rsType.concat(jsonData['rsType']);
						// 初始化datagrid组件
						makegrid_left();	
						makegrid_right();
					}
				}, 'json');
		//搜索框
		$("#searchbox_id").searchbox({
			menu:"#mm_id_left",
			prompt :'请输入',
// 			height: 28,
// 			width:200,
			searcher:function(value, name) {
				var obj = {};
				obj[name] = value;
				$dg_left.datagrid('reload', obj); 
		    }
		});
	});
	// 初始化datagrid组件
	function makegrid_left() {
		$dg_left.datagrid({
			url:'uupm/tenant/listPage',
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
			idField: 'id',
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
	    		findPmsRsCodes(rowIndex, rowData);
            },
	        columns: [[
						{field: 'ttName', title: '租户名称', width: 200, align: 'left'},
						{field: 'ttCode', title: '租户编号', width: 100, align: 'left'}
	                   ]]
		});
	}
	function makegrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.treegrid({
			url:'uupm/resource/listAllTree',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
			rownumbers: true,
			animate: true,
			collapsible: true,
			fitColumns: true,
			border: false,
			striped: true,
			singleSelect: false,
			cascadeCheck: true,
			idField: 'rsCode',
			treeField: 'rsName',
			loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data;
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
				$dg_right.treegrid('select', node.rsCode);
				$('#mm_id_right').menu('show', {
					left: e.pageX,
					top: e.pageY
				});
			},
	        columns: [[
						{field: 'ck', checkbox: true},
						{field: 'rsName', title: '名称', width:300,align: 'left'},
						{field: 'rsCode', title: '编号', width: 100, align: 'left'},
						{field: 'treeId', title: '树ID', width: 100, align: 'left'},
						{field: 'parentCode', title: '父编号', width: 100, align: 'left'},
						{field: 'rsStatus', title: '状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return uidict.fmtDict($arr_statusStyle, value);
							}
						},
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
	
	// 为角色分配功能
	function savePms() {
		var rows_rs = $dg_right.treegrid('getSelections');//获取选中的行-多行
		if(!rows_rs) {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择资源"
			});
			return;
		}
		var row_tenant = $dg_left.datagrid('getSelected');//获取选中的行-单行
		if(row_tenant) {
			parent.$.messager.confirm("提示","确定要【保存设置】吗?如果操作成功，将不可恢复。",function(r){
				if(r) {
					var rsCodeArr = [];
					if(rows_rs) {
						$.each(rows_rs, function(i, obj) {
							rsCodeArr.push(obj['rsCode']);
						});
					}
					var tenant_code = row_tenant.ttCode;
					var data={"ttCode":tenant_code, "rsCodes": JSON.stringify(rsCodeArr)};
					$.post("uupm/sec/pms/savePms", data, function(result) {
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
				}
			});
		} else {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择租户"
			});
		}
	}
	//双击事件
	function findPmsRsCodes(rowIndex, rowData) {
		$.post("uupm/sec/pms/findPmsRsCodes", {ttCode:rowData.ttCode}, function(result) {
			if(result.status=='OK') {
				$dg_right.treegrid('unselectAll');
				var data = result.data;
				if(data && data.length>0) {
					$.each(data, function(i, n) {
						var row = $dg_right.treegrid('find', n);
						if(row) $dg_right.treegrid('select', n);
					});
				} else {
					$.messager.show({
						title :commonui.msg_title,
						timeout : commonui.msg_timeout,
						msg : "该租户暂无可用资源"
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
	function cleanSearch() {
		$('#searchbox_id').searchbox('setValue', '');
	}
	// 刷新资源列表
	function reloadRight() {
		$dg_right.treegrid('reload', {});
	}
	// 展开
	function expandAll() {
		var node = $dg_right.treegrid('getSelected');
		if(node) {
			$dg_right.treegrid('expandAll', node.rsCode);
		} else {
			$dg_right.treegrid('expandAll');
		}
	}
	// 收缩
	function collapseAll() {
		var node = $dg_right.treegrid('getSelected');
		if(node) {
			$dg_right.treegrid('collapseAll', node.rsCode);
		} else {
			$dg_right.treegrid('collapseAll');
		}
	}
	
		
</script>
</head>
<body class="easyui-layout">
<!-- 	<div class="search-form-div" data-options="region:'north',border:false,title:'高级查询',iconCls:'icon-search',collapsible:true"> -->
<!-- 		<div class="badge-div" style="height:100%;"> -->
<!-- 			<span class="badge-title">提示</span> -->
<!-- 			<p style="margin:0px;padding:2px;"> -->
<!-- 				请<span class="label-info"><strong>双击角色</strong></span>查看所属资源！ -->
<!-- 				超级管理员默认拥有<span class="label-info"><strong>所有资源！</strong></span> -->
<!-- 			</p> -->
<!-- 		</div> -->
<!-- 	</div> -->
    <div data-options="region:'west',title:'租户列表',split:true,border:true" style="width:500px;">
	    <div id="tb_id_left" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:2px;padding-bottom:2px;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-config',plain:'true'" onclick="savePms();" href="javascript:void(0);">保存设置</a>
					</td>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch();" href="javascript:void(0);"></a>
					</td>
				</tr>
			</table>
		</div>
		<table id="dg_id_left"></table>
		
		<div id="mm_id_left">
			<div name="ttName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="ttCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'资源列表'" style="padding:5px;">
	    <table id="dg_id_right"></table>
		
		<div id="mm_id_right" class="easyui-menu" style="width:120px;">
	        <div onclick="expandAll()" data-options="iconCls:'icon-undo'">展开</div>
	        <div onclick="collapseAll()" data-options="iconCls:'icon-redo'">收缩</div>
	        <div class="menu-sep"></div>
	        <div onclick="reloadRight()" data-options="iconCls:'icon-reload'">刷新</div>
		</div>
    </div>
		
</body>
</html>