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
	var $json_rsType = [ {id:"", text:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		// 初始化控件数据
		$.post('/uupm/combox/findComboByDict', 
				{'combo':'status,rs-type'}, 
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$json_status = $json_status.concat(jsonData['combo']['status'][0]['children']);
						$json_rsType = $json_rsType.concat(jsonData['combo']['rs-type'][0]['children']);
						// 初始化datagrid组件
						makeGrid_left();
					}
				}, 'json');
	});
	// 初始化datagrid组件
	function makeGrid_left() {
		$dg_left = $('#dg_id_left');
		$dg_left.treegrid({
			url:'uupm/resource/listApp',
		    width: 'auto',
		    height: $(this).height()-commonui.remainHeight-20,
		    fit:true,
			rownumbers: false,
			animate: true,
			collapsible: true,
			fitColumns: true,
			fit:true,
			border: false,
			striped: true,
			singleSelect: true,
			showHeader: true,
			toolbar: '',
			idField: 'id',
			treeField: 'rsName',
		    loadFilter: function(result) {
		    	if("OK"==result.status) {
		    		return result.data || [];
		    	} else {
		    		$.messager.show({
						title :commonui.msg_title,
						msg : result.msg,
						timeout : commonui.msg_timeout
					});
		    		return [];
	    		}
	    	},
			onClickRow: function(row) {
				makeGrid_right(row);
			},
			columns: [[
	                   	{field: 'rsName', title: '名称', width:200,align: 'left'},
						{field: 'rsCode', title: '编号', width: 100, align: 'left'},
						{field: 'rsStatus', title: '状态', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_status, value);
							}
						},
						{field: 'rsType', title: '类型', width: 100, align: 'left',
							formatter: function(value, row) {
								return utils.fmtDict($json_rsType, value);
							}	
						},
						{field: 'seqNo', title: '序号', width: 100, align: 'left'}
	                   ]]
		});
	}
	
	function makeGrid_right(row) {
		$.post('uupm/application/findAppCfg', {'appCode': row.rsCode}, function(result) {
			if(result.status=='OK') {
				var jsonData = result.data || {'total':0, 'rows':[]};
				$('#dg_id_right').propertygrid({
					data: jsonData,
					fit: true,
				    showHeader: false,
				    showGroup: false,
				    scrollbarSize: 0
				});
			}
		}, "json");
	}
	
	function saveAppCfg() {
		try{
			var row = $dg_left.treegrid('getSelected');
			var rows = $('#dg_id_right').propertygrid('getData').rows;
// 			var rows = $('#dg_id_right').propertygrid('getChanges');
			if(rows.length==0) {
				$.messager.show({
					title :commonui.msg_title,
					timeout : commonui.msg_timeout,
					msg : '保存成功',
				});
			} else {
				var paramObj = {};
				paramObj['appCode'] = row['rsCode'];
				$.each(rows, function(i, obj) {
					paramObj[obj.id] = obj.value;
				});
			}
			$.post('uupm/application/saveAppCfg', paramObj, function(result) {
				$.messager.show({
					title :commonui.msg_title,
					msg : result.msg,
					timeout : commonui.msg_timeout
				});
			}, "json");
		}catch(err){
		}
	}
</script>
</head>
<body class="easyui-layout">
    <div data-options="region:'west',title:'应用系统列表',split:true,border:true" style="width:600px;">
		<table id="dg_id_left"></table>
    </div>

    <div data-options="region:'center',title:'配置信息'" style="padding:5px;">
    	<div id="tb_id_right" style="border-bottom:0px;">
    	<a class="easyui-linkbutton" data-options="iconCls:'icon-config',plain:'true'" onclick="saveAppCfg();" href="javascript:void(0);">保存设置</a>
		</div>
    	<table id="dg_id_right"></table>
    </div>
		
</body>
</html>