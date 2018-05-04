<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<script type="text/javascript">
$(function() {
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
						<th>租户名称：</th>
						<td><input name="tenantName" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入名称" /></td>
						<th>租户编号：</th>
						<td><input id="tenantCode_id" name="tenantCode" class="easyui-textbox easyui-validatebox" required="required" placeholder="请输入编号"/></td>
					</tr>
					<tr>
						<th>类型：</th>
						<td><input id="tenantType_id" name="tenantType" type="text"/></td>
						<th>状态：</th>
						<td><input id="tenantStatus_id" name="tenantStatus" type="text" /></td>
					</tr>
					<tr>
						<th>服务方式：</th>
						<td><input id="serveType_id" name="serveType" type="text" /></td>
						<th></th><td></td>
					</tr>
					<tr>
						<th>服务开始时间：</th>
						<td><input type="text" name="startTime" id="startTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endTime\',{d:0});}'})"/></td>
						<th>服务结束时间：</th>
						<td><input type="text" name="endTime" id="endTime" class="Wdate" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startTime\',{d:0});}'})"/></td>
					</tr>
				</table>
			</fieldset>
		</form>
	</div>
</div>
