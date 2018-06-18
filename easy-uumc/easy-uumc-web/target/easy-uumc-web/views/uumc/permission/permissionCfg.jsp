<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var $arr_statusStyle = [ {id:"", text:"全部", "selected": true} ];
	var $arr_rsType = [ {id:"", text:"全部", "selected": true} ];
	var $openWindow = this;// 当前窗口
	var $dg_left;
	var $dg_right;
	$(function() {
		$dg_left = $('#dg_id_left');
		// 初始化控件数据
		$.post('/uumc/common/listComboboxData', 
				{'keyCodes':[uidict.status,uidict.rsType].toString()},
				function(result) {
					if("OK"==result.status) {
						var jsonData = result.data;
						$arr_statusStyle = $arr_statusStyle.concat(jsonData[uidict.status]);
						$arr_rsType = $arr_rsType.concat(jsonData[uidict.rsType]);
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
		$dg_left = $('#dg_id_left');
		$dg_left.datagrid({
			url:'uumc/sys/application/listPage',
		    width: 'auto',
		    height: 'auto',
		    fit:true, rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			border: false, striped: true, singleSelect: true,
			pagination: true,
			pageSize: commonui.pageSize,
			toolbar: '#tb_id_left',
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
	    	onClickRow: function(rowIndex, rowData) {
	    		$dg_right.treegrid('options').url='uumc/sys/resource/listTree';
	    		$dg_right.treegrid('loadData', {'status':'OK', data:[]});
	    		$dg_right.treegrid('reload', {appCode:rowData.appCode});
			},
	        columns: [[
						{field: 'appName', title: '系统应用名称', width: 200, align: 'left'},
						{field: 'appCode', title: '系统应用编号', width: 200, align: 'left'}
	                   ]]
		});
	}
	function makegrid_right() {
		$dg_right = $('#dg_id_right');
		$dg_right.treegrid({
		    width: '500',
		    height: 'auto',
			rownumbers: true, animate: true, collapsible: true, fitColumns: true,
			border: false, striped: true, singleSelect: false, cascadeCheck: true,
			toolbar: '#tb_id_right',
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
	// 清除搜索条件
	function cleanSearch() {
		$('#searchbox_id').searchbox('setValue', '');
	}
	function havePms() {
		var row_left = $dg_left.datagrid('getSelected');//获取选中的行-单行
		if(!row_left) {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【系统应用】记录"
			});
			return;
		}
		$.post("uumc/permission/listHavePmsForRsCodes", {appCode:row_left.appCode}, function(result) {
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
						msg : "未找到有效数据"
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
	function savePms() {
		var row_left = $dg_left.datagrid('getSelected');//获取选中的行-单行
		if(!row_left) {
			parent.$.messager.show({
				title :commonui.msg_title,
				timeout : commonui.msg_timeout,
				msg : "请选择一行【系统应用】记录"
			});
			return;
		}
		var rows_right = $dg_right.treegrid('getSelections');//获取选中的行-多行
		var rsCodeArr = [];
		if(rows_right) {
			$.each(rows_right, function(i, obj) {
				rsCodeArr.push(obj['rsCode']);
			});
		}
		parent.$.messager.confirm("提示","确定要【保存设置】吗?如果操作成功，将不可恢复。",function(r){
			if(r) {
				var appCode = row_left.appCode;
				var data={"appCode":appCode, "rsCodes": JSON.stringify(rsCodeArr)};
				$.post("uumc/permission/savePms", data, function(result) {
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
	}
</script>
<div class="easyui-layout" data-options="fit:true,border:false" >
	<div data-options="region:'west',title:'系统应用列表',split:true,border:true" style="width:500px;">
		<table id="dg_id_left"></table>
	    <div id="tb_id_left" style="background-color: #F5F5F5;padding-left:25px;">
	    	<table cellpadding="0" cellspacing="0">
				<tr>
					<td style="padding-left:10px;padding-bottom:2px;">
						<input id="searchbox_id" type="text"/>
					</td>
					<td style="padding-left:2px">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-clear',plain:'true'" onclick="cleanSearch();" href="javascript:void(0);"></a>
					</td>
					<td style="padding-left:2px;padding-bottom:2px;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:'true'" onclick="havePms();" href="javascript:void(0);">已授权</a>
					</td>
					<td style="padding-left:2px;padding-bottom:2px;">
						<a class="easyui-linkbutton" data-options="iconCls:'icon-config',plain:'true'" onclick="savePms();" href="javascript:void(0);">保存设置</a>
					</td>
				</tr>
			</table>
		</div>
		<div id="mm_id_left">
			<div name="appName">&nbsp;&nbsp;名称&nbsp;&nbsp;&nbsp;&nbsp;</div>
			<div name="appCode">&nbsp;&nbsp;编号&nbsp;&nbsp;&nbsp;&nbsp;</div>
		</div>
    </div>

    <div data-options="region:'center',title:'系统资源列表'" style="padding:5px;">
	    <table id="dg_id_right"></table>
		<div id="mm_id_right" class="easyui-menu" style="width:120px;">
	        <div onclick="expandAll()" data-options="iconCls:'icon-undo'">展开</div>
	        <div onclick="collapseAll()" data-options="iconCls:'icon-redo'">收缩</div>
	        <div class="menu-sep"></div>
	        <div onclick="reloadRight()" data-options="iconCls:'icon-reload'">刷新</div>
		</div>
    </div>
</div>