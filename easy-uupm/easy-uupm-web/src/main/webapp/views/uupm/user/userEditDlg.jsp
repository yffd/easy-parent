<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
	var aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa='123';
$(function() {
	var $orgCombox = $("#orgCode_id").combotree({
		width:171,
		url:"uupm/combox/findComboTreeByOrg",
	 	editable:false,
	 	loadFilter:function(data,parent) {
	 		if("OK"==data.status) {
	 			return data.data;
	    	} else {
	    		$.messager.show({
					title :commonui.msg_title,
					msg : result.msg,
					timeout : commonui.msg_timeout
				});
	    		return [];
    		}
		},
		onLoadSuccess:function(node,data) {
			if(parent.$.modalDialog.handler.checkedFirst==true){
				if(data[0] && data[0].id) $orgCombox.combotree('setValue', data[0].id);// 设置选中
				parent.$.modalDialog.handler.checkedFirst=undefined;
			}
        },
		onBeforeExpand:function(node) {
			var children = $orgCombox.tree("getChildren", node.target);
			if(children && children.length>0) {
				return true;
			} else {
				return false;
			}
		}
	});
	
	$("#form_id").form({
		onSubmit: function() {
			parent.$.messager.progress({
				title : '提示',
				text : '数据处理中，请稍后....'
			});
			var isValid = $(this).form('validate');
			if(!isValid) {
				parent.$.messager.progress('close');
			}
			return isValid;
		},
		success: function(result) {
			parent.$.messager.progress('close');
			result = $.parseJSON(result);
			if(result.status=='OK') {
				parent.$.modalDialog.handler.dialog('close');//打开此窗口时预定义的对象
				parent.$.modalDialog.openner.datagrid('reload');//打开此窗口时预定义的对象
			}
			parent.$.modalDialog.openWindow.$.messager.show({
				title :commonui.msg_title,
				msg : result.msg,
				timeout : commonui.msg_timeout
			});
		}
	});
});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" class="edit-form-div">
		<form id="form_id" method="post" style="width:100%;height:100%;">
			<input name="id" value="" type="hidden"/>
			<fieldset>
				<table class="edit-form-table">
					<tr>
						<th>名称：</th>
						<td><input name="userName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
						<th>编号：</th>
						<td><input name="userCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
					</tr>
					<tr>
						<th>机构：</th>
						<td><input id="orgCode_id" name="orgCode" class="easyui-textbox" required="required"/></td>
						<th></th>
						<td></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
